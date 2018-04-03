package com.zpf.oillogistics.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lljjcoder.city_20170724.CityPickerView;
import com.lljjcoder.city_20170724.bean.CityBean;
import com.lljjcoder.city_20170724.bean.DistrictBean;
import com.lljjcoder.city_20170724.bean.ProvinceBean;
import com.zpf.oillogistics.R;
import com.zpf.oillogistics.base.BaseActivity;
import com.zpf.oillogistics.base.MessageWhat;
import com.zpf.oillogistics.bean.response.HaveCarResponse;
import com.zpf.oillogistics.net.SimplifyThread;
import com.zpf.oillogistics.net.UrlUtil;
import com.zpf.oillogistics.pulltorefreshlibrary.PullToRefreshBase;
import com.zpf.oillogistics.pulltorefreshlibrary.PullToRefreshListView;
import com.zpf.oillogistics.utils.CallPermission;
import com.zpf.oillogistics.utils.DateTimeUtil;
import com.zpf.oillogistics.utils.MyToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/18.
 * <p>
 * 车找货
 */

public class HomeHaveCarActivity extends BaseActivity {

    // 布局相关
    // plv
    @BindView(R.id.have_car_plv)
    PullToRefreshListView plv;
    @BindView(R.id.rel_back_have_car)
    RelativeLayout relBack;
    @BindView(R.id.have_car_st_tv)
    TextView haveCarStTv;
    @BindView(R.id.have_car_ed_tv)
    TextView haveCarEdTv;
    @BindView(R.id.iv_change)
    ImageView ivChange;

    List<HaveCarResponse.DataBeanX.DataBean> mList = new ArrayList();
    HashMap<String, String> hp = new HashMap<>();

    HomeHaveCarAdapter adapter;

    //页数
    int upPage = 1;

    @Override
    protected int setLayout() {
        return R.layout.activity_home_have_car;
    }

    @Override
    protected void initData() {
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

        haveCarStTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CityPickerView cityPicker = new CityPickerView.Builder(HomeHaveCarActivity.this)
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
                        haveCarStTv.setText(province.getName() + "-" + city.getName());
                        mList.clear();
                        upPage = 1;
                        search();
//                        Toast.makeText(HomeHaveProductActivity.this, province.getName() + city.getName() + district.getName(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel() {

                    }
                });
            }
        });

        haveCarEdTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CityPickerView cityPicker = new CityPickerView.Builder(HomeHaveCarActivity.this)
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
                        haveCarEdTv.setText(province.getName() + "-" + city.getName());
                        mList.clear();
                        search();
//                        Toast.makeText(HomeHaveProductActivity.this, province.getName() + city.getName() + district.getName(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel() {

                    }
                });
            }
        });

        ivChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = haveCarStTv.getText().toString();
                String e = haveCarEdTv.getText().toString();

                haveCarStTv.setText(e);
                haveCarEdTv.setText(s);

                mList.clear();
                upPage = 1;
                search();
            }
        });

        plv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                mList.clear();
                upPage = 1;
                search();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                upPage++;
                search();
            }
        });

        plv.setScrollingWhileRefreshingEnabled(true);
        plv.setMode(PullToRefreshBase.Mode.BOTH);
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

        hp.put("start", haveCarStTv.getText().toString());
        hp.put("end", haveCarEdTv.getText().toString());
        hp.put("page", upPage + "");

        SimplifyThread simplifyThread = new SimplifyThread(UrlUtil.URL_FREIGHT_CAR, hp);
        simplifyThread.setOnKeyResultListener(new SimplifyThread.OnResultListener() {
            @Override
            public void resultBody(String res) {
                Message message = new Message();
                message.obj = res;
                message.what = MessageWhat.FREIGHT_CAR;
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
            plv.onRefreshComplete();
            switch (msg.what) {
                case MessageWhat.FREIGHT_CAR:
                    if (msg.obj != null) {
                        try {
                            HaveCarResponse have = gson.fromJson(msg.obj.toString(), HaveCarResponse.class);

                            if (have.getStatus() == 0) {
                                mList.addAll(have.getData().getData());

                            } else if (have != null) {
                                MyToast.show(HomeHaveCarActivity.this, have.getMsg());
                            }

                            if (adapter == null) {
                                adapter = new HomeHaveCarAdapter();
                                plv.setAdapter(adapter);
                            } else {
                                adapter.notifyDataSetChanged();
                            }

                        } catch (Exception e) {
                            MyToast.show(HomeHaveCarActivity.this, "返回数据异常!");
                        }

                    } else {
                        MyToast.show(HomeHaveCarActivity.this, "返回数据失败!");
                    }
                    break;
            }
        }
    };


    class HomeHaveCarAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (mList != null)
                return mList.size();
            return 0;
        }

        @Override
        public Object getItem(int i) {
            return getItem(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            ViewHolder vh;
            if (view == null) {
                view = LayoutInflater.from(HomeHaveCarActivity.this).inflate(R.layout.item_have_car, viewGroup, false);
                vh = new ViewHolder(view);
                view.setTag(vh);
            } else {
                vh = (ViewHolder) view.getTag();
            }

            //加载头像
            if (mList.get(i).getHerder() != null && !mList.get(i).getHerder().equals("")) {
                Glide.with(HomeHaveCarActivity.this)
                        .load(UrlUtil.IMAGE_URL + mList.get(i).getHerder())
                        .error(R.mipmap.head_default)
                        .into(vh.ivHead);
            }
            //地址
            if (mList.get(i).getStartplace() != null &&
                    mList.get(i).getEndplace() != null) {
                vh.tvAdress.setText(mList.get(i).getStartplace().replace("-", "")
                        + "-" + mList.get(i).getEndplace().replace("-", ""));

            } else {
                vh.tvAdress.setText("--");
            }

            //货物名称
            if (mList.get(i).getGoodsname() != null) {
                vh.tvGoods.setText(mList.get(i).getGoodsname());

            } else {
                vh.tvGoods.setText("--");
            }

            //货物数量
            if (mList.get(i).getNumber() != 0) {
                vh.tvGoodsNum.setText(mList.get(i).getNumber() + "吨");

            } else {
                vh.tvGoodsNum.setText("--");
            }

            //货物价格
            if (mList.get(i).getPrice() != null) {
                vh.tvPrice.setText(mList.get(i).getPrice() + "元/吨");

            } else {
                vh.tvPrice.setText("--");
            }

            //货物日期
            if (mList.get(i).getTime() != 0) {
                vh.tvDate.setText(DateTimeUtil.stampToDate("yyyy/MM/dd", mList.get(i).getTime() + "000"));

            } else {
                vh.tvDate.setText("--");
            }

            //订购显示
//            if (mList.get(i).getIdentity() == 3) {
//                vh.relCall.setVisibility(View.GONE);
//                vh.tvTakeOrder.setVisibility(View.VISIBLE);
//            } else {
//                vh.relCall.setVisibility(View.VISIBLE);
//                vh.tvTakeOrder.setVisibility(View.GONE);
//            }
            vh.relCall.setVisibility(View.GONE);
            vh.relCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    DiyDialog.hintTweBtnDialog(HomeHaveCarActivity.this, "点击确定联系此司机", new DiyDialog.HintTweBtnListener() {
//                        @Override
//                        public void confirm() {
//                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mList.get(i).getPhone()));
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
//                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mList.get(i).getPhone()));
//                    if (ActivityCompat.checkSelfPermission(HomeHaveCarActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                        return;
//                    }
//                    startActivity(intent);
                    CallPermission.callPhone(HomeHaveCarActivity.this, mList.get(i).getPhone());
//                        }
//                    });
                }
            });

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(HomeHaveCarActivity.this, OrderDetailsActivity.class);
                    intent.putExtra("orderID", mList.get(i).getId() + "");
                    startActivity(intent);
                }
            });
            return view;
        }

        class ViewHolder {
            @BindView(R.id.iv_head_have_car_item)
            ImageView ivHead;
            @BindView(R.id.tv_adress_have_car_item)
            TextView tvAdress;
            @BindView(R.id.tv_goodsName_have_car_item)
            TextView tvGoods;
            @BindView(R.id.tv_goodsNum_have_car_item)
            TextView tvGoodsNum;
            @BindView(R.id.tv_takeOrder_have_car_item)
            TextView tvTakeOrder;
            @BindView(R.id.tv_price_have_car_item)
            TextView tvPrice;
            @BindView(R.id.tv_date_have_car_item)
            TextView tvDate;
            @BindView(R.id.rel_call_have_car_item)
            RelativeLayout relCall;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
