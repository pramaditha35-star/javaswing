package com.ticketapp.model;

public class Tiket {
    private int idTiket;
    private String namaTiket;
    private double harga;
    private int stokTiket;

    public Tiket() {}

    public Tiket(int idTiket, String namaTiket, double harga, int stokTiket) {
        this.idTiket = idTiket;
        this.namaTiket = namaTiket;
        this.harga = harga;
        this.stokTiket = stokTiket;
    }

    public int getIdTiket() {
        return idTiket;
    }

    public void setIdTiket(int idTiket) {
        this.idTiket = idTiket;
    }

    public String getNamaTiket() {
        return namaTiket;
    }

    public void setNamaTiket(String namaTiket) {
        this.namaTiket = namaTiket;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public int getStokTiket() {
        return stokTiket;
    }

    public void setStokTiket(int stokTiket) {
        this.stokTiket = stokTiket;
    }

    @Override
    public String toString() {
        return namaTiket + " (Stok: " + stokTiket + " - Rp" + String.format("%,.0f", harga) + ")";
    }
}
