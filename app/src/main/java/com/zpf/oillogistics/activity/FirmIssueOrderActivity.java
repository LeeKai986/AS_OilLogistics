package com.zpf.oillogistics.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.zpf.oillogistics.R;
import com.zpf.oillogistics.diy.DiyDialog;
import com.zpf.oillogistics.fragment.PlatformIssueOrderImageFragment;
import com.zpf.oillogistics.fragment.PlatformIssueOrderPartFragment;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/9/15.
 * <p>
 * 厂商发布的订单&&个人发布的订单
 */

public class FirmIssueOrderActivity extends FragmentActivity {

    // 布局相关
    // tablayout
    @BindView(R.id.firm_issue_order_tl)
    TabLayout tl;
    // viewpager
    @BindView(R.id.firm_issue_order_vp)
    ViewPager vp;
    // 关注
    @BindView(R.id.firm_issue_order_atten_tv)
    TextView attenTv;
    // adapter
    FirmIssueOrderAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firm_issue_order);
        initData();
        initViewAction(savedInstanceState);
    }

    protected void initData() {

    }

    protected void initViewAction(Bundle savedInstanceState) {
        attenTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DiyDialog.hintTweBtnDialog(FirmIssueOrderActivity.this, "您确定要取消关注吗？", new DiyDialog.HintTweBtnListener() {
                    @Override
                    public void confirm() {

                    }
                });
            }
        });

        ArrayList<Fragment> list = new ArrayList<>();
        PlatformIssueOrderPartFragment firmIssueOrderPartFragment = new PlatformIssueOrderPartFragment();
        PlatformIssueOrderImageFragment firmIssueOrderImageFragment = new PlatformIssueOrderImageFragment();
        list.add(firmIssueOrderPartFragment);
        list.add(firmIssueOrderImageFragment);
        adapter = new FirmIssueOrderAdapter(getSupportFragmentManager());
        adapter.setList(list);
        vp.setAdapter(adapter);
        tl.setupWithViewPager(vp);
    }

    class FirmIssueOrderAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> list;
        private String[] titles = {"详细描述", "产品图片"};

        public void setList(ArrayList<Fragment> list) {
            this.list = list;
        }

        public FirmIssueOrderAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}
