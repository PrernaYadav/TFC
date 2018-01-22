package com.infosolution.dev.tfc.user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
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

public class PostCommentActivity extends AppCompatActivity {

    private EditText etcom;
    private TextView tvcount;
    private String ResId,UserId,UserName,Comment;
    private RatingBar rb;
    String Rating;

    private Button btnpost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

//fetching required values

        final SharedPreferences prefs = getSharedPreferences("useriddsign", MODE_PRIVATE);
        UserId = prefs.getString("useriddsign", null);
        UserName = prefs.getString("usernamesign", null);

        final SharedPreferences prefss = getSharedPreferences("resmenuDetails", MODE_PRIVATE);
        ResId = prefss.getString("resid", null);



        etcom=findViewById(R.id.et_com);
        tvcount=findViewById(R.id.tv_count);
        btnpost=findViewById(R.id.btn_postcom);
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

        Toast.makeText(PostCommentActivity.this, String.valueOf(rb.getRating()), Toast.LENGTH_SHORT).show();

        Comment=etcom.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigInfo.PostComment,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Intent intent = new Intent(PostCommentActivity.this,CommentsActivity.class);
                        startActivity(intent);

         Toast.makeText(PostCommentActivity.this,response.toString(),Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        Toast.makeText(PostCommentActivity.this, error.toString(), Toast.LENGTH_LONG).show();

//                        pdLoading.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();

                params.put("resid", ResId);
                params.put("comment", Comment);
                params.put("username", UserName);
                params.put("userimage", "123");
                params.put("rating", Rating);
                params.put("userid", UserId);

                Log.i("paramscomment",""+params);


                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }




    }

