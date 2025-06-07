/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wingsnepal.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Aayush Kharel
 */
public class ButtonRenderer extends JButton implements TableCellRenderer {

    public ButtonRenderer() {
        setOpaque(true);
        setText("Book");
        
        // Styling
        setBackground(new Color(0,102,153));     // Button background color
        setForeground(Color.WHITE);                // Text color
        setFont(new Font("Segoe UI Emoji", Font.BOLD, 10));  // Font style
        setCursor(new Cursor(Cursor.HAND_CURSOR));     // Hand cursor on hover
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        return this;
    }
}






