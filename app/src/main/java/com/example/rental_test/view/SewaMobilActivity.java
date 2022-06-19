package com.example.rental_test.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.rental_test.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class SewaMobilActivity extends AppCompatActivity {
    /* *
       Membuat variable yang akan di pakai
    * */

    /*Bagian Identitas Penyewa*/
    // Edit Text Identitas Penyewa
    private EditText nPenyewa,alamatPenyewa,no_tlp;

    /* Bagian Data Mobil*/
    // Spinner
    private Spinner spinner;
    // Radio Button, Radio grup
    private RadioGroup promo;
    private RadioButton weekday,weekend;
    // Edit Text Data Mobil
    private EditText waktu;

    // Button
    private Button selesai;

    /*Bagian FireBase*/
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ProgressDialog progressDialog;
    private String id ="";

    /*Bagian Spinner*/
    private static String URL_SELECT = "https://20200140130.praktikumtiumy.com/TugasUAS/bacaMobil.php";
    private static final String TAG_nama_Mobil = "nama_mobil";

    ArrayList<String> list = new ArrayList<>();
    ArrayAdapter<String> adapter;
    RequestQueue requestQueue;

    /*Bagian Hitung*/
    private String sMerk, sLama;
    private double dPromo;
    private int iLama, iPromo, iHarga;
    private double dTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sewa_mobil);

        nPenyewa = findViewById(R.id.eTNama);
        alamatPenyewa = findViewById(R.id.eTAlamat);
        no_tlp = findViewById(R.id.eTHP);
        spinner = findViewById(R.id.spinner);
        promo = findViewById(R.id.promoGroup);
        weekday = findViewById(R.id.rbWeekDay);
        weekend = findViewById(R.id.rbWeekEnd);
        waktu = findViewById(R.id.eTLamaSewa);
        
        selesai = findViewById(R.id.selesaiHitung);
        
        /*Bagian Spinner*/
        loadSpinner();

        progressDialog = new ProgressDialog(SewaMobilActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Menyimpan...");

        selesai.setOnClickListener(v->{

            sLama = waktu.getText().toString();

            if(nPenyewa.getText().length() > 0  && alamatPenyewa.getText().length() > 0 && no_tlp.getText().length() > 0 && waktu.getText().length() > 0){

                if(weekday.isChecked()){
                    dPromo = 0.1;
                }
                else if(weekend.isChecked()){
                    dPromo = 0.25;
                }

                if (sMerk.equals("Avanza")){
                    iHarga = 150000;
                }
                else if(sMerk.equals("Xenia")){
                    iHarga = 125000;
                }
                else if(sMerk.equals("Alphard")){
                    iHarga = 1500000;
                }
                else if(sMerk.equals("Pajero")){
                    iHarga = 500000;
                }
                else if(sMerk.equals("Innova")){
                    iHarga = 350000;
                }
                else if(sMerk.equals("Bus")){
                    iHarga = 2500000;
                }

                String price = String.valueOf(iHarga);

                iLama = Integer.parseInt(sLama);
                String time = String.valueOf(iLama);
                iPromo = (int) (dPromo * 100);
                String diskon = String.valueOf(iPromo);
                dTotal = (iHarga * iLama) - (iHarga * iLama * dPromo);
                String Akhir = String.valueOf(dTotal);

                // Memanggil Method Save data
                saveData(nPenyewa.getText().toString(), alamatPenyewa.getText().toString(), no_tlp.getText().toString(),sMerk,diskon,time,Akhir,price);
            }
            else {
                Toast.makeText(this, "Silahkan Isi Semua data!!!", Toast.LENGTH_SHORT).show();
            }


        });

        // Mendapatkan data dari info penyewa update
        Intent intent = getIntent();
        if(intent != null){
            id = intent.getStringExtra("id");
            nPenyewa.setText(intent.getStringExtra("nama"));
            alamatPenyewa.setText(intent.getStringExtra("alamat"));
            no_tlp.setText(intent.getStringExtra("no_tlp"));
            waktu.setText(intent.getStringExtra("lama"));
        }
    }

    private void loadSpinner() {

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL_SELECT, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        String data = jsonObject.optString(TAG_nama_Mobil);

                        list.add(data);
                        adapter = new ArrayAdapter<>(SewaMobilActivity.this, android.R.layout.simple_spinner_item, list);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(adapter);
                    }
                    catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                sMerk = adapterView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void saveData(String nama, String alamat, String tlp, String sMerk, String diskon, String time, String akhir, String price) {
        Map<String,Object> penyewa = new HashMap<>();

        penyewa.put("nama", nama);
        penyewa.put("alamat", alamat);
        penyewa.put("no_tlp", tlp);
        penyewa.put("merek", sMerk);
        penyewa.put("hargaMobil", price );
        penyewa.put("lama", diskon);
        penyewa.put("promo", time);
        penyewa.put("total", akhir);

        progressDialog.show();

        if (id != null){
            /**
             *  kode untuk edit data firestore dengan mengambil id
             */
            db.collection("penyewa").document(id)
                    .set(penyewa)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(SewaMobilActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();
                                finish();
                            }else {
                                Toast.makeText(SewaMobilActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }else {
            /**
             * Code untuk menambahkan data dengan add
             */
            db.collection("penyewa")
                    .add(penyewa)
                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            Toast.makeText(SewaMobilActivity.this, "Berhasil di simpan", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SewaMobilActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
        }
    }

    /* Bagian Create Data*/

    /* Akhir Bagian Create Data*/
}