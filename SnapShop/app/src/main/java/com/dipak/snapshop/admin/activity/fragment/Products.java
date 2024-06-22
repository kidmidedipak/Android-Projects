package com.dipak.snapshop.admin.activity.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
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
import com.android.volley.toolbox.Volley;
import com.dipak.snapshop.R;
import com.dipak.snapshop.admin.activity.Admin;
import com.dipak.snapshop.admin.activity.adapter.ProductAdapter;
import com.dipak.snapshop.common.activity.config.UrlConfig;
import com.dipak.snapshop.common.activity.model.Category;
import com.dipak.snapshop.common.activity.model.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Products extends Fragment {

    View view;
    ListView listView;
    String radioSelected="";
    TextInputEditText et_name,et_description,et_brand_name,et_price,et_quantity ;

    List<Category> categorysList=new ArrayList<>();
    Category category=null;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int RESULT_OK = -1;
    private ImageView imageView;
    Bitmap bitmap;
    private static final String SERVER_URL = UrlConfig.BASE_URL+"upload";



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view= inflater.inflate(R.layout.fragment_products, container, false);
         listView=view.findViewById(R.id.lv_product);
        if (getActivity() instanceof Admin) {
            ((Admin) getActivity()).setToolbarTitle("Products");
        }
        getAllProducts();


        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUpdateDialogForm(null);
            }
        });

        return view;
    }

    private void addUpdateDialogForm(Product product) {

        Dialog dialog = new Dialog(getContext());

        // set custom dialog
        dialog.setContentView(R.layout.add_product_popup);

        ImageView iv_cancel = dialog.findViewById(R.id.iv_cancel);
        AppCompatButton btn_add = dialog.findViewById(R.id.btn_add);

        et_name = dialog.findViewById(R.id.et_name);
        et_description = dialog.findViewById(R.id.et_description);
        et_brand_name = dialog.findViewById(R.id.et_brand_name);
        et_price = dialog.findViewById(R.id.et_price);
        et_quantity = dialog.findViewById(R.id.et_quantity);
        imageView = dialog.findViewById(R.id.imageView);
        Button btn_image = dialog.findViewById(R.id.btn_image);

        AutoCompleteTextView act_category=dialog.findViewById(R.id.act_category);

        RadioGroup rg_degree=dialog.findViewById(R.id.rg_degree);
        RadioButton active=dialog.findViewById(R.id.rb_active);
        RadioButton rb_deActive=dialog.findViewById(R.id.rb_deActive);
        ArrayList<String> cateNameList=CateNameList();
        ArrayAdapter adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, cateNameList);
        act_category.setAdapter(adapter);

        btn_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

            }
        });



        act_category.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().equalsIgnoreCase("")) {

                    category = getSelectedcategory(charSequence);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        if(product==null)
        {
            btn_add.setText("Add");
            active.setChecked(true);
            radioSelected="Active";
        }else  {
            btn_add.setText("Update");
            if(product.getStatus().trim().equalsIgnoreCase("active"))
            {
                active.setChecked(true);
                radioSelected="Active";
            }else{
                rb_deActive.setChecked(true);
                radioSelected="Deactive";
            }

            et_name.setText(product.getName());
            et_description.setText(product.getDescription());
            et_brand_name.setText(product.getBrand());
            et_price.setText(product.getPrice()+"");
            et_quantity.setText(product.getQuantity()+"");


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
                String cname=et_name.getText().toString();
                String description=et_description.getText().toString();
                String pname=et_name.getText().toString();
                String brand=et_brand_name.getText().toString();
                String quantityStr=et_quantity.getText().toString();
                String status=radioSelected;
                int quantity=Integer.parseInt(quantityStr);
                 String priceyStr=et_price.getText().toString();
                int price=Integer.parseInt(priceyStr);

                if(product==null) {
                    addProduct(new Product(0,pname,  description,brand , null,
                     quantity,category ,  price,  status), bitmap );

                }else{
                    Toast.makeText(getContext(), "Update here ", Toast.LENGTH_SHORT).show();
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                bitmap = getBitmapFromUri(uri);
                imageView.setImageBitmap(bitmap);
                Toast.makeText(requireContext(), "Selectd image "+bitmap, Toast.LENGTH_SHORT).show();

                imageView.setImageURI(uri);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(requireContext(), "Failed to load image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        InputStream inputStream = requireContext().getContentResolver().openInputStream(uri);
        if (inputStream != null) {
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
            return bitmap;
        } else {
            throw new FileNotFoundException("Unable to open input stream for URI: " + uri);
        }
    }

    private Category getSelectedcategory(CharSequence charSequence) {
        for (Category obj:categorysList)
        {
            if(obj.getCname().trim().contains(charSequence))
            {
               return obj;
            }
        }
        return null;
    }

    private ArrayList CateNameList() {
        ArrayList<String> al=new ArrayList<>();
        for(Category obj:categorysList)
        {
            al.add(obj.getCname());
        }
        return al;
    }

    private void getAllCategorys() {
        String url= UrlConfig.BASE_URL+"category/getall";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Gson gson=new Gson();
                        categorysList.clear();
                     categorysList= Arrays.asList(gson.fromJson(response.toString(),Category[].class));

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

    public void addProduct(Product product,Bitmap bitmap) {

        Toast.makeText(getContext(), "Write add product api", Toast.LENGTH_SHORT).show();
    }

    // Convert bitmap to base64 string
    private static String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
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

                        ProductAdapter productAdapter=new ProductAdapter(requireContext(),productList);
                        listView.setAdapter(productAdapter);

                        listView.setDivider(null);  // or setDivider(new ColorDrawable(Color.TRANSPARENT));
                        listView.setDividerHeight(0);
                        getAllCategorys();

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