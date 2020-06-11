package com.example.xiaoniu.publicuseproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.xiaoniu.publicuseproject.CollapsingToolbarLayout.CollapsingToolbarLayoutActivity;
import com.example.xiaoniu.publicuseproject.ExpandableListView.ExpandableListViewActivity;
import com.example.xiaoniu.publicuseproject.FadeInTextView.FadeInTextView;
import com.example.xiaoniu.publicuseproject.LruCache.PhotoWallActivity;
import com.example.xiaoniu.publicuseproject.callrecorder.CallRecorderActivity;
import com.example.xiaoniu.publicuseproject.clipChildren.ClipChildrenActivity;
import com.example.xiaoniu.publicuseproject.constraintLayout.ConstraintLayoutActivity;
import com.example.xiaoniu.publicuseproject.dial.DialActivity;
import com.example.xiaoniu.publicuseproject.floatingwidow.FloatWindowService;
import com.example.xiaoniu.publicuseproject.fragment.ViewPagerFragmentActivity;
import com.example.xiaoniu.publicuseproject.glide.GlideActivity;
import com.example.xiaoniu.publicuseproject.lottery.LotteryActivity;
import com.example.xiaoniu.publicuseproject.navigation.NavigationTabActivity;
import com.example.xiaoniu.publicuseproject.notification.CreateNotificationActivity;
import com.example.xiaoniu.publicuseproject.https.HttpsActivity;
import com.example.xiaoniu.publicuseproject.picker.PickerActivity;
import com.example.xiaoniu.publicuseproject.recyclerView.RecyclerActivity;
import com.example.xiaoniu.publicuseproject.skipcountdown.SkipCountDownActivity;
import com.example.xiaoniu.publicuseproject.theme.ChangeThemeActivity;
import com.example.xiaoniu.publicuseproject.theme.MDNightActivity;
import com.example.xiaoniu.publicuseproject.timer.TimerActivity;
import com.example.xiaoniu.publicuseproject.utils.UtilMoreText;
import com.example.xiaoniu.publicuseproject.viewLifeCycle.ViewLifeCycleActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tv2;
    private String msg ="在Android开发中，有许多信息展示需要通过TextView来展现，如果只是普通的信息展现，使用TextView setText(CharSequence str)设置即可，但是当在TextView里的这段内容需要截取某一部分字段，可以被点击以及响应响应的操作，这时候就需要用到SpannableString了，下面通过一段简单的代码实现部分文字被点击响应，及富文本表情的实现";
    private TextView mOutput, mAutofitOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        StatusBarUtil.setColor(MainActivity.this, getResources().getColor(R.color.colorAccent));

//        if (Build.VERSION.SDK_INT >= 21) {
//            getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccent));
//        }

//        setColor(MainActivity.this, getResources().getColor(R.color.colorAccent));
        setTranslucent(MainActivity.this);

        /*start for float window*/
        Button startFloatWindow = (Button) findViewById(R.id.start_float_window);
        startFloatWindow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(MainActivity.this, FloatWindowService.class);
                startService(intent);
                finish();
            }
        });
        /*end for float window*/

        /*start for more text*/
        tv2 = (TextView) findViewById(R.id.tv2);
        tv2.setTextSize(20);
        new UtilMoreText(tv2,msg);
        /*end for more text*/

        /*start for changeTheme*/
        Button startChangeTheme = (Button) findViewById(R.id.start_change_theme);
        startChangeTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ChangeThemeActivity.class);
                startActivity(intent);
            }
        });
        /*end for changeTheme*/

        /*start for changeTheme*/
        Button startNight = (Button) findViewById(R.id.start_md_night);
        startNight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MDNightActivity.class);
                startActivity(intent);
            }
        });
        /*end for changeTheme*/

        /*start for auto textview*/
        mOutput = (TextView)findViewById(R.id.output);
        mAutofitOutput = (TextView)findViewById(R.id.output_autofit);
        ((EditText)findViewById(R.id.input)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                // do nothing
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                mOutput.setText(charSequence);
                mAutofitOutput.setText(charSequence);
            }
            @Override
            public void afterTextChanged(Editable editable) {
                // do nothing
            }
        });
        /*end for auto textview*/

        /*start for Skip Count Down*/
        Button skipCountDown = (Button) findViewById(R.id.start_skip_count_down);
        skipCountDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(MainActivity.this, SkipCountDownActivity.class);
                startActivity(intent);
            }
        });
        /*end for Skip Count Down*/

        /*start for fade in textview*/
        final FadeInTextView fadeInTextView = (FadeInTextView) findViewById(R.id.fade_in_text);
        fadeInTextView
                .setTextString("FadeInTextView, type text one by one......")
                .startFadeInAnimation()
                .setTextAnimationListener(new FadeInTextView.TextAnimationListener() {
                    @Override
                    public void animationFinish() {
                        fadeInTextView.stopFadeInAnimation();
                    }
                });
        /*end for fade in textview*/

        /*start for view life cycle*/
        Button viewLifeCycle = (Button) findViewById(R.id.start_view_life_cycle);
        viewLifeCycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(MainActivity.this, ViewLifeCycleActivity.class);
                startActivity(intent);
            }
        });
        /*end for view life cycle*/

        /*start for ConstraintLayout*/
        Button startConstraintLayout = (Button) findViewById(R.id.start_constraint_layout);
        startConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(MainActivity.this, ConstraintLayoutActivity.class);
                startActivity(intent);
            }
        });
        /*end for ConstraintLayout*/

        /*start for notification*/
        Button notification = (Button) findViewById(R.id.create_notification);
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateNotificationActivity.class);
                startActivity(intent);
            }
        });
        /*end for notification*/

        /*start for glide*/
        Button glide = (Button) findViewById(R.id.start_glide);
        glide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GlideActivity.class);
                startActivity(intent);
            }
        });
        /*end for glide*/

        /*start for lruCache*/
        Button lruCache = (Button) findViewById(R.id.start_photo_wall);
        lruCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PhotoWallActivity.class);
                startActivity(intent);
            }
        });
        /*end for lruCache*/

        /*start for ViewPagerFragment*/
        Button viewpagerFragment = (Button) findViewById(R.id.start_viewpager_fragment);
        viewpagerFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ViewPagerFragmentActivity.class);
                startActivity(intent);
            }
        });
        /*end for ViewPagerFragment*/

        /*start for lruCache*/
        Button clipChildren = (Button) findViewById(R.id.start_clip_children);
        clipChildren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ClipChildrenActivity.class);
                startActivity(intent);
            }
        });
        /*end for lruCache*/

        /*start for ExpandableListView*/
        Button ExpandableListView = (Button) findViewById(R.id.start_expandable_listView);
        ExpandableListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ExpandableListViewActivity.class);
                startActivity(intent);
            }
        });
        /*end for ExpandableListView*/

        /*start for https request*/
        Button screenOrientation = (Button) findViewById(R.id.start_https_request);
        screenOrientation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, HttpsActivity.class);
                startActivity(intent);
            }
        });
        /*end for start_https_request*/

        /*start for collapsingToolbarLayout*/
        Button collapsingToolbarLayout = (Button) findViewById(R.id.collapsing_toolbar_layout);
        collapsingToolbarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CollapsingToolbarLayoutActivity.class);
                startActivity(intent);
            }
        });
        /*end for collapsingToolbarLayout*/

        /*start for pickerView*/
        Button pickerView = (Button) findViewById(R.id.picker);
        pickerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PickerActivity.class);
                startActivity(intent);
            }
        });
        /*end for pickerView*/

        /*start for recyclerView*/
        Button recyclerView = (Button) findViewById(R.id.recycler_view);
        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RecyclerActivity.class);
                startActivity(intent);
            }
        });
        /*end for recyclerView*/

        /*start for timer*/
        Button timerView = (Button) findViewById(R.id.timer_view);
        timerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TimerActivity.class);
                startActivity(intent);
            }
        });
        /*end for timer*/

        /*start for tts_speech*/
        Button tts_speech = (Button) findViewById(R.id.tts_speech);
        tts_speech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SpeechActivity.class);
                startActivity(intent);
            }
        });
        /*end for tts_speech*/

        /*start for tts_speech*/
        Button lottery = (Button) findViewById(R.id.lottery);
        lottery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LotteryActivity.class);
                startActivity(intent);
            }
        });
        /*end for tts_speech*/

        /*start for call*/
        Button call = (Button) findViewById(R.id.call);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DialActivity.class);
                startActivity(intent);
            }
        });
        /*end for call*/

        /*start for tab navigation*/
        Button tabNav = (Button) findViewById(R.id.tab_navigation);
        tabNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NavigationTabActivity.class);
                startActivity(intent);
            }
        });
        /*end for tab navigation*/

        /*start for tab navigation*/
        Button callrecorder = (Button) findViewById(R.id.callrecorder);
        callrecorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CallRecorderActivity.class);
                startActivity(intent);
            }
        });
        /*end for tab navigation*/
    }

    /**
     * 设置状态栏颜色
     *
     * @param activity 需要设置的activity
     * @param color    状态栏颜色值
     */
    public static void setColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置状态栏透明
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 生成一个状态栏大小的矩形
            View statusView = createStatusView(activity, color);
            // 添加 statusView 到布局中
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            decorView.addView(statusView);
            // 设置根布局的参数
            ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
            rootView.setFitsSystemWindows(true);
            rootView.setClipToPadding(true);
        }
    }

    /**
     * 生成一个和状态栏大小相同的矩形条
     *
     * @param activity 需要设置的activity
     * @param color    状态栏颜色值
     * @return 状态栏矩形条
     */
    private static View createStatusView(Activity activity, int color) {
        // 获得状态栏高度
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        int statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);

        // 绘制一个和状态栏一样高的矩形
        View statusView = new View(activity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                statusBarHeight);
        statusView.setLayoutParams(params);
        statusView.setBackgroundColor(color);
        return statusView;
    }

    /**
     * 使状态栏透明
     * <p>
     * 适用于图片作为背景的界面,此时需要图片填充到状态栏
     *
     * @param activity 需要设置的activity
     */
    public static void setTranslucent(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置状态栏透明
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 设置根布局的参数
            ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
            rootView.setFitsSystemWindows(true);
            rootView.setClipToPadding(true);
        }
    }
}
