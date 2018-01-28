package com.infosolution.dev.tfc.user;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class ChangePasswordActivity extends AppCompatActivity {
    private EditText etoldpass,etnewpass;
    private Button btnupdate;
    private  String oldpass,newpass,email;
    private View view;
    private ImageView ivbackk;
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        etoldpass=findViewById(R.id.et_enteroldpass);
        etnewpass=findViewById(R.id.et_enternewpass);
        btnupdate=findViewById(R.id.btn_updatepass);


        Typeface typefaceregular = Typeface.createFromAsset(getAssets(), "font/tahoma.ttf");
        Typeface typefacebold = Typeface.createFromAsset(getAssets(), "font/tahomabd.ttf");

        etoldpass.setTypeface(typefaceregular);
        etnewpass.setTypeface(typefaceregular);
        btnupdate.setTypeface(typefacebold);


        view=findViewById(R.id.ab_changepassworda);
        ivbackk=findViewById(R.id.iv);
        ivbackk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        textView=findViewById(R.id.tv);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText("Change Password");
            }
        });



        final SharedPreferences prefs = getSharedPreferences("useriddsign", MODE_PRIVATE);
        email = prefs.getString("email", null);

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                oldpass=etoldpass.getText().toString();
                newpass=etnewpass.getText().toString();

                ChangePass();

            }
        });
    }

    private void ChangePass() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigInfo.changePass,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(ChangePasswordActivity.this,"Password Has been Changed Successfully.",Toast.LENGTH_LONG).show();
                        etoldpass.setText("Enter Old Password");
                        etnewpass.setText("Enter New Password");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        Toast.makeText(ChangePasswordActivity.this, error.toString(), Toast.LENGTH_LONG).show();

//                        pdLoading.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();

                params.put("new_password", newpass);
                params.put("old_password", oldpass);
                params.put("email", email);

                Log.i("paramsss",""+params);


                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
