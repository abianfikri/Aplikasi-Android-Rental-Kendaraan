package com.example.rental_test.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.rental_test.R;
import com.google.firebase.firestore.FirebaseFirestore;

public class DetailPenyewaMobilActivity extends AppCompatActivity {

    private TextView nama,alamat,tlp, nama_kendaraan, harga, promo, lama, total;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_penyewa_mobil);

        nama = findViewById(R.id.HNama2);
        alamat = findViewById(R.id.HAlamat2);
        tlp = findViewById(R.id.HTelp2);
        nama_kendaraan = findViewById(R.id.HMerk2);
        harga = findViewById(R.id.HHarga2);
        promo = findViewById(R.id.HPromo2);
        lama = findViewById(R.id.HLamaSewa2);
        total = findViewById(R.id.HTotal2);

        progressDialog = new ProgressDialog(DetailPenyewaMobilActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Mengambil Data...");

        Intent intent = getIntent();

        if(intent != null){
            nama.setText(intent.getStringExtra("nama"));
            alamat.setText(intent.getStringExtra("alamat"));
            tlp.setText(intent.getStringExtra("no_tlp"));
            nama_kendaraan.setText(intent.getStringExtra("merek"));
            harga.setText("Rp " +intent.getStringExtra("hargaMobil"));
            lama.setText(intent.getStringExtra("lama") + " Hari");
            promo.setText(intent.getStringExtra("promo") + " %");
            total.setText("Rp " +intent.getStringExtra("total"));

        }
    }
}