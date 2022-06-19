package com.example.rental_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    private TextInputEditText nama,username,password;
    private String nma,user,pass;
    private Button regis;

    private static String URL_Regis = "https://20200140130.praktikumtiumy.com/TugasUAS/register.php";
    private static final String TAG_SUCCCES = "success";
    int succes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        
        nama = findViewById(R.id.tiNama);
        username = findViewById(R.id.tiUsername);
        password = findViewById(R.id.tiPassword);
        regis =findViewById(R.id.btnRegis);
        
        regis.setOnClickListener(v->{
            regis();
        });
        
    }

    private void regis() {
        nma = nama.getText().toString();
        user = username.getText().toString();
        pass = password.getText().toString();
        
        if(nma.isEmpty() || user.isEmpty() || pass.isEmpty()){
            Toast.makeText(this, "Data harus di isi semua", Toast.LENGTH_SHORT).show();
        }
        else {
            RequestQueue  requestQueue = Volley.newRequestQueue(getApplicationContext());
            
            final StringRequest register = new StringRequest(Request.Method.POST, URL_Regis, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        succes = jsonObject.getInt(TAG_SUCCCES);
                        
                        if(succes == 1){
                            Toast.makeText(Register.this, "Register berhasil", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(Register.this, "Gagal Regis", Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    
                }
            }){
                @Override
                protected Map<String, String> getParams(){
                    Map<String, String> params  = new HashMap<>();

                    params.put("nama", nma);
                    params.put("username", user);
                    params.put("password",pass);

                    return params;
                }
            };
            requestQueue.add(register);
        }
    }
}