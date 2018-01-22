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

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupUserActivity extends AppCompatActivity {

    private EditText etname, etemail, etphone, etpass, etreppass;
    private Button btnsignup;
    private TextView tvsignin;
    private String name, email, phone, pass, repass;
    private ProgressDialog pdLoading;
    String userID,userName,Email;

    private static final String email_pattern =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_mail);

        etname = findViewById(R.id.et_namesignup);
        etemail = findViewById(R.id.et_emailsignup);
        etphone = findViewById(R.id.et_phonesignup);
        etpass = findViewById(R.id.et_passwordsignup);
        etreppass = findViewById(R.id.et_reppasswordsignup);
        btnsignup = findViewById(R.id.btn_signup);
        tvsignin = findViewById(R.id.tv__signinsignup);
        tvsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupUserActivity.this, LoginMailActivity.class);
                startActivity(intent);
            }
        });

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = etname.getText().toString().trim();
                email = etemail.getText().toString().trim();
                phone = etphone.getText().toString().trim();
                pass = etpass.getText().toString().trim();
                repass = etreppass.getText().toString().trim();

               /* pdLoading = new ProgressDialog(SignupUserActivity.this);
                //this method will be running on UI thread
                pdLoading.setMessage("\tLoading...");
                pdLoading.setCancelable(false);
                pdLoading.show();*/


                Matcher matcherObj = Pattern.compile(email_pattern).matcher(email);

                if (!matcherObj.matches()) {
                    etemail.setError("Invalid Email");
                } else if (pass.length() < 0) {
                    etpass.setError("Password Length is short");
                } else if (name.equals(0)) {
                    etname.setError("Please Enter Name");
                } else if (phone.length() < 10) {
                    etphone.setError("Please Enter Valid Phone Number ");
                } else if (!pass.equals(repass)){
                    etreppass.setError("Password Does't Match");

                }else {
                    Signup();
                }


            }
        });
    }

    private void Signup() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigInfo.registration,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {





                        try {
                            JSONObject json = (JSONObject) new JSONTokener(response).nextValue();
                            JSONObject json2 = json.getJSONObject("data");
                            userID = (String) json2.get("customer_id");
                            userName = (String) json2.get("name");
                            Email = (String) json2.get("email");

                            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("useridd", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                        //    editor.putString("userid", userID);
                            //editor.putString("username", userName);
                          //  editor.putString("email", Email);
                            editor.commit();


                            Log.i("dataaa", "" + json2);
                            Log.i("userId", "" + userID);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Intent intent = new Intent(SignupUserActivity.this, LoginMailActivity.class);
                        startActivity(intent);



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        Toast.makeText(SignupUserActivity.this, error.toString(), Toast.LENGTH_LONG).show();

//                        pdLoading.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();

                params.put("name", name);
                params.put("email", email);
                params.put("phone", phone);
                params.put("password", pass);
                params.put("zip", repass);


                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}

