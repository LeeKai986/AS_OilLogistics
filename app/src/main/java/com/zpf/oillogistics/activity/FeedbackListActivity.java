package com.zpf.oillogistics.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.zpf.oillogistics.R;
import com.zpf.oillogistics.adapter.FeedbackListAdapter;
import com.zpf.oillogistics.base.BaseActivity;
import com.zpf.oillogistics.base.MessageWhat;
import com.zpf.oillogistics.bean.FeedBack;
import com.zpf.oillogistics.bean.response.BackListResponse;
import com.zpf.oillogistics.bean.response.SelfChangeResponse;
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

public class FeedbackListActivity extends BaseActivity {
    @BindView(R.id.lv_feedback_list)
    ListView lv;
    @BindView(R.id.rel_back)
    RelativeLayout relBack;

    private List<FeedBack> mList=new ArrayList<>();
    private FeedbackListAdapter adapter;

    @Override
    protected int setLayout() {
        return R.layout.activity_feedback_list;
    }

    @Override
    protected void initData() {
        indexData();
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
     * 获取我的反馈
     */
    private void indexData() {
        HashMap hp = new HashMap<String, String>();
        hp.put("id", MyShare.getShared().getString("userId", ""));
        SimplifyThread simplifyThread = new SimplifyThread(UrlUtil.URL_FEEDBACK_BACKD, hp);
        simplifyThread.setOnKeyResultListener(new SimplifyThread.OnResultListener() {
            @Override
            public void resultBody(String res) {
                Message message = new Message();
                message.obj = res;
                message.what = MessageWhat.SELF_FEEDBACK_BACKD;
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
                case MessageWhat.SELF_FEEDBACK_BACKD:
                    if (msg.obj != null) {
                        try {
                            BackListResponse back = gson.fromJson(msg.obj.toString(), BackListResponse.class);

                            if (back.getStatus() == 0) {
                                mList=back.getData();

                                if(mList!=null) {
                                    adapter = new FeedbackListAdapter(mList, FeedbackListActivity.this);
                                    lv.setAdapter(adapter);
                                }else {
                                    MyToast.show(FeedbackListActivity.this, "暂无数据!");
                                }

                            } else {
                                MyToast.show(FeedbackListActivity.this, "暂无数据!");
                            }

                        } catch (Exception e) {
                            MyToast.show(FeedbackListActivity.this, "返回数据异常!");
                        }

                    } else {
                        MyToast.show(FeedbackListActivity.this, "返回数据失败!");
                    }
                    break;
            }
        }
    };
}
