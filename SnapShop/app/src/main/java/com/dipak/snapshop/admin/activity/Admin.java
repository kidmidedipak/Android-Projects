package com.dipak.snapshop.admin.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.multidex.MultiDex;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dipak.snapshop.R;
import com.dipak.snapshop.admin.activity.fragment.About;
import com.dipak.snapshop.admin.activity.fragment.Categorys;
import com.dipak.snapshop.admin.activity.fragment.Dashboard;
import com.dipak.snapshop.admin.activity.fragment.Products;
import com.dipak.snapshop.common.activity.LoginActivity;
import com.google.android.material.navigation.NavigationView;

public class Admin extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        MultiDex.install(this);
        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.navigationView);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //set data to header
        setNameAndEmail();
        loadFragment(new Dashboard());

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(
                this,drawerLayout,toolbar,R.string.open,R.string.close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id= item.getItemId();
                if(id==R.id.dashboard)
                {
                    loadFragment(new Dashboard());
                }else if(id==R.id.product){
                    loadFragment(new Products());
                }else if(id==R.id.category){
                    loadFragment(new Categorys());
                }else if(id==R.id.about){
                    loadFragment(new About());
                }else if(id==R.id.logout){
                    logout();
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    public void setToolbarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
    private void setNameAndEmail() {
        Intent receivedIntent = getIntent();
        View headerView = navigationView.getHeaderView(0);

        // Access the TextView inside the header layout
        TextView logInUserNameTxt = headerView.findViewById(R.id.logInUserName);
        TextView logInuserEmailTxt = headerView.findViewById(R.id.logInUserEmail);

// Retrieve the extra data using the key
        if (receivedIntent != null && receivedIntent.hasExtra("email")) {
            String email = receivedIntent.getStringExtra("email");
            String name = receivedIntent.getStringExtra("name");
            logInUserNameTxt.setText(name);
            logInuserEmailTxt.setText(email);
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            // If there are fragments in the back stack, pop the top one
            fragmentManager.popBackStack();
        } else if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
            finishAffinity();
        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm=getSupportFragmentManager() ;
        FragmentTransaction ft=fm.beginTransaction();
        ft.replace(R.id.container, fragment );
        ft.commit();
    }

    public void logout(){
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
        Intent intent=new Intent(this, LoginActivity.class);

        startActivity(intent);
        finish();
    }
}