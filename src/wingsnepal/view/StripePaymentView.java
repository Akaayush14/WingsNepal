/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wingsnepal.view;

import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;



public class StripePaymentView extends JFrame {

    private JButton chargeButton;
    private JTextArea outputArea;

    public StripePaymentView() {
        setTitle("Stripe Payment UI");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        chargeButton = new JButton("Charge $20");
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        add(chargeButton, BorderLayout.NORTH);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);
    }

    public JButton getChargeButton() {
        return chargeButton;
    }

    public JTextArea getOutputArea() {
        return outputArea;
    }

    public void displayMessage(String message) {
        outputArea.append(message + "\n");
    }

    // Show payment success message
    public void showPaymentSuccess() {
        displayMessage("Payment Successful!");
    }

    // Show payment failure message
    public void showPaymentFailure(String message) {
        displayMessage("Payment Failed: " + message);
    }
}
