package com.dipak.onlyfortask.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dipak.onlyfortask.R;

public class GridAdapter extends BaseAdapter {

    private Context context;
    private int dayInMonth;

    public GridAdapter(Context context, int dayInMonth) {
        this.context = context;
        this.dayInMonth = dayInMonth;

    }

    @Override
    public int getCount() {
        return dayInMonth;
    }

    @Override
    public Object getItem(int position) {
        return null; // Not used in this example
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

        // Set the text for each grid item as the position + 1

        TextView textView1 = gridViewItem.findViewById(R.id.gridTxt1);
        TextView textView2 = gridViewItem.findViewById(R.id.gridTxt2);
        textView1.setText("Title "+String.valueOf(position+1));

        LinearLayout linearLayout = gridViewItem.findViewById(R.id.linearView);
        if(position==9  )
        {
            int color = context.getResources().getColor(R.color.light_orange);
            linearLayout.setBackgroundColor(color);
        }else if(position==6)
        {
            int color = context.getResources().getColor(R.color.light_blue);
            linearLayout.setBackgroundColor(color);
        }else if( position==18)
        {
            int color = context.getResources().getColor(R.color.light_red);
            linearLayout.setBackgroundColor(color);
        }

        return gridViewItem;
    }
}