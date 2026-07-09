package com.ticketapp;

import com.ticketapp.ui.MainFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class App {

    public static void main(String[] args) {
        // 1. Generate Static Avatars for the Team profiles if they don't exist yet
        generateStaticAvatars();

        // 2. Set System Look and Feel for modern windows elements if possible
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Could not set System Look and Feel: " + e.getMessage());
        }

        // 3. Launch application on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }

    /**
     * Programmatically generates high-quality circular avatar images with initials
     * to fulfill the static photos requirement. They are saved in `resources/images/`.
     */
    private static void generateStaticAvatars() {
        File dir = new File("resources/images");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String[][] members = {
            {"DN", "avatar1.png", "#1E1E2F", "#14B8A6"}, // Dark Gray & Teal
            {"PR", "avatar2.png", "#1E293B", "#3B82F6"}, // Slate & Blue
            {"DW", "avatar3.png", "#111827", "#10B981"}  // Dark & Emerald
        };

        for (String[] m : members) {
            File imgFile = new File(dir, m[1]);
            if (!imgFile.exists()) {
                try {
                    int size = 200;
                    BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g2 = img.createGraphics();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                    // Decode background gradients
                    Color color1 = Color.decode(m[2]);
                    Color color2 = Color.decode(m[3]);

                    // Paint gradient background
                    GradientPaint gp = new GradientPaint(0, 0, color1, size, size, color2);
                    g2.setPaint(gp);
                    g2.fillOval(0, 0, size, size);

                    // Draw a subtle border
                    g2.setColor(new Color(255, 255, 255, 180));
                    g2.setStroke(new BasicStroke(6));
                    g2.drawOval(3, 3, size - 7, size - 7);

                    // Write Initials in Center
                    g2.setColor(Color.WHITE);
                    g2.setFont(new Font("Segoe UI", Font.BOLD, 72));
                    FontMetrics fm = g2.getFontMetrics();
                    int tx = (size - fm.stringWidth(m[0])) / 2;
                    int ty = ((size - fm.getHeight()) / 2) + fm.getAscent();
                    g2.drawString(m[0], tx, ty);

                    g2.dispose();
                    ImageIO.write(img, "png", imgFile);
                    System.out.println("Generated avatar: " + imgFile.getAbsolutePath());
                } catch (IOException e) {
                    System.err.println("Error generating avatar file " + m[1] + ": " + e.getMessage());
                }
            }
        }
    }
}
