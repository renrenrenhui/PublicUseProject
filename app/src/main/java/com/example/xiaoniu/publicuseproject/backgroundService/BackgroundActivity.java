package com.example.xiaoniu.publicuseproject.backgroundService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.example.xiaoniu.publicuseproject.MainActivity;
import com.example.xiaoniu.publicuseproject.R;
import com.example.xiaoniu.publicuseproject.switchLanguage.ConstantGlobal;
import com.example.xiaoniu.publicuseproject.switchLanguage.MultiLanguageApp;
import com.example.xiaoniu.publicuseproject.switchLanguage.MultiLanguageUtil;
import com.example.xiaoniu.publicuseproject.switchLanguage.SpUtil;

import java.util.Locale;

public class BackgroundActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_background);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
