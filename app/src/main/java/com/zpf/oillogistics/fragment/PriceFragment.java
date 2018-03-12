package com.zpf.oillogistics.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zpf.oillogistics.R;
import com.zpf.oillogistics.activity.InformationDetailsActivity;
import com.zpf.oillogistics.activity.PriceSeekActivity;
import com.zpf.oillogistics.base.CyApplication;
import com.zpf.oillogistics.base.MessageWhat;
import com.zpf.oillogistics.bean.ProviceAndCityBean;
import com.zpf.oillogistics.bean.QuoteBean;
import com.zpf.oillogistics.bean.response.ProductClassResponsse;
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
 * Created by Administrator on 2017/9/13.
 * <p>
 * 报价
 */

public class PriceFragment extends Fragment {

    // 布局相关
    // 搜索
    @BindView(R.id.frag_price_mian_seek_iv)
    ImageView seekIv;
    // lv
    @BindView(R.id.frag_price_main_plv)
    PullToRefreshListView plv;
    // adapter
    PriceAdapter adapter;
    View view;
    // 区域筛选
    @BindView(R.id.home_want_buy_scope_tv)
    TextView scopeTv;
    // 商品
    @BindView(R.id.home_want_buy_product_tv)
    TextView productTv;
    // 筛选界面ll
    @BindView(R.id.home_want_buy_classify_ll)
    LinearLayout allClassifyLl;
    // 主级
    @BindView(R.id.home_want_buy_first_lv)
    ListView firstLv;
    // 次级
    @BindView(R.id.home_want_buy_last_lv)
    ListView lastLv;
    // 双List的fl
    @BindView(R.id.home_want_buy_twe_list_fl)
    FrameLayout tweListFl;
    @BindView(R.id.ll_diyu)
    LinearLayout ll_diyu;
    @BindView(R.id.ll_chanpin)
    LinearLayout ll_chanpin;

    // 类型选择记录
    int type = 0;

    // 数据相关
    // 报价
    SimplifyThread quoteThread;
    // 报价传参
    HashMap<String, String> quoteMap;
    // 报价返回
    QuoteBean quoteBean;
    // 报价存储
    QuoteBean oldQuoteBean;
    private String[] cityClickStr = null;
    private int page = 1;

    private int pro = 0;
    private int city = 0;
    private int oil = 0;
    private int oilModel = 0;
    List<ProductClassResponsse.DataBean> firstList;
    List<ProductClassResponsse.DataBean.ChildBean> secondList;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            plv.onRefreshComplete();
            switch (message.what) {
                case 0:
                    if (message.obj != null)
                        MyToast.show(CyApplication.getCyContext(), message.obj.toString());
                    break;
                case 1:
                    if (quoteBean != null && quoteBean.getData() != null
                            && quoteBean.getData().getData() != null && quoteBean.getData().getData().size() != 0) {
                        adapter.setBean(oldQuoteBean);
                    } else if (quoteBean != null) {
                        MyToast.show(CyApplication.getCyContext(), quoteBean.getMsg());
                        adapter.setBean(oldQuoteBean);
                    } else {
                        MyToast.show(CyApplication.getCyContext(), "未能获取数据");
                    }

                    break;
                case 2:
                    break;
                case 3:
                    if (message.obj != null) {
                        try {
                            ProductClassResponsse product = new Gson().fromJson(message.obj.toString(), ProductClassResponsse.class);

                            if (product.getStatus() == 0) {
                                firstList = product.getData();
                                productNames = new ArrayList<String>();
                                for (int i = 0; firstList != null && i < firstList.size(); i++) {
                                    productNames.add(firstList.get(i).getName());
                                }
                                productTypes = new ArrayList<>();
                                if (firstList != null)
                                    secondList = firstList.get(oil).getChild();
                                for (int i = 0; secondList != null && i < secondList.size(); i++) {
                                    productTypes.add(secondList.get(i).getName());
                                }
                                firstLvClickStr2 = new String[]{productNames.get(0)};
                            } else {
                                MyToast.show(CyApplication.getCyContext(), "暂无产品分类数据!");
                            }

                        } catch (Exception e) {
                            MyToast.show(CyApplication.getCyContext(), "返回数据异常!");
                        }

                    } else {
                        MyToast.show(CyApplication.getCyContext(), "返回数据失败!");
                    }
                    break;
            }
            return false;
        }
    });
    private ArrayList<String> provinces;
    private ArrayList<String> citys;
    private ArrayList<String> productNames;
    private ArrayList<String> productTypes;
    private String[] firstLvClickStr;
    private String[] firstLvClickStr2;
    private ArrayList<ProviceAndCityBean> proviceAndCityBeen;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_price_main, container, false);
        ButterKnife.bind(this, view);
        initView();
        initData();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initData() {
        proviceAndCityBeen = JsonUtil.proviceAndCityBeensResolve(getResources().getString(R.string.province_city));
        provinces = new ArrayList<String>();
        for (int i = 0; i < proviceAndCityBeen.size(); i++) {
            provinces.add(proviceAndCityBeen.get(i).getPro());
        }
        citys = new ArrayList<String>();
        for (int i = 0; i < proviceAndCityBeen.get(pro).getCitys().size(); i++) {
            citys.add(proviceAndCityBeen.get(pro).getCitys().get(i).getCname());
        }
        firstLvClickStr = new String[]{provinces.get(0)};


        page = 1;
        oldQuoteBean = null;
        quote();
        product();
    }

    private void initView() {

        quoteMap = new HashMap<>();

        tweListFl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeSearch();
            }
        });

        plv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
                oldQuoteBean = null;
                quote();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                quote();
            }
        });
        adapter = new PriceAdapter();
        plv.setScrollingWhileRefreshingEnabled(true);
        plv.setMode(PullToRefreshBase.Mode.BOTH);
        plv.setAdapter(adapter);

        seekIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), PriceSeekActivity.class));
            }
        });

        ll_diyu.setOnClickListener(new View.OnClickListener() {
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
                quoteMap.clear();
                firstLv.setVisibility(View.VISIBLE);
                final ListAdapter firstAdapter = new ListAdapter();
                final ListAdapter lastAdapter = new ListAdapter();
                firstAdapter.setLev(1);
                lastAdapter.setLev(2);
                firstAdapter.setList(provinces);
                firstAdapter.setClickLoc(pro);
                lastAdapter.setList(citys);
                lastAdapter.setClickLoc(city);
                firstLv.setAdapter(firstAdapter);
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
                        lastAdapter.setClickLoc(0);
                        firstLvClickStr[0] = provinces.get(i);
                    }
                });
                lastLv.setAdapter(lastAdapter);
                lastLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        lastAdapter.setClickLoc(i);
                        city = i;
                        closeSearch();
                        quoteMap.put("area", firstLvClickStr[0] + "-" + citys.get(i));
                        oldQuoteBean = null;
                        scopeTv.setText(firstLvClickStr[0] + citys.get(i));
                        page = 1;
                        quote();
                    }
                });
            }
        });

        ll_chanpin.setOnClickListener(new View.OnClickListener() {
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
                firstLv.setVisibility(View.VISIBLE);
                final ListAdapter firstAdapter = new ListAdapter();
                final ListAdapter lastAdapter = new ListAdapter();
                firstAdapter.setLev(1);
                lastAdapter.setLev(2);
//                String[] productNameArr = getResources().getStringArray(R.array.product_one_type);
//                final String[] productTypeArr = getResources().getStringArray(R.array.product_twe_type);

                firstAdapter.setList(productNames);
                firstAdapter.setClickLoc(oil);
                lastAdapter.setList(productTypes);
                lastAdapter.setClickLoc(oilModel);
                firstLv.setAdapter(firstAdapter);
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
                        firstLvClickStr2[0] = productNames.get(i);
                    }
                });
                lastLv.setAdapter(lastAdapter);
                lastLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        lastAdapter.setClickLoc(i);
                        oilModel = i;
                        closeSearch();
                        quoteMap.put("classid", firstLvClickStr2[0] + "-" + productTypes.get(i));
                        oldQuoteBean = null;
                        productTv.setText(firstLvClickStr2[0] + productTypes.get(i));
                        page = 1;
                        quote();
                    }
                });
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

    private void quote() {
        quoteMap.put("page", page + "");
        quoteMap.put("page_size", "20");
        quoteThread = new SimplifyThread(UrlUtil.OFFER_INDEX, quoteMap);
        quoteThread.setOnKeyResultListener(new SimplifyThread.OnResultListener() {
            @Override
            public void resultBody(String res) {
                quoteBean = JsonUtil.quoteBeanResolve(res);
                if (oldQuoteBean == null || oldQuoteBean.getData() == null || oldQuoteBean.getData().getData() == null
                        || oldQuoteBean.getData().getData().size() == 0) {
                    oldQuoteBean = quoteBean;
                } else {
                    if (quoteBean != null && quoteBean.getData() != null && quoteBean.getData().getData() != null) {
                        oldQuoteBean.getData().getData().addAll(quoteBean.getData().getData());
                    }
                }
                handler.sendEmptyMessage(1);
            }

            @Override
            public void errorBody(String error) {
                Message msg = new Message();
                msg.what = 0;
                msg.obj = error;
                handler.sendMessage(msg);
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
            return list == null ? 0 : list.size();
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
                view = LayoutInflater.from(getActivity()).inflate(R.layout.item_home_oil, viewGroup, false);
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

    class PriceAdapter extends BaseAdapter {

        private QuoteBean bean;

        public void setBean(QuoteBean bean) {
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
            PriceViewHolder vh;
            if (view == null) {
                view = LayoutInflater.from(getActivity()).inflate(R.layout.item_msg_home_quote, viewGroup, false);
                vh = new PriceViewHolder();
                vh.titleTv = view.findViewById(R.id.item_msg_home_quote_title_tv);
                vh.contentTv = view.findViewById(R.id.item_msg_home_quote_content_tv);
                vh.timeTv = view.findViewById(R.id.item_msg_home_qoute_time_tv);
                view.setTag(vh);
            } else {
                vh = (PriceViewHolder) view.getTag();
            }
            final QuoteBean.DataBeanX.DataBean dataBean = bean.getData().getData().get(i);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), InformationDetailsActivity.class);
                    intent.putExtra("inforID", dataBean.getId() + "");
                    intent.putExtra("uid", dataBean.getUid() + "");
                    startActivity(intent);
                }
            });
            if (dataBean.getCompanyname() != null && !dataBean.getCompanyname().equals("")) {
                vh.titleTv.setText("【" + dataBean.getRegion() + "】");
            }
            if (dataBean.getTitle() != null && !dataBean.getTitle().equals("")) {
                vh.contentTv.setText(dataBean.getTitle());
            }
            if (dataBean.getTime() != null && !dataBean.getTime().equals("")) {
                vh.timeTv.setText(DateTimeUtil.stampToDate("MM/dd", (Long.parseLong(dataBean.getTime()) * 1000) + ""));
            }
            return view;
        }

        class PriceViewHolder {
            TextView titleTv, contentTv, timeTv;
        }
    }
}
