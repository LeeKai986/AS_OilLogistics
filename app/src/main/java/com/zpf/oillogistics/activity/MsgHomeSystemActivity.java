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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.zpf.oillogistics.R;
import com.zpf.oillogistics.base.BaseActivity;
import com.zpf.oillogistics.bean.MsgHomeSystemBean;
import com.zpf.oillogistics.bean.NormalBean;
import com.zpf.oillogistics.diy.DiySwipeMenuListview;
import com.zpf.oillogistics.net.JsonUtil;
import com.zpf.oillogistics.net.SimplifyThread;
import com.zpf.oillogistics.net.UrlUtil;
import com.zpf.oillogistics.utils.DateTimeUtil;
import com.zpf.oillogistics.utils.MyShare;
import com.zpf.oillogistics.utils.MyToast;

import java.util.HashMap;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/9/13.
 * <p>
 * 系统消息
 */

public class MsgHomeSystemActivity extends BaseActivity {

    // 布局相关
    // 返回
    @BindView(R.id.back_rl)
    RelativeLayout backRl;
    // lv
    @BindView(R.id.msg_home_system_lv)
    DiySwipeMenuListview lv;
    // adapter
    MsgHomeSystemAdapter adapter;

    // 数据相关
    // 系统消息传参
    HashMap<String, String> msgMap;
    // 系统消息线程
    SimplifyThread msgThread;
    // 系统信息页数
    int msgPage = 1;
    // 系统消息, 系统消息保存
    MsgHomeSystemBean msgHomeSystemBean, oldMsgHomeSystemBean;

    // 系统消息删除
    HashMap<String, String> delMsgMap;
    // 删除消息线程
    SimplifyThread delMsgThread;
    // 删除消息返回
    NormalBean delMsgBean;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    MyToast.show(MsgHomeSystemActivity.this, message.obj.toString());
                    break;
                case 1:
                    if (msgHomeSystemBean != null && msgHomeSystemBean.getData() != null && msgHomeSystemBean.getData().getData() != null) {
                        adapter.setBean(oldMsgHomeSystemBean);
                        lv.setAdapter(adapter);
                    } else {
                        adapter.setBean(oldMsgHomeSystemBean);
                        lv.setAdapter(adapter);
                        MyToast.show(MsgHomeSystemActivity.this, "无更多数据");
                    }
                    break;
                case 2:
                    adapter.setBean(oldMsgHomeSystemBean);
                    lv.setAdapter(adapter);
                    MyToast.show(MsgHomeSystemActivity.this, "无更多数据");
                    break;
                case 3:
                    if (delMsgBean != null) {
                        MyToast.show(MsgHomeSystemActivity.this, delMsgBean.getMsg());
                        msgMap = new HashMap<>();
                        oldMsgHomeSystemBean = null;
                        msgPage = 1;
                        getSystemData();
                    } else {
                        MyToast.show(MsgHomeSystemActivity.this, "操作未成功");
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
                    MyToast.show(MsgHomeSystemActivity.this, message.obj.toString());
                    break;
                case 1:
                    break;
            }
            return false;
        }
    });

    @Override
    protected int setLayout() {
        return R.layout.activtiy_msg_home_system;
    }

    @Override
    protected void initData() {
        msgMap = new HashMap<>();
        getSystemData();

        dg = ProgressDialog.show(MsgHomeSystemActivity.this, "", "获取数据中...");
        dg.setCanceledOnTouchOutside(false);
        dg.show();
        HashMap<String, String> getAttentionNum = new HashMap<>();
        getAttentionNum.put("id", MyShare.getShared().getString("userId", ""));
        getAttentionNum.put("type", "1");
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

    @Override
    protected void initViewAction(Bundle savedInstanceState) {
        backRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(11);
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
                deleteItem.setTitle("删除");
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
                switch (index) {
                    case 0:
                        if (delMsgMap != null) {
                            delMsgMap.clear();
                        } else {
                            delMsgMap = new HashMap<String, String>();
                        }
                        //        delMsgMap.put("phone", MyShare.getShared().getString("userPhone", ""));
                        delMsgMap.put("phone", MyShare.getShared().getString("userPhone", ""));
                        delMsgMap.put("id", oldMsgHomeSystemBean.getData().getData().get(position).getId());
                        delMsg();
                        break;
                }
                return false;
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (oldMsgHomeSystemBean.getData().getData().get(i).getOrder() == null ||
                        oldMsgHomeSystemBean.getData().getData().get(i).getOrder().equals("")) {
                    return;
                }
                Intent intent;
                switch (Integer.parseInt(MyShare.getShared().getString("userType", "0"))) {
                    //1个人,2企业,3司机
                    case 1:
                        intent = new Intent(MsgHomeSystemActivity.this, InformationDetailsActivity.class);
                        intent.putExtra("inforID", oldMsgHomeSystemBean.getData().getData().get(i).getOrder());
                        intent.putExtra("uid", MyShare.getShared().getString("userId", ""));
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(MsgHomeSystemActivity.this, SelfOrderDetailsActivity.class);
                        intent.putExtra("orderId", oldMsgHomeSystemBean.getData().getData().get(i).getOrder());
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(MsgHomeSystemActivity.this, SelfDriverOrderDetailsActivity.class);
                        intent.putExtra("orderId", oldMsgHomeSystemBean.getData().getData().get(i).getOrder());
                        startActivity(intent);
                        break;
                    default:
                        MyToast.show(MsgHomeSystemActivity.this, "未能获取身份信息,请重新登陆");
                        break;
                }
            }
        });
        adapter = new MsgHomeSystemAdapter();
//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(MsgHomeSystemActivity.this, SelfOrderDetailsActivity.class);
//                intent.putExtra("orderId", oldMsgHomeSystemBean.getData().getData().get(i).getId());
//                startActivity(intent);
//            }
//        });

        lv.setAdapter(adapter);
    }

    private void getSystemData() {
//        msgMap.put("phone", MyShare.getShared().getString("userPhone", ""));
        msgMap.put("phone", MyShare.getShared().getString("userPhone", ""));
        msgMap.put("page", msgPage + "");
        msgThread = new SimplifyThread(UrlUtil.USER_SYSTEM, msgMap);
        msgThread.setOnKeyResultListener(new SimplifyThread.OnResultListener() {
            @Override
            public void resultBody(String res) {
                msgHomeSystemBean = JsonUtil.msgHomeSystemBeanResolve(res);
                if (oldMsgHomeSystemBean == null || oldMsgHomeSystemBean.getData() == null
                        || oldMsgHomeSystemBean.getData().getData() == null
                        || oldMsgHomeSystemBean.getData().getData().size() == 0) {
                    oldMsgHomeSystemBean = msgHomeSystemBean;
                    handler.sendEmptyMessage(1);
                } else {
                    if (msgHomeSystemBean != null && msgHomeSystemBean.getData() != null
                            && msgHomeSystemBean.getData().getData() != null
                            && msgHomeSystemBean.getData().getData().size() != 0) {
                        oldMsgHomeSystemBean.getData().getData().addAll(msgHomeSystemBean.getData().getData());
                        handler.sendEmptyMessage(1);
                    } else {
                        handler.sendEmptyMessage(2);
                    }
                }
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

    private void delMsg() {
        delMsgThread = new SimplifyThread(UrlUtil.USER_DELS, delMsgMap);
        delMsgThread.setOnKeyResultListener(new SimplifyThread.OnResultListener() {
            @Override
            public void resultBody(String res) {
                delMsgBean = JsonUtil.normalBeanResolve(res);
                handler.sendEmptyMessage(3);
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

    class MsgHomeSystemAdapter extends BaseAdapter {

        MsgHomeSystemBean bean;

        public void setBean(MsgHomeSystemBean bean) {
            this.bean = bean;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return bean != null && bean.getData() != null
                    && bean.getData().getData() != null
                    && bean.getData().getData().size() != 0
                    ? bean.getData().getData().size() : 0;
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
            final MsgHomeSystemViewHolder vh;
            if (view == null) {
                view = LayoutInflater.from(MsgHomeSystemActivity.this).inflate(R.layout.item_msg_home_system, viewGroup, false);
                vh = new MsgHomeSystemViewHolder();
                vh.titleTv = view.findViewById(R.id.item_msg_home_system_title_tv);
                vh.timeTv = view.findViewById(R.id.item_msg_home_system_time_tv);
                vh.contentTv = view.findViewById(R.id.item_msg_home_system_content_tv);
                view.setTag(vh);
            } else {
                vh = (MsgHomeSystemViewHolder) view.getTag();
            }
            final MsgHomeSystemBean.DataBeanX.DataBean dataBean = bean.getData().getData().get(i);
            if (dataBean.getTitle() != null && !dataBean.getTitle().equals("")) {
                vh.titleTv.setText(dataBean.getTitle());
            }
            if (dataBean.getTime() != null && !dataBean.getTime().equals("")) {
                vh.timeTv.setText(DateTimeUtil.stampToDate("MM-dd HH:mm", (Long.parseLong(dataBean.getTime()) * 1000) + ""));
            }
            if (dataBean.getContent() != null && !dataBean.getContent().equals("")) {
                vh.contentTv.setText(dataBean.getContent());
            }
            return view;
        }

        class MsgHomeSystemViewHolder {
            // 标题, 时间, 内容
            TextView titleTv, timeTv, contentTv;
        }
    }
}
