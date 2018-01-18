package com.infosolution.dev.tfc.user;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.infosolution.dev.tfc.R;

public class UserRegisterActivity extends AppCompatActivity {
    Button btnnxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        btnnxt=findViewById(R.id.btn_nextreg);
        btnnxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserRegisterActivity.this,UserRegNext.class);
                startActivity(intent);
            }
        });
    }
}
