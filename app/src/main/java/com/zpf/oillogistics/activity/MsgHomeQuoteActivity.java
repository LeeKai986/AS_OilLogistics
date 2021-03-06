package com.zpf.oillogistics.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zpf.oillogistics.R;
import com.zpf.oillogistics.base.BaseActivity;
import com.zpf.oillogistics.bean.AttenQuoteBean;
import com.zpf.oillogistics.net.JsonUtil;
import com.zpf.oillogistics.net.SimplifyThread;
import com.zpf.oillogistics.net.UrlUtil;
import com.zpf.oillogistics.pulltorefreshlibrary.PullToRefreshBase;
import com.zpf.oillogistics.pulltorefreshlibrary.PullToRefreshListView;
import com.zpf.oillogistics.utils.DateTimeUtil;
import com.zpf.oillogistics.utils.MyToast;
import com.zpf.oillogistics.utils.NormalUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/9/14.
 * <p>
 * 报价信息
 */

public class MsgHomeQuoteActivity extends BaseActivity {

    // 布局相关
    // lv
    @BindView(R.id.msg_home_quote_lv)
    PullToRefreshListView lv;
    @BindView(R.id.rel_back)
    RelativeLayout relBack;
    // adapter
    MsgHomeQuoteAdapter adapter;

    // 数据相关
    // 关注的报价信息
    SimplifyThread quoteThread;
    // 关注的报价信息传参
    HashMap<String, String> quoteMap;
    // 关注的报价信息返回
    AttenQuoteBean attenQuoteBean;
    private List<AttenQuoteBean.DataBean> mList = new ArrayList<>();
    //    Dialog dg;
    private int page = 1;
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            lv.onRefreshComplete();
            switch (message.what) {
                case 0:
                    break;
                case 1:
                    attenQuoteBean = JsonUtil.attenQuoteBeanResolve(message.obj.toString());
                    if (attenQuoteBean != null) {
                        if (attenQuoteBean.getData() != null) {
                            if (attenQuoteBean.getData().size() == 0) {
                                MyToast.show(MsgHomeQuoteActivity.this, "暂无数据");
                                break;
                            }
                            mList.addAll(attenQuoteBean.getData());
                            adapter.setBean(mList);
                            lv.setAdapter(adapter);
                        } else {
                            MyToast.show(MsgHomeQuoteActivity.this, attenQuoteBean.getMsg());
                            adapter.setBean(mList);
                            lv.setAdapter(adapter);
                        }
                    } else {
                        MyToast.show(MsgHomeQuoteActivity.this, "未能获取数据");
                    }
                    break;
            }
            return false;
        }
    });

//    Handler hd = new Handler(new Handler.Callback() {
//        @Override
//        public boolean handleMessage(Message message) {
//            if (dg != null && dg.isShowing()) {
//                dg.dismiss();
//            }
//            switch (message.what) {
//                case 0:
//                    MyToast.show(MsgHomeQuoteActivity.this, message.obj.toString());
//                    break;
//                case 1:
//                    break;
//            }
//            return false;
//        }
//    });

    @Override
    protected int setLayout() {
        return R.layout.activity_msg_home_quote;
    }

    @Override
    protected void initData() {
        getdata();
        lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                mList.clear();
                page = 1;
                getdata();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                getdata();
            }
        });
        lv.setScrollingWhileRefreshingEnabled(true);
        lv.setMode(PullToRefreshBase.Mode.BOTH);
    }

    private void getdata() {
        quoteMap = new HashMap<>();
        quoteMap.put("id", NormalUtils.userId());
        quoteMap.put("page", page + "");
        quoteMap.put("page_size", "20");
        quoteThread = new SimplifyThread(UrlUtil.USER_OFFER, quoteMap);
        quoteThread.setOnKeyResultListener(new SimplifyThread.OnResultListener() {
            @Override
            public void resultBody(String res) {
                Message msg = new Message();
                msg.obj = res;
                msg.what = 1;
                handler.sendMessage(msg);
            }

            @Override
            public void errorBody(String error) {
                Log.i("The Error-->", error);
            }
        });

//        dg = ProgressDialog.show(MsgHomeQuoteActivity.this, "", "获取数据中...");
//        dg.setCanceledOnTouchOutside(false);
//        dg.show();
//        HashMap<String, String> getAttentionNum = new HashMap<>();
//        getAttentionNum.put("id", MyShare.getShared().getString("userId", ""));
//        getAttentionNum.put("type", "3");
//        SimplifyThread getAttentionThread = new SimplifyThread(UrlUtil.URL_USER_NUM, getAttentionNum);
//        getAttentionThread.setOnKeyResultListener(new SimplifyThread.OnResultListener() {
//            @Override
//            public void resultBody(String res) {
//                Message msg = new Message();
//                msg.obj = res;
//                msg.what = 1;
//                hd.sendMessage(msg);
//            }
//
//            @Override
//            public void errorBody(String error) {
//                Message msg = new Message();
//                msg.obj = error;
//                msg.what = 0;
//                hd.sendMessage(msg);
//            }
//        });
    }

    @Override
    protected void initViewAction(Bundle savedInstanceState) {
        relBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(13);
                finish();
            }
        });

        adapter = new MsgHomeQuoteAdapter();
        lv.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 22) {
            initData();
        }
    }

    class MsgHomeQuoteAdapter extends BaseAdapter {

        private List<AttenQuoteBean.DataBean> bean;

        public void setBean(List<AttenQuoteBean.DataBean> bean) {
            this.bean = bean;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return bean != null && bean.size() != 0 ? bean.size() : 0;
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
            MsgHomeQuoteViewHolder vh;
            if (view == null) {
                view = LayoutInflater.from(MsgHomeQuoteActivity.this).inflate(R.layout.item_msg_home_quote, viewGroup, false);
                vh = new MsgHomeQuoteViewHolder();
                vh.titleTv = view.findViewById(R.id.item_msg_home_quote_title_tv);
                vh.contentTv = view.findViewById(R.id.item_msg_home_quote_content_tv);
                vh.timeTv = view.findViewById(R.id.item_msg_home_qoute_time_tv);
                view.setTag(vh);
            } else {
                vh = (MsgHomeQuoteViewHolder) view.getTag();
            }
            final AttenQuoteBean.DataBean dataBean = bean.get(i);
            if (dataBean.getRegion() != null && !dataBean.getRegion().equals("")) {
                vh.titleTv.setText("【" + dataBean.getRegion() + "】");
            }
            if (dataBean.getTitle() != null && !dataBean.getTitle().equals("")) {
                vh.contentTv.setText(dataBean.getTitle());
            }
            if (dataBean.getTime() != null && !dataBean.getTime().equals("")) {
                vh.timeTv.setText(DateTimeUtil.stampToDate("MM/dd", dataBean.getTime() + "000"));
            }
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MsgHomeQuoteActivity.this, InformationDetailsActivity.class);
                    intent.putExtra("inforID", dataBean.getId() + "");
                    intent.putExtra("uid", "");
                    startActivityForResult(intent, 1);
                }
            });
            return view;
        }

        class MsgHomeQuoteViewHolder {
            TextView titleTv, contentTv, timeTv;
        }
    }
}
