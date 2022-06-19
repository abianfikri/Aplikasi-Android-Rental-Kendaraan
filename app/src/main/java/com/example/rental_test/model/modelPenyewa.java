package com.example.rental_test.model;

public class modelPenyewa {

    private String id, nama, alamat, no_Tlp, sMerk;
    private String iPromo, iLama, iHarga;
    private String dTotal;

    public modelPenyewa() {
    }

    public modelPenyewa(String nama, String alamat, String no_Tlp, String sMerk, String iPromo, String iLama, String iHarga, String dTotal) {
        this.nama = nama;
        this.alamat = alamat;
        this.no_Tlp = no_Tlp;
        this.sMerk = sMerk;
        this.iPromo = iPromo;
        this.iLama = iLama;
        this.iHarga = iHarga;
        this.dTotal = dTotal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNo_Tlp() {
        return no_Tlp;
    }

    public void setNo_Tlp(String no_Tlp) {
        this.no_Tlp = no_Tlp;
    }

    public String getsMerk() {
        return sMerk;
    }

    public void setsMerk(String sMerk) {
        this.sMerk = sMerk;
    }

    public String getiPromo() {
        return iPromo;
    }

    public void setiPromo(String iPromo) {
        this.iPromo = iPromo;
    }

    public String getiLama() {
        return iLama;
    }

    public void setiLama(String iLama) {
        this.iLama = iLama;
    }

    public String getiHarga() {
        return iHarga;
    }

    public void setiHarga(String iHarga) {
        this.iHarga = iHarga;
    }

    public String getdTotal() {
        return dTotal;
    }

    public void setdTotal(String dTotal) {
        this.dTotal = dTotal;
    }
}