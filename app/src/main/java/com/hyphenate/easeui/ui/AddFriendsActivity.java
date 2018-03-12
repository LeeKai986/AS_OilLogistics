package com.hyphenate.easeui.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.DemoHelper;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.exceptions.HyphenateException;
import com.zpf.oillogistics.R;
import com.zpf.oillogistics.base.BaseActivity;
import com.zpf.oillogistics.base.MessageWhat;
import com.zpf.oillogistics.bean.response.AddFriendResponse;
import com.zpf.oillogistics.net.SimplifyThread;
import com.zpf.oillogistics.net.UrlUtil;
import com.zpf.oillogistics.utils.MyShare;
import com.zpf.oillogistics.utils.MyToast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/9/13.
 */

public class AddFriendsActivity extends BaseActivity {

    @BindView(R.id.tv_cancle_addfriends)
    TextView tvCancle;
    @BindView(R.id.tv_name_search_addfriends)
    TextView tvNameSearch;
    @BindView(R.id.rel_search_addfriends)
    RelativeLayout relSearch;
    @BindView(R.id.tv_wu_addfriends)
    TextView tvWu;
    @BindView(R.id.edit_query_addfriends)
    EditText editQuery;
    @BindView(R.id.iv_head_addfriends)
    CircleImageView ivHead;
    @BindView(R.id.tv_name_addfriends)
    TextView tvName;
    @BindView(R.id.tv_phone_addfriends)
    TextView tvPhone;
    @BindView(R.id.tv_add_addfriends)
    TextView tvAdd;
    @BindView(R.id.rel_addfriends)
    RelativeLayout relAddfriends;
    @BindView(R.id.tv_search_addfriends)
    TextView tvSearch;
    @BindView(R.id.tv_added_addfriends)
    TextView tvAdded;

    private static String TAG = "AddFriendsActivity";


    private HashMap<String, String> friendsHp = new HashMap<>();
    private String friendsName = "";
    /*搜索延时标致*/
    private long lastTime = 0;
    private String phone = "";
    private AddFriendResponse add;

    protected List<EaseUser> friendList;
    private Map<String, EaseUser> contactsMap;

    @Override
    protected int setLayout() {
        return R.layout.activity_addfriends;
    }

    @Override
    protected void initData() {
        tvCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFriends();
            }
        });

        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //参数为要添加的好友的username和添加理由
                try {
                    EMClient.getInstance().contactManager().addContact(editQuery.getText().toString(), "");
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }

                if(add!=null&&add.getData().getHeader()!=null) {
                    String em_picturl = UrlUtil.IMAGE_URL + add.getData().getHeader();
                    //下面是环信客服的扩展消息，本身可以使用和上方一样的方法（扩展参数不同），但是不知道为什么取不到值，因此先取weichat，自己解析扩展消息
                    String hxIdFrom = add.getData().getPhone();
                    EaseUser easeUser = new EaseUser(hxIdFrom);
                    easeUser.setAvatar(em_picturl);
                    // 存入内存
                    DemoHelper.getInstance().getContactList();
                    DemoHelper.getInstance().contactList.put(hxIdFrom, easeUser);
                    // 存入db
                    List<EaseUser> users = new ArrayList<>();
                    users.add(easeUser);
                    DemoHelper.getInstance().userDao.saveContactList(users);
                }

                MyToast.show(AddFriendsActivity.this, "添加成功!");
                finish();
            }
        });


        editQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {


                long time = new Date().getTime();

                if (time - lastTime > 1000) {
                    lastTime = time;
                    phone=editable.toString();
                }
            }
        });


//        getFriends("18766666661");
//        try {
//            EMClient.getInstance().contactManager().acceptInvitation("qq");
//            EMClient.getInstance().contactManager().acceptInvitation("ww");
//            EMClient.getInstance().contactManager().acceptInvitation("ee");
//            EMClient.getInstance().contactManager().acceptInvitation("rr");
//        } catch (HyphenateException e) {
//            e.printStackTrace();
//        }

        getContactList();
    }

    @Override
    protected void initViewAction(Bundle savedInstanceState) {
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
        if(editQuery.getText().toString().equals(MyShare.getShared().getString("userPhone",""))){
            MyToast.show(AddFriendsActivity.this,"不能添加自己为好友");
            return;
        }

        friendsHp.put("phone", editQuery.getText().toString());

        SimplifyThread simplifyThread = new SimplifyThread(UrlUtil.URL_USER_NICK, friendsHp);
        simplifyThread.setOnKeyResultListener(new SimplifyThread.OnResultListener() {
            @Override
            public void resultBody(String res) {
                Log.i(TAG, "getData--res" + res);
                Message message = new Message();
                message.obj = res;
                message.what = MessageWhat.ADDFRIENDS_GET_SELECT;
                handler.sendMessage(message);
            }

            @Override
            public void errorBody(String error) {
                Log.i(TAG, "getData--error" + error);
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
                            add = gson.fromJson(msg.obj.toString(), AddFriendResponse.class);
                            if (add.getStatus() == 0) {
                                relAddfriends.setVisibility(View.VISIBLE);
                                tvWu.setVisibility(View.GONE);

                                if(add.getData().getStatus()==2){
                                    tvName.setText(add.getData().getCompanyname());
                                }else {
                                    tvName.setText(add.getData().getRelname());
                                }

                                tvPhone.setText(editQuery.getText().toString());

                                //加载头像
                                if (add.getData().getHeader()!=null) {
                                    Glide.with(AddFriendsActivity.this)
                                            .load(UrlUtil.IMAGE_URL + add.getData().getHeader())
                                            .placeholder(R.mipmap.head_default)
                                            .error(R.mipmap.head_default)
                                            .into(ivHead);
                                }
                                if (friendList.size() == 0) {
                                    tvAdded.setVisibility(View.GONE);
                                    tvAdd.setVisibility(View.VISIBLE);
                                } else {
                                    for(int i=0;i<friendList.size();i++){
                                        if(friendList.get(i).getUsername().equals(editQuery.getText().toString())){
                                            tvAdded.setVisibility(View.VISIBLE);
                                            tvAdd.setVisibility(View.GONE);
                                        }else {
                                            tvAdded.setVisibility(View.GONE);
                                            tvAdd.setVisibility(View.VISIBLE);
                                        }
                                    }
                                }

                            }else {
                                relAddfriends.setVisibility(View.GONE);
                                tvWu.setVisibility(View.VISIBLE);
                            }

                        } catch (Exception e) {
                            MyToast.show(AddFriendsActivity.this, "返回数据异常!");
                        }

                    } else {
                        MyToast.show(AddFriendsActivity.this, "返回数据失败!");
                    }
                    break;
            }
        }
    };


    /**
     * get contact list and sort, will filter out users in blacklist
     */
    protected void getContactList() {
        contactsMap= DemoHelper.getInstance().getContactList();
        friendList=new ArrayList<>();
        friendList.clear();
        if(contactsMap == null){
            return;
        }
        synchronized (this.contactsMap) {
            Iterator<Map.Entry<String, EaseUser>> iterator = contactsMap.entrySet().iterator();
            List<String> blackList = null;
            blackList = EMClient.getInstance().contactManager().getBlackListUsernames();

            while (iterator.hasNext()) {
                Map.Entry<String, EaseUser> entry = iterator.next();
                // to make it compatible with data in previous version, you can remove this check if this is new app
                if (!entry.getKey().equals("item_new_friends")
                        && !entry.getKey().equals("item_groups")
                        && !entry.getKey().equals("item_chatroom")
                        && !entry.getKey().equals("item_robots")){
                    if(!blackList.contains(entry.getKey())){
                        //filter out users in blacklist
                        EaseUser user = entry.getValue();
                        EaseCommonUtils.setUserInitialLetter(user);
                        friendList.add(user);
                    }
                }
            }
        }

        // sorting
        Collections.sort(friendList, new Comparator<EaseUser>() {

            @Override
            public int compare(EaseUser lhs, EaseUser rhs) {
                if(lhs.getInitialLetter().equals(rhs.getInitialLetter())){
                    return lhs.getNick().compareTo(rhs.getNick());
                }else{
                    if("#".equals(lhs.getInitialLetter())){
                        return 1;
                    }else if("#".equals(rhs.getInitialLetter())){
                        return -1;
                    }
                    return lhs.getInitialLetter().compareTo(rhs.getInitialLetter());
                }
            }
        });
    }

}
