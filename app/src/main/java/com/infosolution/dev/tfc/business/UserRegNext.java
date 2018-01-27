package com.infosolution.dev.tfc.business;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.infosolution.dev.tfc.R;

import java.util.HashMap;
import java.util.Map;

public class UserRegNext extends AppCompatActivity {
    private Spinner spcusinetype, spclosingdays, spcloasingtime;
    private Button btnsignup;
    private String EmailId, Address, Country, City, ZipCode, Website, OtherInfo;
    private String Contactperson, Storename, Position, Phone1, Phone2, Password, Repassword;
    private String Cusinetype, CloasingDay, CloasingTime,Lat,Longi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reg_next);

        final SharedPreferences prefs = getSharedPreferences("latlong", MODE_PRIVATE);
        Lat = prefs.getString("lat", null);
        Longi = prefs.getString("laong", null);


        Intent intent = getIntent();
        Contactperson = intent.getStringExtra("contactper");
        Storename = intent.getStringExtra("storename");
        Position = intent.getStringExtra("position");
        Phone1 = intent.getStringExtra("phone1");
        Phone2 = intent.getStringExtra("phone2");
        Password = intent.getStringExtra("password");
        // Repassword=intent.getStringExtra("repassword");

        EmailId = intent.getStringExtra("emialid");
        Address = intent.getStringExtra("address");
        Country = intent.getStringExtra("country");
        City = intent.getStringExtra("city");
        ZipCode = intent.getStringExtra("zip");
        Website = intent.getStringExtra("website");
        OtherInfo = intent.getStringExtra("otherinfo");


        spcusinetype = findViewById(R.id.sp_cusinetype);
        spclosingdays = findViewById(R.id.sp_closingday);
        spcloasingtime = findViewById(R.id.sp_closingtime);
        btnsignup=findViewById(R.id.btn_signupbusiinxt);

        Typeface typefaceregular = Typeface.createFromAsset(getAssets(), "font/tahoma.ttf");
        Typeface typefacebold = Typeface.createFromAsset(getAssets(), "font/tahomabd.ttf");

        btnsignup.setTypeface(typefacebold);


        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cusinetype = spcusinetype.getSelectedItem().toString();
                CloasingDay = spclosingdays.getSelectedItem().toString();
                CloasingTime = spcloasingtime.getSelectedItem().toString();

                Signup();



            }
        });
    }

    private void Signup() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://thefoodcircle.co.uk/restaurant/demo/web-service/res_registration.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        Log.i("regres",""+response.toString());



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        Toast.makeText(UserRegNext.this, error.toString(), Toast.LENGTH_LONG).show();

//                       pdLoading.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();

                params.put("address ", Address);
                params.put("city ", City);
                params.put("email ", EmailId);
                params.put("fb_url", Country);
                params.put("lat ", Lat);
                params.put("long  ", Longi);
                params.put("name   ", Contactperson);
                params.put("opening_date   ", "abv");
                params.put("opening_days   ", "abv");
                params.put("other_info   ", OtherInfo);
                params.put("password    ", Password);
                params.put("phone1     ", Phone1);
                params.put("phone2      ", Phone2);
                params.put("position       ", Position);
                params.put("store_close_time       ", CloasingTime);
                params.put("store_name       ", Storename);
                params.put("type_of_cuisine       ", Cusinetype);
                params.put("website        ", Website);
                params.put("zip         ", ZipCode);

                Log.i("regparam",""+params);


                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
