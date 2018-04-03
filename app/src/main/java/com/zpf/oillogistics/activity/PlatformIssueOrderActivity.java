package com.zpf.oillogistics.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zpf.oillogistics.R;
import com.zpf.oillogistics.base.MessageWhat;
import com.zpf.oillogistics.bean.OilOrChemtryInfoBean;
import com.zpf.oillogistics.bean.ShipTimeBean;
import com.zpf.oillogistics.bean.response.IndexResponse;
import com.zpf.oillogistics.bean.response.ObjectResponse;
import com.zpf.oillogistics.diy.DiyDialog;
import com.zpf.oillogistics.fragment.PlatformIssueOrderImageFragment;
import com.zpf.oillogistics.fragment.PlatformIssueOrderPartFragment;
import com.zpf.oillogistics.net.JsonUtil;
import com.zpf.oillogistics.net.SimplifyThread;
import com.zpf.oillogistics.net.UrlUtil;
import com.zpf.oillogistics.utils.CallPermission;
import com.zpf.oillogistics.utils.DateTimeUtil;
import com.zpf.oillogistics.utils.MyShare;
import com.zpf.oillogistics.utils.MyToast;
import com.zpf.oillogistics.utils.NormalUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/15.
 * <p>
 * 平台发布的订单
 */

public class PlatformIssueOrderActivity extends FragmentActivity {

    // 布局相关
    // tablayout
    @BindView(R.id.platform_issue_order_tl)
    TabLayout tl;
    @BindView(R.id.rel_back)
    RelativeLayout relBack;
    // viewpager
    @BindView(R.id.platform_issue_order_vp)
    ViewPager vp;
    // 关注
    @BindView(R.id.platform_issue_order_atten_tv)
    TextView attenTv;
    // 分享
    @BindView(R.id.platform_issue_order_share_tv)
    TextView shareTv;
    // adapter
    PlatformIssueOrderAdapter adapter;
    // 名称
    @BindView(R.id.platform_issue_order_title_tv)
    TextView titleTv;
    // 单价
    @BindView(R.id.platform_issue_order_unit_price_tv)
    TextView unitPriceTv;
    // 发布时间
    @BindView(R.id.platform_issue_order_c_time_tv)
    TextView cTimeTv;
    // 公司名称
    @BindView(R.id.platform_issue_order_company_name_tv)
    TextView companyNameTv;
    // 公司地址
    @BindView(R.id.platform_issue_order_company_address_tv)
    TextView companyAddressTv;
    // 公司电话
    @BindView(R.id.platform_issue_order_company_phone_tv)
    TextView companyPhoneTv;
    // 拨打电话(公司)
    @BindView(R.id.platform_issue_order_call_company_phone_iv)
    ImageView callCompanyPhoneIv;
    // 采购数量(全局)
    @BindView(R.id.platform_issue_order_buy_num_ll)
    LinearLayout buyNumLl;
    // 到达时间
    @BindView(R.id.platform_issue_order_arrive_time_Ll)
    LinearLayout arriveTimeLl;
    // 采购数量
    @BindView(R.id.platform_issue_order_buy_num_tv)
    TextView buyNumTv;
    // 订购
    @BindView(R.id.platform_issue_order_buy_tv)
    TextView buyTv;
    // 联系人
    @BindView(R.id.tv_companyname)
    TextView companyName;
    // 运送距离及消耗时间
    @BindView(R.id.platform_issue_order_ship_one_km_tv)
    TextView shipOneKmTv;
    @BindView(R.id.platform_issue_order_ship_twe_km_tv)
    TextView shipTweKmTv;
    @BindView(R.id.platform_issue_order_ship_three_km_tv)
    TextView shipThreeKmTv;
    @BindView(R.id.platform_issue_order_ship_four_km_tv)
    TextView shipFourKmTv;
    @BindView(R.id.platform_issue_order_ship_five_km_tv)
    TextView shipFiveKmTv;
    @BindView(R.id.platform_issue_order_ship_time_one_km_tv)
    TextView shipTimeOneKmTv;
    @BindView(R.id.platform_issue_order_ship_time_twe_km_tv)
    TextView shipTimeTweKmTv;
    @BindView(R.id.platform_issue_order_ship_time_three_km_tv)
    TextView shipTimeThreeKmTv;
    @BindView(R.id.platform_issue_order_ship_time_four_km_tv)
    TextView shipTimeFourKmTv;
    @BindView(R.id.platform_issue_order_ship_time_five_km_tv)
    TextView shipTimeFiveKmTv;


    PlatformIssueOrderPartFragment platformIssueOrderPartFragment;
    PlatformIssueOrderImageFragment platformIssueOrderImageFragment;

    // 数据相关
    // 平台订单详情
    SimplifyThread platformOrderDetailsThread;
    // 平台订单详情传参
    HashMap<String, String> platfromOrderDetailsMap;
    // 平台订单详情返回
    OilOrChemtryInfoBean oilOrChemtryInfoBean;
    OilOrChemtryInfoBean.DataBeanX.DataBean dataBean;
    OilOrChemtryInfoBean.DataBeanX.InfoBean infoBean;

    // 数据相关
    // 关注的产品信息
    SimplifyThread productThread;
    // 关注的产品信息传参
    HashMap<String, String> productMap;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    MyToast.show(PlatformIssueOrderActivity.this, message.obj.toString());
                    break;
                case 1:
                    if (oilOrChemtryInfoBean != null) {
                        if (oilOrChemtryInfoBean.getData() != null) {
                            setData();
                        } else {
                            MyToast.show(PlatformIssueOrderActivity.this, oilOrChemtryInfoBean.getMsg());
                        }
                    } else {
                        MyToast.show(PlatformIssueOrderActivity.this, "未能获取数据");
                    }
                    break;
                case 2:
                    if (message.obj != null) {
                        try {
                            ObjectResponse product = new Gson().fromJson(message.obj.toString(), ObjectResponse.class);

                            if (product.getStatus() == 0) {
                                plateformOrderDetails();
                                MyToast.show(PlatformIssueOrderActivity.this, "已取消关注!");
                            } else {
                                MyToast.show(PlatformIssueOrderActivity.this, product.getMsg());
                            }

                        } catch (Exception e) {
                            MyToast.show(PlatformIssueOrderActivity.this, "返回数据异常!");
                        }

                    } else {
                        MyToast.show(PlatformIssueOrderActivity.this, "返回数据失败!");
                    }
                    break;
                case 3:
                    ShipTimeBean shipTimeBean = new Gson().fromJson(message.obj.toString(), ShipTimeBean.class);
                    if (shipTimeBean != null && shipTimeBean.getData() != null && shipTimeBean.getData().size() != 0) {
                        for (int i = 0; i < shipTimeBean.getData().size(); i++) {
                            switch (i) {
                                case 0:
                                    shipOneKmTv.setText(shipTimeBean.getData().get(0).getKilometre());
                                    shipTimeOneKmTv.setText(shipTimeBean.getData().get(0).getService_time());
                                    break;
                                case 1:
                                    shipTweKmTv.setText(shipTimeBean.getData().get(1).getKilometre());
                                    shipTimeTweKmTv.setText(shipTimeBean.getData().get(1).getService_time());
                                    break;
                                case 2:
                                    shipThreeKmTv.setText(shipTimeBean.getData().get(2).getKilometre());
                                    shipTimeThreeKmTv.setText(shipTimeBean.getData().get(2).getService_time());
                                    break;
                                case 3:
                                    shipFourKmTv.setText(shipTimeBean.getData().get(3).getKilometre());
                                    shipTimeFourKmTv.setText(shipTimeBean.getData().get(3).getService_time());
                                    break;
                                case 4:
                                    shipFiveKmTv.setText(shipTimeBean.getData().get(4).getKilometre());
                                    shipTimeFiveKmTv.setText(shipTimeBean.getData().get(4).getService_time());
                                    break;
                            }
                        }
                    }
                    break;
                case MessageWhat.FOLLOW_FOLLOW:
                    if (message.obj != null) {
                        try {
                            IndexResponse index = new Gson().fromJson(message.obj.toString(), IndexResponse.class);

                            if (index.getStatus() == 0) {
//                                attenTv.setText("已关注");
                                plateformOrderDetails();
                                MyToast.show(PlatformIssueOrderActivity.this, "关注成功!");
                            } else {
                                MyToast.show(PlatformIssueOrderActivity.this, "暂无数据!");
                            }

                        } catch (Exception e) {
                            MyToast.show(PlatformIssueOrderActivity.this, "返回数据异常!");
                        }

                    } else {
                        MyToast.show(PlatformIssueOrderActivity.this, "返回数据失败!");
                    }
                    break;
            }
            return false;
        }
    });


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_platform_issue_order);
        ButterKnife.bind(this);
        initViewAction(savedInstanceState);
        initData();
    }


    protected void initData() {
        plateformOrderDetails();
    }

    protected void initViewAction(Bundle savedInstanceState) {

        relBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        buyNumLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> buyNums = new ArrayList<>();
                String[] buyNumArr = getResources().getStringArray(R.array.buy_num);
                for (int i = 0; i < buyNumArr.length; i++) {
                    buyNums.add(buyNumArr[i]);
                }
                DiyDialog.singleSelectDialog(PlatformIssueOrderActivity.this, buyNums, new DiyDialog.SingleSelectListener() {
                    @Override
                    public void SingleSelect(String res) {
                        buyNumTv.setText(res);
                    }
                });
            }
        });

//        buyTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (dataBean != null && dataBean.getStatus() == 3 &&
//                        MyShare.getShared().getString("userType", "").equals("2")) {
//                    if (buyNumTv.getText().toString().equals("")) {
//                        MyToast.show(PlatformIssueOrderActivity.this, "请选择采购数量");
//                        return;
//                    } else {
//                        Intent intent = new Intent(PlatformIssueOrderActivity.this, OrderConfirmActivity.class);
//                        intent.putExtra("number", buyNumTv.getText().toString().replace("吨", ""));
//                        intent.putExtra("id", dataBean.getId() + "");
//                        startActivity(intent);
//                    }
//
//                } else {
//                    if (infoBean != null && infoBean.getPhone() != null) {
//                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + infoBean.getPhone()));
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(intent);
//                    }
//                }
//            }
//        });

        platfromOrderDetailsMap = new HashMap<>();
        platfromOrderDetailsMap.put("classid", getIntent().getStringExtra("classid"));
        platfromOrderDetailsMap.put("id", getIntent().getStringExtra("id"));
        platfromOrderDetailsMap.put("uid", MyShare.getShared().getString("userId", ""));
        attenTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MyShare.getShared().getString("userType", "").equals("3")) {
                    MyToast.show(PlatformIssueOrderActivity.this, "您当前是司机账号,不能关注");
                    return;
                }
                String msg = "您确定要关注吗";
                if (dataBean.getFollow() == 1) {
                    return;
                }
                DiyDialog.hintTweBtnDialog(PlatformIssueOrderActivity.this, msg, new DiyDialog.HintTweBtnListener() {
                    @Override
                    public void confirm() {
                        attention();
                    }
                });
            }
        });

        shareTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DiyDialog.shareDialog(PlatformIssueOrderActivity.this, new DiyDialog.ShareListener() {
                    @Override
                    public void friend() {
                        MyToast.show(PlatformIssueOrderActivity.this, "分享成功");
                        startActivity(new Intent(PlatformIssueOrderActivity.this, ShareSuccessActivity.class));
                    }

                    @Override
                    public void circle() {
                        MyToast.show(PlatformIssueOrderActivity.this, "分享成功");
                        startActivity(new Intent(PlatformIssueOrderActivity.this, ShareSuccessActivity.class));
                    }
                });
            }
        });

        ArrayList<Fragment> list = new ArrayList<>();
        platformIssueOrderPartFragment = new PlatformIssueOrderPartFragment();
        if (getIntent().getStringExtra("classid").equals("1")) {
            //   石油类
            platformIssueOrderPartFragment.setClassid("1");
        } else {
            //化工类
            platformIssueOrderPartFragment.setClassid("2");
        }
        platformIssueOrderImageFragment = new PlatformIssueOrderImageFragment();
        list.add(platformIssueOrderPartFragment);
        list.add(platformIssueOrderImageFragment);
        adapter = new PlatformIssueOrderAdapter(getSupportFragmentManager());
        adapter.setList(list);
        vp.setAdapter(adapter);
        tl.setupWithViewPager(vp);
    }

    private void setData() {
        dataBean = oilOrChemtryInfoBean.getData().getData().get(0);
        infoBean = oilOrChemtryInfoBean.getData().getInfo();
        if (dataBean != null) {

            if (dataBean.getTitle() != null && !dataBean.getTitle().equals("")) {
                titleTv.setText(dataBean.getTitle());
            }
            if (dataBean.getPrice() != null && !dataBean.getPrice().equals("")) {
                unitPriceTv.setText("价格：" + dataBean.getPrice() + "元/吨");
            }
            if (dataBean.getTime() != 0) {
                cTimeTv.setText(DateTimeUtil.stampToDate("yyyy,MM/dd HH:mm", dataBean.getTime() + "000"));
            }

            if (dataBean.getCompanyname() != null)
                companyNameTv.setText(dataBean.getCompanyname());
            if (dataBean.getPhone() != null) {
                companyPhoneTv.setText(dataBean.getPhone());
            }

            if (dataBean.getProvince() != null) {

                String adress = "";

                if (dataBean.getProvince() != null && !dataBean.getAddress().contains((CharSequence) dataBean.getProvince()))
                    adress += dataBean.getProvince();
                if (dataBean.getCity() != null && !dataBean.getAddress().contains((CharSequence) dataBean.getCity())) {
                    adress += dataBean.getCity();
                    if (dataBean.getArea() != null)
                        adress += dataBean.getArea();
                }
                if (dataBean.getAddress() != null)
                    adress += dataBean.getAddress();
                companyAddressTv.setText(adress);
            }

            if (dataBean.getStatus() == 3) {
                arriveTimeLl.setVisibility(View.VISIBLE);
                getShipTime();
                buyTv.setText("拨打电话");
                buyNumLl.setVisibility(View.GONE);
                buyTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (infoBean != null && infoBean.getPhone() != null) {
//                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + infoBean.getPhone()));
//                            startActivity(intent);
                            CallPermission.callPhone(PlatformIssueOrderActivity.this, infoBean.getPhone());
                        }
                    }
                });
//                if (MyShare.getShared().getString("userType", "").equals("2")) {
//                    buyTv.setText("订购");
//                    buyTv.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            if (buyNumTv.getText().toString().equals("")) {
//                                MyToast.show(PlatformIssueOrderActivity.this, "请选择采购数量");
//                                return;
//                            } else {
//                                Intent intent = new Intent(PlatformIssueOrderActivity.this, OrderConfirmActivity.class);
//                                intent.putExtra("number", buyNumTv.getText().toString().replace("吨", ""));
//                                intent.putExtra("id", dataBean.getId() + "");
//                                startActivity(intent);
//                            }
//                        }
//                    });
//                } else {
//                    buyNumLl.setVisibility(View.GONE);
//                    buyTv.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            if (MyShare.getShared().getString("userType", "").equals("1")) {
//                                MyToast.show(PlatformIssueOrderActivity.this, "您当前账号类型为个人,不可下单");
//                            } else if (MyShare.getShared().getString("userType", "").equals("3")) {
//                                MyToast.show(PlatformIssueOrderActivity.this, "您当前账号类型为司机,不可下单");
//                            }
//                        }
//                    });
//                }
            } else {
                buyTv.setText("拨打电话");
                buyNumLl.setVisibility(View.GONE);
                arriveTimeLl.setVisibility(View.GONE);
                buyTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (infoBean != null && infoBean.getPhone() != null) {
//                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + infoBean.getPhone()));
//                            startActivity(intent);
                            CallPermission.callPhone(PlatformIssueOrderActivity.this, infoBean.getPhone());
                        }
                    }
                });
            }

            if (dataBean.getFollow() == 1) {
                attenTv.setText("已关注");
            } else {
                attenTv.setText("关注");
            }

            platformIssueOrderPartFragment.setData(dataBean);
            platformIssueOrderImageFragment.setData(dataBean);
        }
        if (infoBean != null) {

            if (dataBean.getStatus() == 1) {
                companyName.setText("联 系 人");
                if (infoBean.getRelname() != null) {
                    companyNameTv.setText(infoBean.getRelname());
                }

            } else if (dataBean.getStatus() == 2) {
                if (infoBean.getCompanyname() != null && !infoBean.getCompanyname().equals("")) {
                    companyNameTv.setText(infoBean.getCompanyname());
                }
            } else {
                companyNameTv.setText("石化宝典订购平台");
            }

            if (infoBean.getPhone() != null && !infoBean.getPhone().equals("")) {
                companyPhoneTv.setText(infoBean.getPhone());
                callCompanyPhoneIv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + infoBean.getPhone()));
//                        startActivity(intent);
                        CallPermission.callPhone(PlatformIssueOrderActivity.this, infoBean.getPhone());
                    }
                });
            }
        }
    }

    private void plateformOrderDetails() {
        platformOrderDetailsThread = new SimplifyThread(UrlUtil.TOGOODS_TOGOODS_INFO, platfromOrderDetailsMap);
        platformOrderDetailsThread.setOnGetResultListener(new SimplifyThread.OnResultListener() {
            @Override
            public void resultBody(String res) {
                oilOrChemtryInfoBean = JsonUtil.oilOrChemtryInfoBeanResolve(res);
                handler.sendEmptyMessage(1);
            }

            @Override
            public void errorBody(String error) {

            }
        });
    }

    /**
     * 关注
     */
    private void attention() {
        HashMap<String, String> hp = new HashMap<>();
        hp.put("uid", MyShare.getShared().getString("userId", ""));
        hp.put("source", oilOrChemtryInfoBean.getData().getData().get(0).getUid() + "");
        hp.put("status", "1");

        SimplifyThread simplifyThread = new SimplifyThread(UrlUtil.URL_FOLLOW_FOLLOW, hp);
        simplifyThread.setOnKeyResultListener(new SimplifyThread.OnResultListener() {
            @Override
            public void resultBody(String res) {
                Message message = new Message();
                message.obj = res;
                message.what = MessageWhat.FOLLOW_FOLLOW;
                handler.sendMessage(message);
            }

            @Override
            public void errorBody(String error) {
                handler.sendEmptyMessage(MessageWhat.REQUST_ERROR);
            }
        });
    }

    /**
     * 取消关注
     */
    private void cancelAtten() {
        HashMap hp = new HashMap<>();
        hp.put("id", NormalUtils.userId());
        hp.put("source", oilOrChemtryInfoBean.getData().getInfo());
        productThread = new SimplifyThread(UrlUtil.USER_FOLLOW_CANCEL, hp);
        productThread.setOnKeyResultListener(new SimplifyThread.OnResultListener() {
            @Override
            public void resultBody(String res) {
                Message message = new Message();
                message.obj = res;
                message.what = 2;
                handler.sendMessage(message);
            }

            @Override
            public void errorBody(String error) {
                handler.sendEmptyMessage(0);
            }
        });
    }

    /**
     * 平台订单获取运送时间
     */
    private void getShipTime() {
        HashMap<String, String> hp = new HashMap<>();
        SimplifyThread getShipTime = new SimplifyThread(UrlUtil.URL_ORDER_SHIP_TIME, hp);
        getShipTime.setOnKeyResultListener(new SimplifyThread.OnResultListener() {
            @Override
            public void resultBody(String res) {
                Message message = new Message();
                message.obj = res;
                message.what = 3;
                handler.sendMessage(message);
            }

            @Override
            public void errorBody(String error) {
                handler.sendEmptyMessage(0);
            }
        });
    }

    class PlatformIssueOrderAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> list;
        private String[] titles = {"详细描述", "产品图片"};

        public void setList(ArrayList<Fragment> list) {
            this.list = list;
        }

        public PlatformIssueOrderAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}
