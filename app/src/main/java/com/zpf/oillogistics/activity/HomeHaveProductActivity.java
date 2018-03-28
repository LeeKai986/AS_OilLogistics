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
import android.widget.LinearLayout;
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
import com.zpf.oillogistics.bean.response.HaveProductResponse;
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
 * 货找车
 */

public class HomeHaveProductActivity extends BaseActivity {

    // 布局相关
    @BindView(R.id.rel_back_have_product)
    RelativeLayout relBack;
    // 起点位置
    @BindView(R.id.have_product_st_tv)
    TextView stTv;
    // 终点位置
    @BindView(R.id.have_product_ed_tv)
    TextView edTv;
    // plv
    @BindView(R.id.have_product_plv)
    PullToRefreshListView plv;
    @BindView(R.id.iv_change)
    ImageView ivChange;
    // adapter
    HaveProductAdapter adapter;

    List<HaveProductResponse.DataBeanX.DataBean> mList = new ArrayList();
    HashMap<String, String> hp = new HashMap<>();

    int upPage = 1;


    @Override
    protected int setLayout() {
        return R.layout.activity_have_product;
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

        stTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CityPickerView cityPicker = new CityPickerView.Builder(HomeHaveProductActivity.this)
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
                        stTv.setText(province.getName() + "-" + city.getName());
                        mList.clear();
                        search();
                    }

                    @Override
                    public void onCancel() {

                    }
                });
            }
        });

        edTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CityPickerView cityPicker = new CityPickerView.Builder(HomeHaveProductActivity.this)
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
                        edTv.setText(province.getName() + "-" + city.getName());
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
                String s = stTv.getText().toString();
                String e = edTv.getText().toString();

                stTv.setText(e);
                edTv.setText(s);

                upPage = 1;
                mList.clear();
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

        search();
    }

    /**
     * 货找车接口
     */
    private void search() {

        hp.put("start", stTv.getText().toString());
        hp.put("end", edTv.getText().toString());
        hp.put("page", upPage + "");

        SimplifyThread simplifyThread = new SimplifyThread(UrlUtil.URL_RUN_FREIGHT, hp);
        simplifyThread.setOnKeyResultListener(new SimplifyThread.OnResultListener() {
            @Override
            public void resultBody(String res) {
                Message message = new Message();
                message.obj = res;
                message.what = MessageWhat.RUN_FREIGHT;
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
                case MessageWhat.RUN_FREIGHT:
                    if (msg.obj != null) {
                        try {
                            HaveProductResponse have = gson.fromJson(msg.obj.toString(), HaveProductResponse.class);

                            if (have.getStatus() == 0) {
                                mList.addAll(have.getData().getData());

                            } else {
                                MyToast.show(HomeHaveProductActivity.this, "暂无数据!");
                            }

                            if (adapter == null) {
                                adapter = new HaveProductAdapter();
                                plv.setAdapter(adapter);
                            } else {
                                adapter.notifyDataSetChanged();
                            }

                        } catch (Exception e) {
                            MyToast.show(HomeHaveProductActivity.this, "返回数据异常!");
                        }

                    } else {
                        MyToast.show(HomeHaveProductActivity.this, "返回数据失败!");
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    class HaveProductAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (mList != null)
                return mList.size();
            return 0;
        }

        @Override
        public Object getItem(int i) {
            return mList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            ViewHolder vh;
            if (view == null) {
                view = LayoutInflater.from(HomeHaveProductActivity.this).inflate(R.layout.item_have_product, viewGroup, false);
                vh = new ViewHolder(view);
                view.setTag(vh);
            } else {
                vh = (ViewHolder) view.getTag();
            }

            final HaveProductResponse.DataBeanX.DataBean bean = (HaveProductResponse.DataBeanX.DataBean) getItem(i);

            //名字
            if (bean.getName() != null) {
                vh.tvName.setText(bean.getName());
            } else {
                vh.tvName.setText("--");
            }
            //认证
//            if(bean.!=null){
//                vh.tvName.setText(bean.getName());
//            }else {
//                vh.tvName.setText("--");
//            }
            //日期
            if (bean.getTime() != null) {
                vh.tvTime.setText(DateTimeUtil.stampToDate("yyyy/MM/dd", bean.getTime() + "000"));
            } else {
                vh.tvTime.setText("--");
            }

            //地址
            if (bean.getStartplace() != null && bean.getEndplace() != null) {
                vh.tvRoute.setText(bean.getStartplace().replace("-", "") + "-" + bean.getEndplace().replace("-", ""));
            } else {
                vh.tvRoute.setText("--");
            }

            //车牌号
            if (bean.getLoad() != null) {
                vh.tvPlate.setText(bean.getCartcode());
            }
            vh.ivCall.setVisibility(View.GONE);
            vh.ivCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //用intent启动拨打电话
                    if (mList.get(i).getPhone() != null) {
//                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mList.get(i).getPhone()));
//                        if (ActivityCompat.checkSelfPermission(HomeHaveProductActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                            return;
//                        }
//                        startActivity(intent);
                        CallPermission.callPhone(HomeHaveProductActivity.this, mList.get(i).getPhone());
                    } else {
                        MyToast.show(HomeHaveProductActivity.this, "暂无司机联系电话");
                    }
                }
            });

            //加载头像
            if (bean.getHerder() != null && !bean.getHerder().equals("")) {
                Glide.with(HomeHaveProductActivity.this)
                        .load(UrlUtil.IMAGE_URL + bean.getHerder())
                        .error(R.mipmap.head_default)
                        .into(vh.ivIcon);
            }

            vh.linAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(HomeHaveProductActivity.this, DriverDetailsActivity.class);
                    intent.putExtra("phone", bean.getPhone());
                    intent.putExtra("id", bean.getId() + "");
                    startActivity(intent);
                }
            });

            return view;
        }

        class ViewHolder {
            @BindView(R.id.item_have_product_icon_iv)
            ImageView ivIcon;
            @BindView(R.id.item_have_product_name_tv)
            TextView tvName;
            @BindView(R.id.item_have_product_yes_tv)
            TextView tvYes;
            @BindView(R.id.item_have_product_time_iv)
            TextView tvTime;
            @BindView(R.id.item_have_product_plate_tv)
            TextView tvPlate;
            @BindView(R.id.item_have_product_route_tv)
            TextView tvRoute;
            @BindView(R.id.item_have_product_call_iv)
            ImageView ivCall;
            @BindView(R.id.have_product_all_ll)
            LinearLayout linAll;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
