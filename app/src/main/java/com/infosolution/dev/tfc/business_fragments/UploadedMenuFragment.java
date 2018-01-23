package com.infosolution.dev.tfc.business_fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.infosolution.dev.tfc.Class.ConfigInfo;
import com.infosolution.dev.tfc.R;
import com.infosolution.dev.tfc.adapter.Homeadapter;
import com.infosolution.dev.tfc.adapter.UploadedMenuAdapter;
import com.infosolution.dev.tfc.model.Home;
import com.infosolution.dev.tfc.model.OrderHistoryModel;
import com.infosolution.dev.tfc.model.UploadedMenu;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class UploadedMenuFragment extends Fragment {

    RecyclerView rcview;
    UploadedMenuAdapter uploadedMenuAdapter;
    private List<UploadedMenu> uploadedMenuList;

    private  String ResIdB;

   /* public UploadedMenuFragment() {
        // Required empty public constructor
    }*/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_uploaded_menu, container, false);

        final SharedPreferences prefs = getContext().getSharedPreferences("LogindataB", MODE_PRIVATE);
        ResIdB = prefs.getString("resid", null);

        rcview = v.findViewById(R.id.rc_uploadedmenu);


        uploadedMenuList = fill_with_data();


        uploadedMenuAdapter = new UploadedMenuAdapter(uploadedMenuList, getContext());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rcview.getContext(),
                linearLayoutManager.getOrientation());
        rcview.addItemDecoration(dividerItemDecoration);


        rcview.setLayoutManager(linearLayoutManager);
        rcview.setAdapter(uploadedMenuAdapter);


        return v;
    }

    private List<UploadedMenu> fill_with_data() {


        final List<UploadedMenu> upload = new ArrayList<>();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigInfo.uploadedmenu,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response..........", response);
                        // Toast.makeText(getContext(), response.toString(), Toast.LENGTH_LONG).show();


                        try {

                            JSONObject jsono = new JSONObject(response);
                            JSONArray jarray = jsono.getJSONArray("data");
                            for (int i = 0; i < jarray.length(); i++) {
                                JSONObject object = jarray.getJSONObject(i);
                                String ProductName = object.getString("menu_name");
                                String Timing = object.getString("collection_time");
                                String Logo = object.getString("img1");
                                String Price = object.getString("menu_rate");
                                String Qty = object.getString("quantity_left");

                                upload.add(new UploadedMenu(ProductName, Timing, Qty, Price, R.drawable.icon));



                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                       // rvhistory.setAdapter(orderHistoryAdapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {


                Map<String, String> params = new HashMap<String, String>();
                params.put("res_id", ResIdB);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);


        //upload.add(new UploadedMenu("Biryani", "Ankit", "8:00-8:30PM", "124", R.drawable.icon));


        return upload;

    }

}
