package com.infosolution.dev.tfc.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.infosolution.dev.tfc.R;

public class MainActivity extends AppCompatActivity {
    private Button btnmailid,btnuserreg,btnbusilogin;
    private TextView tvguest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

/*        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/

        setContentView(R.layout.activity_main);
        btnmailid=findViewById(R.id.btn_mailid);
        btnuserreg=findViewById(R.id.btn_userregister);
        btnbusilogin=findViewById(R.id.btn_busilogin);
        tvguest=findViewById(R.id.tv_guest);

        btnmailid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,LoginMailActivity.class);
                startActivity(intent);
            }
        });

        btnbusilogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,LoginBusinessActivity.class);
                startActivity(intent);
            }
        });


    }
}
