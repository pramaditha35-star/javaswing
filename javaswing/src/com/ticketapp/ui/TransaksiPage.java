package com.ticketapp.ui;

import com.ticketapp.dao.TiketDAO;
import com.ticketapp.dao.TransaksiDAO;
import com.ticketapp.model.Tiket;
import com.ticketapp.model.Transaksi;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

public class TransaksiPage extends JPanel {
    private final TiketDAO tiketDAO;
    private final TransaksiDAO transaksiDAO;
    
    // Components
    private JComboBox<Tiket> cbTiket;
    private JLabel lblHargaSatuan;
    private JLabel lblStokTersedia;
    private JTextField txtJumlahBeli;
    private JTextField txtTotalHarga;
    
    private JButton btnBeli;
    private JButton btnClear;
    
    private JTable tblTransaksi;
    private DefaultTableModel tableModel;

    public TransaksiPage() {
        this.tiketDAO = new TiketDAO();
        this.transaksiDAO = new TransaksiDAO();
        
        setLayout(new BorderLayout(15, 15));
        setBackground(UIHelper.COLOR_BACKGROUND);
        setBorder(new EmptyBorder(20, 20, 20, 20));
        
        initComponents();
        loadTicketComboBox();
        loadTransactionTable();
    }

    private void initComponents() {
        // --- TITLE ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(null);
        headerPanel.setOpaque(false);
        
        JLabel lblTitle = new JLabel("Transaksi Penjualan Tiket");
        lblTitle.setFont(UIHelper.FONT_TITLE);
        lblTitle.setForeground(UIHelper.COLOR_TEXT_DARK);
        headerPanel.add(lblTitle, BorderLayout.WEST);
        
        JLabel lblSub = new JLabel("Pencatatan penjualan tiket dengan pengurangan stok otomatis.");
        lblSub.setFont(UIHelper.FONT_SMALL_BOLD);
        lblSub.setForeground(UIHelper.COLOR_TEXT_LIGHT);
        headerPanel.add(lblSub, BorderLayout.SOUTH);
        
        add(headerPanel, BorderLayout.NORTH);

        // --- MAIN CONTENT ---
        // Left Panel: Transaction Form wrapped in RoundedPanel
        UIHelper.RoundedPanel formPanel = new UIHelper.RoundedPanel(16, UIHelper.COLOR_CARD_BG, UIHelper.COLOR_BORDER, 1);
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        formPanel.setPreferredSize(new Dimension(380, 0));
        formPanel.setMaximumSize(new Dimension(380, Integer.MAX_VALUE));

        JLabel lblFormTitle = new JLabel("Form Transaksi");
        lblFormTitle.setFont(UIHelper.FONT_SUBTITLE);
        lblFormTitle.setForeground(UIHelper.COLOR_PRIMARY);
        lblFormTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(lblFormTitle);
        formPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Select Ticket Combobox
        JLabel lblChoose = new JLabel("PILIH TIKET");
        lblChoose.setFont(UIHelper.FONT_SMALL_BOLD);
        lblChoose.setForeground(UIHelper.COLOR_TEXT_LIGHT);
        lblChoose.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(lblChoose);
        formPanel.add(Box.createRigidArea(new Dimension(0, 6)));

        cbTiket = new JComboBox<>();
        UIHelper.styleComboBox(cbTiket);
        cbTiket.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        cbTiket.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(cbTiket);
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Dynamic Display Panel for Selected Ticket Info (Rounded card)
        UIHelper.RoundedPanel infoPanel = new UIHelper.RoundedPanel(12, new Color(240, 246, 255), UIHelper.COLOR_BORDER, 1);
        infoPanel.setLayout(new GridLayout(2, 2, 5, 8));
        infoPanel.setBorder(new EmptyBorder(12, 15, 12, 15));
        infoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 75));

        JLabel lblHrgText = new JLabel("Harga Satuan:");
        lblHrgText.setFont(UIHelper.FONT_SMALL_BOLD);
        lblHrgText.setForeground(UIHelper.COLOR_TEXT_LIGHT);
        lblHargaSatuan = new JLabel("Rp 0");
        lblHargaSatuan.setFont(UIHelper.FONT_BODY_BOLD);
        lblHargaSatuan.setForeground(UIHelper.COLOR_PRIMARY);

        JLabel lblStkText = new JLabel("Stok Tersedia:");
        lblStkText.setFont(UIHelper.FONT_SMALL_BOLD);
        lblStkText.setForeground(UIHelper.COLOR_TEXT_LIGHT);
        lblStokTersedia = new JLabel("0");
        lblStokTersedia.setFont(UIHelper.FONT_BODY_BOLD);
        lblStokTersedia.setForeground(UIHelper.COLOR_SUCCESS);

        infoPanel.add(lblHrgText);
        infoPanel.add(lblHargaSatuan);
        infoPanel.add(lblStkText);
        infoPanel.add(lblStokTersedia);
        
        formPanel.add(infoPanel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Quantity (Jumlah Beli)
        JLabel lblQty = new JLabel("JUMLAH PEMBELIAN");
        lblQty.setFont(UIHelper.FONT_SMALL_BOLD);
        lblQty.setForeground(UIHelper.COLOR_TEXT_LIGHT);
        lblQty.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(lblQty);
        formPanel.add(Box.createRigidArea(new Dimension(0, 6)));

        txtJumlahBeli = new UIHelper.PlaceholderTextField("Contoh: 2");
        UIHelper.styleTextField(txtJumlahBeli);
        txtJumlahBeli.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtJumlahBeli.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        formPanel.add(txtJumlahBeli);
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Total Price (Total Harga)
        JLabel lblTotal = new JLabel("TOTAL BAYAR");
        lblTotal.setFont(UIHelper.FONT_SMALL_BOLD);
        lblTotal.setForeground(UIHelper.COLOR_TEXT_LIGHT);
        lblTotal.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(lblTotal);
        formPanel.add(Box.createRigidArea(new Dimension(0, 6)));

        txtTotalHarga = new JTextField();
        txtTotalHarga.setEditable(false);
        txtTotalHarga.setBackground(new Color(241, 245, 249)); // read only gray
        UIHelper.styleTextField(txtTotalHarga);
        txtTotalHarga.setFont(new Font("Segoe UI", Font.BOLD, 18));
        txtTotalHarga.setForeground(UIHelper.COLOR_PRIMARY);
        txtTotalHarga.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtTotalHarga.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        formPanel.add(txtTotalHarga);
        formPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        // Buttons using RoundedButtons
        JPanel btnPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        btnPanel.setBackground(null);
        btnPanel.setOpaque(false);
        btnPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));

        btnBeli = new UIHelper.RoundedButton("Proses Transaksi", UIHelper.COLOR_SECONDARY, UIHelper.COLOR_SECONDARY_HOVER, 10);
        btnClear = new UIHelper.RoundedButton("Reset", new Color(148, 163, 184), new Color(100, 116, 139), 10);

        btnPanel.add(btnBeli);
        btnPanel.add(btnClear);
        formPanel.add(btnPanel);

        // Right Panel: Transaction History List wrapped in RoundedPanel
        UIHelper.RoundedPanel historyPanel = new UIHelper.RoundedPanel(16, UIHelper.COLOR_CARD_BG, UIHelper.COLOR_BORDER, 1);
        historyPanel.setLayout(new BorderLayout(10, 10));
        historyPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel lblHistTitle = new JLabel("Riwayat Transaksi Penjualan");
        lblHistTitle.setFont(UIHelper.FONT_SUBTITLE);
        lblHistTitle.setForeground(UIHelper.COLOR_PRIMARY);
        historyPanel.add(lblHistTitle, BorderLayout.NORTH);

        String[] columns = {"ID Transaksi", "Nama Tiket", "Jumlah Beli", "Total Bayar", "Waktu Transaksi"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tblTransaksi = new JTable(tableModel);
        UIHelper.styleTable(tblTransaksi);
        
        JScrollPane scrollPane = new JScrollPane(tblTransaksi);
        UIHelper.styleScrollPane(scrollPane);
        historyPanel.add(scrollPane, BorderLayout.CENTER);

        // Combine into a side-by-side solid column panel
        JPanel contentPanel = new JPanel(new BorderLayout(20, 0));
        contentPanel.setOpaque(false);
        contentPanel.add(formPanel, BorderLayout.WEST);
        contentPanel.add(historyPanel, BorderLayout.CENTER);
        
        add(contentPanel, BorderLayout.CENTER);

        // --- EVENT LISTENERS ---

        // Dropdown selection listener
        cbTiket.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateSelectedTicketInfo();
            }
        });

        // Quantity text field input change listener to auto-calculate total price
        txtJumlahBeli.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                recalculateTotal();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                recalculateTotal();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                recalculateTotal();
            }
        });

        // Save Transaction Button
        btnBeli.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processTransaction();
            }
        });

        // Clear Button
        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });
    }

    /**
     * Loads tickets into the JComboBox for purchase selection.
     */
    public void loadTicketComboBox() {
        // Stop action listener during population to prevent errors
        ActionListener[] listeners = cbTiket.getActionListeners();
        for (ActionListener l : listeners) {
            cbTiket.removeActionListener(l);
        }
        
        cbTiket.removeAllItems();
        List<Tiket> list = tiketDAO.getAll();
        for (Tiket t : list) {
            cbTiket.addItem(t);
        }
        
        // Restore action listeners
        for (ActionListener l : listeners) {
            cbTiket.addActionListener(l);
        }
        
        updateSelectedTicketInfo();
    }

    /**
     * Refreshes ticket details like price and stock fields.
     */
    private void updateSelectedTicketInfo() {
        Tiket selected = (Tiket) cbTiket.getSelectedItem();
        if (selected != null) {
            lblHargaSatuan.setText("Rp " + String.format("%,.0f", selected.getHarga()));
            lblStokTersedia.setText(String.valueOf(selected.getStokTiket()));
            
            // Adjust stock label color based on stock size
            if (selected.getStokTiket() <= 5) {
                lblStokTersedia.setForeground(UIHelper.COLOR_DANGER);
            } else if (selected.getStokTiket() <= 15) {
                lblStokTersedia.setForeground(UIHelper.COLOR_WARNING);
            } else {
                lblStokTersedia.setForeground(UIHelper.COLOR_SUCCESS);
            }
            
            recalculateTotal();
        } else {
            lblHargaSatuan.setText("Rp 0");
            lblStokTersedia.setText("0");
            lblStokTersedia.setForeground(UIHelper.COLOR_TEXT_LIGHT);
            txtTotalHarga.setText("");
        }
    }

    /**
     * Calculates the total price based on selected ticket and quantity.
     */
    private void recalculateTotal() {
        Tiket selected = (Tiket) cbTiket.getSelectedItem();
        if (selected == null) {
            txtTotalHarga.setText("");
            return;
        }

        String qtyStr = txtJumlahBeli.getText().trim();
        if (qtyStr.isEmpty()) {
            txtTotalHarga.setText("");
            return;
        }

        try {
            int qty = Integer.parseInt(qtyStr);
            if (qty > 0) {
                double total = selected.getHarga() * qty;
                txtTotalHarga.setText("Rp " + String.format("%,.0f", total));
            } else {
                txtTotalHarga.setText("Rp 0");
            }
        } catch (NumberFormatException e) {
            txtTotalHarga.setText("Format Salah");
        }
    }

    /**
     * Loads/reloads transaction history in table.
     */
    public void loadTransactionTable() {
        tableModel.setRowCount(0);
        List<Transaksi> list = transaksiDAO.getAll();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        for (Transaksi t : list) {
            Object[] row = {
                t.getIdTransaksi(),
                t.getNamaTiket(),
                t.getJumlahBeli(),
                "Rp " + String.format("%,.0f", t.getTotalHarga()),
                dateFormat.format(t.getTanggalTransaksi())
            };
            tableModel.addRow(row);
        }
    }

    private void processTransaction() {
        Tiket selected = (Tiket) cbTiket.getSelectedItem();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Silakan pilih tiket terlebih dahulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String qtyStr = txtJumlahBeli.getText().trim();
        if (qtyStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Silakan isi jumlah pembelian!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int qty = Integer.parseInt(qtyStr);
            if (qty <= 0) {
                JOptionPane.showMessageDialog(this, "Jumlah pembelian harus lebih dari 0!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Verification 1: Check stock locally first
            if (qty > selected.getStokTiket()) {
                JOptionPane.showMessageDialog(this, 
                        "Stok tiket tidak mencukupi!\n" +
                        "Stok tersedia: " + selected.getStokTiket() + "\n" +
                        "Jumlah pembelian: " + qty, 
                        "Peringatan Stok Kurang", 
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Save transaction to DB
            double totalHarga = selected.getHarga() * qty;
            Transaksi trans = new Transaksi(0, selected.getIdTiket(), qty, totalHarga, new Timestamp(System.currentTimeMillis()));
            
            if (transaksiDAO.saveTransaction(trans)) {
                JOptionPane.showMessageDialog(this, "Transaksi berhasil diproses!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
                // Reload transaction log
                loadTransactionTable();
                // Reload ticket combos to update current stock display
                loadTicketComboBox();
            } else {
                JOptionPane.showMessageDialog(this, 
                        "Gagal memproses transaksi. Periksa kembali stok tiket Anda.", 
                        "Transaksi Gagal", 
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Jumlah pembelian harus berupa angka bulat valid!", "Format Salah", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        txtJumlahBeli.setText("");
        txtTotalHarga.setText("");
        if (cbTiket.getItemCount() > 0) {
            cbTiket.setSelectedIndex(0);
        }
    }
}
