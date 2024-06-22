package com.dipak.volleydemo;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

public class MarginPageTransformer implements ViewPager.PageTransformer {

    private final int marginPx;

    public MarginPageTransformer(int marginPx) {
        this.marginPx = marginPx;
    }

    @Override
    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();
        float scaleFactor = 1 - Math.abs(position) / 2;
        float horizontalMargin = marginPx * (1 - scaleFactor);

        if (position < -1) {
            // This page is way off-screen to the left.
            view.setAlpha(0);
        } else if (position <= 1) {
            // Modify the default slide transition to shrink the page as well
            view.setTranslationX(-horizontalMargin);
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);

            // Fade the page relative to its size.
            view.setAlpha(0.5f + 0.5f * (1 - Math.abs(position)));
        } else {
            // This page is way off-screen to the right.
            view.setAlpha(0);
        }
    }
}

