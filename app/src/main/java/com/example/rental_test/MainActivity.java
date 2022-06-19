package com.example.rental_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

public class MainActivity extends AppCompatActivity {

    TextInputEditText Nama,Password;
    String nma,pass;
    Button Login;
    TextView Register;
    private static String URL_Login = "https://20200140130.praktikumtiumy.com/TugasUAS/login.php";
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String TAG_SUCCES = "success";
    int success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Nama = findViewById(R.id.lgNama);
        Password = findViewById(R.id.lgPass);
        Login = findViewById(R.id.lgBtn);
        Register = findViewById(R.id.lgRegister);
        

        // Button Login
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        Register.setOnClickListener(v->{
            Intent pindah = new Intent(getApplicationContext(), com.example.rental_test.Register.class);
            startActivity(pindah);
        });
    }

    private void login() {
        nma = Nama.getText().toString();
        pass = Password.getText().toString();

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        final StringRequest login = new StringRequest(Request.Method.POST, URL_Login, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "Response : " + response.toString());

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    success = jsonObject.getInt(TAG_SUCCES);
                    if (success == 1){
                        Toast.makeText(MainActivity.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), Home.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(MainActivity.this, "GAGAL", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error : " + error.getMessage());

                Toast.makeText(MainActivity.this, "Gagal Login", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map <String,String> getParams(){
                Map<String,String> params = new HashMap<>();

                params.put("username",nma);
                params.put("password", pass);

                return params;
            }
        };
        requestQueue.add(login);
    }
}