package com.zpf.oillogistics.activity;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.zpf.oillogistics.R;
import com.zpf.oillogistics.fragment.AllOrderCompanyFragment;
import com.zpf.oillogistics.fragment.CompleteOrderCompanyFragment;
import com.zpf.oillogistics.fragment.PaiedOrderCompanyFragment;
import com.zpf.oillogistics.fragment.PayOrderCompanyFragment;
import com.zpf.oillogistics.fragment.PickOrderCompanyFragment;
import com.zpf.oillogistics.fragment.PickedOrderCompanyFragment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/19.
 */

public class SelfCompanyOrderActivity extends FragmentActivity {
    @BindView(R.id.rel_back_company_order_list)
    RelativeLayout relBack;
    @BindView(R.id.top_bar_company_order_list)
    TabLayout topBar;
    @BindView(R.id.viewpager_company_order_list)
    ViewPager mViewPager;

    private ArrayList<Fragment> fraglist = new ArrayList<>();
    private MyFrageStatePagerAdapter pagerAdapter;
    private FragmentManager fm;

    private AllOrderCompanyFragment allOrderCompanyFragment;
    private PayOrderCompanyFragment payOrderCompanyFragment;
    private PaiedOrderCompanyFragment paiedOrderCompanyFragment;
    private PickOrderCompanyFragment pickOrderCompanyFragment;
    private PickedOrderCompanyFragment pickedOrderCompanyFragment;
    private CompleteOrderCompanyFragment completeOrderCompanyFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_company_order);
        ButterKnife.bind(this);

        addFragments();
    }

    /**
     * 加载fragment
     */
    private void addFragments() {
        allOrderCompanyFragment = new AllOrderCompanyFragment();
        payOrderCompanyFragment = new PayOrderCompanyFragment();
        paiedOrderCompanyFragment=new PaiedOrderCompanyFragment();
        pickOrderCompanyFragment=new PickOrderCompanyFragment();
        pickedOrderCompanyFragment=new PickedOrderCompanyFragment();
        completeOrderCompanyFragment=new CompleteOrderCompanyFragment();

        fraglist.add(allOrderCompanyFragment);
        fraglist.add(payOrderCompanyFragment);
        fraglist.add(paiedOrderCompanyFragment);
        fraglist.add(pickOrderCompanyFragment);
        fraglist.add(pickedOrderCompanyFragment);
        fraglist.add(completeOrderCompanyFragment);

        fm = getSupportFragmentManager();

        pagerAdapter = new MyFrageStatePagerAdapter(SelfCompanyOrderActivity.this, fm, fraglist);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                System.out.print("---onPageSelected--" + fm.getFragments().size());

                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        topBar.setupWithViewPager(mViewPager);
        topBar.setTabMode(TabLayout.MODE_FIXED);
        setIndicator(topBar, 10, 10);

        relBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            LinearLayout child = (LinearLayout) llTab.getChildAt(i);
            int f = child.getLayoutParams().width;
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }

    /**
     * 定义自己的ViewPager适配器。
     * 也可以使用FragmentPagerAdapter。关于这两者之间的区别，可以自己去搜一下。
     */
    public class MyFrageStatePagerAdapter extends FragmentStatePagerAdapter {
        List<Fragment> fragmentList;
        Context mContext;
        public boolean isApply=true;

        public boolean isApply() {
            return isApply;
        }

        public void setApply(boolean apply) {
            isApply = apply;
        }

        public MyFrageStatePagerAdapter(Context context, FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
            mContext=context;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mContext.getResources().getStringArray(R.array.company_order_title)[position];
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        @Override
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;
        }
    }
}
