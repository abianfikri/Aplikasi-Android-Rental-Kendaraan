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
import com.example.rental_test.model.modelMobil;
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

public class SewaMotorActivity extends AppCompatActivity {

    /*Mendeklarasikan Variable*/
    private EditText nPenyewa,alamatPenyewa,no_tlp;
    private Spinner spinner;
    private RadioGroup radioGroup;
    private RadioButton weekend,weekday;
    private EditText waktuSewa;
    private Button selesai;

    /*Bagian Deklarasi Firebase Firestore*/
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ProgressDialog progressDialog;
    private String id = "";

    /*Bagian Spinner variable*/
    private static String URL_SPINNER = "https://20200140130.praktikumtiumy.com/TugasUAS/bacaMotor.php";
    private static final String TAG_nama_Motor = "nama_kendaraan";
    private ArrayList<String> list = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    RequestQueue requestQueue;

    /*Bagian Variable Hitung*/
    private String sMerk, sLama;
    private int iLama, iPromo, iHarga;
    private double dTotal, dPromo;

    /*Bagian Convert numerik to String*/
    private String time,diskon,price,totalAkhir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sewa_motor);

        /*Inisialisasi Variable*/
        nPenyewa = findViewById(R.id.eTNama);
        alamatPenyewa = findViewById(R.id.eTAlamat);
        no_tlp = findViewById(R.id.eTHP);
        spinner = findViewById(R.id.spinner);
        radioGroup = findViewById(R.id.promoGroup);
        weekday = findViewById(R.id.rbWeekDay);
        weekend = findViewById(R.id.rbWeekEnd);
        waktuSewa = findViewById(R.id.eTLamaSewa);
        selesai = findViewById(R.id.selesaiHitung);

        progressDialog = new ProgressDialog(SewaMotorActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Menyimpan...");

        loadSpinner();

        /*Start Btn Selesai*/
        selesai.setOnClickListener(v->{

            sLama = waktuSewa.getText().toString();

            if(nPenyewa.getText().length() > 0  && alamatPenyewa.getText().length() > 0 && no_tlp.getText().length() > 0 && waktuSewa.getText().length() > 0) {

                if (weekday.isChecked()) {
                    dPromo = 0.1;
                } else if (weekend.isChecked()) {
                    dPromo = 0.25;
                }

                if (sMerk.equals("Vario")) {
                    iHarga = 80000;
                } else if (sMerk.equals("Beat")) {
                    iHarga = 50000;
                } else if (sMerk.equals("Harley")) {
                    iHarga = 500000;
                } else if (sMerk.equals("scoopy")) {
                    iHarga = 115000;
                } else if (sMerk.equals("Astrea")) {
                    iHarga = 90000;
                } else if (sMerk.equals("Vespa")) {
                    iHarga = 250000;
                }

                iLama = Integer.parseInt(sLama);
                iPromo = (int) (dPromo * 100);
                dTotal = (iHarga * iLama) - (iHarga * iLama * dPromo);

                // Convert Data
                price = String.valueOf(iHarga);
                time = String.valueOf(iLama);
                diskon = String.valueOf(iPromo);
                totalAkhir = String.valueOf(dTotal);

                // Method CreatDatabase
                saveData(nPenyewa.getText().toString(), alamatPenyewa.getText().toString(), no_tlp.getText().toString(),sMerk,diskon,time,totalAkhir,price);
            }
            else {
                Toast.makeText(this, "Silahkan Isi Semua Data", Toast.LENGTH_SHORT).show();
            }
        });
        // Mendapatkan data dari info penyewa update
        Intent intent = getIntent();
        if(intent != null){
            id = intent.getStringExtra("id");
            nPenyewa.setText(intent.getStringExtra("nama"));
            alamatPenyewa.setText(intent.getStringExtra("alamat"));
            no_tlp.setText(intent.getStringExtra("no_tlp"));
            waktuSewa.setText(intent.getStringExtra("lama"));
        }
    }

    private void loadSpinner() {

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL_SPINNER, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        String data = jsonObject.optString(TAG_nama_Motor);

                        list.add(data);
                        adapter = new ArrayAdapter<>(SewaMotorActivity.this, android.R.layout.simple_spinner_item, list);
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

    private void saveData(String nama, String alamat, String tlp, String sMerk, String diskon, String time, String totalAkhir, String price) {
        Map<String, Object> penyewa = new HashMap<>();

        penyewa.put("nama", nama);
        penyewa.put("alamat", alamat);
        penyewa.put("no_tlp", tlp);
        penyewa.put("merek", sMerk);
        penyewa.put("hargaMotor", price);
        penyewa.put("lama", time);
        penyewa.put("promo", diskon);
        penyewa.put("total", totalAkhir);

        progressDialog.show();

        if (id != null) {
            /**
             *  kode untuk edit data firestore dengan mengambil id
             */
            db.collection("sewaMotor").document(id)
                    .set(penyewa)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(SewaMotorActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(SewaMotorActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            /**
             * Code untuk menambahkan data dengan add
             */
            db.collection("sewaMotor")
                    .add(penyewa)
                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            Toast.makeText(SewaMotorActivity.this, "Berhasil di simpan", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SewaMotorActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
        }
    }
}