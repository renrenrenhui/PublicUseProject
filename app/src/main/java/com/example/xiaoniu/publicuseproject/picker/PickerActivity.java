package com.example.xiaoniu.publicuseproject.picker;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.xiaoniu.publicuseproject.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.qqtheme.framework.picker.DoublePicker;
import cn.qqtheme.framework.picker.NumberPicker;
import cn.qqtheme.framework.picker.TimePicker;
import cn.qqtheme.framework.util.ConvertUtils;

public class PickerActivity extends AppCompatActivity {

    PickerView minute_pv;
    PickerView second_pv;
    Button btn;
    LinearLayout rootLayout;
    PickerView pickerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picker_view);

        minute_pv = (PickerView) findViewById(R.id.minute_pv);
        second_pv = (PickerView) findViewById(R.id.second_pv);
        List<String> data = new ArrayList<String>();
        List<String> seconds = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            data.add("0" + i);
        }
        for (int i = 0; i < 60; i++) {
            seconds.add(i < 10 ? "0" + i : "" + i);
        }
        minute_pv.setData(data);
        minute_pv.setOnSelectListener(new PickerView.onSelectListener() {

            @Override
            public void onSelect(String text) {
//                Toast.makeText(MainActivity.this, "选择了 " + text + " 分",
//                        Toast.LENGTH_SHORT).show();
            }
        });
        second_pv.setData(seconds);
        second_pv.setOnSelectListener(new PickerView.onSelectListener() {

            @Override
            public void onSelect(String text)
            {
//                Toast.makeText(MainActivity.this, "选择了 " + text + " 秒",
//                        Toast.LENGTH_SHORT).show();
            }
        });
        minute_pv.setSelected(0);


        rootLayout = (LinearLayout) findViewById(R.id.root_layout);
        btn = (Button) findViewById(R.id.btn_bottom_dialog);
        final List<String> heightList = new ArrayList<String>();
        for (int i = 30; i < 241; i++) {
            heightList.add("" + i);
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                final PopupWindow popupWindow = new PopupWindow(PickerActivity.this);
//                popupWindow.setWidth(((WindowManager) PickerActivity.this
//                        .getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth());
//                popupWindow.setHeight(300);
//                View view = LayoutInflater.from(PickerActivity.this).inflate(R.layout.popup_setting, null);
//                popupWindow.setContentView(view);
//                popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
//                popupWindow.setOutsideTouchable(false);
//                popupWindow.setFocusable(true);
//                popupWindow.showAsDropDown(rootLayout,0,0, Gravity.BOTTOM);

                final Dialog dialog = new Dialog(PickerActivity.this, R.style.Theme_Light_Dialog);
                View dialogView = LayoutInflater.from(PickerActivity.this).inflate(R.layout.popup_setting,null);
                //获得dialog的window窗口
                Window window = dialog.getWindow();
                //设置dialog在屏幕底部
                window.setGravity(Gravity.BOTTOM);
                //设置dialog弹出时的动画效果，从屏幕底部向上弹出
                window.setWindowAnimations(R.style.dialogStyle);
                window.getDecorView().setPadding(0, 0, 0, 0);
                //获得window窗口的属性
                android.view.WindowManager.LayoutParams lp = window.getAttributes();
                //设置窗口宽度为充满全屏
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                //设置窗口高度为包裹内容
                lp.height = 300;
                //将设置好的属性set回去
                window.setAttributes(lp);
                //将自定义布局加载到dialog上
                dialog.setContentView(dialogView);
                pickerView = (PickerView) dialogView.findViewById(R.id.picker_view);
                pickerView.setData(heightList);
                pickerView.setSelected("164");
                dialog.show();
            }
        });

    }

    public void onHeightPicker(View view) {
        NumberPicker picker = new NumberPicker(this);
        picker.setCycleDisable(false);
        picker.setDividerVisible(false);
        picker.setOffset(2);//偏移量
        picker.setRange(145, 200, 1);//数字范围
        picker.setSelectedItem(172);
        picker.setLabel("cm");
        picker.setOnNumberPickListener(new NumberPicker.OnNumberPickListener() {
            @Override
            public void onNumberPicked(int index, Number item) {
//                showToast("index=" + index + ", item=" + item.intValue());
            }
        });
        picker.show();
    }

    public void onWeightPicker(View view) {
        final List<String> integerList = new ArrayList<String>();
        for (int i = 30; i < 241; i++) {
            integerList.add("" + i);
        }
        final ArrayList<String> DecimalList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DecimalList.add("" + i);
        }
        DoublePicker picker = new DoublePicker(this, integerList, DecimalList);
        picker.setCanceledOnTouchOutside(true);
//        picker.setTopLineColor(0xFFFB2C3C);
//        picker.setSubmitTextColor(0xFFFB2C3C);
//        picker.setCancelTextColor(0xFFFB2C3C);
        picker.setLineSpaceMultiplier(2.2f);
        picker.setTextSize(15);
//        picker.setTitleText("营业时间");
        picker.setContentPadding(10, 8);
        picker.setFirstLabel("", ".");
        picker.setSecondLabel("", "kg");
        picker.setOnPickListener(new DoublePicker.OnPickListener() {
            @Override
            public void onPicked(int selectedFirstIndex, int selectedSecondIndex) {
//                showToast(hours.get(selectedFirstIndex) + ":" + minutes.get(selectedSecondIndex));
            }
        });
        picker.show();
    }

    public void onTimePicker(View view) {
        TimePicker picker = new TimePicker(this, TimePicker.HOUR_24);
        picker.setUseWeight(false);
        picker.setCycleDisable(false);
        picker.setRangeStart(0, 0);//00:00
        picker.setRangeEnd(23, 59);//23:59
        int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int currentMinute = Calendar.getInstance().get(Calendar.MINUTE);
        picker.setSelectedItem(currentHour, currentMinute);
        picker.setTopLineVisible(false);
        picker.setLabel(":", "");
        picker.setOnTimePickListener(new TimePicker.OnTimePickListener() {
            @Override
            public void onTimePicked(String hour, String minute) {
//                showToast(hour + ":" + minute);
            }
        });
        picker.show();
    }
}