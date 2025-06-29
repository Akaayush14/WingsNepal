package wingsnepal.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

public class MetricCardPanel extends JPanel {

    public MetricCardPanel() {
        super();
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension arcs = new Dimension(15, 15); // Border radius
        int width = getWidth();
        int height = getHeight();
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draws the rounded panel with a light grey background.
        graphics.setColor(new Color(248, 249, 250));
        graphics.fillRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height);
        
        // Adds a border
        graphics.setColor(new Color(222, 226, 230));
        graphics.drawRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height);
    }
} 