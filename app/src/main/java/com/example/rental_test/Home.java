package com.example.rental_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.rental_test.view.InfoPenyewaMobilActivity;
import com.example.rental_test.view.InfoPenyewaMotorActivity;

public class Home extends AppCompatActivity {

    Button InfoMobil, InfoMotor, sewaMotor,sewaMobil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        InfoMobil = findViewById(R.id.btnViewMobil);
        InfoMotor = findViewById(R.id.btnViewMotor);
        sewaMotor = findViewById(R.id.btnSewaMotor);
        sewaMobil = findViewById(R.id.btnSewaMobil);

         /*Info Mobil*/
        InfoMobil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data2 = new Intent(getApplicationContext(), infoMobil.class);
                startActivity(data2);
            }
        });

         /*Sewa Motor */
        sewaMotor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data3 = new Intent(getApplicationContext(), InfoPenyewaMotorActivity.class);
                startActivity(data3);
            }
        });

         /*Sewa Penyewa mobil */
        sewaMobil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data4 = new Intent(getApplicationContext(), InfoPenyewaMobilActivity.class);
                startActivity(data4);
            }
        });

         /*Infor Motor */
        InfoMotor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data1 = new Intent(getApplicationContext(), infoMotor.class);
                startActivity(data1);
            }
        });

    }
}