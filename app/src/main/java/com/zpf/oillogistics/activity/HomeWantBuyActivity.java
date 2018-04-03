package com.zpf.oillogistics.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.zpf.oillogistics.R;
import com.zpf.oillogistics.base.BaseActivity;
import com.zpf.oillogistics.base.MessageWhat;
import com.zpf.oillogistics.bean.ProviceAndCityBean;
import com.zpf.oillogistics.bean.WantBuy;
import com.zpf.oillogistics.bean.response.WantBuyListResponse;
import com.zpf.oillogistics.net.JsonUtil;
import com.zpf.oillogistics.net.SimplifyThread;
import com.zpf.oillogistics.net.UrlUtil;
import com.zpf.oillogistics.pulltorefreshlibrary.PullToRefreshBase;
import com.zpf.oillogistics.pulltorefreshlibrary.PullToRefreshListView;
import com.zpf.oillogistics.utils.DateTimeUtil;
import com.zpf.oillogistics.utils.MyToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/15.
 * <p>
 * 求购
 */

public class HomeWantBuyActivity extends BaseActivity {

    // 布局相关
    //返回
    @BindView(R.id.rel_back_wantBuy)
    RelativeLayout relBack;
    // 类型rg
    @BindView(R.id.home_want_buy_all_ll)
    LinearLayout allLl;
    // 筛选类型
    @BindView(R.id.home_want_buy_scope_tv)
    TextView scopeTv;
    // 产品类型
    @BindView(R.id.home_want_buy_product_tv)
    TextView productTv;
    // 筛选界面ll
    @BindView(R.id.home_want_buy_classify_ll)
    LinearLayout allClassifyLl;
    // 求购列表lv
    @BindView(R.id.lv_want_buy)
    PullToRefreshListView lvWantBuy;
    // 主级
    @BindView(R.id.home_want_buy_first_lv)
    ListView firstLv;
    // 次级
    @BindView(R.id.home_want_buy_last_lv)
    ListView lastLv;
    // 关闭
    @BindView(R.id.home_want_buy_twe_list_fl)
    FrameLayout tweListFl;
    //搜索
    @BindView(R.id.iv_search)
    ImageView ivSearch;

    private static String TAG = "AuditingFrag";
    // 类型选择记录
    int type = 0;

    //求购列表list
    List<WantBuy> mList = new ArrayList<>();
    BuyAdapter adapter;
    //列表页
    int upPage = 1;
    //是否是下拉刷新
    HashMap<String, String> hp = new HashMap<>();

    private String province = "";
    private String cityStr = "";
    private String oilStr = "";

    private ArrayList<String> citys = new ArrayList<>();

    private int pro = 0;
    private int city = 0;
    private int oil = 0;

    @Override
    protected int setLayout() {
        return R.layout.activity_home_want_buy;
    }

    @Override
    protected void initData() {
        indexData();
    }

    @Override
    protected void initViewAction(Bundle savedInstanceState) {
        relBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tweListFl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeSearch();
            }
        });

        lvWantBuy.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                Log.i(TAG, "onPullDownToRefresh--res");
                mList.clear();
                upPage = 1;
                indexData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                Log.i(TAG, "onPullUpToRefresh--res");
                upPage++;
                indexData();
            }
        });

        lvWantBuy.setScrollingWhileRefreshingEnabled(true);
        lvWantBuy.setMode(PullToRefreshBase.Mode.BOTH);

        scopeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drawable drawable;
                switch (type) {
                    case 0:
                        type = 1;
                        allClassifyLl.setVisibility(View.VISIBLE);
                        scopeTv.setTextColor(0xffFC4F4F);
                        drawable = getResources().getDrawable(R.drawable.home_down_checked);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        scopeTv.setCompoundDrawablesRelative(null, null, drawable, null);
                        break;
                    case 1:
                        type = 0;
                        allClassifyLl.setVisibility(View.GONE);
                        scopeTv.setTextColor(0xff979797);
                        drawable = getResources().getDrawable(R.drawable.home_down_no_check);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        scopeTv.setCompoundDrawablesRelative(null, null, drawable, null);
                        break;
                    case 2:
                        type = 1;
                        scopeTv.setTextColor(0xffFC4F4F);
                        drawable = getResources().getDrawable(R.drawable.home_down_checked);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        scopeTv.setCompoundDrawablesRelative(null, null, drawable, null);
                        productTv.setTextColor(0xff979797);
                        drawable = getResources().getDrawable(R.drawable.home_down_no_check);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        productTv.setCompoundDrawablesRelative(null, null, drawable, null);
                        break;
                }
                firstLv.setVisibility(View.VISIBLE);
                final ListAdapter firstAdapter = new ListAdapter();
                final ListAdapter lastAdapter = new ListAdapter();
                firstAdapter.setLev(1);
                lastAdapter.setLev(2);
                final ArrayList<ProviceAndCityBean> proviceAndCityBeen = JsonUtil.proviceAndCityBeensResolve(getResources().getString(R.string.province_city));
                final ArrayList<String> provinces = new ArrayList<>();
                for (int i = 0; i < proviceAndCityBeen.size(); i++) {
                    provinces.add(proviceAndCityBeen.get(i).getPro());
                }
                citys.clear();
                for (int i = 0; i < proviceAndCityBeen.get(pro).getCitys().size(); i++) {
                    citys.add(proviceAndCityBeen.get(pro).getCitys().get(i).getCname());
                }
                firstAdapter.setList(provinces);
                firstAdapter.setClickLoc(pro);
                lastAdapter.setList(citys);
                lastAdapter.setClickLoc(city);
                firstLv.setAdapter(firstAdapter);
                if (TextUtils.isEmpty(province)) {
                    province = provinces.get(0);
                }
                firstLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        firstAdapter.setClickLoc(i);
                        pro = i;
                        citys.clear();
                        for (int v = 0; proviceAndCityBeen.get(i) != null
                                && proviceAndCityBeen.get(i).getCitys() != null
                                && v < proviceAndCityBeen.get(i).getCitys().size(); v++) {
                            citys.add(proviceAndCityBeen.get(i).getCitys().get(v).getCname());
                        }

                        lastAdapter.setList(citys);
                        if (citys.size() != 0)
                            lastAdapter.setClickLoc(0);

                        province = provinces.get(i);

                    }
                });
                lastLv.setAdapter(lastAdapter);
                lastLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        lastAdapter.setClickLoc(i);
                        city = i;
                        closeSearch();
                        cityStr = citys.get(i);
                        scopeTv.setText(province + cityStr);
                        mList.clear();
                        upPage = 1;
                        indexData();

                    }
                });
            }
        });

        productTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drawable drawable;
                switch (type) {
                    case 0:
                        type = 2;
                        allClassifyLl.setVisibility(View.VISIBLE);
                        productTv.setTextColor(0xffFC4F4F);
                        drawable = getResources().getDrawable(R.drawable.home_down_checked);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        productTv.setCompoundDrawablesRelative(null, null, drawable, null);
                        break;
                    case 2:
                        type = 0;
                        allClassifyLl.setVisibility(View.GONE);
                        productTv.setTextColor(0xff979797);
                        drawable = getResources().getDrawable(R.drawable.home_down_no_check);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        productTv.setCompoundDrawablesRelative(null, null, drawable, null);
                        break;
                    case 1:
                        type = 2;
                        productTv.setTextColor(0xffFC4F4F);
                        drawable = getResources().getDrawable(R.drawable.home_down_checked);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        productTv.setCompoundDrawablesRelative(null, null, drawable, null);
                        scopeTv.setTextColor(0xff979797);
                        drawable = getResources().getDrawable(R.drawable.home_down_no_check);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        scopeTv.setCompoundDrawablesRelative(null, null, drawable, null);
                        break;
                }
                final ListAdapter lastAdapter = new ListAdapter();
                lastAdapter.setLev(2);
                final ArrayList<String> roles = new ArrayList<String>();
                roles.add("石油类");
                roles.add("化工类");
                lastAdapter.setList(roles);
                lastAdapter.setClickLoc(oil);
                firstLv.setVisibility(View.GONE);
                lastLv.setAdapter(lastAdapter);
                lastLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        lastAdapter.setClickLoc(i);
                        oil = i;
                        closeSearch();
                        oilStr = roles.get(i);
                        productTv.setText(oilStr);
                        mList.clear();
                        upPage = 1;
                        indexData();
                    }
                });
            }
        });

        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeWantBuyActivity.this, ToBuySeekActivity.class));
            }
        });
    }

    /**
     * 关闭筛选
     */
    private void closeSearch() {
        type = 0;
        allClassifyLl.setVisibility(View.GONE);
        productTv.setTextColor(0xff979797);
        scopeTv.setTextColor(0xff979797);
        Drawable drawable = getResources().getDrawable(R.drawable.home_down_no_check);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        productTv.setCompoundDrawablesRelative(null, null, drawable, null);
        scopeTv.setCompoundDrawablesRelative(null, null, drawable, null);

    }


    /**
     * 获取求购信息列表
     */
    private void indexData() {
        hp.put("page", upPage + "");
        if (province.equals("") && !cityStr.equals("")) {
            province = "北京";
        }
        if (!province.equals("")) {
            hp.put("area", province + "-" + cityStr);
        }
        if (oilStr.equals("石油类")) {
            hp.put("class", "1");
        } else if (oilStr.equals("化工类")) {
            hp.put("class", "2");
        } else {
            hp.put("class", "");
        }
        SimplifyThread simplifyThread = new SimplifyThread(UrlUtil.TOBUY_SCREEN, hp);
        simplifyThread.setOnKeyResultListener(new SimplifyThread.OnResultListener() {
            @Override
            public void resultBody(String res) {
                Message message = new Message();
                message.obj = res;
                message.what = MessageWhat.TOBUY_INDEX;
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
            lvWantBuy.onRefreshComplete();
            switch (msg.what) {
                case MessageWhat.TOBUY_INDEX:
                    if (msg.obj != null) {
                        try {
                            WantBuyListResponse buy = gson.fromJson(msg.obj.toString(), WantBuyListResponse.class);

                            if (buy.getStatus() == 0) {
                                mList.addAll(buy.getData().getData());

                            } else {
                                MyToast.show(HomeWantBuyActivity.this, "暂无数据!");
                            }

                            if (adapter == null) {
                                adapter = new BuyAdapter();
                                lvWantBuy.setAdapter(adapter);
                            } else {
                                adapter.notifyDataSetChanged();
                            }

                        } catch (Exception e) {
                            MyToast.show(HomeWantBuyActivity.this, "返回数据异常!");
                        }

                    } else {
                        MyToast.show(HomeWantBuyActivity.this, "返回数据失败!");
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

    class ListAdapter extends BaseAdapter {

        private ArrayList<String> list;
        // 几级listview
        private int lev;
        // 点击位置
        private int clickLoc = 0;

        public void setList(ArrayList<String> list) {
            this.list = list;
            notifyDataSetChanged();
        }

        public void setLev(int lev) {
            this.lev = lev;
        }

        public void setClickLoc(int clickLoc) {
            this.clickLoc = clickLoc;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ListViewHolder vh;
            if (view == null) {
                view = LayoutInflater.from(HomeWantBuyActivity.this).inflate(R.layout.item_home_oil, viewGroup, false);
                vh = new ListViewHolder();
                vh.allLl = view.findViewById(R.id.item_home_oil_all_ll);
                vh.nameTv = view.findViewById(R.id.item_home_oil_tv);
                vh.lineV = view.findViewById(R.id.item_home_bottom_line_v);
                view.setTag(vh);
            } else {
                vh = (ListViewHolder) view.getTag();
            }
            vh.nameTv.setText(list.get(i));
            switch (type) {
                case 1:
                    if (lev == 1) {
                        vh.allLl.setBackgroundColor(0xffF9F9F9);
                        if (clickLoc == i) {
                            vh.allLl.setBackgroundColor(0xffFFFFFF);
                        }
                    } else {
                        if (clickLoc == i) {
                            vh.nameTv.setTextColor(0xffFC4F4F);
                            vh.lineV.setVisibility(View.VISIBLE);
                        } else {
                            vh.nameTv.setTextColor(0xff545454);
                            vh.lineV.setVisibility(View.INVISIBLE);
                        }
                    }
                    break;
                case 2:
                    if (lev == 1) {
                        vh.allLl.setBackgroundColor(0xffF9F9F9);
                        if (clickLoc == i) {
                            vh.allLl.setBackgroundColor(0xffFFFFFF);
                        }
                    } else {
                        if (clickLoc == i) {
                            vh.nameTv.setTextColor(0xffFC4F4F);
                            vh.lineV.setVisibility(View.VISIBLE);
                        } else {
                            vh.nameTv.setTextColor(0xff545454);
                            vh.lineV.setVisibility(View.INVISIBLE);
                        }
                    }
                    break;
            }
            return view;
        }

        class ListViewHolder {
            LinearLayout allLl;
            TextView nameTv;
            View lineV;
        }
    }

    class BuyAdapter extends BaseAdapter {

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
            ViewHolder vh = null;
            if (view == null) {
                view = LayoutInflater.from(HomeWantBuyActivity.this).inflate(R.layout.item_want_buy_main, viewGroup, false);
                vh = new ViewHolder(view);
                view.setTag(vh);
            } else {
                vh = (ViewHolder) view.getTag();
            }

            final WantBuy buy = (WantBuy) getItem(i);
            if (buy.getImg() != null) {
                Glide.with(HomeWantBuyActivity.this)
                        .load(UrlUtil.IMAGE_URL + buy.getImg())
                        .error(R.mipmap.default_goods)
                        .into(vh.ivIcon);
            }
            if (buy.getTitle() != null) {
                vh.tvTitle.setText(buy.getTitle());
            }

            //2企业,1个人,3平台
            if (buy.getIs_user() == 3) {
                vh.tvName.setText("平台");

            } else if (buy.getIs_user() == 2) {
                if (buy.getName() != null) {
                    vh.tvName.setText(buy.getName());
                }
            } else {
                vh.tvName.setText("个人");
            }

            if (buy.getNumber() != null) {
                vh.tvNum.setText("求购数量:" + buy.getNumber() + "吨");
            }

            if (buy.getTime() != 0) {
                vh.tvTime.setText(DateTimeUtil.stampToDate("yyyy/MM/dd HH:mm:ss", buy.getTime() + "000"));
            }

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(HomeWantBuyActivity.this, FirmDetailsActivity.class);
                    intent.putExtra("inforId", buy.getId() + "");
                    startActivity(intent);
                }
            });
            return view;
        }


        class ViewHolder {
            @BindView(R.id.iv_Icon)
            ImageView ivIcon;
            @BindView(R.id.tv_title)
            TextView tvTitle;
            @BindView(R.id.tv_time)
            TextView tvTime;
            @BindView(R.id.tv_num)
            TextView tvNum;
            @BindView(R.id.tv_name)
            TextView tvName;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
