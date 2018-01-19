package com.infosolution.dev.tfc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.infosolution.dev.tfc.R;

/**
 * Created by amit on 1/19/2018.
 */

public class OrderHistoryAdapter extends BaseAdapter {

    private Context mContext;
    String[] proname;
    String[] deliver;
    String[] quantity;
    String[] price;
    String[] username;
    String[] date;
    int[] logo;

    public OrderHistoryAdapter(Context mContext, String[] proname, String[] deliver, String[] quantity, String[] price, String[] username, String[] date, int[] logo) {
        this.mContext = mContext;
        this.proname = proname;
        this.deliver = deliver;
        this.quantity = quantity;
        this.price = price;
        this.username = username;
        this.date = date;
        this.logo = logo;
    }


    @Override
    public int getCount() {
        return proname.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.orderhistory_row, null);
            TextView tvproname = (TextView) grid.findViewById(R.id.tv_pronamehis);
            TextView tvdeliver = (TextView) grid.findViewById(R.id.tv_deliverhis);
            TextView tvusername= (TextView) grid.findViewById(R.id.tv_usernamehis);
            TextView tvquantity= (TextView) grid.findViewById(R.id.tv_quantityhis);
            TextView tvprice= (TextView) grid.findViewById(R.id.tv_pricehis);
            TextView tvdate= (TextView) grid.findViewById(R.id.tv_datehis);
            ImageView ivlogo = (ImageView)grid.findViewById(R.id.iv_logohis);
            tvproname.setText(proname[position]);
            tvusername.setText(username[position]);
            tvdeliver.setText(deliver[position]);
            tvquantity.setText(quantity[position]);
            tvprice.setText(price[position]);
            tvdate.setText(date[position]);
            ivlogo.setImageResource(logo[position]);
        } else {
            grid = (View) view;
        }

        return grid;
    }
    }
