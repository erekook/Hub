package com.example.erek.hub.activity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.erek.hub.R;
import com.example.erek.hub.fragment.FirstFragment;
import com.example.erek.hub.fragment.SecondFragment;
import com.example.erek.hub.fragment.ThirdFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.dl_left)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.lv_left_menu)
    ListView mLeftMenu;
    @BindView(R.id.dl_top)
    RelativeLayout mDlRelativeTop;
    @BindView(R.id.vp_content)
    ViewPager mViewPager;
    @BindView(R.id.tl_tab)
    TabLayout mTabLayout;

    private ActionBarDrawerToggle mDrawerToggle;
    private String[] dlMenus={"Menu Item 1","Menu Item 2","Menu Item 3","Menu Item 4","Menu Item 5"};
    private int[] tlMenus={R.mipmap.ic_menu_tab1,R.mipmap.ic_menu_tab2,R.mipmap.ic_menu_tab3};
    private ArrayAdapter mAdapter;
    private List<String> tabIndicators;
    private List<Fragment> tabFragments;

    private ContentPagerAdapter mContentPagerAdapter;
    private long mExitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mToolbar.setTitle("main");
        mToolbar.setTitleTextColor(0xffffffff);
        mDrawerLayout.setOnClickListener(this);
        initDrawerLayout();
        initContent();
        initTab();

    }

    private void initTab() {
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setSelectedTabIndicatorHeight(0);
        ViewCompat.setElevation(mTabLayout,10);
        mTabLayout.setupWithViewPager(mViewPager);
        for (int i = 0; i < tabIndicators.size(); i++) {
            TabLayout.Tab itemTab = mTabLayout.getTabAt(i);
            if (itemTab != null){
                itemTab.setCustomView(R.layout.custom_tab_layout_item);
                TextView itemTv = itemTab.getCustomView().findViewById(R.id.tv_menu_item);
                itemTv.setText(tabIndicators.get(i));
                ImageView imageView = itemTab.getCustomView().findViewById(R.id.tv_menu_img);
                Glide.with(itemTab.getCustomView())
                        .load(tlMenus[i])
                        .into(imageView);
            }
        }
    }

    private void initContent() {
        tabIndicators = new ArrayList<>();
        for (int i=0;i<3;i++){
            tabIndicators.add("Tab "+i);
        }
        tabFragments = new ArrayList<>();
        tabFragments.add(FirstFragment.newInstance());
        tabFragments.add(SecondFragment.newInstance());
        tabFragments.add(ThirdFragment.newInstance());
        mContentPagerAdapter = new ContentPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mContentPagerAdapter);


    }

    private void initDrawerLayout() {
        Glide.with(this)
                .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1515491200341&di=0ef122086516c2619cb91dbbbd73d9c9&imgtype=0&src=http%3A%2F%2Fa.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2Fae51f3deb48f8c54cc4ec07631292df5e1fe7fcd.jpg")
                .into(new SimpleTarget<Drawable>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        mDlRelativeTop.setBackground(resource);
                    }
                });
        //创建返回键，并实现打开/关闭监听
        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,mToolbar,R.string.open,R.string.close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        //左上角图标改变
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,dlMenus);
        mLeftMenu.setAdapter(mAdapter);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000){
            Toast.makeText(MainActivity.this,"再按一次退出HUB",Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        }else {
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dl_left:
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                assert imm != null;
                imm.hideSoftInputFromWindow(v.getWindowToken(),0);
                break;
        }
    }

    class ContentPagerAdapter extends FragmentPagerAdapter{

        public ContentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return tabIndicators.get(position);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return tabFragments.get(position);
        }

        @Override
        public int getCount() {
            return tabIndicators.size();
        }
    }
}
