package com.example.xiaoniu.publicuseproject;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;

public class SpeechActivity extends AppCompatActivity {

    private EditText input;
    private Button speech,record;
    private TextToSpeech textToSpeech;
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private Button down = null;
    private Button up = null;
    private ProgressBar pb = null;
    private int maxVolume = 50; // 最大音量值
    private int curVolume = 20; // 当前音量值
    private int stepVolume = 0; // 每次调整的音量幅度
    private AudioManager audioMgr = null; // Audio管理器，用了控制音量

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech);

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == textToSpeech.SUCCESS) {
//                    int result = textToSpeech.setLanguage(Locale.CHINA);
                    int result = textToSpeech.setLanguage(Locale.ENGLISH);
//                    if (result != TextToSpeech.LANG_COUNTRY_AVAILABLE
//                            && result != TextToSpeech.LANG_AVAILABLE){
                        if (result == TextToSpeech.LANG_MISSING_DATA
                                || result == TextToSpeech.LANG_NOT_SUPPORTED){
                            Toast.makeText(SpeechActivity.this, "TTS暂时不支持这种语音的朗读！",
                                    Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        input = (EditText) findViewById(R.id.input_text);
        speech = (Button) findViewById(R.id.speech);
        record = (Button) findViewById(R.id.record);
        speech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textToSpeech.speak(input.getText().toString(),
//                        TextToSpeech.QUEUE_ADD, null);
                        TextToSpeech.QUEUE_FLUSH, null);
            }
        });
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputText = input.getText().toString();
//                HashMap<String, String> myHashRender = new HashMap<>();
//                myHashRender.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, inputText);
//                textToSpeech.synthesizeToFile(inputText, myHashRender,
//                        Environment.getExternalStorageDirectory().getAbsolutePath() + "tts.wav");
                textToSpeech.synthesizeToFile(inputText, null, new File("/mnt/sdcard/sound.mp3"), "record");
                Toast.makeText(SpeechActivity.this, "声音记录成功。", Toast.LENGTH_SHORT).show();
            }
        });

        Button btnPlay = (Button) findViewById(R.id.btnPlay);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果没在播放中，立刻开始播放。
                if(!mediaPlayer.isPlaying()){
                    mediaPlayer.start();
                }
            }
        });
        Button btnPause = (Button) findViewById(R.id.btnPause);
        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果在播放中，立刻暂停。
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                }
            }
        });
        Button btnStop = (Button) findViewById(R.id.btnStop);
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果在播放中，立刻停止。
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.reset();
                    initMediaPlayer();//初始化播放器 MediaPlayer
                }
            }
        });
        down = (Button) findViewById(R.id.down);
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curVolume -= stepVolume;
                if (curVolume <= 0) {
                    curVolume = 0;
                }
                pb.setProgress(curVolume);
                // 调整音量
                adjustVolume();
            }
        });
        up = (Button) findViewById(R.id.up);
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curVolume += stepVolume;
                if (curVolume >= maxVolume) {
                    curVolume = maxVolume;
                }
                pb.setProgress(curVolume);
                // 调整音量
                adjustVolume();
            }
        });
        pb = (ProgressBar) findViewById(R.id.progress);
        pb.setMax(maxVolume);
        pb.setProgress(curVolume);
        initMediaPlayer();
    }

    @Override
    protected void onDestroy() {
        if (textToSpeech != null)
            textToSpeech.shutdown();
        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        super.onDestroy();
    }

    private void initMediaPlayer() {
        try {
//            File file = new File(Environment.getExternalStorageDirectory(), "sound.mp3");
//            mediaPlayer.setDataSource(file.getPath());//指定音频文件路径
            audioMgr = (AudioManager) getSystemService(Context.AUDIO_SERVICE);//调系统音量
            // 获取最大音乐音量
            maxVolume = audioMgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            // 初始化音量大概为最大音量的1/2
            curVolume = maxVolume / 2;
            // 每次调整的音量大概为最大音量的1/6
            stepVolume = maxVolume / 6;
            AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.exercise_bg_music);
            mediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(),
                    file.getLength());
            mediaPlayer.setLooping(true);//设置为循环播放
            mediaPlayer.prepare();//初始化播放器MediaPlayer
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 调整音量
     */
    private void adjustVolume() {
        audioMgr.setStreamVolume(AudioManager.STREAM_MUSIC, curVolume,
                AudioManager.FLAG_PLAY_SOUND);
    }

}