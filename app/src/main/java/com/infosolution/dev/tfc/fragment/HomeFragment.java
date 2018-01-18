package com.infosolution.dev.tfc.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.infosolution.dev.tfc.R;
import com.infosolution.dev.tfc.adapter.Homeadapter;
import com.infosolution.dev.tfc.model.Home;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    RecyclerView rcview;
    Homeadapter homeadapter;
    private List<Home> homeList;


   /* public HomeFragment() {
        // Required empty public constructor
    }*/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_home, container, false);
        rcview=v.findViewById(R.id.rc_home);

        homeList = fill_with_data();


        homeadapter = new Homeadapter(homeList, getContext());

        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rcview.getContext(),
                linearLayoutManager.getOrientation());
        rcview.addItemDecoration(dividerItemDecoration);


        rcview.setLayoutManager(linearLayoutManager);
        rcview.setAdapter(homeadapter);


        return v;
    }

    private List<Home> fill_with_data() {

        List<Home> home= new ArrayList<>();

        home.add(new Home("Biryani", R.drawable.stat, "Ankit","8:00-8:30PM","124","2",R.mipmap.ic_launcher,"2455...",R.drawable.fav));
        home.add(new Home("Biryani", R.drawable.stat, "shreyansh","9:00-8:30PM","1224","1",R.mipmap.ic_launcher,"245...",R.drawable.fav));
        home.add(new Home("Biryani", R.drawable.stat, "lucky","10:00-8:30PM","1244","5",R.mipmap.ic_launcher,"24552445...",R.drawable.fav));

        return home;


    }

}
