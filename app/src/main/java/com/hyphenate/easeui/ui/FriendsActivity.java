package com.hyphenate.easeui.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.zpf.oillogistics.R;

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

//        MyFriendsFragment fragment =  new MyFriendsFragment();
//        Bundle bundle=new Bundle();
//        fragment.setArguments(bundle);
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.lin_frag_contant_friends, new MyFriendsFragment()).commit();
    }

}
