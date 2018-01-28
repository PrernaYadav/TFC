package com.infosolution.dev.tfc.business_fragments;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import com.infosolution.dev.tfc.adapter.NewOrderAdapter;
import com.infosolution.dev.tfc.adapter.UploadedMenuAdapter;
import com.infosolution.dev.tfc.model.Home;
import com.infosolution.dev.tfc.model.NewOrderModel;
import com.infosolution.dev.tfc.model.UploadedMenu;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewOrderdFragment extends Fragment {


    private RecyclerView rcnew;
    NewOrderAdapter newOrderAdapter;
    private ArrayList<NewOrderModel> newOrderModelList;
    private ProgressDialog pd;
    private String ResIdNew;
    private ProgressDialog pdLoading;
    private String responew;
    private TextView emptyView;



   /* public NewOrderdFragment() {
        // Required empty public constructor
    }*/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_new_orderd, container, false);


        final SharedPreferences prefs = getContext().getSharedPreferences("LogindataB", MODE_PRIVATE);
        ResIdNew = prefs.getString("resid", null);


        rcnew = v.findViewById(R.id.rc_newOrder);
        emptyView = v.findViewById(R.id.text_empty);


        rcnew.setLayoutManager(new LinearLayoutManager(getContext()));
        rcnew.setHasFixedSize(true);


        rcnew.setAdapter(newOrderAdapter);
        newOrderModelList = new ArrayList<>();
        newOrderAdapter = new NewOrderAdapter(newOrderModelList, getContext(), getActivity());
        FetchnewOrder();
        new NewOrder().execute();


        return v;
    }

    private void FetchnewOrder() {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://thefoodcircle.co.uk/restaurant/demo/web-service/orderDetail.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSONObject jsono = null;
                        try {
                            JSONObject json_response = new JSONObject(response);
                            String status = json_response.getString("status");

                            if (status.equals("success"))
                            {

                                rcnew.setVisibility(View.VISIBLE);
                                emptyView.setVisibility(View.GONE);
                               // Toast.makeText(getContext(), "successs", Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                              //  Toast.makeText(getContext(),"failed"+ status, Toast.LENGTH_LONG).show();
                                rcnew.setVisibility(View.GONE);
                                emptyView.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }





                        responew = response;
                        Log.i("respon", "" + responew);


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
                params.put("resid", ResIdNew);
                Log.i("mmm", "" + params);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }


    public class NewOrder extends AsyncTask<Object, Object, Boolean> {

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(getContext());
            pd.setMessage("loading");
            pd.show();
        }

        @Override
        protected Boolean doInBackground(Object... params) {
            try {
                String dataurl = "http://thefoodcircle.co.uk/restaurant/demo/web-service/res_list.php";
                HttpPost httppost = new HttpPost(dataurl);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httppost);
                Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
                int status = response.getStatusLine().getStatusCode();
                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);
                    JSONObject jsono = new JSONObject(responew);
                    JSONArray jarray = jsono.getJSONArray("data");

                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);

                        String Price = object.getString("menu_rate");
                        String Qty = object.getString("quantity");
                        String Phone = object.getString("phone");
                        String UserName = object.getString("name");
                        String MenuName = object.getString("menu_name");
                        String ColTime = object.getString("collection_time");
                        String Date = object.getString("date");
                        String stat = object.getString("status");

                        NewOrderModel newOrderModel = new NewOrderModel();
                        newOrderModel.setCollectionTime(ColTime);
                        newOrderModel.setUsername(UserName);
                        newOrderModel.setStatus(stat);
                        newOrderModel.setQty(Qty);
                        newOrderModel.setPhone(Phone);
                        newOrderModel.setMenuName(MenuName);
                        newOrderModel.setPrice(Price);
                        newOrderModel.setDate(Date);


                        newOrderModelList.add(newOrderModel);

                    }


                    return true;
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            pd.dismiss();
            rcnew.setAdapter(newOrderAdapter);
            newOrderAdapter.notifyDataSetChanged();
        }
    }


}


