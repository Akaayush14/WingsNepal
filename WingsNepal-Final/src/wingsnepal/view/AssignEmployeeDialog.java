package wingsnepal.view;

import javax.swing.*;
import java.awt.*;

public class AssignEmployeeDialog extends JDialog {

    private String flightCode;

    public AssignEmployeeDialog(Frame owner, String flightCode) {
        super(owner, "Assign Employees to Flight: " + flightCode, true);
        this.flightCode = flightCode;
        
        initComponents();
        
        setSize(500, 400);
        setLocationRelativeTo(owner);
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        
        JLabel titleLabel = new JLabel("Managing assignments for flight: " + flightCode, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        
        // In the future, this will contain lists of assigned and unassigned employees.
        JPanel placeholderPanel = new JPanel();
        placeholderPanel.add(new JLabel("Employee assignment functionality will be built here."));
        
        add(titleLabel, BorderLayout.NORTH);
        add(placeholderPanel, BorderLayout.CENTER);
    }
} 