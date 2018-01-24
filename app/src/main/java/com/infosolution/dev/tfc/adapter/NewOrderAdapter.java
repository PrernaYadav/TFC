package com.infosolution.dev.tfc.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.infosolution.dev.tfc.R;
import com.infosolution.dev.tfc.model.NewOrderModel;
import com.infosolution.dev.tfc.model.UploadedMenu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Shreyansh Srivastava on 1/24/2018.
 */

public class NewOrderAdapter extends RecyclerView.Adapter<NewOrderAdapter.MyViewHolder> {



    private ArrayList<NewOrderModel> newOrderModelArrayList;
    Context context;
    private Activity activity;

    public NewOrderAdapter(ArrayList<NewOrderModel> newOrderModelArrayList, Context context, Activity activity) {
        this.newOrderModelArrayList = newOrderModelArrayList;
        this.context = context;
        this.activity = activity;
    }


    @Override
    public NewOrderAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.neworder_row, parent, false);
        return new MyViewHolder(itemView,context,newOrderModelArrayList);
    }

    @Override
    public void onBindViewHolder(NewOrderAdapter.MyViewHolder holder, int position) {


        final  NewOrderModel newOrderModel=newOrderModelArrayList.get(position);


      //  holder.ivmenu.setImageResource(uploadedMenuList.get(position).getImgupld());
        holder.tvusername.setText(newOrderModelArrayList.get(position).getUsername());
        holder.tvmenuname.setText(newOrderModelArrayList.get(position).getMenuName());
        holder.tvdate.setText(newOrderModelArrayList.get(position).getDate());
        holder.tvtime.setText(newOrderModelArrayList.get(position).getCollectionTime());
        holder.tvprice.setText(newOrderModelArrayList.get(position).getPrice());
        holder.tvqty.setText(newOrderModelArrayList.get(position).getQty());
        holder.tvphone.setText(newOrderModelArrayList.get(position).getPhone());
        holder.tvstatus.setText(newOrderModelArrayList.get(position).getStatus());


    }

    @Override
    public int getItemCount() {
        if (newOrderModelArrayList == null)
            return 0;
        return newOrderModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvusername,tvmenuname,tvdate,tvtime,tvprice,tvqty,tvphone,tvstatus;
        ImageView ivlogo,ivphone,ivmenuimage;

        public MyViewHolder(View itemView, Context context, final ArrayList<NewOrderModel> newOrderModelArrayList) {
            super(itemView);

            tvusername=itemView.findViewById(R.id.tv_usernamenew);
            tvmenuname=itemView.findViewById(R.id.tv_menunamenew);
            tvdate=itemView.findViewById(R.id.tv_datenew);
            tvtime=itemView.findViewById(R.id.tv_timenew);
            tvprice=itemView.findViewById(R.id.tv_pricenew);
            tvqty=itemView.findViewById(R.id.tv_quantitynew);
            tvphone=itemView.findViewById(R.id.tv_phone);
            tvstatus=itemView.findViewById(R.id.tv_status);
            ivlogo=itemView.findViewById(R.id.iv_logonew);
            ivmenuimage=itemView.findViewById(R.id.iv_menuimgnew);
            ivphone=itemView.findViewById(R.id.iv_call);
            ivphone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Toast.makeText(activity,"clicked",Toast.LENGTH_LONG).show();
                    int position=getAdapterPosition();

                    String ph=newOrderModelArrayList.get(position).getPhone();


                    dialContactPhone(ph);
                }
            });
        }
    }

    private void dialContactPhone(final String s) {

       activity. startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", s, null)));
    }
}
