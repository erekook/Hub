package com.example.erek.hub.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.erek.hub.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by erek on 2018/1/11
 */

public class BaseActivity extends AppCompatActivity {
    @BindView(R.id.tl_custom)
    Toolbar mToolbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        ButterKnife.bind(this);
        setToolBar();
    }

    private void setToolBar() {
        mToolbar.setTitle("HUB");
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);//设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


}
