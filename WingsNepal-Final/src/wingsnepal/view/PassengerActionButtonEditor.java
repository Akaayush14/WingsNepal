package wingsnepal.view;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PassengerActionButtonEditor extends AbstractCellEditor implements TableCellEditor {
    private final JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
    private final JButton editButton = new JButton("Edit");
    private final JButton deleteButton = new JButton("Delete");
    private JTable table;
    private int row;
    private ActionListener onEdit;
    private ActionListener onDelete;

    public PassengerActionButtonEditor(ActionListener onEdit, ActionListener onDelete) {
        this.onEdit = onEdit;
        this.onDelete = onDelete;
        panel.add(editButton);
        panel.add(deleteButton);
        editButton.addActionListener(e -> {
            if (table != null) {
                onEdit.actionPerformed(new ActionEvent(table, ActionEvent.ACTION_PERFORMED, "Edit", System.currentTimeMillis(), 0));
            }
            fireEditingStopped();
        });
        deleteButton.addActionListener(e -> {
            if (table != null) {
                onDelete.actionPerformed(new ActionEvent(table, ActionEvent.ACTION_PERFORMED, "Delete", System.currentTimeMillis(), 0));
            }
            fireEditingStopped();
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.table = table;
        this.row = row;
        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return null;
    }
} 