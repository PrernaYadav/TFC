package com.infosolution.dev.tfc.business;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.infosolution.dev.tfc.activities.LoginMailActivity;
import com.infosolution.dev.tfc.activities.SignupUserActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class SignupBusiActivity extends AppCompatActivity {

    private EditText etcontactper, etstore, etposition, etphone, etothphone, etpass, etreppass;
    private Button btnsignup;
    private TextView tvsignin;
    private String contactper, store, position, pass, reppass, ph1, ph2,Resid;
    int phone, othphone;
    private ImageView ivpicker;
    private Bitmap bitmap;
    private String encodedResume;
    private static final int REQUEST_CODE_JOB = 1;
    private ProgressDialog pdLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_busi);

        final SharedPreferences prefs = getSharedPreferences("LogindataB", MODE_PRIVATE);
        Resid = prefs.getString("resid", null);




        etcontactper = findViewById(R.id.et_namesignupbusi);
        etstore = findViewById(R.id.et_storenamebusi);
        etposition = findViewById(R.id.et_positionbusi);
        etphone = findViewById(R.id.et_phonebusi);
        etothphone = findViewById(R.id.et_othphonebusi);
        etpass = findViewById(R.id.et_passbusinxt);
        etreppass = findViewById(R.id.et_reppassbusi);
        tvsignin = findViewById(R.id.tv__signinsignupbusi);
        btnsignup = findViewById(R.id.btn_signupbusinxt);
        ivpicker = findViewById(R.id.imagepicker);

        ivpicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickImage();
            }
        });


        Typeface typefaceregular = Typeface.createFromAsset(getAssets(), "font/tahoma.ttf");
        Typeface typefacebold = Typeface.createFromAsset(getAssets(), "font/tahomabd.ttf");

        etcontactper.setTypeface(typefaceregular);
        etstore.setTypeface(typefaceregular);
        etposition.setTypeface(typefaceregular);
        etphone.setTypeface(typefaceregular);
        etothphone.setTypeface(typefaceregular);
        etpass.setTypeface(typefaceregular);
        etreppass.setTypeface(typefaceregular);
        tvsignin.setTypeface(typefaceregular);
        btnsignup.setTypeface(typefacebold);


        tvsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupBusiActivity.this, LoginBusinessActivity.class);
                startActivity(intent);
            }
        });
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contactper = etcontactper.getText().toString();
                store = etstore.getText().toString();
                position = etposition.getText().toString();
                pass = etpass.getText().toString();
                reppass = etreppass.getText().toString();



                try{
                    phone = Integer.parseInt(etphone.getText().toString());
                    othphone = Integer.parseInt(etothphone.getText().toString());
                }catch(NumberFormatException e){ // handle your exception
                    e.printStackTrace();
                }






                ph1 = String.valueOf(phone);
                ph2 = String.valueOf(othphone);


                SharedPreferences sharedPreferencess = getSharedPreferences("Sign", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferencess.edit();
                editor.putString("contact", contactper);
                editor.putString("store", store);
                editor.putString("pos", position);
                editor.putString("phone", ph1);
                editor.putString("othphone", ph2);
                editor.putString("pass", pass);
                editor.commit();

                if (contactper.length() == 0) {
                    etcontactper.setError("Please Enter Person Name");
                } else if (store.length() == 0) {
                    etstore.setError("Please Enter Store Name");
                } else if (position.length() == 0) {
                    etposition.setError("Please Enter Store Name");
                } else if (pass.length() == 0) {
                    etpass.setError("Password length is short");
                } else if (!reppass.equals(pass)) {
                    etreppass.setError("Passsword Does't Match");
                } else {

                   // ImageUpload();
                    Intent intent = new Intent(SignupBusiActivity.this, UserRegActivity.class);
                    startActivity(intent);



                }
            }
        });


    }





    public void PickImage() {

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
                stream = getContentResolver().openInputStream(data.getData());
                bitmap = BitmapFactory.decodeStream(stream);

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();


                ivpicker.setImageBitmap(bitmap);


                encodedResume = Base64.encodeToString(byteArray, Base64.DEFAULT);

                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Imagepicker", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("imagee", encodedResume);
                editor.commit();


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
            Toast.makeText(SignupBusiActivity.this, "error in image loading", Toast.LENGTH_LONG).show();

        }
    }
}
