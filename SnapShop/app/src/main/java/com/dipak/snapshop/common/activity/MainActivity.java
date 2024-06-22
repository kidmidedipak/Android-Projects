package com.dipak.snapshop.common.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dipak.snapshop.R;
import com.dipak.snapshop.admin.activity.Admin;
import com.dipak.snapshop.common.activity.config.UrlConfig;
import com.dipak.snapshop.common.activity.model.Customer;
import com.dipak.snapshop.user.UserActivity;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Button admin,user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!isAlreadyLogin())
        {
        Intent intent=new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        }

    }

    boolean isAlreadyLogin() {
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String email = prefs.getString("email", null);
        String password = prefs.getString("password", null);

        if(email==null    || password==null  )
        {
            return false;
        }

      loginUser(email,password);
        return true;
    }

    public void loginUser(String email, String pass) {


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = UrlConfig.BASE_URL+"auth";

        JSONObject postData = new JSONObject();
        try {
            postData.put("email", email);
            postData.put("password", pass);
            // Add other parameters if needed
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle response
                        Log.d("Response", response.toString());
                        Gson gson = new Gson();
                        Customer obj = gson.fromJson(response.toString(), Customer.class);
                        if (obj != null) {
                            SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("email", email);
                            editor.putString("password", pass);
                            editor.putString("id", String.valueOf(obj.getId()));
                            editor.apply();
                            checkUserRole(obj);
                        } else {
                            Toast.makeText(MainActivity.this, "Invalid user", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        Log.e("Error", error.toString());
                    }
                });

        requestQueue.add(jsonObjectRequest);

    }

    private void checkUserRole(Customer obj) {
        Intent it=null;
        if(obj.getRole().trim().equalsIgnoreCase("user")) {
            it = new Intent( this, UserActivity.class);

        }else if(obj.getRole().trim().equalsIgnoreCase("admin")) {
            it = new Intent( this, Admin.class);
        }
        it.putExtra("email", obj.getEmail());
        it.putExtra("name", obj.getName());
        startActivity(it);
    }


}