package com.zpf.oillogistics.activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zpf.oillogistics.R;
import com.zpf.oillogistics.base.BaseActivity;
import com.zpf.oillogistics.utils.MyShare;
import com.zpf.oillogistics.utils.MyToast;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/9/13.
 * <p>
 * 引导页
 */

public class GuidanceActivity extends BaseActivity {

    // 布局相关
    // vp
    @BindView(R.id.guidance_vp)
    ViewPager vp;
    // 原点layout
    @BindView(R.id.guidance_site_ll)
    LinearLayout pointLl;
    // 立即体验
    @BindView(R.id.guidance_come_in_tv)
    TextView comeInTv;
    // iv集
    ArrayList<ImageView> ivs;
    // 图片id集
    int[] images = {R.mipmap.guide_1, R.mipmap.guide_2, R.mipmap.guide_3};
    // 圆点间隔
    int pointWidth;
    // 蓝色圆点
    @BindView(R.id.guidance_blue_v)
    View bluePoint;
    // adapter
    GuidanceAdapter adapter;

    @Override
    protected int setLayout() {
        return R.layout.activity_guidance;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViewAction(Bundle savedInstanceState) {
        if (!MyShare.getShared(GuidanceActivity.this, "isFirst").getString("isFirstOn", "").equals("")) {
            startActivity(new Intent(GuidanceActivity.this, SplashActivity.class));
            finish();
            return;
        }
        ivs = new ArrayList<>();
        for (int i = 0; i < images.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setBackgroundResource(images[i]);// 设置引导页背景
            ivs.add(iv);
        }
        comeInTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestRunTimePermissions(new String[]{
                            Manifest.permission.RECEIVE_BOOT_COMPLETED,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_WIFI_STATE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.RECORD_AUDIO,
                            Manifest.permission.CAMERA
                    }, new PermissionCall() {
                        @Override
                        public void requestSuccess() {
                            new Thread() {
                                @Override
                                public void run() {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {

                                            if (!Settings.System.canWrite(GuidanceActivity.this)) {
                                                MyToast.show(GuidanceActivity.this, "请在该设置页面勾选，才可以使用路况提醒功能");
                                                Uri selfPackageUri = Uri.parse("package:"
                                                        + GuidanceActivity.this.getPackageName());
                                                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS,
                                                        selfPackageUri);
                                                startActivity(intent);
                                            }
                                            MyShare.getShared(GuidanceActivity.this, "isFirst").edit().putString("isFirstOn", "1").commit();
                                            startActivity(new Intent(GuidanceActivity.this, MainActivity.class));
                                            finish();
                                        }
                                    });
                                }
                            }.start();
                        }

                        @Override
                        public void refused() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    MyToast.show(GuidanceActivity.this, "请授予相关权限!");
//                                Intent localIntent = new Intent();
//                                localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                if (Build.VERSION.SDK_INT >= 9) {
//                                    localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
//                                    localIntent.setData(Uri.fromParts("package", getPackageName(), null));
//                                } else if (Build.VERSION.SDK_INT <= 8) {
//                                    localIntent.setAction(Intent.ACTION_VIEW);
//                                    localIntent.setClassName("com.android.settings","com.android.settings.InstalledAppDetails");
//                                    localIntent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
//                                }
//                                startActivity(localIntent);
                                    MyShare.getShared(GuidanceActivity.this, "isFirst").edit().putString("isFirstOn", "1").commit();
                                    startActivity(new Intent(GuidanceActivity.this, MainActivity.class));
                                    finish();
                                }
                            });
                        }
                    });
                } else {
                    MyShare.getShared(GuidanceActivity.this, "isFirst").edit().putString("isFirstOn", "1").commit();
                    startActivity(new Intent(GuidanceActivity.this, MainActivity.class));
                    finish();
                }

            }
        });

        for (int i = 0; i < images.length; i++) {
            View point = new View(this);
            point.setBackgroundResource(R.drawable.bg_oval_gray_ten);// 设置引导页默认圆点
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
            if (i > 0) {
                params.leftMargin = 20;// 设置圆点间隔
            }
            point.setLayoutParams(params);// 设置圆点的大小
            pointLl.addView(point);// 将圆点添加给线性布局
        }

        pointLl.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {

                    // 当layout执行结束后回调此方法
                    @Override
                    public void onGlobalLayout() {

                        pointLl.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        pointWidth = pointLl.getChildAt(1).getLeft() - pointLl.getChildAt(0).getLeft();
                    }
                });

        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int len = (int) (pointWidth * positionOffset) + position * pointWidth;
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) bluePoint.getLayoutParams();// 获取当前蓝点的布局参数
                params.leftMargin = len;// 设置左边距
                bluePoint.setLayoutParams(params);// 重新给蓝点设置布局参数
            }

            @Override
            public void onPageSelected(int position) {
                if (position == ivs.size() - 1) {// 最后一个页面
                    comeInTv.setVisibility(View.VISIBLE);// 显示立即进入的按钮
                } else {
                    comeInTv.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        adapter = new GuidanceAdapter();
        vp.setAdapter(adapter);
    }

    class GuidanceAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return ivs != null && ivs.size() != 0 ? ivs.size() : 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(ivs.get(position));
            return ivs.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
