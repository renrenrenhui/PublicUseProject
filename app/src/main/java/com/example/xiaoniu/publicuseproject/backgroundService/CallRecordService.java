package com.example.xiaoniu.publicuseproject.backgroundService;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.support.v4.app.NotificationCompat;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.example.xiaoniu.publicuseproject.MainActivity;
import com.example.xiaoniu.publicuseproject.R;
import com.example.xiaoniu.publicuseproject.utils.Logger;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class CallRecordService extends Service {

    private boolean isRequestData = false;

    private PhoneState mPhoneState = new PhoneState();
    private PhoneStateReceiver mPhoneStateReceiver = new PhoneStateReceiver();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        IntentFilter intentFilterPhone = new IntentFilter();
        intentFilterPhone.addAction(TelephonyManager.ACTION_PHONE_STATE_CHANGED);
        mPhoneStateReceiver.setLocalPhoneStateListener(mPhoneState);
        registerReceiver(mPhoneStateReceiver, intentFilterPhone);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Notification notification;
        notification = startServiceForegroundNotification(this);
        if (notification == null) {
            notification = new Notification();
        }
        startForeground(10, notification);

        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {

        stopForeground(true);

        try {
            startCallRecorderService(this);
        } catch (Exception ex) {
        }

        unregisterReceiver(mPhoneStateReceiver);

        super.onDestroy();

    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

    }

    public static void startCallRecorderService(Context context) {
        try {
            if (isRunningCallRecorderService(context, "com.example.xiaoniu.publicuseproject.backgroundService.CallRecordService")) return;

            Intent intent = new Intent(context, CallRecordService.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                try {
                    context.startService(intent);
                } catch (Exception e) {
                    context.startForegroundService(intent);
                }
            } else {
                context.startService(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class PhoneState implements PhoneStateReceiver.LocalPhoneStateListener {
        @Override
        public void onCallStateChanged(String state, String incomingNumber) {
            Logger.e("onCallStateChanged()  " + state + "    " + incomingNumber + "  isRequestData = " + isRequestData);
            if (TelephonyManager.EXTRA_STATE_IDLE.equals(state)) {
                if (isRequestData) {
                    startActivity(new Intent(CallRecordService.this, BackgroundActivity.class));
                }
                isRequestData = false;
            } else if (TelephonyManager.EXTRA_STATE_RINGING.equals(state)) {
                isRequestData = true;
            } else if (TelephonyManager.EXTRA_STATE_OFFHOOK.equals(state)) {
                isRequestData = true;
            }
        }
    }

    private static NotificationManager getNotificationManager(Context context) {
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        return notificationManager;
    }

    public static Notification startServiceForegroundNotification(Context context) {
        NotificationManager notificationManager = getNotificationManager(context);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        Intent intent = new Intent();
        intent.setClass(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK );
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        String text = "heihei";
        builder.setContentText(text)
                .setContentTitle(context.getResources().getString(R.string.app_name))
                .setSmallIcon(R.drawable.ic_close)
                .setContentIntent(pendingIntent)
                .setPriority(Notification.PRIORITY_HIGH)
                .setOngoing(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("channel", "demo",
                    NotificationManager.IMPORTANCE_LOW);

            notificationManager.createNotificationChannel(channel);
            builder.setChannelId("channel");
        }

        Notification notification = builder.build();
        notificationManager.notify(10, notification);
        return notification;
    }

    public static boolean isRunningCallRecorderService(Context context, String serviceName) {
        boolean isRunning = false;
        try {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
            List<ActivityManager.RunningServiceInfo> listService = activityManager.getRunningServices(300);
            if (listService != null) {
                for (int i = 0; i < listService.size(); i++) {
                    if (serviceName.equals(listService.get(i).service.getClassName())) {
                        isRunning = true;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isRunning;
    }
}
