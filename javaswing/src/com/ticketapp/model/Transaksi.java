package com.ticketapp.model;

import java.sql.Timestamp;

public class Transaksi {
    private int idTransaksi;
    private int idTiket;
    private int jumlahBeli;
    private double totalHarga;
    private Timestamp tanggalTransaksi;
    
    // Joined field for convenience in UI display
    private String namaTiket;

    public Transaksi() {}

    public Transaksi(int idTransaksi, int idTiket, int jumlahBeli, double totalHarga, Timestamp tanggalTransaksi) {
        this.idTransaksi = idTransaksi;
        this.idTiket = idTiket;
        this.jumlahBeli = jumlahBeli;
        this.totalHarga = totalHarga;
        this.tanggalTransaksi = tanggalTransaksi;
    }

    public int getIdTransaksi() {
        return idTransaksi;
    }

    public void setIdTransaksi(int idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    public int getIdTiket() {
        return idTiket;
    }

    public void setIdTiket(int idTiket) {
        this.idTiket = idTiket;
    }

    public int getJumlahBeli() {
        return jumlahBeli;
    }

    public void setJumlahBeli(int jumlahBeli) {
        this.jumlahBeli = jumlahBeli;
    }

    public double getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(double totalHarga) {
        this.totalHarga = totalHarga;
    }

    public Timestamp getTanggalTransaksi() {
        return tanggalTransaksi;
    }

    public void setTanggalTransaksi(Timestamp tanggalTransaksi) {
        this.tanggalTransaksi = tanggalTransaksi;
    }

    public String getNamaTiket() {
        return namaTiket;
    }

    public void setNamaTiket(String namaTiket) {
        this.namaTiket = namaTiket;
    }
}
