package com.infosolution.dev.tfc.business;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginBusinessActivity extends AppCompatActivity {

    private EditText etusername,etpassword;
    private Button btnsignin;
    private  String email,password;
    private ProgressDialog pdLoading;
    private TextView tvsignup;
    private String ResIdB,EmailB,Phone1B,Phone2B;

    private static final String email_pattern =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_business);
        etusername=findViewById(R.id.et_usernamebusi);
        etpassword=findViewById(R.id.et_passwordbusi);
        btnsignin=findViewById(R.id.btn_signinbusi);
        tvsignup=findViewById(R.id.tv_signupp);


        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email=etusername.getText().toString().trim();
                password=etpassword.getText().toString().trim();


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
                }else {
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
                        Log.e("pppppppppp", response);
                     //   Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                        pdLoading.dismiss();

                        try {


                            JSONObject result = new JSONObject(response);
                            JSONArray routearray = result.getJSONArray("record");
                            for (int i = 0; i < routearray.length(); i++) {

                                ResIdB = routearray.getJSONObject(i).getString("res_id");
                                EmailB = routearray.getJSONObject(i).getString("email");
                                Phone1B = routearray.getJSONObject(i).getString("phone1");
                                Phone2B = routearray.getJSONObject(i).getString("phone2");

                                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("LogindataB", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                  editor.putString("resid", ResIdB);
                                  editor.putString("emailid", EmailB);
                                  editor.putString("phone1", Phone1B);
                                  editor.putString("phone2", Phone2B);
                                editor.commit();

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
//                        pdLoading.dismiss();
                        Intent intent = new Intent(LoginBusinessActivity.this,BusinessNavigation.class);
                        startActivity(intent);

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

                params.put("email",email );
                params.put("password",password );



                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    }



