package com.zpf.oillogistics.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.zpf.oillogistics.R;
import com.zpf.oillogistics.base.BaseActivity;
import com.zpf.oillogistics.net.UrlUtil;

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
        WebView webview = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = webview.getSettings();//获取webview设置属性
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);//把html中的内容放大webview等宽的一列中
        webSettings.setJavaScriptEnabled(true);//支持js
//        webSettings.setBuiltInZoomControls(true); // 显示放大缩小
//        webSettings.setSupportZoom(true); // 可以缩放
        webview.loadUrl(UrlUtil.AGREEMENT);
//        DensityUtil.
    }

    @Override
    protected void initViewAction(Bundle savedInstanceState) {

    }
}
