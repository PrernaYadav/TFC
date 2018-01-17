package com.infosolution.dev.tfc.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.infosolution.dev.tfc.R;

public class ProductPageActivity extends AppCompatActivity {

    TextView proqty,tvminus,tvplus;
    int count=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page);

        proqty = (TextView) findViewById(R.id.tv_proqty);
        tvminus = (TextView) findViewById(R.id.tv_minus);
        tvplus = (TextView) findViewById(R.id.tv_plus);
        tvminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count--;

                if(count<0){
                    count=0;
                }



                proqty.setText(String.valueOf(count));



            }
        });
        tvplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;



                proqty.setText(String.valueOf(count));

            }
        });
    }
}
