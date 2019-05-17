package com.arunrk.boradcost;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NumberAdapter extends RecyclerView.Adapter<NumberAdapter.NumberHolder> {

    private List<MobileNumber> list = new ArrayList<>();

    public NumberAdapter(List<MobileNumber> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public NumberHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new NumberHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_row
                        , parent
                        , false));
    }

    @Override
    public void onBindViewHolder(@NonNull NumberHolder holder, int position) {
        holder.txtId.setText("" + list.get(position).getId());
        holder.txtNumber.setText("" + list.get(position).getNumber());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NumberHolder extends RecyclerView.ViewHolder {

        TextView txtId, txtNumber;

        public NumberHolder(@NonNull View itemView) {
            super(itemView);
            txtId = itemView.findViewById(R.id.txtId);
            txtNumber = itemView.findViewById(R.id.txtNumber);
        }

    }
}
