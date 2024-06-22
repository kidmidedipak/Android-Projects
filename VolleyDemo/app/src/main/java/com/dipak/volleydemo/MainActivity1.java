package com.dipak.volleydemo;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.getkeepsafe.taptargetview.TapTargetView;


public class MainActivity1 extends AppCompatActivity {

    Button next,TranslateAnimation;
     int isvalid=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

         next=findViewById(R.id.next);

        TranslateAnimation=findViewById(R.id.TranslateAnimation);
        LinearLayout layout = findViewById(R.id.layout);
        AnimationDrawable animationDrawable = (AnimationDrawable)
                layout.getBackground();
        animationDrawable.setEnterFadeDuration(1500);
        animationDrawable.setExitFadeDuration(3000);
        animationDrawable.start();

        if ((getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK)
                == Configuration.UI_MODE_NIGHT_YES) {
            // Dark mode is active
            next.setBackgroundColor(getResources().getColor(R.color.btnNightClr));
            next.setTextColor(getResources().getColor(R.color.white));
        }

//        startGuidedTour();

        Intent intent = new Intent( this, MainActivity2.class);
        String title="Animation Button 1";
        String description="Animation Button Description... This is the sample app for TapTargetView";
        showGuidedTourForButton(findViewById(R.id.next),title,description);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(intent);

//                TranslateAnimation translate = new TranslateAnimation(0, 100, 0, 0);
//                translate.setDuration(400);
//                view.startAnimation(translate);
//                overridePendingTransition(R.anim.slide_in_right,
//                        R.anim.slide_out_left);
//                startNextActivity();
            }
        });

        TranslateAnimation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                TranslateAnimation translate = new TranslateAnimation(0, 100, 0, 0);
//                translate.setDuration(400);
//                view.startAnimation(translate);
                overridePendingTransition(0, R.anim.slide_out_right);
                startActivity(intent);
            }
        });



    }
    private void startGuidedTour() {
        new TapTargetSequence(this)
                .targets(
                        TapTarget.forView(findViewById(R.id.next), "Button 1", "This is the first button.")
                                .targetRadius(50)
                                .outerCircleColor(R.color.colorAccent)
                                .textColor(android.R.color.white)
                        // Add more targets for other buttons or features
                )
                .listener(new TapTargetSequence.Listener() {
                    @Override
                    public void onSequenceFinish() {
                        // Handle the guided tour completion
                    }

                    @Override
                    public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {
                        // Handle each step in the guided tour
                    }

                    @Override
                    public void onSequenceCanceled(TapTarget lastTarget) {
                        // Handle the guided tour cancellation
                    }
                })
                .start();
    }

    private void showGuidedTourForButton(final View targetView,String title,String description) {
        TapTargetView.showFor(this,
                TapTarget.forView(targetView, title, description)
                        .outerCircleColor(R.color.mycolor)
                        .targetCircleColor(android.R.color.white)
                        .textColor(android.R.color.white)
                        .cancelable(false)
                        .tintTarget(false)
                        .descriptionTextSize(20)
                        .titleTextSize(30)
//                        .icon(getResources().getDrawable(R.drawable.down_arrow))
                        .targetRadius(60),
                new TapTargetView.Listener() {
                    @Override
                    public void onTargetClick(TapTargetView view) {
                        super.onTargetClick(view);
                        // Perform any action when the guided tour target is clicked
                        isvalid++;
                        if(isvalid==1) {
                            showGuidedTourForButton(findViewById(R.id.next1), "Tap Target Prompt Button 2", "Tap Target button 2 description Perform any action when the guided tour target is clicked ");
                        } else if(isvalid==2){
                            showGuidedTourForButton(findViewById(R.id.next2), "Tap Target Prompt Button 3", "Tap Target button 3 description Perform any action when the guided tour target is clicked ");
                        }
                    }

                    @Override
                    public void onOuterCircleClick(TapTargetView view) {
                        super.onOuterCircleClick(view);
                        // Perform any action when the outer circle is clicked
                    }

                    @Override
                    public void onTargetDismissed(TapTargetView view, boolean userInitiated) {
                        super.onTargetDismissed(view, userInitiated);
                        // Perform any action when the guided tour is dismissed
                        // For example, you can start the next guided tour step
                    }
                });
    }

    private void startNextActivity() {
        Intent intent = new Intent(this, MainActivity2.class);

        AlphaAnimation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setDuration(1000);

        // Apply the animation to the view
        findViewById(android.R.id.content).startAnimation(fadeIn);

        // Start the next activity after the animation finishes
        startActivity(intent);



//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Slide slide = new Slide(Gravity.END); // or Gravity.START for the opposite direction
//            slide.setDuration(400); // Set the duration as needed
//
//            // Apply the transition to the window
//            getWindow().setEnterTransition(slide);
//            getWindow().setExitTransition(slide);
//
//            // Start Activity2 with transition
//            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
//        } else {
//            // For devices running older versions without transitions
//            startActivity(intent);
//        }
    }


}