package com.example.rental_test.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rental_test.R;

public class DetailMobilActivity extends AppCompatActivity {

    private TextView namaMobil,hargaMobil;
    private ImageView imageView;
    private String Merk;
    private String sHarga,sGambar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_mobil);

        namaMobil = findViewById(R.id.JMobil);
        hargaMobil = findViewById(R.id.JHarga);
        imageView = findViewById(R.id.ivMobil);

        Bundle bundle = getIntent().getExtras();

        Merk = bundle.getString("data1");
        sHarga = bundle.getString("data2");

        if (Merk.equals("Avanza")){
            sGambar = "avanza";
        }
        else if(Merk.equals("Xenia")){
            sGambar = "xenia";
        }
        else if(Merk.equals("Alphard")){
            sGambar = "alphard";
        }else if(Merk.equals("Pajero")){
            sGambar = "pajero";
        }else if(Merk.equals("Innova")){
            sGambar = "innova";
        }else if(Merk.equals("Bus")){
            sGambar = "bus";
        }

        namaMobil.setText(Merk);
        hargaMobil.setText(sHarga);
        imageView.setImageResource(getResources().getIdentifier(sGambar,"drawable", getPackageName()));


    }
}