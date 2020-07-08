package com.test.takephotodemo.takephotodemo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.apkfuns.logutils.LogUtils;
import com.bumptech.glide.Glide;
import com.example.zhouwei.library.CustomPopWindow;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.TResult;
import com.wang.avi.AVLoadingIndicatorView;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import net.grandcentrix.tray.AppPreferences;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends TakePhotoActivity {

    // 拍照相关
    private TakePhoto takePhoto;
    private CropOptions cropOptions;  //裁剪参数
    private CompressConfig compressConfig;  //压缩参数
    private Uri imageUri;  //图片保存路径

    // pop
    private CustomPopWindow mCustomPopWindow;


    @BindView(R.id.iv_show)
    ImageView iv_show;
    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;
    @BindView(R.id.btn_pop)
    Button btn_pop;
    // 轮播图
    @BindView(R.id.test_banner)
    Banner test_banner;


    @OnClick(R.id.btn_show_loading)
    public void showLoading() {
        avi.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.btn_close_loading)
    public void closeLoading() {
        avi.setVisibility(View.GONE);
    }

    @OnClick(R.id.btn_pop)
    public void showPop() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.test_pop, null);
        mCustomPopWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(contentView)
                .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.7f) // 控制亮度
                .size(400, 400)
                .create()
                .showAsDropDown(btn_pop, 0, 0);

        TextView textView_A = contentView.findViewById(R.id.tv_a);
        TextView textView_B = contentView.findViewById(R.id.tv_b);
        textView_A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "你点击了A", Toast.LENGTH_SHORT).show();
                mCustomPopWindow.dissmiss();
            }
        });

        textView_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "你点击了B", Toast.LENGTH_SHORT).show();
                mCustomPopWindow.dissmiss();
            }
        });
    }

    @OnClick(R.id.btn_second)
    public void jump() {
        startActivity(new Intent(this, SecondActivity.class));
    }


    @OnClick(R.id.btn_dialog)
    public void showDialog() {
        startActivity(new Intent(this, MyDialogActivity.class));
    }

    @OnClick(R.id.btn_takephoto)
    public void showTakePhoto() {
        if (takePhoto != null) {
            imageUri = getImageCropUri();
            //拍照并裁剪
            takePhoto.onPickFromCaptureWithCrop(imageUri, cropOptions);
            //仅仅拍照不裁剪
            //takePhoto.onPickFromCapture(imageUri);
        } else {
            initPermission();
        }
    }


    @OnClick(R.id.btn_time)
    public void jumpToTimeList() {
        startActivity(new Intent(getApplicationContext(), TimeCountDownActivity.class));
    }

    @OnClick(R.id.btn_selectphoto)
    public void selectFromPic() {
        if (takePhoto != null) {
            imageUri = getImageCropUri();
            //拍照并裁剪
            takePhoto.onPickFromGalleryWithCrop(imageUri, cropOptions);
            //仅仅拍照不裁剪
            //takePhoto.onPickFromGallery();
        } else {
            initPermission();
        }
    }


    //获得照片的输出保存Uri
    private Uri getImageCropUri() {
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        return Uri.fromFile(file);
    }


    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        String iconPath = result.getImage().getOriginalPath();
        //Toast显示图片路径
        Toast.makeText(this, "imagePath:" + iconPath, Toast.LENGTH_SHORT).show();
        Glide.with(this).load(iconPath).into(iv_show);

    }


    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    // 初始化数据
    public void initData() {
        takePhoto = getTakePhoto();
        //设置裁剪参数
        cropOptions = new CropOptions.Builder().setAspectX(1).setAspectY(1).setWithOwnCrop(false).create();
        //设置压缩参数
        compressConfig = new CompressConfig.Builder().setMaxSize(50 * 1024).setMaxPixel(800).create();
        takePhoto.onEnableCompress(compressConfig, true); //设置为需要压缩
    }

    // 初始化轮播图
    public void initBanner() {


        List<String> images = new ArrayList<>();
        images.add("http://res.lgdsunday.club/poster-1.png");
        images.add("http://res.lgdsunday.club/poster-2.png");
        images.add("http://res.lgdsunday.club/poster-3.png");
        images.add("http://res.lgdsunday.club/poster-4.png");
        images.add("http://res.lgdsunday.club/poster-5.png");

        //设置图片加载器
        test_banner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {

            }
        });
        //设置图片集合
        test_banner.setImages(images);
        test_banner.isAutoPlay(true);
        // 轮播时间
        test_banner.setDelayTime(3000);
        // 指示器显示位置
        test_banner.setIndicatorGravity(BannerConfig.RIGHT);
        // banner设置方法全部调用完毕时最后调用
        test_banner.start();
        // 点击事件
        test_banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                switch (position) {
                    case 0:
                        Toast.makeText(getApplicationContext(), "点击了第一张轮播图", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(getApplicationContext(), "点击了第二张轮播图", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(getApplicationContext(), "点击了第三张轮播图", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(getApplicationContext(), "点击了第四张轮播图", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Toast.makeText(getApplicationContext(), "点击了第五张轮播图", Toast.LENGTH_SHORT).show();
                        break;

                }
            }
        });

        // 测试一下跨进程存储的用法
        AppPreferences appPreferences = new AppPreferences(this);
        appPreferences.put("TEST_STRING", "首页跨进程存储");

    }


    //申请相关权限
    public void initPermission() {
        // 申请权限。
        AndPermission.with(this)
                .permission(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE).onGranted(new Action() {
            @Override
            public void onAction(List<String> permissions) {
                initData();
                Toast.makeText(getApplicationContext(), "用户同意了", Toast.LENGTH_SHORT).show();
            }
        }).onDenied(new Action() {
            @Override
            public void onAction(List<String> permissions) {
                Toast.makeText(getApplicationContext(), "拍照上传图片功能被禁止，被禁止的功能将无法使用", Toast.LENGTH_SHORT).show();
            }
        }).start();


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        avi.setVisibility(View.GONE);
        LogUtils.d("当前版本: = " + android.os.Build.VERSION.SDK_INT);
        // 6.0运行时权限判断
        if (android.os.Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PermissionChecker.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PermissionChecker.PERMISSION_GRANTED) {
            initPermission();
        } else {
            initData();
        }

        initBanner();


//        String testUrl = "https://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=13215546701";
//        RequestParams params = new RequestParams(testUrl);
//        x.http().get(params, new Callback.CommonCallback<String>() {
//
//            @Override
//            public void onSuccess(String result) {
//                LogUtils.d("result = " + result);
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//
//            }
//
//            @Override
//            public void onCancelled(CancelledException cex) {
//
//            }
//
//            @Override
//            public void onFinished() {
//
//            }
//        });
    }
}
