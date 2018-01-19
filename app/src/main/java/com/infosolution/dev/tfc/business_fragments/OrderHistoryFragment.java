package com.infosolution.dev.tfc.business_fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.infosolution.dev.tfc.R;
import com.infosolution.dev.tfc.adapter.OrderHistoryAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderHistoryFragment extends Fragment {

    GridView grid;

    private String[] proname = new String[] { "acbb", "gaj", "sak"};
    public static int [] logo={R.drawable.icon,R.drawable.icon,R.drawable.icon,};
    private String[] deliver = new String[] { "Deliver", "Deliver", "Deliver" };
    private String[] username = new String[] { "Shreyansh", "Nayni", "Lucky" };
    private String[] quantity= new String[] { "1", "1", "3" };
    private String[] price= new String[] { "12", "5", "200" };
    private String[] date= new String[] { "12-2-2018", "05-12-2017", "01-05-2017" };


   /* public OrderHistoryFragment() {
        // Required empty public constructor
    }*/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_order_history, container, false);

        OrderHistoryAdapter adapter = new OrderHistoryAdapter(getContext(), proname,deliver,quantity,price,username,date,logo);

        grid=(GridView)v.findViewById(R.id.gv_producthis);

        grid.setAdapter(adapter);

        return v;
    }

}
