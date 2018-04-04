package com.zpf.oillogistics.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zpf.oillogistics.R;
import com.zpf.oillogistics.base.BaseActivity;
import com.zpf.oillogistics.base.Constant;
import com.zpf.oillogistics.base.CyApplication;
import com.zpf.oillogistics.customview.NavItem;
import com.zpf.oillogistics.utils.ClearMemory;
import com.zpf.oillogistics.utils.MyIntent;
import com.zpf.oillogistics.utils.MyShare;
import com.zpf.oillogistics.utils.MyToast;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/18.
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.rel_back_setting)
    RelativeLayout relBack;
    @BindView(R.id.nav_password_setting)
    NavItem navPassword;
    @BindView(R.id.nav_feedback_setting)
    NavItem navFeedback;
    @BindView(R.id.nav_version_setting)
    NavItem navVersion;
    @BindView(R.id.nav_clear_setting)
    NavItem navClear;
    @BindView(R.id.tv_exit_self)
    TextView tvExit;

    @Override
    protected int setLayout() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initData() {
        relBack.setOnClickListener(this);
        navFeedback.setOnClickListener(this);
        navPassword.setOnClickListener(this);
        navVersion.setOnClickListener(this);
        navClear.setOnClickListener(this);
        tvExit.setOnClickListener(this);


        try {
            navClear.setTvActionState((ClearMemory.getTotalCacheSize(SettingActivity.this)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initViewAction(Bundle savedInstanceState) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rel_back_setting:
                finish();
                break;
            case R.id.nav_password_setting:
                Bundle bundle = new Bundle();
                bundle.putString("type", "alterPass");
                new MyIntent(SettingActivity.this).startAct(ForgetActivity.class, bundle);
                break;
            case R.id.nav_feedback_setting:
                new MyIntent(SettingActivity.this).startAct(SelfFeedBackActivity.class);
                break;
            case R.id.nav_version_setting:
                break;
            case R.id.nav_clear_setting:
                if (navClear.getTvActionState().equals("0.0K")) {
                    MyToast.show(SettingActivity.this, "无缓存数据");
                    return;
                }
                File file = new File(Constant.APK_CASHE_URI);
                if (file != null && file.exists()
                        && file.isDirectory()) {
                    for (File item : file.listFiles()) {
                        if (!item.isFile()) {
                            for (File f : item.listFiles()) {
                                f.delete();
                            }
                        } else {
                            item.delete();
                        }
                    }
                    MyToast.show(SettingActivity.this, "缓存已清理");
                    try {
                        navClear.setTvActionState(ClearMemory.getTotalCacheSize(SettingActivity.this));
                    } catch (Exception e) {
                        e.printStackTrace();
                        MyToast.show(SettingActivity.this, "缓存大小获取失败:" + e.toString());
                    }
                }
                break;
            case R.id.tv_exit_self:
//                EMClient.getInstance().logout(true);
//                new InviteMessgeDao(SettingActivity.this).clearMessage();
                SharedPreferences.Editor editor = MyShare.getShared().edit();
                editor.clear();
                editor.commit();
                CyApplication.province = "";
                CyApplication.area = "";
                CyApplication.adress = "";
                CyApplication.lat = "";
                CyApplication.lon = "";
                Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
        }
    }
}
