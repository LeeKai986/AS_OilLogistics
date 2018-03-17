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
import com.zpf.oillogistics.bean.response.MyResorceResponse;
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
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/9/14.
 * <p>
 * 发布的资源
 */

public class SelfPublicResorceActivity extends BaseActivity {

    // 布局相关
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.msg_home_product_lv)
    DiySwipeMenuListview productLv;
    @BindView(R.id.rel_back)
    RelativeLayout relBack;

    // adapter
    ProductAdapter adapter;
    private int page=1;

    private List<MyResorceResponse.DataBeanX.DataBean> mList=new ArrayList<>();

    @Override
    protected int setLayout() {
        return R.layout.activity_msg_home_product;
    }

    @Override
    protected void initData() {
        tvTitle.setText("我发布的货源");

        resorce();
    }

    @Override
    protected void initViewAction(Bundle savedInstanceState) {
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
                resorceDel(mList.get(position).getId()+"");
                return false;
            }
        });
        productLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(SelfPublicResorceActivity.this, OrderDetailsActivity.class);
                intent.putExtra("orderID", mList.get(i).getId() + "");
                startActivity(intent);
            }
        });

        productLv.setOnRefreshLoadListener(new DiySwipeMenuListview.OnRefreshLoadListener() {
            @Override
            public void onRefresh() {

                mList.clear();
                page=1;
                resorce();
            }

            @Override
            public void onLoad() {
                page++;
                resorce();
            }
        });

        adapter = new ProductAdapter();
        productLv.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }


    /**
     * 我的资源
     */
    private void resorce() {
        HashMap hp = new HashMap<String, String>();
        hp.put("id", MyShare.getShared().getString("userId",""));
        hp.put("page", page+"");
        SimplifyThread simplifyThread = new SimplifyThread(UrlUtil.URL_FREIGT_SUPPLY, hp);
        simplifyThread.setOnKeyResultListener(new SimplifyThread.OnResultListener() {
            @Override
            public void resultBody(String res) {
                Message message = new Message();
                message.obj = res;
                message.what = MessageWhat.SELF_FREIGT_SUPPLY;
                handler.sendMessage(message);
            }

            @Override
            public void errorBody(String error) {
                handler.sendEmptyMessage(MessageWhat.REQUST_ERROR);
            }
        });
    }

    /**
     * 删除资源
     */
    private void resorceDel(String id) {
        HashMap hp = new HashMap<String, String>();
        hp.put("uid", MyShare.getShared().getString("userId",""));
        hp.put("id",id);
        SimplifyThread simplifyThread = new SimplifyThread(UrlUtil.URL_FREIGT_DELSUPPLY, hp);
        simplifyThread.setOnKeyResultListener(new SimplifyThread.OnResultListener() {
            @Override
            public void resultBody(String res) {
                Message message = new Message();
                message.obj = res;
                message.what = MessageWhat.SELF_FREIGT_DELSUPPLY;
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
                case MessageWhat.SELF_FREIGT_SUPPLY:
                    if (msg.obj != null) {
                        try {
                            MyResorceResponse product = gson.fromJson(msg.obj.toString(), MyResorceResponse.class);

                            if (product.getStatus() == 0) {
                                mList.addAll(product.getData().getData());

                            } else {
                                MyToast.show(SelfPublicResorceActivity.this, "暂无数据!");
                            }

                            if (adapter==null){
                                adapter = new ProductAdapter();
                                productLv.setAdapter(adapter);
                            }else {
                                adapter.notifyDataSetChanged();
                            }

                        } catch (Exception e) {
                            MyToast.show(SelfPublicResorceActivity.this, "返回数据异常!");
                        }

                    } else {
                        MyToast.show(SelfPublicResorceActivity.this, "返回数据失败!");
                    }
                    break;
                case MessageWhat.SELF_FREIGT_DELSUPPLY:
                    if (msg.obj != null) {
                        try {
                            MyResorceResponse product = gson.fromJson(msg.obj.toString(), MyResorceResponse.class);

                            if (product.getStatus() == 0) {
                                mList.clear();
                                page=1;
                                resorce();
                                MyToast.show(SelfPublicResorceActivity.this, "删除成功!");
                            } else {
                                MyToast.show(SelfPublicResorceActivity.this, "暂无数据!");
                            }

                        } catch (Exception e) {
                            MyToast.show(SelfPublicResorceActivity.this, "返回数据异常!");
                        }

                    } else {
                        MyToast.show(SelfPublicResorceActivity.this, "返回数据失败!");
                    }
                    break;
            }
        }
    };

    class ProductAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if(mList!=null)
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
            ProductViewHolder vh;
            if (view == null) {
                view = LayoutInflater.from(SelfPublicResorceActivity.this).inflate(R.layout.item_msg_self_resorce, viewGroup, false);
                vh = new ProductViewHolder(view);
                view.setTag(vh);
            } else {
                vh = (ProductViewHolder) view.getTag();
            }
            MyResorceResponse.DataBeanX.DataBean  resorce= mList.get(i);

            //头像
            if(resorce.getHerder()!=null){
                Glide.with(SelfPublicResorceActivity.this)
                        .load(UrlUtil.IMAGE_URL +resorce.getHerder())
                        .error(R.mipmap.head_default)
                        .into(vh.cirHead);
            }

            //地址
            if(resorce.getStartplace()!=null){
                vh.tvAdress.setText(resorce.getStartplace()+"-"+resorce.getEndplace());
            }

            //货物名称
            if(resorce.getGoodsname()!=null){
                vh.tvGoodsName.setText("货物名称："+resorce.getGoodsname());
            }else {
                vh.tvGoodsName.setText("货物名称：--");
            }

            //货物数量
            if(resorce.getNumber()!=0){
                vh.tvNum.setText("货量："+resorce.getNumber()+"吨");
            }else {
                vh.tvNum.setText("货量：--");
            }

            //单价
            if(resorce.getPrice()!=null){
                vh.tvPrice.setText("单价："+resorce.getPrice()+"元/吨");
            }else {
                vh.tvPrice.setText("单价：--");
            }

            //起运日期
            if(resorce.getTime()!=0){
                vh.tvTime.setText("起运日期："+ DateTimeUtil.stampToDate("yyyy/MM/dd",resorce.getTime()+"000"));
            }else {
                vh.tvTime.setText("起运日期：--");
            }

            return view;
        }


         class ProductViewHolder {
            @BindView(R.id.cir_head_resorce_adapter)
            CircleImageView cirHead;
            @BindView(R.id.tv_adress_resorce_adapter)
            TextView tvAdress;
            @BindView(R.id.tv_goodsName_resorce_adapter)
            TextView tvGoodsName;
            @BindView(R.id.tv_num_resorce_adapter)
            TextView tvNum;
            @BindView(R.id.tv_price_resorce_adapter)
            TextView tvPrice;
            @BindView(R.id.tv_time_resorce_adapter)
            TextView tvTime;

            ProductViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
