package com.dipak.slotbookingapplication;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import org.json.JSONObject;

public class PaymentActivity extends AppCompatActivity implements PaymentResultListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        initiateRazorpayPayment();
    }

    private void initiateRazorpayPayment() {
        try {
            Checkout checkout = new Checkout();
            checkout.setKeyID("YOUR_RAZORPAY_KEY_ID"); // Replace with your Razorpay Key ID

            JSONObject options = new JSONObject();

            options.put("name", "Your App Name");
            options.put("description", "Purchase Description");
            options.put("image", "https://your-website.com/logo.png"); // URL of your app's logo
            options.put("currency", "INR"); // Use the currency code of your choice
            options.put("amount", "10000"); // Amount in paise (10000 paise = ₹100)
            options.put("order_id", "your_order_id"); // Replace with your own order ID

            JSONObject preFill = new JSONObject();
            preFill.put("email", "kidmidedipak0666@gmail.com");
            preFill.put("contact", "9975066696");

            options.put("prefill", preFill);

            checkout.open(this, options);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        // Handle the payment success event
    }

    @Override
    public void onPaymentError(int code, String description) {
        // Handle the payment error event
    }
}