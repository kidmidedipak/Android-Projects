package com.dipak.snapshop.common.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

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

public class LoginActivity extends AppCompatActivity {

    EditText emailTxt,passTxt;
    AppCompatButton loginBtn;
    TextView createBtn;
    private CheckBox showPasswordCheckBox;
//    MyDB myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        emailTxt=findViewById(R.id.editTextEmail);
        passTxt=findViewById(R.id.editTextPassword);
        loginBtn=findViewById(R.id.buttonLogin);
        createBtn=findViewById(R.id.createNewAc);

        showPasswordCheckBox = findViewById(R.id.showPasswordCheckBox);
        passTxt.setTransformationMethod(new PasswordTransformationMethod());
        showPasswordCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Toggle password visibility based on the checkbox state
            togglePasswordVisibility(isChecked);
        });



        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email=emailTxt.getText().toString();
                String pass=passTxt.getText().toString();
                if(email.trim()!=null || pass.trim()!=null) {
                loginUser(email,pass);
                }else{
                    showErrorDialog("All fields are required");
                }
            }
        });

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it=new Intent(LoginActivity.this, Register.class);
                startActivity(it);
            }
        });
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
                                Log.d("share","inside of shared pref");
                                editor.apply();
                                checkUserRole(obj);
                            } else {
                                showErrorDialog("Invalid user");
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
        Toast.makeText(this, obj+"", Toast.LENGTH_SHORT).show();
        Intent it=null;
        if(obj.getRole().trim().equalsIgnoreCase("user")) {
             it = new Intent(LoginActivity.this, UserActivity.class);

        }else if(obj.getRole().trim().equalsIgnoreCase("admin")) {
             it = new Intent(LoginActivity.this, Admin.class);
        }
        it.putExtra("email", obj.getEmail());
        it.putExtra("name", obj.getName());
        startActivity(it);

    }


    private void togglePasswordVisibility(boolean showPassword) {

        if (showPassword) {
            // Show password
            passTxt.setTransformationMethod(null);
        } else {
            // Hide password
            passTxt.setTransformationMethod(new PasswordTransformationMethod());
        }

        // Move the cursor to the end of the text to maintain the cursor position
        passTxt.setSelection(passTxt.getText().length());

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    private  void showErrorDialog(String msg ){
        LinearLayout linearLayout=findViewById(R.id.successConsLayout);
        View view= LayoutInflater.from(LoginActivity.this).inflate(R.layout.success_dialog,linearLayout );
        Button successDone=view.findViewById(R.id.successDone);
        ImageView errorIcon=view.findViewById(R.id.successImage);
        TextView textView=view.findViewById(R.id.successdesc);
        TextView dialogTitle=view.findViewById(R.id.successTitle);
        dialogTitle.setText("Invalid Input");
        errorIcon.setImageResource(R.drawable.error_icon);
        textView.setText(msg);
        AlertDialog.Builder builder=new AlertDialog.Builder(LoginActivity.this);
        builder.setView(view);
        final AlertDialog alertDialog=builder.create();

        successDone.findViewById(R.id.successDone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialog.dismiss();
            }
        });

        if(alertDialog.getWindow()!=null)
        {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }
}