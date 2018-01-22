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
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.infosolution.dev.tfc.Class.ConfigInfo;
import com.infosolution.dev.tfc.Class.RecyclerItemClickListener;
import com.infosolution.dev.tfc.R;
import com.infosolution.dev.tfc.activities.BuyNowActivity;
import com.infosolution.dev.tfc.activities.MainActivity;
import com.infosolution.dev.tfc.activities.ProductPageActivity;
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

    private RecyclerView rcview;
    private Homeadapter homeadapter;
    //    private List<Home> homeList;
    private String city;
    private ArrayList<Home> homeList;
    private ProgressDialog pd;

    private String Proname;
    private String Proimage;
    private String Username;
    private String Timing;
    private String Price;
    private String Quantity;
    private String Availimg, CollectionTime, Logoo, Addresss;
    private String Count;
    private String Fav;
    private String resID, menuID, Quantityyy;
    private EditText etsearchcity;
    // private String qty,price;

    private String ressss, UserId, fav, resuser;


    public HomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);

Intent intent=getActivity().getIntent();
city=intent.getStringExtra("citttyy");


        final SharedPreferences prefss = getContext().getSharedPreferences("useriddsign", MODE_PRIVATE);
        UserId = prefss.getString("userid", null);
        UserId = prefss.getString("userid", null);

        etsearchcity = v.findViewById(R.id.et_searchcity);


        etsearchcity.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    city = etsearchcity.getText().toString();
                    checkString();
                    return true;
                }
                return false;
            }
        });


        //fetching city
       /* final SharedPreferences prefs = getActivity().getSharedPreferences("City", MODE_PRIVATE);
        city = prefs.getString("city", null);*/

        Log.i("city", "" + city);


        rcview = v.findViewById(R.id.rc_home);
        //checking city is empty or not and hit api accordingly
        checkString();





        rcview.setLayoutManager(new LinearLayoutManager(getContext()));
        rcview.setHasFixedSize(true);

        rcview.setAdapter(homeadapter);
        homeList = new ArrayList<>();
        homeadapter = new Homeadapter(homeList, getContext(), getActivity());









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
                    JSONObject jsono = new JSONObject(resuser);
                    JSONArray jarray = jsono.getJSONArray("restaurents");

                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);

                        Username = object.getString("name");
                        resID = object.getString("id");
                        Logoo = object.getString("logo");
                        Addresss = object.getString("address");
                        fav = object.getString("fav_staus");
                        int favv = Integer.parseInt(fav);


                        SharedPreferences sharedPreferencess = getContext().getSharedPreferences("resmenuDetailss", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferencess.edit();
                        editor.putString("log", Logoo);
                        editor.putString("add", Addresss);
                        editor.commit();

                        Log.i("resname", "" + Username);
                        Log.i("resId", "" + resID);
                        Home home = new Home();

                        if (favv == 1) {

                            home.setFav(R.drawable.favselectedicon);
                        } else {
                            home.setFav(R.drawable.fav);
                        }
                        home.setUsername(Username);
                        home.setRes(resID);


                        JSONObject jsono1 = new JSONObject(resuser);
                        jarray = jsono.getJSONArray("restaurents");
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
                            CollectionTime = object1.getString("collection_time");
                            Log.i("menuId", "" + menuID);

                            SharedPreferences sharedPreferences = getContext().getSharedPreferences("resmenuDetails", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editorr = sharedPreferences.edit();
                            editorr.putString("name", Proname);
                            editorr.putString("resid", resID);
                            editorr.putString("menuid", menuID);
                            editorr.putString("quantityyy", Quantity);
                            editorr.putString("ct", CollectionTime);
                            editorr.commit();


                            home.setProname(Proname);
                            home.setTiming(Timing);
                            home.setPrice(Price);
                            home.setQuantity(Quantity);
                            home.setProimage(Proimage);
                            home.setAvailimg(Availimg);


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


    private void Fetchcity() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://thefoodcircle.co.uk/restaurant/demo/web-service/search_cityname.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response..........", response);
                        ressss = response;
                        // Toast.makeText(CommentsActivity.this, response.toString(), Toast.LENGTH_LONG).show();

                        Log.i("ressssss..", "" + response.toString());


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
                params.put("city", city);
                params.put("userid", UserId);

                Log.i("par", "" + params);


                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);


    }

    public class FetchEventPreviousData extends AsyncTask<Object, Object, Boolean> {

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(getContext());
            pd.setMessage("loading");
            pd.show();
        }

        @Override
        protected Boolean doInBackground(Object... params) {
            try {
              /*  String dataurl = "http://thefoodcircle.co.uk/restaurant/demo/web-service/res_list.php";
                HttpPost httppost = new HttpPost(dataurl);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httppost);
                Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
                int status = response.getStatusLine().getStatusCode();
                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);*/
                    JSONObject jsono = new JSONObject(ressss);
                    JSONArray jarray = jsono.getJSONArray("restaurents");

                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);

                        Username = object.getString("name");
                        resID = object.getString("id");
                        Logoo = object.getString("logo");
                        Addresss = object.getString("address");
                        fav = object.getString("fav_staus");
                        int favv = Integer.parseInt(fav);

                        SharedPreferences sharedPreferencess = getContext().getSharedPreferences("resmenuDetailss", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferencess.edit();
                        editor.putString("log", Logoo);
                        editor.putString("add", Addresss);
                        editor.commit();

                        Log.i("resname", "" + Username);
                        Log.i("resId", "" + resID);
                        Home home = new Home();


                        if (favv == 1) {

                            home.setFav(R.drawable.favselectedicon);
                        } else {
                            home.setFav(R.drawable.fav);
                        }
                        home.setUsername(Username);
                        home.setRes(resID);


                        JSONObject jsono1 = new JSONObject(resuser);
                        jarray = jsono.getJSONArray("restaurents");
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
                            CollectionTime = object1.getString("collection_time");
                            menuID = object1.getString("id");
                            Log.i("menuId", "" + menuID);

                            SharedPreferences sharedPreferences = getContext().getSharedPreferences("resmenuDetails", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editorr = sharedPreferences.edit();
                            editorr.putString("name", Proname);
                            editorr.putString("resid", resID);
                            editorr.putString("menuid", menuID);
                            editorr.putString("ct", CollectionTime);
                            editorr.putString("quantityyy", Quantity);
                            editorr.commit();


                            home.setProname(Proname);
                            home.setTiming(Timing);
                            home.setPrice(Price);
                            home.setQuantity(Quantity);
                            home.setProimage(Proimage);
                            home.setAvailimg(Availimg);


                            //home.setFav(R.id.iv_fav);
                            homeList.add(home);

                        }


                    }


                    return true;
//                }
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

    public void checkString() {
        if (TextUtils.isEmpty(city)) {
            SendId();


        } else {
            Fetchcity();
            new FetchEventPreviousData().execute();

        }
    }

    private void SendId() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://thefoodcircle.co.uk/restaurant/demo/web-service/res_list.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response..........", response.toString());
                        resuser = response;
                        new FetchEventPreviousDataTask().execute();


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

                Log.i("parr", "" + params);


                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }


}
