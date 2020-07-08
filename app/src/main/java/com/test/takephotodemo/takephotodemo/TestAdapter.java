package com.test.takephotodemo.takephotodemo;

import android.content.Context;

import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class TestAdapter extends CommonAdapter<TestBean> {
    public TestAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, TestBean testBean, int position) {
        // 设置数据
        viewHolder.setText(R.id.tv_title, testBean.getTitle()).setText(R.id.tv_content, testBean.getContent());

    }
}
