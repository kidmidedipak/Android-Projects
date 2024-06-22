package com.dipak.slotbookingapplication;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import java.util.List;

public class MyPagerAdapter extends FragmentPagerAdapter {

    private List<String> tabTitles;

    public MyPagerAdapter(FragmentManager fm, List<String> tabTitles) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.tabTitles = tabTitles;
    }

    @Override
    public Fragment getItem(int position) {
        // Create and return a new instance of your fragment
        return MyFragment.newInstance(tabTitles.get(position));
    }

    @Override
    public int getCount() {
        // Number of tabs
        return tabTitles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Set tab names based on the dynamic titles
        return tabTitles.get(position);
    }
}



