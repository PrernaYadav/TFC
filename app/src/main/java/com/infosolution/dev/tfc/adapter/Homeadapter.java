package com.infosolution.dev.tfc.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.infosolution.dev.tfc.Class.ConfigInfo;
import com.infosolution.dev.tfc.R;
import com.infosolution.dev.tfc.activities.BuyNowActivity;
import com.infosolution.dev.tfc.model.Home;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by SHreyansh Srivastava on 1/18/2018.
 */

public class Homeadapter extends RecyclerView.Adapter<Homeadapter.MyViewHolder> {

    private ArrayList<Home> homeList;
    private String UserId,ResId,favstat;


    public Homeadapter(ArrayList<Home> homeList, Context context, Activity activity) {
        this.homeList = homeList;
        this.context = context;
        this.activity = activity;
    }

    Context context;
    private Activity activity;
private    String qty,price;

    @Override
    public Homeadapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_row, parent, false);
        return new MyViewHolder(view, context, homeList);
    }

    @Override
    public void onBindViewHolder(Homeadapter.MyViewHolder holder, int position) {
        final Home home = homeList.get(position);



        holder.tvproname.setText(homeList.get(position).getProname());
        holder.tvusername.setText(homeList.get(position).getUsername());
        holder.tvprice.setText(homeList.get(position).getPrice());
        holder.tvquantity.setText(homeList.get(position).getQuantity());
        holder.tvtiming.setText(homeList.get(position).getTiming());
        holder.tvcount.setText(homeList.get(position).getCount());
        holder.ress.setText(homeList.get(position).getRes());
        holder.favstatus.setText(homeList.get(position).getFavstatus());
        holder.ivfav.setImageResource(homeList.get(position).getFav());
        holder.ivavail.setImageResource(homeList.get(position).getAvailimg());
        holder.ivtrans.setImageResource(homeList.get(position).getTrans());

        Glide.with(activity).load(home.getProimage()).into(holder.ivproimage);
      //  Glide.with(activity).load(home.getAvailimg()).into(holder.ivavail);


        //Glide.with(activity).load(home.getFav()).into(holder.ivfav);

    }

    @Override
    public int getItemCount() {
        if (homeList == null)
            return 0;
        return homeList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivproimage, ivavail, ivfav,ivtrans;
        TextView tvusername, tvproname, tvprice, tvquantity, tvtiming, tvcount,ress,favstatus;
        Context ctx;
        ArrayList<Home> home = new ArrayList<Home>();

        Typeface typefaceregular = Typeface.createFromAsset(activity.getAssets(), "font/tahoma.ttf");
        Typeface typefacebold = Typeface.createFromAsset(activity.getAssets(), "font/tahomabd.ttf");


//        public Button btnJoinEvent;

        public MyViewHolder(final View view, final Context ctx, ArrayList<Home> home) {
            super(view);
            this.home = home;
            this.ctx = ctx;

            ivproimage = (ImageView) view.findViewById(R.id.iv_proimagee);
            ivtrans = (ImageView) view.findViewById(R.id.iv_trans);
            ivavail = (ImageView) view.findViewById(R.id.iv_avail);
            ivfav = (ImageView) view.findViewById(R.id.iv_fav);
            tvproname = (TextView) view.findViewById(R.id.tv_proname);
            tvusername = (TextView) view.findViewById(R.id.tv_username);
            tvtiming = (TextView) view.findViewById(R.id.tv_timing);
            tvprice = (TextView) view.findViewById(R.id.tv_price);
            tvquantity = (TextView) view.findViewById(R.id.tv_quantity);
            tvcount = (TextView) view.findViewById(R.id.tv_count);
            ress = (TextView) view.findViewById(R.id.res);
            favstatus = (TextView) view.findViewById(R.id.fav_status);
          /*  qty=tvquantity.getText().toString();
            price=tvprice.getText().toString();*/

          tvproname.setTypeface(typefacebold);
          tvprice.setTypeface(typefaceregular);
          tvquantity.setTypeface(typefaceregular);
          tvtiming.setTypeface(typefaceregular);
          tvusername.setTypeface(typefaceregular);

            ivfav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int positions=getAdapterPosition();

                    ResId=homeList.get(positions).getRes();
                    favstat=homeList.get(positions).getFavstatus();



                    int favv = Integer.parseInt(favstat);


                    if (favv == 1) {
                        favstat="0";

                    } else if (favv == 0){
                        favstat="1";

                    }

                    ivfav.setImageResource(R.drawable.favselectedicon);
                    Fav();
                }
            });



            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  int position=getAdapterPosition();

                    Intent intent = new Intent(activity, BuyNowActivity.class);
                    intent.putExtra("qty",homeList.get(position).getQuantity());
                    intent.putExtra("price",homeList.get(position).getPrice());
                    intent.putExtra("time",homeList.get(position).getTiming());
                    intent.putExtra("proimage",homeList.get(position).getProimage());
                    intent.putExtra("proname",homeList.get(position).getProname());
                    intent.putExtra("resname",homeList.get(position).getUsername());
                  activity.startActivity(intent);
                }
            });

        }


    }

    private void Fav() {


        final SharedPreferences prefs = activity.getSharedPreferences("useriddsign", MODE_PRIVATE);
        UserId = prefs.getString("userid", null);



        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigInfo.AddtoFav,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(activity,response.toString(),Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                      //  Toast.makeText(activity, error.toString(), Toast.LENGTH_LONG).show();

//                        pdLoading.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();

                params.put("userid", UserId);
                params.put("resid", ResId);
                params.put("favorite_status",favstat );


                Log.i("paramsssad",""+params);


                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);


    }

    public class MyViewHolderNew {
    }
}




