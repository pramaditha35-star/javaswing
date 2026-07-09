package com.ticketapp.ui;

import com.ticketapp.dao.UserDAO;
import com.ticketapp.model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginPage extends JPanel {
    private final MainFrame mainFrame;
    private final UserDAO userDAO;

    // Login Form fields
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JLabel lblError;

    // Register Form fields
    private JTextField txtRegNama;
    private JTextField txtRegUsername;
    private JPasswordField txtRegPassword;
    private JPasswordField txtRegConfirmPassword;
    private JButton btnRegDaftar;
    private JLabel lblRegError;

    // Outer card container
    private UIHelper.RoundedPanel cardPanel;
    private CardLayout formCardLayout;

    public LoginPage(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.userDAO = new UserDAO();
        
        setLayout(new GridBagLayout());
        initComponent();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Beautiful diagonal linear gradient
        GradientPaint gp = new GradientPaint(
                0, 0, UIHelper.COLOR_PRIMARY, 
                getWidth(), getHeight(), UIHelper.COLOR_SECONDARY
        );
        g2.setPaint(gp);
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.dispose();
    }

    public void initComponent() {
        removeAll();
        GridBagConstraints gbc = new GridBagConstraints();
        
        formCardLayout = new CardLayout();
        // Initialize rounded card panel with CardLayout
        cardPanel = new UIHelper.RoundedPanel(24, UIHelper.COLOR_CARD_BG, UIHelper.COLOR_BORDER, 1);
        cardPanel.setLayout(formCardLayout);
        cardPanel.setBorder(new EmptyBorder(40, 40, 40, 40));
        
        // Default size for Login view (centered elements look very neat here)
        cardPanel.setPreferredSize(new Dimension(400, 440));
        cardPanel.setMaximumSize(new Dimension(400, 440));

        // Create cards
        JPanel loginCard = createLoginCard();
        JPanel registerCard = createRegisterCard();

        cardPanel.add(loginCard, "LOGIN");
        cardPanel.add(registerCard, "REGISTER");

        // Center card in GridBagLayout
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        add(cardPanel, gbc);
    }

    private JPanel createLoginCard() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        // Title
        JLabel lblTitle = new JLabel("<html><font color='#0194F3'>santep</font><font color='#FF5E1F'>.com</font></html>");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 28));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(lblTitle);

        panel.add(Box.createRigidArea(new Dimension(0, 4)));

        // Subtitle
        JLabel lblSubtitle = new JLabel("Sistem Informasi Penjualan Tiket");
        lblSubtitle.setFont(UIHelper.FONT_SMALL_BOLD);
        lblSubtitle.setForeground(UIHelper.COLOR_TEXT_LIGHT);
        lblSubtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(lblSubtitle);

        panel.add(Box.createRigidArea(new Dimension(0, 35)));

        // Username Field (with placeholder text)
        txtUsername = new UIHelper.PlaceholderTextField("Masukkan Username");
        UIHelper.styleTextField(txtUsername);
        txtUsername.setMaximumSize(new Dimension(330, 42));
        txtUsername.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(txtUsername);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Password Field (with placeholder text)
        txtPassword = new UIHelper.PlaceholderPasswordField("Masukkan Password");
        UIHelper.stylePasswordField(txtPassword);
        txtPassword.setMaximumSize(new Dimension(330, 42));
        txtPassword.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(txtPassword);

        panel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Error message label
        lblError = new JLabel(" ");
        lblError.setFont(UIHelper.FONT_SMALL_BOLD);
        lblError.setForeground(UIHelper.COLOR_DANGER);
        lblError.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(lblError);

        panel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Login Button
        btnLogin = new UIHelper.RoundedButton("Masuk", UIHelper.COLOR_SECONDARY, UIHelper.COLOR_SECONDARY_HOVER, 10);
        btnLogin.setMaximumSize(new Dimension(330, 45));
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(btnLogin);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Link to Register
        JLabel lblSwitchToReg = new JLabel("<html>Belum punya akun? <font color='#0194F3'><b>Daftar Sekarang</b></font></html>");
        lblSwitchToReg.setFont(UIHelper.FONT_SMALL);
        lblSwitchToReg.setForeground(UIHelper.COLOR_TEXT_LIGHT);
        lblSwitchToReg.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblSwitchToReg.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        lblSwitchToReg.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showCard("REGISTER");
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                lblSwitchToReg.setText("<html>Belum punya akun? <u><font color='#0194F3'><b>Daftar Sekarang</b></font></u></html>");
            }
            @Override
            public void mouseExited(MouseEvent e) {
                lblSwitchToReg.setText("<html>Belum punya akun? <font color='#0194F3'><b>Daftar Sekarang</b></font></html>");
            }
        });
        panel.add(lblSwitchToReg);

        // Bind Enter key to action
        KeyAdapter enterKeyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    performLogin();
                }
            }
        };
        txtUsername.addKeyListener(enterKeyListener);
        txtPassword.addKeyListener(enterKeyListener);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performLogin();
            }
        });

        return panel;
    }

    private JPanel createRegisterCard() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        // Title
        JLabel lblTitle = new JLabel("<html><font color='#0194F3'>santep</font><font color='#FF5E1F'>.com</font></html>");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 28));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(lblTitle);

        panel.add(Box.createRigidArea(new Dimension(0, 4)));

        // Subtitle
        JLabel lblSubtitle = new JLabel("Daftar Akun Baru");
        lblSubtitle.setFont(UIHelper.FONT_SMALL_BOLD);
        lblSubtitle.setForeground(UIHelper.COLOR_TEXT_LIGHT);
        lblSubtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(lblSubtitle);

        panel.add(Box.createRigidArea(new Dimension(0, 25)));

        // Full Name Field (with placeholder text)
        txtRegNama = new UIHelper.PlaceholderTextField("Masukkan Nama Lengkap");
        UIHelper.styleTextField(txtRegNama);
        txtRegNama.setMaximumSize(new Dimension(330, 42));
        txtRegNama.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(txtRegNama);

        panel.add(Box.createRigidArea(new Dimension(0, 18)));

        // Username Field (with placeholder text)
        txtRegUsername = new UIHelper.PlaceholderTextField("Masukkan Username");
        UIHelper.styleTextField(txtRegUsername);
        txtRegUsername.setMaximumSize(new Dimension(330, 42));
        txtRegUsername.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(txtRegUsername);

        panel.add(Box.createRigidArea(new Dimension(0, 18)));

        // Password Field (with placeholder text)
        txtRegPassword = new UIHelper.PlaceholderPasswordField("Masukkan Password");
        UIHelper.stylePasswordField(txtRegPassword);
        txtRegPassword.setMaximumSize(new Dimension(330, 42));
        txtRegPassword.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(txtRegPassword);

        panel.add(Box.createRigidArea(new Dimension(0, 18)));

        // Confirm Password Field (with placeholder text)
        txtRegConfirmPassword = new UIHelper.PlaceholderPasswordField("Konfirmasi Password");
        UIHelper.stylePasswordField(txtRegConfirmPassword);
        txtRegConfirmPassword.setMaximumSize(new Dimension(330, 42));
        txtRegConfirmPassword.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(txtRegConfirmPassword);

        panel.add(Box.createRigidArea(new Dimension(0, 12)));

        // Error Label
        lblRegError = new JLabel(" ");
        lblRegError.setFont(UIHelper.FONT_SMALL_BOLD);
        lblRegError.setForeground(UIHelper.COLOR_DANGER);
        lblRegError.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(lblRegError);

        panel.add(Box.createRigidArea(new Dimension(0, 12)));

        // Register Button
        btnRegDaftar = new UIHelper.RoundedButton("Daftar Akun Baru", UIHelper.COLOR_SECONDARY, UIHelper.COLOR_SECONDARY_HOVER, 10);
        btnRegDaftar.setMaximumSize(new Dimension(330, 45));
        btnRegDaftar.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(btnRegDaftar);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Link to Login
        JLabel lblSwitchToLogin = new JLabel("<html>Sudah punya akun? <font color='#0194F3'><b>Masuk</b></font></html>");
        lblSwitchToLogin.setFont(UIHelper.FONT_SMALL);
        lblSwitchToLogin.setForeground(UIHelper.COLOR_TEXT_LIGHT);
        lblSwitchToLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblSwitchToLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        lblSwitchToLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showCard("LOGIN");
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                lblSwitchToLogin.setText("<html>Sudah punya akun? <u><font color='#0194F3'><b>Masuk</b></font></u></html>");
            }
            @Override
            public void mouseExited(MouseEvent e) {
                lblSwitchToLogin.setText("<html>Sudah punya akun? <font color='#0194F3'><b>Masuk</b></font></html>");
            }
        });
        panel.add(lblSwitchToLogin);

        // Bind Enter Key to Register Action
        KeyAdapter regEnterListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    performRegister();
                }
            }
        };
        txtRegNama.addKeyListener(regEnterListener);
        txtRegUsername.addKeyListener(regEnterListener);
        txtRegPassword.addKeyListener(regEnterListener);
        txtRegConfirmPassword.addKeyListener(regEnterListener);

        btnRegDaftar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performRegister();
            }
        });

        return panel;
    }

    private void showCard(String cardName) {
        formCardLayout.show(cardPanel, cardName);
        boolean isRegister = cardName.equals("REGISTER");
        // Login card needs less height now since labels are removed, register card fits nicely in 560
        cardPanel.setPreferredSize(new Dimension(400, isRegister ? 560 : 440));
        cardPanel.setMaximumSize(new Dimension(400, isRegister ? 560 : 440));
        
        // Reset warnings
        lblError.setText(" ");
        lblRegError.setText(" ");
        
        cardPanel.revalidate();
        cardPanel.repaint();
    }

    private void performLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            lblError.setText("Username dan password tidak boleh kosong!");
            return;
        }

        // Validate credentials against DB
        User loggedInUser = userDAO.login(username, password);
        if (loggedInUser != null) {
            lblError.setText(" ");
            // Clear inputs
            txtUsername.setText("");
            txtPassword.setText("");
            
            // Trigger login success on MainFrame
            mainFrame.onLoginSuccess(loggedInUser);
        } else {
            lblError.setText("Username atau password salah!");
        }
    }

    private void performRegister() {
        String nama = txtRegNama.getText().trim();
        String username = txtRegUsername.getText().trim();
        String password = new String(txtRegPassword.getPassword()).trim();
        String confirm = new String(txtRegConfirmPassword.getPassword()).trim();

        if (nama.isEmpty() || username.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
            lblRegError.setText("Semua kolom registrasi harus diisi!");
            return;
        }

        if (username.length() < 3) {
            lblRegError.setText("Username minimal 3 karakter!");
            return;
        }

        if (password.length() < 4) {
            lblRegError.setText("Password minimal 4 karakter!");
            return;
        }

        if (!password.equals(confirm)) {
            lblRegError.setText("Konfirmasi password tidak cocok!");
            return;
        }

        if (userDAO.isUsernameExists(username)) {
            lblRegError.setText("Username '" + username + "' sudah digunakan!");
            return;
        }

        // Save new user
        User newUser = new User(0, username, password, nama);
        if (userDAO.register(newUser)) {
            JOptionPane.showMessageDialog(this, 
                    "Pendaftaran akun baru berhasil! Silakan masuk.", 
                    "Registrasi Sukses", JOptionPane.INFORMATION_MESSAGE);
            
            // Switch back to Login card
            showCard("LOGIN");
            
            // Pre-populate username field for UX convenience
            txtUsername.setText(username);
            txtPassword.setText("");
            txtPassword.requestFocus();
            
            // Clear register form inputs
            txtRegNama.setText("");
            txtRegUsername.setText("");
            txtRegPassword.setText("");
            txtRegConfirmPassword.setText("");
        } else {
            lblRegError.setText("Gagal mendaftarkan akun ke database.");
        }
    }
}
