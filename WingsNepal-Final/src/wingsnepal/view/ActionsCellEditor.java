package wingsnepal.view;

import wingsnepal.dao.ReservationDao;
import wingsnepal.model.ReservationModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionsCellEditor extends AbstractCellEditor implements javax.swing.table.TableCellEditor {

    private JPanel panel;
    private JButton editButton;
    private JButton deleteButton;
    private JTable table;
    private ReservationDao reservationDao;
    private Runnable refreshAction;

    public ActionsCellEditor(JTable table, Runnable refreshAction) {
        this.table = table;
        this.refreshAction = refreshAction;
        this.reservationDao = new ReservationDao();
        
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");

        panel.add(editButton);
        panel.add(deleteButton);

        editButton.addActionListener(e -> {
            fireEditingStopped();
            int selectedRow = table.convertRowIndexToModel(table.getEditingRow());
            // Assuming the first column is the reservation ID, which is not ideal.
            // A better approach is to have a hidden column with the database primary key.
            int reservationId = (int) table.getModel().getValueAt(selectedRow, 0);

            ReservationModel reservation = reservationDao.getReservationById(reservationId);
            if (reservation != null) {
                EditReservationDialog dialog = new EditReservationDialog(
                        (Frame) SwingUtilities.getWindowAncestor(table),
                        true,
                        reservation,
                        refreshAction);
                dialog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(table, "Could not find reservation details.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        deleteButton.addActionListener(e -> {
            fireEditingStopped();
            int selectedRow = table.convertRowIndexToModel(table.getEditingRow());
            int reservationId = (int) table.getModel().getValueAt(selectedRow, 0);
            String passengerName = table.getModel().getValueAt(selectedRow, 1).toString().split("<")[0];


            int choice = JOptionPane.showConfirmDialog(
                    table,
                    "Are you sure you want to delete the reservation for " + passengerName + "?",
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);

            if (choice == JOptionPane.YES_OPTION) {
                boolean success = reservationDao.deleteReservation(reservationId);
                if (success) {
                    JOptionPane.showMessageDialog(table, "Reservation deleted successfully.");
                    refreshAction.run();
                } else {
                    JOptionPane.showMessageDialog(table, "Failed to delete reservation.", "Database Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        // Set the background of the panel to match the table's selection
        panel.setBackground(table.getSelectionBackground());
        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return "";
    }
} 