package com.example.rental_test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.rental_test.adapter.mobilAdapter;
import com.example.rental_test.model.modelMobil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class infoMobil extends AppCompatActivity {

    private RecyclerView recyclerView;
    private mobilAdapter adapter;
    private ArrayList<modelMobil> modelMobilArrayList = new ArrayList<>();
    private Button sewa;
    private static final String TAG = infoMobil.class.getSimpleName();
    private static String URL_SELECT = "https://20200140130.praktikumtiumy.com/TugasUAS/bacaMobil.php";
    private static final String TAG_ID = "id";
    private static final String TAG_nama_Mobil = "nama_mobil";
    private static final String TAG_Harga_Mobil = "harga";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_mobil);

        recyclerView = findViewById(R.id.recyclerViewMobil);
        bacaData();
        adapter = new mobilAdapter(android.R.layout.simple_spinner_item, modelMobilArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(infoMobil.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        sewa = findViewById(R.id.BtnPenyewaan);


    }

    private void bacaData() {
        modelMobilArrayList.clear();

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL_SELECT, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++){

                    try {
                        JSONObject obj = response.getJSONObject(i);

                        modelMobil item = new modelMobil();

                        item.setId(obj.getString(TAG_ID));
                        item.setNama_mobil(obj.getString(TAG_nama_Mobil));
                        item.setHargaMobil(obj.getString(TAG_Harga_Mobil));

                        modelMobilArrayList.add(item);
                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(infoMobil.this, "GAGAL", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
}