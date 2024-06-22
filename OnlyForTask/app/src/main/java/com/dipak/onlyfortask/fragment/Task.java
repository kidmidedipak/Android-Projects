package com.dipak.onlyfortask.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.dipak.onlyfortask.R;
import com.dipak.onlyfortask.adapter.GridAdapter;

import java.util.ArrayList;

public class Task extends Fragment {

    View view;
    ArrayList<String> spinnerArrList;
    Spinner spinner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_task1, container, false);

        spinnerInitialization(view);

        initializeGridView(view, 40);
        return view;
    }

    private void spinnerInitialization(View view) {
        spinnerArrList = new ArrayList<>();
        spinnerArrList.add("January");
        spinnerArrList.add("February");
        spinnerArrList.add("March");
        spinnerArrList.add("April");
        spinnerArrList.add("May");
        spinnerArrList.add("June");
        spinnerArrList.add("July");
        spinnerArrList.add("August");
        spinnerArrList.add("September");
        spinnerArrList.add("October");
        spinnerArrList.add("November");
        spinnerArrList.add("December");

        spinner = view.findViewById(R.id.spinner);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_spinner_item,
                spinnerArrList
        );

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Display the selected item in the TextView
                String selectedItem = (String) parentView.getItemAtPosition(position);

                Toast.makeText(getContext(), selectedItem, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });
    }

    public void initializeGridView(View view, int itemCount) {
        GridView gridView = view.findViewById(R.id.gridView);
        GridAdapter gridAdapter = new GridAdapter(getContext(), itemCount);
        gridView.setAdapter(gridAdapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the LinearLayout inside the MaterialCardView
                LinearLayout linearLayout = view.findViewById(R.id.linearView);
                Drawable backgroundDrawable = linearLayout.getBackground();

                int backgroundColor = Color.TRANSPARENT;

                if (backgroundDrawable instanceof ColorDrawable) {
                    // Extract the color
                    backgroundColor = ((ColorDrawable) backgroundDrawable).getColor();
                }

                gridItemDialog("Title " + (position + 1), backgroundColor);

            }
        });
    }

    private void gridItemDialog(String title, int backgroundColor) {

        Dialog dialog = new Dialog(getContext());

        // set custom dialog
        dialog.setContentView(R.layout.grid_item_popup);

        TextView tv_title = dialog.findViewById(R.id.tv_title);
        Button btn_ok = dialog.findViewById(R.id.btn_ok);
        LinearLayout ll_title = dialog.findViewById(R.id.ll_title);

        tv_title.setText(title);
        ll_title.setBackgroundColor(backgroundColor);


        btn_ok.setOnClickListener(new View.OnClickListener() {
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