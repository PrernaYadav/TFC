package com.infosolution.dev.tfc.business_fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
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
import com.infosolution.dev.tfc.adapter.OrderHistoryAdapter;
import com.infosolution.dev.tfc.fragment.HomeFragment;
import com.infosolution.dev.tfc.model.Home;
import com.infosolution.dev.tfc.model.OrderHistoryModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderHistoryFragment extends Fragment {

   private RecyclerView rvhistory;
    private OrderHistoryAdapter orderHistoryAdapter;

    private ArrayList<OrderHistoryModel> historyModelArrayList;

    private String UserId;

    public OrderHistoryFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_order_history, container, false);


        final SharedPreferences prefs = getActivity().getSharedPreferences("useriddsign", MODE_PRIVATE);
        UserId = prefs.getString("userid", null);

        rvhistory=v.findViewById(R.id.rc_orderhis);


        rvhistory.setLayoutManager(new LinearLayoutManager(getContext()));
        rvhistory.setHasFixedSize(true);
        rvhistory.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));


      /*  DividerItemDecoration itemDecorator = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.divider));*/

        int numberOfColumns = 2;
        rvhistory.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns));

        rvhistory.setAdapter(orderHistoryAdapter);
        historyModelArrayList= new ArrayList<>();
        orderHistoryAdapter = new OrderHistoryAdapter(historyModelArrayList, getContext(), getActivity());
       FetchHistoryOrder();

        return v;
    }

    private void FetchHistoryOrder() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigInfo.orderHistory,
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
                                String    ProductName = object.getString("menu_name");
                                String  Quantity = object.getString("quantity");
                                String  Logo = object.getString("logo");
                                String   Price = object.getString("price");
                                String   Date = object.getString("date");
                                String   UserName = object.getString("user_name");

                                OrderHistoryModel orderHistoryModel= new OrderHistoryModel();
                                orderHistoryModel.setProname(ProductName);
                                orderHistoryModel.setQuantity(Quantity);
                                orderHistoryModel.setPrice(Price);
                                orderHistoryModel.setDate(Date);
                                orderHistoryModel.setUsername(UserName);
                                orderHistoryModel.setLogo(Logo);
                                historyModelArrayList.add(orderHistoryModel);



                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        rvhistory.setAdapter(orderHistoryAdapter);
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
                params.put("user_id", UserId);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

}
