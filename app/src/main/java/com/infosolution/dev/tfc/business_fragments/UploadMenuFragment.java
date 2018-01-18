package com.infosolution.dev.tfc.business_fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.infosolution.dev.tfc.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UploadMenuFragment extends Fragment {


   /* public UploadMenuFragment() {
        // Required empty public constructor
    }*/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upload_menu, container, false);
    }

}
