package com.dipak.bottomnavbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bnview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bnview=findViewById(R.id.bnView);

        bnview.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                if(id==R.id.home)
                {
                loadFrag(new AFragment(), true);
                }else if(id==R.id.search){
                    Toast.makeText(MainActivity.this, "Search", Toast.LENGTH_SHORT).show();
                }else if(id==R.id.contactus){
                    Toast.makeText(MainActivity.this, "Contact Us", Toast.LENGTH_SHORT).show();
                }else if(id==R.id.utilities){
                    Toast.makeText(MainActivity.this, "Utilities", Toast.LENGTH_SHORT).show();
                }else  {
                    Toast.makeText(MainActivity.this, "My profile", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

        bnview.setSelectedItemId(R.id.myprofile);

    }

    public void loadFrag(Fragment fragment, boolean flag){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction ft= fragmentManager.beginTransaction();
        if(flag) {
            ft.add(R.id.container, fragment);
        }
        else {
            ft.replace(R.id.container, fragment);
            ft.commit();
        }

    }
}