package com.ticketapp.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class AboutUsPage extends JPanel {

    public AboutUsPage() {
        setLayout(new BorderLayout(15, 15));
        setBackground(UIHelper.COLOR_BACKGROUND);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        initComponents();
    }

    private void initComponents() {
        // --- TITLE HEADER ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JLabel lblTitle = new JLabel("Tentang Kami (About Us)");
        lblTitle.setFont(UIHelper.FONT_TITLE);
        lblTitle.setForeground(UIHelper.COLOR_TEXT_DARK);
        headerPanel.add(lblTitle, BorderLayout.WEST);

        JLabel lblSub = new JLabel("Informasi Aplikasi Penjualan Tiket santep.com dan Tim Pengembang.");
        lblSub.setFont(UIHelper.FONT_SMALL_BOLD);
        lblSub.setForeground(UIHelper.COLOR_TEXT_LIGHT);
        headerPanel.add(lblSub, BorderLayout.SOUTH);

        add(headerPanel, BorderLayout.NORTH);

        // --- CONTENT PANEL ---
        JPanel contentPanel = new JPanel(new BorderLayout(20, 0));
        contentPanel.setOpaque(false);

        // 1. LEFT SIDE: App Information Card
        UIHelper.RoundedPanel appInfoCard = new UIHelper.RoundedPanel(16, UIHelper.COLOR_CARD_BG, UIHelper.COLOR_BORDER, 1);
        appInfoCard.setLayout(new BoxLayout(appInfoCard, BoxLayout.Y_AXIS));
        appInfoCard.setBorder(new EmptyBorder(24, 24, 24, 24));
        appInfoCard.setPreferredSize(new Dimension(420, 0));
        appInfoCard.setMaximumSize(new Dimension(420, Integer.MAX_VALUE));

        JLabel lblAppTitle = new JLabel("Sistem Informasi Penjualan Tiket");
        lblAppTitle.setFont(UIHelper.FONT_SUBTITLE);
        lblAppTitle.setForeground(UIHelper.COLOR_PRIMARY);
        lblAppTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        appInfoCard.add(lblAppTitle);

        JLabel lblAppDomain = new JLabel("santep.com Platform");
        lblAppDomain.setFont(UIHelper.FONT_SMALL_BOLD);
        lblAppDomain.setForeground(UIHelper.COLOR_SECONDARY);
        lblAppDomain.setAlignmentX(Component.LEFT_ALIGNMENT);
        appInfoCard.add(lblAppDomain);

        appInfoCard.add(Box.createRigidArea(new Dimension(0, 16)));

        // Wrap HTML text to align nicely and limit width
        JLabel lblDesc = new JLabel("<html><body style='width: 320px;'>" 
                + "<b>santep.com</b> adalah platform modern yang dirancang untuk memudahkan manajemen transaksi kasir penjualan tiket konser, pertunjukan seni, "
                + "wisata, dan berbagai event digital lainnya secara cepat, efisien, dan aman.<br><br>"
                + "Aplikasi ini dibangun menggunakan bahasa pemrograman Java dengan teknologi visual berbasis GUI Java Swing & AWT, serta "
                + "memanfaatkan database MySQL untuk persistensi data transaksi. Desain antarmuka dirancang dengan mengutamakan "
                + "keindahan estetika modern, kemudahan penggunaan (usabilitas), serta respon navigasi yang cepat."
                + "</body></html>");
        lblDesc.setFont(UIHelper.FONT_BODY);
        lblDesc.setForeground(UIHelper.COLOR_TEXT_DARK);
        lblDesc.setAlignmentX(Component.LEFT_ALIGNMENT);
        appInfoCard.add(lblDesc);

        appInfoCard.add(Box.createRigidArea(new Dimension(0, 24)));

        // Specifications Section
        JLabel lblSpecTitle = new JLabel("Spesifikasi Sistem:");
        lblSpecTitle.setFont(UIHelper.FONT_BODY_BOLD);
        lblSpecTitle.setForeground(UIHelper.COLOR_TEXT_DARK);
        lblSpecTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        appInfoCard.add(lblSpecTitle);

        appInfoCard.add(Box.createRigidArea(new Dimension(0, 10)));

        JPanel specPanel = new JPanel(new GridLayout(5, 2, 10, 8));
        specPanel.setOpaque(false);
        specPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        specPanel.setMaximumSize(new Dimension(380, 120));

        addSpecRow(specPanel, "Versi Aplikasi", "1.0.0 (Stable)");
        addSpecRow(specPanel, "Basis Data", "MySQL / MariaDB");
        addSpecRow(specPanel, "Runtime Engine", "Adoptium OpenJDK 21");
        addSpecRow(specPanel, "Teknologi GUI", "Java Swing & AWT");
        addSpecRow(specPanel, "Estetika Tema", "Traveloka Blue / Solid Modern");

        appInfoCard.add(specPanel);

        // 2. RIGHT SIDE: Development Team
        JPanel teamContainer = new JPanel(new BorderLayout(0, 15));
        teamContainer.setOpaque(false);

        JLabel lblTeamTitle = new JLabel("Tim Pengembang Aplikasi");
        lblTeamTitle.setFont(UIHelper.FONT_SUBTITLE);
        lblTeamTitle.setForeground(UIHelper.COLOR_PRIMARY);
        teamContainer.add(lblTeamTitle, BorderLayout.NORTH);

        // 1x3 Grid of Developer Cards
        JPanel gridPanel = new JPanel(new GridLayout(1, 3, 16, 16));
        gridPanel.setOpaque(false);

        gridPanel.add(createDeveloperCard(
                "Denanda", 
                "Project Manager & Lead Dev", 
                "resources/images/avatar1.png", 
                "DN", 
                Color.decode("#1E1E2F"), 
                Color.decode("#14B8A6")
        ));
        gridPanel.add(createDeveloperCard(
                "Pramaditha", 
                "Frontend Engineer", 
                "resources/images/avatar2.png", 
                "PR", 
                Color.decode("#1E293B"), 
                Color.decode("#3B82F6")
        ));
        gridPanel.add(createDeveloperCard(
                "Dwi Wibawa", 
                "Backend Engineer", 
                "resources/images/avatar3.png", 
                "DW", 
                Color.decode("#111827"), 
                Color.decode("#10B981")
        ));

        teamContainer.add(gridPanel, BorderLayout.CENTER);

        contentPanel.add(appInfoCard, BorderLayout.WEST);
        contentPanel.add(teamContainer, BorderLayout.CENTER);

        add(contentPanel, BorderLayout.CENTER);
    }

    private void addSpecRow(JPanel panel, String label, String value) {
        JLabel lblL = new JLabel(label);
        lblL.setFont(UIHelper.FONT_SMALL_BOLD);
        lblL.setForeground(UIHelper.COLOR_TEXT_LIGHT);

        JLabel lblV = new JLabel(value);
        lblV.setFont(UIHelper.FONT_SMALL);
        lblV.setForeground(UIHelper.COLOR_TEXT_DARK);

        panel.add(lblL);
        panel.add(lblV);
    }

    private JPanel createDeveloperCard(String name, String role, String avatarPath, String initials, Color startGrad, Color endGrad) {
        // Create specialized hover rounded card
        HoverRoundedPanel card = new HoverRoundedPanel(16, UIHelper.COLOR_CARD_BG, UIHelper.COLOR_BORDER, 1);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(20, 15, 20, 15));

        // Avatar Image Label
        JLabel lblAvatarImage = new JLabel();
        lblAvatarImage.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblAvatarImage.setPreferredSize(new Dimension(80, 80));
        lblAvatarImage.setMinimumSize(new Dimension(80, 80));
        lblAvatarImage.setMaximumSize(new Dimension(80, 80));

        // Load static file or fallback icon
        File file = new File(avatarPath);
        if (file.exists()) {
            try {
                ImageIcon icon = new ImageIcon(avatarPath);
                Image img = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
                lblAvatarImage.setIcon(new ImageIcon(img));
            } catch (Exception e) {
                lblAvatarImage.setIcon(createFallbackAvatarIcon(initials, startGrad, endGrad));
            }
        } else {
            lblAvatarImage.setIcon(createFallbackAvatarIcon(initials, startGrad, endGrad));
        }

        // Developer Name
        JLabel lblName = new JLabel(name);
        lblName.setFont(UIHelper.FONT_BODY_BOLD);
        lblName.setForeground(UIHelper.COLOR_TEXT_DARK);
        lblName.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Role
        JLabel lblRole = new JLabel(role);
        lblRole.setFont(UIHelper.FONT_SMALL_BOLD);
        lblRole.setForeground(UIHelper.COLOR_SECONDARY);
        lblRole.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Assembly
        card.add(lblAvatarImage);
        card.add(Box.createRigidArea(new Dimension(0, 14)));
        card.add(lblName);
        card.add(Box.createRigidArea(new Dimension(0, 6)));
        card.add(lblRole);

        return card;
    }

    private Icon createFallbackAvatarIcon(String initials, Color startGrad, Color endGrad) {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                GradientPaint gp = new GradientPaint(0, 0, startGrad, getIconWidth(), getIconHeight(), endGrad);
                g2.setPaint(gp);
                g2.fillOval(0, 0, getIconWidth(), getIconHeight());

                // White subtle border
                g2.setColor(new Color(255, 255, 255, 180));
                g2.setStroke(new BasicStroke(3));
                g2.drawOval(1, 1, getIconWidth() - 2, getIconHeight() - 2);

                // Initial Lettering
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 28));
                FontMetrics fm = g2.getFontMetrics();
                int tx = (getIconWidth() - fm.stringWidth(initials)) / 2;
                int ty = ((getIconHeight() - fm.getHeight()) / 2) + fm.getAscent();
                g2.drawString(initials, tx, ty);
                g2.dispose();
            }

            @Override
            public int getIconWidth() {
                return 80;
            }

            @Override
            public int getIconHeight() {
                return 80;
            }
        };
    }

    /**
     * Specialty Rounded JPanel that reacts to mouse hover events.
     */
    private static class HoverRoundedPanel extends JPanel {
        private final int cornerRadius;
        private Color backgroundColor;
        private Color borderColor;
        private final int borderThickness;

        public HoverRoundedPanel(int radius, Color bg, Color border, int thickness) {
            this.cornerRadius = radius;
            this.backgroundColor = bg;
            this.borderColor = border;
            this.borderThickness = thickness;
            setOpaque(false);

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    backgroundColor = new Color(241, 245, 249); // Muted slate hover highlight
                    borderColor = UIHelper.COLOR_PRIMARY;      // Accent blue border
                    repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    backgroundColor = bg;
                    borderColor = border;
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Draw rounded card background
            g2.setColor(backgroundColor != null ? backgroundColor : getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);

            // Draw border if requested
            if (borderColor != null && borderThickness > 0) {
                g2.setColor(borderColor);
                g2.setStroke(new BasicStroke(borderThickness));
                g2.drawRoundRect(borderThickness / 2, borderThickness / 2,
                        getWidth() - borderThickness, getHeight() - borderThickness,
                        cornerRadius, cornerRadius);
            }
            g2.dispose();
        }
    }
}
