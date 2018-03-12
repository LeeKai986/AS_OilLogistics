package com.zpf.oillogistics.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zpf.oillogistics.R;
import com.zpf.oillogistics.base.BaseActivity;
import com.zpf.oillogistics.base.MessageWhat;
import com.zpf.oillogistics.bean.response.OderoverResponse;
import com.zpf.oillogistics.net.SimplifyThread;
import com.zpf.oillogistics.net.UrlUtil;
import com.zpf.oillogistics.utils.DateTimeUtil;
import com.zpf.oillogistics.utils.MyToast;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/15.
 * <p>
 * 订单支付成功
 */

public class OrderPaySuccessActivity extends BaseActivity {
    @BindView(R.id.rel_back)
    RelativeLayout relBack;
    @BindView(R.id.tv_checkOrder)
    TextView tvCheckOrder;
    @BindView(R.id.tv_orderNum)
    TextView tvOrderNum;
    @BindView(R.id.tv_orderTime)
    TextView tvOrderTime;
    @BindView(R.id.tv_orderStatus)
    TextView tvOrderStatus;
    @BindView(R.id.tv_orderMoney)
    TextView tvOrderMoney;
    @BindView(R.id.tv_goodsName)
    TextView tvGoodsName;
    @BindView(R.id.tv_company)
    TextView tvCompany;
    @BindView(R.id.tv_adress)
    TextView tvAdress;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_goodsNum)
    TextView tvGoodsNum;
    @BindView(R.id.platform_issue_order_share_tv)
    TextView tvBackMain;

    private String no = "";

    @Override
    protected int setLayout() {
        return R.layout.activity_order_pay_success;
    }

    @Override
    protected void initData() {
        no = getIntent().getExtras().getString("no", "");
        orderOver();
    }

    @Override
    protected void initViewAction(Bundle savedInstanceState) {

        relBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tvCheckOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OrderPaySuccessActivity.this, SelfCompanyOrderActivity.class));
            }
        });

        tvBackMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderPaySuccessActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    /**
     * 生成订单
     */
    private void orderOver() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("no", no);

        SimplifyThread simplifyThread = new SimplifyThread(UrlUtil.URL_ORDER_OVER, hashMap);
        simplifyThread.setOnKeyResultListener(new SimplifyThread.OnResultListener() {
            @Override
            public void resultBody(String res) {
                Message message = new Message();
                message.obj = res;
                message.what = MessageWhat.ORDER_PAY;
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
                case MessageWhat.ORDER_PAY:
                    if (msg.obj != null) {
                        try {
                            OderoverResponse over = gson.fromJson(msg.obj.toString(), OderoverResponse.class);

                            if (over.getStatus() == 0) {
                                OderoverResponse.DataBean dataBean = over.getData();
                                if (dataBean.getNo() != null) {
                                    tvOrderNum.setText(dataBean.getNo());
                                }

                                if (dataBean.getTime() != 0) {
                                    tvOrderTime.setText(DateTimeUtil.stampToDate("yyyy-MM-dd HH:mm:ss", dataBean.getTime() + "000"));
                                }
                                switch (dataBean.getStatus()) {
                                    case 0:
                                        tvOrderStatus.setText("定金未支付");
                                        break;
                                    case 1:
                                        tvOrderStatus.setText("定金已支付");
                                        break;
                                    case 2:
                                        tvOrderStatus.setText("已付款");
                                        break;
                                }
                                if (dataBean.getMoney() != null && !dataBean.getMoney().equals("")) {
                                    tvOrderMoney.setText(dataBean.getMoney() + "元");
                                }
                                if (dataBean.getGoodsname() != null) {
                                    tvGoodsName.setText(dataBean.getGoodsname());
                                }
                                if (dataBean.getCompany() != null) {
                                    tvCompany.setText(dataBean.getCompany());
                                }

                                if (dataBean.getAddress() != null) {
                                    tvAdress.setText(dataBean.getAddress());
                                }
                                if (dataBean.getPhone() != null) {
                                    tvPhone.setText(dataBean.getPhone());
                                }
                                if (dataBean.getPrice() != null) {
                                    tvPrice.setText(dataBean.getPrice() + "元/吨");
                                }
                                if (dataBean.getNumber() != 0) {
                                    tvGoodsNum.setText(dataBean.getNumber() + "吨");
                                }
                            } else {
                                MyToast.show(OrderPaySuccessActivity.this, "暂无数据!");
                            }

                        } catch (Exception e) {
                            MyToast.show(OrderPaySuccessActivity.this, "返回数据异常!");
                        }

                    } else {
                        MyToast.show(OrderPaySuccessActivity.this, "返回数据失败!");
                    }
                    break;
            }
        }
    };
}
