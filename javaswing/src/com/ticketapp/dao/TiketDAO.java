package com.ticketapp.dao;

import com.ticketapp.config.DatabaseConnection;
import com.ticketapp.model.Tiket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TiketDAO {

    /**
     * Retrieve all tickets from database.
     */
    public List<Tiket> getAll() {
        List<Tiket> list = new ArrayList<>();
        String sql = "SELECT * FROM tiket ORDER BY id_tiket DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                list.add(new Tiket(
                    rs.getInt("id_tiket"),
                    rs.getString("nama_tiket"),
                    rs.getDouble("harga"),
                    rs.getInt("stok_tiket")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching tickets: " + e.getMessage());
        }
        return list;
    }

    /**
     * Retrieve a ticket by ID.
     */
    public Tiket getById(int idTiket) {
        String sql = "SELECT * FROM tiket WHERE id_tiket = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idTiket);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Tiket(
                        rs.getInt("id_tiket"),
                        rs.getString("nama_tiket"),
                        rs.getDouble("harga"),
                        rs.getInt("stok_tiket")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching ticket by ID: " + e.getMessage());
        }
        return null;
    }

    /**
     * Insert a new ticket.
     */
    public boolean insert(Tiket tiket) {
        String sql = "INSERT INTO tiket (nama_tiket, harga, stok_tiket) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, tiket.getNamaTiket());
            stmt.setDouble(2, tiket.getHarga());
            stmt.setInt(3, tiket.getStokTiket());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error inserting ticket: " + e.getMessage());
            return false;
        }
    }

    /**
     * Update an existing ticket's information.
     */
    public boolean update(Tiket tiket) {
        String sql = "UPDATE tiket SET nama_tiket = ?, harga = ?, stok_tiket = ? WHERE id_tiket = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, tiket.getNamaTiket());
            stmt.setDouble(2, tiket.getHarga());
            stmt.setInt(3, tiket.getStokTiket());
            stmt.setInt(4, tiket.getIdTiket());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating ticket: " + e.getMessage());
            return false;
        }
    }

    /**
     * Delete a ticket from the database.
     */
    public boolean delete(int idTiket) {
        String sql = "DELETE FROM tiket WHERE id_tiket = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idTiket);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting ticket: " + e.getMessage());
            return false;
        }
    }

    /**
     * Update ticket stock (used directly during transactions).
     */
    public boolean reduceStock(int idTiket, int quantityToReduce) {
        String sql = "UPDATE tiket SET stok_tiket = stok_tiket - ? WHERE id_tiket = ? AND stok_tiket >= ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, quantityToReduce);
            stmt.setInt(2, idTiket);
            stmt.setInt(3, quantityToReduce);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating ticket stock: " + e.getMessage());
            return false;
        }
    }
}
