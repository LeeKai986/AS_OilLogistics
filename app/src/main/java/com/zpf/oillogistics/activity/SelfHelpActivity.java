package com.zpf.oillogistics.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zpf.oillogistics.R;
import com.zpf.oillogistics.base.BaseActivity;
import com.zpf.oillogistics.base.MessageWhat;
import com.zpf.oillogistics.bean.response.HelpResponse;
import com.zpf.oillogistics.bean.response.MyResorceResponse;
import com.zpf.oillogistics.net.SimplifyThread;
import com.zpf.oillogistics.net.UrlUtil;
import com.zpf.oillogistics.utils.MyShare;
import com.zpf.oillogistics.utils.MyToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/19.
 */

public class SelfHelpActivity extends BaseActivity {
    @BindView(R.id.rel_back_help_self)
    RelativeLayout relBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.lv_help)
    ListView lvHelp;

    private List<HelpResponse.DataBean> mList=new ArrayList();
    private HelpAdapter adapter;

    @Override
    protected int setLayout() {
        return R.layout.activity_help_self;
    }

    @Override
    protected void initData() {
        lvHelp.setAdapter(new HelpAdapter());

        help();
    }

    @Override
    protected void initViewAction(Bundle savedInstanceState) {
        relBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }


    /**
     * 我的help
     */
    private void help() {
        SimplifyThread simplifyThread = new SimplifyThread(UrlUtil.URL_HELP_INDEX, new HashMap<String, String>());
        simplifyThread.setOnKeyResultListener(new SimplifyThread.OnResultListener() {
            @Override
            public void resultBody(String res) {
                Message message = new Message();
                message.obj = res;
                message.what = MessageWhat.SELF_HELP_INDEX;
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
                case MessageWhat.SELF_HELP_INDEX:
                    if (msg.obj != null) {
                        try {
                            HelpResponse help = gson.fromJson(msg.obj.toString(), HelpResponse.class);

                            if (help.getStatus() == 0) {
                                mList = help.getData();

                                adapter = new HelpAdapter();
                                lvHelp.setAdapter(adapter);

                            } else {
                                MyToast.show(SelfHelpActivity.this, "暂无数据!");
                            }

                        } catch (Exception e) {
                            MyToast.show(SelfHelpActivity.this, "返回数据异常!");
                        }

                    } else {
                        MyToast.show(SelfHelpActivity.this, "返回数据失败!");
                    }
                    break;
            }
        }
    };


    class HelpAdapter extends BaseAdapter {

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
            final HelpViewHolder vh;
            if (view == null) {
                view = LayoutInflater.from(SelfHelpActivity.this).inflate(R.layout.item_msg_self_help, viewGroup, false);
                vh = new HelpViewHolder(view);
                view.setTag(vh);
            } else {
                vh = (HelpViewHolder) view.getTag();
            }

            HelpResponse.DataBean help=mList.get(i);

            //标题
            if (help.getTitle()!=null)
                vh.tvTitleHelp.setText(help.getTitle());

            //内容
            if(help.getContnet()!=null)
                vh.tvMsgHelp.setText(help.getContnet());


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(vh.tvMsgHelp.getVisibility()==View.GONE)
                        vh.tvMsgHelp.setVisibility(View.VISIBLE);
                    else
                        vh.tvMsgHelp.setVisibility(View.GONE);
                }
            });

            return view;
        }

         class HelpViewHolder {
            @BindView(R.id.tv_title_help)
            TextView tvTitleHelp;
            @BindView(R.id.tv_msg_help)
            TextView tvMsgHelp;

             HelpViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
