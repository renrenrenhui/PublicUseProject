package com.example.xiaoniu.publicuseproject.googleAuthentication;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.xiaoniu.publicuseproject.R;
import com.example.xiaoniu.publicuseproject.googleAuthentication.totp.LinAuthenication;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 参考
 * https://www.jianshu.com/p/e1031d36888b
 *https://note.youdao.com/ynoteshare1/index.html?id=356a33367a82a825dbd714be962ba593&type=note
 *https://rootprojects.org/authenticator/
 * 链接样式
 * otpauth://totp/Google:starlightshirley189@gmail.com?secret=av5z4aa5ixa4kyn2oib4ktdbgsuuf655&issuer=Google
 *
 * 方向
 * otpauth://
 * totp 方向
 * Google 采用的业内标准的二次认证协议 google-authenticator
 * 知乎 上关于谷歌验证 (Google Authenticator) 的实现原理的内容
 * 服务器随机生成一个类似于DPI45HKISEXU6HG7的密钥，并且把这个密钥保存在数据库中
 * 在页面上显示一个二维码，内容是一个URI地址(otpauth://totp/账号?secret=密钥)如『otpauth://totp/kisexu@gmail.com?secret=DPI45HCEBCJK6HG』
 * 找到的线上的demo Live Demo
 * 解析格式 Key Uri Format
 *
 * hotp 方向
 * 按照个数来对照秘钥
 * count 的数值没法确认(或者说确认了也无法保证正确性)
 */
public class GoogleAuthenticationActivity extends Activity {

    private TextView mCodeTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_authentication);
        mCodeTv = findViewById(R.id.tv_code);

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    //单纯输出时间
                    Date now = new Date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//可以方便地修改日期格式
                    String hehe = dateFormat.format( now );
                    Log.e("totp", "time: " + hehe);

                    //测试算法

                    String secret = "abcdefghijklmnopqrst";
                    final String result = LinAuthenication.getCurrentCode(secret);
                    Log.e("totp", "code: " + result);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mCodeTv.setText(result);
                        }
                    });
                } catch (LinAuthenication.OtpSourceException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
