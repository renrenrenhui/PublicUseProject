package com.example.xiaoniu.publicuseproject.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import java.util.List;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> fragments;
    private List<String> titles;

    public MyFragmentPagerAdapter(FragmentManager fm, List<BaseFragment> fragments, List<String> titles){
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        if (this.fragments == null)
            return null;
        return this.fragments.get(position);
    }

    @Override
    public int getCount() {
        if (this.fragments == null)
            return 0;
        return this.fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (this.titles == null)
            return null;
        return this.titles.get(position);
    }
}
