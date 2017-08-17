package com.example.xiaoniu.publicuseproject.notification;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

import com.example.xiaoniu.publicuseproject.R;

public class CreateNotificationActivity extends Activity {

    private NotificationCompat.Builder mBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_notification);

        Button normalButton = (Button) findViewById(R.id.normal_notification);
        normalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int normalNum = 0;
                mBuilder = new NotificationCompat.Builder(CreateNotificationActivity.this);
                mBuilder.setAutoCancel(true) // 设置自动清除
                        .setContentInfo(String.valueOf(++normalNum)) // 设置附加内容
                        .setContentTitle("5 new mails") // 设置内容标题
                        .setContentText("renhui@gmail.com") // 设置内容文本
                        .setDefaults(Notification.DEFAULT_ALL)  // 设置使用所有默认值（声音、震动、闪屏等）
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher)) // 设置大型图标
                        .setSmallIcon(R.drawable.sticker)  // 设置小型图标
                        .setTicker("Normal Notification"); // 设置状态栏提示信息
                notifyNotification();
            }
        });

        Button inboxButton = (Button) findViewById(R.id.inbox_notification);
        inboxButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int inboxNum = 0;
                mBuilder = new NotificationCompat.Builder(CreateNotificationActivity.this);
                mBuilder.setAutoCancel(true)
                        .setContentInfo(String.valueOf(++inboxNum))
                        .setContentTitle("5 new mails")
                        .setContentText("renhui@gmail.com")
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher)) // 设置大型图标
                        .setSmallIcon(R.drawable.sticker)  // 设置小型图标
                        .setTicker("Inbox Notification")
                        .setStyle(new NotificationCompat.InboxStyle() // 设置通知样式为收件箱样式
                                .addLine("what")
                                .addLine("the")
                                .addLine("fuck")
                                .setSummaryText("+3 more")); // 设置在细节区域底端添加一行文本
                notifyNotification();
            }
        });

        Button bigTextButton = (Button) findViewById(R.id.big_text_notification);
        bigTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int bigtextNum = 0;
                mBuilder = new NotificationCompat.Builder(CreateNotificationActivity.this);
                mBuilder.setAutoCancel(true)
                        .setContentInfo(String.valueOf(++bigtextNum))
                        .setContentTitle("New mail")
                        .setContentText("renhui@gmail.com")
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher)) // 设置大型图标
                        .setSmallIcon(R.drawable.sticker)  // 设置小型图标
                        .setTicker("BigText Notification")
                        .setStyle(new NotificationCompat.BigTextStyle() // 设置通知样式为大型文本样式
                                .bigText("Helper class for generating large-format notifications that include a lot of text. This class is a \"rebuilder\": It attaches to a Builder object and modifies its behavior, like so. blalalalalalalalalalalalalalalalalalala"));
                notifyNotification();
            }
        });

        Button bigPictureButton = (Button) findViewById(R.id.big_picture_notification);
        bigPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int bigpictureNum = 0;
                mBuilder = new NotificationCompat.Builder(CreateNotificationActivity.this);
                mBuilder.setAutoCancel(true)
                        .setContentInfo(String.valueOf(++bigpictureNum))
                        .setContentTitle("New photo")
                        .setContentText("renhui@gmail.com")
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher)) // 设置大型图标
                        .setSmallIcon(R.drawable.sticker)  // 设置小型图标
                        .setTicker("BigPicture Notification")
                        .setStyle(new NotificationCompat.BigPictureStyle() // 设置通知样式为大型图片样式
                                .bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.preview_1)));
                notifyNotification();
            }
        });

        Button diyButton = (Button) findViewById(R.id.diy_notification);
        diyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RemoteViews contentViews = new RemoteViews(getPackageName(),
                        R.layout.custom_notification);
                //通过控件的Id设置属性
                contentViews
                        .setImageViewResource(R.id.imageNo, R.drawable.sticker);
                contentViews.setTextViewText(R.id.titleNo, "自定义通知标题");
                contentViews.setTextViewText(R.id.textNo, "自定义通知内容");
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                        CreateNotificationActivity.this).setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("My notification")
                        .setContentText("My notification")
                        .setTicker("new message");
                mBuilder.setAutoCancel(true);
                mBuilder.setContent(contentViews);//默认情况下通知高度为64dp
                mBuilder.setAutoCancel(true);
                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(10, mBuilder.build());
            }
        });

        Button diyButton2 = (Button) findViewById(R.id.diy_notification2);
        diyButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RemoteViews contentViews = new RemoteViews(getPackageName(),
                        R.layout.custom_notification);
                //通过控件的Id设置属性
                contentViews
                        .setImageViewResource(R.id.imageNo, R.drawable.sticker);
                contentViews.setTextViewText(R.id.titleNo, "自定义通知标题");
                contentViews.setTextViewText(R.id.textNo, "自定义通知内容");
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                        CreateNotificationActivity.this).setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("My notification")
                        .setContentText("My notification")
                        .setTicker("new message");
                mBuilder.setAutoCancel(true);
                mBuilder.setCustomBigContentView(contentViews);//注意bigContentView 的最大高度是250dp
                mBuilder.setAutoCancel(true);
                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(10, mBuilder.build());
            }
        });

        Button diyButton3 = (Button) findViewById(R.id.diy_notification3);
        diyButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RemoteViews contentViews = new RemoteViews(getPackageName(),
                        R.layout.custom_notification);
                //通过控件的Id设置属性
                contentViews
                        .setImageViewResource(R.id.imageNo, R.drawable.sticker);
                contentViews.setTextViewText(R.id.titleNo, "自定义通知标题");
                contentViews.setTextViewText(R.id.textNo, "自定义通知内容");
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                        CreateNotificationActivity.this).setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("My notification")
                        .setContentText("My notification")
                        .setTicker("new message");
                mBuilder.setAutoCancel(true);
                mBuilder.setContent(contentViews);
                mBuilder.setCustomBigContentView(contentViews);
                mBuilder.setAutoCancel(true);
                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(10, mBuilder.build());
            }
        });

        Button headsUpButton = (Button) findViewById(R.id.heads_up_notification);
        headsUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBuilder = new NotificationCompat.Builder(CreateNotificationActivity.this);
                mBuilder.setAutoCancel(true) // 设置自动清除
                        .setContentTitle("5 new mails") // 设置内容标题
                        .setContentText("renhui@gmail.com") // 设置内容文本
                        .setDefaults(Notification.DEFAULT_ALL)  // 设置使用所有默认值（声音、震动、闪屏等）
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher)) // 设置大型图标
                        .setSmallIcon(R.drawable.sticker)  // 设置小型图标
                        .setTicker("Normal Notification"); // 设置状态栏提示信息
                RemoteViews headsUpView = new RemoteViews(getPackageName(),
                        R.layout.custom_notification);
                Notification notification = mBuilder.build();
                if (Build.VERSION.SDK_INT >= 21) {
                    notification.priority = Notification.PRIORITY_MAX;
                    notification.headsUpContentView = headsUpView;
                }
                NotificationManager mgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                mgr.notify(20, notification);
            }
        });
    }

    private void notifyNotification() {
        Notification notification = mBuilder.build();
        NotificationManager mgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mgr.notify(0, notification);
    }
}
