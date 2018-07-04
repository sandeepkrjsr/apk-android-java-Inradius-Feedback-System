package com.kodexlabs.inradius.Main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kodexlabs.inradius.R;

/**
 * Created by MadhuRima on 19-03-2017.
 */

public class Fragment_Dummy extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_general, container, false);
        return view;
    }
}
