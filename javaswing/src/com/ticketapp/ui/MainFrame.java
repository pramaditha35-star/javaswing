package com.ticketapp.ui;

import com.ticketapp.model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainFrame extends JFrame {
    // Session State
    private User currentUser = null;

    // Layout panels (Root CardLayout)
    private JPanel rootContentPanel;
    private CardLayout rootCardLayout;

    // Dashboard Content Panel (CardLayout)
    private JPanel mainContentPanel;
    private CardLayout cardLayout;
    
    private LoginPage loginPage;
    private MasterTiketPage masterTiketPage;
    private TransaksiPage transaksiPage;
    private AboutUsPage aboutUsPage;

    // Top Navigation Tabs
    private HeaderTabButton btnMaster;
    private HeaderTabButton btnTransaksi;
    private HeaderTabButton btnAbout;
    
    // User profile components (Top Header)
    private JLabel lblAvatar;
    private JLabel lblUserName;
    private JLabel lblUserUsername;

    public MainFrame() {
        setTitle("TICKETAPP - Sistem Informasi Penjualan Tiket");
        setSize(1150, 750);
        setMinimumSize(new Dimension(950, 680));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initContentPanels();
        
        // Initially logged out state
        onLogout();
    }

    private void initContentPanels() {
        rootCardLayout = new CardLayout();
        rootContentPanel = new JPanel(rootCardLayout);
        rootContentPanel.setBackground(UIHelper.COLOR_BACKGROUND);

        // Instantiate pages
        loginPage = new LoginPage(this);
        masterTiketPage = new MasterTiketPage();
        transaksiPage = new TransaksiPage();
        aboutUsPage = new AboutUsPage();

        // 1. Add Login to root
        rootContentPanel.add(loginPage, "LOGIN");

        // 2. Build Dashboard panel (Traveloka style: Top Bar + Content Area)
        JPanel dashboardPanel = new JPanel(new BorderLayout());
        dashboardPanel.setBackground(UIHelper.COLOR_BACKGROUND);

        // North: Top Navigation Header
        JPanel header = createHeaderPanel();
        dashboardPanel.add(header, BorderLayout.NORTH);

        // Center: Card layout for actual pages
        cardLayout = new CardLayout();
        mainContentPanel = new JPanel(cardLayout);
        mainContentPanel.setBackground(UIHelper.COLOR_BACKGROUND);

        mainContentPanel.add(masterTiketPage, "MASTER_TIKET");
        mainContentPanel.add(transaksiPage, "TRANSAKSI");
        mainContentPanel.add(aboutUsPage, "ABOUT_US");

        dashboardPanel.add(mainContentPanel, BorderLayout.CENTER);

        // Add Dashboard to root
        rootContentPanel.add(dashboardPanel, "DASHBOARD");

        add(rootContentPanel, BorderLayout.CENTER);
    }

    private JPanel createHeaderPanel() {
        // Traveloka Blue Solid Header Panel (64px height)
        JPanel header = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Draw solid Traveloka Blue background
                g2.setColor(UIHelper.COLOR_PRIMARY);
                g2.fillRect(0, 0, getWidth(), getHeight());
                // Draw a very subtle dark line at the bottom
                g2.setColor(UIHelper.COLOR_PRIMARY_DARK);
                g2.fillRect(0, getHeight() - 1, getWidth(), 1);
                g2.dispose();
            }
        };
        header.setPreferredSize(new Dimension(0, 64));
        header.setBorder(new EmptyBorder(0, 24, 0, 24));

        // LEFT SIDE: santep.com brand logo
        JPanel brandPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        brandPanel.setOpaque(false);
        
        // Dynamic geometric logo icon
        JLabel lblLogoIcon = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Draw active orange geometric logo square
                g2.setColor(UIHelper.COLOR_SECONDARY);
                g2.fillRoundRect(2, 2, 20, 20, 6, 6);
                
                // Draw white inner square accent
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(7, 7, 10, 10, 3, 3);
                g2.dispose();
            }
        };
        lblLogoIcon.setPreferredSize(new Dimension(24, 24));
        
        JLabel lblLogo = new JLabel("santep");
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 24));
        lblLogo.setForeground(Color.WHITE);
        
        JLabel lblLogoSub = new JLabel(".com");
        lblLogoSub.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 24));
        lblLogoSub.setForeground(UIHelper.COLOR_SECONDARY); // Orange dot com
        
        brandPanel.add(lblLogoIcon);
        brandPanel.add(lblLogo);
        brandPanel.add(lblLogoSub);
        header.add(brandPanel, BorderLayout.WEST);

        // CENTER: Horizontal Navigation Tabs
        JPanel tabsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        tabsPanel.setOpaque(false);

        btnMaster = new HeaderTabButton("Master Tiket");
        btnTransaksi = new HeaderTabButton("Transaksi");
        btnAbout = new HeaderTabButton("About Us");

        tabsPanel.add(btnMaster);
        tabsPanel.add(btnTransaksi);
        tabsPanel.add(btnAbout);
        header.add(tabsPanel, BorderLayout.CENTER);

        // Listeners for tabs
        btnMaster.addActionListener(e -> {
            masterTiketPage.loadData();
            showPage("MASTER_TIKET");
        });
        
        btnTransaksi.addActionListener(e -> {
            transaksiPage.loadTicketComboBox();
            transaksiPage.loadTransactionTable();
            showPage("TRANSAKSI");
        });

        btnAbout.addActionListener(e -> {
            showPage("ABOUT_US");
        });

        // RIGHT SIDE: Profile and Logout info
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        rightPanel.setOpaque(false);

        // User info texts
        JPanel userPanel = new JPanel(new GridLayout(2, 1, 0, 0));
        userPanel.setOpaque(false);
        
        lblUserName = new JLabel("Nama Kasir");
        lblUserName.setFont(UIHelper.FONT_BODY_BOLD);
        lblUserName.setForeground(Color.WHITE);
        lblUserName.setHorizontalAlignment(SwingConstants.RIGHT);
        
        lblUserUsername = new JLabel("@username");
        lblUserUsername.setFont(UIHelper.FONT_SMALL);
        lblUserUsername.setForeground(new Color(224, 242, 254)); // Soft light blue
        lblUserUsername.setHorizontalAlignment(SwingConstants.RIGHT);
        
        userPanel.add(lblUserName);
        userPanel.add(lblUserUsername);
        rightPanel.add(userPanel);

        // Avatar circle dynamically drawn in white/blue
        lblAvatar = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Circle background (White)
                g2.setColor(Color.WHITE);
                g2.fillOval(0, 0, getWidth(), getHeight());
                
                // Draw Initials in Traveloka Blue
                g2.setColor(UIHelper.COLOR_PRIMARY);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 14));
                String initials = getInitials(currentUser != null ? currentUser.getNama() : "U");
                FontMetrics fm = g2.getFontMetrics();
                int tx = (getWidth() - fm.stringWidth(initials)) / 2;
                int ty = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
                g2.drawString(initials, tx, ty);
                
                g2.dispose();
            }
        };
        lblAvatar.setPreferredSize(new Dimension(36, 36));
        lblAvatar.setMinimumSize(new Dimension(36, 36));
        lblAvatar.setMaximumSize(new Dimension(36, 36));
        rightPanel.add(lblAvatar);

        // Outline white Logout Button
        JButton btnLogout = new JButton("Keluar") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                ButtonModel model = getModel();
                if (model.isPressed()) {
                    g2.setColor(new Color(255, 255, 255, 200));
                } else if (model.isRollover()) {
                    g2.setColor(new Color(255, 255, 255, 40));
                } else {
                    g2.setColor(new Color(255, 255, 255, 0));
                }
                
                g2.setStroke(new BasicStroke(1.2f));
                g2.setColor(Color.WHITE);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);
                
                if (!model.isPressed() && model.isRollover()) {
                    g2.setColor(new Color(255, 255, 255, 20));
                    g2.fillRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 8, 8);
                }
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btnLogout.setFont(UIHelper.FONT_SMALL_BOLD);
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setContentAreaFilled(false);
        btnLogout.setFocusPainted(false);
        btnLogout.setBorderPainted(false);
        btnLogout.setOpaque(false);
        btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogout.setPreferredSize(new Dimension(75, 32));
        btnLogout.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, 
                    "Apakah Anda yakin ingin keluar?", 
                    "Konfirmasi Logout", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                onLogout();
            }
        });
        rightPanel.add(btnLogout);

        header.add(rightPanel, BorderLayout.EAST);

        // Align components in the header vertically
        // Swing vertical alignment inside BorderLayout works natively
        return header;
    }

    private String getInitials(String name) {
        if (name == null || name.trim().isEmpty()) return "U";
        String[] parts = name.trim().split("\\s+");
        if (parts.length >= 2) {
            return "" + parts[0].toUpperCase().charAt(0) + parts[1].toUpperCase().charAt(0);
        }
        return "" + parts[0].toUpperCase().substring(0, Math.min(2, parts[0].length()));
    }

    /**
     * Swaps display cards.
     */
    public void showPage(String pageName) {
        cardLayout.show(mainContentPanel, pageName);
        
        // Update header active button states
        btnMaster.setActive(pageName.equals("MASTER_TIKET"));
        btnTransaksi.setActive(pageName.equals("TRANSAKSI"));
        btnAbout.setActive(pageName.equals("ABOUT_US"));
    }

    /**
     * Handshake from Login panel on authentication success.
     */
    public void onLoginSuccess(User user) {
        this.currentUser = user;
        
        // Update profile data in header
        lblUserName.setText(user.getNama());
        lblUserUsername.setText("@" + user.getUsername());
        
        // Swap to dashboard shell
        rootCardLayout.show(rootContentPanel, "DASHBOARD");
        
        // Trigger page selection (Default: Master Tiket)
        masterTiketPage.loadData();
        showPage("MASTER_TIKET");
    }

    /**
     * Logs out the user and clears privileges.
     */
    public void onLogout() {
        this.currentUser = null;
        
        // Reset active states
        btnMaster.setActive(false);
        btnTransaksi.setActive(false);
        btnAbout.setActive(false);

        // Go back to login screen
        rootCardLayout.show(rootContentPanel, "LOGIN");
    }

    /**
     * Nested class representing an elegant navigation tab button for the top header.
     */
    private static class HeaderTabButton extends JButton {
        private boolean active = false;
        
        public HeaderTabButton(String text) {
            super(text);
            setFont(UIHelper.FONT_BODY_BOLD);
            setForeground(new Color(224, 242, 254)); // Soft light blue-white
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setOpaque(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setPreferredSize(new Dimension(130, 64)); // Align height with header
            setBorder(BorderFactory.createEmptyBorder(0, 16, 0, 16));
        }
        
        public void setActive(boolean active) {
            this.active = active;
            if (active) {
                setForeground(Color.WHITE);
            } else {
                setForeground(new Color(224, 242, 254));
            }
            repaint();
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            ButtonModel model = getModel();
            if (active) {
                // White underline indicator at the bottom
                g2.setColor(Color.WHITE);
                g2.fillRect(0, getHeight() - 4, getWidth(), 4);
            } else if (model.isRollover()) {
                // Soft rounded hover highlight
                g2.setColor(new Color(255, 255, 255, 25));
                g2.fillRoundRect(8, 12, getWidth() - 16, getHeight() - 24, 8, 8);
            }
            g2.dispose();
            super.paintComponent(g);
        }
    }
}
