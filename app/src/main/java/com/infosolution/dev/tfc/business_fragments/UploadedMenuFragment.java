package com.infosolution.dev.tfc.business_fragments;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
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
import com.infosolution.dev.tfc.business.LoginBusinessActivity;
import com.infosolution.dev.tfc.fragment.FavFragment;
import com.infosolution.dev.tfc.model.Home;
import com.infosolution.dev.tfc.model.OrderHistoryModel;
import com.infosolution.dev.tfc.model.UploadedMenu;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
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

    private RecyclerView rcupld;
    private UploadedMenuAdapter uploadedMenuAdapter;
    //    private List<Home> homeList;
    private String city;
    private ArrayList<UploadedMenu> uploadedMenuList;
    private ProgressDialog pd;

    private  String ResIdB;

    private String respon;

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

        rcupld = v.findViewById(R.id.rc_uploadedmenu);





        rcupld.setLayoutManager(new LinearLayoutManager(getContext()));
        rcupld.setHasFixedSize(true);

        rcupld.setAdapter(uploadedMenuAdapter);
        uploadedMenuList= new ArrayList<>();
        uploadedMenuAdapter = new UploadedMenuAdapter(uploadedMenuList, getContext(), getActivity());
        FetchValues();
        new uploaded().execute();

        return v;
    }



    private void FetchValues() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://thefoodcircle.co.uk/restaurant/demo/web-service/allmenu.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response..........", response);
                        respon=response;

                        Log.i("respon",""+respon);



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
                Log.i("mmm", "" + params);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);


    }
    public class uploaded extends AsyncTask<Object, Object, Boolean> {

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
                    JSONObject jsono = new JSONObject(respon);
                    JSONArray jarray = jsono.getJSONArray("data");

                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);

                        String ProductName = object.getString("menu_name");
                        String Timing = object.getString("collection_time");
                        String Logo = object.getString("img1");
                        String Price = object.getString("menu_rate");
                        String Qty = object.getString("quantity_left");
                        String foodtype= object.getString("food_type");

                        UploadedMenu uploadedMenu= new UploadedMenu();
                        uploadedMenu.setPronameupld(ProductName);
                        uploadedMenu.setTiminguplad(Timing);
                        uploadedMenu.setPriceupld(Price);
                        uploadedMenu.setQtyupld(Qty);
                        uploadedMenu.setImgupld(Logo);

                        if (foodtype.equals("Non-Veg")){
                            uploadedMenu.setImgveg(R.drawable.red);
                        }else if (foodtype.equals("Veg")){
                            uploadedMenu.setImgveg(R.drawable.green);
                        }
                        uploadedMenuList.add(uploadedMenu);

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
            rcupld.setAdapter(uploadedMenuAdapter);
            uploadedMenuAdapter.notifyDataSetChanged();
        }
    }


}
