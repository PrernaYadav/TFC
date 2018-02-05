package com.dev.tfc.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
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
import com.dev.tfc.R;
import com.dev.tfc.model.NewOrderModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Shreyansh Srivastava on 1/24/2018.
 */

public class NewOrderAdapter extends RecyclerView.Adapter<NewOrderAdapter.MyViewHolder> {


    private ArrayList<NewOrderModel> newOrderModelArrayList;
    private Context context;
    private Activity activity;
    private String ResIdNewA, UserIdNewA, MenuIdNewA, DelIdNewA;
    TextView tvready;

    public NewOrderAdapter(ArrayList<NewOrderModel> newOrderModelArrayList, Context context, Activity activity) {
        this.newOrderModelArrayList = newOrderModelArrayList;
        this.context = context;
        this.activity = activity;
    }


    @Override
    public NewOrderAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.neworder_row, parent, false);
        return new MyViewHolder(itemView, context, newOrderModelArrayList);
    }

    @Override
    public void onBindViewHolder(NewOrderAdapter.MyViewHolder holder, int position) {


        final NewOrderModel newOrderModel = newOrderModelArrayList.get(position);


        //  holder.ivmenu.setImageResource(uploadedMenuList.get(position).getImgupld());
        holder.tvusername.setText(newOrderModelArrayList.get(position).getUsername());
        holder.tvmenuname.setText(newOrderModelArrayList.get(position).getMenuName());
        holder.tvdate.setText(newOrderModelArrayList.get(position).getDate());
        holder.tvtime.setText(newOrderModelArrayList.get(position).getCollectionTime());
        holder.tvprice.setText(newOrderModelArrayList.get(position).getPrice());
        holder.tvqty.setText(newOrderModelArrayList.get(position).getQty());
        holder.tvphone.setText(newOrderModelArrayList.get(position).getPhone());
        holder.tvstatus.setText(newOrderModelArrayList.get(position).getStatus());
        holder.tvdelid.setText(newOrderModelArrayList.get(position).getDelid());
        holder.tvresid.setText(newOrderModelArrayList.get(position).getResIdNew());
        holder.tvmenuid.setText(newOrderModelArrayList.get(position).getMenuId());
        holder.tvuserid.setText(newOrderModelArrayList.get(position).getUserIdNew());


    }

    @Override
    public int getItemCount() {
        if (newOrderModelArrayList == null)

            return 0;
        return newOrderModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvusername, tvmenuname, tvdate, tvtime, tvprice, tvqty, tvphone, tvstatus, tvdelid, tvresid, tvmenuid, tvuserid;
        ImageView ivlogo, ivphone, ivmenuimage;

        Typeface typefaceregular = Typeface.createFromAsset(activity.getAssets(), "font/tahoma.ttf");
        Typeface typefacebold = Typeface.createFromAsset(activity.getAssets(), "font/tahomabd.ttf");

        public MyViewHolder(View itemView, Context context, final ArrayList<NewOrderModel> newOrderModelArrayList) {
            super(itemView);


            tvusername = itemView.findViewById(R.id.tv_usernamenew);
            tvmenuname = itemView.findViewById(R.id.tv_menunamenew);
            tvdate = itemView.findViewById(R.id.tv_datenew);
            tvtime = itemView.findViewById(R.id.tv_timenew);
            tvprice = itemView.findViewById(R.id.tv_pricenew);
            tvqty = itemView.findViewById(R.id.tv_quantitynew);
            tvphone = itemView.findViewById(R.id.tv_phone);
            tvstatus = itemView.findViewById(R.id.tv_status);
            tvdelid = itemView.findViewById(R.id.tv_delid);
            ivlogo = itemView.findViewById(R.id.iv_logonew);
            tvready = itemView.findViewById(R.id.ready);
            tvresid = itemView.findViewById(R.id.tv_resid);
            tvmenuid = itemView.findViewById(R.id.tv_menuid);
            tvuserid = itemView.findViewById(R.id.tv_userid);
            ivmenuimage = itemView.findViewById(R.id.iv_menuimgnew);
            ivphone = itemView.findViewById(R.id.iv_call);

            tvusername.setTypeface(typefaceregular);
            tvdate.setTypeface(typefaceregular);
            tvtime.setTypeface(typefaceregular);
            tvprice.setTypeface(typefacebold);
            tvqty.setTypeface(typefacebold);
            tvmenuname.setTypeface(typefaceregular);


            tvready.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int Position = getAdapterPosition();

                    UserIdNewA = newOrderModelArrayList.get(Position).getUserIdNew();
                    ResIdNewA = newOrderModelArrayList.get(Position).getResIdNew();
                    MenuIdNewA = newOrderModelArrayList.get(Position).getMenuId();
                    DelIdNewA = newOrderModelArrayList.get(Position).getDelid();


                    tvready.setText("Delivered");


                    OnReadyClick();
                }
            });


            ivphone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Toast.makeText(activity, "clicked", Toast.LENGTH_LONG).show();
                    int position = getAdapterPosition();

                    String ph = newOrderModelArrayList.get(position).getPhone();


                    dialContactPhone(ph);
                }
            });
        }
    }

    private void OnReadyClick() {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://thefoodcircle.co.uk/restaurant/demo/web-service/delever_by_id.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //Toast.makeText(activity, response.toString(), Toast.LENGTH_LONG).show();


                        Log.i("respon", "" + response);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                     //   Toast.makeText(activity, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {


                Map<String, String> params = new HashMap<String, String>();
                params.put("res_id", ResIdNewA);
                params.put("user_id", UserIdNewA);
                params.put("menu_id", MenuIdNewA);
                params.put("delever_id", DelIdNewA);


                Log.i("readyclick", "" + params);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);


    }

    private void dialContactPhone(final String s) {

        activity.startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", s, null)));
    }
}
