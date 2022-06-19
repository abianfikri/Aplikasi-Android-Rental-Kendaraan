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
import com.example.rental_test.model.modelMotor;
import com.example.rental_test.view.DetailMotorActivity;

import java.util.ArrayList;

public class motorAdapter extends RecyclerView.Adapter<motorAdapter.motorViewHolder> {

    private ArrayList<modelMotor> listData;

    public motorAdapter(int simple_spinner_item, ArrayList<modelMotor> listData){
        this.listData = listData;
    }

    @NonNull
    @Override
    public motorAdapter.motorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_data_motor, parent, false);
        return new motorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull motorAdapter.motorViewHolder holder, int position) {
        String nama_motor,hargaMotor;

        nama_motor = listData.get(position).getNamaKendaraan();
        hargaMotor = listData.get(position).getHargaMotor();

        //Style
        holder.nama_motor.setTextColor(Color.WHITE);
        holder.nama_motor.setTextSize(25);

        holder.nama_motor.setText(nama_motor);

        // Pindah Halaman Detail Motor
        holder.cardMotor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();

                bundle.putString("namaMotor", nama_motor);
                bundle.putString("hargaMotor", hargaMotor);

                Intent intent = new Intent(view.getContext(), DetailMotorActivity.class);
                intent.putExtras(bundle);

                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (listData !=null) ? listData.size():0;
    }

    public class motorViewHolder extends RecyclerView.ViewHolder {
        private CardView cardMotor;
        private TextView nama_motor;

        public motorViewHolder(View view){
            super(view);

            cardMotor = (CardView) view.findViewById(R.id.cardMotor);
            nama_motor = (TextView) view.findViewById(R.id.textMotor);
        }
    }
}
