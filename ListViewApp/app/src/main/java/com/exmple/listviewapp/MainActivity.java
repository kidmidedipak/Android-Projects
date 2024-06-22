package com.exmple.listviewapp;

import androidx.annotation.NonNull;
import  androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    SearchView searchView;
    Toolbar toolbar;
    String[] arr={"Pune", "Mumbai","Latur","Nanded","Thane", "Pimpri-Chinchwad","Nashik","Solapur","Navi Mumbai", "Jalgaon","Akola","Ahmednagar","Jalna", "Udgir","Barshi","Satara"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//--------------------------------------------------------

//        onlyforListView
        customAndSimpleList();

    }



    private void customAndSimpleList() {

        listView=findViewById(R.id.listView);
        searchView=findViewById(R.id.srcView);
        searchView.clearFocus();

        //custom list
        DipakAdapter ad1=new DipakAdapter(this,R.layout.my_layout,arr);
        listView.setAdapter(ad1);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filterList(s);
//                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        //simple list
//        ArrayAdapter<String> ad=new ArrayAdapter(this, android.R.layout.simple_list_item_1,arr);
//        listView.setAdapter(ad);

    }

    private void filterList(String s) {
        List<String> al=new ArrayList<>();
        for (String str:arr)
        {
           if(str.toLowerCase().contains(s.toLowerCase()))
           {
               al.add(str);
           }
        }

        if(al.isEmpty())
        {
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        }else{

            String[] strarr=new String[al.size()];
            DipakAdapter ad=new DipakAdapter(this,R.layout.my_layout,al.toArray(strarr));
            listView.setAdapter(ad);
//             obj.setFilteredList(al);

        }
    }
}