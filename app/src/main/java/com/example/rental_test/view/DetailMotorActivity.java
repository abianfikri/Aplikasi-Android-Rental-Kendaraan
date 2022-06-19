package com.example.rental_test.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rental_test.R;

public class DetailMotorActivity extends AppCompatActivity {

    private TextView namaMotor, hargaMotor;
    private ImageView imageView;
    private String sMerk, sHarga, sGambar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_motor);

        namaMotor = findViewById(R.id.JMotor);
        hargaMotor = findViewById(R.id.JHargaMotor);
        imageView = findViewById(R.id.ivMotor);

        Bundle bundle = getIntent().getExtras();

        sMerk = bundle.getString("namaMotor");
        sHarga = bundle.getString("hargaMotor");

        if(sMerk.equals("Vario")){
            sGambar = "vario";
        }
        else if(sMerk.equals("Beat")){
            sGambar = "beat";
        }
        else if(sMerk.equals("Harley")){
            sGambar = "harley";
        }
        else if(sMerk.equals("scoopy")){
            sGambar = "scoopy";
        }
        else if(sMerk.equals("Astrea")){
            sGambar = "astrea";
        }
        else if(sMerk.equals("Vespa")){
            sGambar = "vespa";
        }

        namaMotor.setText(sMerk);
        hargaMotor.setText(sHarga);
        imageView.setImageResource(getResources().getIdentifier(sGambar,"drawable", getPackageName()));
    }
}