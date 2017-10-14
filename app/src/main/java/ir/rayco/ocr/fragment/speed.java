package ir.rayco.ocr.fragment;


import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ir.rayco.ocr.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class speed extends Fragment {


    public speed() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_speed, container, false);
        return  view;
    }

}
