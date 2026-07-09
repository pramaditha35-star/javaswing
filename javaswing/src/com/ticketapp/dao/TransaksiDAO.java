package com.ticketapp.dao;

import com.ticketapp.config.DatabaseConnection;
import com.ticketapp.model.Transaksi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransaksiDAO {

    /**
     * Retrieve all transactions, joined with ticket names.
     */
    public List<Transaksi> getAll() {
        List<Transaksi> list = new ArrayList<>();
        String sql = "SELECT t.*, tk.nama_tiket FROM transaksi t " +
                     "JOIN tiket tk ON t.id_tiket = tk.id_tiket " +
                     "ORDER BY t.id_transaksi DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Transaksi trans = new Transaksi(
                    rs.getInt("id_transaksi"),
                    rs.getInt("id_tiket"),
                    rs.getInt("jumlah_beli"),
                    rs.getDouble("total_harga"),
                    rs.getTimestamp("tanggal_transaksi")
                );
                trans.setNamaTiket(rs.getString("nama_tiket"));
                list.add(trans);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching transactions: " + e.getMessage());
        }
        return list;
    }

    /**
     * Process a transaction with transaction isolation.
     * Reduces ticket stock and inserts the transaction record.
     * Returns true if successful, false if out of stock or error.
     */
    public boolean saveTransaction(Transaksi trans) {
        String checkStockSql = "SELECT stok_tiket FROM tiket WHERE id_tiket = ? FOR UPDATE";
        String reduceStockSql = "UPDATE tiket SET stok_tiket = stok_tiket - ? WHERE id_tiket = ?";
        String insertTransSql = "INSERT INTO transaksi (id_tiket, jumlah_beli, total_harga) VALUES (?, ?, ?)";
        
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); // Start transaction block
            
            // 1. Check stock
            int currentStock = 0;
            try (PreparedStatement checkStmt = conn.prepareStatement(checkStockSql)) {
                checkStmt.setInt(1, trans.getIdTiket());
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next()) {
                        currentStock = rs.getInt("stok_tiket");
                    } else {
                        // Ticket not found
                        conn.rollback();
                        return false;
                    }
                }
            }
            
            if (currentStock < trans.getJumlahBeli()) {
                // Out of stock
                conn.rollback();
                return false;
            }
            
            // 2. Reduce stock
            try (PreparedStatement reduceStmt = conn.prepareStatement(reduceStockSql)) {
                reduceStmt.setInt(1, trans.getJumlahBeli());
                reduceStmt.setInt(2, trans.getIdTiket());
                reduceStmt.executeUpdate();
            }
            
            // 3. Insert transaction
            try (PreparedStatement insertStmt = conn.prepareStatement(insertTransSql)) {
                insertStmt.setInt(1, trans.getIdTiket());
                insertStmt.setInt(2, trans.getJumlahBeli());
                insertStmt.setDouble(3, trans.getTotalHarga());
                insertStmt.executeUpdate();
            }
            
            conn.commit(); // Commit all operations
            return true;
            
        } catch (SQLException e) {
            System.err.println("Transaction process error: " + e.getMessage());
            if (conn != null) {
                try {
                    conn.rollback(); // Rollback on error
                } catch (SQLException ex) {
                    System.err.println("Rollback error: " + ex.getMessage());
                }
            }
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true); // Restore default
                } catch (SQLException e) {
                    System.err.println("Failed to restore autocommit: " + e.getMessage());
                }
            }
        }
    }
}
