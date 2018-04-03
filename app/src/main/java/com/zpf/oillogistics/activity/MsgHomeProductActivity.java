package com.zpf.oillogistics.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.zpf.oillogistics.R;
import com.zpf.oillogistics.base.BaseActivity;
import com.zpf.oillogistics.bean.AttenProductBean;
import com.zpf.oillogistics.bean.response.ObjectResponse;
import com.zpf.oillogistics.diy.DiySwipeMenuListview;
import com.zpf.oillogistics.net.JsonUtil;
import com.zpf.oillogistics.net.SimplifyThread;
import com.zpf.oillogistics.net.UrlUtil;
import com.zpf.oillogistics.utils.DateTimeUtil;
import com.zpf.oillogistics.utils.MyIntent;
import com.zpf.oillogistics.utils.MyShare;
import com.zpf.oillogistics.utils.MyToast;
import com.zpf.oillogistics.utils.NormalUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/9/14.
 * <p>
 * 关注产品信息
 */

public class MsgHomeProductActivity extends BaseActivity {

    // 布局相关
    // lv
    @BindView(R.id.msg_home_product_lv)
    DiySwipeMenuListview lv;
    @BindView(R.id.rel_back)
    RelativeLayout relBack;
    // adapter
    MsgHomeProductAdapter adapter;

    // 数据相关
    // 关注的产品信息
    SimplifyThread productThread;
    // 关注的产品信息传参
    HashMap<String, String> productMap;
    // 关注的产品信息返回
    AttenProductBean attenProductBean;

    private List<AttenProductBean.DataBeanX.DataBean> mList = new ArrayList<>();

    private int page = 1;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            lv.onRLStop();
            switch (message.what) {
                case 0:
                    break;
                case 1:
                    if (attenProductBean != null) {
                        if (attenProductBean.getData() != null) {
                            if (attenProductBean.getData().getData().size() == 0) {
                                MyToast.show(MsgHomeProductActivity.this, "暂无数据");
                                break;
                            }
                            mList.addAll(attenProductBean.getData().getData());
                        } else {
                            MyToast.show(MsgHomeProductActivity.this, attenProductBean.getMsg());
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        MyToast.show(MsgHomeProductActivity.this, "未能获取数据");
                    }
                    break;

                case 2:
                    if (message.obj != null) {
                        try {
                            ObjectResponse product = new Gson().fromJson(message.obj.toString(), ObjectResponse.class);

                            if (product.getStatus() == 0) {
                                page = 1;
                                mList.clear();
                                getData();
                                MyToast.show(MsgHomeProductActivity.this, "已取消关注!");
                            } else {
                                MyToast.show(MsgHomeProductActivity.this, product.getMsg());
                            }

                        } catch (Exception e) {
                            MyToast.show(MsgHomeProductActivity.this, "返回数据异常!");
                        }

                    } else {
                        MyToast.show(MsgHomeProductActivity.this, "返回数据失败!");
                    }
                    break;
            }
            return false;
        }
    });

    Dialog dg;

    Handler hd = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if (dg != null && dg.isShowing()) {
                dg.dismiss();
            }
            switch (message.what) {
                case 0:
                    MyToast.show(MsgHomeProductActivity.this, message.obj.toString());
                    break;
                case 1:
                    break;
            }
            return false;
        }
    });

    @Override
    protected int setLayout() {
        return R.layout.activity_msg_home_product;
    }

    @Override
    protected void initData() {
        getData();

        if (getIntent().getBundleExtra(MyIntent.BUNDLE) == null || getIntent().getBundleExtra(MyIntent.BUNDLE).getString("isMsg", "").equals("")) {
            dg = ProgressDialog.show(MsgHomeProductActivity.this, "", "获取数据中...");
            dg.setCanceledOnTouchOutside(false);
            dg.show();
            HashMap<String, String> getAttentionNum = new HashMap<>();
            getAttentionNum.put("id", MyShare.getShared().getString("userId", ""));
            getAttentionNum.put("type", "2");
            SimplifyThread getAttentionThread = new SimplifyThread(UrlUtil.URL_USER_NUM, getAttentionNum);
            getAttentionThread.setOnKeyResultListener(new SimplifyThread.OnResultListener() {
                @Override
                public void resultBody(String res) {
                    Message msg = new Message();
                    msg.obj = res;
                    msg.what = 1;
                    hd.sendMessage(msg);
                }

                @Override
                public void errorBody(String error) {
                    Message msg = new Message();
                    msg.obj = error;
                    msg.what = 0;
                    hd.sendMessage(msg);
                }
            });
        }
    }

    @Override
    protected void initViewAction(Bundle savedInstanceState) {

        relBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(12);
                finish();
            }
        });

        lv.setMenuCreator(new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xff,
                        0x4f, 0x4f)));
                // set item width
                deleteItem.setWidth(200);
                deleteItem.setTitle("取消\n关注");
                deleteItem.setTitleSize(15);
                deleteItem.setTitleColor(Color.WHITE);
//                // set a icon
//                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        });
        lv.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                delData(mList.get(position).getUid() + "");
                return false;
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MsgHomeProductActivity.this, PlatformIssueOrderActivity.class);
                intent.putExtra("classid", mList.get(i).getClassX() + "");
                intent.putExtra("id", mList.get(i).getId() + "");
                startActivity(intent);
            }
        });

        lv.setOnRefreshLoadListener(new DiySwipeMenuListview.OnRefreshLoadListener() {
            @Override
            public void onRefresh() {
                mList.clear();
                page = 1;
                getData();
            }

            @Override
            public void onLoad() {
                page++;
                getData();
            }
        });

        adapter = new MsgHomeProductAdapter();
        lv.setAdapter(adapter);
    }

    /**
     * 获取数据
     */
    private void getData() {
        productMap = new HashMap<>();
        productMap.put("id", NormalUtils.userId());
        productMap.put("page", page + "");
        productMap.put("page_size", "10");
        productThread = new SimplifyThread(UrlUtil.USER_FOLLOW, productMap);
        productThread.setOnKeyResultListener(new SimplifyThread.OnResultListener() {
            @Override
            public void resultBody(String res) {
                attenProductBean = JsonUtil.attenProductBeanResolve(res);
                handler.sendEmptyMessage(1);
            }

            @Override
            public void errorBody(String error) {
                handler.sendEmptyMessage(0);
            }
        });

    }

    /**
     * 取消关注
     */
    private void delData(String id) {
        HashMap hp = new HashMap<>();
        hp.put("id", NormalUtils.userId());
        hp.put("source", id);
        hp.put("status", "1");
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

    class MsgHomeProductAdapter extends BaseAdapter {

        public AttenProductBean bean;

        public void setBean(AttenProductBean bean) {
            notifyDataSetChanged();
            this.bean = bean;
        }

        @Override
        public int getCount() {
            if (mList != null)
                return mList.size();
            return 0;
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
            MsgHomeProductViewHolder vh;
            if (view == null) {
                view = LayoutInflater.from(MsgHomeProductActivity.this).inflate(R.layout.item_msg_home_product, viewGroup, false);
                vh = new MsgHomeProductViewHolder();
                vh.iconIv = view.findViewById(R.id.item_msg_home_product_icon_iv);
                vh.titleTv = view.findViewById(R.id.item_msg_home_product_title_tv);
                vh.timeTv = view.findViewById(R.id.item_msg_home_product_time_tv);
                vh.priceTv = view.findViewById(R.id.item_msg_home_product_price_tv);
                vh.addressTv = view.findViewById(R.id.item_msg_home_product_adress_tv);
                vh.tvOrder = view.findViewById(R.id.tv_order);
                view.setTag(vh);
            } else {
                vh = (MsgHomeProductViewHolder) view.getTag();
            }
            final AttenProductBean.DataBeanX.DataBean dataBean = mList.get(i);
            if (dataBean.getImg() != null && !dataBean.getImg().equals("")) {
                Glide.with(MsgHomeProductActivity.this)
                        .load(UrlUtil.IMAGE_URL + dataBean.getImg())
                        .error(R.mipmap.default_goods)
                        .into(vh.iconIv);
            }
            if (dataBean.getTitle() != null && !dataBean.getTitle().equals("")) {
                vh.titleTv.setText(dataBean.getTitle());
            }
            if (dataBean.getTime() != null && !dataBean.getTime().equals("")) {
                vh.timeTv.setText(DateTimeUtil.stampToDate("yyyy/MM/dd HH:mm:ss", dataBean.getTime() + "000"));
            }
            if (dataBean.getPrice() != null && !dataBean.getPrice().equals("")) {
                vh.priceTv.setText("单价:" + dataBean.getPrice() + "元/吨");
            }
            if (dataBean.getAddress() != null && !dataBean.getAddress().equals("")) {
                vh.addressTv.setText(dataBean.getAddress());
            }

            //status:1平台,2企业,3个人
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
            }
            if (dataBean.getStatus() == 1) {
                vh.tvOrder.setVisibility(View.GONE);
                vh.addressTv.setText("个人");
            }

            return view;
        }

        class MsgHomeProductViewHolder {
            ImageView iconIv;
            TextView titleTv, timeTv, priceTv, addressTv, tvOrder;
        }
    }
}
