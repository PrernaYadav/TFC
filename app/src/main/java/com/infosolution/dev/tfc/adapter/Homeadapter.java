package com.infosolution.dev.tfc.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.infosolution.dev.tfc.R;
import com.infosolution.dev.tfc.model.Home;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by SHreyansh Srivastava on 1/18/2018.
 */

public class Homeadapter extends RecyclerView.Adapter<Homeadapter.MyViewHolder> {

    private ArrayList<Home> homeList;

    public Homeadapter(ArrayList<Home> homeList, Context context, Activity activity) {
        this.homeList = homeList;
        this.context = context;
        this.activity = activity;
    }

    Context context;
    private Activity activity;

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


        Glide.with(activity).load(home.getProimage()).into(holder.ivproimage);
        Glide.with(activity).load(home.getAvailimg()).into(holder.ivavail);
        Glide.with(activity).load(home.getFav()).into(holder.ivfav);

    }

    @Override
    public int getItemCount() {
        if (homeList == null)
            return 0;
        return homeList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivproimage, ivavail, ivfav;
        TextView tvusername, tvproname, tvprice, tvquantity, tvtiming, tvcount;
        Context ctx;
        ArrayList<Home> home = new ArrayList<Home>();
//        public Button btnJoinEvent;

        public MyViewHolder(final View view, final Context ctx, ArrayList<Home> home) {
            super(view);
            this.home = home;
            this.ctx = ctx;

            ivproimage = (ImageView) view.findViewById(R.id.iv_proimagee);
            ivavail = (ImageView) view.findViewById(R.id.iv_avail);
            ivfav = (ImageView) view.findViewById(R.id.iv_fav);
            tvproname = (TextView) view.findViewById(R.id.tv_proname);
            tvusername = (TextView) view.findViewById(R.id.tv_username);
            tvtiming = (TextView) view.findViewById(R.id.tv_timing);
            tvprice = (TextView) view.findViewById(R.id.tv_price);
            tvquantity = (TextView) view.findViewById(R.id.tv_quantity);
            tvcount = (TextView) view.findViewById(R.id.tv_count);

        }


    }
}




