package com.example.xiaoniu.publicuseproject.callrecorder;

import android.annotation.TargetApi;
import android.content.Intent;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.xiaoniu.publicuseproject.R;

public class CallRecorderActivity extends AppCompatActivity {
    public static final String LOG_PREFIX = "CALL_FUNCTION_TEST";

    private static final int ALL_PERMISSIONS_PERMISSION_CODE = 1000;
    private static final int CREATE_SCREEN_CAPTURE = 1001;

    Button _btnGetOkPermissions;
    Button _btnInitCapture;
    Button _btnStartCapture;
    Button _btnStopCapture;
    private MediaProjectionManager _manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_callrecorder);

        _btnGetOkPermissions = findViewById(R.id.btnGetOkPermissions);
        _btnGetOkPermissions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                requestCaptureScreenInfoPermission();
            }
        });

        _btnInitCapture = findViewById(R.id.btnInitCapture);
        _btnInitCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initAudioCapture();
            }
        });

        _btnStartCapture = findViewById(R.id.btnStartCapture);
        _btnStartCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRecording();
            }
        });

        _btnStopCapture = findViewById(R.id.btnStopAudioCapture);
        _btnStopCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopRecording();
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initAudioCapture() {
        _manager = (MediaProjectionManager) getSystemService(MEDIA_PROJECTION_SERVICE);
        Intent intent = _manager.createScreenCaptureIntent();
        startActivityForResult(intent, CREATE_SCREEN_CAPTURE);
    }

    private void stopRecording() {
        Intent broadCastIntent = new Intent();
        broadCastIntent.setAction(MediaCaptureService.ACTION_ALL);
        broadCastIntent.putExtra(MediaCaptureService.EXTRA_ACTION_NAME, MediaCaptureService.ACTION_STOP);
        this.sendBroadcast(broadCastIntent);
    }

    private void startRecording() {
        Intent broadCastIntent = new Intent();
        broadCastIntent.setAction(MediaCaptureService.ACTION_ALL);
        broadCastIntent.putExtra(MediaCaptureService.EXTRA_ACTION_NAME, MediaCaptureService.ACTION_START);
        this.sendBroadcast(broadCastIntent);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (CREATE_SCREEN_CAPTURE == requestCode) {
            if (resultCode == RESULT_OK) {
                Intent i = new Intent(this, MediaCaptureService.class);
                i.setAction(MediaCaptureService.ACTION_START);
                i.putExtra(MediaCaptureService.EXTRA_RESULT_CODE, resultCode);
                i.putExtras(intent);
                this.startService(i);
            } else {
                // user did not grant permissions
            }
        }
    }

    public void requestCaptureScreenInfoPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return;
        }
        _manager = (MediaProjectionManager) getSystemService(MEDIA_PROJECTION_SERVICE);
        if (_manager != null) {
            Intent captureIntent = _manager.createScreenCaptureIntent();
            startActivityForResult(captureIntent, CREATE_SCREEN_CAPTURE);
        }
    }
}