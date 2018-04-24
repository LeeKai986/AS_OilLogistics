package com.zpf.oillogistics.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.zpf.oillogistics.base.MessageWhat;
import com.zpf.oillogistics.bean.response.MyProductResponse;
import com.zpf.oillogistics.diy.DiyDialog;
import com.zpf.oillogistics.net.SimplifyThread;
import com.zpf.oillogistics.net.UrlUtil;
import com.zpf.oillogistics.utils.DateTimeUtil;
import com.zpf.oillogistics.utils.MyShare;
import com.zpf.oillogistics.utils.MyToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/14.
 * <p>
 * 发货运
 */

public class IssueIncarActivity extends BaseActivity {

    // 布局相关
    // 起点位置
    @BindView(R.id.issue_incar_st_ll)
    LinearLayout stLl;
    // 终点位置
    @BindView(R.id.issue_incar_ed_ll)
    LinearLayout edLl;
    // 结算方式
    @BindView(R.id.issue_incar_settlement_type_rl)
    RelativeLayout settlementTypeRl;
    // 起运日期
    @BindView(R.id.issue_incar_start_run_time_rl)
    RelativeLayout startRunTimeRl;
    //起始区域
    @BindView(R.id.edit_star_arae)
    TextView editStarArae;
    //起始详细地址
    @BindView(R.id.edit_star_adress)
    EditText editStarAdress;
    //结束区域
    @BindView(R.id.edit_end_arae)
    TextView editEndArae;
    //结束详细地址
    @BindView(R.id.edit_end_adress)
    EditText editEndAdress;
    //返回
    @BindView(R.id.rel_back)
    RelativeLayout relBack;
    //货物名称
    @BindView(R.id.tv_goodsName)
    EditText tvGoodsName;
    //货物数量
    @BindView(R.id.edit_goodsNum)
    EditText editGoodsNum;
    //价格
    @BindView(R.id.edit_goodsPrice)
    EditText editGoodsPrice;
    //支付方式
    @BindView(R.id.tv_payway)
    TextView tvPayway;
    //起始时间
    @BindView(R.id.tv_star_time)
    TextView tvStarTime;
    //联系人
    @BindView(R.id.tv_recipient_name)
    EditText editRecipientName;
    //联系电话
    @BindView(R.id.tv_recipient_phone)
    EditText editRecipientPhone;
    //发布提交
    @BindView(R.id.issue_incar_sub_tv)
    TextView tvSub;
    //备注
    @BindView(R.id.edit_marker)
    EditText editMarker;
    @BindView(R.id.iv_change)
    ImageView ivChange;

    String starArea = "";
    String endArea = "";

    @Override
    protected int setLayout() {
        return R.layout.activity_issue_incar;
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

        tvSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvSub.setClickable(false);
                publicCar();
            }
        });

        ivChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sa = editStarArae.getText().toString();
                String sd = editStarAdress.getText().toString();
                String ea = editEndArae.getText().toString();
                String ed = editEndAdress.getText().toString();

                editStarArae.setText(ea);
                editStarAdress.setText(ed);
                editEndArae.setText(sa);
                editEndAdress.setText(sd);

            }
        });

        editStarArae.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CityPickerView cityPicker = new CityPickerView.Builder(IssueIncarActivity.this)
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
                        if (district.getName() != null && !district.getName().equals(""))
                            starArea = province.getName() + "-" + city.getName() + "-" + district.getName();
                        else
                            starArea = province.getName() + "-" + city.getName();
                        editStarArae.setText(province.getName() + city.getName() + district.getName());
                    }

                    @Override
                    public void onCancel() {
                    }
                });
            }
        });

        editEndArae.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CityPickerView cityPicker = new CityPickerView.Builder(IssueIncarActivity.this)
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
                        if (district.getName() != null && !district.getName().equals(""))
                            endArea = province.getName() + "-" + city.getName() + "-" + district.getName();
                        else
                            endArea = province.getName() + "-" + city.getName();
                        editEndArae.setText(province.getName() + city.getName() + district.getName());
                    }

                    @Override
                    public void onCancel() {
                    }
                });
            }
        });


        settlementTypeRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> settlementTypes = new ArrayList<>();
                settlementTypes.add("单结");
                settlementTypes.add("周结");
                settlementTypes.add("月结");
                DiyDialog.singleSelectDialog(IssueIncarActivity.this, settlementTypes, new DiyDialog.SingleSelectListener() {
                    @Override
                    public void SingleSelect(String res) {
                        tvPayway.setText(res);
                    }
                });
            }
        });

        startRunTimeRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DiyDialog.dateSelectDialog(IssueIncarActivity.this, new DiyDialog.GetTimeListener() {
                    @Override
                    public void getTime(String res) {
                        tvStarTime.setText(res);
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
     * 发布货运
     */
    private void publicCar() {
        if (editStarArae.getText().toString().equals("")) {
            MyToast.show(IssueIncarActivity.this, "请选择起点区域");
            return;
        }
        if (editStarAdress.getText().toString().equals("")) {
            MyToast.show(IssueIncarActivity.this, "请输入详细起点");
            return;
        }
        if (editEndArae.getText().toString().equals("")) {
            MyToast.show(IssueIncarActivity.this, "请选择终点区域");
            return;
        }
        if (editEndAdress.getText().toString().equals("")) {
            MyToast.show(IssueIncarActivity.this, "请输入详细终点");
            return;
        }
        if (tvGoodsName.getText().toString().equals("")) {
            MyToast.show(IssueIncarActivity.this, "请输入货物名称");
            return;
        }
        if (editGoodsNum.getText().toString().equals("")) {
            MyToast.show(IssueIncarActivity.this, "请输入货物数量");
            return;
        }
        if (editGoodsPrice.getText().toString().equals("")) {
            MyToast.show(IssueIncarActivity.this, "请输入货物单价");
            return;
        }
        if (tvPayway.getText().toString().equals("")) {
            MyToast.show(IssueIncarActivity.this, "请选择结算方式");
            return;
        }
        if (tvStarTime.getText().toString().equals("")) {
            MyToast.show(IssueIncarActivity.this, "请选择起运日期");
            return;
        }
        if (editRecipientName.getText().toString().equals("")) {
            MyToast.show(IssueIncarActivity.this, "请输入联系人");
            return;
        }
        if (editRecipientPhone.getText().toString().equals("")) {
            MyToast.show(IssueIncarActivity.this, "请输入联系电话");
            return;
        }

        HashMap hp = new HashMap<String, String>();
        hp.put("uid", MyShare.getShared().getString("userId", ""));
        hp.put("startplace", starArea);
        hp.put("startaddress", editStarAdress.getText().toString());
        hp.put("endplace", endArea);
        hp.put("endaddress", editEndAdress.getText().toString());
        hp.put("goodsname", tvGoodsName.getText().toString());
        hp.put("company", MyShare.getShared().getString("companyname", ""));
        hp.put("number", editGoodsNum.getText().toString());
        hp.put("price", editGoodsPrice.getText().toString());

        String way = tvPayway.getText().toString();
        if (way.equals("单结"))
            hp.put("method", "1");
        else if (way.equals("周结"))
            hp.put("method", "2");
        else if (way.equals("月结"))
            hp.put("method", "3");

        hp.put("time", DateTimeUtil.dateToStamp("yyyy/MM/dd", tvStarTime.getText().toString()));
        hp.put("name", editRecipientName.getText().toString());
        hp.put("phone", editRecipientPhone.getText().toString());
        hp.put("comment", editMarker.getText().toString());
        SimplifyThread simplifyThread = new SimplifyThread(UrlUtil.URL_PLUSSIGN_FREIGHT, hp);
        simplifyThread.setOnKeyResultListener(new SimplifyThread.OnResultListener() {
            @Override
            public void resultBody(String res) {
                Message message = new Message();
                message.obj = res;
                message.what = MessageWhat.SELF_PLUSSIGN_FREIGHT;
                handler.sendMessage(message);
            }

            @Override
            public void errorBody(String error) {
                handler.sendEmptyMessage(MessageWhat.REQUST_ERROR);
                tvSub.setClickable(true);
            }
        });
    }


    private Gson gson = new Gson();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MessageWhat.SELF_PLUSSIGN_FREIGHT:

                    if (msg.obj != null) {
                        try {
                            MyProductResponse product = gson.fromJson(msg.obj.toString(), MyProductResponse.class);

                            if (product.getStatus() == 0) {
                                MyToast.show(IssueIncarActivity.this, "发布成功!");
                                finish();
                            } else {
                                tvSub.setClickable(true);
                                MyToast.show(IssueIncarActivity.this, product.getMsg());
                            }

                        } catch (Exception e) {
                            tvSub.setClickable(true);
                            MyToast.show(IssueIncarActivity.this, "返回数据异常!");
                        }

                    } else {
                        tvSub.setClickable(true);
                        MyToast.show(IssueIncarActivity.this, "返回数据失败!");
                    }

                    break;
            }
        }
    };
}
