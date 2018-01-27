package com.infosolution.dev.tfc.fragment;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.infosolution.dev.tfc.R;
import com.infosolution.dev.tfc.adapter.Homeadapter;
import com.infosolution.dev.tfc.model.Home;

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
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavFragment extends Fragment {

    private RecyclerView rcview;
    private Homeadapter homeadapter;
    //    private List<Home> homeList;
    private String city;
    private ArrayList<Home> homeList;
    private ProgressDialog pd;

    private String Proname;
    private String  Proimage;
    private String Username;
    private  String Timing;
    private  String Price;
    private String Quantity;
    private String Availimg;
    private String Count;
    private String Fav,ress;
    private String resID,menuID,Quantityyy,Foodtype;
    String UserId;


    public FavFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        View v= inflater.inflate(R.layout.fragment_fav, container, false);

        FetchFav();
        //fetching userid


        final SharedPreferences prefs = getContext().getSharedPreferences("useriddsign", MODE_PRIVATE);
        UserId = prefs.getString("userid", null);



        rcview=v.findViewById(R.id.rc_fav);


        rcview.setLayoutManager(new LinearLayoutManager(getContext()));
        rcview.setHasFixedSize(true);

        rcview.setAdapter(homeadapter);
        homeList= new ArrayList<>();
        homeadapter = new Homeadapter(homeList, getContext(), getActivity());



        new FetchEventPreviousDataTaskFav().execute();





  return v;
    }




    private void FetchFav() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://thefoodcircle.co.uk/restaurant/demo/web-service/my_fav_res.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response..........", response);
                        ress=response;
                        // Toast.makeText(CommentsActivity.this, response.toString(), Toast.LENGTH_LONG).show();

                        Log.i("ressssss..",""+response.toString());







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
                params.put("userid", UserId);
        Log.i("mmm",""+params);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    public class FetchEventPreviousDataTaskFav extends AsyncTask<Object, Object, Boolean> {

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
                    JSONObject jsono = new JSONObject(ress);
                    JSONArray jarray = jsono.getJSONArray("data");

                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);

                        Username= object.getString("name");
                        resID = object.getString("id");

                        Log.i("resname",""+Username);
                        Log.i("resId",""+resID);
                        Home home= new Home();
                        home.setUsername(Username);


                        JSONObject jsono1 = new JSONObject(ress);
                        jarray = jsono.getJSONArray("data");
                        JSONArray jarray1 = object.getJSONArray("menu");

                        for (int j = 0; j < jarray1.length(); j++) {
                            JSONObject object1 = jarray1.getJSONObject(j);

                            Proname = object1.getString("menu_name");
                            Timing = object1.getString("collection_time");
                            Price = object1.getString("menu_rate");
                            Quantity = object1.getString("quantity_left");
                            Proimage = object1.getString("img1");
                            Availimg = object1.getString("img2");
                            Fav = object1.getString("img3");
                            menuID = object1.getString("id");
                            Foodtype = object1.getString("food_type");
                            Log.i("menuId",""+menuID);



                            if (Foodtype.equals("Veg")){
                                home.setAvailimg(R.drawable.green);
                            }else if (Foodtype.equals("Non-Veg")){
                                home.setAvailimg(R.drawable.red);
                            }


                            home.setProname(Proname);
                            home.setTiming(Timing);
                            home.setPrice(Price);
                            home.setQuantity(Quantity);
                            home.setProimage(Proimage);
                           // home.setAvailimg(Availimg);
                            //home.setFav(R.id.iv_fav);
                            homeList.add(home);

                        }



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
            rcview.setAdapter(homeadapter);
            homeadapter.notifyDataSetChanged();
        }
    }



}
