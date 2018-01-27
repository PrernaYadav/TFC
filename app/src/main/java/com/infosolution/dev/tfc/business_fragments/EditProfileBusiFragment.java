package com.infosolution.dev.tfc.business_fragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.infosolution.dev.tfc.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfileBusiFragment extends Fragment {

    private Button btndoone,btnchangepass;
    private EditText etname,etname2,etemail,etphone;



   /* public EditProfileBusiFragment() {
        // Required empty public constructor
    }*/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_edit_profile_busi, container, false);

        btndoone = v.findViewById(R.id.btn_doneepbusi);
        btnchangepass = v.findViewById(R.id.btn_changepassepbusi);
        etname=v.findViewById(R.id.et_namepbusi);
        etname2=v.findViewById(R.id.et_nameepbusi);
        etemail=v.findViewById(R.id.et_emailepbusi);
        etphone=v.findViewById(R.id.et_phoneepbusi);

        Typeface typefaceregular = Typeface.createFromAsset(getContext().getAssets(), "font/tahoma.ttf");
        Typeface typefacebold = Typeface.createFromAsset(getContext().getAssets(), "font/tahomabd.ttf");

        btndoone.setTypeface(typefacebold);
        btnchangepass.setTypeface(typefacebold);
        etname.setTypeface(typefaceregular);
        etname2.setTypeface(typefaceregular);
        etemail.setTypeface(typefaceregular);
        etphone.setTypeface(typefaceregular);




        btndoone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



        btnchangepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        return v;
    }

}
