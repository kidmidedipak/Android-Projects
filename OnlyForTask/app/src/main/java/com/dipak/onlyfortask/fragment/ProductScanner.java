package com.dipak.onlyfortask.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dipak.onlyfortask.R;
import com.dipak.onlyfortask.adapter.RecyclerAdapter;
import com.dipak.onlyfortask.model.Batch;

import java.util.ArrayList;


public class ProductScanner extends Fragment {

    View view;
    RecyclerView rv_product;
    RecyclerAdapter recyclerAdapter;
    ArrayList<Batch> batchArrayList=new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_product_scanner, container, false);
        rv_product=view.findViewById(R.id.rv_product);
        AppCompatButton acb_scanProduct=view.findViewById(R.id.acb_scanProduct);

        acb_scanProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanProductPopup();
            }
        });

        batchArrayList.add(new Batch("234029",100));
        batchArrayList.add(new Batch("234029",100));
        batchArrayList.add(new Batch("333333",400));
        batchArrayList.add(new Batch("333333",400));
        batchArrayList.add(new Batch("234029",100));
        batchArrayList.add(new Batch("234029",100));
        batchArrayList.add(new Batch("111111",200));
        batchArrayList.add(new Batch("234029",100));
        batchArrayList.add(new Batch("234029",100));
        batchArrayList.add(new Batch("222222",300));
        batchArrayList.add(new Batch("333333",400));
        batchArrayList.add(new Batch("333333",400));
        batchArrayList.add(new Batch("111111",200));
        batchArrayList.add(new Batch("234029",100));
        batchArrayList.add(new Batch("333333",400));
        batchArrayList.add(new Batch("111111",200));
        recyclerSetup();
        return view;
    }

    private void recyclerSetup() {
        recyclerAdapter = new RecyclerAdapter(getContext(), batchArrayList);
        rv_product.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_product.setHasFixedSize(false);
        rv_product.setAdapter(recyclerAdapter);
    }

    private void scanProductPopup() {

        Dialog dialog = new Dialog(getContext());

        // set custom dialog
        dialog.setContentView(R.layout.scan_product_popup);

        AppCompatButton acb_add = dialog.findViewById(R.id.acb_add);

        acb_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        // set custom height and width
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.96);
        dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);

        // set transparent background
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(true);

        // show dialog
        dialog.show();

    }
}