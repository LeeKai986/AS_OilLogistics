package com.zpf.oillogistics.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.zpf.oillogistics.R;
import com.zpf.oillogistics.activity.DriverMsgActivity;
import com.zpf.oillogistics.activity.SelfOrderDetailsActivity;
import com.zpf.oillogistics.adapter.CompanyOrderAdapter;
import com.zpf.oillogistics.base.CyApplication;
import com.zpf.oillogistics.base.MessageWhat;
import com.zpf.oillogistics.bean.CompanyOrder;
import com.zpf.oillogistics.bean.response.CompanyOrderResponse;
import com.zpf.oillogistics.net.SimplifyThread;
import com.zpf.oillogistics.net.UrlUtil;
import com.zpf.oillogistics.pulltorefreshlibrary.PullToRefreshBase;
import com.zpf.oillogistics.pulltorefreshlibrary.PullToRefreshListView;
import com.zpf.oillogistics.utils.MyShare;
import com.zpf.oillogistics.utils.MyToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/9/19.
 */

public class CompleteOrderCompanyFragment extends Fragment implements CompanyOrderAdapter.CompanyOrderListener {
    @BindView(R.id.lin_wu_complete_company_order)
    LinearLayout linWu;
    @BindView(R.id.lv_complete_company_order)
    PullToRefreshListView lvComplete;
    Unbinder unbinder;
    private View view;
    private CompanyOrderAdapter adapter;

    private List<CompanyOrder> mList = new ArrayList();
    private HashMap<String, String> hp = new HashMap<>();
    int upPage = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_self_complete_company_order, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {

        lvComplete.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                mList.clear();
                upPage = 1;
                search();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                upPage++;
                search();
            }
        });

        lvComplete.setScrollingWhileRefreshingEnabled(true);
        lvComplete.setMode(PullToRefreshBase.Mode.BOTH);

    }

    /**
     * 请求数据接口
     */
    private void search() {

        hp.put("companyname", MyShare.getShared().getString("companyname", ""));
        hp.put("status", "5");
        hp.put("page", upPage + "");

        SimplifyThread simplifyThread = new SimplifyThread(UrlUtil.URL_ORDER_MYORDER, hp);
        simplifyThread.setOnKeyResultListener(new SimplifyThread.OnResultListener() {
            @Override
            public void resultBody(String res) {
                Message message = new Message();
                message.obj = res;
                message.what = MessageWhat.SELF_ORDER_MYORDER;
                handler.sendMessage(message);
            }

            @Override
            public void errorBody(String error) {
                handler.sendEmptyMessage(MessageWhat.REQUST_ERROR);
            }
        });
    }


    private Gson gson = new Gson();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (lvComplete != null)
                lvComplete.onRefreshComplete();
            switch (msg.what) {
                case MessageWhat.SELF_ORDER_MYORDER:
                    if (msg.obj != null) {
                        try {
                            CompanyOrderResponse order = gson.fromJson(msg.obj.toString(), CompanyOrderResponse.class);

                            if (order.getStatus() == 0) {
                                mList.addAll(order.getData().getData());
                                linWu.setVisibility(View.GONE);
                                if (adapter == null) {
                                    adapter = new CompanyOrderAdapter(getActivity(), mList);
                                    adapter.setCompanyOrderListener(CompleteOrderCompanyFragment.this);
                                    lvComplete.setAdapter(adapter);
                                } else {
                                    adapter.notifyDataSetChanged();
                                }

                            } else {
//                                MyToast.show(CyApplication.getCyContext(), "暂无数据!");
                            }

                        } catch (Exception e) {
                            MyToast.show(CyApplication.getCyContext(), "返回数据异常!");
                        }

                    } else {
                        MyToast.show(CyApplication.getCyContext(), "返回数据失败!");
                    }
                    break;
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        search();
    }

    @Override
    public void onPause() {
        super.onPause();
        mList.clear();
        adapter = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void toDriverMsg(int n) {
        Intent intent = new Intent(getActivity(), DriverMsgActivity.class);
        intent.putExtra("driverId", mList.get(n).getId() + "");
        getActivity().startActivity(intent);
    }

    @Override
    public void toDriverLocation(int n) {
    }

    @Override
    public void cancle(int n) {
    }

    @Override
    public void pay(int n) {
//        startActivity(new Intent(getActivity(), ));
    }

    @Override
    public void delete(int n) {

    }

    @Override
    public void config(int n) {

    }

    @Override
    public void details(int n) {
        Intent intent = new Intent(getActivity(), SelfOrderDetailsActivity.class);
        intent.putExtra("orderId", mList.get(n).getId() + "");
        getActivity().startActivity(intent);
    }
}
