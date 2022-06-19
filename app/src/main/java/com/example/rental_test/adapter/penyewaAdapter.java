package com.example.rental_test.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rental_test.R;
import com.example.rental_test.model.modelPenyewa;

import java.util.List;

public class penyewaAdapter extends RecyclerView.Adapter<penyewaAdapter.PenyewaViewHolder> {

    private Context context;
    private List<modelPenyewa> list;
    private Dialog dialog;

    public interface Dialog{
        void onClick(int pos);
    }

    public void setDialog(Dialog dialog){
        this.dialog = dialog;
    }

    public penyewaAdapter(Context context, List<modelPenyewa> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public PenyewaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_data_motor,parent, false);
        return new PenyewaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PenyewaViewHolder holder, int position) {
        holder.nama.setTextSize(30);
        holder.nama.setTextColor(Color.WHITE);
        holder.nama.setText(list.get(position).getNama());
//        holder.alamat.setText(list.get(position).getAlamat());
//        holder.no_Tlp.setText(list.get(position).getNo_Tlp());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class PenyewaViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView nama, alamat, no_Tlp;

        public PenyewaViewHolder(@NonNull View itemView){
            super(itemView);

            cardView =  itemView.findViewById(R.id.cardMotor);
            nama = itemView.findViewById(R.id.textMotor);
            
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(dialog != null){
                        dialog.onClick(getLayoutPosition());
                    }
                }
            });
        }
    }
}
