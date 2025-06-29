package wingsnepal.view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

public class CheckInButtonEditor extends AbstractCellEditor implements TableCellEditor {
    private JButton button;
    private int row;
    private JTable table;
    private ActionListener actionListener;

    public CheckInButtonEditor(JTable table, ActionListener actionListener) {
        this.table = table;
        this.actionListener = actionListener;
        button = new JButton("Check In");
        button.setBackground(new java.awt.Color(0, 153, 51));
        button.setForeground(java.awt.Color.WHITE);
        button.setFocusPainted(false);
        button.addActionListener(e -> {
            if (actionListener != null) {
                actionListener.actionPerformed(new ActionEvent(table, ActionEvent.ACTION_PERFORMED, String.valueOf(row)));
            }
            fireEditingStopped();
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.row = row;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        return "";
    }
} 