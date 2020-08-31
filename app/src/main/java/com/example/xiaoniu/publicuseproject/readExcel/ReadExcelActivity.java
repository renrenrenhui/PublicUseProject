package com.example.xiaoniu.publicuseproject.readExcel;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.xiaoniu.publicuseproject.R;

import java.util.ArrayList;

import static com.cipher.CipherUtils.getCipherKeyFromJNI;


public class ReadExcelActivity extends Activity {


    private Button mWriteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_excel);

        mWriteBtn = findViewById(R.id.write_btn);
        mWriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ArrayList<String> decodeList = new ArrayList<>();
                        decodeList.clear();
        //                ExcelUtil.writeExcel(Environment.getExternalStorageDirectory().getAbsolutePath() + "/writeExcel", "writeExcel", "hebe");
        //                ArrayList<String> content = ExcelUtil.readExcelFile(Environment.getExternalStorageDirectory().getAbsolutePath() + "/readExcel", "writeExcel");
                        ArrayList<String> content = ExcelUtil.readExcelFile(ReadExcelActivity.this);
                        if (content != null) {
                            String[] array;
                            for (String encodeWord : content) {
                                array = encodeWord.split("\\.");
                                if (array.length > 2) {
                                    String word = AESUtil.decrypt(array[0], getCipherKeyFromJNI()) + " " + AESUtil.decrypt(array[1], getCipherKeyFromJNI()) + " GPA." + AESUtil.decrypt(array[2], getCipherKeyFromJNI());
                                    Log.e("encodeWord1: " , encodeWord + "  " + word);
                                    decodeList.add(word);
                                } else if (array.length > 1) {
                                    String word = AESUtil.decrypt(array[0], getCipherKeyFromJNI()) + " GPA." + AESUtil.decrypt(array[1], getCipherKeyFromJNI());
                                    Log.e("encodeWord2: " , encodeWord + "  " + word);
                                    decodeList.add(word);
                                } else {
                                    String word = AESUtil.decrypt(array[0], getCipherKeyFromJNI());
                                    Log.e("encodeWord3: " , encodeWord + "  " + word);
                                    decodeList.add(word);
                                }
                            }
                        } else {
                            Log.e("encodeWord: " , "content != null");
                        }

                        ExcelUtil.writeObjListToExcel(decodeList, Environment.getExternalStorageDirectory().getAbsolutePath() + "/writeExcel", "decryptExcel", ReadExcelActivity.this);
                    }
                }).start();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
