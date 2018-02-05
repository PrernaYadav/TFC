package com.dev.tfc.business_fragments;


import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dev.tfc.R;
import com.dev.tfc.adapter.OrderHistoryBusiAdapter;
import com.dev.tfc.model.OrderHistoryBusiModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderHisBusinessFragment extends Fragment {

  private   RecyclerView rvhistorybusi;
    private OrderHistoryBusiAdapter orderHistoryAdapter;

    private ArrayList<OrderHistoryBusiModel> historyModelArrayList;

    private String ResId,im;
    private TextView emptyView;


    public OrderHisBusinessFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_order_his, container, false);


        Bitmap bmap  = BitmapFactory.decodeResource(getResources(),  R.drawable.icon);
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bmap.compress(Bitmap.CompressFormat.JPEG, 100, bao);
        byte [] ba = bao.toByteArray();
          im= Base64.encodeToString(ba,Base64.DEFAULT);


        final SharedPreferences prefs = getActivity().getSharedPreferences("LogindataB", MODE_PRIVATE);
        ResId = prefs.getString("resid", null);

        rvhistorybusi=v.findViewById(R.id.rc_orderhisbusi);

        emptyView = v.findViewById(R.id.emptyhisbusi);

        rvhistorybusi.setLayoutManager(new LinearLayoutManager(getContext()));
        rvhistorybusi.setHasFixedSize(true);
        rvhistorybusi.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));

        int numberOfColumns = 2;
        rvhistorybusi.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns));

        rvhistorybusi.setAdapter(orderHistoryAdapter);
        historyModelArrayList= new ArrayList<>();
        orderHistoryAdapter = new OrderHistoryBusiAdapter(historyModelArrayList, getContext(), getActivity());
        FetchHistoryOrder();


        return v;
    }

    private void FetchHistoryOrder() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://thefoodcircle.co.uk/restaurant/demo/web-service/resturent_detail_by_id.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response..........", response);
                        // Toast.makeText(getContext(), response.toString(), Toast.LENGTH_LONG).show();









                        try {

                            JSONObject jsono = new JSONObject(response);






                            JSONArray jarray = jsono.getJSONArray("data");
                            for (int i = jarray.length()-1; i >= 0 ; i--) {
                                JSONObject object = jarray.getJSONObject(i);
                                String    ProductName = object.getString("menu_name");
                                String  Quantity = object.getString("quantity");
                             //   String  Logo = object.getString("logo");
                                String   Price = object.getString("price");
                                String   Date = object.getString("date");
                                String   UserName = object.getString("name");
                                String   Deliver = object.getString("del_id");


                                OrderHistoryBusiModel orderHistoryModel= new OrderHistoryBusiModel();
                                orderHistoryModel.setProname(ProductName);
                                orderHistoryModel.setQuantity(Quantity);
                                orderHistoryModel.setPrice(Price);
                                orderHistoryModel.setDate(Date);
                                orderHistoryModel.setUsername(UserName);
                                orderHistoryModel.setDel(Deliver);
                               orderHistoryModel.setLogo(R.drawable.icon);



                                if (Deliver.equals("1")){
                                    orderHistoryModel.setDeliver("Delivered");
                                    orderHistoryModel.setDelimg(R.drawable.deliver);
                                }else if (Deliver.equals("0")){
                                    orderHistoryModel.setDeliver("Undeliver");
                                    orderHistoryModel.setDelimg(R.drawable.undeliver);
                                }
                                historyModelArrayList.add(orderHistoryModel);



                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        rvhistorybusi.setAdapter(orderHistoryAdapter);
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
                params.put("res_id", ResId);
                Log.i("asdf",""+params);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

}
