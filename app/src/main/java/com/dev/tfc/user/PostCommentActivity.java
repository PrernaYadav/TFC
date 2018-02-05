package com.dev.tfc.user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.dev.tfc.Class.ConfigInfo;
import com.dev.tfc.R;

import java.util.HashMap;
import java.util.Map;

public class PostCommentActivity extends AppCompatActivity {

    private EditText etcom;
    private TextView tvcount,tvcomname;
    private String ResId,UserId,UserName,Comment;
    private RatingBar rb;
   private String Rating,UserImage;
private ImageView iv;
    private Button btnpost;
    private View view;
    private ImageView ivbackk;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        Typeface typefaceregular = Typeface.createFromAsset(getAssets(), "font/tahoma.ttf");
        Typeface typefacebold = Typeface.createFromAsset(getAssets(), "font/tahomabd.ttf");

//fetching required values

        final SharedPreferences prefs = getSharedPreferences("useriddsign", MODE_PRIVATE);
        UserId = prefs.getString("userid", null);
        UserName = prefs.getString("usernamesign", null);
        UserImage = prefs.getString("profileimage", null);

        final SharedPreferences prefss = getSharedPreferences("resmenuDetails", MODE_PRIVATE);
        ResId = prefss.getString("resid", null);

        view=findViewById(R.id.ab_changepassworda);
        ivbackk=findViewById(R.id.iv);
        ivbackk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        etcom=findViewById(R.id.et_com);
        tvcomname=findViewById(R.id.tv_comname);
        tvcount=findViewById(R.id.tv_counttt);
        btnpost=findViewById(R.id.btn_postcom);
        iv=findViewById(R.id.ivvvvv);

        tvcomname.setText(UserName);
        Glide.with(this).load(UserImage).into(iv);

        etcom.setTypeface(typefaceregular);
        tvcount.setTypeface(typefaceregular);
        btnpost.setTypeface(typefacebold);

        btnpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Post();
            }
        });

        rb=findViewById(R.id.rb_comm);
        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

                 // Rating=String.valueOf(v);
                Log.i("rating",""+Rating);


               /* Toast.makeText(PostCommentActivity.this,
                        String.valueOf(ratingBar.getRating()),
                        Toast.LENGTH_SHORT).show();*/
            }
        });

        etcom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                int length = etcom.length();
                String convert = String.valueOf(length);
                tvcount.setText(convert);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    private void Post() {

        Rating=String.valueOf(rb.getRating());
        Comment=etcom.getText().toString();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigInfo.PostComment,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response.....email.....", response);
                        Toast.makeText(PostCommentActivity.this, "Your Comment Has been Posted Successfully", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(PostCommentActivity.this,CommentsActivity.class);
                        startActivity(intent);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                     //   Toast.makeText(PostCommentActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {


                Map<String, String> params = new HashMap<String, String>();

                params.put("resid", ResId);
                params.put("comment", Comment);
                params.put("username", UserName);
                params.put("userimage", UserImage);
                params.put("rating", Rating);
                params.put("userid", UserId);

                Log.i("bnm",""+params);


                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }




    }

