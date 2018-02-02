package com.infosolution.dev.tfc.business_fragments;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.infosolution.dev.tfc.Class.ConfigInfo;
import com.infosolution.dev.tfc.R;
import com.infosolution.dev.tfc.business.LoginBusinessActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class UploadMenuFragment extends Fragment {

    private EditText etmenuname, etprice,  etqtyleft;
    private String MenuName, Price, FoodType, CollectionTime, Quantity;
    private ImageView ivmenuimage;
    private Button btnupload;
    private  String ResIdB,Country,Currency;
    private Bitmap bitmap;
    private  String encodedResume;
    private static final int REQUEST_CODE_JOB = 1;
    ProgressDialog pdLoading ;
    private Spinner spfoodtype,spcollectiontime;
    private  Context ctx;
    private Bitmap bmap;
    private  String MN,P,Q,T;
    private static final int CAMERA_PIC_REQUEST = 22;
    private TextView tvcurr;




   /* public UploadMenuFragment() {
        // Required empty public constructor
    }*/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_upload_menu, container, false);


        final SharedPreferences prefs = getContext().getSharedPreferences("LogindataB", MODE_PRIVATE);
        ResIdB = prefs.getString("resid", null);
        Country = prefs.getString("country", null);






//view=v.findViewById(R.id.llreg);

        etmenuname = v.findViewById(R.id.et_menuname_upload);
        etprice = v.findViewById(R.id.et_price_upload);
        spfoodtype = v.findViewById(R.id.sp_foodtype);
        spcollectiontime = v.findViewById(R.id.sp_collectiontime);
        etqtyleft = v.findViewById(R.id.et_qtyleft);
        ivmenuimage = v.findViewById(R.id.iv_image_upload);
        btnupload = v.findViewById(R.id.btn_submit_upload);
        tvcurr=v.findViewById(R.id.tv_currsign);


        if (Country.equals("United Kingdom")){
            tvcurr.setText("£");
        }else if (Country.equals("India")){
            tvcurr.setText("₹");
        }else if (Country.equals("United States")){
            tvcurr.setText("$");
        }else if (Country.equals("Japan")){
            tvcurr.setText("¥");
        }else if (Country.equals("Australia")){
            tvcurr.setText("$");
        }else if (Country.equals("New Zealand")){
            tvcurr.setText("$");
        }else if (Country.equals("Canada")){
            tvcurr.setText("$");
        }else if (Country.equals("China")){
            tvcurr.setText("¥");
        }else if (Country.equals("France")){
            tvcurr.setText("₣");
        }else if (Country.equals("Singapore")){
            tvcurr.setText("$");
        }else if (Country.equals("Thailand")){
            tvcurr.setText("฿");
        }

Currency=tvcurr.getText().toString();


        etmenuname.setHint("Please Enter Menu Name");
        etprice.setHint("Please Enter Price");
        etqtyleft.setHint("Please Enter Quantity left");
        ivmenuimage.setImageResource(R.drawable.uploadimage);


        Intent intent= getActivity().getIntent();
        MN=intent.getStringExtra("menuname");
        P=intent.getStringExtra("price");
        T=intent.getStringExtra("img");
        Q=intent.getStringExtra("qty");

        if (TextUtils.isEmpty(MN)){
            etmenuname.setHint("Please Enter Menu Name");
            etprice.setHint("Please Enter Price");
            etqtyleft.setHint("Please Enter Quantity Left");
            ivmenuimage.setImageResource(R.drawable.upim);
        }else {
            etmenuname.setHint(MN);
            etprice.setHint(P);
            etqtyleft.setHint(Q);
            Glide.with(this).load(T).into(ivmenuimage);
        }

        bmap  = BitmapFactory.decodeResource(getResources(),  R.drawable.icon);
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bmap.compress(Bitmap.CompressFormat.JPEG, 100, bao);
        byte [] ba = bao.toByteArray();
        encodedResume=Base64.encodeToString(ba,Base64.DEFAULT);



        ivmenuimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Couldn't load photo", Toast.LENGTH_LONG).show();
                }

            }
        });

        btnupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MenuName = etmenuname.getText().toString();
                Price = etprice.getText().toString();
                FoodType = spfoodtype.getSelectedItem().toString();
                CollectionTime = spcollectiontime.getSelectedItem().toString();
                Quantity = etqtyleft.getText().toString();

                 if (MenuName.length()<1){
                    etmenuname.setError("Please Enter Menu Name");
                }else if (Price.length() < 1){
                    etprice.setError("Please Enter Price");
                }else  if (FoodType.trim().equals("Food Type") ){
                   Toast.makeText(getContext(),"Please Select Food Type",Toast.LENGTH_SHORT).show();
                }else  if (CollectionTime.trim().equals("Collection Time") ){
                    Toast.makeText(getContext(),"Please Select Collection Time",Toast.LENGTH_SHORT).show();
                }else  if (Quantity.length() < 0){
                    etqtyleft.setError("Please Enter Quantity Left");
                }
                else {
                    SubmitUpload();
                }
            }
        });


        return v;
    }

    private void SubmitUpload() {

         pdLoading = new ProgressDialog(getContext());
        //this method will be running on UI thread
        pdLoading.setMessage("\tLoading...");
        pdLoading.setCancelable(false);
        pdLoading.show();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigInfo.uploadmenu,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        Log.e("pppppppppp", response);
                      // Toast.makeText(getContext(), response.toString(), Toast.LENGTH_LONG).show();
                        Toast.makeText(getContext(),"Menu has Uploaded", Toast.LENGTH_LONG).show();
                        Log.i("uploadres",""+response.toString());
                        etmenuname.setText("Please Enter Menu Name");
                        etprice.setText("Please Enter Price");
                        etqtyleft.setText("Please Enter Quantity left");
                        ivmenuimage.setImageResource(R.drawable.uploadimage);
                        spfoodtype.setSelection(0);
                        spcollectiontime.setSelection(0);

                        pdLoading.dismiss();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

//                        Toast.makeText(ctx, error.toString(), Toast.LENGTH_SHORT).show();

                        Toast.makeText(getContext(), "Please Upload Menu Image", Toast.LENGTH_LONG).show();

                      pdLoading.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();

                params.put("image1",encodedResume );
                params.put("res_id",ResIdB );
                params.put("menu_rate",Price );
                params.put("collection_time",CollectionTime );
                params.put("quantity_left",Quantity );
                params.put("menu_name",MenuName );
                params.put("food_type",FoodType );

                Log.i("uploadparam",""+params);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }



    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        try {
            switch (requestCode) {
                case CAMERA_PIC_REQUEST:
                    if (resultCode == RESULT_OK) {
                        try {
                            Bitmap photo = (Bitmap) data.getExtras().get("data");

                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            photo.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                            byte[] byteArray = byteArrayOutputStream.toByteArray();
                            encodedResume = Base64.encodeToString(byteArray, Base64.DEFAULT);

                            ivmenuimage.setImageBitmap(photo);
                            Log.i("imagebase",encodedResume);






                        } catch (Exception e) {
                            Toast.makeText(getContext(), "Couldn't load photo", Toast.LENGTH_LONG).show();
                        }
                    }
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
        }
            }
        }



   /* public static Fragment newInstance() {

        return new UploadMenuFragment();
    }*/


