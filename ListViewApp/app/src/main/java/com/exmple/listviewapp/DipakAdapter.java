package com.exmple.listviewapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class DipakAdapter extends ArrayAdapter {

    private String[] arr;
    private Context context;


    public DipakAdapter(@NonNull Context context, int resource, @NonNull String [] arr) {
        super(context, resource, arr);
        this.context=context;
        this.arr=arr;
    }



    @Nullable
    @Override
    public Object getItem(int position) {
        return arr[position];
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView= LayoutInflater.from(getContext()).inflate(R.layout.my_layout,parent,false);
        TextView t=convertView.findViewById(R.id.textView);
        t.setText((CharSequence) getItem(position));
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "You Clicked on : "+arr[position], Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }
}
