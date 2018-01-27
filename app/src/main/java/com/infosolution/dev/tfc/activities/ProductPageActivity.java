package com.infosolution.dev.tfc.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.infosolution.dev.tfc.Class.ConfigInfo;
import com.infosolution.dev.tfc.R;
import com.infosolution.dev.tfc.user.PostCommentActivity;

import java.util.HashMap;
import java.util.Map;

public class ProductPageActivity extends AppCompatActivity {

    TextView proqty,tvminus,tvplus,tvtotalprice;
    int count=1;
    String qty,price,proimage;
    int qtyyy,priceee,totalpricee;
    private String tp,qtyy;
    Button btnbook;
    private ImageView ivproduct;

   private String UserId,ResId,MenuID,MenuName,pricee,Quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page);

        ivproduct=findViewById(R.id.iv_proimg);
        proqty = (TextView) findViewById(R.id.tv_proqty);
        tvminus = (TextView) findViewById(R.id.tv_minus);
        tvplus = (TextView) findViewById(R.id.tv_plus);
        tvtotalprice = (TextView) findViewById(R.id.tv_totalprice);
        btnbook=findViewById(R.id.btn_bookpay);

        Typeface typefaceregular = Typeface.createFromAsset(getAssets(), "font/tahoma.ttf");
        Typeface typefacebold = Typeface.createFromAsset(getAssets(), "font/tahomabd.ttf");

        proqty.setTypeface(typefacebold);
        tvtotalprice.setTypeface(typefaceregular);
        btnbook.setTypeface(typefacebold);


        //fetching values from sharedpreferece

        final SharedPreferences prefs = getSharedPreferences("useriddsign", MODE_PRIVATE);
        UserId = prefs.getString("userid", null);

        final SharedPreferences prefss = getSharedPreferences("resmenuDetails", MODE_PRIVATE);
        MenuName = prefss.getString("name", null);
        ResId = prefss.getString("resid", null);
        MenuID = prefss.getString("menuid", null);
     //   Quantity = prefss.getString("quantityyy", null);

        btnbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Book();
            }
        });

        Intent intent=getIntent();
        qty=intent.getStringExtra("qtyyy");
        price=intent.getStringExtra("priceee");
        proimage=intent.getStringExtra("img");

        qtyyy = Integer.parseInt(qty);




        Glide.with(this)
                .load(Uri.parse(proimage))
                .into(ivproduct);

        tvtotalprice.setText(price);

    /*    qtyyy = Integer.parseInt(proqty.getText().toString());
        priceee = Integer.parseInt(price);*/
        tvminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count--;

                if(count<0){
                    count=0;

                }
                proqty.setText(String.valueOf(count));

                qtyy=proqty.getText().toString();

                 tp = String.valueOf(Integer.parseInt(qtyy) * Integer.parseInt(price));
                 tvtotalprice.setText(tp);
                pricee=tvtotalprice.getText().toString();
              //  Quantity=proqty.getText().toString();
            }
        });
        tvplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                count++;
                if (count>qtyyy){
                    count=qtyyy;
                }

                proqty.setText(String.valueOf(count));
                qtyy=proqty.getText().toString();

                tp = String.valueOf(Integer.parseInt(qtyy) * Integer.parseInt(price));
                tvtotalprice.setText(tp);
                pricee=tvtotalprice.getText().toString();


            }
        });
    }

    private void Book() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigInfo.placeOrder,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Intent intent = new Intent(ProductPageActivity.this,PostCommentActivity.class);
                        startActivity(intent);

                        Toast.makeText(ProductPageActivity.this,response.toString(),Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        Toast.makeText(ProductPageActivity.this, error.toString(), Toast.LENGTH_LONG).show();

//                        pdLoading.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();

                params.put("userid", UserId);
                params.put("resid", ResId);
                params.put("menuid", MenuID);
                params.put("quantity", qtyy);
                params.put("price", pricee);
                params.put("name", MenuName);

                Log.i("paramsss",""+params);


                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }



    }
