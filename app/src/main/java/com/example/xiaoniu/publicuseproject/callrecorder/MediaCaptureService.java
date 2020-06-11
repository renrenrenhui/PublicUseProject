package com.example.xiaoniu.publicuseproject.callrecorder;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioPlaybackCaptureConfiguration;
import android.media.AudioRecord;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.xiaoniu.publicuseproject.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MediaCaptureService extends Service {
    public static final String ACTION_ALL = "ALL";
    public static final String ACTION_START = "ACTION_START";
    public static final String ACTION_STOP = "ACTION_STOP";
    public static final String EXTRA_RESULT_CODE = "EXTRA_RESULT_CODE";
    public static final String EXTRA_ACTION_NAME = "ACTION_NAME";

    private static final int RECORDER_SAMPLERATE = 8000;
    private static final int RECORDER_CHANNELS = AudioFormat.CHANNEL_IN_MONO;
    private static final int RECORDER_AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;

    NotificationCompat.Builder _notificationBuilder;
    NotificationManager _notificationManager;
    private String NOTIFICATION_CHANNEL_ID = "ChannelId";
    private String NOTIFICATION_CHANNEL_NAME = "Channel";
    private String NOTIFICATION_CHANNEL_DESC = "ChannelDescription";
    private int NOTIFICATION_ID = 1000;
    private static final String ONGING_NOTIFICATION_TICKER = "RecorderApp";

    int BufferElements2Rec = 1024; // want to play 2048 (2K) since 2 bytes we use only 1024
    int BytesPerElement = 2; // 2 bytes in 16bit format

    AudioRecord _recorder;
    private boolean isRecording = true;

    private MediaProjectionManager _mediaProjectionManager;
    private MediaProjection _mediaProjection;

    Intent _callingIntent;

    public MediaCaptureService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //Call Start foreground with notification
            Intent notificationIntent = new Intent(this, MediaCaptureService.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
            _notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Starting Service")
                    .setContentText("Starting monitoring service")
                    .setTicker(ONGING_NOTIFICATION_TICKER)
                    .setContentIntent(pendingIntent);
            Notification notification = _notificationBuilder.build();
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(NOTIFICATION_CHANNEL_DESC);
            _notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            _notificationManager.createNotificationChannel(channel);
            startForeground(NOTIFICATION_ID, notification);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            _mediaProjectionManager = (MediaProjectionManager) getSystemService(MEDIA_PROJECTION_SERVICE);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        _callingIntent = intent;

        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_ALL);
        registerReceiver(_actionReceiver, filter);

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void startRecording(Intent intent) {
        //final int resultCode = intent.getIntExtra(EXTRA_RESULT_CODE, 0);
        _mediaProjection = _mediaProjectionManager.getMediaProjection(-1, intent);
        startRecording(_mediaProjection);
    }

    @TargetApi(29)
    private void startRecording(MediaProjection mediaProjection) {
        if (mediaProjection == null) {
            return;
        }
        AudioPlaybackCaptureConfiguration config =
                new AudioPlaybackCaptureConfiguration.Builder(mediaProjection)
                        .addMatchingUsage(AudioAttributes.USAGE_MEDIA)
                        .build();
        AudioFormat audioFormat = new AudioFormat.Builder()
                .setEncoding(RECORDER_AUDIO_ENCODING)
                .setSampleRate(RECORDER_SAMPLERATE)
                .setChannelMask(RECORDER_CHANNELS)

//                .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
//                .setSampleRate(48000)
//                .setChannelMask(AudioFormat.CHANNEL_IN_MONO)
                .build();
        _recorder = new AudioRecord.Builder()
                //               .setAudioSource(MediaRecorder.AudioSource.MIC)
                .setAudioFormat(audioFormat)
                .setBufferSizeInBytes(BufferElements2Rec * BytesPerElement)
                .setAudioPlaybackCaptureConfig(config)
                .build();

        _recorder.startRecording();
        new Thread(new Runnable() {
            @Override
            public void run() {
                writeAudioDataToFile();
            }
        });

    }

    private byte[] short2byte(short[] sData) {
        int shortArrsize = sData.length;
        byte[] bytes = new byte[shortArrsize * 2];
        for (int i = 0; i < shortArrsize; i++) {
            bytes[i * 2] = (byte) (sData[i] & 0x00FF);
            bytes[(i * 2) + 1] = (byte) (sData[i] >> 8);
            sData[i] = 0;
        }
        return bytes;

    }

    private void writeAudioDataToFile() {
        Log.e("MediaCaptureSerive", "writeAudioDataToFile");
        // Write the output audio in byte
        File sampleDir = new File(Environment.getExternalStorageDirectory().getPath() + "/publicUseProject/");
        if (!sampleDir.exists()) {
            sampleDir.mkdirs();
        }
        String fileName = "Record-" + new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss").format(new Date()) + ".pcm";
        String filePath = sampleDir.getAbsolutePath() + "/" + fileName;
        //String filePath = "/sdcard/voice8K16bitmono.pcm";
        short sData[] = new short[BufferElements2Rec];

        FileOutputStream os = null;
        try {
            os = new FileOutputStream(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (isRecording) {
            // gets the voice output from microphone to byte format
            _recorder.read(sData, 0, BufferElements2Rec);
            Log.e("MediaCaptureSerive", "Short wirting to file" + sData.toString());
            try {
                // // writes the data to file from buffer
                // // stores the voice buffer
                byte bData[] = short2byte(sData);
                os.write(bData, 0, BufferElements2Rec * BytesPerElement);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void stopRecording() {
        // stops the recording activity
        if (null != _recorder) {
            isRecording = false;
            _recorder.stop();
            _recorder.release();
            _recorder = null;
        }

        _mediaProjection.stop();

        stopSelf();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(_actionReceiver);
    }

    BroadcastReceiver _actionReceiver = new BroadcastReceiver() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equalsIgnoreCase(ACTION_ALL)) {
                String actionName = intent.getStringExtra(EXTRA_ACTION_NAME);
                if (actionName != null && !actionName.isEmpty()) {
                    if (actionName.equalsIgnoreCase(ACTION_START)) {
                        startRecording(_callingIntent);
                    } else if (actionName.equalsIgnoreCase(ACTION_STOP)) {
                        stopRecording();
                    }
                }

            }
        }
    };
}