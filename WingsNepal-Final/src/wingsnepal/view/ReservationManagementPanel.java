package wingsnepal.view;

import wingsnepal.dao.ReservationDao;
import wingsnepal.model.ReservationModel;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReservationManagementPanel extends JPanel {

    private JTable reservationsTable;
    private DefaultTableModel tableModel;
    private ReservationDao reservationDao;
    private JLabel totalReservationsLabel;

    public ReservationManagementPanel() {
        reservationDao = new ReservationDao();
        initComponents();
        loadReservations();
    }

    private void initComponents() {
        // Match the background color from your AdminDashboard.form
        setBackground(new Color(204, 204, 255));
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // --- Header Panel ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false); // Make it transparent to show panel background

        totalReservationsLabel = new JLabel("Reservation Management");
        totalReservationsLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        totalReservationsLabel.setForeground(new Color(46, 62, 79)); // Dark blue-grey text
        headerPanel.add(totalReservationsLabel, BorderLayout.WEST);

        // --- Top Right Panel for the button ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        JButton newReservationButton = new JButton("+ New Reservation");
        // Style it like the other buttons in your .form file
        newReservationButton.setBackground(new Color(0, 102, 255));
        newReservationButton.setForeground(Color.WHITE);
        newReservationButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        newReservationButton.setFocusPainted(false);
        newReservationButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        newReservationButton.addActionListener(e -> openAddReservationDialog());
        buttonPanel.add(newReservationButton);
        headerPanel.add(buttonPanel, BorderLayout.EAST);

        // --- Table ---
        String[] columnNames = {"ID", "Reservation ID", "Passenger", "Flight", "Route", "Date & Time", "Seat", "Class", "Status", "Amount", "Actions"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 10; // Only actions column is editable
            }
        };
        reservationsTable = new JTable(tableModel);
        setupTableStyle();

        // --- Renderers and Editors ---
        reservationsTable.getColumnModel().getColumn(8).setCellRenderer(new StatusCellRenderer());
        ActionsCellEditor actionsCellEditor = new ActionsCellEditor(reservationsTable, this::loadReservations);
        reservationsTable.getColumnModel().getColumn(10).setCellRenderer(new ActionsCellRenderer());
        reservationsTable.getColumnModel().getColumn(10).setCellEditor(actionsCellEditor);

        // Hide the raw ID column
        reservationsTable.getColumnModel().getColumn(0).setMinWidth(0);
        reservationsTable.getColumnModel().getColumn(0).setMaxWidth(0);
        reservationsTable.getColumnModel().getColumn(0).setWidth(0);

        JScrollPane scrollPane = new JScrollPane(reservationsTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(224, 224, 224)));

        add(headerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void setupTableStyle() {
        reservationsTable.setRowHeight(50);
        reservationsTable.setFillsViewportHeight(true);
        reservationsTable.setBackground(Color.WHITE);
        reservationsTable.setIntercellSpacing(new Dimension(0, 0));
        reservationsTable.setShowGrid(false);
        
        // Header style based on your .form file
        JTableHeader header = reservationsTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        header.setOpaque(false);
        header.setBackground(new Color(242, 242, 242));
        header.setForeground(Color.BLACK);
        header.setPreferredSize(new Dimension(header.getWidth(), 40));
        
        // Center headers
        ((DefaultTableCellRenderer)header.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
        
        // Align cell content
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        
        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment( JLabel.LEFT );
        
        reservationsTable.getColumnModel().getColumn(2).setCellRenderer(leftRenderer); // Passenger
    }

    public void loadReservations() {
        List<ReservationModel> reservations = reservationDao.getAllReservations();
        tableModel.setRowCount(0); // Clear existing data
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        for (ReservationModel res : reservations) {
            Object[] row = {
                res.getId(),
                res.getReservationId(),
                "<html><div style='padding: 5px;'><b>" + res.getPassengerName() + "</b><br><span style='color:gray;'>" + res.getPassengerEmail() + "</span></div></html>",
                res.getFlightNumber(),
                res.getRouteFrom() + " → " + res.getRouteTo(),
                res.getDepartureDateTime().format(formatter),
                res.getSeatNumber(),
                res.getSeatClass(),
                res.getStatus(),
                "NPR " + String.format("%,.2f", res.getAmount()),
                ""
            };
            tableModel.addRow(row);
        }
    }

    private void openAddReservationDialog() {
        AddReservationDialog dialog = new AddReservationDialog((JFrame) SwingUtilities.getWindowAncestor(this), true, this::loadReservations);
        dialog.setVisible(true);
    }
}

class ActionsCellRenderer extends JPanel implements javax.swing.table.TableCellRenderer {
    private final JButton editButton = new JButton("Edit");
    private final JButton deleteButton = new JButton("Delete");

    public ActionsCellRenderer() {
        super(new FlowLayout(FlowLayout.CENTER, 5, 5));
        setOpaque(true);
        setBackground(Color.WHITE); // Match table row background
        
        // Style buttons to be simple and clean
        styleButton(editButton, new Color(0, 153, 112)); // Vibrant green
        styleButton(deleteButton, new Color(231, 76, 60)); // Red

        add(editButton);
        add(deleteButton);
    }
    
    private void styleButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
            setBackground(table.getSelectionBackground());
        } else {
            setBackground(table.getBackground());
        }
        return this;
    }
}

// A placeholder class to resolve KGradientPanel dependency.
// In a real scenario, you would have this library.
class KGradientPanel {
    static class RoundedBorder implements javax.swing.border.Border {
        private int radius;
        private int thickness;
        private Color color;
        public RoundedBorder(Color color, int radius, int thickness) {
            this.radius = radius;
            this.thickness = thickness;
            this.color = color;
        }
        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
        }
        public boolean isBorderOpaque() {
            return true;
        }
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            // This is a simplified version. A real rounded border is more complex.
            g.setColor(c.getParent().getBackground());
            g.drawRoundRect(x, y, width-1, height-1, radius, radius);
        }
    }
} 