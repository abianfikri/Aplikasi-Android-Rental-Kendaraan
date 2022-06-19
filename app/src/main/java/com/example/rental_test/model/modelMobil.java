package com.example.rental_test.model;

public class modelMobil {

    String id,nama_mobil;
    String hargaMobil;


    public modelMobil(String id, String nama_mobil, String hargaMobil) {
        this.id = id;
        this.nama_mobil = nama_mobil;
        this.hargaMobil = hargaMobil;
    }

    public modelMobil() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama_mobil() {
        return nama_mobil;
    }

    public void setNama_mobil(String nama_mobil) {
        this.nama_mobil = nama_mobil;
    }

    public String getHargaMobil() {
        return hargaMobil;
    }

    public void setHargaMobil(String hargaMobil) {
        this.hargaMobil = hargaMobil;
    }
}
