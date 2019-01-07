package com.example.xiaoniu.publicuseproject.lottery;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xiaoniu.publicuseproject.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class LotteryActivity extends AppCompatActivity {

    private EditText mTotalNumber;
    private EditText mName;
    private Button mGoBtn;
    private ListView mListView;
    private int mNumCount;
    private String mYourName;
    private int mCurrentNum;
    private LinkedHashMap<String, Integer> mNumMap = new LinkedHashMap<>();
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottery);
        mTotalNumber = (EditText) findViewById(R.id.total_number);
        mName = (EditText) findViewById(R.id.name);
        mGoBtn = (Button) findViewById(R.id.go);
        mListView = (ListView) findViewById(R.id.listview);
        mNumMap.clear();
        mGoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mNumCount > 0 && !TextUtils.isEmpty(mYourName)) {
                    mCurrentNum = doLottery(mNumCount);
                    if (mNumMap.size() > 0) {
                        if (mNumMap.containsKey(mYourName)) {
                            Toast.makeText(LotteryActivity.this, "名字再确认下", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (mNumMap.containsValue(mCurrentNum)) {
                            if (mNumMap.size() < mNumCount) {
                                Log.e("lottery", "performClick " + mCurrentNum);
                                mGoBtn.performClick();
                            }
                        } else {
                            mNumMap.put(mYourName, mCurrentNum);
                            Log.e("lottery", "!containsValue " + mCurrentNum);
                            addToList();
                        }
                    } else {
                        mNumMap.put(mYourName, mCurrentNum);
                        Log.e("lottery", "first " + mCurrentNum);
                        addToList();
                    }
                } else {
                    Toast.makeText(LotteryActivity.this, "抽签人数和名字再确认下", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mTotalNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mNumCount = Integer.parseInt(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mYourName = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        return;
    }

    private int doLottery(int maxNum) {
        return (int) (1 + Math.random() * (maxNum - 1 + 1));
        //(数据类型)(最小值+Math.random()*(最大值-最小值+1))
    }

    private void addToList() {
        myAdapter = new MyAdapter(mNumMap);
        mListView.setAdapter(myAdapter);
        for (LinkedHashMap.Entry<String, Integer> entry : mNumMap.entrySet()) {
            Log.e("lottery", "map: " + entry.getKey() + "-" + entry.getValue());
        }
        Toast.makeText(LotteryActivity.this, "抽签完毕！请在列表中查看结果", Toast.LENGTH_SHORT).show();
    }

    static class ViewHolder {
        TextView name;
        TextView value;
    }

    final class MyAdapter extends BaseAdapter {

        private final ArrayList mData;

        public MyAdapter(LinkedHashMap<String, Integer> map) {
            mData = new ArrayList();
            mData.addAll(map.entrySet());
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public LinkedHashMap.Entry<String, Integer> getItem(int position) {
            return (LinkedHashMap.Entry) mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO implement you own logic with ID
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            final ViewHolder viewHolder;
            if (convertView == null) {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_key_value_list, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.name = (TextView) view.findViewById(R.id.title);
                viewHolder.value = (TextView) view.findViewById(R.id.value);
                view.setTag(viewHolder);
            } else {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            }
            LinkedHashMap.Entry<String, Integer> item = getItem(position);
            viewHolder.name.setText(item.getKey());
            viewHolder.value.setText(item.getValue() + "");
            return view;
        }
    }
}