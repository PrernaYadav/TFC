package com.infosolution.dev.tfc.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.infosolution.dev.tfc.Class.ConfigInfo;
import com.infosolution.dev.tfc.R;
import com.infosolution.dev.tfc.business.LoginBusinessActivity;
import com.infosolution.dev.tfc.user.Navigation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginMailActivity extends AppCompatActivity {

    private EditText etusername, etpassword;
    private Button btnsignin;
    private String email, password;
    private ProgressDialog pdLoading;
    private TextView tvsignup,tvfrgtpass;
    //  String userID,Email;

    private SharedPreferences sh_Pref;
    private SharedPreferences.Editor editor;
    private static final String IS_LOGIN = "IsLoggedIn";
    int PRIVATE_MODE = 0;


    private static final String email_pattern =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_mail);
        etusername = findViewById(R.id.et_username);
        etpassword = findViewById(R.id.et_password);
        btnsignin = findViewById(R.id.btn_signin);
        tvsignup = findViewById(R.id.tv_signupp);
        tvfrgtpass = findViewById(R.id.tv_forgotpass);


        Typeface typefaceregular = Typeface.createFromAsset(getAssets(), "font/tahoma.ttf");
        Typeface typefacebold = Typeface.createFromAsset(getAssets(), "font/tahomabd.ttf");

        etusername.setTypeface(typefaceregular);
        etpassword.setTypeface(typefaceregular);
        btnsignin.setTypeface(typefacebold);
        tvsignup.setTypeface(typefaceregular);
        tvfrgtpass.setTypeface(typefaceregular);

        tvfrgtpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(LoginMailActivity.this,ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });


        sh_Pref = getSharedPreferences("Login Credentials", PRIVATE_MODE);
        boolean check = sh_Pref.getBoolean(IS_LOGIN, false);
        if (check) {
            Intent intent = new Intent(this, Navigation.class);
            startActivity(intent);
            finish();
        }


        tvsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginMailActivity.this, SignupUserActivity.class);
                startActivity(intent);
            }
        });
        BtnClick();


    }

    public void BtnClick() {

        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = etusername.getText().toString().trim();
                password = etpassword.getText().toString().trim();


                Matcher matcherObj = Pattern.compile(email_pattern).matcher(email);

                if (!matcherObj.matches()) {
                    etusername.setError("Invalid Email");
                } else if (etpassword.getText().toString().trim().length() < 0) {
                    etpassword.setError("Password Length is short");
                } else {
                    Login();
                }


            }
        });

    }

    private void Login() {


        pdLoading = new ProgressDialog(LoginMailActivity.this);
        //this method will be running on UI thread
        pdLoading.setMessage("\tLoading...");
        pdLoading.setCancelable(false);
        pdLoading.show();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigInfo.login,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.i("sigresss",""+response.toString());

                        pdLoading.dismiss();

                        try {
                            JSONObject jsono = new JSONObject(response);
                            String status = jsono.getString("status");

                            if (status.equals("Success"))
                            {

                                JSONArray jarray = jsono.getJSONArray("record");
                                for (int i = 0; i < jarray.length(); i++) {
                                    JSONObject object = jarray.getJSONObject(i);
                                    String userID = object.getString("userid");
                                    String Email = object.getString("email");
                                    String Username = object.getString("name");
                                    String Phone = object.getString("phone");
                                    String Profileimage = object.getString("Profile");
                                    String loginstatus = object.getString("login_status");
                                    
                                    if (loginstatus.equals("0")){
                                        Toast.makeText(LoginMailActivity.this, "Please verify your email id, We have sent a verification mail on your registered mail id on signup process.", Toast.LENGTH_SHORT).show();
                                    }else {
                                        sharedPreferences();
                                        Intent intent=new Intent(LoginMailActivity.this,Navigation.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);

                                    }


                                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("useriddsign", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("userid", userID);
                                    editor.putString("email", Email);
                                    editor.putString("usernamesign", Username);
                                    editor.putString("phone", Phone);
                                    editor.putString("profileimage", Profileimage);
                                    editor.commit();


                                }


                                // Toast.makeText(LoginMailActivity.this, "successs", Toast.LENGTH_LONG).show();

                            }
                            else
                            {
                                  Toast.makeText(LoginMailActivity.this,"Please Check Your Login Credential.", Toast.LENGTH_LONG).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }






                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pdLoading.dismiss();

                        Toast.makeText(LoginMailActivity.this, error.toString(), Toast.LENGTH_LONG).show();

//                        pdLoading.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();

                params.put("email", email);
                params.put("password", password);
                Log.i("userpar",""+params);


                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }




    public void sharedPreferences() {

        sh_Pref = getSharedPreferences("Login Credentials", PRIVATE_MODE);
        editor = sh_Pref.edit();
        editor.putBoolean(IS_LOGIN, true);
        editor.putString("Username", email);
        editor.putString("Password", password);
        editor.commit();
    }


}
