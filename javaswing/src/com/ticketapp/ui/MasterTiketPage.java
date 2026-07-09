package com.ticketapp.ui;

import com.ticketapp.dao.TiketDAO;
import com.ticketapp.model.Tiket;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MasterTiketPage extends JPanel {
    private final TiketDAO tiketDAO;
    
    // Components
    private JTextField txtId;
    private JTextField txtNama;
    private JTextField txtHarga;
    private JTextField txtStok;
    
    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnClear;
    
    private JTable tblTiket;
    private DefaultTableModel tableModel;

    public MasterTiketPage() {
        this.tiketDAO = new TiketDAO();
        
        setLayout(new BorderLayout(15, 15));
        setBackground(UIHelper.COLOR_BACKGROUND);
        setBorder(new EmptyBorder(20, 20, 20, 20));
        
        initComponents();
        loadData();
    }

    private void initComponents() {
        // --- TITLE ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(null);
        headerPanel.setOpaque(false);
        
        JLabel lblTitle = new JLabel("Kelola Master Data Tiket");
        lblTitle.setFont(UIHelper.FONT_TITLE);
        lblTitle.setForeground(UIHelper.COLOR_TEXT_DARK);
        headerPanel.add(lblTitle, BorderLayout.WEST);
        
        JLabel lblSub = new JLabel("Tambah, ubah, dan hapus ketersediaan tiket konser atau wisata.");
        lblSub.setFont(UIHelper.FONT_SMALL_BOLD);
        lblSub.setForeground(UIHelper.COLOR_TEXT_LIGHT);
        headerPanel.add(lblSub, BorderLayout.SOUTH);
        
        add(headerPanel, BorderLayout.NORTH);

        // --- MAIN CONTENT SPLIT PANE ---
        // Left Panel: Form card wrapped in RoundedPanel
        UIHelper.RoundedPanel formPanel = new UIHelper.RoundedPanel(16, UIHelper.COLOR_CARD_BG, UIHelper.COLOR_BORDER, 1);
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        formPanel.setPreferredSize(new Dimension(350, 0));
        formPanel.setMaximumSize(new Dimension(350, Integer.MAX_VALUE));

        JLabel lblFormTitle = new JLabel("Form Data Tiket");
        lblFormTitle.setFont(UIHelper.FONT_SUBTITLE);
        lblFormTitle.setForeground(UIHelper.COLOR_PRIMARY);
        lblFormTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(lblFormTitle);
        formPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Fields
        txtId = new JTextField();
        txtId.setEditable(false);
        txtId.setBackground(new Color(241, 245, 249)); // read-only light gray background
        addFormField(formPanel, "ID Tiket (Otomatis)", txtId);
        
        txtNama = new UIHelper.PlaceholderTextField("Contoh: Tiket Dufan Ancol");
        addFormField(formPanel, "Nama Tiket", txtNama);
        
        txtHarga = new UIHelper.PlaceholderTextField("Contoh: 250000");
        addFormField(formPanel, "Harga Tiket (Rp)", txtHarga);
        
        txtStok = new UIHelper.PlaceholderTextField("Contoh: 100");
        addFormField(formPanel, "Jumlah Stok Tiket", txtStok);

        // Buttons Panel (2x2 Grid or FlowLayout) using custom RoundedButtons
        JPanel btnPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        btnPanel.setBackground(null);
        btnPanel.setOpaque(false);
        btnPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));

        btnAdd = new UIHelper.RoundedButton("Tambah", UIHelper.COLOR_SECONDARY, UIHelper.COLOR_SECONDARY_HOVER, 10);
        btnUpdate = new UIHelper.RoundedButton("Ubah", UIHelper.COLOR_PRIMARY, UIHelper.COLOR_PRIMARY_DARK, 10);
        btnDelete = new UIHelper.RoundedButton("Hapus", UIHelper.COLOR_DANGER, UIHelper.COLOR_DANGER_HOVER, 10);
        btnClear = new UIHelper.RoundedButton("Reset", new Color(148, 163, 184), new Color(100, 116, 139), 10);

        btnPanel.add(btnAdd);
        btnPanel.add(btnUpdate);
        btnPanel.add(btnDelete);
        btnPanel.add(btnClear);
        
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        formPanel.add(btnPanel);

        // Right Panel: Table card wrapped in RoundedPanel
        UIHelper.RoundedPanel tablePanel = new UIHelper.RoundedPanel(16, UIHelper.COLOR_CARD_BG, UIHelper.COLOR_BORDER, 1);
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        String[] columns = {"ID Tiket", "Nama Tiket", "Harga", "Stok"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tblTiket = new JTable(tableModel);
        UIHelper.styleTable(tblTiket);
        
        JScrollPane scrollPane = new JScrollPane(tblTiket);
        UIHelper.styleScrollPane(scrollPane);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Combine into a side-by-side solid column panel
        JPanel contentPanel = new JPanel(new BorderLayout(20, 0));
        contentPanel.setOpaque(false);
        contentPanel.add(formPanel, BorderLayout.WEST);
        contentPanel.add(tablePanel, BorderLayout.CENTER);
        
        add(contentPanel, BorderLayout.CENTER);

        // --- EVENT LISTENERS ---
        
        // Table selection changed
        tblTiket.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = tblTiket.getSelectedRow();
                    if (selectedRow != -1) {
                        txtId.setText(tableModel.getValueAt(selectedRow, 0).toString());
                        txtNama.setText(tableModel.getValueAt(selectedRow, 1).toString());
                        
                        // Parse price (remove formatting to edit)
                        String rawHarga = tableModel.getValueAt(selectedRow, 2).toString()
                                .replace("Rp", "")
                                .replace(".", "")
                                .trim();
                        txtHarga.setText(rawHarga);
                        txtStok.setText(tableModel.getValueAt(selectedRow, 3).toString());
                    }
                }
            }
        });

        // Add Button
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createTiket();
            }
        });

        // Update Button
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTiket();
            }
        });

        // Delete Button
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteTiket();
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

    private void addFormField(JPanel panel, String labelText, JTextField field) {
        JLabel label = new JLabel(labelText.toUpperCase());
        label.setFont(UIHelper.FONT_SMALL_BOLD);
        label.setForeground(UIHelper.COLOR_TEXT_LIGHT);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        UIHelper.styleTextField(field);
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(0, 6)));
        panel.add(field);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
    }

    public void loadData() {
        tableModel.setRowCount(0);
        List<Tiket> list = tiketDAO.getAll();
        for (Tiket t : list) {
            Object[] row = {
                t.getIdTiket(),
                t.getNamaTiket(),
                "Rp " + String.format("%,.0f", t.getHarga()),
                t.getStokTiket()
            };
            tableModel.addRow(row);
        }
    }

    private void createTiket() {
        String nama = txtNama.getText().trim();
        String hargaStr = txtHarga.getText().trim();
        String stokStr = txtStok.getText().trim();

        if (nama.isEmpty() || hargaStr.isEmpty() || stokStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua input harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            double harga = Double.parseDouble(hargaStr);
            int stok = Integer.parseInt(stokStr);

            if (harga < 0 || stok < 0) {
                JOptionPane.showMessageDialog(this, "Harga dan Stok harus bernilai positif!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Tiket tiket = new Tiket(0, nama, harga, stok);
            if (tiketDAO.insert(tiket)) {
                JOptionPane.showMessageDialog(this, "Tiket berhasil ditambahkan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menambahkan tiket ke database.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Harga dan Stok harus berupa angka valid!", "Format Salah", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTiket() {
        String idStr = txtId.getText().trim();
        if (idStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih tiket yang ingin diubah dari tabel terlebih dahulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nama = txtNama.getText().trim();
        String hargaStr = txtHarga.getText().trim();
        String stokStr = txtStok.getText().trim();

        if (nama.isEmpty() || hargaStr.isEmpty() || stokStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua input harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int id = Integer.parseInt(idStr);
            double harga = Double.parseDouble(hargaStr);
            int stok = Integer.parseInt(stokStr);

            if (harga < 0 || stok < 0) {
                JOptionPane.showMessageDialog(this, "Harga dan Stok harus bernilai positif!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Tiket tiket = new Tiket(id, nama, harga, stok);
            if (tiketDAO.update(tiket)) {
                JOptionPane.showMessageDialog(this, "Tiket berhasil diperbarui!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal memperbarui tiket ke database.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Harga dan Stok harus berupa angka valid!", "Format Salah", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteTiket() {
        String idStr = txtId.getText().trim();
        if (idStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih tiket yang ingin dihapus dari tabel terlebih dahulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, 
                "Apakah Anda yakin ingin menghapus tiket ini?", 
                "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int id = Integer.parseInt(idStr);
                if (tiketDAO.delete(id)) {
                    JOptionPane.showMessageDialog(this, "Tiket berhasil dihapus!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                    clearFields();
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(this, "Gagal menghapus tiket dari database.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                System.err.println("Error parsing ID during delete: " + e.getMessage());
            }
        }
    }

    private void clearFields() {
        txtId.setText("");
        txtNama.setText("");
        txtHarga.setText("");
        txtStok.setText("");
        tblTiket.clearSelection();
    }
}
