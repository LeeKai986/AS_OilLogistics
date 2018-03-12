package com.hyphenate.easeui.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.zpf.oillogistics.R;
import com.zpf.oillogistics.base.BaseActivity;
import com.zpf.oillogistics.utils.MyToast;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/13.
 */

public class GroupNoticeActivity extends BaseActivity {


    @BindView(R.id.iv_back_groupnotice)
    RelativeLayout ivBack;
    @BindView(R.id.tv_config_groupnotice)
    TextView tvConfig;
    @BindView(R.id.edit_search_groupnotice)
    EditText editSearch;
    @BindView(R.id.tv_search_groupnotice)
    TextView tvSearch;

    private String groupid="";
    private String ss="";
    private boolean isAdmin=false;

    @Override
    protected int setLayout() {
        return R.layout.activity_groupnotice;
    }

    @Override
    protected void initData() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        groupid=getIntent().getExtras().getString("groupid");
        isAdmin=getIntent().getExtras().getBoolean("isAdmin");

        if(isAdmin){
            tvConfig.setVisibility(View.VISIBLE);
            editSearch.setVisibility(View.VISIBLE);
        }else {
            tvConfig.setVisibility(View.GONE);
            tvSearch.setVisibility(View.GONE);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                /**
                 * 更新群公告
                 * @param groupId 群id
                 * @param announcement 公告内容
                 * @throws HyphenateException
                 */
                try {
                    ss=EMClient.getInstance().groupManager().fetchGroupAnnouncement(groupid);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if(isAdmin){
                                tvConfig.setVisibility(View.VISIBLE);
                                editSearch.setVisibility(View.VISIBLE);
                                tvSearch.setVisibility(View.GONE);
                                editSearch.setText(ss);
                            }else {
                                tvConfig.setVisibility(View.GONE);
                                editSearch.setVisibility(View.GONE);
                                tvSearch.setVisibility(View.VISIBLE);
                                if(ss!=null)
                                    tvSearch.setText(ss);
                                else
                                    tvSearch.setText("暂无");
                            }

                        }
                    });
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        tvConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        /**
                         * 更新群公告
                         * @param groupId 群id
                         * @param announcement 公告内容
                         * @throws HyphenateException
                         */
                        try {
                            EMClient.getInstance().groupManager().
                                    updateGroupAnnouncement(groupid, editSearch.getText().toString());

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    MyToast.show(GroupNoticeActivity.this,"群公告设置成功");
                                    finish();
                                }
                            });
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

    }

    @Override
    protected void initViewAction(Bundle savedInstanceState) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}
