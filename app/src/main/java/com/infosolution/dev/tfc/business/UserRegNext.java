package com.infosolution.dev.tfc.business;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Handler;
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
import com.infosolution.dev.tfc.Class.ConfigInfo;
import com.infosolution.dev.tfc.R;
import com.infosolution.dev.tfc.activities.LoginMailActivity;
import com.infosolution.dev.tfc.activities.SignupUserActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;
import java.util.Map;

public class UserRegNext extends AppCompatActivity {
    private Spinner spcusinetype, spclosingdays, spcloasingtime;
    private Button btnsignup;
    private String EmailId, Address, Country, City, ZipCode, Website, OtherInfo;
    private String Contactperson, Storename, Position,  Password;
   private String Phone1, Phone2;

    private String Cusinetype, CloasingDay, CloasingTime,Lat,Longi,ImagePick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reg_next);

        final SharedPreferences prefs = getSharedPreferences("latlong", MODE_PRIVATE);
        Lat = prefs.getString("lat", null);
        Longi = prefs.getString("laong", null);


        final SharedPreferences prefss = getSharedPreferences("Imagepicker", MODE_PRIVATE);
        ImagePick = prefss.getString("imagee", null);

        final SharedPreferences prefsss = getSharedPreferences("Sign", MODE_PRIVATE);
        Contactperson = prefsss.getString("contact", null);
        Storename = prefsss.getString("store", null);
        Position = prefsss.getString("pos", null);
        Phone1 = prefsss.getString("phone", null);
        Phone2 = prefsss.getString("othphone", null);
        Password = prefsss.getString("pass", null);





        final SharedPreferences pre = getSharedPreferences("Signnnn", MODE_PRIVATE);
        EmailId = pre.getString("email", null);
        Address = pre.getString("add", null);
        Country = pre.getString("con", null);
        City = pre.getString("city", null);
        ZipCode = pre.getString("zip", null);
        Website = pre.getString("website", null);
        OtherInfo = pre.getString("otherinfo", null);








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

                SignupBusi();



            }
        });
    }

    private void SignupBusi() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigInfo.resbusi,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        Toast.makeText(UserRegNext.this,"Restaurent Registered Successfully ! Ragistration activation code is send to your mail",Toast.LENGTH_LONG).show();

                        Log.i("busisuc",""+response);

                        Intent intent=new Intent(UserRegNext.this,LoginBusinessActivity.class);
                        startActivity(intent);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                      //  Toast.makeText(UserRegNext.this, error.toString(), Toast.LENGTH_LONG).show();
                        Log.i("busierr",""+error.toString());


                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();

                params.put("address",Address);
                params.put("city",City);
                params.put("email",EmailId);
                params.put("fb_url",Country);
                params.put("lat"," ");
                params.put("long"," ");
                params.put("name",Contactperson);
                params.put("opening_date",CloasingDay);
                params.put("opening_days",CloasingDay);
                params.put("other_info",OtherInfo);
                params.put("password",Password);
                params.put("phone1",Phone1);
                params.put("phone2",Phone2);
                params.put("position",Position);
                params.put("store_close_time",CloasingTime);
                params.put("store_name",Storename);
                params.put("type_of_cuisine",Cusinetype);
                params.put("website",Website);
                params.put("zip",ZipCode);

                Log.i("busiparamm",""+params);


                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }


}
