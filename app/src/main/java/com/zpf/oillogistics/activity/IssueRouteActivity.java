package com.zpf.oillogistics.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

import static com.zpf.oillogistics.utils.MyShare.getShared;

/**
 * Created by Administrator on 2017/9/14.
 * <p>
 * 发布行程
 */

public class IssueRouteActivity extends BaseActivity implements View.OnClickListener {

    // 布局相关
    // 类型
    @BindView(R.id.issue_route_type_rl)
    RelativeLayout typeRl;
    //返回
    @BindView(R.id.rel_back)
    RelativeLayout relBack;
    //起点
    @BindView(R.id.tv_star_adress)
    TextView tvStar;
    //终点
    @BindView(R.id.tv_end_adress)
    TextView tvEnd;
    //日期
    @BindView(R.id.tv_time)
    TextView tvTime;
    // 承运日期
    @BindView(R.id.issue_route_start_run_time_rl)
    RelativeLayout timeRl;
    //类型
    @BindView(R.id.tv_type)
    TextView tvType;
    //提交
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    //载重
    @BindView(R.id.edit_load)
    EditText editLoad;

    String starArea = "";
    String endArea = "";


    @Override
    protected int setLayout() {
        return R.layout.activity_issue_route;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViewAction(Bundle savedInstanceState) {
        timeRl.setOnClickListener(this);
        typeRl.setOnClickListener(this);
        tvStar.setOnClickListener(this);
        tvEnd.setOnClickListener(this);
        relBack.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);
        String load = MyShare.getShared().getString("load", "");
        String car_type = MyShare.getShared().getString("car_type", "");
        if (load != null && !load.equals("")) {
            editLoad.setText(load);
        }
        if (car_type != null && !car_type.equals("")) {
            tvType.setText(car_type);
        }
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
            case R.id.tv_star_adress:
                CityPickerView cityPicker1 = new CityPickerView.Builder(IssueRouteActivity.this)
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
                cityPicker1.show();

                //监听方法，获取选择结果
                cityPicker1.setOnCityItemClickListener(new CityPickerView.OnCityItemClickListener() {
                    @Override
                    public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                        //返回结果
                        //ProvinceBean 省份信息
                        //CityBean     城市信息
                        //DistrictBean 区县信息

                        if (district.getName() != null && !district.getName().equals(""))
                            starArea = province.getName() + "-" + city.getName() + "-" + district.getName();
                        else
                            starArea = province.getName() + "-" + city.getName();
                        tvStar.setText(province.getName() + city.getName() + district.getName());
                    }

                    @Override
                    public void onCancel() {

                    }
                });
                break;
            case R.id.tv_end_adress:
                CityPickerView cityPicker2 = new CityPickerView.Builder(IssueRouteActivity.this)
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
                cityPicker2.show();

                //监听方法，获取选择结果
                cityPicker2.setOnCityItemClickListener(new CityPickerView.OnCityItemClickListener() {
                    @Override
                    public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                        //返回结果
                        //ProvinceBean 省份信息
                        //CityBean     城市信息
                        //DistrictBean 区县信息

                        if (district.getName() != null && !district.getName().equals(""))
                            endArea = province.getName() + "-" + city.getName() + "-" + district.getName();
                        else
                            endArea = province.getName() + "-" + city.getName();
                        tvEnd.setText(province.getName() + city.getName() + district.getName());
                    }

                    @Override
                    public void onCancel() {

                    }
                });
                break;
            case R.id.issue_route_start_run_time_rl:
                DiyDialog.dateSelectDialog(IssueRouteActivity.this, new DiyDialog.GetTimeListener() {
                    @Override
                    public void getTime(String res) {
                        tvTime.setText(res);
                    }
                });

                break;
            case R.id.issue_route_type_rl:
//                DiyDialog.routeTypeDialog(IssueRouteActivity.this, new DiyDialog.RouteTypeListener() {
//                    @Override
//                    public void routeType(String res) {
//                        tvType.setText(res);
//                    }
//                });
                break;
            case R.id.tv_submit:
                toSubmitw();
                break;
        }
    }

    /**
     * 提交
     */
    private void toSubmitw() {
        if (tvStar.getText().toString().equals("")) {
            MyToast.show(IssueRouteActivity.this, "请输入起点");
            return;
        }
        if (tvEnd.getText().toString().equals("")) {
            MyToast.show(IssueRouteActivity.this, "请输入终点");
            return;
        }
        if (tvTime.getText().toString().equals("")) {
            MyToast.show(IssueRouteActivity.this, "请选择承运日期");
            return;
        }
        if (tvType.getText().toString().equals("")) {
            MyToast.show(IssueRouteActivity.this, "请选择类型");
            return;
        }
        if (editLoad.getText().toString().equals("")) {
            MyToast.show(IssueRouteActivity.this, "请输入载重");
            return;
        }

        HashMap subHp = new HashMap();

        subHp.put("uid", getShared().getString("userId", ""));
        subHp.put("startplace", starArea);
        subHp.put("endplace", endArea);
        subHp.put("time", DateTimeUtil.dateToStamp("yyyy/MM/dd", tvTime.getText().toString()));
        subHp.put("class", getType(tvType.getText().toString()));
        subHp.put("load", editLoad.getText().toString());

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
                                MyToast.show(IssueRouteActivity.this, "发布成功!");
                                finish();
                            } else if (index != null) {
                                MyToast.show(IssueRouteActivity.this, index.getMsg());
                            }

                        } catch (Exception e) {
                            MyToast.show(IssueRouteActivity.this, "返回数据异常!");
                        }

                    } else {
                        MyToast.show(IssueRouteActivity.this, "返回数据失败!");
                    }
                    break;
            }
        }
    };
}
