package com.hyphenate.easeui.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyphenate.EMGroupChangeListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroupManager;
import com.hyphenate.chat.EMGroupOptions;
import com.hyphenate.chat.EMMucSharedFile;
import com.hyphenate.easeui.DemoHelper;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.adapter.SelectMemberAdapter;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.widget.EaseContactList;
import com.hyphenate.exceptions.HyphenateException;
import com.zpf.oillogistics.R;
import com.zpf.oillogistics.base.BaseActivity;
import com.zpf.oillogistics.base.Constant;
import com.zpf.oillogistics.base.MessageWhat;
import com.zpf.oillogistics.net.SimplifyThread;
import com.zpf.oillogistics.utils.MyToast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/13.
 */

public class CreateGroupActivity extends BaseActivity {
    @BindView(R.id.iv_back_creategroup)
    RelativeLayout ivBack;
    @BindView(R.id.tv_config_creategroup)
    TextView tvConfig;
    @BindView(R.id.edit_name_groupname)
    EditText editName;
    @BindView(R.id.rel_clear_groupname)
    RelativeLayout relClear;
    @BindView(R.id.edit_search_creategroup)
    EditText editSearch;


    private EaseContactList contactListLayout;
    private LinearLayout contentContainer;
    private ListView listView;

    private List<EaseUser> alluserList;
    private SelectMemberAdapter adapter;
    private boolean isCreate = true;

    @Override
    protected int setLayout() {
        return R.layout.activity_creategroup;
    }

    @Override
    protected void initData() {
//        isCreate=getIntent().getExtras().getBoolean("isCreate");

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isCreate) {
                    createGroup();
                } else {

                }
            }
        });

        contentContainer = findViewById(R.id.content_container_groupcreate);
        contactListLayout = findViewById(R.id.contact_list_groupcreate);
        listView = contactListLayout.getListView();

        getContactList();
        contactListLayout.init(null, alluserList, null, R.layout.adapter_selectmenber);


    }

    @Override
    protected void initViewAction(Bundle savedInstanceState) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }


    private void getContactList() {
        // get contact list
        alluserList = new ArrayList<EaseUser>();
        for (EaseUser user : DemoHelper.getInstance().getContactList().values()) {
            if (!user.getUsername().equals(EaseConstant.NEW_FRIENDS_USERNAME)
                    & !user.getUsername().equals(EaseConstant.GROUP_USERNAME)
                    & !user.getUsername().equals(EaseConstant.CHAT_ROOM)
                    & !user.getUsername().equals(EaseConstant.CHAT_ROBOT))
                alluserList.add(user);
        }
        // sort the list
        Collections.sort(alluserList, new Comparator<EaseUser>() {

            @Override
            public int compare(EaseUser lhs, EaseUser rhs) {
                if (lhs.getInitialLetter().equals(rhs.getInitialLetter())) {
                    return lhs.getNick().compareTo(rhs.getNick());
                } else {
                    if ("#".equals(lhs.getInitialLetter())) {
                        return 1;
                    } else if ("#".equals(rhs.getInitialLetter())) {
                        return -1;
                    }
                    return lhs.getInitialLetter().compareTo(rhs.getInitialLetter());
                }
            }
        });
    }

    private void createGroup() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                if (!editName.getText().toString().equals("")) {
                    try {


                        int n = contactListLayout.getCheckList().size();
                        String[] members = contactListLayout.getCheckList().toArray(new String[n]);
                        EMGroupOptions option = new EMGroupOptions();
                        option.maxUsers = 200;
                        option.inviteNeedConfirm = true;

                        option.style = EMGroupManager.EMGroupStyle.EMGroupStylePrivateOnlyOwnerInvite;

                        EMClient.getInstance().groupManager().createGroup(
                                editName.getText().toString(), "", members, "", option);
                        runOnUiThread(new Runnable() {
                            public void run() {


                                Toast.makeText(CreateGroupActivity.this, "建群成功!", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        });
                    } catch (final HyphenateException e) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(CreateGroupActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    } catch (Exception e) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(CreateGroupActivity.this, "建群失败!", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } else {
                    MyToast.show(CreateGroupActivity.this, "请输入群名称");
                }
            }
        }).start();

        EMClient.getInstance().groupManager().addGroupChangeListener(new EMGroupChangeListener() {
            @Override
            public void onInvitationReceived(String groupId, String groupName, String inviter, String reason) {
                //接收到群组加入邀请
                Toast.makeText(CreateGroupActivity.this, "接收到群组加入邀请!", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onRequestToJoinReceived(String groupId, String groupName, String applyer, String reason) {
                //用户申请加入群
                Toast.makeText(CreateGroupActivity.this, "用户申请加入群!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onRequestToJoinAccepted(String groupId, String groupName, String accepter) {
                //加群申请被同意
                Toast.makeText(CreateGroupActivity.this, "加群申请被同意!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onRequestToJoinDeclined(String groupId, String groupName, String decliner, String reason) {
                //加群申请被拒绝
                Toast.makeText(CreateGroupActivity.this, "加群申请被拒绝!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onInvitationAccepted(String groupId, String inviter, String reason) {
                //群组邀请被同意
            }

            @Override
            public void onInvitationDeclined(String groupId, String invitee, String reason) {
                //群组邀请被拒绝
            }

            @Override
            public void onUserRemoved(String s, String s1) {

            }

            @Override
            public void onGroupDestroyed(String s, String s1) {
                Toast.makeText(CreateGroupActivity.this, "onGroupDestroyed!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAutoAcceptInvitationFromGroup(String groupId, String inviter, String inviteMessage) {
                //接收邀请时自动加入到群组的通知
            }

            @Override
            public void onMuteListAdded(String groupId, final List<String> mutes, final long muteExpire) {
                //成员禁言的通知
            }

            @Override
            public void onMuteListRemoved(String groupId, final List<String> mutes) {
                //成员从禁言列表里移除通知
            }

            @Override
            public void onAdminAdded(String groupId, String administrator) {
                //增加管理员的通知
            }

            @Override
            public void onAdminRemoved(String groupId, String administrator) {
                //管理员移除的通知
            }

            @Override
            public void onOwnerChanged(String groupId, String newOwner, String oldOwner) {
                //群所有者变动通知

            }

            @Override
            public void onMemberJoined(final String groupId, final String member) {
                //群组加入新成员通知
            }

            @Override
            public void onMemberExited(final String groupId, final String member) {
                //群成员退出通知
            }

            @Override
            public void onAnnouncementChanged(String groupId, String announcement) {
                //群公告变动通知
                Toast.makeText(CreateGroupActivity.this, "群公告变动通知!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSharedFileAdded(String groupId, EMMucSharedFile sharedFile) {
                //增加共享文件的通知
            }

            @Override
            public void onSharedFileDeleted(String groupId, String fileId) {
                //群共享文件删除通知
            }
        });
    }


    /**
     * 查找好友
     */
    private void getFriends(String friendsphone) {
        HashMap<String, String> hp = new HashMap<>();
        hp.put("name", "");
        hp.put("uid", "");
        hp.put("g_id", "");
        SimplifyThread simplifyThread = new SimplifyThread(Constant.URL_USER_NICK, hp);
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

                        } catch (Exception e) {
                            MyToast.show(CreateGroupActivity.this, "返回数据异常!");
                        }

                    } else {
                        MyToast.show(CreateGroupActivity.this, "返回数据失败!");
                    }
                    break;
            }
        }
    };


}
