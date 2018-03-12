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
import com.zpf.oillogistics.activity.HomeHaveCarActivity;
import com.zpf.oillogistics.activity.MapActivity;
import com.zpf.oillogistics.activity.OrderConfirmActivity;
import com.zpf.oillogistics.activity.OrderPayActivity;
import com.zpf.oillogistics.activity.SelfOrderDetailsActivity;
import com.zpf.oillogistics.adapter.CompanyOrderAdapter;
import com.zpf.oillogistics.base.CyApplication;
import com.zpf.oillogistics.base.MessageWhat;
import com.zpf.oillogistics.bean.CompanyOrder;
import com.zpf.oillogistics.bean.response.CompanyOrderResponse;
import com.zpf.oillogistics.bean.response.HaveCarResponse;
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

public class AllOrderCompanyFragment extends Fragment implements CompanyOrderAdapter.CompanyOrderListener {
    @BindView(R.id.lin_wu_all_company_order)
    LinearLayout linWu;
    @BindView(R.id.lv_all_company_order)
    PullToRefreshListView lvAll;
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
        upPage = 1;
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
        Intent intent = new Intent(getActivity(), MapActivity.class);
        intent.putExtra("nav", false);
        intent.putExtra("driverId", mList.get(n).getDriver_phone());
        getActivity().startActivity(intent);
    }

    @Override
    public void cancle(final int n) {

        DiyDialog.hintTweBtnDialog(getActivity(), "确定取消订单吗?", new DiyDialog.HintTweBtnListener() {
            @Override
            public void confirm() {
                cancleOrder(mList.get(n).id);
            }
        });
    }

    @Override
    public void pay(final int n) {
        DiyDialog.hintTweBtnDialog(getActivity(), "确定支付订单吗?", new DiyDialog.HintTweBtnListener() {
            @Override
            public void confirm() {
                Intent intent = new Intent(getActivity(), OrderPayActivity.class);
                intent.putExtra("money", mList.get(n).money);
                intent.putExtra("orderNo", mList.get(n).no);
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    public void delete(final int n) {
        DiyDialog.hintTweBtnDialog(getActivity(), "确定删除订单吗?", new DiyDialog.HintTweBtnListener() {
            @Override
            public void confirm() {
                deleteOrder(mList.get(n).id);
            }
        });
    }

    @Override
    public void config(final int n) {
        DiyDialog.hintTweBtnDialog(getActivity(), "确认收货吗?", new DiyDialog.HintTweBtnListener() {
            @Override
            public void confirm() {
                configOrder(mList.get(n).id);
            }
        });
    }

    @Override
    public void details(int n) {
        Intent intent = new Intent(getActivity(), SelfOrderDetailsActivity.class);
        intent.putExtra("orderId", mList.get(n).getId() + "");
        getActivity().startActivity(intent);
    }

    /**
     * 请求数据接口
     */
    private void search() {
        hp.put("companyname", MyShare.getShared().getString("companyname", ""));
        hp.put("status", "");
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

    /**
     * 支付订单
     */
    private void orderPay(String no, String money) {
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
     * 删除接口
     */
    private void deleteOrder(int id) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id", id + "");

        SimplifyThread simplifyThread = new SimplifyThread(UrlUtil.URL_ORDER_DELETE, hashMap);
        simplifyThread.setOnKeyResultListener(new SimplifyThread.OnResultListener() {
            @Override
            public void resultBody(String res) {
                Message message = new Message();
                message.obj = res;
                message.what = MessageWhat.SELF_COMPANY_ORDER_DELETE;
                handler.sendMessage(message);
            }

            @Override
            public void errorBody(String error) {
                handler.sendEmptyMessage(MessageWhat.REQUST_ERROR);
            }
        });
    }

    /**
     * 提交接口
     */
    private void configOrder(int id) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id", id + "");

        SimplifyThread simplifyThread = new SimplifyThread(UrlUtil.URL_ORDER_TAKE, hashMap);
        simplifyThread.setOnKeyResultListener(new SimplifyThread.OnResultListener() {
            @Override
            public void resultBody(String res) {
                Message message = new Message();
                message.obj = res;
                message.what = MessageWhat.SELF_COMPANY_ORDER_TAKE;
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
                            CompanyOrderResponse order = gson.fromJson(msg.obj.toString(), CompanyOrderResponse.class);

                            if (order.getStatus() == 0) {
                                mList.addAll(order.getData().getData());
                                linWu.setVisibility(View.GONE);

                            } else {
//                                MyToast.show(CyApplication.getCyContext(), "暂无数据!");
                            }

                            if (adapter == null) {
                                adapter = new CompanyOrderAdapter(getActivity(), mList);
                                adapter.setCompanyOrderListener(AllOrderCompanyFragment.this);
                                lvAll.setAdapter(adapter);
                            } else {
                                adapter.notifyDataSetChanged();
                            }

                        } catch (Exception e) {
                            MyToast.show(CyApplication.getCyContext(), "返回数据异常!");
                        }

                    } else {
                        MyToast.show(CyApplication.getCyContext(), "返回数据失败!");
                    }
                    break;
                case MessageWhat.SELF_COMPANY_ORDER_DELETE:
                    if (msg.obj != null) {
                        try {
                            CompanyOrderResponse order = gson.fromJson(msg.obj.toString(), CompanyOrderResponse.class);

                            if (order.getStatus() == 0) {
                                MyToast.show(CyApplication.getCyContext(), "订单删除成功!");
                                mList.clear();
                                upPage = 1;
                                search();
                            } else {
                                MyToast.show(CyApplication.getCyContext(), "删除失败!");
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
                                upPage = 1;
                                search();
                            } else {
                                MyToast.show(CyApplication.getCyContext(), "取消失败!");
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
                                upPage = 1;
                                search();
                            } else {
                                MyToast.show(CyApplication.getCyContext(), "支付失败!");
                            }

                        } catch (Exception e) {
                            MyToast.show(CyApplication.getCyContext(), "返回数据异常!");
                        }

                    } else {
                        MyToast.show(CyApplication.getCyContext(), "返回数据失败!");
                    }
                    break;
                case MessageWhat.SELF_COMPANY_ORDER_TAKE:
                    if (msg.obj != null) {
                        try {
                            CompanyOrderResponse order = gson.fromJson(msg.obj.toString(), CompanyOrderResponse.class);

                            if (order.getStatus() == 0) {
                                MyToast.show(CyApplication.getCyContext(), "订单提交成功!");
                                mList.clear();
                                upPage = 1;
                                search();
                            } else {
                                MyToast.show(CyApplication.getCyContext(), "订单提交失败!");
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
