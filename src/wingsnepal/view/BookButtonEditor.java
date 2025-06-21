package wingsnepal.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.TableCellEditor;
import wingsnepal.dao.SeatClassDao;
import java.util.List;

/**
 * Button editor for the "Book" action in UserPortal's JTable
 */
public class BookButtonEditor extends AbstractCellEditor implements TableCellEditor {
    private final JButton button = new JButton("Book");
    private final UserPortal userPortal;
    private final JTable table;

    public BookButtonEditor(UserPortal userPortal, JTable table) {
        this.userPortal = userPortal;
        this.table = table;

        button.setOpaque(true);
        button.setBackground(new Color(0, 102, 153));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI Emoji", Font.BOLD, 12));
        button.addActionListener(e -> handleBooking());
    }

    private void handleBooking() {
        int row = table.getSelectedRow();
        if (row != -1) {
            Object flightCode = table.getValueAt(row, 0);
            Object flightName = table.getValueAt(row, 1);
            Object date = table.getValueAt(row, 5);

            userPortal.getFlightCodeTextField().setText(flightCode.toString());
            userPortal.getFlightNameTextField().setText(flightName.toString());
            userPortal.getSeatComboBox().setSelectedItem("Economy");

            SeatClassDao dao = new SeatClassDao();
            int flightId = dao.getFlightIdByCode(flightCode.toString());
            List<String> seatNumbers = dao.getAvailableSeats(flightId, "Economy");

            userPortal.getSeatNoComboBox().removeAllItems();
            for (String seat : seatNumbers) {
                userPortal.getSeatNoComboBox().addItem(seat);
            }

            int price = dao.getPriceByFlightAndClass(flightId, "Economy");
            userPortal.getPriceTextField().setText(String.valueOf(price));

            userPortal.getFullNameTextField().setText(userPortal.getLoggedInUser().getFullName());
            userPortal.getEmailTextField().setText(userPortal.getLoggedInUser().getEmail());

            userPortal.lockTravelFields(); // fixed wrapper call

            if (date != null) {
                try {
                    java.sql.Date sqlDate = java.sql.Date.valueOf(date.toString());
                    java.util.Calendar cal = java.util.Calendar.getInstance();
                    cal.setTime(sqlDate);

                    userPortal.getTravelYearChooser().setYear(cal.get(java.util.Calendar.YEAR));
                    userPortal.getTravelMonthChooser().setMonth(cal.get(java.util.Calendar.MONTH));
                    userPortal.getTravelDaySpinnerField().setValue(cal.get(java.util.Calendar.DAY_OF_MONTH));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            userPortal.getMainTabbedPane().setSelectedIndex(2); // Go to Book Flights tab
        }
        fireEditingStopped();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        return "Book";
    }
}
