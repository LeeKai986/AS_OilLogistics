package com.zpf.oillogistics.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zpf.oillogistics.R;
import com.zpf.oillogistics.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/11/24.
 * <p>
 * 分享成功
 */

public class ShareSuccessActivity extends BaseActivity {

    // 返回
    @BindView(R.id.rel_back)
    RelativeLayout backRl;
    // 下载
    @BindView(R.id.share_success_download_tv)
    TextView downloadTv;

    @Override
    protected int setLayout() {
        return R.layout.activity_share_success;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViewAction(Bundle savedInstanceState) {
        backRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        downloadTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse("https://fir.im/fsa2");
                intent.setData(content_url);
                startActivity(intent);
            }
        });
    }
}
