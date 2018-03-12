package com.zpf.oillogistics.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zpf.oillogistics.R;
import com.zpf.oillogistics.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/9/15.
 * <p>
 * 订单生成成功
 */

public class OrderCreateSuccessActivity extends BaseActivity {

    // 布局相关
    // 订单支付
    @BindView(R.id.order_create_success_pay_tv)
    TextView payTv;

    @Override
    protected int setLayout() {
        return R.layout.activity_order_create_success;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViewAction(Bundle savedInstanceState) {
        payTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OrderCreateSuccessActivity.this, OrderPayActivity.class));
            }
        });
    }
}
