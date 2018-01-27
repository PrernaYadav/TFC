package com.infosolution.dev.tfc.business;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.infosolution.dev.tfc.R;

public class UserRegActivity extends AppCompatActivity {
    Button btnnxt;
    private String Contactperson,Storename,Position,Phone1,Phone2,Password,Repassword;
    private  String EmailId,Address,Country,City,ZipCode,Website,OtherInfo;
    private EditText etemail,etadd,etcountry,etcity,etzip,etwebsite,etotherInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

       Intent intent=getIntent();
        Contactperson=intent.getStringExtra("contactperson");
        Storename=intent.getStringExtra("storename");
        Position=intent.getStringExtra("position");
        Phone1=intent.getStringExtra("phone1");
        Phone2=intent.getStringExtra("phone2");
        Password=intent.getStringExtra("password");
        Repassword=intent.getStringExtra("repassword");


        etemail=findViewById(R.id.et_emailreg);
        etadd=findViewById(R.id.et_addreg);
        etcountry=findViewById(R.id.et_countryreg);
        etcity=findViewById(R.id.et_cityreg);
        etzip=findViewById(R.id.et_zipreg);
        etwebsite=findViewById(R.id.et_websitereg);
        etotherInfo=findViewById(R.id.et_otherinfo);
        btnnxt=findViewById(R.id.btn_nextreg);

        Typeface typefaceregular = Typeface.createFromAsset(getAssets(), "font/tahoma.ttf");
        Typeface typefacebold = Typeface.createFromAsset(getAssets(), "font/tahomabd.ttf");

        etemail.setTypeface(typefaceregular);
        etadd.setTypeface(typefaceregular);
        etcountry.setTypeface(typefaceregular);
        etcity.setTypeface(typefaceregular);
        etzip.setTypeface(typefaceregular);
        etwebsite.setTypeface(typefaceregular);
        etotherInfo.setTypeface(typefaceregular);
        btnnxt.setTypeface(typefacebold);





        btnnxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EmailId=etemail.getText().toString();
                Address=etadd.getText().toString();
                Country=etcountry.getText().toString();
                City=etcity.getText().toString();
                ZipCode=etzip.getText().toString();
                Website=etwebsite.getText().toString();
                OtherInfo=etotherInfo.getText().toString();


                Intent intent = new Intent(UserRegActivity.this,UserRegNext.class);

                intent.putExtra("contactper",Contactperson);
                intent.putExtra("storename",Storename);
                intent.putExtra("position",Position);
                intent.putExtra("phone1",Phone1);
                intent.putExtra("phone2",Phone2);
                intent.putExtra("password",Password);

                intent.putExtra("emialid",EmailId);
                intent.putExtra("address",Address);
                intent.putExtra("country",Country);
                intent.putExtra("city",City);
                intent.putExtra("zip",ZipCode);
                intent.putExtra("website",Website);
                intent.putExtra("otherinfo",OtherInfo);

                startActivity(intent);
            }
        });
    }
}
