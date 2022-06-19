package com.example.rental_test.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rental_test.R;
import com.example.rental_test.model.modelMobil;
import com.example.rental_test.view.DetailMobilActivity;

import java.util.ArrayList;

public class mobilAdapter extends RecyclerView.Adapter<mobilAdapter.mobilViewHolder> {

    private ArrayList<modelMobil> listData;

    public  mobilAdapter(int simple_spinner_item, ArrayList<modelMobil> listData){
        this.listData = listData;
    }
    @NonNull
    @Override
    public mobilViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_data_motor,parent, false);
        return new mobilViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull mobilViewHolder holder, int position) {
        String namaMobil;
        String hargaMobil;

        namaMobil = listData.get(position).getNama_mobil();
        hargaMobil = listData.get(position).getHargaMobil();

        // Style
        holder.txtNama_Mobil.setTextColor(Color.WHITE);
        holder.txtNama_Mobil.setTextSize(25);

        holder.txtNama_Mobil.setText(namaMobil);

        holder.cardMobil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();

                bundle.putString("data1", namaMobil);
                bundle.putString("data2", hargaMobil);

                Intent intent = new Intent(view.getContext(), DetailMobilActivity.class);
                intent.putExtras(bundle);

                view.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return (listData != null) ? listData.size() :0;
    }

    public class mobilViewHolder extends RecyclerView.ViewHolder {
        private CardView cardMobil;
        private TextView txtNama_Mobil, txtHarga;

        public  mobilViewHolder(View view){
            super(view);

            cardMobil = (CardView) view.findViewById(R.id.cardMotor);
            txtNama_Mobil = (TextView) view.findViewById(R.id.textMotor);
        }
    }
}
