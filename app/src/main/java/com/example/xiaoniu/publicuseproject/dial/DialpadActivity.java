package com.example.xiaoniu.publicuseproject.dial;

import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.xiaoniu.publicuseproject.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class DialpadActivity extends AppCompatActivity {

    private FrameLayout mDialpadLayout;
    private DialpadFragment mDialpadSysFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialpad);

//        mDialpadLayout = (FrameLayout) findViewById(R.id.dialpad_layout);
        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (mDialpadSysFragment == null) {
            mDialpadSysFragment = new DialpadFragment();
            ft.add(R.id.dialpad_layout, mDialpadSysFragment);
        } else {
            ft.show(mDialpadSysFragment);
        }
        mDialpadSysFragment.setAnimate(true);
        ft.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}