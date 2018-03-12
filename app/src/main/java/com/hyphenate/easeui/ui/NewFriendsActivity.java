package com.hyphenate.easeui.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.hyphenate.easeui.adapter.NewFriendsMsgAdapter;
import com.hyphenate.easeui.db.InviteMessgeDao;
import com.hyphenate.easeui.domain.InviteMessage;
import com.zpf.oillogistics.R;
import com.zpf.oillogistics.base.BaseActivity;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/13.
 */

public class NewFriendsActivity extends BaseActivity {

    @BindView(R.id.iv_back_newfriends)
    RelativeLayout ivBack;
    @BindView(R.id.lv_newFriends)
    ListView lvNewFriends;

    @Override
    protected int setLayout() {
        return R.layout.activity_newfriends;
    }

    @Override
    protected void initData() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        InviteMessgeDao dao = new InviteMessgeDao(this);
        List<InviteMessage> msgs = dao.getMessagesList();
        Collections.reverse(msgs);
        NewFriendsMsgAdapter adapter = new NewFriendsMsgAdapter(this, 1, msgs);
        lvNewFriends.setAdapter(adapter);
        dao.saveUnreadMessageCount(0);

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
