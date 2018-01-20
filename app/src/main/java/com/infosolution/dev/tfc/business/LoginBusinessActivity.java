package com.infosolution.dev.tfc.business;

import android.app.ProgressDialog;
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
       /* tvsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(LoginBusinessActivity.this,SignupUserActivity.class);
                startActivity(intent);
            }
        });*/

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


        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigInfo.loginbusi,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {



                        Log.e("pppppppppp", response);
                        Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
//                        pdLoading.dismiss();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {



                        Toast.makeText(LoginBusinessActivity.this, error.toString(), Toast.LENGTH_LONG).show();

//                        pdLoading.dismiss();
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



