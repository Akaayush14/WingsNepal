package wingsnepalController;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.FileOutputStream;
import javax.swing.JOptionPane;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;

public class TicketGenerator {
    
    // Method to generate the ticket PDF
    public void generateTicket(int userId, String flightCode, String flightName, 
                                String seatClass, String seatNo, String fullName, 
                                String email, java.sql.Date travelDate, String paymentMethod) {
        // Create a PdfWriter instance and PdfDocument
        try {
            String fileName = "Ticket_" + userId + "_" + flightCode + ".pdf";
            PdfWriter writer = new PdfWriter(new FileOutputStream(fileName));
            PdfDocument pdfDoc = new PdfDocument(writer);

            // Create a Document object
            Document document = new Document(pdfDoc);

            // Load Helvetica Bold font from the built-in fonts
            PdfFont font = PdfFontFactory.createFont("Helvetica-Bold");

            // Add title to the document using the loaded font
            document.add(new Paragraph("Wings Nepal Flight Ticket")
                .setFont(font)
                .setFontSize(18));

            // Add booking details using the same font
            document.add(new Paragraph("User ID: " + userId));
            document.add(new Paragraph("Full Name: " + fullName));
            document.add(new Paragraph("Email: " + email));
            document.add(new Paragraph("Flight Code: " + flightCode));
            document.add(new Paragraph("Flight Name: " + flightName));
            document.add(new Paragraph("Seat Class: " + seatClass));
            document.add(new Paragraph("Seat No: " + seatNo));
            document.add(new Paragraph("Travel Date: " + travelDate));
            document.add(new Paragraph("Payment Method: " + paymentMethod));

            // Add confirmation message
            document.add(new Paragraph("Booking Confirmation: Payment Successful"));

            // Close the document
            document.close();

            // Show confirmation
            JOptionPane.showMessageDialog(null, "Ticket generated successfully!");

            // Automatically open the PDF in the system's default PDF viewer
            openPdf(fileName);  // Auto open the ticket

            // Or you can use this code if you want the user to choose where to save the file:
            // savePdfWithFileChooser(fileName, userId, flightCode, flightName, seatClass, seatNo, fullName, email, travelDate, paymentMethod);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error generating ticket: " + e.getMessage());
        }
    }

    // Method to automatically open the generated PDF in the default PDF viewer
    private void openPdf(String fileName) {
        try {
            // Create a File object for the generated PDF
            File pdfFile = new File(fileName);

            // Check if Desktop is supported (for opening the file)
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (pdfFile.exists()) {
                    // Open the PDF file automatically
                    desktop.open(pdfFile);
                } else {
                    System.out.println("File not found.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error opening PDF: " + e.getMessage());
        }
    }

    // Method to show a save dialog and allow the user to save the PDF file
    private void savePdfWithFileChooser(String fileName, int userId, String flightCode, String flightName, 
                                         String seatClass, String seatNo, String fullName, String email, 
                                         java.sql.Date travelDate, String paymentMethod) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save PDF Ticket");

        // Set default file name
        fileChooser.setSelectedFile(new File(fileName));

        // Open the Save As dialog
        int userSelection = fileChooser.showSaveDialog(null);

        // If the user chooses a valid file location
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            
            // Generate and save the ticket PDF at the chosen location
            try {
                // Create a PdfWriter instance with the new file location
                PdfWriter writer = new PdfWriter(new FileOutputStream(fileToSave));
                PdfDocument pdfDoc = new PdfDocument(writer);

                // Create a Document object
                Document document = new Document(pdfDoc);

                // Add content to the document (similar to the generateTicket method)
                PdfFont font = PdfFontFactory.createFont("Helvetica-Bold");

                document.add(new Paragraph("Wings Nepal Flight Ticket").setFont(font).setFontSize(18));
                document.add(new Paragraph("User ID: " + userId));
                document.add(new Paragraph("Full Name: " + fullName));
                document.add(new Paragraph("Email: " + email));
                document.add(new Paragraph("Flight Code: " + flightCode));
                document.add(new Paragraph("Flight Name: " + flightName));
                document.add(new Paragraph("Seat Class: " + seatClass));
                document.add(new Paragraph("Seat No: " + seatNo));
                document.add(new Paragraph("Travel Date: " + travelDate));
                document.add(new Paragraph("Payment Method: " + paymentMethod));
                document.add(new Paragraph("Booking Confirmation: Payment Successful"));

                document.close();

                // Show confirmation message
                JOptionPane.showMessageDialog(null, "Ticket saved to: " + fileToSave.getAbsolutePath());
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error saving ticket: " + e.getMessage());
            }
        }
    }
}
