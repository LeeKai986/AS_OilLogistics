package com.zpf.oillogistics.activity;

import android.os.Bundle;
import android.view.View;

import com.zpf.oillogistics.R;
import com.zpf.oillogistics.base.BaseActivity;

/**
 * Created by Administrator on 2017/9/13.
 * <p>
 * 服务条款
 */

public class ServiceTermsActivity extends BaseActivity {

    @Override
    protected int setLayout() {
        return R.layout.activity_service_terms;
    }

    @Override
    protected void initData() {
        findViewById(R.id.service_terms_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    protected void initViewAction(Bundle savedInstanceState) {

    }
}
