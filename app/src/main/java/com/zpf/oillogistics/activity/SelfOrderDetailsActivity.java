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
import com.zpf.oillogistics.adapter.CompanyOrderAdapter;
import com.zpf.oillogistics.base.BaseActivity;
import com.zpf.oillogistics.base.MessageWhat;
import com.zpf.oillogistics.bean.response.CompanyOrderDetailsResponse;
import com.zpf.oillogistics.bean.response.CompanyOrderResponse;
import com.zpf.oillogistics.diy.DiyDialog;
import com.zpf.oillogistics.fragment.AllOrderCompanyFragment;
import com.zpf.oillogistics.net.SimplifyThread;
import com.zpf.oillogistics.net.UrlUtil;
import com.zpf.oillogistics.utils.DateTimeUtil;
import com.zpf.oillogistics.utils.MyShare;
import com.zpf.oillogistics.utils.MyToast;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/19.
 * <p>
 * 个人中心所有订单详情
 */

public class SelfOrderDetailsActivity extends BaseActivity {

    @BindView(R.id.rel_back)
    RelativeLayout relBack;
    @BindView(R.id.tv_orderNo)
    TextView tvOrderNo;
    @BindView(R.id.tv_orderStatus)
    TextView tvOrderStatus;
    @BindView(R.id.tv_orderTime)
    TextView tvOrderTime;
    @BindView(R.id.tv_goodsName)
    TextView tvGoodsName;
    @BindView(R.id.tv_orderCompany)
    TextView tvOrderCompany;
    @BindView(R.id.tv_orderAdress)
    TextView tvOrderAdress;
    @BindView(R.id.tv_orderPhone)
    TextView tvOrderPhone;
    @BindView(R.id.tv_orderNum)
    TextView tvOrderNum;
    @BindView(R.id.tv_orderPrice)
    TextView tvOrderPrice;
    @BindView(R.id.tv_orderMoney)
    TextView tvOrderMoney;
    @BindView(R.id.tv_toName)
    TextView tvToName;
    @BindView(R.id.tv_toPhone)
    TextView tvToPhone;
    @BindView(R.id.tv_toAdress)
    TextView tvToAdress;
    @BindView(R.id.tv_tatail)
    TextView tvTatail;
    @BindView(R.id.tv_config)
    TextView tvConfig;
    @BindView(R.id.tv_pay)
    TextView tvPay;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;

    private String orderId = "";
    private CompanyOrderDetailsResponse.DataBean order;

    @Override
    protected int setLayout() {
        return R.layout.activity_self_order_details;
    }

    @Override
    protected void initData() {
        orderId = getIntent().getExtras().getString("orderId");
        search();
    }

    @Override
    protected void initViewAction(Bundle savedInstanceState) {
        relBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cancleOrder(orderId);
            }
        });
        tvPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DiyDialog.hintTweBtnDialog(SelfOrderDetailsActivity.this, "确定支付订单吗?", new DiyDialog.HintTweBtnListener() {
                    @Override
                    public void confirm() {
                        Intent intent = new Intent(SelfOrderDetailsActivity.this, OrderPayActivity.class);
                        intent.putExtra("money", order.getMoney());
                        intent.putExtra("orderNo", order.getNo());
                        startActivityForResult(intent, 0);
                    }
                });
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    /**
     * 搜索接口
     */
    private void search() {
        HashMap<String, String> hp = new HashMap<>();
        hp.put("id", orderId);

        SimplifyThread simplifyThread = new SimplifyThread(UrlUtil.URL_ORDER_ORDERINFOR, hp);
        simplifyThread.setOnKeyResultListener(new SimplifyThread.OnResultListener() {
            @Override
            public void resultBody(String res) {
                Message message = new Message();
                message.obj = res;
                message.what = MessageWhat.SELF_COMPANY_ORDER_INFOR;
                handler.sendMessage(message);
            }

            @Override
            public void errorBody(String error) {
                handler.sendEmptyMessage(MessageWhat.REQUST_ERROR);
            }
        });
    }

    /**
     * 取消订单接口
     */
    private void cancleOrder(String id) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id", id);

        SimplifyThread simplifyThread = new SimplifyThread(UrlUtil.URL_ORDER_CANCEL, hashMap);
        simplifyThread.setOnKeyResultListener(new SimplifyThread.OnResultListener() {
            @Override
            public void resultBody(String res) {
                Message message = new Message();
                message.obj = res;
                message.what = MessageWhat.SELF_COMPANY_ORDER_CANCEL;
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
                case MessageWhat.SELF_COMPANY_ORDER_INFOR:
                    if (msg.obj != null) {
                        try {
                            CompanyOrderDetailsResponse orderRe = gson.fromJson(msg.obj.toString(), CompanyOrderDetailsResponse.class);

                            if (orderRe.getStatus() == 0) {
                                order = orderRe.getData();
                                //订单号
                                if (order.no != null) {
                                    tvOrderNo.setText(order.no);
                                }
                                //状态
                                if (order.status == 0 || order.status == 1) {
                                    tvOrderStatus.setText("未付款");
                                    tvPay.setVisibility(View.VISIBLE);
                                    tvCancel.setVisibility(View.VISIBLE);
                                } else if (order.status == 2) {
                                    tvOrderStatus.setText("已付款");
                                } else if (order.status == 3) {
                                    tvOrderStatus.setText("未提货");
                                } else if (order.status == 5) {
                                    tvOrderStatus.setText("已完成");
                                } else if (order.status == 6) {
                                    tvOrderStatus.setText("已取消");
                                } else if (order.status == 4) {
                                    tvOrderStatus.setText("已提货");
                                    tvCancel.setVisibility(View.VISIBLE);
                                } else if (order.status == 7) {
                                    tvOrderStatus.setText("已送达");
                                }
                                //订单时间
                                if (order.time != 0) {
                                    tvOrderTime.setText(DateTimeUtil.stampToDate("yyyy/MM/dd HH:mm:ss", order.time + "000"));
                                }
                                //货物名称
                                if (order.goodsname != null) {
                                    tvGoodsName.setText(order.goodsname);
                                }
                                //卖家公司名称
                                if (order.company != null) {
                                    tvOrderCompany.setText(order.company);
                                }
                                //卖家地址
                                if (order.address != null) {
                                    tvOrderAdress.setText(order.address);
                                }
                                //卖家电话
                                if (order.phone != null) {
                                    tvOrderPhone.setText(order.phone);
                                }
                                //单价
                                if (order.price != null) {
                                    tvOrderPrice.setText(order.price + "元/吨");
                                }
                                //货物数量
                                if (order.number != 0) {
                                    tvOrderNum.setText(order.number + "吨");
                                }
                                //成交总价
                                if (order.money != null) {
                                    tvOrderMoney.setText((order.number * Float.parseFloat(order.price)) + "元");
                                }
                                //买家姓名
                                if (order.getTocompany() != null && !order.tocompany.equals("")) {
                                    tvToName.setText(order.tocompany);
                                }
                                if (order.toname != null && !order.toname.equals("")) {
                                    tvToName.setText(order.toname);
                                }
                                //买家电话
                                if (order.tophone != null) {
                                    tvToPhone.setText(order.tophone);
                                }
                                //合计总价
                                if (order.totalmoney != 0) {
                                    tvTatail.setText(order.money + "元");
                                }
                                //买家地址
                                if (order.toplace != null) {
                                    tvToAdress.setText(order.toplace + order.toaddress);
                                }

                            } else {
                                MyToast.show(SelfOrderDetailsActivity.this, "暂无数据!");
                            }

                        } catch (Exception e) {
                            MyToast.show(SelfOrderDetailsActivity.this, "返回数据异常!");
                        }

                    } else {
                        MyToast.show(SelfOrderDetailsActivity.this, "返回数据失败!");
                    }
                    break;
                case MessageWhat.SELF_COMPANY_ORDER_CANCEL:
                    if (msg.obj != null) {
                        try {
                            CompanyOrderResponse order = gson.fromJson(msg.obj.toString(), CompanyOrderResponse.class);

                            if (order.getStatus() == 0) {
                                MyToast.show(SelfOrderDetailsActivity.this, "订单取消成功!");
                                finish();
                            } else {
                                MyToast.show(SelfOrderDetailsActivity.this, "暂无数据!");
                            }

                        } catch (Exception e) {
                            MyToast.show(SelfOrderDetailsActivity.this, "返回数据异常!");
                        }

                    } else {
                        MyToast.show(SelfOrderDetailsActivity.this, "返回数据失败!");
                    }
                    break;
            }
        }
    };
}
