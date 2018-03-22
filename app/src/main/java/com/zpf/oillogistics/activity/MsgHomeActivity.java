package com.zpf.oillogistics.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zpf.oillogistics.R;
import com.zpf.oillogistics.base.BaseActivity;
import com.zpf.oillogistics.bean.MsgClickBean;
import com.zpf.oillogistics.net.SimplifyThread;
import com.zpf.oillogistics.net.UrlUtil;
import com.zpf.oillogistics.utils.MyShare;
import com.zpf.oillogistics.utils.MyToast;

import java.util.HashMap;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/9/13.
 * <p>
 * 首页信息
 */

public class MsgHomeActivity extends BaseActivity {

    // 布局相关
    // 返回
    @BindView(R.id.back_rl)
    RelativeLayout backRl;
    // 系统消息
    @BindView(R.id.msg_home_system_all_ll)
    LinearLayout systemLl;
    // 产品信息
    @BindView(R.id.msg_home_product_all_ll)
    LinearLayout productLl;
    // 报价信息
    @BindView(R.id.msg_home_quote_all_ll)
    LinearLayout quoteLl;
    // 系统消息数量
    @BindView(R.id.msg_home_system_num_tv)
    TextView systemNumTv;
    // 产品消息数量
    @BindView(R.id.msg_home_product_num_tv)
    TextView productNumTv;
    // 报价消息数量
    @BindView(R.id.msg_home_quote_num_tv)
    TextView quoteNumTv;
    @BindView(R.id.tv_xt)
    TextView tv_xt;
    @BindView(R.id.tv_cp)
    TextView tv_cp;
    @BindView(R.id.tv_bj)
    TextView tv_bj;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    MyToast.show(MsgHomeActivity.this, message.obj.toString());
                    break;
                case 1:
                    MsgClickBean msgClickBean = new Gson().fromJson(message.obj.toString(), MsgClickBean.class);
                    if (msgClickBean != null && msgClickBean.getData() != null && msgClickBean.getData().getSys() != 0) {
                        systemNumTv.setVisibility(View.VISIBLE);
                        systemNumTv.setText(msgClickBean.getData().getSys() + "");
                        tv_xt.setText("您有新的消息");
                    } else {
                        tv_xt.setText("暂无新消息");
                        systemNumTv.setVisibility(View.GONE);
                    }
                    if (msgClickBean != null && msgClickBean.getData() != null && msgClickBean.getData().getGoods() != 0) {
                        productNumTv.setVisibility(View.VISIBLE);
                        productNumTv.setText(msgClickBean.getData().getGoods() + "");
                        tv_cp.setText("您有新的消息");
                    } else {
                        tv_cp.setText("暂无新消息");
                        productNumTv.setVisibility(View.GONE);
                    }
                    if (msgClickBean != null && msgClickBean.getData() != null && msgClickBean.getData().getOffer() != 0) {
                        quoteNumTv.setVisibility(View.VISIBLE);
                        quoteNumTv.setText(msgClickBean.getData().getOffer() + "");
                        tv_bj.setText("您有新的消息");
                    } else {
                        tv_bj.setText("暂无新消息");
                        quoteNumTv.setVisibility(View.GONE);
                    }
                    break;
            }
            return false;
        }
    });

    @Override
    protected int setLayout() {
        return R.layout.activity_msg_home;
    }

    @Override
    protected void initData() {
        HashMap<String, String> getAttentionNum = new HashMap<>();
        getAttentionNum.put("id", MyShare.getShared().getString("userId", ""));
        SimplifyThread getAttentionThread = new SimplifyThread(UrlUtil.URL_USER_NUM, getAttentionNum);
        getAttentionThread.setOnKeyResultListener(new SimplifyThread.OnResultListener() {
            @Override
            public void resultBody(String res) {
                Message msg = new Message();
                msg.obj = res;
                msg.what = 1;
                handler.sendMessage(msg);
            }

            @Override
            public void errorBody(String error) {
                Message msg = new Message();
                msg.obj = error;
                msg.what = 0;
                handler.sendMessage(msg);
            }
        });
    }

    @Override
    protected void initViewAction(Bundle savedInstanceState) {
        if (MyShare.getShared().getString("userType", "").equals("3")) {
            productLl.setVisibility(View.GONE);
            quoteLl.setVisibility(View.GONE);
        }
        backRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        systemLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MsgHomeActivity.this, MsgHomeSystemActivity.class), 1);
            }
        });
        productLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MsgHomeActivity.this, MsgHomeProductActivity.class), 1);
            }
        });
        quoteLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MsgHomeActivity.this, MsgHomeQuoteActivity.class), 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 11) {
            systemNumTv.setVisibility(View.GONE);
        } else if (resultCode == 12) {
            productNumTv.setVisibility(View.GONE);
        } else if (resultCode == 13) {
            quoteNumTv.setVisibility(View.GONE);
        }
        initData();
    }
}
