package com.infosolution.dev.tfc.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.infosolution.dev.tfc.R;
import com.infosolution.dev.tfc.activities.LoginMailActivity;
import com.infosolution.dev.tfc.business.BusinessNavigation;
import com.infosolution.dev.tfc.model.Home;
import com.infosolution.dev.tfc.model.UploadedMenu;
import com.infosolution.dev.tfc.user.Navigation;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by amit on 1/19/2018.
 */

public class UploadedMenuAdapter extends RecyclerView.Adapter<UploadedMenuAdapter.MyViewHolderNew> {

    private ArrayList<UploadedMenu> uploadedMenusList;
    Context context;
    private Activity activity;

    private String ProName,Price,Timing,Qty;
    private int Image;

    public UploadedMenuAdapter(ArrayList<UploadedMenu> uploadedMenus, Context context, Activity activity) {
        this.uploadedMenusList = uploadedMenus;
        this.context = context;
        this.activity = activity;
    }

    @Override
    public UploadedMenuAdapter.MyViewHolderNew onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.uploadedmenu_row, parent, false);
        return new MyViewHolderNew(view,context,uploadedMenusList);
    }

    @Override
    public void onBindViewHolder(MyViewHolderNew holder, int position) {
        final  UploadedMenu uploadd=uploadedMenusList.get(position);


        holder.ivveg.setImageResource(uploadedMenusList.get(position).getImgveg());

        holder.name.setText(uploadedMenusList.get(position).getPronameupld());
        holder.qty.setText(uploadedMenusList.get(position).getQtyupld());
        holder.price.setText(uploadedMenusList.get(position).getPriceupld());
        holder.timing.setText(uploadedMenusList.get(position).getTiminguplad());
holder.ivmenu.setImageResource(uploadedMenusList.get(position).getImgupld());
      //  Glide.with(activity).load(uploadd.getImgupld()).into(holder.ivmenu);


    }


    @Override
    public int getItemCount() {
        if (uploadedMenusList == null)
            return 0;
        return uploadedMenusList.size();
    }

    public class MyViewHolderNew extends RecyclerView.ViewHolder {

        ImageView ivmenu,ivveg;
        TextView name,qty,price,timing,tvreupload;
        Context ctx;
        ArrayList<UploadedMenu> upload = new ArrayList<UploadedMenu>();

        public MyViewHolderNew(View itemView, final Context ctx,   ArrayList<UploadedMenu> upload) {
            super(itemView);
            this.ctx = ctx;
            this.upload=upload;

            ivmenu=itemView.findViewById(R.id.iv_menuimg);
            ivveg=itemView.findViewById(R.id.vegiv);
            name=itemView.findViewById(R.id.tv_pronameupld);
            qty=itemView.findViewById(R.id.tv_qtyupld);
            price=itemView.findViewById(R.id.tv_priceupld);
            timing=itemView.findViewById(R.id.tv_timingupld);
            tvreupload=itemView.findViewById(R.id.tv_reupload);
            tvreupload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos =getAdapterPosition();
                    ProName=uploadedMenusList.get(pos).getPronameupld();
                  String  numberOnly=uploadedMenusList.get(pos).getPriceupld();
                    Timing=uploadedMenusList.get(pos).getTiminguplad();
                    Qty=uploadedMenusList.get(pos).getQtyupld();
                    Image=uploadedMenusList.get(pos).getImgupld();

                    Price= numberOnly.replaceAll("[^0-9]", "");

                    Alert();



                }
            });


        }
    }

    private void Alert() {


        AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
        builder1.setMessage("Do you want to upload menu with same price and same collection time?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent intent = new Intent(activity, BusinessNavigation.class);
                        intent.putExtra("menuname",ProName);
                        intent.putExtra("price",Price);
                        intent.putExtra("timing",Timing);
                        intent.putExtra("qty",Qty);
                        intent.putExtra("img",Image);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        activity.startActivity(intent);


                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }


}
