package com.zpf.oillogistics.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
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
import com.zpf.oillogistics.bean.WantBuy;
import com.zpf.oillogistics.bean.response.OilSearchResponse;
import com.zpf.oillogistics.bean.response.WantBuyListResponse;
import com.zpf.oillogistics.diy.SimpleFlowLayout;
import com.zpf.oillogistics.net.SimplifyThread;
import com.zpf.oillogistics.net.UrlUtil;
import com.zpf.oillogistics.pulltorefreshlibrary.PullToRefreshBase;
import com.zpf.oillogistics.pulltorefreshlibrary.PullToRefreshListView;
import com.zpf.oillogistics.utils.DateTimeUtil;
import com.zpf.oillogistics.utils.MyShare;
import com.zpf.oillogistics.utils.MyToast;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/14.
 * <p>
 * 首页搜索
 */

public class ToBuySeekActivity extends BaseActivity {

    // 布局相关
    //返回
    @BindView(R.id.rel_back_seek)
    RelativeLayout relBack;
    //edit
    @BindView(R.id.home_seek_et)
    EditText seekEt;
    //清除
    @BindView(R.id.iv_clear_seek)
    ImageView ivClear;
    // 流布局
    @BindView(R.id.home_seek_sfl)
    SimpleFlowLayout sfl;
    //历史搜索
    @BindView(R.id.lin_history_seek)
    LinearLayout linHistory;
    // lv
    @BindView(R.id.home_seek_lv)
    PullToRefreshListView lv;
    @BindView(R.id.tv_search)
    TextView tvSearch;

    private static final String HISTORY_SEARCH="HISTORY_SEARCH_TOBUY";

    //请求HashMap
    HashMap<String, String> hp = new HashMap<>();
    HomeSeelAdapter adapter;
    String searchStr = "";
    ArrayList<WantBuy> mList = new ArrayList<>();

    //列表页
    int upPage = 1;

    @Override
    protected int setLayout() {
        return R.layout.activity_home_seek;
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
        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mList.clear();
                searchStr = seekEt.getText().toString();
                upPage = 1;
                search();
            }
        });

        ivClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyShare.get(ToBuySeekActivity.this).remove(HISTORY_SEARCH);
                sfl.removeAllViews();
            }
        });

        lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
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

        lv.setScrollingWhileRefreshingEnabled(true);
        lv.setMode(PullToRefreshBase.Mode.BOTH);


        sflAddView();

    }

    /**
     * 搜索接口
     */
    private void search() {

        hp.put("key", searchStr);
        hp.put("page", upPage+"");

        SimplifyThread simplifyThread = new SimplifyThread(UrlUtil.URL_TOUBUY_TOBUYSEARCH, hp);
        simplifyThread.setOnKeyResultListener(new SimplifyThread.OnResultListener() {
            @Override
            public void resultBody(String res) {
                Message message = new Message();
                message.obj = res;
                message.what = MessageWhat.TOUBUY_SEARCH;
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
            lv.onRefreshComplete();
            switch (msg.what) {
                case MessageWhat.TOUBUY_SEARCH:
                    if (msg.obj != null) {
                        try {
                            WantBuyListResponse oil = gson.fromJson(msg.obj.toString(), WantBuyListResponse.class);

                            if (oil.getStatus() == 0) {
                                linHistory.setVisibility(View.GONE);
                                lv.setVisibility(View.VISIBLE);
                                save();

                                mList.addAll(oil.getData().getData());

                            } else {
                                MyToast.show(ToBuySeekActivity.this, "暂无数据!");
                            }

                            if (adapter == null) {
                                adapter = new HomeSeelAdapter();
                                lv.setAdapter(adapter);
                            } else {
                                adapter.notifyDataSetChanged();
                            }

                        } catch (Exception e) {
                            MyToast.show(ToBuySeekActivity.this, "返回数据异常!");
                        }


                    } else {
                        MyToast.show(ToBuySeekActivity.this, "返回数据失败!");
                    }
                    break;
            }
        }
    };

    private void save() {
        String his = MyShare.getShared().getString(HISTORY_SEARCH, ",");
        if (!searchStr.equals("") && !his.contains("," + searchStr + ",")) {
            his += (searchStr + ",");
            MyShare.get(ToBuySeekActivity.this).putString(HISTORY_SEARCH, his);
        }
    }

    private void sflAddView() {
        String his = MyShare.getShared().getString(HISTORY_SEARCH, "");
        if (!his.equals("")) {
            ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.leftMargin = 10;
            lp.rightMargin = 10;
            lp.topMargin = 10;
            lp.bottomMargin = 10;

            his = his.substring(1, his.length() - 1);
            String[] history = his.split(",");

            for (int i = 0; i < history.length; i++) {
                sfl.addView(createView(history[i]), lp);
            }
        }
    }

    private TextView createView(String str) {
        TextView view = new TextView(ToBuySeekActivity.this);
        view.setText(str.trim());
        view.setTextSize(15);
        view.setTextColor(0xff565656);
        view.setBackgroundResource(R.drawable.frame_black);
        view.setPadding(20, 20, 20, 20);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchStr=((TextView)view).getText().toString();
                search();
            }
        });
        return view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    class HomeSeelAdapter extends BaseAdapter {

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
                view = LayoutInflater.from(ToBuySeekActivity.this).inflate(R.layout.item_want_buy_main, viewGroup, false);
                vh = new ViewHolder(view);
                view.setTag(vh);
            } else {
                vh = (ViewHolder) view.getTag();
            }

            final WantBuy buy = (WantBuy) getItem(i);
            if (buy.getImg() != null) {
                Glide.with(ToBuySeekActivity.this)
                        .load(UrlUtil.IMAGE_URL + buy.getImg())
                        .into(vh.ivIcon);
            }
            if (buy.getTitle() != null) {
                vh.tvTitle.setText(buy.getTitle());
            }

            //2企业,1个人,3平台
            if(buy.getIs_user()==3) {
                vh.tvName.setText("平台");

            }else if(buy.getIs_user()==2){
                if (buy.getName() != null) {
                    vh.tvName.setText(buy.getName());
                }
            }else{
                vh.tvName.setText("个人");
            }

            if (buy.getNumber() != null) {
                vh.tvNum.setText("求购数量:" + buy.getNumber() + "吨");
            }

            if(buy.getTime()!=0){
                vh.tvTime.setText(DateTimeUtil.stampToDate("yyyy/MM/dd HH:mm:ss",buy.getTime()+"000"));
            }

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ToBuySeekActivity.this, FirmDetailsActivity.class);
                    intent.putExtra("inforId", buy.getId()+"");
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
