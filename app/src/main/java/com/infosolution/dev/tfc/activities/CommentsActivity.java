package com.infosolution.dev.tfc.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.infosolution.dev.tfc.Class.ConfigInfo;
import com.infosolution.dev.tfc.Class.Jsonparse;
import com.infosolution.dev.tfc.R;
import com.infosolution.dev.tfc.adapter.CommentsAdapter;
import com.infosolution.dev.tfc.adapter.Homeadapter;
import com.infosolution.dev.tfc.adapter.OrderHistoryAdapter;
import com.infosolution.dev.tfc.business.Navigation;
import com.infosolution.dev.tfc.fragment.HomeFragment;
import com.infosolution.dev.tfc.model.Comments;
import com.infosolution.dev.tfc.model.Home;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.security.AccessController.getContext;

public class CommentsActivity extends AppCompatActivity {

    RecyclerView rvcomments;
    CommentsAdapter commentsAdapter;
    View view;
    private ArrayList<Comments> commentsList;
    ProgressDialog pd;
    String res;


   private String Name,Rating,Comment,ResId,MenuId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        rvcomments = findViewById(R.id.rv_comments);





        //fetching values from sharedpreferece

        final SharedPreferences prefss = getSharedPreferences("resmenuDetails", MODE_PRIVATE);
        MenuId = prefss.getString("menuid", null);
        ResId = prefss.getString("resid", null);

        rvcomments.setLayoutManager(new LinearLayoutManager(CommentsActivity.this));
        rvcomments.setHasFixedSize(true);
        rvcomments.setAdapter(commentsAdapter);
        commentsList= new ArrayList<>();
        commentsAdapter = new CommentsAdapter(commentsList, this, this);

        FetchComments();


        final SharedPreferences prefs = getSharedPreferences("Res", MODE_PRIVATE);
        res = prefs.getString("responce", null);
        Log.i("bbb",""+res);

        new Fet().execute();



    }












    public void FetchComments() {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://thefoodcircle.co.uk/restaurant/demo/web-service/res_menu.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response..........", response);
                       // Toast.makeText(CommentsActivity.this, response.toString(), Toast.LENGTH_LONG).show();

                        Log.i("ressssss..",""+response.toString());


                         SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Res", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("responcee", response.toString());
                        editor.apply();





                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CommentsActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {


                Map<String, String> params = new HashMap<String, String>();
                params.put("resid", MenuId);
                params.put("menuid", ResId);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(CommentsActivity.this);
        requestQueue.add(stringRequest);


    }


    public class Fet extends AsyncTask<Object, Object, Boolean> {

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(CommentsActivity.this);
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
                    JSONObject jsono = new JSONObject(res);
                    JSONArray jarray = jsono.getJSONArray("data");

                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);


                        JSONObject jsono1 = new JSONObject(res);
                        jarray = jsono.getJSONArray("data");
                        JSONArray jarray1 = object.getJSONArray("comments_data");

                        for (int j = 0; j < jsono.length(); j++) {
                            JSONObject object1 = jarray1.getJSONObject(j);

                            final String Namee = object1.getString("username");
                            final String Ratingg = object1.getString("rating");
                            final String Commentt = object1.getString("comment");


                            Comments comments1 = new Comments();


                            comments1.setUsername(Namee);
                            comments1.setRating(Ratingg);
                            comments1.setComments(Commentt);
                            comments1.setProfileimage(R.drawable.fav);


                            commentsList.add(comments1);

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
            rvcomments.setAdapter(commentsAdapter);
            commentsAdapter.notifyDataSetChanged();
        }
    }



    }


