package com.zpf.oillogistics.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
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
import com.dream.life.library.throwable.utils.CheckUtil;
import com.google.gson.Gson;
import com.zpf.oillogistics.R;
import com.zpf.oillogistics.base.BaseActivity;
import com.zpf.oillogistics.base.MessageWhat;
import com.zpf.oillogistics.bean.OilOrChemtryBean;
import com.zpf.oillogistics.bean.ProviceAndCityBean;
import com.zpf.oillogistics.bean.response.ProductClassResponsse;
import com.zpf.oillogistics.net.JsonUtil;
import com.zpf.oillogistics.net.SimplifyThread;
import com.zpf.oillogistics.net.UrlUtil;
import com.zpf.oillogistics.pulltorefreshlibrary.PullToRefreshBase;
import com.zpf.oillogistics.pulltorefreshlibrary.PullToRefreshListView;
import com.zpf.oillogistics.utils.DateTimeUtil;
import com.zpf.oillogistics.utils.MyIntent;
import com.zpf.oillogistics.utils.MyShare;
import com.zpf.oillogistics.utils.MyToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/9/14.
 * <p>
 * 石油类
 */

public class HomeOilActivity extends BaseActivity {

    // 布局相关
    // 返回
    @BindView(R.id.back_rl)
    RelativeLayout backRl;
    //搜索
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    // 类型rg
    @BindView(R.id.home_oil_all_ll)
    LinearLayout allLl;
    // 筛选类型
    @BindView(R.id.home_oil_scope_tv)
    TextView scopeTv;
    // 产品类型
    @BindView(R.id.home_oil_product_tv)
    TextView productTv;
    // 标题
    @BindView(R.id.tv_title)
    TextView tvTitle;
    // 所有类型
    @BindView(R.id.home_oil_type_tv)
    TextView typeTv;
    // 筛选界面ll
    @BindView(R.id.home_oil_classify_ll)
    LinearLayout allClassifyLl;
    // 主级
    @BindView(R.id.home_oil_first_lv)
    ListView firstLv;
    // 次级
    @BindView(R.id.home_oil_last_lv)
    ListView lastLv;
    // 双List的fl
    @BindView(R.id.home_oil_twe_list_fl)
    FrameLayout tweListFl;
    // plv
    @BindView(R.id.home_oil_plv)
    PullToRefreshListView plv;
    // adapter
    OilOrChemtryAdapter adapter;

    // 类型选择记录
    int type = 0;
//    // 主级位置记录
//    String firstLvLoc = "0";
//    // 次级位置记录
//    String lastLvLoc;

    // 数据相关
    // 石油类
    SimplifyThread oilThread;
    // 石油类传参
    HashMap<String, String> oilMap;
    // 石油类 化工类
    OilOrChemtryBean oilOrChemtryBean;
    // 石油类 化工类 存储
    OilOrChemtryBean oldOilOrChemtryBean;

    private String province = "";
    private String cityStr = "";
    private String oilStr = "";
    private String oilModelStr = "";
    private String userTypeStr = "";

    private int pro = 0;
    private int city = 0;
    private int oil = 0;
    private int oilModel = 0;
    private int userType = 0;
    List<ProductClassResponsse.DataBean> firstList;
    List<ProductClassResponsse.DataBean.ChildBean> secondList;

    //分页参数
    private int page = 1;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    break;
                case 1:
                    if (oldOilOrChemtryBean != null && oldOilOrChemtryBean.getData() != null && oldOilOrChemtryBean.getData().getData() != null && oldOilOrChemtryBean.getData().getData().size() != 0) {

                        if (page == 1 && oilOrChemtryBean != null && oilOrChemtryBean.getData() != null && oilOrChemtryBean.getData().getData() != null && oilOrChemtryBean.getData().getData().size() != 0) {
                            adapter.setBean(oilOrChemtryBean);
                        } else
                            adapter.setBean(oldOilOrChemtryBean);
                    } else if (oilOrChemtryBean != null) {
                        adapter.setBean(oilOrChemtryBean);
                        MyToast.show(HomeOilActivity.this, oilOrChemtryBean.getMsg());
                    } else {
                        adapter.setBean(oilOrChemtryBean);
                        MyToast.show(HomeOilActivity.this, "未能获取数据");
                    }
                    plv.onRefreshComplete();
                    break;
                case 2:
                    break;
                case 3:
                    if (message.obj != null) {
                        try {
                            ProductClassResponsse product = new Gson().fromJson(message.obj.toString(), ProductClassResponsse.class);

                            if (product.getStatus() == 0) {
                                firstList = product.getData();

                            } else {
                                MyToast.show(HomeOilActivity.this, "暂无产品分类数据!");
                            }

                        } catch (Exception e) {
                            MyToast.show(HomeOilActivity.this, "返回数据异常!");
                        }

                    } else {
                        MyToast.show(HomeOilActivity.this, "返回数据失败!");
                    }
                    break;
            }
            return false;
        }
    });

    @Override
    protected int setLayout() {
        return R.layout.activity_home_oil;
    }

    @Override
    protected void initData() {
        getOilOrChemistry();
    }

    @Override
    protected void initViewAction(Bundle savedInstanceState) {
        oilMap = new HashMap<>();

        if (getIntent().getStringExtra("manage").equals("1")) {
            oilMap.put("class", "1");
            tvTitle.setText("石油类");
        } else {
            oilMap.put("class", "2");
            tvTitle.setText("化工类");
        }

        oilMap.put("type", "");
        oilMap.put("area", "");
        oilMap.put("product", "");
        oilMap.put("page", page + "");

        backRl.setOnClickListener(new View.OnClickListener() {
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

        adapter = new OilOrChemtryAdapter();

        plv.setScrollingWhileRefreshingEnabled(true);
        plv.setMode(PullToRefreshBase.Mode.BOTH);
        plv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                oldOilOrChemtryBean = null;
                page = 1;
                oilMap.put("page", page + "");
                getOilOrChemistry();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                oilMap.put("page", page + "");
                getOilOrChemistry();
            }
        });
        plv.setAdapter(adapter);
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
                    case 3:
                        type = 1;
                        scopeTv.setTextColor(0xffFC4F4F);
                        drawable = getResources().getDrawable(R.drawable.home_down_checked);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        scopeTv.setCompoundDrawablesRelative(null, null, drawable, null);
                        typeTv.setTextColor(0xff979797);
                        drawable = getResources().getDrawable(R.drawable.home_down_no_check);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        typeTv.setCompoundDrawablesRelative(null, null, drawable, null);
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
                final ArrayList<String> citys = new ArrayList<>();
                for (int i = 0; i < proviceAndCityBeen.get(pro).getCitys().size(); i++) {
                    citys.add(proviceAndCityBeen.get(pro).getCitys().get(i).getCname());
                }
                final String[] firstLvClickStr = {provinces.get(0)};
                firstAdapter.setList(provinces);
                lastAdapter.setList(citys);
                firstAdapter.setClickLoc(pro);
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
                        for (int v = 0; proviceAndCityBeen.get(i) != null &&
                                proviceAndCityBeen.get(i).getCitys() != null
                                && v < proviceAndCityBeen.get(i).getCitys().size(); v++) {
                            citys.add(proviceAndCityBeen.get(i).getCitys().get(v).getCname());
                        }
                        lastAdapter.setList(citys);
                        if (citys.size() != 0)
                            lastAdapter.setClickLoc(0);
                        firstLvClickStr[0] = provinces.get(i);
//                        firstLvClickStr[0] + "-" +
                        province = provinces.get(i);

                    }
                });

                lastAdapter.setClickLoc(city);
                lastLv.setAdapter(lastAdapter);
                lastLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        lastAdapter.setClickLoc(i);
                        city = i;
//                        firstLvClickStr[0] + "-" +
                        closeSearch();
                        cityStr = citys.get(i);
                        scopeTv.setText(cityStr);
                        oilMap.put("area", province + "-" + cityStr);
                        oldOilOrChemtryBean = null;
                        getOilOrChemistry();
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
                    case 3:
                        type = 2;
                        productTv.setTextColor(0xffFC4F4F);
                        drawable = getResources().getDrawable(R.drawable.home_down_checked);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        productTv.setCompoundDrawablesRelative(null, null, drawable, null);
                        typeTv.setTextColor(0xff979797);
                        drawable = getResources().getDrawable(R.drawable.home_down_no_check);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        typeTv.setCompoundDrawablesRelative(null, null, drawable, null);
                        break;
                }
                firstLv.setVisibility(View.VISIBLE);
                final ListAdapter firstAdapter = new ListAdapter();
                final ListAdapter lastAdapter = new ListAdapter();
                firstAdapter.setLev(1);
                lastAdapter.setLev(2);
                String[] productNameArr = getResources().getStringArray(R.array.product_one_type);
                final String[] productTypeArr = getResources().getStringArray(R.array.product_twe_type);
                final ArrayList<String> productNames = new ArrayList<>();
                for (int i = 0; firstList != null && i < firstList.size(); i++) {
                    productNames.add(firstList.get(i).getName());
                }
                final ArrayList<String> productTypes = new ArrayList<>();
                if (firstList != null)
                    secondList = firstList.get(oil).getChild();
                for (int i = 0; secondList != null && i < secondList.size(); i++) {
                    productTypes.add(secondList.get(i).getName());
                }
                if (!CheckUtil.isNull(productNames)) {
                    final String[] firstLvClickStr = {productNames.get(0)};
                    firstAdapter.setList(productNames);
                    lastAdapter.setList(productTypes);
                    firstAdapter.setClickLoc(oil);
                    firstLv.setAdapter(firstAdapter);
                    if (TextUtils.isEmpty(oilStr)) {
                        oilStr = productNames.get(0);
                    }
                    firstLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            firstAdapter.setClickLoc(i);
                            oil = i;
                            productTypes.clear();
                            if (firstList != null)
                                secondList = firstList.get(oil).getChild();
                            for (int v = 0; secondList != null && v < secondList.size(); v++) {
                                productTypes.add(secondList.get(v).getName());
                            }
                            lastAdapter.setList(productTypes);
                            lastAdapter.setClickLoc(0);
                            firstLvClickStr[0] = productNames.get(i);
                            oilStr = productNames.get(i);
                            oldOilOrChemtryBean = null;
                            getOilOrChemistry();
                        }
                    });

                    lastAdapter.setClickLoc(oilModel);
                    lastLv.setAdapter(lastAdapter);
                    lastLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            lastAdapter.setClickLoc(i);
                            oilModel = i;
                            closeSearch();
                            oilModelStr = productTypes.get(i);
                            productTv.setText(oilStr + oilModelStr);
                            oilMap.put("product", oilStr + "-" + oilModelStr);
                            oldOilOrChemtryBean = null;
                            getOilOrChemistry();
                        }
                    });
                }

            }
        });

        typeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drawable drawable;
                switch (type) {
                    case 0:
                        type = 3;
                        allClassifyLl.setVisibility(View.VISIBLE);
                        typeTv.setTextColor(0xffFC4F4F);
                        drawable = getResources().getDrawable(R.drawable.home_down_checked);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        typeTv.setCompoundDrawablesRelative(null, null, drawable, null);
                        break;
                    case 1:
                        type = 3;
                        typeTv.setTextColor(0xffFC4F4F);
                        drawable = getResources().getDrawable(R.drawable.home_down_checked);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        typeTv.setCompoundDrawablesRelative(null, null, drawable, null);
                        scopeTv.setTextColor(0xff979797);
                        drawable = getResources().getDrawable(R.drawable.home_down_no_check);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        scopeTv.setCompoundDrawablesRelative(null, null, drawable, null);
                        break;
                    case 2:
                        type = 3;
                        typeTv.setTextColor(0xffFC4F4F);
                        drawable = getResources().getDrawable(R.drawable.home_down_checked);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        typeTv.setCompoundDrawablesRelative(null, null, drawable, null);
                        productTv.setTextColor(0xff979797);
                        drawable = getResources().getDrawable(R.drawable.home_down_no_check);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        productTv.setCompoundDrawablesRelative(null, null, drawable, null);
                        break;
                    case 3:
                        type = 0;
                        allClassifyLl.setVisibility(View.GONE);
                        typeTv.setTextColor(0xff979797);
                        drawable = getResources().getDrawable(R.drawable.home_down_no_check);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        typeTv.setCompoundDrawablesRelative(null, null, drawable, null);
                        break;
                }
                final ListAdapter lastAdapter = new ListAdapter();
                lastAdapter.setLev(2);
                final ArrayList<String> roles = new ArrayList<String>();
                roles.add("个人");
                roles.add("企业");
                roles.add("平台");
                lastAdapter.setList(roles);
                lastAdapter.setClickLoc(userType);
                firstLv.setVisibility(View.GONE);
                lastLv.setAdapter(lastAdapter);
                lastLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        lastAdapter.setClickLoc(i);
                        userType = i;
                        closeSearch();
                        userTypeStr = roles.get(i);
                        typeTv.setText(userTypeStr);
                        oilMap.put("type", (userType + 1) + "");
                        oldOilOrChemtryBean = null;
                        getOilOrChemistry();
                    }
                });
            }
        });

        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                if (getIntent().getStringExtra("manage").equals("1")) {
                    bundle.putString("type", "oil");
                } else {
                    bundle.putString("type", "chem");
                }
                new MyIntent(HomeOilActivity.this).startAct(HomeSeekActivity.class, bundle);
            }
        });
        product();
    }

    /**
     * 关闭筛选
     */
    private void closeSearch() {
        type = 0;
        allClassifyLl.setVisibility(View.GONE);
        productTv.setTextColor(0xff979797);
        typeTv.setTextColor(0xff979797);
        scopeTv.setTextColor(0xff979797);
        Drawable drawable = getResources().getDrawable(R.drawable.home_down_no_check);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        productTv.setCompoundDrawablesRelative(null, null, drawable, null);
        typeTv.setCompoundDrawablesRelative(null, null, drawable, null);
        scopeTv.setCompoundDrawablesRelative(null, null, drawable, null);
    }

    private void getOilOrChemistry() {
        oilThread = new SimplifyThread(UrlUtil.TOGOODS_INDEX, oilMap);
        oilThread.setOnKeyResultListener(new SimplifyThread.OnResultListener() {
            @Override
            public void resultBody(String res) {
                oilOrChemtryBean = JsonUtil.oilOrChemtryBeanResolve(res);
                if (oldOilOrChemtryBean == null || oldOilOrChemtryBean.getData() == null) {
                    oldOilOrChemtryBean = oilOrChemtryBean;
                } else {
                    if (oilOrChemtryBean != null && oilOrChemtryBean.getData() != null
                            && oilOrChemtryBean.getData().getData() != null
                            && oilOrChemtryBean.getData().getData().size() != 0) {
                        oldOilOrChemtryBean.getData().getData().addAll(oilOrChemtryBean.getData().getData());
                    }
                }
                handler.sendEmptyMessage(1);
            }

            @Override
            public void errorBody(String error) {

            }
        });
    }

    /**
     * 产品分类
     */
    private void product() {

        SimplifyThread simplifyThread = new SimplifyThread(UrlUtil.URL_PRODUCT_GOODSCLASS, new HashMap<String, String>());
        simplifyThread.setOnKeyResultListener(new SimplifyThread.OnResultListener() {
            @Override
            public void resultBody(String res) {
                Message message = new Message();
                message.obj = res;
                message.what = 3;
                handler.sendMessage(message);
            }

            @Override
            public void errorBody(String error) {
                handler.sendEmptyMessage(MessageWhat.REQUST_ERROR);
            }
        });
    }


    class ListAdapter extends BaseAdapter {

        private ArrayList<String> list;
        // 几级listview
        private int lev;
        // 点击位置
        private int clickLoc = 0;
        public String contant = "";

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
                view = LayoutInflater.from(HomeOilActivity.this).inflate(R.layout.item_home_oil, viewGroup, false);
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
                case 3:
                    if (clickLoc == i) {
                        vh.nameTv.setTextColor(0xffFC4F4F);
                        vh.lineV.setVisibility(View.VISIBLE);
                    } else {
                        vh.nameTv.setTextColor(0xff545454);
                        vh.lineV.setVisibility(View.INVISIBLE);
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

    class OilOrChemtryAdapter extends BaseAdapter {

        private OilOrChemtryBean bean;

        public void setBean(OilOrChemtryBean bean) {
            this.bean = bean;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return bean != null && bean.getData() != null && bean.getData().getData().size() != 0 ? bean.getData().getData().size() : 0;
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
            OilOrChemtryViewHolder vh;
            if (view == null) {
                view = LayoutInflater.from(HomeOilActivity.this).inflate(R.layout.item_msg_home_product, viewGroup, false);
                vh = new OilOrChemtryViewHolder();
                vh.iconIv = view.findViewById(R.id.item_msg_home_product_icon_iv);
                vh.titleTv = view.findViewById(R.id.item_msg_home_product_title_tv);
                vh.priceTv = view.findViewById(R.id.item_msg_home_product_price_tv);
                vh.addressTv = view.findViewById(R.id.item_msg_home_product_adress_tv);
                vh.timeTv = view.findViewById(R.id.item_msg_home_product_time_tv);
                vh.tvOrder = view.findViewById(R.id.tv_order);
//                vh.iconIv = view.findViewById(R.id.item_msg_home_product_icon_iv);
                view.setTag(vh);
            } else {
                vh = (OilOrChemtryViewHolder) view.getTag();
            }
            final OilOrChemtryBean.DataBeanX.DataBean dataBean = bean.getData().getData().get(i);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    if () {
                    Intent intent = new Intent(HomeOilActivity.this, PlatformIssueOrderActivity.class);
                    intent.putExtra("classid", getIntent().getStringExtra("manage"));
                    intent.putExtra("id", dataBean.getId() + "");
                    startActivity(intent);
//                    } else {
//                        Intent intent = new Intent(HomeOilActivity.this, );
//                        startActivity(intent);
//                    }
                }
            });

            //3平台,2企业,1个人
            if (dataBean.getStatus() == 3) {
                vh.tvOrder.setVisibility(View.GONE);
                vh.addressTv.setText("平台");

                if (MyShare.getShared().getString("userType", "").equals("2")) {
//                    vh.tvOrder.setVisibility(View.VISIBLE);
                    vh.tvOrder.setVisibility(View.GONE);
                }
            } else if (dataBean.getStatus() == 2) {
                vh.tvOrder.setVisibility(View.GONE);

                if (dataBean.getCompanyname() != null) {
                    vh.addressTv.setText(dataBean.getCompanyname());
                }
            } else if (dataBean.getStatus() == 1) {
                vh.tvOrder.setVisibility(View.GONE);
                vh.addressTv.setText("个人");
            }

            if (dataBean.getImg() != null) {
                Glide.with(HomeOilActivity.this)
                        .load(UrlUtil.IMAGE_URL + dataBean.getImg())
                        .placeholder(R.mipmap.default_goods)
                        .error(R.mipmap.default_goods)
                        .into(vh.iconIv);
            }
            if (dataBean.getTitle() != null) {
                vh.titleTv.setText(dataBean.getTitle());
            }
            if (dataBean.getPrice() != null) {
                vh.priceTv.setText("单价：" + dataBean.getPrice() + "元/吨");
            }

            if (dataBean.getTime() != 0) {
                vh.timeTv.setText(DateTimeUtil.stampToDate("yyyy/MM/dd HH:mm:ss", dataBean.getTime() + "000"));
            }
            return view;
        }

        class OilOrChemtryViewHolder {
            ImageView iconIv;
            TextView titleTv, priceTv, addressTv, timeTv, tvOrder;
        }
    }
}
