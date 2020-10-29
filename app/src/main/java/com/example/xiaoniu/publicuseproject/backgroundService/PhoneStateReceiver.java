package com.example.xiaoniu.publicuseproject.backgroundService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

public class PhoneStateReceiver extends BroadcastReceiver {

    private LocalPhoneStateListener mLocalPhoneStateListener;

    public interface LocalPhoneStateListener {
        void onCallStateChanged(String state, String incomingNumber);
    }

    public void setLocalPhoneStateListener(LocalPhoneStateListener localPhoneStateListener) {
        mLocalPhoneStateListener = localPhoneStateListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            if (TelephonyManager.ACTION_PHONE_STATE_CHANGED.equals(intent.getAction())) {
                String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
                String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                if (mLocalPhoneStateListener != null) {
                    mLocalPhoneStateListener.onCallStateChanged(state, incomingNumber);
                }
            }
        }
    }
}
