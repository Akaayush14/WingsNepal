package wingsnepalController;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import wingsnepal.model.StripePayment;
import wingsnepal.view.StripePaymentView;

/**
 *
 * @author Aayush Kharel
 */
public class StripePaymentController {

    private StripePaymentView view;
    private StripePayment model;

    public StripePaymentController(StripePaymentView view, StripePayment model) {
        this.view = view;
        this.model = model;

        // Set action listener for the charge button
        view.getChargeButton().addActionListener(new HandlePaymentStripe());
    }

    public void open() {
        view.setVisible(true); 
    }

    public void close() {
        view.dispose();
    }

    class HandlePaymentStripe implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            // Show processing message
            view.displayMessage("Processing payment...");

            // Call model to create the checkout session
            String sessionUrl = model.createCheckoutSession();
            System.out.println("Session URL: " + sessionUrl);

            if (sessionUrl != null) {
                view.displayMessage("Redirecting to payment page...");

                // Directly open the session URL
                try {
                    if (Desktop.isDesktopSupported()) {
                        Desktop.getDesktop().browse(new java.net.URI(sessionUrl));
                    }
                } catch (IOException | URISyntaxException ex) {
                    view.showPaymentFailure("Failed to open payment page.");
                    return;
                }
                boolean paymentStatus;
                try {
                    paymentStatus = model.checkPaymentStatus(sessionUrl); // Pass the session URL to check status
                if (paymentStatus) {
                    // If payment is successful
                    view.showPaymentSuccess();
                } else {
                    // If payment failed or was not completed
                    view.showPaymentFailure("Payment failed or not completed.");
                }
                 } catch (InterruptedException ex) {
                    Logger.getLogger(StripePaymentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                view.showPaymentFailure("Failed to create checkout session.");
            }
        }
    }
}