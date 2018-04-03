package com.hyphenate.easeui.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.google.gson.Gson;
import com.zpf.oillogistics.R;
import com.zpf.oillogistics.base.MessageWhat;
import com.zpf.oillogistics.bean.response.FriendsListResponse;
import com.zpf.oillogistics.net.SimplifyThread;
import com.zpf.oillogistics.net.UrlUtil;
import com.zpf.oillogistics.utils.MyShare;
import com.zpf.oillogistics.utils.MyToast;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/9/14.
 */

public class FriendsActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        initView();
    }

    /**
     * 界面初始化
     */
    private void initView() {
        getFriendsList();
//        MyFriendsFragment fragment =  new MyFriendsFragment();
//        Bundle bundle=new Bundle();
//        fragment.setArguments(bundle);

    }

    private void getFriendsList() {
        HashMap<String, String> hp = new HashMap<>();
        hp.put("username", MyShare.getShared().getString("userPhone", ""));
        SimplifyThread simplifyThread = new SimplifyThread(UrlUtil.URL_FRIENDSLIST, hp);
        simplifyThread.setOnKeyResultListener(new SimplifyThread.OnResultListener() {
            @Override
            public void resultBody(String res) {
                Message message = new Message();
                message.obj = res;
                message.what = MessageWhat.SELF_FRIENDSLIST_MSG;
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
                case MessageWhat.SELF_FRIENDSLIST_MSG:
                    if (msg.obj != null) {
                        try {
                            FriendsListResponse friends = gson.fromJson(msg.obj.toString(), FriendsListResponse.class);

                            if (friends.getStatus() == 0) {
                                if (friends.getData() != null && friends.getData().size() > 0) {
                                    final List<FriendsListResponse.DataBean> friendsList = friends.getData();

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            FragmentManager fm = getSupportFragmentManager();
                                            MyFriendsFragment myFriendsFragment = new MyFriendsFragment();
                                            myFriendsFragment.setFriendsList(friendsList);
                                            fm.beginTransaction().replace(R.id.lin_frag_contant_friends, myFriendsFragment).commit();

                                        }
                                    });
                                }
                            } else if (friends != null) {
                                MyToast.show(FriendsActivity.this, friends.getMsg());
                            }


                        } catch (Exception e) {
                            MyToast.show(FriendsActivity.this, "返回数据异常!");
                        }

                    } else {
                        MyToast.show(FriendsActivity.this, "返回数据失败!");
                    }
                    break;
            }
        }
    };
}
