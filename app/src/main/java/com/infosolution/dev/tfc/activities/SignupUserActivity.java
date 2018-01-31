package com.infosolution.dev.tfc.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
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
import com.infosolution.dev.tfc.business.LoginBusinessActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupUserActivity extends AppCompatActivity {

    private EditText etname, etemail, etphone, etpass, etreppass;
    private Button btnsignup;
    private TextView tvsignin,tvterms;
    private String name, email, phone, pass, repass;
    private ProgressDialog pdLoading;
    String userID,userName,Email;
    private ImageView ivimg;
    private Bitmap bitmap;
    private  String encodedResume;
    private static final int REQUEST_CODE_JOB = 1;
    Bitmap bmap;


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
        tvterms = findViewById(R.id.tv_terms);

        ivimg=findViewById(R.id.iv_resuserimg);
        ivimg.setImageResource(R.drawable.icon);


        bmap  = BitmapFactory.decodeResource(getResources(),  R.drawable.icon);
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bmap.compress(Bitmap.CompressFormat.JPEG, 100, bao);
        byte [] ba = bao.toByteArray();
         encodedResume=Base64.encodeToString(ba,Base64.DEFAULT);


         tvterms.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent(SignupUserActivity.this,TermsActivity.class);
                 startActivity(intent);
             }
         });






        Typeface typefaceregular = Typeface.createFromAsset(getAssets(), "font/tahoma.ttf");
        Typeface typefacebold = Typeface.createFromAsset(getAssets(), "font/tahomabd.ttf");

        etname.setTypeface(typefaceregular);
        etemail.setTypeface(typefaceregular);
        etphone.setTypeface(typefaceregular);
        etpass.setTypeface(typefaceregular);
        etreppass.setTypeface(typefaceregular);
        btnsignup.setTypeface(typefacebold);

        ivimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadImage();
            }
        });



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




                Matcher matcherObj = Pattern.compile(email_pattern).matcher(email);

                if (etname.getText().toString().length() < 1) {
                    etname.setError("Please Enter Name");
                } else if (!matcherObj.matches()) {
                    etemail.setError("Invalid Email");
                }  else  if (phone.length() < 10) {
                    etphone.setError("Please Enter Valid Phone Number ");
                } else if (etpass.getText().toString().length() < 1) {
                    etpass.setError("Password Length is short");
                }else if (!pass.equals(repass)){
                    etreppass.setError("Password Does't Match");

                }else {
                    Signup();
                }


            }
        });
    }



    private void Signup() {

        pdLoading = new ProgressDialog(SignupUserActivity.this);
        //this method will be running on UI thread
        pdLoading.setMessage("\tLoading...");
        pdLoading.setCancelable(false);
        pdLoading.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigInfo.registration,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        pdLoading.dismiss();
                        Toast.makeText(SignupUserActivity.this,"User Registered Successfully ! Your varification link has been sended to your email",Toast.LENGTH_LONG).show();
                        UploadApi();

                        try {
                            JSONObject json = (JSONObject) new JSONTokener(response).nextValue();
                            JSONObject json2 = json.getJSONObject("data");
                            userID = (String) json2.get("customer_id");
                            userName = (String) json2.get("name");
                            Email = (String) json2.get("email");

                            Log.i("dataaa", "" + json2);
                            Log.i("userId", "" + userID);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(SignupUserActivity.this, LoginMailActivity.class);
                                startActivity(intent);

                            }
                        }, 2000);





                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        Toast.makeText(SignupUserActivity.this, error.toString(), Toast.LENGTH_LONG).show();

                      pdLoading.dismiss();
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

                Log.i("sigparam",""+params);


                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }



    private void UploadImage() {

        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.putExtra("bmp_Image", bitmap);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, REQUEST_CODE_JOB);



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        InputStream stream = null;
        if (requestCode == REQUEST_CODE_JOB && resultCode == Activity.RESULT_OK) {
            try {
                // recyle unused bitmaps
                if (bitmap != null) {
                    bitmap.recycle();
                }
                stream = SignupUserActivity.this.getContentResolver().openInputStream(data.getData());
                bitmap = BitmapFactory.decodeStream(stream);

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();

                ivimg.setImageBitmap(bitmap);


                encodedResume = Base64.encodeToString(byteArray, Base64.DEFAULT);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                if (stream != null)
                    try {
                        stream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
        } else {
            Toast.makeText(SignupUserActivity.this, "error in image loading", Toast.LENGTH_LONG).show();

        }
    }
    private void UploadApi() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://thefoodcircle.co.uk/restaurant/demo/web-service/profile-upload.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.i("uploadres",""+response);
//                        Toast.makeText(SignupUserActivity.this,"User Registered Successfully ! Your varification link has been sended to your email",Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        Toast.makeText(SignupUserActivity.this, error.toString(), Toast.LENGTH_LONG).show();

//                       pdLoading.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();

                params.put("image", encodedResume);
                params.put("userid", userID);



                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);



    }




    }

