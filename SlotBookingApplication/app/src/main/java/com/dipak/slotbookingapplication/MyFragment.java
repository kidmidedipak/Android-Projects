package com.dipak.slotbookingapplication;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class MyFragment extends Fragment {

    private static final String ARG_TAB_TITLE = "tabTitle";

    public static MyFragment newInstance(String tabTitle) {
        MyFragment fragment = new MyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TAB_TITLE, tabTitle);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_slots, container, false);

        ArrayList<SlotModel > al=new ArrayList<>();
        al.clear();
         initializeAL(al) ;

        initializeGridView(view,al);


        return view;
    }

    public void initializeGridView( View view, ArrayList<SlotModel> slotModelArrayList){
        GridView gridView=view.findViewById(R.id.gridView);
        GridAdapter gridAdapter = new GridAdapter(getContext(), slotModelArrayList );
        gridView.setAdapter(gridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Show a Toast with the selected item's position + 1

//                openOtSchedulerList(position);
                Toast.makeText(getContext(), "Selected item: " + (position + 1), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initializeAL(ArrayList<SlotModel> al) {

        int time=12;
        al.add(new SlotModel(time+" AM",settime(time )));

        time=1;
        al.add(new SlotModel(time+" AM",settime(time )));

        time=2;
        al.add(new SlotModel(time+" AM",settime(time )));

        time=3;
        al.add(new SlotModel(time+" AM",settime(time )));

        time=4;
        al.add(new SlotModel(time+" AM",settime(time )));

        time=5;
        al.add(new SlotModel(time+" AM",settime(time )));

        time=6;
        al.add(new SlotModel(time+" AM",settime(time )));

        time=7;
        al.add(new SlotModel(time+" AM",settime(time )));

        time=8;
        al.add(new SlotModel(time+" AM",settime(time )));

        time=9;
        al.add(new SlotModel(time+" AM",settime(time )));

        time=10;
        al.add(new SlotModel(time+" AM",settime(time )));

        time=11;
        al.add(new SlotModel(time+" AM",settime(time )));

        time=12;
        al.add(new SlotModel(time+" PM",settime(time )));
    }

    public ArrayList<String > settime(int time ){
        ArrayList<String > slotRow=new ArrayList<>();
        slotRow.add(time+":00AM-"+time+":15AM");
        slotRow.add(time+":15AM-"+time+":30AM");
        slotRow.add(time+":30AM-"+time+":45AM");
        if(time==12) {
            slotRow.add(time + ":45AM-" + 1 + ":00AM");
        }
        else {
            slotRow.add(time + ":45AM-" + (time + 1) + ":00AM");
        }
        return slotRow;
    }

}
