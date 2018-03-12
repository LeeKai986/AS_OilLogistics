package com.zpf.oillogistics.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zpf.oillogistics.R;
import com.zpf.oillogistics.base.BaseActivity;
import com.zpf.oillogistics.base.MessageWhat;
import com.zpf.oillogistics.bean.Newa;
import com.zpf.oillogistics.bean.response.GetOrderResponse;
import com.zpf.oillogistics.bean.response.InformaListResponse;
import com.zpf.oillogistics.net.SimplifyThread;
import com.zpf.oillogistics.net.UrlUtil;
import com.zpf.oillogistics.pulltorefreshlibrary.PullToRefreshListView;
import com.zpf.oillogistics.utils.DateTimeUtil;
import com.zpf.oillogistics.utils.MyShare;
import com.zpf.oillogistics.utils.MyToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/18.
 * <p>
 * 主页资讯
 */

public class HomeInformationActivity extends BaseActivity {

    // 布局相关
    // plv
    @BindView(R.id.home_information_plv)
    PullToRefreshListView plv;
    // adapter
    HomeInformationAdapter adapter;
    @BindView(R.id.rel_back)
    RelativeLayout relBack;

    private List<Newa> msgList = new ArrayList<>();
    private HashMap<String,String> hashMap=new HashMap<>();

    @Override
    protected int setLayout() {
        return R.layout.activity_home_information;
    }

    @Override
    protected void initData() {
        getInformation();
    }

    @Override
    protected void initViewAction(Bundle savedInstanceState) {
        relBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        adapter = new HomeInformationAdapter();
        plv.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    /**
     * 获取订单数据接口
     */
    private void getInformation() {


        SimplifyThread simplifyThread = new SimplifyThread(UrlUtil.URL_SLIDESHOW_NEWS, hashMap);
        simplifyThread.setOnKeyResultListener(new SimplifyThread.OnResultListener() {
            @Override
            public void resultBody(String res) {
                Message message = new Message();
                message.obj = res;
                message.what = MessageWhat.SELF_SLIDESHOW_NEWS;
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
            switch (msg.what) {
                case MessageWhat.SELF_SLIDESHOW_NEWS:
                    if (msg.obj != null) {
                        try {
                            InformaListResponse orderRe = gson.fromJson(msg.obj.toString(), InformaListResponse.class);
                            if(orderRe.getStatus()==0){
                                msgList.addAll(orderRe.getData().getNews().getData());
                                if(msgList==null&&msgList.size()==0){
                                    MyToast.show(HomeInformationActivity.this, "暂无数据");
                                }
                                adapter.notifyDataSetChanged();

                            }else {
                                MyToast.show(HomeInformationActivity.this, orderRe.getMsg());
                            }

                        } catch (Exception e) {
                            MyToast.show(HomeInformationActivity.this, "返回数据异常!");
                        }

                    } else {
                        MyToast.show(HomeInformationActivity.this, "返回数据失败!");
                    }
                    break;
            }
        }
    };

    class HomeInformationAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (msgList!=null)
                return msgList.size();
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
            final MsgHomeSystemViewHolder vh;
            if (view == null) {
                view = LayoutInflater.from(HomeInformationActivity.this).inflate(R.layout.item_home_information, viewGroup, false);
                vh = new MsgHomeSystemViewHolder();
                vh.allLl = view.findViewById(R.id.home_information_all_ll);
                vh.tvTitle = view.findViewById(R.id.tv_title);
                vh.tvTime = view.findViewById(R.id.tv_time);
                view.setTag(vh);
            } else {
                vh = (MsgHomeSystemViewHolder) view.getTag();
            }



            if(msgList.get(i).getTitle()!=null){
                vh.tvTitle.setText(msgList.get(i).getTitle());
            }

            if(msgList.get(i).getTime()!=0){
                vh.tvTime.setText(DateTimeUtil.stampToDate("MM/dd",msgList.get(i).getTime()+"000"));
            }

            vh.allLl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(HomeInformationActivity.this, InformationDetailsActivity.class);
                    intent.putExtra("inforID", msgList.get(i).getId()+"");
                    intent.putExtra("flag", "information");
                    startActivity(intent);
                }
            });
            return view;
        }

        class MsgHomeSystemViewHolder {
            LinearLayout allLl;
            TextView tvTitle,tvTime;
        }
    }
}
