package com.hyphenate.easeui.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCursorResult;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.easeui.DemoHelper;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.adapter.SelectMemberAdapter;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.widget.EaseContactList;
import com.hyphenate.exceptions.HyphenateException;
import com.zpf.oillogistics.R;
import com.zpf.oillogistics.base.BaseActivity;
import com.zpf.oillogistics.utils.MyToast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/15.
 */

public class AddGroupMenberActivity extends BaseActivity {


    @BindView(R.id.iv_back_creategroup)
    RelativeLayout ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_config_creategroup)
    TextView tvConfig;
    @BindView(R.id.tv_goupname)
    TextView tvGoupname;
    @BindView(R.id.edit_name_groupname)
    EditText editName;
    @BindView(R.id.iv_clear_groupname)
    ImageView ivClear;
    @BindView(R.id.rel_clear_groupname)
    RelativeLayout relClear;
    @BindView(R.id.rel_goupname)
    RelativeLayout relGoupname;
    @BindView(R.id.edit_search_creategroup)
    EditText editSearch;
    @BindView(R.id.contact_list_groupcreate)
    EaseContactList contactList;
    @BindView(R.id.content_container_groupcreate)
    LinearLayout contentContainer;

    private ListView listView;

    private List<String> memberList = new ArrayList<>();
    private List<String> addList = new ArrayList<>();
    private List<EaseUser> alluserList = new ArrayList<>();
    private String groupid = "";


    private List<EaseUser> outFriends = new ArrayList<>();

    @Override
    protected int setLayout() {
        return R.layout.activity_creategroup;
    }

    @Override
    protected void initData() {

        relGoupname.setVisibility(View.GONE);
        tvTitle.setText("添加成员");

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                setResult(3, intent);
                finish();
            }
        });
        tvConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = getIntent();
                setResult(3, intent);
                finish();

                addList = contactList.getCheckList();
                if (addList != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String[] name = addList.toArray(new String[addList.size()]);
                                //群主加人调用此方法
                                EMClient.getInstance().groupManager().addUsersToGroup(groupid, name);//需异步处理

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        MyToast.show(AddGroupMenberActivity.this, "该成员已邀请加入!");
                                        Intent intent = getIntent();
                                        setResult(2, intent);
                                        finish();
                                    }
                                });
                            } catch (HyphenateException e) {
                                MyToast.show(AddGroupMenberActivity.this, "加入失败!");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        MyToast.show(AddGroupMenberActivity.this, "加入失败!");
                                        ;
                                    }
                                });
                            }

                        }
                    }).start();

                } else {
                    MyToast.show(AddGroupMenberActivity.this, "请选择成员!");
                }
            }
        });


        contentContainer = findViewById(R.id.content_container_groupcreate);
        contactList = findViewById(R.id.contact_list_groupcreate);
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
     * 获取所有好友
     */
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
        getGroupMember();
    }

    /**
     * 获取群成员
     */
    private void getGroupMember() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    groupid = getIntent().getExtras().getString("groupid");

                    EMCursorResult<String> result = null;
                    memberList.clear();
                    if (groupid != null) {
                        do {
                            result = EMClient.getInstance().groupManager().fetchGroupMembers(groupid,
                                    result != null ? result.getCursor() : "", 2);

                            memberList.addAll(result.getData());
                        }
                        while (!TextUtils.isEmpty(result.getCursor()) && result.getData().size() == 2);
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            for (int i = 0; i < memberList.size(); i++) {
                                for (int j = 0; j < alluserList.size(); j++) {
                                    if (alluserList.get(j).getUsername().equals(memberList.get(i))) {
                                        alluserList.remove(j);
                                    }
                                }
                            }
                            listView = contactList.getListView();
                            contactList.init(null, alluserList, null, R.layout.adapter_selectmenber);

                        }
                    });

                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
