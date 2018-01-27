package com.infosolution.dev.tfc.fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.infosolution.dev.tfc.R;
import com.infosolution.dev.tfc.user.ChangePasswordActivity;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfileFragment extends Fragment {
    Button btnchangepass;
    String Username,EmailId,PhoneNO;
    private EditText etname,etemail,etphone;


   /* public EditProfileFragment() {
        // Required empty public constructor
    }*/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_edit_profile, container, false);
        btnchangepass=v.findViewById(R.id.btn_changepassep);
        etname=v.findViewById(R.id.et_nameep);
        etemail=v.findViewById(R.id.et_emailep);
        etphone=v.findViewById(R.id.et_phoneep);

        Typeface typefaceregular = Typeface.createFromAsset(getContext().getAssets(), "font/tahoma.ttf");
        Typeface typefacebold = Typeface.createFromAsset(getContext().getAssets(), "font/tahomabd.ttf");

        btnchangepass.setTypeface(typefacebold);
        etname.setTypeface(typefaceregular);
        etemail.setTypeface(typefaceregular);
        etphone.setTypeface(typefaceregular);

        final SharedPreferences prefs = getContext().getSharedPreferences("useriddsign", MODE_PRIVATE);
        Username = prefs.getString("usernamesign", null);
        EmailId = prefs.getString("email", null);
        PhoneNO = prefs.getString("phone", null);

        etname.setHint(Username);
        etemail.setHint(EmailId);
        etphone.setHint(PhoneNO);


        btnchangepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),ChangePasswordActivity.class);
                startActivity(intent);


            }
        });

    return v;
    }

}
