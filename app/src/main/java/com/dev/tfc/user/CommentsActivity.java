package com.dev.tfc.user;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dev.tfc.R;
import com.dev.tfc.adapter.CommentsAdapter;
import com.dev.tfc.model.Comments;

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
import java.util.Map;

public class CommentsActivity extends AppCompatActivity {

    RecyclerView rvcomments;
    CommentsAdapter commentsAdapter;
  private   View view;
    private ArrayList<Comments> commentsList;
    ProgressDialog pd;
    String res;
    private ImageView ivbackk;
    private TextView textView;
    private TextView emptyView;




   private String Name,Rating,Comment,ResId,MenuId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);


        rvcomments = findViewById(R.id.rv_comments);

        view=findViewById(R.id.ab_comments);
        ivbackk=findViewById(R.id.iv);
        ivbackk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        textView=findViewById(R.id.tv);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText("Change Password");
            }
        });


        emptyView = findViewById(R.id.tv_empty);



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

        new Fet().execute();



    }

    public void FetchComments() {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://thefoodcircle.co.uk/restaurant/demo/web-service/res_menu.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response..........", response);
                       // Toast.makeText(CommentsActivity.this, response.toString(), Toast.LENGTH_LONG).show();



                        JSONObject jsono = null;
                        try {
                            JSONObject json_response = new JSONObject(response);
                            String status = json_response.getString("status");

                            if (status.equals("success"))
                            {

                                rvcomments.setVisibility(View.VISIBLE);
                                emptyView.setVisibility(View.GONE);
                                // Toast.makeText(getContext(), "successs", Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                //  Toast.makeText(getContext(),"failed"+ status, Toast.LENGTH_LONG).show();
                                rvcomments.setVisibility(View.GONE);
                                emptyView.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.i("ressssss..",""+response.toString());

                        res=response;

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
                params.put("resid", ResId);
                params.put("menuid", MenuId);

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
                String dataurl = "http://thefoodcircle.co.uk/restaurant/demo/web-service/res_menu.php";
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

                        for (int j = 0; j < jarray1.length(); j++) {
                            JSONObject object1 = jarray1.getJSONObject(j);
                            String Namee = object1.getString("username");
                            float Ratingg = object1.getInt("rating");
                            String Commentt = object1.getString("comment");
                            String Userimage= object1.getString("userimage");


                            Comments comments1 = new Comments();
                            comments1.setUsername(Namee);
                            comments1.setRating(Ratingg);
                            comments1.setComments(Commentt);
                            comments1.setProfileimage(Userimage);
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


