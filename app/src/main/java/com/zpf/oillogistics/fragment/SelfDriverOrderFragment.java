package com.zpf.oillogistics.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.zpf.oillogistics.R;
import com.zpf.oillogistics.activity.SelfOrderDetailsActivity;
import com.zpf.oillogistics.base.BaseFragment;
import com.zpf.oillogistics.pulltorefreshlibrary.PullToRefreshListView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/19.
 * <p>
 * 个人中心司机订单填充view
 */

public class SelfDriverOrderFragment extends Fragment {

    // 载入数据控制
    public static int TYPE = 0;
    // 布局相关
    // plv
    @BindView(R.id.self_driver_order_plv)
    PullToRefreshListView plv;
    // adapter
    SelfDriverOrderAdapter adapter;

    public static SelfDriverOrderFragment newInstance(int type) {
        SelfDriverOrderFragment selfDriverOrderFragment = new SelfDriverOrderFragment();
        SelfDriverOrderFragment.TYPE = type;
        return selfDriverOrderFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_self_driver_order, container, false);
        ButterKnife.bind(this, view);
        intiViewAction(view, savedInstanceState);
        intiData();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected void intiData() {

    }

    protected void intiViewAction(View view, Bundle savedInstanceState) {
        adapter = new SelfDriverOrderAdapter();
        plv.setAdapter(adapter);
    }

    class SelfDriverOrderAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            SelfDriverOrderViewHolder vh;
            if (view == null) {
                view = LayoutInflater.from(getActivity()).inflate(R.layout.item_self_driver_order, viewGroup, false);
                vh = new SelfDriverOrderViewHolder();
                vh.allLl = view.findViewById(R.id.item_self_driver_order_all_ll);
                view.setTag(vh);
            } else {
                vh = (SelfDriverOrderViewHolder) view.getTag();
            }
            vh.allLl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().startActivity(new Intent(getActivity(), SelfOrderDetailsActivity.class));
                }
            });
            return view;
        }

        class SelfDriverOrderViewHolder {
            LinearLayout allLl;
        }
    }
}
