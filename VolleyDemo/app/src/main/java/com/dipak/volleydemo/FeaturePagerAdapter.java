package com.dipak.volleydemo;

//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.LinearLayout;
//import androidx.viewpager.widget.PagerAdapter;
//import java.util.List;
//
//public class FeaturePagerAdapter extends PagerAdapter {
//
//    private final Context context;
//    private final List<Integer> featureItems; // List of layout resource IDs
//
//    public FeaturePagerAdapter(Context context, List<Integer> featureItems) {
//        this.context = context;
//        this.featureItems = featureItems;
//    }
//
//    @Override
//    public int getCount() {
//        return featureItems.size();
//    }
//
//    @Override
//    public boolean isViewFromObject(View view, Object object) {
//        return view == object;
//    }
//
//    @Override
//    public Object instantiateItem(ViewGroup container, int position) {
//        LayoutInflater inflater = LayoutInflater.from(context);
//        View view = inflater.inflate(featureItems.get(position), container, false);
//
//        container.addView(view);
//        return view;
//    }
//
//    @Override
//    public void destroyItem(ViewGroup container, int position, Object object) {
//        container.removeView((View) object);
//    }
//}

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.viewpager.widget.PagerAdapter;
import com.bumptech.glide.Glide; // Add this import for image loading
import com.squareup.picasso.Picasso;

import java.util.List;

public class FeaturePagerAdapter extends PagerAdapter {

    private final Context context;
    private final List<String> imageUrls; // List of image URLs

    public FeaturePagerAdapter(Context context, List<String> imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;
    }

    @Override
    public int getCount() {
        return imageUrls.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_feature_item, container, false);

        ImageView imageView = view.findViewById(R.id.imageView);

        // Load the image into the ImageView using a library like Glide
//        Glide.with(context)
//                .load(imageUrls.get(position))
//                .into(imageView);


        Picasso.get().
                load(imageUrls.get(position))
                .fit()
                .into(imageView);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
