package com.dipak.slotbookingapplication;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {

    private Context context;
    ArrayList<SlotModel> slotModelArrayList;
    ArrayList<String> gridArrayList;
    boolean isSelected=false;
    private int secPosition = -1;
    SlotModel slotModel;
    ArrayList<Integer> selectedIndex=new ArrayList<>();

    public GridAdapter(Context context,ArrayList<SlotModel> slotModelArrayList) {
        this.context = context;
        this.slotModelArrayList = slotModelArrayList;

        gridArrayList=new ArrayList<>();
        for( int i=0;i<slotModelArrayList.size();i++)
        {
            gridArrayList.add(slotModelArrayList.get(i).getTime());
            gridArrayList.addAll(slotModelArrayList.get(i).getTimeList());
        }

    }

    @Override
    public int getCount() {
        return gridArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return gridArrayList.get(position); // Not used in this example
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View gridViewItem;

        if (convertView == null) {
            // Inflate the layout for each grid item
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridViewItem = inflater.inflate(R.layout.grid_item, null);
        } else {
            gridViewItem = convertView;
        }

            TextView textView1 = gridViewItem.findViewById(R.id.gridTxt1);
//            TextView textView2 = gridViewItem.findViewById(R.id.gridTxt2);
            LinearLayout linearLayout = gridViewItem.findViewById(R.id.linearView);
            String fulltime = gridArrayList.get(position);
        if((position%5)!=0) {
            // Set the text for each grid item as the position + 1

            textView1.setText(fulltime);
//        String time[]=fulltime.split("-");
//        textView1.setText(time[0]);
//        textView2.setText(time[1]);

            //===================================================================================

            if (isSelected) {
                 if(selectedIndex.contains(position))
                {
                    linearLayout.setBackgroundColor(context.getResources().getColor(R.color.yellow_3));
                }else {
                 linearLayout.setBackgroundColor(context.getResources().getColor(R.color.skyBlue));
                }
            } else {
                linearLayout.setBackgroundColor(context.getResources().getColor(R.color.skyBlue));
            }
        }else {
            textView1.setText(fulltime);
            linearLayout.setBackgroundColor(context.getResources().getColor(R.color.white));
        }

// Check the current background color

        gridViewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isSelected = true;
                if (position % 5 != 0) {
                    if (secPosition < 0) {
                        selectedIndex.add(position);
                        secPosition = position;
                    } else {
                        if (selectedIndex.contains(position)) {
                            setRemoveData(secPosition, position);

                            if (position == 1) {
                                secPosition = -1;
                            } else {
                                secPosition = position;
                            }
                        } else {

                            setDataAccording(secPosition, position);
                            secPosition = position;
                        }
                    }

//                // Notify the adapter that the data has changed
                    notifyDataSetChanged();
                }
                }

        });


        return gridViewItem;
    }

    private void setRemoveData(int secPosition, int position) {
        if(secPosition>position)
        {
            for (int i = position; i<=secPosition ; i++) {
                if((i%5)!=0) {
                    selectedIndex.remove((Integer) i);
                }
            }
        }else{
            for (int i = secPosition; i<=position ; i++) {
                if((i%5)!=0) {
                    selectedIndex.remove((Integer) i);
                }
            }
        }

    }

    private void setDataAccording(int secPosition, int position) {
        if(secPosition >position)
        {
            for (int i = position; i <=  secPosition; i++) {
                if(selectedIndex.contains(i)) {

                }else {
                    if ((i % 5) != 0) {
                        selectedIndex.add(i);
                    }
                }
            }
        }else{
            for (int i =  secPosition; i <=position ; i++) {
                if(selectedIndex.contains(i)) {

                }else {
                    if ((i % 5) != 0) {
                        selectedIndex.add(i);
                    }
                }
            }
        }
    }




}