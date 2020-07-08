package com.test.takephotodemo.takephotodemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.apkfuns.logutils.LogUtils;
import com.kongzue.dialog.interfaces.OnDialogButtonClickListener;
import com.kongzue.dialog.interfaces.OnDismissListener;
import com.kongzue.dialog.interfaces.OnMenuItemClickListener;
import com.kongzue.dialog.interfaces.OnNotificationClickListener;
import com.kongzue.dialog.util.BaseDialog;
import com.kongzue.dialog.v3.BottomMenu;
import com.kongzue.dialog.v3.InputDialog;
import com.kongzue.dialog.v3.MessageDialog;
import com.kongzue.dialog.v3.Notification;
import com.kongzue.dialog.v3.TipDialog;

import net.grandcentrix.tray.AppPreferences;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyDialogActivity extends BaseActivity {

    @OnClick({R.id.btn_dialog_one, R.id.btn_dialog_two, R.id.btn_dialog_three, R.id.btn_dialog_four, R.id.btn_dialog_five, R.id.btn_dialog_six})
    public void showDialog(Button button) {
        switch (button.getId()) {
            case R.id.btn_dialog_one:
                MessageDialog.show(MyDialogActivity.this, "提示", "这是一条消息", "确定");
                break;
            case R.id.btn_dialog_two:
                MessageDialog.show(MyDialogActivity.this, "提示", "这是一条二按钮消息", "确定", "取消").setOnOkButtonClickListener(new OnDialogButtonClickListener() {
                    @Override
                    public boolean onClick(BaseDialog baseDialog, View v) {
                        Toast.makeText(getApplicationContext(), "你点击了确认", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }).setOnCancelButtonClickListener(new OnDialogButtonClickListener() {
                    @Override
                    public boolean onClick(BaseDialog baseDialog, View v) {
                        Toast.makeText(getApplicationContext(), "你点击了取消", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });
                break;
            case R.id.btn_dialog_three:

                InputDialog.show(MyDialogActivity.this, "输入对话框" + new AppPreferences(this).getString("TEST_STRING", ""), "输入一些内容", "确定");
                break;
            case R.id.btn_dialog_four:
                TipDialog.show(MyDialogActivity.this, "警告提示", TipDialog.TYPE.WARNING);
                break;
            case R.id.btn_dialog_five:
                Notification.show(MyDialogActivity.this, "提示", "提示信息", R.mipmap.ic_launcher).setOnNotificationClickListener(new OnNotificationClickListener() {
                    @Override
                    public void onClick() {
                        MessageDialog.show(MyDialogActivity.this, "提示", "点击了消息");
                    }
                }).setOnDismissListener(new OnDismissListener() {
                    @Override
                    public void onDismiss() {

                    }
                });


                break;
            case R.id.btn_dialog_six:
                List<String> datas = new ArrayList<>();
                datas.add("菜单1");
                datas.add("菜单2");
                datas.add("菜单3");

                BaseAdapter baseAdapter = new ArrayAdapter(MyDialogActivity.this, com.kongzue.dialog.R.layout.item_bottom_menu_kongzue, datas);

                BottomMenu.show(MyDialogActivity.this, baseAdapter, new OnMenuItemClickListener() {
                    @Override
                    public void onClick(String text, int index) {
                        //注意此处的 text 返回为自定义 Adapter.getItem(position).toString()，如需获取自定义Object，请尝试 datas.get(index)
                        Toast.makeText(getApplicationContext(), "点击内容是 = " + text, Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mydialog);
        ButterKnife.bind(this);
    }
}
