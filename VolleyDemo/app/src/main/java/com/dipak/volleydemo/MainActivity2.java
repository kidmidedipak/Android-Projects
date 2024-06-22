package com.dipak.volleydemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

public class MainActivity2 extends AppCompatActivity implements Animation.AnimationListener {

    ImageView imageView;
    Button btnmove;
    ShowcaseView showcaseView1=null;
    Animation animMove;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Button previous=findViewById(R.id.previous);
        imageView =  findViewById(R.id.imgmove);
        btnmove =  findViewById(R.id.btnmove);

        animMove = AnimationUtils.loadAnimation(this,
                R.anim.move );
        animMove.setAnimationListener(this);

//        ============================================================================================================

        View myButton = findViewById(R.id.guide1);

         showcaseView1 = new ShowcaseView.Builder(this)
                .setTarget(new ViewTarget(myButton))
                .setContentTitle("Welcome to MyApp")
                .setContentText("Tap this button to get started!")
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Handle click event on the showcase view
                        Toast.makeText(MainActivity2.this, "showcaseview clicked", Toast.LENGTH_SHORT).show();
                        if (showcaseView1 != null) {
                            showcaseView1.hide(); // Dismiss the showcase view
                        }
                    }
                })
                .build();
        // Assign showcaseView1 to the variable after it's created
        showcaseView1.setButtonText("Done");

        // Resize the showcase view circle (adjust the radius as needed)
        showcaseView1.setShowcase(new ViewTarget(myButton), true);
//        showcaseView1.anima(0, 0, 0, -100);

//        ============================================================================================================

        btnmove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.startAnimation(animMove);
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animationscreen();
            }
        });

    }

    private void animationscreen() {
        Intent intent = new Intent( this, MainActivity1.class);

//        ScaleAnimation scaleUp = new ScaleAnimation(0, 1, 0, 1);
//        scaleUp.setDuration(400);

//        // Apply the animation to the view
//        findViewById(android.R.id.content).startAnimation(scaleUp);

        // Start the next activity after the animation finishes
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        startActivity(intent);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
//        overridePendingTransition(R.anim.slide_in_left,
//                R.anim.slide_out_right);
    }


    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}