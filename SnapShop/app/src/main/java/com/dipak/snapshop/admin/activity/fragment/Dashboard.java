package com.dipak.snapshop.admin.activity.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.dipak.snapshop.R;
import com.dipak.snapshop.admin.activity.Admin;
import com.dipak.snapshop.admin.activity.adapter.CategoryAdapter;
import com.dipak.snapshop.admin.activity.adapter.ProductAdapter;
import com.dipak.snapshop.common.activity.config.UrlConfig;
import com.dipak.snapshop.common.activity.model.Category;
import com.dipak.snapshop.common.activity.model.Product;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.Arrays;
import java.util.List;

public class Dashboard extends Fragment {

    TextView tv_product_cnt,tv_category_cnt ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_dashboard, container, false);

        if (getActivity() instanceof Admin) {
            ((Admin) getActivity()).setToolbarTitle("Dashboard");
        }
        tv_product_cnt=view.findViewById(R.id.tv_product_cnt);
        tv_category_cnt=view.findViewById(R.id.tv_category_cnt);

        getAllProducts();
        getAllCategorys();
        return view;
    }

    private void getAllCategorys() {
        String url= UrlConfig.BASE_URL+"category/getall";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Gson gson=new Gson();
                        List<Category> categorysList= Arrays.asList(gson.fromJson(response.toString(),Category[].class));
                        tv_category_cnt.setText(categorysList.size()+"");

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error",error.getMessage());
                    }
                });

        requestQueue.add(jsonArrayRequest);

    }

    private void getAllProducts() {

        String url= UrlConfig.BASE_URL+"getallproduct";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Gson gson=new Gson();
                        List<Product> productList= Arrays.asList(gson.fromJson(response.toString(),Product[].class));
                        tv_product_cnt.setText(productList.size()+"");

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error",error.getMessage());
                    }
                });

        requestQueue.add(jsonArrayRequest);


    }
}