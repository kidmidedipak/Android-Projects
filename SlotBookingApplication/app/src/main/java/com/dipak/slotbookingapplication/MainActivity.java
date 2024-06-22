package com.dipak.slotbookingapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager viewPager = findViewById(R.id.viewPager);

        // Get your dynamic tab titles (replace this with your own logic)
        List<String> tabTitles = getDynamicTabTitles();

        // Set up ViewPager with custom adapter
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), tabTitles);
        viewPager.setAdapter(pagerAdapter);

        // Attach ViewPager to TabLayout
        tabLayout.setupWithViewPager(viewPager);

    }

    private List<String> getDynamicTabTitles() {
        // Replace this with your logic to fetch dynamic tab titles
        List<String> tabTitles = new ArrayList<>();
        tabTitles.add("1 Jan 2023");
        tabTitles.add("2 Jan 2023");
//        tabTitles.add("3 Jan 2023");
//        tabTitles.add("4 Jan 2023");
//        tabTitles.add("5 Jan 2023");
//        tabTitles.add("6 Jan 2023");
//        tabTitles.add("7 Jan 2023");
//        tabTitles.add("8 Jan 2023");
//        tabTitles.add("9 Jan 2023");
//        tabTitles.add("10 Jan 2023");

        // Add more tabs as needed
        return tabTitles;
    }



}