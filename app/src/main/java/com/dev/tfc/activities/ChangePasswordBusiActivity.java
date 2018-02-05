package com.dev.tfc.activities;

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
import com.dev.tfc.R;

import java.util.HashMap;
import java.util.Map;

public class ChangePasswordBusiActivity extends AppCompatActivity {


    private EditText etoldpass,etnewpass;
    private Button btnupdate;
    private  String oldpass,newpass,email;
    private View view;
    private ImageView ivbackk,iv;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password_busi);


        etoldpass=findViewById(R.id.et_enteroldpassbusi);
        etnewpass=findViewById(R.id.et_enternewpassbusi);
        btnupdate=findViewById(R.id.btn_updatepassbusi);

        Typeface typefaceregular = Typeface.createFromAsset(getAssets(), "font/tahoma.ttf");
        Typeface typefacebold = Typeface.createFromAsset(getAssets(), "font/tahomabd.ttf");

        etoldpass.setTypeface(typefaceregular);
        etnewpass.setTypeface(typefaceregular);
        btnupdate.setTypeface(typefacebold);

        final SharedPreferences prefs = getSharedPreferences("LogindataB", MODE_PRIVATE);
        email = prefs.getString("emailid", null);



        view=findViewById(R.id.ab_changepassworda);
        ivbackk=findViewById(R.id.ivchange);
        ivbackk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                oldpass=etoldpass.getText().toString();
                newpass=etnewpass.getText().toString();

                ChangePassword();

            }
        });


    }

    private void ChangePassword() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://thefoodcircle.co.uk/restaurant/demo/web-service/change-businesspassword.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(ChangePasswordBusiActivity.this,"Password Has been Changed Successfully.",Toast.LENGTH_LONG).show();
                        etoldpass.setText("Enter Old Password");
                        etnewpass.setText("Enter New Password");

                      /*  Intent intent = new Intent(ChangePasswordBusiActivity.this, LoginBusinessActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();*/
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        Toast.makeText(ChangePasswordBusiActivity.this, error.toString(), Toast.LENGTH_LONG).show();

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
