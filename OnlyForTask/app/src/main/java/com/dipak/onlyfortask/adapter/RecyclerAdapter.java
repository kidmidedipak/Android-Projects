package com.dipak.onlyfortask.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dipak.onlyfortask.R;
import com.dipak.onlyfortask.model.Batch;

import java.util.ArrayList;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    ArrayList<Batch> batchArrayList;
    Context context;

    public RecyclerAdapter(Context context, ArrayList<Batch> batchArrayList) {
        this.batchArrayList = batchArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_product,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Batch batch = batchArrayList.get(position);

        holder.tv_batchNo.setText(batch.getBatchNo());
//        holder.tv_unit.setText(consumableData.);
        holder.tv_quantity.setText(batch.getQuantity()+"");

    }

    @Override
    public int getItemCount() {
        return batchArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_batchNo, tv_quantity ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_batchNo = itemView.findViewById(R.id.tv_batchNo);
            tv_quantity = itemView.findViewById(R.id.tv_qty);
        }
    }
}
