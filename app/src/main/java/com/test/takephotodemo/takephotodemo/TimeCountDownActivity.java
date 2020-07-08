package com.test.takephotodemo.takephotodemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.apkfuns.logutils.LogUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TimeCountDownActivity extends BaseActivity {
    @BindView(R.id.lv_time)
    ListView lv_time;
    TimeCountDownAdapter timeCountDownAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_count_down);
        ButterKnife.bind(this);
        initData();

        LogUtils.d("时间差= " + TimeUtil.dateDiff("2020-06-27 14:20:00"));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        timeCountDownAdapter.cancelAllTimers();
    }

    public void initData() {
        ArrayList<TimeBean> al = new ArrayList<>();

        al.add(new TimeBean("2020-06-27 14:20:00"));
        al.add(new TimeBean("2020-06-27 15:20:00"));
        al.add(new TimeBean("2020-06-27 16:20:00"));
        al.add(new TimeBean("2020-06-27 17:20:00"));
        al.add(new TimeBean("2020-06-27 18:20:00"));
        al.add(new TimeBean("2020-06-27 19:20:00"));
        al.add(new TimeBean("2020-06-27 20:20:00"));
        al.add(new TimeBean("2020-06-27 21:20:00"));
        al.add(new TimeBean("2020-06-27 22:20:00"));
        al.add(new TimeBean("2020-06-27 23:20:00"));
        al.add(new TimeBean("2020-06-27 14:20:00"));
        al.add(new TimeBean("2020-06-27 20:20:00"));
        al.add(new TimeBean("2020-06-27 20:20:00"));
        al.add(new TimeBean("2020-06-27 20:20:00"));
        al.add(new TimeBean("2020-06-27 20:20:00"));
        al.add(new TimeBean("2020-06-28 01:20:00"));
        al.add(new TimeBean("2020-06-28 03:20:00"));
        al.add(new TimeBean("2020-06-28 04:20:00"));
        al.add(new TimeBean("2020-06-28 05:20:00"));
        al.add(new TimeBean("2020-06-28 06:20:00"));
//        al.add(new TimeBean("700000"));
//        al.add(new TimeBean("7000000"));
//        al.add(new TimeBean("1110000"));
//        al.add(new TimeBean("10000222"));
//        al.add(new TimeBean("40000122"));
//        al.add(new TimeBean("56214000"));
//        al.add(new TimeBean("711112222"));
//        al.add(new TimeBean("111110000"));
//        al.add(new TimeBean("410000000"));
//        al.add(new TimeBean("70330000"));
//        al.add(new TimeBean("19932000"));
//        al.add(new TimeBean("10000222"));
//        al.add(new TimeBean("703333000"));
//        al.add(new TimeBean("110033300"));
//        al.add(new TimeBean("100440222"));
//        al.add(new TimeBean("70337000"));
//        al.add(new TimeBean("1963000"));
//        al.add(new TimeBean("10050222"));
//        al.add(new TimeBean("703633000"));
//        al.add(new TimeBean("11633300"));
//        al.add(new TimeBean("103340222"));

        timeCountDownAdapter = new TimeCountDownAdapter(al, getApplicationContext());
        lv_time.setAdapter(timeCountDownAdapter);
        timeCountDownAdapter.notifyDataSetChanged();
    }
}
