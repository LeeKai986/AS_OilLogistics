package com.zpf.oillogistics.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lljjcoder.city_20170724.CityPickerView;
import com.lljjcoder.city_20170724.bean.CityBean;
import com.lljjcoder.city_20170724.bean.DistrictBean;
import com.lljjcoder.city_20170724.bean.ProvinceBean;
import com.zpf.oillogistics.R;
import com.zpf.oillogistics.base.BaseActivity;
import com.zpf.oillogistics.base.CyApplication;
import com.zpf.oillogistics.base.MessageWhat;
import com.zpf.oillogistics.bean.response.CreateSuccessResponse;
import com.zpf.oillogistics.bean.response.IndexResponse;
import com.zpf.oillogistics.bean.response.OrderConfirmResponse;
import com.zpf.oillogistics.diy.DiyToast;
import com.zpf.oillogistics.net.SimplifyThread;
import com.zpf.oillogistics.net.UrlUtil;
import com.zpf.oillogistics.utils.MyShare;
import com.zpf.oillogistics.utils.MyToast;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/15.
 * <p>
 * 订单确认
 */

public class OrderConfirmActivity extends BaseActivity {

    // 布局相关
    // 生成订单
    @BindView(R.id.platform_issue_order_create_tv)
    TextView createTv;
    //返回
    @BindView(R.id.rel_back)
    RelativeLayout relBack;
    //货物名称
    @BindView(R.id.tv_goodsName)
    TextView tvGoodsName;
    //公司名称
    @BindView(R.id.tv_company)
    TextView tvCompany;
    //卖家地址
    @BindView(R.id.tv_adress)
    TextView tvAdress;
    //卖家电话号码
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    //价格
    @BindView(R.id.tv_goodsPrice)
    TextView tvGoodsPrice;
    //数量
    @BindView(R.id.tv_goodsNum)
    TextView tvGoodsNum;
    //    //买家公司
//    @BindView(R.id.tv_toCompany)
//    TextView tvToCompany;
    //买家联系人
    @BindView(R.id.tv_toName)
    TextView tvToName;
    //买家联系电话
    @BindView(R.id.tv_toPhone)
    TextView tvToPhone;
    //买家地址
    @BindView(R.id.tv_toArea)
    TextView tvToArea;
    @BindView(R.id.issue_incar_settlement_area_rl)
    RelativeLayout areaRl;
    @BindView(R.id.rel_toAdress)
    RelativeLayout relAdress;
    @BindView(R.id.edit_toAdress)
    EditText editToAdress;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_orderNo)
    TextView tvOrderNo;
    @BindView(R.id.tv_serchOrder)
    TextView tvSerchOrder;
    @BindView(R.id.lin_orderNo)
    LinearLayout linOrderNo;
    @BindView(R.id.tv_adress1)
    TextView tvAdress1;
    @BindView(R.id.lin_toadress1)
    LinearLayout linToadress1;
    @BindView(R.id.platform_issue_order_create_rl)
    LinearLayout platformIssueOrderCreateRl;
    @BindView(R.id.tv_starMoney)
    TextView tvStarMoney;
    @BindView(R.id.order_create_success_pay_tv)
    TextView payTv;
    @BindView(R.id.platform_issue_order_footer_rl)
    RelativeLayout platformIssueOrderFooterRl;
    @BindView(R.id.lin_goods)
    LinearLayout linGoods;
    @BindView(R.id.line)
    View line;
    @BindView(R.id.rel_location)
    RelativeLayout relLocation;

    OrderConfirmResponse.DataBean.GoodsBean goodsBean;
    OrderConfirmResponse.DataBean.UserBean userBean;
    String adress = "";
    String adressArea = "";
    String longitude = "";
    String latitude = "";

    @Override
    protected int setLayout() {
        return R.layout.activity_order_confirm;
    }

    @Override
    protected void initData() {

        //加载详情地址
        if (!MyShare.getShared().getString("toaddress", "").equals("")) {
            CyApplication.adress = MyShare.getShared().getString("toaddress", "");
            adress = MyShare.getShared().getString("toaddress", "");
        }

        //加载省
        if (!MyShare.getShared().getString("province", "").equals("")) {
            tvToArea.setText(MyShare.getShared().getString("province", ""));
            adressArea = MyShare.getShared().getString("province", "");
            CyApplication.province = MyShare.getShared().getString("province", "");

            if (adress.contains(MyShare.getShared().getString("province", "")))
                adress.replace(MyShare.getShared().getString("province", ""), "");
        }

        //加载市
        if (!MyShare.getShared().getString("city", "").equals("")) {
            tvToArea.setText(MyShare.getShared().getString("province", "") +
                    MyShare.getShared().getString("city", ""));
            adressArea += ("-" + MyShare.getShared().getString("city", ""));

            if (adress.contains(MyShare.getShared().getString("city", "")))
                adress.replace(MyShare.getShared().getString("city", ""), "");
        }

        CyApplication.area = adressArea;
        editToAdress.setText(adress);


        if (!MyShare.getShared().getString("longitude", "").equals(""))
            longitude = MyShare.getShared().getString("longitude", "");

        if (!MyShare.getShared().getString("latitude", "").equals(""))
            latitude = MyShare.getShared().getString("latitude", "");
        buyDetails();
    }

    @Override
    protected void initViewAction(Bundle savedInstanceState) {
        relBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        relLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = "";
                String adress = "";
                if (tvToArea.getText().toString().equals("")) {
                    MyToast.show(OrderConfirmActivity.this, "请选择区域地址");
                    return;
                } else {
                    city = tvToArea.getText().toString();
                }

                adress = editToAdress.getText().toString();

                Intent intent = new Intent(OrderConfirmActivity.this, MapActivity.class);
                intent.putExtra("city", city);
                intent.putExtra("adress", adress);
                intent.putExtra("nav", false);//是否导航
                startActivity(intent);
            }
        });


        createTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvGoodsNum.getText().toString().equals("")) {
                    MyToast.show(OrderConfirmActivity.this, "请选采购数量");
                    return;
                } else if (tvToArea.getText().toString().equals("")) {
                    MyToast.show(OrderConfirmActivity.this, "请选择送货地区");
                    return;
                } else if (editToAdress.getText().toString().equals("")) {
                    MyToast.show(OrderConfirmActivity.this, "请输入送货详细地址");
                    return;
                } else {
                    generateOrder();
                }
            }
        });

        tvSerchOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OrderConfirmActivity.this, SelfCompanyOrderActivity.class));
            }
        });

        payTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderConfirmActivity.this, OrderPayActivity.class);
                intent.putExtra("money", goodsBean.getMoney());
                intent.putExtra("orderNo", tvOrderNo.getText().toString());
                startActivityForResult(intent, 0);
            }
        });
        areaRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CityPickerView cityPicker = new CityPickerView.Builder(OrderConfirmActivity.this)
                        .textSize(20)
                        .title("  ")
                        .backgroundPop(0xa0000000)
                        .titleBackgroundColor("#FFFFFF")
                        .titleTextColor("#000000")
                        .confirTextColor("#FF6571")
                        .cancelTextColor("#C9C9C9")
                        .province("北京市")
                        .city("北京")
                        .district("东城区")
                        .textColor(Color.parseColor("#000000"))
                        .provinceCyclic(true)
                        .cityCyclic(false)
                        .districtCyclic(false)
                        .visibleItemsCount(5)
                        .itemPadding(10)
                        .onlyShowProvinceAndCity(false)
                        .build();
                cityPicker.show();

                //监听方法，获取选择结果
                cityPicker.setOnCityItemClickListener(new CityPickerView.OnCityItemClickListener() {
                    @Override
                    public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                        //返回结果
                        //ProvinceBean 省份信息
                        //CityBean     城市信息
                        //DistrictBean 区县信息
                        adressArea = province.getName() + "-" + city.getName() + "-" + district.getName();
                        tvToArea.setText(province.getName() + city.getName() + district.getName());
                    }

                    @Override
                    public void onCancel() {

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

    @Override
    protected void onResume() {
        super.onResume();
        tvToArea.setText(CyApplication.area.replace("-", ""));
        editToAdress.setText(CyApplication.adress);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null && data.getExtras() != null &&
                data.getExtras().getString("flag") != null
                && data.getExtras().getString("flag").equals("0")) {
            finish();
        }
    }

    /**
     * 订购详情
     */
    private void buyDetails() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("number", getIntent().getExtras().getString("number"));
        hashMap.put("id", getIntent().getStringExtra("id"));
        hashMap.put("uid", MyShare.getShared().getString("userId", ""));

        SimplifyThread simplifyThread = new SimplifyThread(UrlUtil.URL_ORDER_ORDER, hashMap);
        simplifyThread.setOnKeyResultListener(new SimplifyThread.OnResultListener() {
            @Override
            public void resultBody(String res) {
                Message message = new Message();
                message.obj = res;
                message.what = MessageWhat.SELF_COMPANY_ORDER_ORDER;
                handler.sendMessage(message);
            }

            @Override
            public void errorBody(String error) {
                handler.sendEmptyMessage(MessageWhat.REQUST_ERROR);
            }
        });
    }

    /**
     * 生成订单
     */
    private void generateOrder() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("number", tvGoodsNum.getText().toString().replace("吨", ""));
        hashMap.put("id", getIntent().getStringExtra("id"));
        hashMap.put("uid", MyShare.getShared().getString("userId", ""));
        hashMap.put("toaddress", editToAdress.getText().toString());
        hashMap.put("toplace", adressArea);
        if (longitude.equals(""))
            longitude = CyApplication.lon;
        if (latitude.equals(""))
            latitude = CyApplication.lat;
        hashMap.put("longitude", longitude);
        hashMap.put("latitude", latitude);

        SimplifyThread simplifyThread = new SimplifyThread(UrlUtil.URL_ORDER_GENERATE, hashMap);
        simplifyThread.setOnKeyResultListener(new SimplifyThread.OnResultListener() {
            @Override
            public void resultBody(String res) {
                Message message = new Message();
                message.obj = res;
                message.what = MessageWhat.SELF_COMPANY_ORDER_GENERATE;
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
                case MessageWhat.SELF_COMPANY_ORDER_ORDER:
                    if (msg.obj != null) {
                        try {
                            OrderConfirmResponse oil = gson.fromJson(msg.obj.toString(), OrderConfirmResponse.class);

                            if (oil.getStatus() == 0) {
                                goodsBean = oil.getData().getGoods();
                                userBean = oil.getData().getUser();
                                //产品名称
                                if (goodsBean.getTitle() != null) {
                                    tvGoodsName.setText(goodsBean.getTitle());
                                }
                                //卖家公司名称
                                if (goodsBean.getCompanyname() != null) {
                                    tvCompany.setText(goodsBean.getCompanyname());
                                }

                                // 电话
                                if (goodsBean.getPhone() != null) {
                                    tvPhone.setText(goodsBean.getPhone());
                                }
                                //提货地址
                                if (goodsBean.getProvince() != null) {

                                    String adress = "";

                                    if (goodsBean.getProvince() != null
                                            && !goodsBean.getAddress().contains(goodsBean.getProvince()))
                                        adress += goodsBean.getProvince();

                                    if (goodsBean.getCity() != null
                                            && !goodsBean.getAddress().contains(goodsBean.getCity())) {
                                        adress += goodsBean.getCity();
                                        if (goodsBean.getArea() != null)
                                            adress += goodsBean.getArea();
                                    }
                                    if (goodsBean.getAddress() != null)
                                        adress += goodsBean.getAddress();
                                    tvAdress.setText(adress);
                                }
                                //联系电话
//                                if(goodsBean.get()!=null){
//                                    tvGoodsName.setText(goodsBean.getTitle());
//                                }
                                //货物单价
                                if (goodsBean.getPrice() != null) {
                                    tvGoodsPrice.setText(goodsBean.getPrice() + "元/吨");
                                }
                                //货物数量
                                if (goodsBean.getNumber() != null) {
                                    tvGoodsNum.setText(goodsBean.getNumber() + "吨");
                                }
                                if (userBean.getRelname() != null && !userBean.getRelname().equals("")) {
                                    //买家联系人
                                    if (userBean.getRelname() != null) {
                                        tvToName.setText(userBean.getRelname());
                                    }
                                } else {
                                    //买家公司
                                    if (userBean.getCompanyname() != null) {
                                        tvToName.setText(userBean.getCompanyname());
                                    }
                                }
                                //买家联系电话
                                if (userBean.getPhone() != null) {
                                    tvToPhone.setText(userBean.getPhone());
                                }

                            } else {
                                MyToast.show(OrderConfirmActivity.this, "暂无数据!");
                            }

                        } catch (Exception e) {
                            MyToast.show(OrderConfirmActivity.this, "返回数据异常!");
                        }

                    } else {
                        MyToast.show(OrderConfirmActivity.this, "返回数据失败!");
                    }
                    break;
                case MessageWhat.SELF_COMPANY_ORDER_GENERATE:
                    if (msg.obj != null) {
                        try {
                            CreateSuccessResponse index = gson.fromJson(msg.obj.toString(), CreateSuccessResponse.class);

                            if (index.getStatus() == 0) {
                                tvTitle.setText("订单生成成功");
                                linGoods.setVisibility(View.GONE);
                                linOrderNo.setVisibility(View.VISIBLE);
                                linToadress1.setVisibility(View.VISIBLE);
                                line.setVisibility(View.GONE);
                                areaRl.setVisibility(View.GONE);
                                relAdress.setVisibility(View.GONE);
                                platformIssueOrderCreateRl.setVisibility(View.GONE);
                                platformIssueOrderFooterRl.setVisibility(View.VISIBLE);

                                if (index.getData().getNo() != null) {
                                    tvOrderNo.setText(index.getData().getNo());
                                }

                                if (index.getData().getToplace() != null) {
                                    String adress = index.getData().getToplace();
                                    if (index.getData().getToaddress() != null)
                                        adress += index.getData().getToaddress();
                                    tvAdress1.setText(adress);
                                }

                                if (index.getData().getMoney() != null) {
                                    if (Float.parseFloat(index.getData().getMoney()) > 10000) {
                                        tvStarMoney.setText((Float.parseFloat(index.getData().getMoney()) / 10000f) + "万元");
                                    } else {
                                        tvStarMoney.setText(index.getData().getMoney() + "元");
                                    }
                                }

                                DiyToast.centerToast(OrderConfirmActivity.this, "订购成功");
                            } else {
                                MyToast.show(OrderConfirmActivity.this, "订购失败!");
                            }

                        } catch (Exception e) {
                            MyToast.show(OrderConfirmActivity.this, "返回数据异常!");
                        }
                    } else {
                        MyToast.show(OrderConfirmActivity.this, "返回数据失败!");
                    }
                    break;
            }
        }
    };
}
