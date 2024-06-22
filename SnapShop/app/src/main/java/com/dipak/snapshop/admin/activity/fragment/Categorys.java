package com.dipak.snapshop.admin.activity.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dipak.snapshop.R;
import com.dipak.snapshop.admin.activity.Admin;
import com.dipak.snapshop.admin.activity.adapter.CategoryAdapter;
import com.dipak.snapshop.common.activity.MainActivity;
import com.dipak.snapshop.common.activity.config.UrlConfig;
import com.dipak.snapshop.common.activity.model.Category;
import com.dipak.snapshop.common.activity.model.Customer;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Categorys extends Fragment implements CategoryAdapter.CategoryService {


    View view;
    ListView listView;
    String radioSelected = "";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         view= inflater.inflate(R.layout.fragment_categorys, container, false);
        listView=view.findViewById(R.id.lv_product);

        if (getActivity() instanceof Admin) {
            ((Admin) getActivity()).setToolbarTitle("Categories");
        }

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUpdateDialogForm(null);
            }
        });
        getAllCategorys();
        return view;
    }

    private void addUpdateDialogForm(Category category) {
        Dialog dialog = new Dialog(getContext());
        // set custom dialog
        dialog.setContentView(R.layout.add_category_popup);

        ImageView iv_cancel = dialog.findViewById(R.id.iv_cancel);
        AppCompatButton btn_add = dialog.findViewById(R.id.btn_add);

        TextInputEditText et_cname = dialog.findViewById(R.id.et_cname);
        TextInputEditText et_description = dialog.findViewById(R.id.et_description);

        RadioGroup rg_degree=dialog.findViewById(R.id.rg_degree);
        RadioButton active=dialog.findViewById(R.id.rb_active);
        RadioButton rb_deActive=dialog.findViewById(R.id.rb_deActive);
        if(category==null)
        {
            btn_add.setText("Add");
            active.setChecked(true);
            radioSelected="Active";
        }else  {
            if(category.getStatus().trim().equalsIgnoreCase("active"))
            {
                active.setChecked(true);
                radioSelected="Active";
            }else{
                rb_deActive.setChecked(true);
                radioSelected="Deactive";
            }
            et_cname.setText(category.getCname());
            et_description.setText(category.getDescription());
            btn_add.setText("Update");

        }


        rg_degree.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(active.isChecked()){
                    radioSelected = "Active";
                } else if (rb_deActive.isChecked()) {
                    radioSelected = "Deactive";
                }
                Toast.makeText(getContext(), radioSelected, Toast.LENGTH_SHORT).show();
            }
        });

        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cname=et_cname.getText().toString();
                String description=et_description.getText().toString();

                if(category==null) {
                    addCategory(new Category(0, cname, description, radioSelected));
                }else{
                    editCategoryapi(new Category(category.getId(), cname, description, radioSelected));
                }
                dialog.dismiss();
            }
        });

        // set custom height and width
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.96);
        dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);

        // set transparent background
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(true);

        // show dialog
        dialog.show();
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

                        CategoryAdapter categoryAdapter=new CategoryAdapter(requireContext(),Categorys.this,categorysList);
                        listView.setAdapter(categoryAdapter);

                        listView.setDivider(null);  // or setDivider(new ColorDrawable(Color.TRANSPARENT));
                        listView.setDividerHeight(0);
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

    public void addCategory(Category category) {
        Gson gson = new Gson();
        String requestBody = gson.toJson(category);

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String url = UrlConfig.BASE_URL + "category/add";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle response
                        if (Boolean.parseBoolean(response)) {
                            // Category added successfully
                            Toast.makeText(getContext(), "Category added successfully", Toast.LENGTH_SHORT).show();
                            getAllCategorys();
                        } else {
                            // Category addition failed
                            Toast.makeText(getContext(), "Failed to add category", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        Log.e("Error", error.toString());
                    }
                }) {
            @Override
            public byte[] getBody() {
                return requestBody.getBytes();
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        requestQueue.add(stringRequest);
    }

    @Override
    public void onDelete(int id) {
        deleteCategoryApi(id);
    }


    @Override
    public void onEdit(Category category) {
        addUpdateDialogForm(category);

    }

    private void editCategoryapi(Category category) {
        Gson gson = new Gson();
        String requestBody = gson.toJson(category);

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String url = UrlConfig.BASE_URL + "category/update";

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle response
                        if (Boolean.parseBoolean(response)) {
                            // Category added successfully
                            Toast.makeText(getContext(), "Category Updated successfully", Toast.LENGTH_SHORT).show();
                            getAllCategorys();
                        } else {
                            // Category addition failed
                            Toast.makeText(getContext(), "Failed to update category", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        Log.e("Error", error.toString());
                    }
                }) {
            @Override
            public byte[] getBody() {
                return requestBody.getBytes();
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void deleteCategoryApi(int id) {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String url = UrlConfig.BASE_URL+"category/delete/"+id;

        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle response
                        if (Boolean.parseBoolean(response)) {
                            // Category added successfully
                            Toast.makeText(getContext(), "Category Delete successfully", Toast.LENGTH_SHORT).show();
                            getAllCategorys();
                        } else {
                            // Category addition failed
                            Toast.makeText(getContext(), "Failed to Delete category", Toast.LENGTH_SHORT).show();
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

        requestQueue.add(stringRequest);

    }

}