package com.infosolution.dev.tfc.business_fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.infosolution.dev.tfc.R;
import com.infosolution.dev.tfc.activities.ChangePasswordBusiActivity;
import com.infosolution.dev.tfc.business.LoginBusinessActivity;
import com.infosolution.dev.tfc.user.ChangePasswordActivity;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfileBusiFragment extends Fragment {

    private Button btndoone,btnchangepass;
    private EditText etname,etname2,etemail,etphone;
    private String Name,Email,Phonee,Store,ResID,rescheck;



   /* public EditProfileBusiFragment() {
        // Required empty public constructor
    }*/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_edit_profile_busi, container, false);


        final SharedPreferences prefs = getActivity().getSharedPreferences("LogindataB", MODE_PRIVATE);
        ResID = prefs.getString("resid", null);


        final SharedPreferences prefss = getActivity().getSharedPreferences("editpro", MODE_PRIVATE);
        Name = prefss.getString("namee", null);
        Email = prefss.getString("emailidd", null);
        Phonee = prefss.getString("phone22", null);
        Store = prefss.getString("storee", null);
        rescheck = prefss.getString("residd", null);

        if (TextUtils.isEmpty(rescheck)){
            GetUpdatedData();
        }







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

        etname.setText(Name);
        etemail.setText(Email);
        etname2.setText(Store);
        etphone.setText(Phonee);




        btndoone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Name=etname.getText().toString();
                Email=etemail.getText().toString();
                Phonee=etphone.getText().toString();
                Store=etname2.getText().toString();



                SharedPreferences preferences =getContext().getSharedPreferences("editpro", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();



                SharedPreferences pref = getContext().getSharedPreferences("Edit", Context.MODE_PRIVATE);
                SharedPreferences.Editor editorr = pref.edit();
                editorr.putString("emailid", Email);
                editorr.putString("phone", Phonee);
                editorr.putString("name", Name);
                editorr.putString("store", Store);
                editorr.commit();

                EditProfile();

            }
        });



        btnchangepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getContext(), ChangePasswordBusiActivity.class);
                startActivity(intent);

            }
        });


        return v;
    }

    private void EditProfile() {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://thefoodcircle.co.uk/restaurant/demo/web-service/update_businessprofile.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       Toast.makeText(getContext(),"Your profile has been sucessfully updated.",Toast.LENGTH_LONG).show();
                     //   Toast.makeText(getContext(), response.toString(), Toast.LENGTH_SHORT).show();
                       etname.setText(Name);
                       etemail.setText(Email);
                       etphone.setText(Phonee);
                       etname2.setText(Store);



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();

//                        pdLoading.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();

                params.put("name", Name);
                params.put("email", Email);
                params.put("phone", Phonee);
                params.put("storename", Store);
                params.put("userid", ResID);

                Log.i("paramsss",""+params);


                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);


    }

    public void GetUpdatedData(){
        final SharedPreferences prefsss = getActivity().getSharedPreferences("Edit", MODE_PRIVATE);
        Name = prefsss.getString("name", null);
        Email = prefsss.getString("emailid", null);
        Phonee = prefsss.getString("phone", null);
        Store = prefsss.getString("store", null);

    }


}
