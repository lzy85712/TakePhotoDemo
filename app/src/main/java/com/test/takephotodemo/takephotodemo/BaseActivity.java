package com.test.takephotodemo.takephotodemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.gyf.immersionbar.ImmersionBar;


public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        ImmersionBar.with(this).transparentBar().statusBarDarkFont(true).fitsSystemWindows(true).init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
