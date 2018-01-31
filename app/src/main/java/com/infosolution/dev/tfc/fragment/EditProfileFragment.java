package com.infosolution.dev.tfc.fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import com.infosolution.dev.tfc.R;
import com.infosolution.dev.tfc.business_fragments.UploadMenuFragment;
import com.infosolution.dev.tfc.user.ChangePasswordActivity;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfileFragment extends Fragment {
    private Button btnchangepass,btndone;
  private   String Username,EmailId,PhoneNO,UserId;
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

        final SharedPreferences prefs = getContext().getSharedPreferences("useriddsign", MODE_PRIVATE);
        Username = prefs.getString("usernamesign", null);
        EmailId = prefs.getString("email", null);
        PhoneNO = prefs.getString("phone", null);
        UserId = prefs.getString("userid", null);

        etname.setText(Username);
        etemail.setText(EmailId);
        etphone.setText(PhoneNO);

btndone.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        Username=etname.getText().toString();
        EmailId=etemail.getText().toString();
        PhoneNO=etphone.getText().toString();
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

}
