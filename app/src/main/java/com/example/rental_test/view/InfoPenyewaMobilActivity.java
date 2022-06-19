package com.example.rental_test.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.rental_test.R;
import com.example.rental_test.adapter.penyewaAdapter;
import com.example.rental_test.model.modelMobil;
import com.example.rental_test.model.modelPenyewa;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class InfoPenyewaMobilActivity extends AppCompatActivity {

    private Button Sewa;

    private RecyclerView recyclerView;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private List<modelPenyewa> list = new ArrayList<>();
    private penyewaAdapter adapter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_penyewa_mobil);

        recyclerView = findViewById(R.id.recyclerInfoMobil);

        Sewa = findViewById(R.id.BtnPenyewaan);

        progressDialog = new ProgressDialog(InfoPenyewaMobilActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Mengambil data...");
        adapter = new penyewaAdapter(getApplicationContext(), list);

        adapter.setDialog(new penyewaAdapter.Dialog() {
            @Override
            public void onClick(int pos) {
                final CharSequence[] dialogItem = {"Lihat Data", "Hapus","Edit Data"};
                AlertDialog.Builder diaglog =new AlertDialog.Builder(InfoPenyewaMobilActivity.this);
                diaglog.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                Intent intentView = new Intent(getApplicationContext(),DetailPenyewaMobilActivity.class);
                                intentView.putExtra("id", list.get(pos).getId());
                                intentView.putExtra("nama", list.get(pos).getNama());
                                intentView.putExtra("alamat", list.get(pos).getAlamat());
                                intentView.putExtra("no_tlp", list.get(pos).getNo_Tlp());
                                intentView.putExtra("merek", list.get(pos).getsMerk());
                                intentView.putExtra("hargaMobil", list.get(pos).getiHarga());
                                intentView.putExtra("promo", list.get(pos).getiPromo());
                                intentView.putExtra("lama",list.get(pos).getiLama());
                                intentView.putExtra("total", list.get(pos).getdTotal());
                                startActivity(intentView);
                                break;
                            case 1:
                                // Memanggil class delete data
                                deleteData(list.get(pos).getId());
                                break;
                            case 2:
                                Intent intent1 = new Intent(getApplicationContext(), SewaMobilActivity.class);
                                intent1.putExtra("id", list.get(pos).getId());
                                intent1.putExtra("nama", list.get(pos).getNama());
                                intent1.putExtra("alamat", list.get(pos).getAlamat());
                                intent1.putExtra("no_tlp", list.get(pos).getNo_Tlp());
                                intent1.putExtra("lama", list.get(pos).getiLama());
                                startActivity(intent1);
                                break;

                        }
                    }
                });
                diaglog.show();
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(adapter);

        Sewa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data = new Intent(getApplicationContext(), SewaMobilActivity.class);
                startActivity(data);
            }
        });
    }

    // Delete database
    private void deleteData(String id) {
        progressDialog.show();
        db.collection("penyewa").document(id)
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(InfoPenyewaMobilActivity.this, "Data Gagal Di hapus", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(InfoPenyewaMobilActivity.this, "Data Berhasil di Hapus", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                        getData();
                    }
                });
    }

    @Override
    protected void onStart(){
        super.onStart();
        getData();
    }

    // Select data
    private void getData() {
        progressDialog.show();

        db.collection("penyewa")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();
                        if(task.isSuccessful()){

                            for(QueryDocumentSnapshot document : task.getResult()){
                                modelPenyewa modelPenyewa = new modelPenyewa(document.getString("nama"), document.getString("alamat"), document.getString("no_tlp"), document.getString("merek"),
                                        document.getString("lama"), document.getString("promo"), document.getString("hargaMobil"), document.getString("total"));
                                modelPenyewa.setId(document.getId());
                                list.add(modelPenyewa);
                            }
                            adapter.notifyDataSetChanged();
                        }
                        else {
                            Toast.makeText(InfoPenyewaMobilActivity.this, "Data Gagal di ambil", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }
}