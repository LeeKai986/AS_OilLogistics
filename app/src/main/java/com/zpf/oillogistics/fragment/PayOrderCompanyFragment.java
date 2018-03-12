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
import com.zpf.oillogistics.activity.MapActivity;
import com.zpf.oillogistics.activity.OrderPayActivity;
import com.zpf.oillogistics.activity.SelfOrderDetailsActivity;
import com.zpf.oillogistics.adapter.CompanyOrderAdapter;
import com.zpf.oillogistics.base.CyApplication;
import com.zpf.oillogistics.base.MessageWhat;
import com.zpf.oillogistics.bean.CompanyOrder;
import com.zpf.oillogistics.bean.response.CompanyOrderResponse;
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
 * 企业所有订单
 */

public class PayOrderCompanyFragment extends Fragment implements CompanyOrderAdapter.CompanyOrderListener{

    @BindView(R.id.lin_wu_pay_company_order)
    LinearLayout linWu;
    @BindView(R.id.lv_pay_company_order)
    PullToRefreshListView lvPay;
    Unbinder unbinder;
    private View view;

    private CompanyOrderAdapter adapter;

    private List<CompanyOrder> mList = new ArrayList();
    private HashMap<String, String> hp = new HashMap<>();

    //页数
    int upPage = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_self_pay_company_order, container, false);

        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {

        lvPay.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                mList.clear();
                upPage = 1;
                searchPay();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                upPage++;
                searchPay();
            }
        });

        lvPay.setScrollingWhileRefreshingEnabled(true);
        lvPay.setMode(PullToRefreshBase.Mode.BOTH);

    }

    /**
     * 搜索接口
     */
    private void searchPay() {

        hp.put("companyname", MyShare.getShared().getString("companyname", ""));
        hp.put("status", "0");
        hp.put("page", upPage+"");

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

    /**
     * 支付订单
     */
    private void orderPay(String no,String money) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("no", no);
        hashMap.put("totalmoney", money);

        SimplifyThread simplifyThread = new SimplifyThread(UrlUtil.URL_ORDER_PAY, hashMap);
        simplifyThread.setOnKeyResultListener(new SimplifyThread.OnResultListener() {
            @Override
            public void resultBody(String res) {
                Message message = new Message();
                message.obj = res;
                message.what = MessageWhat.ORDER_PAY;
                handler.sendMessage(message);
            }

            @Override
            public void errorBody(String error) {
                handler.sendEmptyMessage(MessageWhat.REQUST_ERROR);
            }
        });
    }

    /**
     * 取消接口
     */
    private void cancleOrder(int id) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id", id + "");

        SimplifyThread simplifyThread = new SimplifyThread(UrlUtil.URL_ORDER_CANCEL, hashMap);
        simplifyThread.setOnKeyResultListener(new SimplifyThread.OnResultListener() {
            @Override
            public void resultBody(String res) {
                Message message = new Message();
                message.obj = res;
                message.what = MessageWhat.SELF_COMPANY_ORDER_CANCEL;
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
            if(lvPay!=null)
            lvPay.onRefreshComplete();
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
                                    adapter.setCompanyOrderListener(PayOrderCompanyFragment.this);
                                    lvPay.setAdapter(adapter);
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
                case MessageWhat.ORDER_PAY:
                    if (msg.obj != null) {
                        try {
                            CompanyOrderResponse order = gson.fromJson(msg.obj.toString(), CompanyOrderResponse.class);

                            if (order.getStatus() == 0) {
                                MyToast.show(CyApplication.getCyContext(), "订单支付成功!");
                                mList.clear();
                                upPage=1;
                                searchPay();
                            } else {
                                MyToast.show(CyApplication.getCyContext(), "暂无数据!");
                            }

                        } catch (Exception e) {
                            MyToast.show(CyApplication.getCyContext(), "返回数据异常!");
                        }

                    } else {
                        MyToast.show(CyApplication.getCyContext(), "返回数据失败!");
                    }
                    break;
                case MessageWhat.SELF_COMPANY_ORDER_CANCEL:
                    if (msg.obj != null) {
                        try {
                            CompanyOrderResponse order = gson.fromJson(msg.obj.toString(), CompanyOrderResponse.class);

                            if (order.getStatus() == 0) {
                                MyToast.show(CyApplication.getCyContext(), "订单取消成功!");
                                mList.clear();
                                upPage=1;
                                searchPay();
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
        searchPay();
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
    public void toDriverMsg(int n) {
    }

    @Override
    public void toDriverLocation(int n) {
    }

    @Override
    public void cancle(final int n) {

        DiyDialog.hintTweBtnDialog(getActivity(),"确定取消订单吗?",new DiyDialog.HintTweBtnListener() {
            @Override
            public void confirm() {
                cancleOrder(mList.get(n).id);
            }
        });
    }

    @Override
    public void pay(final int n) {

        DiyDialog.hintTweBtnDialog(getActivity(),"确定支付订单吗?",new DiyDialog.HintTweBtnListener() {
            @Override
            public void confirm() {
//                orderPay(mList.get(n).no,mList.get(n).money);
                DiyDialog.hintTweBtnDialog(getActivity(),"确定支付订单吗?",new DiyDialog.HintTweBtnListener() {
                    @Override
                    public void confirm() {
                        Intent intent = new Intent(getActivity(), OrderPayActivity.class);
                        intent.putExtra("money", mList.get(n).money);
                        intent.putExtra("orderNo", mList.get(n).no);
                        startActivityForResult(intent, 0);
                    }
                });
            }

        });
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
