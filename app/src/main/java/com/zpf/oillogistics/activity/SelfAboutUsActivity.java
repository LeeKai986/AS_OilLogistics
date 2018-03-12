package com.zpf.oillogistics.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zpf.oillogistics.R;
import com.zpf.oillogistics.base.BaseActivity;
import com.zpf.oillogistics.base.MessageWhat;
import com.zpf.oillogistics.bean.FeedBack;
import com.zpf.oillogistics.bean.response.AboutUsResponse;
import com.zpf.oillogistics.bean.response.DriverOrderResponse;
import com.zpf.oillogistics.net.SimplifyThread;
import com.zpf.oillogistics.net.UrlUtil;
import com.zpf.oillogistics.utils.MyToast;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/10/16.
 */

public class SelfAboutUsActivity extends BaseActivity {
    @BindView(R.id.wv_contant)
    WebView wvContant;
    @BindView(R.id.rel_back)
    RelativeLayout relBack;

    @Override
    protected int setLayout() {
        return R.layout.activity_self_aboutus;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViewAction(Bundle savedInstanceState) {
        relBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getData();

    }

    /**
     * 提货接口
     */
    private void getData() {

        SimplifyThread simplifyThread = new SimplifyThread(UrlUtil.URL_ABOUT_INDEX, new HashMap<String, String>());
        simplifyThread.setOnKeyResultListener(new SimplifyThread.OnResultListener() {
            @Override
            public void resultBody(String res) {
                Message message = new Message();
                message.obj = res;
                message.what = MessageWhat.SELF_ABOUT_US;
                handler.sendMessage(message);
            }

            @Override
            public void errorBody(String error) {
                handler.sendEmptyMessage(MessageWhat.REQUST_ERROR);
            }
        });
    }


    private Gson gson = new Gson();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MessageWhat.SELF_ABOUT_US:
                    if (msg.obj != null) {
                        try {
                            AboutUsResponse order = gson.fromJson(msg.obj.toString(), AboutUsResponse.class);

                            if (order.getStatus() == 0) {
//                                tvContant.setText(order.getData().get(0).getContent());
                                wvContant.getSettings().setDomStorageEnabled(true);
                                wvContant.getSettings().setJavaScriptEnabled(true);
                                wvContant.loadDataWithBaseURL(null,
                                        "<html><head><style>img{width:100%;}</style></head><div id='test' style='width:100%;overflow:hidden'>"
                                                + order.getData().getContent() + "</div>"
                                                + "</html>", "text/html", "utf-8", null);
                            } else {
                                MyToast.show(SelfAboutUsActivity.this, "暂无数据!");
                            }

                        } catch (Exception e) {
                            MyToast.show(SelfAboutUsActivity.this, "返回数据异常!");
                        }

                    } else {
                        MyToast.show(SelfAboutUsActivity.this, "返回数据失败!");
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}
