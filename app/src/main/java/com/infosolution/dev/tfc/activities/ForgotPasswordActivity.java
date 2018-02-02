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
import android.widget.ImageView;
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

public class ForgotPasswordActivity extends AppCompatActivity {
    private View view;
    private ImageView ivbackk, iv;
    private EditText etemail;
    private Button btn;
    private String Email;
    private ProgressDialog pdLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        view = findViewById(R.id.ab_forpassworda);
        ivbackk = findViewById(R.id.ivfor);
        ivbackk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        etemail = findViewById(R.id.et_emailfor);
        btn = findViewById(R.id.btn_forgot);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Email = etemail.getText().toString();
                ForgotPass();
            }
        });


    }

    private void ForgotPass() {

        pdLoading = new ProgressDialog(ForgotPasswordActivity.this);
        //this method will be running on UI thread
        pdLoading.setMessage("\tLoading...");
        pdLoading.setCancelable(false);
        pdLoading.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://thefoodcircle.co.uk/restaurant/demo/web-service/forget_passwords.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.i("sigresfss", "" + response.toString());

                        pdLoading.dismiss();

                        etemail.setText("Enter Registered Email Id");

                        Toast.makeText(ForgotPasswordActivity.this, "Your password has been send to your registered email id", Toast.LENGTH_SHORT).show();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pdLoading.dismiss();

                        Toast.makeText(ForgotPasswordActivity.this, error.toString(), Toast.LENGTH_LONG).show();

//                        pdLoading.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();

                params.put("email", Email);

                Log.i("userpar", "" + params);


                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
