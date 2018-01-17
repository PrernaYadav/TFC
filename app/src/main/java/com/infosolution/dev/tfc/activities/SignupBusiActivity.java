package com.infosolution.dev.tfc.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.infosolution.dev.tfc.R;

public class SignupBusiActivity extends AppCompatActivity {

    private EditText etcontactper,etstore,etposition,etphone,etothphone,etpass,etreppass;
    private Button btnsignup;
    private TextView tvsignin;
    private String contactper,store,position,phone,othphone,pass,reppass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_busi);


        etcontactper=findViewById(R.id.et_namesignupbusi);
        etstore=findViewById(R.id.et_storenamebusi);
        etposition=findViewById(R.id.et_positionbusi);
        etphone=findViewById(R.id.et_phonebusi);
        etothphone=findViewById(R.id.et_othphonebusi);
        etpass=findViewById(R.id.et_passwordbusi);
        etreppass=findViewById(R.id.et_reppassbusi);
        tvsignin=findViewById(R.id.tv__signinsignupbusi);
        btnsignup=findViewById(R.id.btn_signinbusi);
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contactper=etcontactper.getText().toString().trim();
                store=etstore.getText().toString().trim();
                position=etposition.getText().toString().trim();
                phone=etphone.getText().toString().trim();
                othphone=etothphone.getText().toString().trim();
                pass=etpass.getText().toString().trim();
                reppass=etreppass.getText().toString().trim();
            }
        });


    }
}
