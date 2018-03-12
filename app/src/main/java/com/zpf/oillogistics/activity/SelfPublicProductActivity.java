package com.zpf.oillogistics.activity;

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
import com.zpf.oillogistics.base.MessageWhat;
import com.zpf.oillogistics.bean.response.MyProductResponse;
import com.zpf.oillogistics.diy.DiySwipeMenuListview;
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
 * 发布的产品
 */

public class SelfPublicProductActivity extends BaseActivity {

    // 布局相关
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.msg_home_product_lv)
    DiySwipeMenuListview productLv;
    @BindView(R.id.rel_back)
    RelativeLayout relBack;

    // adapter
    ProductAdapter adapter;
    HashMap<String, String> hp = new HashMap<>();

    private int page = 1;

    private List<MyProductResponse.DataBeanX.DataBean> mList = new ArrayList<>();


    @Override
    protected int setLayout() {
        return R.layout.activity_msg_home_product;
    }

    @Override
    protected void initData() {
        indexData();
    }

    @Override
    protected void initViewAction(Bundle savedInstanceState) {
        tvTitle.setText("发布的产品");

        relBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        productLv.setMenuCreator(new SwipeMenuCreator() {
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
                deleteItem.setTitle("删除");
                deleteItem.setTitleSize(15);
                deleteItem.setTitleColor(Color.WHITE);
//                // set a icon
//                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        });
        productLv.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                delProduct(mList.get(position).getId() + "");
                return false;
            }
        });

        productLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(SelfPublicProductActivity.this, PlatformIssueOrderActivity.class);
                intent.putExtra("classid", mList.get(i).getClassX() + "");
                intent.putExtra("id", mList.get(i).getId() + "");
                startActivity(intent);
            }
        });

        productLv.setOnRefreshLoadListener(new DiySwipeMenuListview.OnRefreshLoadListener() {
            @Override
            public void onRefresh() {
                mList.clear();
                page = 1;
                indexData();
            }

            @Override
            public void onLoad() {
                page++;
                indexData();
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    /**
     * 获取我的发布
     */
    private void indexData() {

        hp.put("id", MyShare.getShared().getString("userId", ""));
        hp.put("page", page + "");
        SimplifyThread simplifyThread = new SimplifyThread(UrlUtil.URL_USER_PRODUCTS, hp);
        simplifyThread.setOnKeyResultListener(new SimplifyThread.OnResultListener() {
            @Override
            public void resultBody(String res) {
                Message message = new Message();
                message.obj = res;
                message.what = MessageWhat.SELF_USER_PRODUCTS;
                handler.sendMessage(message);
            }

            @Override
            public void errorBody(String error) {
                handler.sendEmptyMessage(MessageWhat.REQUST_ERROR);
            }
        });
    }

    /**
     * 删除我的发布
     */
    private void delProduct(String productID) {
        HashMap<String, String> hp = new HashMap<>();
        hp.put("id", productID);
        SimplifyThread simplifyThread = new SimplifyThread(UrlUtil.URL_USER_DEL, hp);
        simplifyThread.setOnKeyResultListener(new SimplifyThread.OnResultListener() {
            @Override
            public void resultBody(String res) {
                Message message = new Message();
                message.obj = res;
                message.what = MessageWhat.SELF_USER_DEL;
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
            productLv.onRLStop();
            switch (msg.what) {
                case MessageWhat.SELF_USER_PRODUCTS:
                    if (msg.obj != null) {
                        try {
                            MyProductResponse product = gson.fromJson(msg.obj.toString(), MyProductResponse.class);

                            if (product.getStatus() == 0 && product.getData().getData() != null) {
                                mList.addAll(product.getData().getData());
                            } else {
                                MyToast.show(SelfPublicProductActivity.this, "暂无数据!");
                            }

                            if (adapter == null) {
                                adapter = new ProductAdapter();
                                productLv.setAdapter(adapter);
                            } else {
                                adapter.notifyDataSetChanged();
                            }

                        } catch (Exception e) {
                            MyToast.show(SelfPublicProductActivity.this, "返回数据异常!");
                        }

                    } else {
                        MyToast.show(SelfPublicProductActivity.this, "返回数据失败!");
                    }
                    break;
                case MessageWhat.SELF_USER_DEL:
                    if (msg.obj != null) {
                        try {
                            MyProductResponse product = gson.fromJson(msg.obj.toString(), MyProductResponse.class);

                            if (product.getStatus() == 0) {
                                page = 1;
                                mList.clear();
                                indexData();
                                MyToast.show(SelfPublicProductActivity.this, "删除成功!");
                            } else {
                                MyToast.show(SelfPublicProductActivity.this, "暂无数据!");
                            }

                        } catch (Exception e) {
                            MyToast.show(SelfPublicProductActivity.this, "返回数据异常!");
                        }

                    } else {
                        MyToast.show(SelfPublicProductActivity.this, "返回数据失败!");
                    }
                    break;
            }
        }
    };

    class ProductAdapter extends BaseAdapter {

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
        public View getView(final int i, View view, ViewGroup viewGroup) {
            ViewHolder vh;
            if (view == null) {
                view = LayoutInflater.from(SelfPublicProductActivity.this)
                        .inflate(R.layout.item_msg_home_product, viewGroup, false);
                vh = new ViewHolder(view);
                view.setTag(vh);
            } else {
                vh = (ViewHolder) view.getTag();
            }

            MyProductResponse.DataBeanX.DataBean product = mList.get(i);
            //1个人,2企业,3司机
            if (product.getStatus() == 1) {
                vh.tvOrder.setVisibility(View.GONE);
                vh.adressTv.setText("个人");

                if (MyShare.getShared().getString("userType", "").equals("2")) {
                    vh.tvOrder.setVisibility(View.VISIBLE);
                }
            } else if (product.getStatus() == 2) {
                vh.tvOrder.setVisibility(View.GONE);

                if (product.getCompanyname() != null) {
                    vh.adressTv.setText(product.getCompanyname());
                }
            } else if (product.getStatus() == 3) {
                vh.tvOrder.setVisibility(View.GONE);
                vh.adressTv.setText("平台");
            }

            //标题
            if (product.getTitle() != null) {
                vh.titleTv.setText(product.getTitle());
            }

            //时间
            if (product.getTime() != 0) {
                vh.timeTv.setText(DateTimeUtil.stampToDate("yyyy/MM/dd HH:mm:ss", product.getTime() + "000"));
            }
            //单价
            if (product.getPrice() != null) {
                vh.priceTv.setText("单价:" + product.getPrice() + "元/吨");
            }

//            //公司
//            if (product.getCompanyname() != null && !product.getCompanyname().equals("")) {
//                vh.adressTv.setText(product.getCompanyname());
//            }

            //头像
            if (product.getImg() != null) {
                Glide.with(SelfPublicProductActivity.this)
                        .load(UrlUtil.IMAGE_URL + product.getImg())
                        .placeholder(R.mipmap.default_goods)
                        .error(R.mipmap.default_goods)
                        .into(vh.iconIv);
            }


            return view;
        }

        class ViewHolder {
            @BindView(R.id.item_msg_home_product_icon_iv)
            ImageView iconIv;
            @BindView(R.id.item_msg_home_product_title_tv)
            TextView titleTv;
            @BindView(R.id.item_msg_home_product_time_tv)
            TextView timeTv;
            @BindView(R.id.item_msg_home_product_price_tv)
            TextView priceTv;
            @BindView(R.id.item_msg_home_product_adress_tv)
            TextView adressTv;
            @BindView(R.id.tv_order)
            TextView tvOrder;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
