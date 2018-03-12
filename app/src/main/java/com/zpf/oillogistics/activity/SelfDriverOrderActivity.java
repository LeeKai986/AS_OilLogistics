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
import android.widget.RelativeLayout;

import com.zpf.oillogistics.R;
import com.zpf.oillogistics.base.BaseActivity;
import com.zpf.oillogistics.fragment.AllOrderDriverFragment;
import com.zpf.oillogistics.fragment.CompleteOrderDriverFragment;
import com.zpf.oillogistics.fragment.PickOrderDriverFragment;
import com.zpf.oillogistics.fragment.PickedOrderDriverFragment;
import com.zpf.oillogistics.fragment.SelfDriverOrderFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/19.
 * <p>
 * 个人中心司机订单
 */

public class SelfDriverOrderActivity extends FragmentActivity {

    // 布局相关
    // tablayout
    @BindView(R.id.self_driver_order_tl)
    TabLayout tl;
    // vp
    @BindView(R.id.self_driver_order_vp)
    ViewPager vp;
    @BindView(R.id.rel_back)
    RelativeLayout relBack;
    // adapter
    SelfDriverOrderAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_driver_order);
        ButterKnife.bind(this);
        initViewAction(savedInstanceState);
        initData();
    }

    protected void initData() {
    }

    protected void initViewAction(Bundle savedInstanceState) {
        relBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new AllOrderDriverFragment());
        fragments.add(new PickOrderDriverFragment());
        fragments.add(new PickedOrderDriverFragment());
        fragments.add(new CompleteOrderDriverFragment());
        adapter = new SelfDriverOrderAdapter(getSupportFragmentManager());
        adapter.setFragments(fragments);
        vp.setAdapter(adapter);
        tl.setupWithViewPager(vp);
    }

    class SelfDriverOrderAdapter extends FragmentPagerAdapter {

        private String[] titleArr = new String[]{"全部", "未提货", "已提货", "完成"};
        private ArrayList<Fragment> fragments;

        public void setFragments(ArrayList<Fragment> fragments) {
            this.fragments = fragments;
            notifyDataSetChanged();
        }

        public SelfDriverOrderAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return titleArr.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titleArr[position];
        }
    }
}
