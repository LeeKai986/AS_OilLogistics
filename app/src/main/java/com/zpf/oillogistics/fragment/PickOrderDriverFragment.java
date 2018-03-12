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
import com.zpf.oillogistics.activity.SelfDriverOrderDetailsActivity;
import com.zpf.oillogistics.adapter.DriverOrderAdapter;
import com.zpf.oillogistics.base.CyApplication;
import com.zpf.oillogistics.base.MessageWhat;
import com.zpf.oillogistics.bean.DriverOrder;
import com.zpf.oillogistics.bean.response.DriverOrderResponse;
import com.zpf.oillogistics.diy.DiyDialog;
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

public class PickOrderDriverFragment extends Fragment implements DriverOrderAdapter.DriverOrderListener {
    @BindView(R.id.lin_wu_pick_company_order)
    LinearLayout linWu;
    @BindView(R.id.lv_pick_company_order)
    PullToRefreshListView lvPick;
    Unbinder unbinder;
    private View view;
    private DriverOrderAdapter adapter;

    private List<DriverOrder> mList = new ArrayList();
    private HashMap<String, String> hp = new HashMap<>();
    int upPage = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_self_pick_company_order, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {

        lvPick.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
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

        lvPick.setScrollingWhileRefreshingEnabled(true);
        lvPick.setMode(PullToRefreshBase.Mode.BOTH);

    }

    /**
     * 请求数据接口
     */
    private void search() {

        hp.put("id", MyShare.getShared().getString("userId",""));
        hp.put("status","3");
        hp.put("page", upPage+"");

        SimplifyThread simplifyThread = new SimplifyThread(UrlUtil.URL_DRIVER_MYORDER, hp);
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

    /**
     * 提货接口
     */
    private void takeOrder(int id) {
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put("id", id+"");

        SimplifyThread simplifyThread = new SimplifyThread(UrlUtil.URL_DRIVER_TAKE, hashMap);
        simplifyThread.setOnKeyResultListener(new SimplifyThread.OnResultListener() {
            @Override
            public void resultBody(String res) {
                Message message = new Message();
                message.obj = res;
                message.what = MessageWhat.SELF_DRIVER_ORDER_TAKE;
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
            if(lvPick!=null)
            lvPick.onRefreshComplete();
            switch (msg.what) {
                case MessageWhat.SELF_ORDER_MYORDER:
                    if (msg.obj != null) {
                        try {
                            DriverOrderResponse order = gson.fromJson(msg.obj.toString(), DriverOrderResponse.class);

                            if (order.getStatus() == 0) {
                                mList.addAll(order.getData().getData());
                                if(mList.size()!=0) {
                                    linWu.setVisibility(View.GONE);
                                    if (adapter == null) {
                                        adapter = new DriverOrderAdapter(getActivity(), mList);
                                        adapter.setDriverOrderListener(PickOrderDriverFragment.this);
                                        lvPick.setAdapter(adapter);
                                    } else {
                                        adapter.notifyDataSetChanged();
                                    }
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
                case MessageWhat.SELF_DRIVER_ORDER_TAKE:
                    if (msg.obj != null) {
                        try {
                            DriverOrderResponse order = gson.fromJson(msg.obj.toString(), DriverOrderResponse.class);

                            if (order.getStatus() == 0) {
                                MyToast.show(CyApplication.getCyContext(), "货物已提出!");
                                mList.clear();
                                upPage = 1;
                                search();
                            } else if (order != null) {
                                MyToast.show(CyApplication.getCyContext(), order.getMsg());
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
        adapter=null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void toDriverLocation(int n,int flag) {
    }

    @Override
    public void config(final int n) {
        DiyDialog.hintTweBtnDialog(getActivity(),"确认提货吗?",new DiyDialog.HintTweBtnListener() {
            @Override
            public void confirm() {
                takeOrder(mList.get(n).getId());
            }
        });

    }

    @Override
    public void details(int n) {
        Intent intent = new Intent(getActivity(), SelfDriverOrderDetailsActivity.class);
        intent.putExtra("orderId", mList.get(n).getId() + "");
        getActivity().startActivity(intent);
    }

    @Override
    public void arrive(int n) {

    }
}
