-- Database Setup Script for Sistem Informasi Penjualan Tiket
CREATE DATABASE IF NOT EXISTS db_penjualan_tiket;
USE db_penjualan_tiket;

-- Table for User Authentication
CREATE TABLE IF NOT EXISTS user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    nama VARCHAR(100) NOT NULL
);

-- Table for Tickets (Master Data)
CREATE TABLE IF NOT EXISTS tiket (
    id_tiket INT AUTO_INCREMENT PRIMARY KEY,
    nama_tiket VARCHAR(100) NOT NULL,
    harga DOUBLE NOT NULL,
    stok_tiket INT NOT NULL
);

-- Table for Transactions
CREATE TABLE IF NOT EXISTS transaksi (
    id_transaksi INT AUTO_INCREMENT PRIMARY KEY,
    id_tiket INT NOT NULL,
    jumlah_beli INT NOT NULL,
    total_harga DOUBLE NOT NULL,
    tanggal_transaksi TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_tiket) REFERENCES tiket(id_tiket) ON DELETE CASCADE
);

-- Seed Initial Data
-- Insert user credentials
INSERT INTO user (username, password, nama) VALUES 
('admin', 'admin123', 'Administrator'),
('kasir', 'kasir123', 'Kasir Utama')
ON DUPLICATE KEY UPDATE id=id;

-- Insert initial tickets
INSERT INTO tiket (nama_tiket, harga, stok_tiket) VALUES
('Konser Coldplay Jakarta', 1500000, 50),
('Tiket Dufan Ancol', 250000, 100),
('Kereta Api Argo Bromo', 450000, 30),
('Bioskop IMAX XXI', 75000, 200)
ON DUPLICATE KEY UPDATE id_tiket=id_tiket;
