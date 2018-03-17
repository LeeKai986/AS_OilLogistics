package com.hyphenate.easeui.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.zpf.oillogistics.R;
import com.zpf.oillogistics.base.BaseActivity;
import com.zpf.oillogistics.base.MessageWhat;
import com.zpf.oillogistics.bean.response.AddFriendResponse;
import com.zpf.oillogistics.net.SimplifyThread;
import com.zpf.oillogistics.net.UrlUtil;
import com.zpf.oillogistics.utils.MyToast;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/9/13.
 */

public class FriendsDetailActivity extends BaseActivity {
    @BindView(R.id.iv_back_friendsdetail)
    RelativeLayout ivBack;
    @BindView(R.id.iv_name_friendsdetail)
    TextView ivName;
    @BindView(R.id.criv_head_friendsdetial)
    CircleImageView crivHead;
    @BindView(R.id.tv_name_frinedsdetails)
    TextView tvName;
    @BindView(R.id.tv_phone_frinedsdetails)
    TextView tvPhone;
    @BindView(R.id.tv_toChat_friendsdetails)
    TextView tvToChat;

    HashMap<String,String> friendsHp=new HashMap<>();

    @Override
    protected int setLayout() {
        return R.layout.activity_friends_details;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViewAction(Bundle savedInstanceState) {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tvToChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getFriends();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    /**
     * 查找好友
     */
    private void getFriends( ) {
        friendsHp.put("phone",getIntent().getExtras().getString("phone",""));
        SimplifyThread simplifyThread = new SimplifyThread(UrlUtil.URL_USER_NICK, friendsHp);
        simplifyThread.setOnKeyResultListener(new SimplifyThread.OnResultListener() {
            @Override
            public void resultBody(String res) {
                Message message = new Message();
                message.obj = res;
                message.what = MessageWhat.ADDFRIENDS_GET_SELECT;
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
                case MessageWhat.ADDFRIENDS_GET_SELECT:
                    if (msg.obj != null) {
                        try {
                            AddFriendResponse add = gson.fromJson(msg.obj.toString(), AddFriendResponse.class);
                            if (add.getStatus() == 0) {
//                                ivName.setText();
                                if(add.getData().getStatus()==2)
                                    tvName.setText(add.getData().getCompanyname());
                                else
                                    tvName.setText(add.getData().getRelname());

                                tvPhone.setText(getIntent().getExtras().getString("phone",""));
                                if(add.getData().getHeader()!=null){
                                    Glide.with(FriendsDetailActivity.this)
                                            .load(UrlUtil.IMAGE_URL + add.getData().getHeader())
                                            .error(R.mipmap.head_default)
                                            .into(crivHead);
                                }
                            }

                        } catch (Exception e) {
                            MyToast.show(FriendsDetailActivity.this, "返回数据异常!");
                        }

                    } else {
                        MyToast.show(FriendsDetailActivity.this, "返回数据失败!");
                    }
                    break;
            }
        }
    };
}
