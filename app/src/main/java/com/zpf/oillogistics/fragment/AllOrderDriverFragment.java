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
import com.zpf.oillogistics.activity.MapActivity;
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

public class AllOrderDriverFragment extends Fragment implements DriverOrderAdapter.DriverOrderListener {
    @BindView(R.id.lin_wu_all_company_order)
    LinearLayout linWu;
    @BindView(R.id.lv_all_company_order)
    PullToRefreshListView lvAll;
    Unbinder unbinder;
    private View view;
    private DriverOrderAdapter adapter;

    private List<DriverOrder> mList = new ArrayList();
    private HashMap<String, String> hp = new HashMap<>();

    //页数
    int upPage = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_self_all_company_order, container, false);
        unbinder = ButterKnife.bind(this, view);

        init();

        return view;
    }

    private void init() {

        lvAll.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
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

        lvAll.setScrollingWhileRefreshingEnabled(true);
        lvAll.setMode(PullToRefreshBase.Mode.BOTH);

    }


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
    public void toDriverLocation(int n, int flag) {
        Intent intent = new Intent(getActivity(), MapActivity.class);

        intent.putExtra("nav", true);
        intent.putExtra("location", true);
        if (flag == 0) {
            intent.putExtra("city", mList.get(n).getAddress());
            intent.putExtra("adress", mList.get(n).getAddress());
        } else if (flag == 2) {
            intent.putExtra("elatitude", mList.get(n).getLatitude());
            intent.putExtra("elongitude", mList.get(n).getLongitude());
            intent.putExtra("ecity", mList.get(n).getToplace());
            intent.putExtra("eadress", mList.get(n).getToaddress());
        } else {
            intent.putExtra("ecity", mList.get(n).getPlace());
            intent.putExtra("elatitude", mList.get(n).getS_latitude());
            intent.putExtra("elongitude", mList.get(n).getS_longitude());
            intent.putExtra("eadress", mList.get(n).getAddress());
        }
        getActivity().startActivity(intent);

    }

    @Override
    public void config(final int n) {
        DiyDialog.hintTweBtnDialog(getActivity(), "确认提货吗?", new DiyDialog.HintTweBtnListener() {
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
    public void arrive(final int n) {
        Intent intent = new Intent(getActivity(), SelfDriverOrderDetailsActivity.class);
        intent.putExtra("orderId", mList.get(n).getId() + "");
        getActivity().startActivity(intent);
    }

    /**
     * 请求数据接口
     */
    private void search() {
        hp.put("id", MyShare.getShared().getString("userId", ""));
        hp.put("status", "");
        hp.put("page", upPage + "");

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
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id", id + "");

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
            if (lvAll != null)
                lvAll.onRefreshComplete();
            switch (msg.what) {
                case MessageWhat.SELF_ORDER_MYORDER:
                    if (msg.obj != null) {
                        try {
                            DriverOrderResponse order = gson.fromJson(msg.obj.toString(), DriverOrderResponse.class);

                            if (order.getStatus() == 0) {
                                mList.addAll(order.getData().getData());
                                if (mList.size() != 0) {
                                    linWu.setVisibility(View.GONE);
                                    if (adapter == null) {
                                        adapter = new DriverOrderAdapter(getActivity(), mList);
                                        adapter.setDriverOrderListener(AllOrderDriverFragment.this);
                                        lvAll.setAdapter(adapter);
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
}
