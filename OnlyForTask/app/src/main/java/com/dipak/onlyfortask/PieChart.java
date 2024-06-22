package com.dipak.onlyfortask;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dipak.onlyfortask.adapter.PieChartView;
import com.dipak.onlyfortask.model.Batch;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PieChart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);
        float[] data = {30f, 40f, 30f}; // Example data
        int[] colors = {Color.RED, Color.GREEN, Color.BLUE}; // Example colors

        PieChartView pieChartView = findViewById(R.id.pieChart);
        pieChartView.setData(data, colors);
    }

    private class ExApi extends AsyncTask<String, Batch,Void>{

        @Override
        protected Void doInBackground(String... strings) {

            RequestQueue requestQueue= Volley.newRequestQueue(PieChart.this);
            StringRequest stringRequest=new StringRequest(Request.Method.GET, "https://lksjfla.com",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            JSONObject person=null;

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {


                            error.printStackTrace();
                        }
                    }
                    ) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params=new HashMap<>();

                    return params;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(stringRequest);

            return null;
        }
    }
}