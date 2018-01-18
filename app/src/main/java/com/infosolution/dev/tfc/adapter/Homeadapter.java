package com.infosolution.dev.tfc.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.infosolution.dev.tfc.R;
import com.infosolution.dev.tfc.model.Home;

import java.util.Collections;
import java.util.List;

/**
 * Created by SHreyansh Srivastava on 1/18/2018.
 */

public class Homeadapter extends RecyclerView.Adapter<Homeadapter.MyViewHolder> {

    List<Home> homeList= Collections.emptyList();
    Context context;

    public Homeadapter(List<Home> homeList, Context context) {
        this.homeList = homeList;
        this.context = context;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView ivproimage,ivavail,ivfav;
        TextView tvusername,tvproname,tvprice,tvquantity,tvtiming,tvcount;
        public MyViewHolder(View view) {
            super(view);
            ivproimage=(ImageView) view.findViewById(R.id.iv_proimagee);
            ivavail=(ImageView) view.findViewById(R.id.iv_avail);
            ivfav=(ImageView) view.findViewById(R.id.iv_fav);
            tvproname=(TextView) view.findViewById(R.id.tv_proname);
            tvusername=(TextView) view.findViewById(R.id.tv_username);
            tvtiming=(TextView) view.findViewById(R.id.tv_timing);
            tvprice=(TextView) view.findViewById(R.id.tv_price);
            tvquantity=(TextView) view.findViewById(R.id.tv_quantity);
            tvcount=(TextView) view.findViewById(R.id.tv_count);
        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_row, parent, false);

        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.ivproimage.setImageResource(homeList.get(position).getProimage());
        holder.ivavail.setImageResource(homeList.get(position).getAvailimg());
        holder.ivfav.setImageResource(homeList.get(position).getFav());
        holder.tvproname.setText(homeList.get(position).getProname());
        holder.tvusername.setText(homeList.get(position).getUsername());
        holder.tvprice.setText(homeList.get(position).getPrice());
        holder.tvquantity.setText(homeList.get(position).getQuantity());
        holder.tvtiming.setText(homeList.get(position).getTiming());
        holder.tvcount.setText(homeList.get(position).getCount());



    }
    @Override
    public int getItemCount()
    {
        return homeList.size();
    }
}
