package com.example.xiaoniu.publicuseproject.switchLanguage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.xiaoniu.publicuseproject.MainActivity;
import com.example.xiaoniu.publicuseproject.R;
import com.example.xiaoniu.publicuseproject.googleAuthentication.totp.LinAuthenication;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class SwichLanguageActivity extends Activity {

    private Button mChineseBtn;
    private Button mEnglishBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swich_language);
        mChineseBtn = findViewById(R.id.btn_chinese);
        mEnglishBtn = findViewById(R.id.btn_english);
        mChineseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLanguage("zh", "CN");
            }
        });
        mEnglishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLanguage("en", "US");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //修改应用内语言设置
    private void changeLanguage(String language, String area) {
        if (TextUtils.isEmpty(language) && TextUtils.isEmpty(area)) {
            //如果语言和地区都是空，那么跟随系统
            SpUtil.saveString(ConstantGlobal.LOCALE_LANGUAGE, "");
            SpUtil.saveString(ConstantGlobal.LOCALE_COUNTRY, "");
        } else {
            //不为空，那么修改app语言，并true是把语言信息保存到sp中，false是不保存到sp中
            Locale newLocale = new Locale(language, area);
//            MultiLanguageUtil.changeAppLanguage(this, newLocale, true);
            MultiLanguageUtil.changeAppLanguage(MultiLanguageApp.getContext(), newLocale, true);
        }
        //重启app,这一步一定要加上，如果不重启app，可能打开新的页面显示的语言会不正确
        Intent intent = new Intent(MultiLanguageApp.getContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        MultiLanguageApp.getContext().startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
//        finish();
    }
}
