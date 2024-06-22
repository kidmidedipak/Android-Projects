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
import com.dipak.onlyfortask.model.UserModel;

import java.util.ArrayList;


public class FirebaseRecyclerAdapter extends RecyclerView.Adapter<FirebaseRecyclerAdapter.ViewHolder> {

    ArrayList<UserModel> arrayList;
    Context context;

    public FirebaseRecyclerAdapter(Context context, ArrayList<UserModel> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserModel userModel = arrayList.get(position);

        holder.tv_name.setText(userModel.getName());
        holder.tv_number.setText(userModel.getNumber());
        holder.tv_email.setText(userModel.getEmail());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name, tv_number,tv_email ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_number = itemView.findViewById(R.id.tv_number);
            tv_email = itemView.findViewById(R.id.tv_email);
        }
    }
}
