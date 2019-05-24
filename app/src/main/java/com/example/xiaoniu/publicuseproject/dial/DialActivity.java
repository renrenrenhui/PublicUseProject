package com.example.xiaoniu.publicuseproject.dial;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.xiaoniu.publicuseproject.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class DialActivity extends AppCompatActivity {

    public Button mDialBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dial);

        mDialBtn = (Button) findViewById(R.id.btn_dial);

        EventBus.getDefault().register(this);//订阅

        mDialBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DialActivity.this, DialpadActivity.class));
            }
        });

        if (!FloatWindowManager.getInstance(this).isHoldAlertWindowPermission(this)) {
            View view = View.inflate(this, R.layout.request_alert_window_dialog, null);
            if (view != null) {
                final Dialog dialog = Utils.getStandardDialog(this, view);
                view.findViewById(R.id.tv_confirm).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Utils.openOpsSettings(DialActivity.this);
                        dialog.cancel();
                    }
                });
            }
        } else {
            FloatWindowManager.getInstance(this).showFloatWindow(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//解除订阅
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onDataSynEvent(DataSynEvent event) {
        callPhone("+16677713650");
    }

    /**
     * 拨打电话（直接拨打电话）
     * @param phoneNum 电话号码
     */
    public void callPhone(String phoneNum){
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }


    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     *
     * @param phoneNum 电话号码
     */
    public void dialPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }
}