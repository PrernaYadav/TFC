package com.dev.tfc.fragment;


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
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dev.tfc.R;
import com.dev.tfc.user.ChangePasswordActivity;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfileFragment extends Fragment {
    private Button btnchangepass,btndone;
  private   String Username,EmailId,PhoneNO,UserId,resch;
            private EditText etname,etemail,etphone;
    FrameLayout fl;


   /* public EditProfileFragment() {
        // Required empty public constructor
    }*/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_edit_profile, container, false);
        btnchangepass=v.findViewById(R.id.btn_changepassep);


        final SharedPreferences prefsS = getContext().getSharedPreferences("useriddsign", MODE_PRIVATE);
        UserId = prefsS.getString("userid", null);


        final SharedPreferences prefs = getContext().getSharedPreferences("edituserr", MODE_PRIVATE);
        Username = prefs.getString("usernamesignn", null);
        EmailId = prefs.getString("emaill", null);
        PhoneNO = prefs.getString("phonee", null);
        resch = prefs.getString("useriddfor", null);

        Log.i("rescheck",""+Username);


        if (TextUtils.isEmpty(Username)){
            GetUpdatedData();
        }



        btndone=v.findViewById(R.id.btn_doneep);
        etname=v.findViewById(R.id.et_nameep);
        etemail=v.findViewById(R.id.et_emailep);
        etphone=v.findViewById(R.id.et_phoneep);

       // fl=v.findViewById(R.id.container_layout);

        Typeface typefaceregular = Typeface.createFromAsset(getContext().getAssets(), "font/tahoma.ttf");
        Typeface typefacebold = Typeface.createFromAsset(getContext().getAssets(), "font/tahomabd.ttf");

        btnchangepass.setTypeface(typefacebold);
        etname.setTypeface(typefaceregular);
        etemail.setTypeface(typefaceregular);
        etphone.setTypeface(typefaceregular);



        etname.setText(Username);
        etemail.setText(EmailId);
        etphone.setText(PhoneNO);




btndone.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        Username=etname.getText().toString();
        EmailId=etemail.getText().toString();
        PhoneNO=etphone.getText().toString();

        SharedPreferences preferences =getContext().getSharedPreferences("edituserr", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();


        SharedPreferences pref = getContext().getSharedPreferences("Edittt", Context.MODE_PRIVATE);
        SharedPreferences.Editor editorr = pref.edit();
        editorr.putString("username", Username);
        editorr.putString("emailid", EmailId);
        editorr.putString("phone", PhoneNO);

        editorr.commit();



        EditPRo();
    }
});



        btnchangepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),ChangePasswordActivity.class);
                startActivity(intent);


               /* Fragment fragment = UploadMenuFragment.newInstance();

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                transaction.replace(R.id.container_layout, fragment).commit();*/


            }
        });

    return v;
    }

    private void EditPRo() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://thefoodcircle.co.uk/restaurant/demo/web-service/update_profile.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getContext(),"Your profile has been sucessfully updated.",Toast.LENGTH_LONG).show();
                        etname.setText(Username);
                        etemail.setText(EmailId);
                        etphone.setText(PhoneNO);


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

                params.put("name", Username);
                params.put("email", EmailId);
                params.put("phone", PhoneNO);
                params.put("userid", UserId);

                Log.i("paramsss",""+params);


                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }


    public void GetUpdatedData(){
        final SharedPreferences prefsss = getActivity().getSharedPreferences("Edittt", MODE_PRIVATE);
        Username = prefsss.getString("username", null);
        EmailId = prefsss.getString("emailid", null);
        PhoneNO = prefsss.getString("phone", null);


    }

}
