package com.zpf.oillogistics.activity;

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
import com.zpf.oillogistics.bean.response.CompanyOrderDetailsResponse;
import com.zpf.oillogistics.bean.response.CompanyOrderResponse;
import com.zpf.oillogistics.net.SimplifyThread;
import com.zpf.oillogistics.net.UrlUtil;
import com.zpf.oillogistics.utils.DateTimeUtil;
import com.zpf.oillogistics.utils.MyToast;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/19.
 * <p>
 * 个人中心所有订单详情
 */

public class SelfDriverOrderDetailsActivity extends BaseActivity {

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
    @BindView(R.id.tv_config)
    TextView tvConfig;
    @BindView(R.id.tv_arrive)
    TextView tvArrive;

    private String orderId = "";
    private CompanyOrderDetailsResponse.DataBean order;

    @Override
    protected int setLayout() {
        return R.layout.activity_self_driver_order_details;
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
                takeOrder(orderId);
            }
        });
        tvArrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                arriveOrder(orderId);
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
     * 提货接口
     */
    private void takeOrder(String id) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id", id);

        SimplifyThread simplifyThread = new SimplifyThread(UrlUtil.URL_DRIVER_TAKE, hashMap);
        simplifyThread.setOnKeyResultListener(new SimplifyThread.OnResultListener() {
            @Override
            public void resultBody(String res) {
                Message message = new Message();
                message.obj = res;
                message.what = MessageWhat.SELF_DRIVER_ORDER_TAKE;
                handler.sendMessage(message);
            }

            @Override
            public void errorBody(String error) {
                handler.sendEmptyMessage(MessageWhat.REQUST_ERROR);
            }
        });
    }

    /**
     * 到达接口
     */
    private void arriveOrder(String id) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id", id);

        SimplifyThread simplifyThread = new SimplifyThread(UrlUtil.URL_DRIVER_ARRIVE, hashMap);
        simplifyThread.setOnKeyResultListener(new SimplifyThread.OnResultListener() {
            @Override
            public void resultBody(String res) {
                Message message = new Message();
                message.obj = res;
                message.what = MessageWhat.SELF_DRIVER_ORDER_ARRIVE;
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
                                if (order.status == 3) {
                                    tvOrderStatus.setText("未提货");
                                    tvConfig.setVisibility(View.VISIBLE);
                                } else if (order.status == 5) {
                                    tvOrderStatus.setText("完成");
                                } else if (order.status == 4) {
                                    tvOrderStatus.setText("已提货");
                                    tvArrive.setVisibility(View.VISIBLE);
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
                                    tvOrderMoney.setText(order.money + "元");
                                }
                                //买家姓名
                                if (order.toname != null && !order.toname.equals("")) {
                                    tvToName.setText(order.toname);
                                } else {
                                    tvToName.setText(order.tocompany);
                                }
                                //买家电话
                                if (order.tophone != null) {
                                    tvToPhone.setText(order.tophone);
                                }
                                //买家地址
                                if (order.toplace != null) {
                                    tvToAdress.setText(order.toplace + order.toaddress.replace(order.toplace, ""));
                                }

                            } else {
                                MyToast.show(SelfDriverOrderDetailsActivity.this, "暂无数据!");
                            }

                        } catch (Exception e) {
                            MyToast.show(SelfDriverOrderDetailsActivity.this, "返回数据异常!");
                        }

                    } else {
                        MyToast.show(SelfDriverOrderDetailsActivity.this, "返回数据失败!");
                    }
                    break;
                case MessageWhat.SELF_DRIVER_ORDER_ARRIVE:
                    if (msg.obj != null) {
                        try {
                            CompanyOrderResponse order = gson.fromJson(msg.obj.toString(), CompanyOrderResponse.class);

                            if (order.getStatus() == 0) {
                                MyToast.show(SelfDriverOrderDetailsActivity.this, "货物已到达!");
                                finish();
                            } else {
                                MyToast.show(SelfDriverOrderDetailsActivity.this, "暂无数据!");
                            }

                        } catch (Exception e) {
                            MyToast.show(SelfDriverOrderDetailsActivity.this, "返回数据异常!");
                        }

                    } else {
                        MyToast.show(SelfDriverOrderDetailsActivity.this, "返回数据失败!");
                    }
                    break;
                case MessageWhat.SELF_DRIVER_ORDER_TAKE:
                    if (msg.obj != null) {
                        try {
                            CompanyOrderResponse order = gson.fromJson(msg.obj.toString(), CompanyOrderResponse.class);

                            if (order.getStatus() == 0) {
                                MyToast.show(SelfDriverOrderDetailsActivity.this, "货物已提出!");
                                finish();
                            } else {
                                MyToast.show(SelfDriverOrderDetailsActivity.this, "暂无数据!");
                            }

                        } catch (Exception e) {
                            MyToast.show(SelfDriverOrderDetailsActivity.this, "返回数据异常!");
                        }

                    } else {
                        MyToast.show(SelfDriverOrderDetailsActivity.this, "返回数据失败!");
                    }
                    break;
            }
        }
    };
}
