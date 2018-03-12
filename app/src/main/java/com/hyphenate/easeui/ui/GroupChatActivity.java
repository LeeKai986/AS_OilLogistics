package com.hyphenate.easeui.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.exceptions.HyphenateException;
import com.zpf.oillogistics.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/14.
 */

public class GroupChatActivity extends FragmentActivity {

    @BindView(R.id.rel_back_groupchat)
    RelativeLayout relBack;
    @BindView(R.id.tv_name_groupchat)
    TextView tvName;
    @BindView(R.id.rel_details_groupchat)
    RelativeLayout relDetails;
    @BindView(R.id.lin_content_groupchat)
    LinearLayout linContent;

    private String title="";
    private String groupID="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupchat);
        ButterKnife.bind(this);
        relBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        relDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(GroupChatActivity.this,GroupDetialsActivity.class);
                intent.putExtra("groupid",groupID);
                startActivityForResult(intent,0);
            }
        });

        MyChatFragment fragment = new MyChatFragment();
        title= getIntent().getExtras().getString("groupname");
        groupID= getIntent().getExtras().getString("groupid");
        Bundle bundle=new Bundle();
        bundle.putString(EaseConstant.EXTRA_USER_ID,groupID);
        bundle.putInt(EaseConstant.EXTRA_CHAT_TYPE,EaseConstant.CHATTYPE_GROUP);
        fragment.setArguments(bundle);
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.lin_content_groupchat, fragment).commit();

    }

    EMGroup group;
    @Override
    protected void onStart() {
        super.onStart();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    group = EMClient.getInstance().groupManager().getGroupFromServer(groupID);
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       if(group!=null) {
                           if (group.getGroupName() != null && !group.getGroupName().equals(""))
                               tvName.setText(group.getGroupName());
                           else
                               tvName.setText(group.getGroupId());
                       }
                    }
                });
            }
        }).start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==1){
            finish();
        }
    }
}