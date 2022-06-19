package com.example.rental_test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.rental_test.adapter.motorAdapter;
import com.example.rental_test.model.modelMotor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class infoMotor extends AppCompatActivity {

    private RecyclerView recyclerView;
    private motorAdapter adapter;
    private ArrayList<modelMotor> modelMotorArrayList = new ArrayList<>();

    private static final String TAG = infoMotor.class.getSimpleName();
    private static String URL_SELECT = "https://20200140130.praktikumtiumy.com/TugasUAS/bacaMotor.php";
    private static final String TAG_ID = "id";
    private static final String TAG_nama_Motor = "nama_kendaraan";
    private static final String TAG_Harga_motor = "harga";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_motor);

        recyclerView = findViewById(R.id.recyclerViewMotor);
        bacaData();
        adapter = new motorAdapter(android.R.layout.simple_spinner_item, modelMotorArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(infoMotor.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    private void bacaData() {
        modelMotorArrayList.clear();

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL_SELECT, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());

                for(int i = 0; i < response.length(); i++){

                    try{
                        JSONObject obj = response.getJSONObject(i);

                        modelMotor item = new modelMotor();

                        item.setId(obj.getString(TAG_ID));
                        item.setNamaKendaraan(obj.getString(TAG_nama_Motor));
                        item.setHargaMotor(obj.getString(TAG_Harga_motor));

                        modelMotorArrayList.add(item);
                    }
                    catch (JSONException e){
                        e.printStackTrace();
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                error.printStackTrace();
                Toast.makeText(infoMotor.this, "GAGAL", Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(jsonArrayRequest);
    }
}