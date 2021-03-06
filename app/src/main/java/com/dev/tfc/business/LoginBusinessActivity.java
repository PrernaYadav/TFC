package com.dev.tfc.business;

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
import com.dev.tfc.Class.ConfigInfo;
import com.dev.tfc.R;
import com.dev.tfc.activities.ForgotPasswordActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginBusinessActivity extends AppCompatActivity {

    private EditText etusername, etpassword;
    private Button btnsignin;
    private String email, password;
    private ProgressDialog pdLoading;
    private TextView tvsignup, tvforgtpass;
  //  private String ResIdB, EmailB, Phone1B, Phone2B, ProImg, Nameee;

    private SharedPreferences sh_Preff;
    private SharedPreferences.Editor editorr;
    private static final String IS_LOGINN = "IsLoggedInn";
    private int PRIVATE_MODE = 0;


    private static final String email_pattern =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_business);


        Typeface typefaceregular = Typeface.createFromAsset(getAssets(), "font/tahoma.ttf");
        Typeface typefacebold = Typeface.createFromAsset(getAssets(), "font/tahomabd.ttf");

        sh_Preff = getSharedPreferences("Login Busi", PRIVATE_MODE);
        boolean check = sh_Preff.getBoolean(IS_LOGINN, false);
        if (check) {
            Intent intent = new Intent(this, BusinessNavigation.class);
            startActivity(intent);
            finish();
        }


        etusername = findViewById(R.id.et_usernamebusi);
        etpassword = findViewById(R.id.et_passwordbusi);
        btnsignin = findViewById(R.id.btn_signinbusi);
        tvsignup = findViewById(R.id.tv_signupbusi);
        tvforgtpass = findViewById(R.id.tv_forgotpassbusi);


        tvforgtpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(LoginBusinessActivity.this,ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        etusername.setTypeface(typefaceregular);
        etpassword.setTypeface(typefaceregular);
        btnsignin.setTypeface(typefacebold);
        tvsignup.setTypeface(typefacebold);
        tvforgtpass.setTypeface(typefacebold);


        tvsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginBusinessActivity.this, SignupBusiActivity.class);
                startActivity(intent);
            }
        });


        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = etusername.getText().toString().trim();
                password = etpassword.getText().toString().trim();


/*                 pdLoading = new ProgressDialog(LoginMailActivity.this);
                //this method will be running on UI thread
                pdLoading.setMessage("\tLoading...");
                pdLoading.setCancelable(false);
                pdLoading.show();*/

                Matcher matcherObj = Pattern.compile(email_pattern).matcher(email);

                if (!matcherObj.matches()) {
                    etusername.setError("Invalid Email");
                } else if (etpassword.getText().toString().trim().length() < 0) {
                    etpassword.setError("Password Length is short");
                } else {
                    Loginbusi();
                }


            }
        });
    }

    private void Loginbusi() {

        pdLoading = new ProgressDialog(LoginBusinessActivity.this);
        //this method will be running on UI thread
        pdLoading.setMessage("\tLoading...");
        pdLoading.setCancelable(false);
        pdLoading.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigInfo.loginbusi,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        sharedPreferencess();
                        Log.e("pppppppppp", response);

                        pdLoading.dismiss();


                        try {
                            JSONObject result = new JSONObject(response);
                            String status = result.getString("status");

                            if (status.equals("Success")) {


                                JSONArray routearray = result.getJSONArray("record");
                                for (int i = 0; i < routearray.length(); i++) {

                                    String   ResIdB = routearray.getJSONObject(i).getString("res_id");
                                    String  EmailB = routearray.getJSONObject(i).getString("email");
                                    String   Phone1B = routearray.getJSONObject(i).getString("phone1");
                                    String   Phone2B = routearray.getJSONObject(i).getString("phone2");
                                    String  ProImg = routearray.getJSONObject(i).getString("logo");
                                    String   Nameee = routearray.getJSONObject(i).getString("name");
                                    String store = routearray.getJSONObject(i).getString("store_name");
                                    String Country = routearray.getJSONObject(i).getString("fb_url");

                                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("LogindataB", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("resid", ResIdB);
                                    editor.putString("emailid", EmailB);
                                    editor.putString("phone1", Phone1B);
                                    editor.putString("phone2", Phone2B);
                                    editor.putString("proimg", ProImg);
                                    editor.putString("name", Nameee);
                                    editor.putString("store", store);
                                    editor.putString("country", Country);
                                    editor.commit();


                                    SharedPreferences pref = getApplicationContext().getSharedPreferences("editpro", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editorr = pref.edit();
                                    editorr.putString("emailidd", EmailB);
                                    editorr.putString("phone11", Phone1B);
                                    editorr.putString("phone22", Phone2B);
                                    editorr.putString("proimgg", ProImg);
                                    editorr.putString("namee", Nameee);
                                    editorr.putString("storee", store);
                                    editorr.putString("residd", ResIdB);
                                    editorr.commit();

                                }
                                Intent intent = new Intent(LoginBusinessActivity.this, BusinessNavigation.class);
                                startActivity(intent);
                                finish();
                                //   Toast.makeText(LoginBusinessActivity.this, "successs", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(LoginBusinessActivity.this, "Please Check Your Login Credential.", Toast.LENGTH_LONG).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }




                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        Toast.makeText(LoginBusinessActivity.this, error.toString(), Toast.LENGTH_LONG).show();

                        pdLoading.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();

                params.put("email", email);
                params.put("password", password);


                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void sharedPreferencess() {

        sh_Preff = getSharedPreferences("Login Busi", PRIVATE_MODE);
        editorr = sh_Preff.edit();
        editorr.putBoolean(IS_LOGINN, true);
        editorr.putString("Username", email);
        editorr.putString("Password", password);
        editorr.commit();
    }

}



