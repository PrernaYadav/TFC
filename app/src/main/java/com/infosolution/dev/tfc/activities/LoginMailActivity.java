package com.infosolution.dev.tfc.activities;

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
import com.infosolution.dev.tfc.user.Navigation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginMailActivity extends AppCompatActivity {

    private EditText etusername,etpassword;
    private Button btnsignin;
    private  String email,password;
    private ProgressDialog pdLoading;
    private TextView tvsignup;
  //  String userID,Email;

    SharedPreferences sh_Pref;
    SharedPreferences.Editor editor;

    private static final String IS_LOGIN = "IsLoggedIn";
    int PRIVATE_MODE = 0;




    private static final String email_pattern =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_mail);
        etusername=findViewById(R.id.et_username);
        etpassword=findViewById(R.id.et_password);
        btnsignin=findViewById(R.id.btn_signin);
        tvsignup=findViewById(R.id.tv_signupp);


         sh_Pref = getSharedPreferences("Login Credentials", PRIVATE_MODE);
        boolean check = sh_Pref.getBoolean(IS_LOGIN, false);
        if(check){
            Intent intent = new Intent(this, Navigation.class);
            startActivity(intent);
            finish();
        }




        tvsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(LoginMailActivity.this,SignupUserActivity.class);
                startActivity(intent);
            }
        });
        BtnClick();


    }

    public void BtnClick(){

        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email=etusername.getText().toString().trim();
                password=etpassword.getText().toString().trim();



                Matcher matcherObj = Pattern.compile(email_pattern).matcher(email);

                if (!matcherObj.matches()) {
                    etusername.setError("Invalid Email");
                } else if (etpassword.getText().toString().trim().length() < 0) {
                    etpassword.setError("Password Length is short");
                }else {
                    Login();
                }


            }
        });

    }

    private void Login() {





        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigInfo.login,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

sharedPreferences();


                    try {




                        JSONObject jsono = new JSONObject(response);
                        JSONArray jarray = jsono.getJSONArray("record");
                        for (int i = 0; i < jarray.length(); i++) {
                            JSONObject object = jarray.getJSONObject(i);
                         String   userID = object.getString("userid");
                            String  Email = object.getString("email");
                            String  Username= object.getString("name");
                            String  Phone= object.getString("phone");


                            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("useriddsign", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("userid", userID);
                            editor.putString("email", Email);
                            editor.putString("usernamesign", Username);
                            editor.putString("phone", Phone);
                            editor.commit();
                        }



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    Intent intent = new Intent(LoginMailActivity.this, Navigation.class);
                    startActivity(intent);



                    Log.e("pppppppppp", response);
                   // Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
//                        pdLoading.dismiss();


                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {



                    Toast.makeText(LoginMailActivity.this, error.toString(), Toast.LENGTH_LONG).show();

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


    public void sharedPreferences() {

        sh_Pref = getSharedPreferences("Login Credentials", PRIVATE_MODE);
        editor = sh_Pref.edit();
        editor.putBoolean(IS_LOGIN, true);
        editor.putString("Username", email);
        editor.putString("Password", password);
        editor.commit();
    }



    }
