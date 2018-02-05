package com.dev.tfc.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dev.tfc.R;
import com.dev.tfc.model.OrderHistoryModel;

import java.util.ArrayList;

/**
 * Created by amit on 1/19/2018.
 */

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.MyViewHolder> {
    private ArrayList<OrderHistoryModel> orderHistoryModelArrayList;
    Context context;
    private Activity activity;
    private String Del;

    public OrderHistoryAdapter(ArrayList<OrderHistoryModel> orderHistoryModelArrayList, Context context, Activity activity) {
        this.orderHistoryModelArrayList = orderHistoryModelArrayList;
        this.context = context;
        this.activity = activity;
    }

    @Override
    public OrderHistoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderhistory_row, parent, false);
        return new MyViewHolder(view, context, orderHistoryModelArrayList);

    }

    /*@Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }*/

    @Override
    public void onBindViewHolder(OrderHistoryAdapter.MyViewHolder holder, int position) {

        final OrderHistoryModel orderHistoryModel = orderHistoryModelArrayList.get(position);


        holder.tvproname.setText(orderHistoryModelArrayList.get(position).getProname());
      //  holder.tvusername.setText(orderHistoryModelArrayList.get(position).getUsername());
        holder.tvprice.setText(orderHistoryModelArrayList.get(position).getPrice());
        holder.tvquantity.setText(orderHistoryModelArrayList.get(position).getQuantity());
        holder.tvdate.setText(orderHistoryModelArrayList.get(position).getDate());
        holder.tvdeliver.setText(orderHistoryModelArrayList.get(position).getDeliver());
        holder.del.setText(orderHistoryModelArrayList.get(position).getDel());
        holder.delimg.setImageResource(orderHistoryModelArrayList.get(position).getDelimg());
       // holder.ivlogohis.setImageResource(orderHistoryModelArrayList.get(position).getLogo());


        Glide.with(activity).load(orderHistoryModel.getLogo()).into(holder.ivlogohis);


        Del= orderHistoryModelArrayList.get(position).getDeliver();

    }

    @Override
    public int getItemCount() {
        if (orderHistoryModelArrayList == null)
            return 0;
        return orderHistoryModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        Context ctx;
        ArrayList<OrderHistoryModel> orderHistoryModels = new ArrayList<OrderHistoryModel>();
        TextView tvproname, tvusername, tvprice, tvquantity, tvdate, tvdeliver,del;
        ImageView ivlogohis,delimg;

        Typeface typefaceregular = Typeface.createFromAsset(activity.getAssets(), "font/tahoma.ttf");
        Typeface typefacebold = Typeface.createFromAsset(activity.getAssets(), "font/tahomabd.ttf");


        public MyViewHolder(View view, Context ctx, ArrayList<OrderHistoryModel> orderHistoryModels) {
            super(view);
            this.ctx = ctx;
            this.orderHistoryModels = orderHistoryModels;
            int pos=getAdapterPosition();


            tvproname = view.findViewById(R.id.tv_pronamehis);
            del = view.findViewById(R.id.del);
            tvdeliver = view.findViewById(R.id.tv_deliverhis);
           // tvusername = view.findViewById(R.id.tv_usernamehis);
            tvprice = view.findViewById(R.id.tv_pricehis);
            tvquantity = (TextView) view.findViewById(R.id.tv_quantityhis);
            tvdate = (TextView) view.findViewById(R.id.tv_datehis);
            ivlogohis = view.findViewById(R.id.iv_logohis);
            delimg = view.findViewById(R.id.delimg);





            tvproname.setTypeface(typefacebold);
            tvdate.setTypeface(typefacebold);
//            tvusername.setTypeface(typefacebold);
            tvquantity.setTypeface(typefaceregular);
            tvprice.setTypeface(typefaceregular);
        }
    }
}
