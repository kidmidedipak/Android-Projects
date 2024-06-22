package com.dipak.volleydemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.pubnub.api.PNConfiguration;
import com.pubnub.api.PubNub;
import com.pubnub.api.callbacks.SubscribeCallback;
import com.pubnub.api.models.consumer.PNStatus;
import com.pubnub.api.models.consumer.objects_api.channel.PNChannelMetadataResult;
import com.pubnub.api.models.consumer.objects_api.membership.PNMembershipResult;
import com.pubnub.api.models.consumer.objects_api.uuid.PNUUIDMetadataResult;
import com.pubnub.api.models.consumer.pubsub.PNMessageResult;
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult;
import com.pubnub.api.models.consumer.pubsub.PNSignalResult;
import com.pubnub.api.models.consumer.pubsub.files.PNFileEventResult;
import com.pubnub.api.models.consumer.pubsub.message_actions.PNMessageActionResult;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HomeFeaturesAds extends AppCompatActivity {

    private ViewPager viewPager;
    private FeaturePagerAdapter featurePagerAdapter;
    private LinearLayout dotsLayout;
    private List<String> imageUrls;

    String token="dipak";
    PubNub pubnub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_features_ads);

        createNotification();

        viewPager = findViewById(R.id.viewPager);
        dotsLayout = findViewById(R.id.dotsLayout);

        // Sample image URLs (replace with your actual image URLs)
        imageUrls = new ArrayList<>();
        imageUrls.add("https://rukminim2.flixcart.com/fk-p-flap/1600/710/image/c0aed8819fdf0746.jpeg?q=20");
        imageUrls.add("https://rukminim2.flixcart.com/fk-p-flap/1600/710/image/06b3e7aff6bfbad2.jpg?q=20");
        imageUrls.add("https://rukminim2.flixcart.com/fk-p-flap/1600/710/image/58869310f542a104.jpeg?q=20");
        imageUrls.add("https://rukminim2.flixcart.com/fk-p-flap/1600/710/image/64740f70659f8b3d.jpg?q=20");
        imageUrls.add("https://rukminim2.flixcart.com/fk-p-flap/1600/710/image/46cc01b55f9b9d4b.jpg?q=20");
        imageUrls.add("https://rukminim2.flixcart.com/fk-p-flap/1600/710/image/06b3e7aff6bfbad2.jpg?q=20");
        imageUrls.add("https://rukminim2.flixcart.com/fk-p-flap/1600/710/image/e2ddbbf7ef284eb3.jpeg?q=20");
        imageUrls.add("https://rukminim2.flixcart.com/fk-p-flap/1600/710/image/06b3e7aff6bfbad2.jpg?q=20");


        // Set a negative MarginPageTransformer to show left and right sides of adjacent items
//        int margin = getResources().getDimensionPixelOffset(R.dimen.viewpager_page_margin); // Adjust margin as needed

        int margin = getResources().getDimensionPixelOffset(R.dimen.viewpager_page_margin); // Adjust margin as needed
        viewPager.setPageMargin(-margin);
        viewPager.setClipToPadding(false);
        viewPager.setPadding(margin, 0, margin, 0);
        viewPager.setPageTransformer(true, new MarginPageTransformer(margin));
//        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
//        viewPager.setPageTransformer(true, new ClipToPaddingPageTransformer());

        featurePagerAdapter = new FeaturePagerAdapter(this, imageUrls);
        viewPager.setAdapter(featurePagerAdapter);

        // Add indicators (dots)
        addDotsIndicator();
        // Optional: Set up automatic scrolling
        startAutoScrolling();
    }

    private void createNotification() {

        FirebaseMessaging.getInstance().subscribeToTopic("Dipak")
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                String msg="Done";
                                if(!task.isSuccessful())
                                {
                                    msg="Failed";
                                }
                            }
                        });

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                           System.out.println("Fetching FCM registration token failed" );
                            return;
                        }

                        // Get new FCM registration token
                         token = task.getResult();

                        System.out.println(token);
                        Toast.makeText(HomeFeaturesAds.this, "My Device register token is "+token, Toast.LENGTH_SHORT).show();
                    }
                });
        Log.d("token1",token);
    }

    private void addDotsIndicator() {
        for (int i = 0; i < featurePagerAdapter.getCount(); i++) {
            ImageView dot = new ImageView(this);
            dot.setImageDrawable(getResources().getDrawable(R.drawable.dot_inactive));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.gravity = Gravity.CENTER;

            dotsLayout.addView(dot, params);
        }

    }

    private void setDotStatus(int position, boolean isActive) {
        ImageView dot = (ImageView) dotsLayout.getChildAt(position);
        if (dot != null) {
            dot.setImageDrawable(getResources().getDrawable(
                    isActive ? R.drawable.dot_active : R.drawable.dot_inactive));
        }
    }

    private void startAutoScrolling() {
        final int delayMillis = 3000; // Adjust the delay time as needed
        final int pageCount = featurePagerAdapter.getCount();

        final android.os.Handler handler = new android.os.Handler();
        final Runnable runnable = new Runnable() {
            public void run() {
                int currentItem = viewPager.getCurrentItem();
                int nextItem = (currentItem + 1) % pageCount;
                viewPager.setCurrentItem(nextItem, true);

                // Update dots indicator
                setDotStatus(currentItem, false);
                setDotStatus(nextItem, true);

                handler.postDelayed(this, delayMillis);
            }
        };

        handler.postDelayed(runnable, delayMillis);
    }


}