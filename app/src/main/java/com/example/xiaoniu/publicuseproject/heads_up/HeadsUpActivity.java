package com.example.xiaoniu.publicuseproject.heads_up;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.xiaoniu.publicuseproject.R;


public class HeadsUpActivity extends Activity {

    private LinearLayout mRootLayout;
    private Button headsup1;
    private Button headsup2;
    boolean clickPop = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heads_up);

        mRootLayout = findViewById(R.id.root_layout);
        headsup1 = (Button) findViewById(R.id.heads_up1);
        headsup2 = (Button) findViewById(R.id.heads_up2);
        headsup1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel("channel01", "name",
                            NotificationManager.IMPORTANCE_HIGH);   // for heads-up notifications
                    channel.setDescription("");
                    notificationManager.createNotificationChannel(channel);
                }
                Notification notification = new NotificationCompat.Builder(HeadsUpActivity.this, "channel01")
                        .setSmallIcon(android.R.drawable.ic_dialog_info)
                        .setContentTitle(getResources().getString(R.string.app_name))
                        .setContentText("hello")
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)   // heads-up
                        .build();
                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(HeadsUpActivity.this);
                notificationManagerCompat.notify(0, notification);
            }
        });
        headsup2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.root_layout).post(new Runnable() {
                    @Override
                    public void run() {
                        View view = getLayoutInflater().inflate(R.layout.call_receive_up_layout, null);
                        final PopupWindow receivePop = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                        receivePop.setOutsideTouchable(false);
                        receivePop.setFocusable(false);
                        receivePop.setAnimationStyle(R.style.recevice_window_style);
                        receivePop.showAtLocation(findViewById(R.id.root_layout), Gravity.TOP, 0, 0);
//        ImageView mClose = view.findViewById(R.id.call_close);
//        mClose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                clickPop=true;
//                receivePop.dismiss();
//            }
//        });
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                receivePop.dismiss();
                            }
                        });
                        receivePop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                if(clickPop)
                                    return;
                            }
                        });
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (receivePop != null && receivePop.isShowing())
                                    receivePop.dismiss();
                            }
                        }, 6000);
                    }
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
