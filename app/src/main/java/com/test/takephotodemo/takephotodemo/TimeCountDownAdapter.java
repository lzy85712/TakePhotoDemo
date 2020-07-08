package com.test.takephotodemo.takephotodemo;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TimeCountDownAdapter extends BaseAdapter {

    private ArrayList<TimeBean> mDatas;
    private Context mContext;

    private LayoutInflater mInflater;

    //用于退出 Activity,避免 Countdown，造成资源浪费。
    private SparseArray<CountDownTimer> countDownCounters;


    public TimeCountDownAdapter(ArrayList<TimeBean> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        this.countDownCounters = new SparseArray<>();
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int i) {
        return mDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        TimeBean timeBean = mDatas.get(i);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_lv_time, viewGroup, false);
            holder = new ViewHolder(convertView);
            // holder.mTextView = (TextView)convertView.findViewById(R.id.tv_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        CountDownTimer countDownTimer = countDownCounters.get(holder.tv_time.hashCode());

        if (countDownTimer != null) {
            //将复用的倒计时清除
            countDownTimer.cancel();
        }

        final ViewHolder finalHolder = holder;

        long timer = 0;
        timer = TimeUtil.dateDiff(timeBean.getCountDownTime());
        if (timer > 0) {
            countDownTimer = new CountDownTimer(timer, 1000) {
                @Override
                public void onTick(long l) {
                    long time = l / 1000;
                    finalHolder.tv_time.setText(TimeUtil.formatLongToTimerStr(time));
                }

                @Override
                public void onFinish() {
                    finalHolder.tv_time.setText("时间结束");
                }
            }.start();
            countDownCounters.put(holder.tv_time.hashCode(), countDownTimer);
        } else {
            finalHolder.tv_time.setText("时间结束");
        }


        return convertView;
    }

    static class ViewHolder {

        @BindView(R.id.tv_time)
        TextView tv_time;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


    public void cancelAllTimers() {
        if (countDownCounters == null) {
            return;
        }
        for (int i = 0, length = countDownCounters.size(); i < length; i++) {
            CountDownTimer cdt = countDownCounters.get(countDownCounters.keyAt(i));
            if (cdt != null) {
                cdt.cancel();
            }
        }
    }
}
