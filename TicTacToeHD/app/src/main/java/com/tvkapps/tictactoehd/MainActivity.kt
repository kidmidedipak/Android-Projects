package com.tvkapps.tictactoehd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class MainActivity : AppCompatActivity() {


    lateinit var mAdView : AdView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)







        MobileAds.initialize(this) {}

        mAdView = findViewById(com.tvkapps.tictactoehd.R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)




        val rulesActivity = findViewById<ImageView>(R.id.rulesbtn)
        rulesActivity.setOnClickListener {
            val Intent = Intent(this,RulesActivity::class.java)
            startActivity(Intent)
        }


        val careActivity = findViewById<ImageView>(R.id.carelogo)
        careActivity.setOnClickListener {
            val Intent = Intent(this,CareActivity::class.java)
            startActivity(Intent)
        }








        val adinterBtn = findViewById<ImageView>(R.id.playbtn)

        val myInterstitialAds = MyInterstitialAds(this)
        myInterstitialAds.loadInterstitialAds(R.string.interstitial_ads1)







        adinterBtn.setOnClickListener {


            myInterstitialAds.showInterstitialAds {

                val afterIntent = Intent(this, PlayActivity::class.java)
                startActivity(afterIntent)

            }


        }





        val btnExit: ImageView = findViewById(R.id.exitbtn)

        btnExit.setOnClickListener {
            finish() // Finish the current activity
            moveTaskToBack(true) // Move the app to the background
            android.os.Process.killProcess(android.os.Process.myPid()) // Kill the app process
        }







    }

}