package com.dev.tfc.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dev.tfc.R;
import com.dev.tfc.user.CommentsActivity;
import com.dev.tfc.user.PostCommentActivity;

public class BuyNowActivity extends AppCompatActivity {

    private TextView tvqty,tvprice,tvtime,tvadd,tvviewcom,tvpostcom;
    private ImageView ivlogo,ivreslogo;
    private Button btnbuy;
    private String UserId,ResId,MenuID,MenuName,pricee,Quantity,Ct;
   private String qty,price,proimage,logo,Add,timing;
   int q;
   private View view;

   private String ResName,menuName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_now);



        final SharedPreferences prefss = getSharedPreferences("resmenuDetails", MODE_PRIVATE);
        Ct = prefss.getString("ct", null);

        final SharedPreferences prefsss = getSharedPreferences("resmenuDetailss", MODE_PRIVATE);
        logo = prefsss.getString("log", null);
        Add = prefsss.getString("add", null);

        final SharedPreferences prefssss = getSharedPreferences("resmenuDetails", MODE_PRIVATE);
        MenuName = prefssss.getString("name", null);
        ResId = prefssss.getString("resid", null);
        MenuID = prefssss.getString("menuid", null);


        Intent intent=getIntent();
        qty=intent.getStringExtra("qty");
        price=intent.getStringExtra("price");
        proimage=intent.getStringExtra("proimage");
        timing=intent.getStringExtra("time");
        ResName =intent.getStringExtra("resname");
        menuName =intent.getStringExtra("proname");

        q = Integer.parseInt(qty);;




        tvqty=findViewById(R.id.tv_quantitybuy);
        tvprice=findViewById(R.id.tv_pricebuy);
        tvtime=findViewById(R.id.tv_timebuy);
        tvadd=findViewById(R.id.tv_addbuy);
        tvviewcom=findViewById(R.id.tv_viewcommbuy);
        tvpostcom=findViewById(R.id.tv_postcommbuy);
        btnbuy=findViewById(R.id.btn_buynow);

        view=findViewById(R.id.buyaction);
        ImageView iv =findViewById(R.id.ivnowbuy);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        Typeface typefaceregular = Typeface.createFromAsset(getAssets(), "font/tahoma.ttf");
        Typeface typefacebold = Typeface.createFromAsset(getAssets(), "font/tahomabd.ttf");

        tvqty.setTypeface(typefaceregular);
        tvprice.setTypeface(typefaceregular);
        tvtime.setTypeface(typefaceregular);
        tvadd.setTypeface(typefaceregular);
        tvviewcom.setTypeface(typefaceregular);
        tvpostcom.setTypeface(typefaceregular);
        btnbuy.setTypeface(typefacebold);









        tvqty.setText(qty);
        tvprice.setText(price);
        tvtime.setText(timing);
        tvadd.setText(Add);

        if (q <= 0){
            btnbuy.setText("Sold Out");
            btnbuy.setClickable(false);
        }else {
            btnbuy.setText("Buy Now");
        }

        ivlogo=findViewById(R.id.iv_proimagebuy);
        Glide.with(this)
                .load(Uri.parse(proimage))
                .into(ivlogo);


        ivreslogo=findViewById(R.id.iv_logobuy);
        Glide.with(this)
                .load(Uri.parse(logo))
                .into(ivreslogo);

        tvpostcom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(BuyNowActivity.this,PostCommentActivity.class);
                startActivity(intent1);
                finish();
            }
        });

        tvviewcom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(BuyNowActivity.this,CommentsActivity.class);
                startActivity(intent1);
                finish();
            }
        });



        btnbuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(BuyNowActivity.this,ProductPageActivity.class);
                intent1.putExtra("img",proimage);
                intent1.putExtra("qtyyy",qty);
                intent1.putExtra("priceee",price);
                intent1.putExtra("resname",ResName);
                intent1.putExtra("menuname",menuName);
                intent1.putExtra("timing",timing);
                startActivity(intent1);


            }
        });

    }




}
