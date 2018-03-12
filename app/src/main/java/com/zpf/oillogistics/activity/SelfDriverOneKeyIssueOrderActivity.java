package com.zpf.oillogistics.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lljjcoder.city_20170724.CityPickerView;
import com.lljjcoder.city_20170724.bean.CityBean;
import com.lljjcoder.city_20170724.bean.DistrictBean;
import com.lljjcoder.city_20170724.bean.ProvinceBean;
import com.zpf.oillogistics.R;
import com.zpf.oillogistics.base.BaseActivity;
import com.zpf.oillogistics.base.MessageWhat;
import com.zpf.oillogistics.bean.response.IndexResponse;
import com.zpf.oillogistics.diy.DiyDialog;
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
 * 个人司机一键下单
 */

public class SelfDriverOneKeyIssueOrderActivity extends BaseActivity implements View.OnClickListener {

    //返回
    @BindView(R.id.rel_back)
    RelativeLayout relBack;
    //起始地
    @BindView(R.id.have_product_st_tv)
    TextView stTv;
    //结束地
    @BindView(R.id.have_product_ed_tv)
    TextView edTv;
    //运营时间
    @BindView(R.id.tv_time)
    TextView tvTime;
    //类型
    @BindView(R.id.tv_type)
    TextView tvType;
    //重载
    @BindView(R.id.tv_load)
    EditText tvLoad;
    //提交
    @BindView(R.id.platform_issue_order_share_tv)
    TextView shareTv;
    //时间rel
    @BindView(R.id.rel_time)
    RelativeLayout relTime;
    //类型rel
    @BindView(R.id.rel_type)
    RelativeLayout relType;

    @Override
    protected int setLayout() {
        return R.layout.activity_self_driver_one_key_issue_order;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViewAction(Bundle savedInstanceState) {
        relTime.setOnClickListener(this);
        relType.setOnClickListener(this);
        stTv.setOnClickListener(this);
        edTv.setOnClickListener(this);
        relBack.setOnClickListener(this);
        shareTv.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rel_back:
                finish();
                break;
            case R.id.have_product_st_tv:
                CityPickerView cityPicker1 = new CityPickerView.Builder(SelfDriverOneKeyIssueOrderActivity.this)
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
                        .onlyShowProvinceAndCity(true)
                        .build();
                cityPicker1.show();

                //监听方法，获取选择结果
                cityPicker1.setOnCityItemClickListener(new CityPickerView.OnCityItemClickListener() {
                    @Override
                    public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                        //返回结果
                        //ProvinceBean 省份信息
                        //CityBean     城市信息
                        //DistrictBean 区县信息
                        stTv.setText(province.getName() + city.getName() + district.getName());
                    }

                    @Override
                    public void onCancel() {

                    }
                });

                break;
            case R.id.have_product_ed_tv:
                CityPickerView cityPicker = new CityPickerView.Builder(SelfDriverOneKeyIssueOrderActivity.this)
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
                        .onlyShowProvinceAndCity(true)
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
                        edTv.setText(province.getName() + city.getName() + district.getName());
                    }

                    @Override
                    public void onCancel() {

                    }
                });
                break;
            case R.id.rel_time:
                DiyDialog.dateSelectDialog(SelfDriverOneKeyIssueOrderActivity.this, new DiyDialog.GetTimeListener() {
                    @Override
                    public void getTime(String res) {
                        tvTime.setText(res);
                    }
                });

                break;
            case R.id.rel_type:
                DiyDialog.routeTypeDialog(SelfDriverOneKeyIssueOrderActivity.this, new DiyDialog.RouteTypeListener() {
                    @Override
                    public void routeType(String res) {
                        tvType.setText(res);
                    }
                });
                break;
            case R.id.platform_issue_order_share_tv:
                toSubmitw();
                break;
        }
    }

    /**
     * 提交
     */
    private void toSubmitw() {
        HashMap subHp = new HashMap();

        subHp.put("uid", MyShare.getShared().getString("userId", ""));
        subHp.put("startplace", stTv.getText().toString());
        subHp.put("endplace", edTv.getText().toString());
        subHp.put("time", DateTimeUtil.dateToStamp("yyyy/MM/dd", tvTime.getText().toString()));
        subHp.put("class", getType(tvType.getText().toString()));
        subHp.put("load", tvLoad.getText().toString());
        subHp.put("dispatch", "1");

        SimplifyThread simplifyThread = new SimplifyThread(UrlUtil.URL_PLUSSIGN_RUN, subHp);
        simplifyThread.setOnKeyResultListener(new SimplifyThread.OnResultListener() {
            @Override
            public void resultBody(String res) {
                Message message = new Message();
                message.obj = res;
                message.what = MessageWhat.SELF_PLUSSIGN_RUN;
                handler.sendMessage(message);
            }

            @Override
            public void errorBody(String error) {
                handler.sendEmptyMessage(MessageWhat.REQUST_ERROR);
            }
        });
    }

    //类型值转换
    private String getType(String type) {
        switch (type) {
            case "第一类":
                return "1";
            case "第二类":
                return "2";
            case "第三类":
                return "3";
            case "第四类":
                return "4";
            case "第五类":
                return "5";
            case "第六类":
                return "6";
            case "第七类":
                return "7";
            case "第八类":
                return "8";
            case "第九类":
                return "9";
        }
        return "1";
    }

    private Gson gson = new Gson();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MessageWhat.SELF_PLUSSIGN_RUN:
                    if (msg.obj != null) {
                        try {
                            IndexResponse index = gson.fromJson(msg.obj.toString(), IndexResponse.class);

                            if (index.getStatus() == 0) {
                                MyToast.show(SelfDriverOneKeyIssueOrderActivity.this, "下单成功!");
                                finish();
                            } else if (index != null) {
                                MyToast.show(SelfDriverOneKeyIssueOrderActivity.this, index.getMsg());
                            }

                        } catch (Exception e) {
                            MyToast.show(SelfDriverOneKeyIssueOrderActivity.this, "返回数据异常!");
                        }

                    } else {
                        MyToast.show(SelfDriverOneKeyIssueOrderActivity.this, "返回数据失败!");
                    }
                    break;
            }
        }
    };
}
