package com.hyphenate.easeui.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.db.PersonInfo;
import com.zpf.oillogistics.R;
import com.zpf.oillogistics.base.CyApplication;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/13.
 */

public class FriendsChatActivity extends FragmentActivity {
    @BindView(R.id.rel_back_friendschat)
    RelativeLayout relBack;
    @BindView(R.id.tv_name_friendschat)
    TextView tvName;
    @BindView(R.id.rel_details_friendschat)
    RelativeLayout relDetails;

    private String title = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        initData();
    }

    protected void initData() {
        relBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        relDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FriendsChatActivity.this, FriendsDetailActivity.class);
                intent.putExtra("phone", title);
                startActivity(intent);
            }
        });

        MyChatFragment fragment = new MyChatFragment();
        title = getIntent().getExtras().getString("username");
        Bundle bundle = new Bundle();
        bundle.putString(EaseConstant.EXTRA_USER_ID, title);
        fragment.setArguments(bundle);
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.lin_content_friendschat, fragment).commit();
        if (CyApplication.findUserData(title) != null) {
            PersonInfo userinfo = CyApplication.findUserData(title);
            tvName.setText(userinfo.getRelname());
        } else {
            tvName.setText(title);
        }
        ;
    }


}
