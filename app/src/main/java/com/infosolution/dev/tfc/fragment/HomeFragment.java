package com.infosolution.dev.tfc.fragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.infosolution.dev.tfc.Class.ConfigInfo;
import com.infosolution.dev.tfc.R;
import com.infosolution.dev.tfc.activities.MainActivity;
import com.infosolution.dev.tfc.adapter.Homeadapter;
import com.infosolution.dev.tfc.model.Home;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    RecyclerView rcview;
    Homeadapter homeadapter;
//    private List<Home> homeList;
    String city;
    private ArrayList<Home> homeList;
    ProgressDialog pd;

    String Proname;
    String  Proimage;
   String Username;
    String Timing;
    String Price;
    String Quantity;
    String Availimg;
    String Count;
    String Fav;


    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;

    public HomeFragment() {
    }


   /* public HomeFragment() {
        // Required empty public constructor
    }*/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_home, container, false);


        //fetching city
        final SharedPreferences prefs = getActivity().getSharedPreferences("City", MODE_PRIVATE);
        city = prefs.getString("city", null);

        Log.i("city",""+city);



        rcview=v.findViewById(R.id.rc_home);


        rcview.setLayoutManager(new LinearLayoutManager(getContext()));
        rcview.setHasFixedSize(true);

        rcview.setAdapter(homeadapter);
        homeList= new ArrayList<>();
        homeadapter = new Homeadapter(homeList, getContext(), getActivity());
        new FetchEventPreviousDataTask().execute();




        return v;
    }


    public class FetchEventPreviousDataTask extends AsyncTask<Object, Object, Boolean> {

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
                    JSONObject jsono = new JSONObject(data);
                    JSONArray jarray = jsono.getJSONArray("restaurents");

                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);

                        Username= object.getString("name");

                        Log.i("resname",""+Username);
                        Home home= new Home();
                        home.setUsername(Username);


                        JSONObject jsono1 = new JSONObject(data);
                        jarray = jsono.getJSONArray("restaurents");
                        JSONArray jarray1 = object.getJSONArray("menu");

                        for (int j = 0; j < jarray.length(); j++) {
                            JSONObject object1 = jarray1.getJSONObject(j);

                            Proname = object1.getString("menu_name");
                            Timing = object1.getString("collection_time");
                            Price = object1.getString("menu_rate");
                            Quantity = object1.getString("quantity_left");
                            Proimage = object1.getString("img1");
                            Availimg = object1.getString("img2");
                            Fav = object1.getString("img3");

                            home.setProname(Proname);
                            home.setTiming(Timing);
                            home.setPrice(Price);
                            home.setQuantity(Quantity);
                            home.setProimage(Proimage);
                            home.setAvailimg(Availimg);
                            home.setFav(Fav);
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



