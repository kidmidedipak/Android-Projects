package com.dipak.slotbookingapplication;
import android.app.Application;
import com.razorpay.Checkout;
public class RazorpayPayment extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Checkout.preload(getApplicationContext());
    }
}