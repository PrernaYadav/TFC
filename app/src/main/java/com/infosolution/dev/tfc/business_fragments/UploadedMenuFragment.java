package com.infosolution.dev.tfc.business_fragments;


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
import com.infosolution.dev.tfc.adapter.UploadedMenuAdapter;
import com.infosolution.dev.tfc.model.Home;
import com.infosolution.dev.tfc.model.UploadedMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UploadedMenuFragment extends Fragment {

    RecyclerView rcview;
    UploadedMenuAdapter uploadedMenuAdapter;
    private List<UploadedMenu> uploadedMenuList;

   /* public UploadedMenuFragment() {
        // Required empty public constructor
    }*/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_uploaded_menu, container, false);

        rcview=v.findViewById(R.id.rc_uploadedmenu);


        uploadedMenuList = fill_with_data();


        uploadedMenuAdapter = new UploadedMenuAdapter(uploadedMenuList, getContext());

        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rcview.getContext(),
                linearLayoutManager.getOrientation());
        rcview.addItemDecoration(dividerItemDecoration);


        rcview.setLayoutManager(linearLayoutManager);
        rcview.setAdapter(uploadedMenuAdapter);


        return v;
    }

    private List<UploadedMenu> fill_with_data() {

        List<UploadedMenu> upload= new ArrayList<>();

        upload.add(new UploadedMenu("Biryani", "Ankit","8:00-8:30PM","124",R.drawable.icon));
        upload.add(new UploadedMenu("Biryani" ,"shreyansh","9:00-8:30PM","1224",R.drawable.icon));
        upload.add(new UploadedMenu("Biryani","lucky","10:00-8:30PM","1244",R.drawable.icon));

        return upload;

    }

}
