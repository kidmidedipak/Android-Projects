
package com.dipak.snapshop.common.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dipak.snapshop.R;
import com.dipak.snapshop.common.activity.model.Customer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class Register extends AppCompatActivity {

    AppCompatButton btn;
    TextView loginBtn,tv_selectedDate;
    EditText nameTxt,emailTxt,phoneTxt,passTxt;
    private CheckBox showPasswordCheckBox;

    RadioButton rb_male,rb_female;

    ImageView iv_dobPicker;
    static String gender="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initializeData();
        getGender();
        passTxt.setTransformationMethod(new PasswordTransformationMethod());
        showPasswordCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Toggle password visibility based on the checkbox state
            togglePasswordVisibility(isChecked);
        });


        iv_dobPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=nameTxt.getText().toString();
                String email=emailTxt.getText().toString();
                String phone=phoneTxt.getText().toString();
                String pass=passTxt.getText().toString();
                String dob=tv_selectedDate.getText().toString();

                if( name.trim().isEmpty() || email.trim().isEmpty() ||
                        phone.trim().isEmpty() || pass.trim().isEmpty())
                {
                    showErrorDialog("All fields are required");

                }else if(fieldsValidate(email,phone)){

//                    if(myDB.loginValid(email, pass)!=null)
//                    {
//                        Toast.makeText(Register.this, "Account already exists", Toast.LENGTH_SHORT).show();
//                    }else {
//                        //send register value to db
//                        Toast.makeText(Register.this, "Gender: "+gender, Toast.LENGTH_SHORT).show();
//
//                        boolean b = registerApi(new Customer(0,name,email,phone,gender,dob,pass,"User"));
//                        if (b) {
//                            showSuccessDialog("User registered successfully...!!");
//                            clearFields();
//                        } else {
//                            Toast.makeText(Register.this, "Something wrong", Toast.LENGTH_SHORT).show();
//                        }
//                    }
                }
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it=new Intent(Register.this   , LoginActivity.class);
                startActivity(it);
            }
        });
    }

    private boolean registerApi(Customer user) {
        JSONObject requestBody = new JSONObject();
        try {

            requestBody.put("id", user.getId());
            requestBody.put("name", user.getName());
            requestBody.put("email", user.getEmail());
            requestBody.put("contact", user.getContact());
            requestBody.put("gender", user.getGender());
            requestBody.put("dob", user.getDob());
            requestBody.put("password", user.getPassword());
            requestBody.put("role", user.getRole());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String url = "http://192.168.0.106:8080/register";

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, url, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response!=null) {
                                showSuccessDialog("User registered successfully...!!");
                                clearFields();
                            } else {
                                Toast.makeText(Register.this, "Something wrong", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            Log.d("dk",e+"");
                            Toast.makeText(getApplicationContext(), "exception", Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("dk",error+"");
                        Toast.makeText(getApplicationContext(), "exception", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                }
        );

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);


        return true;
    }

    private void initializeData() {
        nameTxt=findViewById(R.id.editTextName);
        emailTxt=findViewById(R.id.editTextEmail);
        phoneTxt=findViewById(R.id.editTextPhone);
        tv_selectedDate=findViewById(R.id.tv_selectedDate);
        passTxt=findViewById(R.id.editTextPassword);
        rb_male=findViewById(R.id.rb_male);
        rb_female=findViewById(R.id.rb_female);
        iv_dobPicker=findViewById(R.id.iv_dobPicker);
        showPasswordCheckBox = findViewById(R.id.showPasswordCheckBox);
        loginBtn=findViewById(R.id.login);
        btn=findViewById(R.id.buttonSignUp);
    }

    private void showDatePickerDialog() {
        // Get the current date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create a DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        // Handle the selected date
                        String selectedDate = "Selected Date: " + dayOfMonth + "-" + (month + 1) + "-" + year;
                        // You can perform further actions with the selected date
                        tv_selectedDate.setText(selectedDate);
                    }
                },
                year,
                month,
                day);

        // Show the DatePickerDialog
        datePickerDialog.show();
    }

    private String getGender() {

        rb_male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rb_male.isChecked())
                {
                    gender= "Male";
                }
            }
        });
        rb_female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rb_male.isChecked())
                {
                    gender= "Female";
                }
            }
        });

        return gender;
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

    private boolean fieldsValidate(String email, String phone) {
//        final String EMAIL_PATTERN =
//                "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
//        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
//        Matcher matcher = pattern.matcher(email);
//        if(matcher.matches())
//        {
//            if(phone.trim().length()==10)
//            {
//                return true;
//            }else{
//                showErrorDialog("Enter valid 10 digit mobile number") ;
//            return false;
//            }
//        }else{
//            showErrorDialog("Enter valid email") ;
//
//         return false;
//        }
        return true;
    }

    public void clearFields(){
        nameTxt.setText("");
        emailTxt.setText("");
        phoneTxt.setText("");
        passTxt.setText("");
    }

    private  void showSuccessDialog(String msg ){
        LinearLayout linearLayout=findViewById(R.id.successConsLayout);
        View view= LayoutInflater.from(Register.this).inflate(R.layout.success_dialog,linearLayout );
        Button successDone=view.findViewById(R.id.successDone);
        TextView textView=view.findViewById(R.id.successdesc);
        textView.setText(msg);
        AlertDialog.Builder builder=new AlertDialog.Builder(Register.this);
        builder.setView(view);
        final AlertDialog alertDialog=builder.create();

        successDone.findViewById(R.id.successDone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it=new Intent(Register.this   ,LoginActivity.class);
                startActivity(it);
                alertDialog.dismiss();
            }
        });

        if(alertDialog.getWindow()!=null)
        {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }


    private  void showErrorDialog(String msg ){
            LinearLayout linearLayout=findViewById(R.id.successConsLayout);
            View view= LayoutInflater.from(Register.this).inflate(R.layout.success_dialog,linearLayout );
            Button successDone=view.findViewById(R.id.successDone);
            ImageView errorIcon=view.findViewById(R.id.successImage);
            TextView textView=view.findViewById(R.id.successdesc);
            TextView dialogTitle=view.findViewById(R.id.successTitle);
            dialogTitle.setText("Invalid Input");
            errorIcon.setImageResource(R.drawable.error_icon);
            textView.setText(msg);
            AlertDialog.Builder builder=new AlertDialog.Builder(Register.this);
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