package com.hyphenate.easeui.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.adapter.GroupAdapter;
import com.hyphenate.easeui.adapter.GroupsAdapter;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.exceptions.HyphenateException;
import com.zpf.oillogistics.R;
import com.zpf.oillogistics.base.BaseActivity;
import com.zpf.oillogistics.base.Constant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/13.
 */

public class GroupsActivity extends BaseActivity {


    public static final String TAG = "GroupsActivity";
    private ListView groupListView;
    protected List<EMGroup> grouplist;
    private GroupsAdapter groupAdapter;
    private InputMethodManager inputMethodManager;
    public static GroupsActivity instance;
    private View progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout linBack;


    Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            swipeRefreshLayout.setRefreshing(false);
            switch (msg.what) {
                case 0:
                    try {
                        refresh();
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                    }
                    break;
                case 1:
                    Toast.makeText(GroupsActivity.this, R.string.Failed_to_get_group_chat_information, Toast.LENGTH_LONG).show();
                    break;

                default:
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        instance = this;
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        grouplist = EMClient.getInstance().groupManager().getAllGroups();
        groupListView = (ListView) findViewById(R.id.list);
        //show group list
        groupAdapter = new GroupsAdapter(this,  grouplist);
        groupListView.setAdapter(groupAdapter);

        linBack=findViewById(R.id.lin_ease_back);

        linBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.holo_blue_bright, R.color.holo_green_light,
                R.color.holo_orange_light, R.color.holo_red_light);
        //pull down to refresh
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                new Thread(){
                    @Override
                    public void run(){
                        try {
                            EMClient.getInstance().groupManager().getJoinedGroupsFromServer();
                            handler.sendEmptyMessage(0);
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                            handler.sendEmptyMessage(1);
                        }
                    }
                }.start();
            }
        });

        groupListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (position == 1) {
                    // create a new group
//                    startActivityForResult(new Intent(GroupsActivity.this, NewGroupActivity.class), 0);
//                } else if (position == 2) {
                    // join a public group
//                    startActivityForResult(new Intent(GroupsActivity.this, PublicGroupsActivity.class), 0);
//                } else {
                    // enter group chat
                    Intent intent = new Intent(GroupsActivity.this, GroupChatActivity.class);
                    // it is group chat
                    intent.putExtra("chatType", EaseConstant.CHATTYPE_GROUP);
                    intent.putExtra("groupid", grouplist.get(position).getGroupId());
                    intent.putExtra("groupname", grouplist.get(position).getGroupName());
                    startActivityForResult(intent, 0);
//                }
            }
        });
        groupListView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
                    if (getCurrentFocus() != null)
                        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                }
                return false;
            }
        });

        registerGroupChangeReceiver();
    }

    @Override
    protected int setLayout() {
        return R.layout.em_fragment_groups;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViewAction(Bundle savedInstanceState) {

    }

    void registerGroupChangeReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(EaseConstant.ACTION_GROUP_CHANAGED);
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if(action.equals(EaseConstant.ACTION_GROUP_CHANAGED)){
                    if (EaseCommonUtils.getTopActivity(GroupsActivity.this).equals(GroupsActivity.class.getName())) {
                        try {
                            refresh();
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
        broadcastManager.registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume() {
        try {
            refresh();
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
        super.onResume();
    }

    private void refresh() throws HyphenateException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //从服务器获取自己加入的和创建的群组列表，此api获取的群组sdk会自动保存到内存和db。
                try {
                    grouplist= EMClient.getInstance().groupManager().getJoinedGroupsFromServer();//需异步处理
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
//        grouplist = EMClient.getInstance().groupManager().getAllGroups();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        groupAdapter = new GroupsAdapter(GroupsActivity.this, grouplist);
                        groupListView.setAdapter(groupAdapter);
                        groupAdapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        instance = null;
    }
}
