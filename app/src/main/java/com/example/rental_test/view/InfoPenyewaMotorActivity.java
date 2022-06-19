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
import com.example.rental_test.model.modelPenyewa;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class InfoPenyewaMotorActivity extends AppCompatActivity {

    /*Deklarasi Variable yang akan di pakai*/
    private Button Sewa;
    private RecyclerView recyclerView;

    /*Deklarasi FirebaseFirestore*/
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<modelPenyewa> list = new ArrayList<>();
    private penyewaAdapter PenyewaAdapter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_penyewa_motor);

        /*Iniasialisai Variable*/
        Sewa = findViewById(R.id.BtnSewa);
        recyclerView = findViewById(R.id.recyclerInfoMotor);

        /*Pembuatan PopUp loading*/
        progressDialog = new ProgressDialog(InfoPenyewaMotorActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Mengambil data...");
        PenyewaAdapter = new penyewaAdapter(getApplicationContext(), list);

        PenyewaAdapter.setDialog(new penyewaAdapter.Dialog() {
            @Override
            public void onClick(int pos) {
                /*Membuat char Pilihan View,Delete, and Update*/
                final CharSequence[] dialogItem = {"Lihat Data", "Hapus", "Edit Data"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(InfoPenyewaMotorActivity.this);

                dialog.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        /*Pilihan*/
                        switch (i){
                            /*Lihat Data*/
                            case 0:
                                Intent intentView = new Intent(getApplicationContext(),DetailPenyewaMotorActivity.class);
                                intentView.putExtra("id", list.get(pos).getId());
                                intentView.putExtra("nama", list.get(pos).getNama());
                                intentView.putExtra("alamat", list.get(pos).getAlamat());
                                intentView.putExtra("no_tlp", list.get(pos).getNo_Tlp());
                                intentView.putExtra("merek", list.get(pos).getsMerk());
                                intentView.putExtra("promo", list.get(pos).getiPromo());
                                intentView.putExtra("lama", list.get(pos).getiLama());
                                intentView.putExtra("hargaMotor", list.get(pos).getiHarga());
                                intentView.putExtra("total", list.get(pos).getdTotal());
                                startActivity(intentView);
                                break;
                            /*Hapus Data*/
                            case 1:
                                deleteData(list.get(pos).getId());
                                break;
                            /*Update Data*/
                            case 2:
                                Intent intent = new Intent(getApplicationContext(),SewaMotorActivity.class);
                                intent.putExtra("id", list.get(pos).getId());
                                intent.putExtra("nama", list.get(pos).getNama());
                                intent.putExtra("alamat", list.get(pos).getAlamat());
                                intent.putExtra("no_tlp", list.get(pos).getNo_Tlp());
                                intent.putExtra("lama", list.get(pos).getiLama());
                                startActivity(intent);
                                break;
                        }
                    }
                });
                dialog.show();
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL, false);
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(PenyewaAdapter);

        /*Button Sewa*/
        Sewa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Sewa Motor*/
                Intent sewa = new Intent(getApplicationContext(),SewaMotorActivity.class);
                startActivity(sewa);
            }
        });
    }

    /* Delete Database */
    private void deleteData(String id) {
        progressDialog.show();

        db.collection("sewaMotor").document(id)
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()){
                            Toast.makeText(InfoPenyewaMotorActivity.this, "Data Gagal Di hapus", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(InfoPenyewaMotorActivity.this, "Data Berhasil Di hapus", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                        getData();
                    }
                });
    }

    /*Membuat data tampil di halaman info penyewa motor*/
    @Override
    protected void onStart(){
        super.onStart();
        getData();
    }

    /*Select Data*/
    private void getData() {
        progressDialog.show();

        db.collection("sewaMotor")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();

                        if (task.isSuccessful()){

                            for (QueryDocumentSnapshot document : task.getResult()){
                                modelPenyewa modelPenyewa = new modelPenyewa(document.getString("nama"), document.getString("alamat"), document.getString("no_tlp"), document.getString("merek"),
                                        document.getString("promo"), document.getString("lama"), document.getString("hargaMotor"), document.getString("total"));
                                modelPenyewa.setId(document.getId());
                                list.add(modelPenyewa);
                            }
                            PenyewaAdapter.notifyDataSetChanged();
                        }
                        else{
                            Toast.makeText(InfoPenyewaMotorActivity.this, "Data Gagal di Ambil", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }
}