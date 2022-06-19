package com.example.rental_test.model;

public class modelMotor {

    String id,namaKendaraan,hargaMotor;

    public modelMotor(){

    }

    public modelMotor(String id, String namaKendaraan, String hargaMotor) {
        this.id = id;
        this.namaKendaraan = namaKendaraan;
        this.hargaMotor = hargaMotor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNamaKendaraan() {
        return namaKendaraan;
    }

    public void setNamaKendaraan(String namaKendaraan) {
        this.namaKendaraan = namaKendaraan;
    }

    public String getHargaMotor() {
        return hargaMotor;
    }

    public void setHargaMotor(String hargaMotor) {
        this.hargaMotor = hargaMotor;
    }
}
