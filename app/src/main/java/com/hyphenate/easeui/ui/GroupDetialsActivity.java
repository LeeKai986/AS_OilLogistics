package com.hyphenate.easeui.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCursorResult;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.easeui.adapter.GroupMemberAdapter;
import com.hyphenate.exceptions.HyphenateException;
import com.zpf.oillogistics.R;
import com.zpf.oillogistics.base.BaseActivity;
import com.zpf.oillogistics.utils.MyToast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/14.
 */

public class GroupDetialsActivity extends BaseActivity implements View.OnClickListener
        , GroupMemberAdapter.MemberClickListener {
    @BindView(R.id.rel_back_groupdetails)
    RelativeLayout relBack;
    @BindView(R.id.grid_menber_groupdetails)
    GridView gridMenber;
    @BindView(R.id.person_center_state_sw)
    Switch swichView;
    @BindView(R.id.tv_groupname_groupdetails)
    TextView tvGroupname;
    @BindView(R.id.tv_notice_groupdetails)
    TextView tvNotice;
    @BindView(R.id.lin_groupname_groupdetails)
    RelativeLayout linGroupname;
    @BindView(R.id.lin_notice_groupdetails)
    RelativeLayout linNotice;
    @BindView(R.id.lin_clear_groupdetails)
    RelativeLayout linClear;
    @BindView(R.id.lin_dissolve_groupdetails)
    RelativeLayout linDissolve;
    @BindView(R.id.tv_config_groupdetails)
    TextView tvConfig;
    @BindView(R.id.lin_excit_groupdetails)
    RelativeLayout linExcit;

    private GroupMemberAdapter adapter;
    List<String> memberList = new ArrayList<>();
    private String groupNme = "";
    private String groupid = "";
    private String groupNotice="";
    private EMGroup group;
    private List<String> adminList = Collections.synchronizedList(new ArrayList<String>());


    @Override
    protected int setLayout() {
        return R.layout.activity_groupsdetails;
    }

    @Override
    protected void initData() {
        swichView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, final boolean b) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (b) {
                                EMClient.getInstance().groupManager().blockGroupMessage(groupid);
                            } else {
                                EMClient.getInstance().groupManager().unblockGroupMessage(groupid);
                            }

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (b) {
                                        MyToast.show(GroupDetialsActivity.this, "已屏蔽该群");
                                    } else {
                                        MyToast.show(GroupDetialsActivity.this, "已解除屏蔽");
                                    }
                                }
                            });
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        getGroupMember();

    }

    @Override
    protected void initViewAction(Bundle savedInstanceState) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rel_back_groupdetails:
                finish();
                break;
            case R.id.lin_groupname_groupdetails:
                Intent intent = new Intent(GroupDetialsActivity.this, GroupNameActivity.class);
                startActivityForResult(intent, 0);
                break;
            case R.id.lin_dissolve_groupdetails:
                deleteGrop();
                break;
            case R.id.lin_excit_groupdetails:
                exitGrop();
                break;
            case R.id.lin_clear_groupdetails:
                break;
            case R.id.lin_notice_groupdetails:
                Intent inNotice = new Intent(GroupDetialsActivity.this, GroupNoticeActivity.class);
                inNotice.putExtra("groupid", groupid);
                inNotice.putExtra("isAdmin",isCanAddMember(group));
                startActivity(inNotice);
                break;
            case R.id.tv_config_groupdetails:
                finish();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

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
                    groupNotice=EMClient.getInstance().groupManager().fetchGroupAnnouncement(groupid);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if(groupNotice!=null)
                                tvNotice.setText(groupNotice);
                            else
                                tvNotice.setText("暂无");
                        }
                    });
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == 1) {//设置名称
                groupNme = data.getExtras().getString("name");
                tvGroupname.setText(groupNme);
                changeGroupNme();
            } else if (resultCode == 2) {//删除成员
                getGroupMember();
            } else if (resultCode == 3) {//添加成员
                getGroupMember();
            }
        }
    }

    /**
     * 修改群名
     */
    private void changeGroupNme(){
        new Thread(new Runnable() {
            public void run() {
                try {
                    EMClient.getInstance().groupManager().changeGroupName(groupid, groupNme);

                } catch (HyphenateException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        public void run() {
                        }
                    });
                }
            }
        }).start();
    }

    /**
     * 获取群成员
     */
    private void getGroupMember() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //获取群组信息
                    groupid = getIntent().getExtras().getString("groupid");
                    group = EMClient.getInstance().groupManager().getGroupFromServer(groupid);

                    //群管理员list
                    adminList.clear();
                    adminList.addAll(group.getAdminList());

                    //获取群成员
                    EMCursorResult<String> result = null;
                    memberList.clear();
                    if (groupid != null) {
                        memberList.add(EMClient.getInstance().groupManager().getGroup(groupid).getOwner());
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
                            adapter = new GroupMemberAdapter(memberList, GroupDetialsActivity.this, isCanAddMember(group));
                            adapter.setMemberClickListener(GroupDetialsActivity.this);
                            gridMenber.setAdapter(adapter);

                            //群名称
                            tvGroupname.setText(group.getGroupName());

                            //设置屏蔽状态
                            if(group.isMsgBlocked()){
                                swichView.setChecked(true);
                            }else {
                                swichView.setChecked(false);
                            }

                            //根据成员角色显示各自界面
                            if(isCanAddMember(group)){
                                linDissolve.setVisibility(View.VISIBLE);
                                tvConfig.setVisibility(View.VISIBLE);
                                linGroupname.setOnClickListener(GroupDetialsActivity.this);

                                linClear.setOnClickListener(GroupDetialsActivity.this);
                                linDissolve.setOnClickListener(GroupDetialsActivity.this);
                                tvConfig.setOnClickListener(GroupDetialsActivity.this);
                            }else {
                                linExcit.setVisibility(View.VISIBLE);
                                linExcit.setOnClickListener(GroupDetialsActivity.this);
                            }
                            linNotice.setOnClickListener(GroupDetialsActivity.this);
                            relBack.setOnClickListener(GroupDetialsActivity.this);
                        }
                    });

                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 退出群组
     *
     */
    private void exitGrop() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    EMClient.getInstance().groupManager().leaveGroup(groupid);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            MyToast.show(GroupDetialsActivity.this, "已退出该群");
                            setResult(1);
                            finish();
                        }
                    });
                } catch (final Exception e) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), "退出失败"+ " " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        }).start();
    }

    /**
     * 解散群组
     *
     */
    private void deleteGrop() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    EMClient.getInstance().groupManager().destroyGroup(groupid);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            MyToast.show(GroupDetialsActivity.this, "已解散该群");
                            setResult(1);
                            finish();
                        }
                    });
                } catch (final Exception e) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), "解散群组失败", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        }).start();
    }


    @Override
    public void kickMember() {
        Intent intent = new Intent(GroupDetialsActivity.this, KickMenberActivity.class);
        intent.putExtra("groupid", groupid);
        startActivityForResult(intent, 0);
    }

    @Override
    public void addMenber() {
        Intent intent = new Intent(GroupDetialsActivity.this, AddGroupMenberActivity.class);
        intent.putExtra("groupid", groupid);
        startActivityForResult(intent, 0);
    }

    boolean isCurrentOwner(EMGroup group) {
        String owner = group.getOwner();
        if (owner == null || owner.isEmpty()) {
            return false;
        }
        return owner.equals(EMClient.getInstance().getCurrentUser());
    }

    /**
     * 是否是管理员
     * @param group
     * @return
     */
    boolean isCurrentAdmin(EMGroup group) {
        synchronized (adminList) {
            String currentUser = EMClient.getInstance().getCurrentUser();
            for (String admin : adminList) {
                if (currentUser.equals(admin)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 是否是管理员
     * @param id
     * @return
     */
    boolean isAdmin(String id) {
        synchronized (adminList) {
            for (String admin : adminList) {
                if (id.equals(admin)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 能否添加成员
     * @param group
     * @return
     */
    boolean isCanAddMember(EMGroup group) {
        if (group.isMemberAllowToInvite() ||
                isAdmin(EMClient.getInstance().getCurrentUser()) ||
                isCurrentOwner(group)) {
            return true;
        }
        return false;
    }
}
