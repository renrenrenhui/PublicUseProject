package com.example.xiaoniu.publicuseproject.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.xiaoniu.publicuseproject.R;

import java.util.ArrayList;

public class ViewPagerFragmentActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private MyFragmentPagerAdapter mTabsAdapter;
    private ArrayList<BaseFragment> fragments;
    private ArrayList<String> mTabTitles;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_viewpager_fragment);

        fragments = new ArrayList<>();
        Fragment1 fragment1 = new Fragment1();
        Fragment2 fragment2 = new Fragment2();
        Fragment3 fragment3 = new Fragment3();
        Fragment4 fragment4 = new Fragment4();
        fragments.add(fragment1);
        fragments.add(fragment2);
        fragments.add(fragment3);
        fragments.add(fragment4);
        mTabTitles = new ArrayList<>();
        mTabTitles.add("Fragment1");
        mTabTitles.add("Fragment2");
        mTabTitles.add("Fragment3");
        mTabTitles.add("Fragment4");
        mTabsAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments, mTabTitles);

        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(mTabsAdapter);
        mViewPager.setOffscreenPageLimit(1);
        mTabLayout.setupWithViewPager(mViewPager, true);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.setTabsFromPagerAdapter(mTabsAdapter);
    }
}
