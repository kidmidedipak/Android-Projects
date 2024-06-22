package com.dipak.volleydemo;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

public class ClipToPaddingPageTransformer implements ViewPager.PageTransformer {

    @Override
    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();

        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.setAlpha(0);
        } else if (position <= 1) { // [-1,1]
            // Modify the default slide transition to show previous half, current full, and next half
            float scaleFactor = Math.max(0.5f, 1 - Math.abs(position));
            float verticalMargin = view.getHeight() * (1 - scaleFactor) / 2;
            float horizontalMargin = pageWidth * (1 - scaleFactor) / 2;

            if (position < 0) {
                view.setTranslationX(horizontalMargin - verticalMargin / 2);
            } else {
                view.setTranslationX(-horizontalMargin + verticalMargin / 2);
            }

            // Scale the page down (between 0.5 and 1)
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);

            // Fade the page relative to its size.
            view.setAlpha(0.5f + 0.5f * (1 - Math.abs(position)));
        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            view.setAlpha(0);
        }
    }
}

