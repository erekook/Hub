package com.example.erek.hub.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.erek.hub.R;

/**
 * Created by erek on 2018/1/11
 */

public class ThirdFragment extends Fragment{
    public static ThirdFragment newInstance() {
        
        Bundle args = new Bundle();
        
        ThirdFragment fragment = new ThirdFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third,null);
        return view;
    }
}
