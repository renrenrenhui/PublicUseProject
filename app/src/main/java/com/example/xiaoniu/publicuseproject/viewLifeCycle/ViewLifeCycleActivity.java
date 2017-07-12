package com.example.xiaoniu.publicuseproject.viewLifeCycle;

import android.app.Activity;
import android.os.Bundle;

import com.example.xiaoniu.publicuseproject.R;


public class ViewLifeCycleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_life_cycle);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
