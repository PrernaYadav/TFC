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
import com.infosolution.dev.tfc.model.UploadedMenu;

import org.w3c.dom.Text;

import java.util.Collections;
import java.util.List;

/**
 * Created by amit on 1/19/2018.
 */

public class UploadedMenuAdapter extends RecyclerView.Adapter<UploadedMenuAdapter.MyViewHolder> {
    public UploadedMenuAdapter(List<UploadedMenu> uploadedMenuList, Context context) {
        this.uploadedMenuList = uploadedMenuList;
        this.context = context;
    }

    List<UploadedMenu> uploadedMenuList= Collections.emptyList();
    Context context;



    @Override
    public UploadedMenuAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.uploadedmenu_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UploadedMenuAdapter.MyViewHolder holder, int position) {

        holder.ivmenu.setImageResource(uploadedMenuList.get(position).getImgupld());
        holder.name.setText(uploadedMenuList.get(position).getPronameupld());
        holder.qty.setText(uploadedMenuList.get(position).getQtyupld());
        holder.price.setText(uploadedMenuList.get(position).getPriceupld());
        holder.timing.setText(uploadedMenuList.get(position).getTiminguplad());

    }

    @Override
    public int getItemCount() {
        return uploadedMenuList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivmenu;
        TextView name,qty,price,timing;
        public MyViewHolder(View itemView) {
            super(itemView);
            ivmenu=itemView.findViewById(R.id.iv_menuimg);
            name=itemView.findViewById(R.id.tv_pronameupld);
            qty=itemView.findViewById(R.id.tv_qtyupld);
            price=itemView.findViewById(R.id.tv_priceupld);
            timing=itemView.findViewById(R.id.tv_timingupld);

        }
    }
}
