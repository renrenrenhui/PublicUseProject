package com.example.xiaoniu.publicuseproject.picker;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.xiaoniu.publicuseproject.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.qqtheme.framework.picker.DoublePicker;
import cn.qqtheme.framework.picker.NumberPicker;
import cn.qqtheme.framework.picker.TimePicker;

public class PickerActivity extends AppCompatActivity {

    PickerView minute_pv;
    PickerView second_pv;
    Button btn;
    LinearLayout rootLayout;
    PickerView pickerView;
    private LineChart mChart;
    protected BarChart mBarChart;
    private HeartRateGraphView heart;
    //x轴坐标对应的数据
    private List<String> xValue = new ArrayList<>();
    //y轴坐标对应的数据
    private List<Integer> yValue = new ArrayList<>();
    //折线对应的数据
    private Map<String, Integer> value = new HashMap<>();

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


        mChart = (LineChart) findViewById(R.id.chart1);
//        mChart.setOnChartValueSelectedListener(this);
        // no description text
        mChart.getDescription().setEnabled(false);
        mChart.setDragDecelerationFrictionCoef(0.9f);
        // 不显示表格颜色
        mChart.setDrawGridBackground(false);
        mChart.setHighlightPerDragEnabled(true);
        //设置是否可以触摸，如为false，则不能拖动，缩放等
        mChart.setTouchEnabled(true);
        //设置是否可以拖拽，缩放
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        //设置是否能扩大扩小
        mChart.setPinchZoom(true);
        // 不显示y轴右边的值
        mChart.getAxisRight().setEnabled(false);
        // 不显示图例
        Legend legend = mChart.getLegend();
        legend.setEnabled(false);
        XAxis xAxis = mChart.getXAxis();
        // 不显示x轴
        xAxis.setDrawAxisLine(false);
        // 设置x轴数据的位置
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setTextSize(12);
        xAxis.setGridColor(Color.parseColor("#30FFFFFF"));
        YAxis yAxis = mChart.getAxisLeft();
        // 不显示y轴
        yAxis.setDrawAxisLine(false);
        // 设置y轴数据的位置
        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        // 不从y轴发出横向直线
        yAxis.setDrawGridLines(false);
        yAxis.setTextColor(Color.BLACK);
        yAxis.setTextSize(12);
        LimitLine xLimitLine = new LimitLine(4f,"xL 测试");
        xLimitLine.setLineColor(Color.GREEN);
        xLimitLine.setTextColor(Color.GREEN);
        xAxis.addLimitLine(xLimitLine);
        LimitLine yLimitLine = new LimitLine(60f,"yLimit 测试");
        yLimitLine.setLineColor(Color.RED);
        yLimitLine.setTextColor(Color.RED);
        // 获得左侧侧坐标轴
        yAxis.addLimitLine(yLimitLine);
        // add data
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        yVals1.add(new Entry(0, 60, "08-01"));
        yVals1.add(new Entry(1, 61, "08-02"));
        yVals1.add(new Entry(2, 59, "08-03"));
        yVals1.add(new Entry(3, 60, "08-04"));
        yVals1.add(new Entry(4, 61, "08-05"));
        yVals1.add(new Entry(5, 59, "08-06"));
        yVals1.add(new Entry(6, 60, "08-07"));
        yVals1.add(new Entry(7, 61, "08-08"));
        yVals1.add(new Entry(8, 59, "08-09"));
        setLineChartData(yVals1);
        mChart.animateX(2500);


        mBarChart = (BarChart) findViewById(R.id.chart2);
//        mBarChart.setOnChartValueSelectedListener(this);
        mBarChart.setDrawBarShadow(false);
        mBarChart.setDrawValueAboveBar(true);
        mBarChart.getDescription().setEnabled(false);
        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
//        mBarChart.setMaxVisibleValueCount(60);
        // scaling can now only be done on x- and y-axis separately
        mBarChart.setPinchZoom(false);
        mBarChart.setDrawGridBackground(false);
//        IAxisValueFormatter xAxisFormatter = new DayAxisValueFormatter(mBarChart);
//        XAxis xAxis = mBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setTypeface(mTfLight);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(7);
//        xAxis.setValueFormatter(xAxisFormatter);
//        IAxisValueFormatter custom = new MyAxisValueFormatter();
        YAxis leftAxis = mBarChart.getAxisLeft();
//        leftAxis.setTypeface(mTfLight);
        leftAxis.setLabelCount(8, false);
//        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        YAxis rightAxis = mBarChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
//        rightAxis.setTypeface(mTfLight);
        rightAxis.setLabelCount(8, false);
//        rightAxis.setValueFormatter(custom);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        Legend l = mBarChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
        // 不显示y轴右边的值
        mBarChart.getAxisRight().setEnabled(false);
        // 不显示图例
        Legend barLegend = mBarChart.getLegend();
        barLegend.setEnabled(false);
        XAxis barXAxis = mBarChart.getXAxis();
        // 不显示x轴
        barXAxis.setDrawAxisLine(false);
        // 设置x轴数据的位置
        barXAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        barXAxis.setTextColor(Color.BLACK);
        barXAxis.setTextSize(12);
        barXAxis.setGridColor(Color.parseColor("#30FFFFFF"));
        YAxis barYAxis = mBarChart.getAxisLeft();
        // 不显示y轴
        barYAxis.setDrawAxisLine(false);
        // 设置y轴数据的位置
        barYAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        // 不从y轴发出横向直线
        barYAxis.setDrawGridLines(false);
        barYAxis.setTextColor(Color.BLACK);
        barYAxis.setTextSize(12);
        mBarChart.getXAxis().setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return "year";
            }
        });
        // add data
        ArrayList<BarEntry> barVals = new ArrayList<>();
        barVals.add(new BarEntry(0, 60));
        barVals.add(new BarEntry(1, 61));
        barVals.add(new BarEntry(2, 59));
        barVals.add(new BarEntry(3, 60));
        barVals.add(new BarEntry(4, 61));
        barVals.add(new BarEntry(5, 59));
        barVals.add(new BarEntry(6, 60));
        barVals.add(new BarEntry(7, 61));
        barVals.add(new BarEntry(8, 59));
        setBarChartData(barVals);


        heart = (HeartRateGraphView) findViewById(R.id.heart);
        int[] heartData = {80, 81, 82, 83, 84,80, 81, 82, 83, 84,80, 81, 82, 83, 84,80, 81, 82, 83, 84,80, 81, 82, 83, 84,80, 81, 82, 83, 84,80, 81, 82, 83, 84,80, 81, 82, 83, 84};
        heart.setData(heartData);

        for (int i = 0; i < 100; i++) {
            xValue.add((i + 1) + "月");
            value.put((i + 1) + "月", (int) (Math.random() * 181 + 60));//60--240
        }
        for (int i = 0; i < 6; i++) {
            yValue.add(i * 60);
        }
        ChartView chartView = (ChartView) findViewById(R.id.chartview);
        chartView.setValue(value, xValue, yValue);

        ArrayList<com.example.xiaoniu.publicuseproject.picker.MyBarChartView.BarData> innerData = new ArrayList<>();
        innerData.add(new com.example.xiaoniu.publicuseproject.picker.MyBarChartView.BarData(14, "2月"));
        innerData.add(new com.example.xiaoniu.publicuseproject.picker.MyBarChartView.BarData(43, "3月"));
        innerData.add(new com.example.xiaoniu.publicuseproject.picker.MyBarChartView.BarData(35, "4月"));
        innerData.add(new com.example.xiaoniu.publicuseproject.picker.MyBarChartView.BarData(56, "5月"));
        innerData.add(new com.example.xiaoniu.publicuseproject.picker.MyBarChartView.BarData(12, "6月"));
        innerData.add(new com.example.xiaoniu.publicuseproject.picker.MyBarChartView.BarData(142, "7月"));
        innerData.add(new com.example.xiaoniu.publicuseproject.picker.MyBarChartView.BarData(121, "8月"));
        innerData.add(new com.example.xiaoniu.publicuseproject.picker.MyBarChartView.BarData(102, "9月"));
        innerData.add(new com.example.xiaoniu.publicuseproject.picker.MyBarChartView.BarData(238, "10月"));
        innerData.add(new com.example.xiaoniu.publicuseproject.picker.MyBarChartView.BarData(18, "11月"));
        innerData.add(new com.example.xiaoniu.publicuseproject.picker.MyBarChartView.BarData(348, "12月"));
        innerData.add(new com.example.xiaoniu.publicuseproject.picker.MyBarChartView.BarData(8, "13月"));
        innerData.add(new com.example.xiaoniu.publicuseproject.picker.MyBarChartView.BarData(258, "14月"));
        innerData.add(new com.example.xiaoniu.publicuseproject.picker.MyBarChartView.BarData(168, "15月"));
        innerData.add(new com.example.xiaoniu.publicuseproject.picker.MyBarChartView.BarData(78, "17月"));
        innerData.add(new com.example.xiaoniu.publicuseproject.picker.MyBarChartView.BarData(78, "18月"));
        innerData.add(new com.example.xiaoniu.publicuseproject.picker.MyBarChartView.BarData(78, "19月"));
        innerData.add(new com.example.xiaoniu.publicuseproject.picker.MyBarChartView.BarData(78, "20月"));
        innerData.add(new com.example.xiaoniu.publicuseproject.picker.MyBarChartView.BarData(78, "21月"));
        innerData.add(new com.example.xiaoniu.publicuseproject.picker.MyBarChartView.BarData(78, "22月"));
        innerData.add(new com.example.xiaoniu.publicuseproject.picker.MyBarChartView.BarData(78, "23月"));
        innerData.add(new com.example.xiaoniu.publicuseproject.picker.MyBarChartView.BarData(78, "24月"));
        innerData.add(new com.example.xiaoniu.publicuseproject.picker.MyBarChartView.BarData(0, "25月"));
        MyBarChartView mybarCharView = (MyBarChartView) findViewById(R.id.mybarCharView);
        mybarCharView.setBarChartData(innerData);
    }

    public void onHeightPicker(View view) {
        NumberPicker picker = new NumberPicker(this);
        picker.setCycleDisable(false);
        picker.setDividerVisible(false);
        picker.setCancelTextColor(getResources().getColor(R.color.colorPrimary));
        picker.setSubmitTextColor(getResources().getColor(R.color.colorPrimary));
        picker.setTextColor(getResources().getColor(R.color.colorPrimary));
//        picker.setTextSize((int) getResources().getDimension(R.dimen.picker_text_size));
//        picker.setTopLineVisible(false);
        picker.setTopLineColor(getResources().getColor(R.color.colorPrimary));
//        picker.setTopHeight(72);
//        picker.setOffset(2);//偏移量
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

    private void setLineChartData(final List<Entry> values) {
        LineDataSet set1;
        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, "");
            set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            // 设置曲线颜色
            set1.setColor(ColorTemplate.getHoloBlue());
            // 设置平滑曲线
//            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            set1.setCircleColor(Color.WHITE);
            set1.setLineWidth(2f);
            set1.setCircleRadius(3f);
            set1.setFillAlpha(65);
            set1.setFillColor(ColorTemplate.getHoloBlue());
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setDrawCircleHole(false);
            mChart.getXAxis().setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    return values.get((int) value).getData()+"";
                }
            });
            // 不显示坐标点的小圆点
//            set1.setDrawCircles(false);
            // 不显示坐标点的数据
//            set1.setDrawValues(false);
            // 不显示定位线
//            set1.setHighlightEnabled(false);
            // create a data object with the datasets
            LineData data = new LineData(set1);
            data.setValueTextColor(Color.WHITE);
            data.setValueTextSize(9f);
            // set data
            mChart.setData(data);
        }
    }

    private void setBarChartData(List<BarEntry> values) {
        BarDataSet set1;
        if (mBarChart.getData() != null &&
                mBarChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mBarChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            mBarChart.getData().notifyDataChanged();
            mBarChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(values, "The year 2017");
            set1.setDrawIcons(false);
            int startColor1 = ContextCompat.getColor(this, android.R.color.holo_orange_light);
            set1.setColor(startColor1);
            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);
            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(0.9f);
            mBarChart.setData(data);
        }
    }
}