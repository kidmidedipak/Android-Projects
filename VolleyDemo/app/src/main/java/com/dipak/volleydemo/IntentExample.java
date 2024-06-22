package com.dipak.volleydemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class IntentExample extends AppCompatActivity {

    Button btnSendEmail,btnWebSearch;
    ImageView img;
    private static final int MY_SOCKET_TIMEOUT_MS = 10000; // 1000=1 seconds
    private OrientationEventListener orientationEventListener;
    private static boolean isLandscape = false;

    EditText msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_example);

        SSLUtils.trustAllCertificates();
        btnSendEmail=findViewById(R.id.btnSendEmail);
        btnWebSearch=findViewById(R.id.btnWebSearch);
        msg=findViewById(R.id.msg);
        Button rotateButton = findViewById(R.id.rotateButton);

        // Initialize OrientationEventListener
//        orientationEventListener = new OrientationEventListener(this) {
//            @Override
//            public void onOrientationChanged(int orientation) {
//                // Check if the device is in landscape or portrait mode
//                if ((orientation >= 0 && orientation < 45) || (orientation >= 315 && orientation < 360)) {
//                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//                    isLandscape = false;
//                } else if (orientation >= 225 && orientation < 315) {
//                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
//                    isLandscape = true;
//                } else if (orientation >= 135 && orientation < 225) {
//                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//                    isLandscape = false;
//                } else if (orientation >= 45 && orientation < 135) {
//                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//                    isLandscape = true;
//                }
//            }
//        };
//
//        // Check if the device supports orientation changes
//        if (orientationEventListener.canDetectOrientation()) {
//            orientationEventListener.enable();
//        } else {
//            Toast.makeText(this, "Cannot detect orientation changes on this device", Toast.LENGTH_SHORT).show();
//        }

        // Button click listener to manually toggle between landscape and portrait
        rotateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLandscape) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    isLandscape = false;
                } else {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    isLandscape = true;
                }
            }
        });

        btnSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent email = new Intent(Intent.ACTION_SENDTO);
                email.setData(Uri.parse("mailto:")); // Use "mailto:" for email apps
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{"Kidmidedipak0666@gmail.com"});
                email.putExtra(Intent.EXTRA_SUBJECT, "here is subject");

                if (email.resolveActivity(getPackageManager()) != null) {
                    startActivity(Intent.createChooser(email, "Send email using"));
                }
            }
        });

        btnWebSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                springApiRequest();
//                makeApiRequest();
            }
        });
    }



    public void makeApiRequest() {

        String apiUrl = "https://192.168.0.171/hospital_api/dipak_ki_api";

        Toast.makeText(getApplicationContext(), "Request Send Successfully", Toast.LENGTH_SHORT).show();

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, apiUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the API response
                        Toast.makeText(getApplicationContext(), "response  "+response, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("dkerror",""+error);
                        // Handle errors
                        if (error.networkResponse == null) {
                            // Network error, possibly a timeout
                            showToast(getApplicationContext(), "Network request timed out");
                        } else {
                            // Other types of errors, handle accordingly
                            showToast(getApplicationContext(), "Error: " + error.getMessage());
                        }
                    }
                });

        // Set a custom timeout for the request
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);

    }

    public void springApiRequest() {

        String apiUrl = "http://192.168.0.117:8080/getallproduct";

        List<Product> arrayList=new ArrayList<>();
        Toast.makeText(getApplicationContext(), "Request Send Successfully", Toast.LENGTH_SHORT).show();

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, apiUrl, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Process the JSON response here
                        // The 'response' variable contains the JSON array
                        Gson gson = new Gson();
                        List<Product> postsList = Arrays.asList(gson.fromJson(response.toString(),     //if the api return list of item that time used
                                Product[].class));
                        arrayList.addAll(postsList);
//                        Product product = gson.fromJson(response.toString(), Product.class);   //if the only one object return api that time used
//                        arrayList.add(product);
                       print(arrayList);
                        Toast.makeText(IntentExample.this, "size : "+arrayList.size(), Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("dkerror",""+error);
                        // Handle errors
                        if (error.networkResponse == null) {
                            // Network error, possibly a timeout
                            showToast(getApplicationContext(), "Network request timed out");
                        } else {
                            // Other types of errors, handle accordingly
                            showToast(getApplicationContext(), "Error: " + error.getMessage());
                        }
                    }
                });

        // Set a custom timeout for the request
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonArrayRequest);

    }

    private void print(List<Product> arrayList) {
        for (int i = 0; i < arrayList.size(); i++) {
            Log.d("dk"+i,arrayList.get(i).toString());

        }

        String image_url="http://192.168.0.117:8080/images/"+arrayList.get(2).getImgname();
//        int desireWidth=170;
//        int desireHeight=170;
        Picasso.get().
                load(image_url)
//                .resize(desireWidth,desireHeight)
//                .centerCrop()
                .into(img);
    }

    private void showToast(final Context context, final String message) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        }, 0); // Show the Toast immediately
    }
}