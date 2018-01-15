package com.example.erek.hub.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.erek.hub.R;

/**
 * Created by erek on 2018/1/11
 */

public class FirstFragment extends Fragment {
    public static FirstFragment newInstance() {
        
        Bundle args = new Bundle();
        
        FirstFragment fragment = new FirstFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first,null);
        return view;
    }
}
