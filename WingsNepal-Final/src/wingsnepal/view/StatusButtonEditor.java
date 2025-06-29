package wingsnepal.view;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import java.awt.Component;

public class StatusButtonEditor extends DefaultCellEditor {
    private JComboBox<String> comboBox;

    public StatusButtonEditor() {
        super(new JComboBox<String>());
        comboBox = (JComboBox<String>) getComponent();
        comboBox.addItem("Active");
        comboBox.addItem("Inactive");
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        comboBox.setSelectedItem(value != null ? value.toString() : "Active");
        return comboBox;
    }

    @Override
    public Object getCellEditorValue() {
        return comboBox.getSelectedItem();
    }
} 