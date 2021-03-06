package com.dev.tfc.business;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
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
import com.dev.tfc.Class.ConfigInfo;
import com.dev.tfc.R;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class UserRegNext extends AppCompatActivity {
    private Spinner spcusinetype, spclosingdays, spcloasingtime;
    private Button btnsignup;
    private String EmailId, Address, Country, City, ZipCode, Website, OtherInfo;
    private String Contactperson, Storename, Position,  Password;
   private String Phone1, Phone2,encoded,Res;
   int residd;
   private Bitmap bmap;

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



        final SharedPreferences preferences = getSharedPreferences("Imagepicker", MODE_PRIVATE);
        encoded = preferences.getString("imagee", null);

        if (TextUtils.isEmpty(encoded)){
            bmap  = BitmapFactory.decodeResource(getResources(),  R.drawable.icon);
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            bmap.compress(Bitmap.CompressFormat.JPEG, 100, bao);
            byte [] ba = bao.toByteArray();
            encoded= Base64.encodeToString(ba,Base64.DEFAULT);
        }









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

                        ImageUpload();

                        Log.i("busisuc",""+response);


                        try {

                            JSONObject mainObject = new JSONObject(response);
                            JSONObject uniObject = mainObject.getJSONObject("data");
                              residd = uniObject.getInt("id");

                            Res = String.valueOf(residd);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }




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


                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("country", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("country", Country);
                editor.commit();

                Log.i("busiparamm",""+params);


                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }


    private void ImageUpload() {



        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://thefoodcircle.co.uk/restaurant/demo/web-service/res_uploadimageAndroid.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                      //   Toast.makeText(UserRegNext.this,"Your image has been sucessfully updated",Toast.LENGTH_LONG).show();
                       // Toast.makeText(UserRegNext.this, response.toString(), Toast.LENGTH_SHORT).show();



                        Log.i("uploadimageres",""+response.toString());


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                       // Toast.makeText(UserRegNext.this, error.toString(), Toast.LENGTH_LONG).show();


                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();

                params.put("image", encoded);
                params.put("resid", Res);
                params.put("type ", "business");

                Log.i("uploadimage",""+params);


                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


}}
