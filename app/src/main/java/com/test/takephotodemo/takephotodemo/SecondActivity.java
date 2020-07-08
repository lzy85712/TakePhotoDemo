package com.test.takephotodemo.takephotodemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SecondActivity extends BaseActivity {

    @BindView(R.id.lv_test)
    ListView lv_test;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ButterKnife.bind(this);
        initData();

    }


    public void initData() {
        final List<TestBean> al = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            TestBean testBean = new TestBean();
            testBean.setTitle("title" + i);
            testBean.setContent("爱可登极大地加快拉倒就爱了的接口垃圾枯鲁杜鹃阿卡丽进度款拉达克拉进来开打了卡里的卡拉肯德基拉到了" + i);
            al.add(testBean);
        }
        lv_test.setAdapter(new TestAdapter(getApplicationContext(), R.layout.item_test_common, al));
        lv_test.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), "点击的title = " + al.get(i).getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
