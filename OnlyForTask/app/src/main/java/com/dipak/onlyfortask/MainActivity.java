package com.dipak.onlyfortask;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.dipak.onlyfortask.fragment.PaymentGetway;
import com.dipak.onlyfortask.fragment.ProductScanner;
import com.dipak.onlyfortask.fragment.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Task task=new Task();
        ProductScanner productScanner=new ProductScanner();
        getSupportFragmentManager()
                .beginTransaction()
//                .replace(R.id.container,task)
                .replace(R.id.container, productScanner)
                .commit();


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
               int id= item.getItemId();

               if(id==R.id.navigation_home){
                   selectedFragment = new PaymentGetway();
               }
               if(id==R.id.navigation_dashboard){
                   selectedFragment = new ProductScanner();
               }
               if(id==R.id.navigation_notifications){
                   selectedFragment = new Task();
               }

                getSupportFragmentManager().beginTransaction().replace(R.id.container,
                        selectedFragment).commit();
                return true;
            }
        });

        // Set the default selected fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.container,
                new Task()).commit();


    }
}