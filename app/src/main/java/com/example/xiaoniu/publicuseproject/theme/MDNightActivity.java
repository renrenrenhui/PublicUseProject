package com.example.xiaoniu.publicuseproject.theme;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatDelegate;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.xiaoniu.publicuseproject.R;

import java.util.ArrayList;
import java.util.List;

public class MDNightActivity extends Activity {

    ListView mNewsListView;
    List<String> mNewsList = new ArrayList<String>();
    boolean isNight = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_md_night);

        // 换肤事件
        findViewById(R.id.change_btn).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                changeStatus();
            }
        });

        mNewsListView = (ListView) findViewById(R.id.listview);
        // 模拟数据
        mockNews();
        mNewsListView.setAdapter(new NewsAdapter());

        changeTheme();

    }


    /**
     * 切换主题
     */
    private void changeTheme() {
        isNight = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("night", false);

        if (isNight) {
            //这是设置成非夜间模式
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        } else {
            //这是设置成夜间模式
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        }

    }

    private void changeStatus() {
        isNight = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("night", false);

        if (isNight) {
            PreferenceManager.getDefaultSharedPreferences(this)
                    .edit().putBoolean("night", false).apply();
        } else {
            PreferenceManager.getDefaultSharedPreferences(this)
                    .edit().putBoolean("night", true).apply();
        }
        recreate();
    }

    /*start for adapter*/
    private void mockNews() {
        for (int i = 0; i < 20; i++) {
            mNewsList.add("News Title - " + i);
        }
    }

    class NewsAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mNewsList.size();
        }

        @Override
        public String getItem(int position) {
            return mNewsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            NewsViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.md_lv_item, parent, false);
                viewHolder = new NewsViewHolder();
                viewHolder.newsTitleView = (TextView) convertView
                        .findViewById(R.id.news_title);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (NewsViewHolder) convertView.getTag();
            }

            viewHolder.newsTitleView.setText(getItem(position));
            return convertView;
        }

    }

    public static class NewsViewHolder {
        public TextView newsTitleView;

    }
    /*end for adapter*/
}
