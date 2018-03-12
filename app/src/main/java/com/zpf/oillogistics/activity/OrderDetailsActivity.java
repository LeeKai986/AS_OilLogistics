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
import com.zpf.oillogistics.bean.response.GetOrderResponse;
import com.zpf.oillogistics.diy.DiyDialog;
import com.zpf.oillogistics.net.SimplifyThread;
import com.zpf.oillogistics.net.UrlUtil;
import com.zpf.oillogistics.utils.CallPermission;
import com.zpf.oillogistics.utils.DateTimeUtil;
import com.zpf.oillogistics.utils.MyShare;
import com.zpf.oillogistics.utils.MyToast;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/18.
 * <p>
 * 订单详情
 */

public class OrderDetailsActivity extends BaseActivity {

    //返回
    @BindView(R.id.rel_back_order_details)
    RelativeLayout relBack;
    @BindView(R.id.order_details_all_title_rl)
    RelativeLayout orderDetails;
    //起点省份
    @BindView(R.id.tv_starprovince_order_details)
    TextView tvStarprovince;
    //起点详细地址
    @BindView(R.id.tv_starcity_order_details)
    TextView tvStarcity;
    //终点省份
    @BindView(R.id.tv_endprovince_order_details)
    TextView tvEndprovince;
    //终点详细地址
    @BindView(R.id.tv_endcity_order_details)
    TextView tvEndcity;
    //货物名称
    @BindView(R.id.tv_goodsname_order_details)
    TextView tvGoodsname;
    //货物数量
    @BindView(R.id.tv_goodsnum_order_details)
    TextView tvGoodsnum;
    //货物价格
    @BindView(R.id.tv_goodsprice_order_details)
    TextView tvGoodsprice;
    //结算方式
    @BindView(R.id.tv_payway_order_details)
    TextView tvPayway;
    //起运日期
    @BindView(R.id.tv_time_order_details)
    TextView tvTime;
    //备注
    @BindView(R.id.tv_marck_order_details)
    TextView tvMarck;
    //订单公司
    @BindView(R.id.tv_company_order_details)
    TextView tvCompany;
    //订单认证
    @BindView(R.id.tv_attestation_order_details)
    TextView tvAttestation;
    //订单地址
    @BindView(R.id.tv_adress_order_details)
    TextView tvAdress;
    //订单人
    @BindView(R.id.tv_name_order_details)
    TextView tvName;
    //订单电话
    @BindView(R.id.tv_phone_order_details)
    TextView tvPhone;
    //确定
    @BindView(R.id.tv_config_order_details)
    TextView tvConfig;
    @BindView(R.id.rel_navi_order_details)
    RelativeLayout relNavi;

    HashMap<String, String> hp = new HashMap<>();
    HashMap<String, String> addhp = new HashMap<>();

    String orderID = "";
    GetOrderResponse.DataBean order;


    @Override
    protected int setLayout() {
        return R.layout.activity_order_details;
    }

    @Override
    protected void initData() {
        order();
    }

    @Override
    protected void initViewAction(Bundle savedInstanceState) {
        relBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        relNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderDetailsActivity.this, MapActivity.class);
                intent.putExtra("nav", true);
                intent.putExtra("sadress", order.getStartaddress());
                intent.putExtra("eadress", order.getEndaddress());
                if (order.getLongitude() != null && order.getLatitude() != null) {
                    intent.putExtra("slongitude", order.getS_longitude());
                    intent.putExtra("slatitude", order.getS_latitude());
                    intent.putExtra("elongitude", order.getLongitude());
                    intent.putExtra("elatitude", order.getLatitude());
                    startActivity(intent);
                } else {
                    intent.putExtra("scity", order.getStartplace().replace("-", ""));
                    intent.putExtra("ecity", order.getEndplace().replace("-", ""));
                    startActivity(intent);
                }
            }
        });

        tvConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MyShare.getShared().getString("userType", "").equals("1")) {
                    MyToast.show(OrderDetailsActivity.this, "您当前账号为个人账号, 不可进入车找货功能");
                    return;
                }
                if (MyShare.getShared().getString("userType", "").equals("2")) {
                    MyToast.show(OrderDetailsActivity.this, "您当前账号为公司账号, 不可进入车找货功能");
                    return;
                }
                if (order != null) {
                    if (order.getIdentity() == 3) {
                        DiyDialog.hintTweBtnDialog(OrderDetailsActivity.this, "确定要下单吗?", new DiyDialog.HintTweBtnListener() {
                            @Override
                            public void confirm() {
                                getOrder();
                            }
                        });

                    } else {

                        //用intent启动拨打电话
                        if (order.getPhone() != null) {
//                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + order.getPhone()));
//                            if (ActivityCompat.checkSelfPermission(OrderDetailsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                                return;
//                            }
//                            startActivity(intent);
                            CallPermission.callPhone(OrderDetailsActivity.this, order.getPhone());
                        } else {
                            MyToast.show(OrderDetailsActivity.this, "暂无联系电话");
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }


    /**
     * 获取订单数据接口
     */
    private void order() {
        orderID = getIntent().getExtras().getString("orderID");
        hp.put("id", orderID);

        SimplifyThread simplifyThread = new SimplifyThread(UrlUtil.URL_FREIGHT_ORDER, hp);
        simplifyThread.setGetKeyResultListener(new SimplifyThread.OnResultListener() {
            @Override
            public void resultBody(String res) {
                Message message = new Message();
                message.obj = res;
                message.what = MessageWhat.FREIGHT_ORDER;
                handler.sendMessage(message);
            }

            @Override
            public void errorBody(String error) {
                handler.sendEmptyMessage(MessageWhat.REQUST_ERROR);
            }
        });
    }

    /**
     * 获取订单数据接口
     */
    private void getOrder() {
        addhp.put("goodsid", order.getId() + "");
        addhp.put("uid", MyShare.getShared().getString("userId", ""));

        SimplifyThread simplifyThread = new SimplifyThread(UrlUtil.URL_ORDER_ADDCAR, addhp);
        simplifyThread.setOnKeyResultListener(new SimplifyThread.OnResultListener() {
            @Override
            public void resultBody(String res) {
                Message message = new Message();
                message.obj = res;
                message.what = MessageWhat.ORDER_ADDCAR;
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
                case MessageWhat.FREIGHT_ORDER:
                    if (msg.obj != null) {
                        try {
                            GetOrderResponse orderRe = gson.fromJson(msg.obj.toString(), GetOrderResponse.class);
                            if (orderRe.getStatus() == 0) {
                                order = orderRe.getData();

                                //起点省份
                                if (order.getStartplace() != null) {
                                    tvStarprovince.setText(order.getStartplace().replace("-", ""));
                                }
                                //起点详细地址
                                if (order.getStartaddress() != null) {
                                    String adress = order.getStartaddress();
                                    if (adress.contains(order.getStartplace()))
                                        adress = adress.replace(order.getStartplace(), "");
                                    tvStarcity.setText(adress);
                                }
                                //终点省份
                                if (order.getEndplace() != null) {
                                    tvEndprovince.setText(order.getEndplace().replace("-", ""));
                                }
                                //终点详细地址
                                if (order.getEndaddress() != null) {
                                    String adress = order.getEndaddress();
                                    if (adress.contains(order.getEndplace()))
                                        adress = adress.replace(order.getEndplace(), "");
                                    tvEndcity.setText(adress);
                                }
                                //货物名字
                                if (order.getGoodsname() != null) {
                                    tvGoodsname.setText(order.getGoodsname());
                                }
                                //货物数量
                                if (order.getNumber() != 0) {
                                    tvGoodsnum.setText(order.getNumber() + "吨");
                                }
                                //货物价格
                                if (order.getPrice() != null) {
                                    tvGoodsprice.setText(order.getPrice() + "元/吨");
                                }

                                //结算方式
                                if (order.getMethod() == 1) {
                                    tvPayway.setText("单结");
                                } else if (order.getMethod() == 2) {
                                    tvPayway.setText("周结");
                                } else if (order.getMethod() == 3) {
                                    tvPayway.setText("月结");
                                }
                                //下单时间
                                if (order.getTime() != null) {
                                    tvTime.setText(DateTimeUtil.stampToDate("yyyy/MM/dd", order.getTime() + "000"));
                                }
                                //备注
                                if (order.getComment() != null) {
                                    tvMarck.setText(order.getComment());
                                }
                                //订单公司
                                if (order.getCompany() != null && !order.getCompany().equals("")) {
                                    tvCompany.setText(order.getCompany());
                                } else {
                                    tvCompany.setText(order.getName());
                                }
                                //备注
                                if (order.getComment() != null) {
                                    tvMarck.setText(order.getComment());
                                }

                                if (order.getStartaddress() != null) {
                                    String ss = order.getStartaddress();
                                    if (order.getStartplace() != null && !ss.contains(order.getStartplace())) {
                                        ss = order.getStartplace() + order.getStartaddress();
                                    }

                                    tvAdress.setText(ss.replace("-", ""));
                                }


                                //订单人
                                if (order.getName() != null) {
                                    tvName.setText(order.getName());
                                }
                                //订单电话
                                if (order.getPhone() != null) {
                                    tvPhone.setText(order.getPhone());
                                }

                                if (order.getIdentity() == 3) {
                                    tvConfig.setText("去下单");
                                } else {
                                    tvConfig.setText("拨打电话");
                                }


                            } else {
                                MyToast.show(OrderDetailsActivity.this, "暂无数据!");
                            }

                        } catch (Exception e) {
                            MyToast.show(OrderDetailsActivity.this, "返回数据异常!");
                        }

                    } else {
                        MyToast.show(OrderDetailsActivity.this, "返回数据失败!");
                    }
                    break;
                case MessageWhat.ORDER_ADDCAR:
                    if (msg.obj != null) {
                        try {
                            GetOrderResponse orderRe = gson.fromJson(msg.obj.toString(), GetOrderResponse.class);
                            if (orderRe.getStatus() == 0) {

                                MyToast.show(OrderDetailsActivity.this, "下单成功!");
                                finish();

                            } else if (orderRe != null) {
                                MyToast.show(OrderDetailsActivity.this, orderRe.getMsg());
                            }
                        } catch (Exception e) {
                            MyToast.show(OrderDetailsActivity.this, "返回数据异常!");
                        }
                    } else {
                        MyToast.show(OrderDetailsActivity.this, "返回数据失败!");
                    }
                    break;
            }
        }
    };
}
