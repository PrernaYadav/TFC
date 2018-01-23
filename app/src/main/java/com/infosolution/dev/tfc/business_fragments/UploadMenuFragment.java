package com.infosolution.dev.tfc.business_fragments;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.infosolution.dev.tfc.Class.ConfigInfo;
import com.infosolution.dev.tfc.R;
import com.infosolution.dev.tfc.business.LoginBusinessActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class UploadMenuFragment extends Fragment {

    private EditText etmenuname, etprice,  etqtyleft;
    private String MenuName, Price, FoodType, CollectionTime, Quantity;
    private ImageView ivmenuimage;
    private Button btnupload;
    private  String ResIdB;
    private Bitmap bitmap;
    private  String encodedResume;
    private static final int REQUEST_CODE_JOB = 1;
    ProgressDialog pdLoading ;
    private Spinner spfoodtype,spcollectiontime;


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



        etmenuname = v.findViewById(R.id.et_menuname_upload);
        etprice = v.findViewById(R.id.et_price_upload);
        spfoodtype = v.findViewById(R.id.sp_foodtype);
        spcollectiontime = v.findViewById(R.id.sp_collectiontime);
        etqtyleft = v.findViewById(R.id.et_qtyleft);
        ivmenuimage = v.findViewById(R.id.iv_image_upload);
        btnupload = v.findViewById(R.id.btn_submit_upload);

        ivmenuimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadImagee();
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

                if (MenuName.length()<0){
                    etmenuname.setError("Please Enter Menu Name");
                }else if (Price.length() < 0){
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
                        Toast.makeText(getContext(), response.toString(), Toast.LENGTH_LONG).show();
                        Log.i("uploadres",""+response.toString());

                        pdLoading.dismiss();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {



                        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();

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

    public void loadImagee() {

        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.putExtra("bmp_Image", bitmap);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, REQUEST_CODE_JOB);



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        InputStream stream = null;
        if (requestCode == REQUEST_CODE_JOB && resultCode == Activity.RESULT_OK) {
            try {
                // recyle unused bitmaps
                if (bitmap != null) {
                    bitmap.recycle();
                }
                stream = getContext().getContentResolver().openInputStream(data.getData());
                bitmap = BitmapFactory.decodeStream(stream);

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();

                ivmenuimage.setImageBitmap(bitmap);


                encodedResume = Base64.encodeToString(byteArray, Base64.DEFAULT);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                if (stream != null)
                    try {
                        stream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
        } else {
            Toast.makeText(getContext(), "error in image loading", Toast.LENGTH_LONG).show();

        }
    }

    }

