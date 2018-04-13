package com.zpf.oillogistics.fragment;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dream.life.library.throwable.utils.LogUtil;
import com.google.gson.Gson;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.zpf.oillogistics.R;
import com.zpf.oillogistics.activity.HomeHaveCarActivity;
import com.zpf.oillogistics.activity.HomeHaveProductActivity;
import com.zpf.oillogistics.activity.HomeInformationActivity;
import com.zpf.oillogistics.activity.HomeOilActivity;
import com.zpf.oillogistics.activity.HomeSeekActivity;
import com.zpf.oillogistics.activity.HomeWantBuyActivity;
import com.zpf.oillogistics.activity.InformationDetailsActivity;
import com.zpf.oillogistics.activity.MsgHomeActivity;
import com.zpf.oillogistics.base.CyApplication;
import com.zpf.oillogistics.base.MessageWhat;
import com.zpf.oillogistics.bean.MsgClickBean;
import com.zpf.oillogistics.bean.Newa;
import com.zpf.oillogistics.bean.Slid;
import com.zpf.oillogistics.bean.response.IndexResponse;
import com.zpf.oillogistics.bean.response.PriceTrendResponse;
import com.zpf.oillogistics.customview.NoSlidingListView;
import com.zpf.oillogistics.net.SimplifyThread;
import com.zpf.oillogistics.net.UrlUtil;
import com.zpf.oillogistics.utils.DateTimeUtil;
import com.zpf.oillogistics.utils.MyShare;
import com.zpf.oillogistics.utils.MyToast;
import com.zpf.oillogistics.utils.NormalUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by Administrator on 2017/9/13.
 * <p>
 * 主页
 */

public class HomeFragment extends Fragment implements BGARefreshLayout.BGARefreshLayoutDelegate {

    // 布局相关
    // 检索
    @BindView(R.id.home_main_seek_tv)
    TextView seekTv;
    // 信息
    @BindView(R.id.home_main_msg_iv)
    FrameLayout msgIv;
    // 消息数量
    @BindView(R.id.msg_home_system_num_tv)
    TextView msgtvNum;
    // 轮播图
    @BindView(R.id.home_main_rpv)
    RollPagerView rpv;
    // 中间gridbutton
    // 石油
    @BindView(R.id.home_main_oil_tv)
    TextView oilTv;
    // 化工
    @BindView(R.id.home_main_chemitry_tv)
    TextView chemistryTv;
    // 求购
    @BindView(R.id.home_main_want_to_buy_tv)
    TextView wantToBuyTv;
    // 货找车
    @BindView(R.id.home_main_have_product_tv)
    TextView haveProductTv;
    // 查看更多资讯
    @BindView(R.id.homepage_msg_more_tv)
    TextView msgMoreTv;
    // listviw
    @BindView(R.id.homepage_msg_lv)
    NoSlidingListView msgLv;
    //车找货
    @BindView(R.id.home_main_find_product_tv)
    TextView tvFindProduct;
    // 左侧价格
    @BindView(R.id.home_main_left_price_tv)
    TextView leftPriceTv;
    // 右侧价格
    @BindView(R.id.home_main_right_price_tv)
    TextView rightPriceTv;
    // 左侧上涨
    @BindView(R.id.home_main_left_up_price_tv)
    TextView leftUpTv;
    // 右侧上涨
    @BindView(R.id.home_main_right_up_price_tv)
    TextView rightUpTv;
    // 左侧比率
    @BindView(R.id.home_main_left_rate_tv)
    TextView leftRateTv;
    // 右侧比率
    @BindView(R.id.home_main_right_rate_tv)
    TextView rightRateTv;
    // 时间
    @BindView(R.id.home_main_all_time_tv)
    TextView allTimeTv;
    // adapter
    HomeAdapter adapter;
    @BindView(R.id.ll_trend1)
    LinearLayout llTrend1;
    @BindView(R.id.sv_trend1)
    HorizontalScrollView svTrend1;
    @BindView(R.id.ll_trend2)
    LinearLayout llTrend2;
    @BindView(R.id.sv_trend2)
    HorizontalScrollView svTrend2;
    @BindView(R.id.price_trend)
    LinearLayout priceTrendView;
    @BindView(R.id.tv_1)
    TextView tv1;
    @BindView(R.id.iv_2)
    ImageView iv2;
    @BindView(R.id.tv_3)
    TextView tv3;
    @BindView(R.id.iv_4)
    ImageView iv4;
    @BindView(R.id.tv_5)
    TextView tv5;
    @BindView(R.id.tv_21)
    TextView tv21;
    @BindView(R.id.tv_22)
    TextView tv22;
    @BindView(R.id.tv_23)
    TextView tv23;
    @BindView(R.id.tv_24)
    TextView tv24;
    @BindView(R.id.tv_25)
    TextView tv25;
    @BindView(R.id.tv_26)
    TextView tv26;
    @BindView(R.id.tv_27)
    TextView tv27;
    @BindView(R.id.bga_refresh)
    BGARefreshLayout bgaRefresh;


    private FragmentActivity fgActy;

    //录播图数据集合
    private List<Slid> rpvList = new ArrayList<>();
    private List<Newa> msgList = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_home_main, container, false);
        ButterKnife.bind(this, view);
        intiView();
//        rollImage();
//        local();
        bgaRefresh.setDelegate(this);
        bgaRefresh.setRefreshViewHolder(new BGANormalRefreshViewHolder(getActivity(), true));
        bgaRefresh.setIsShowLoadingMoreView(false);
        return view;
    }

    /**
     * 开始刷新
     *
     * @param refreshLayout
     */
    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        indexData();
        homeData();
        homePriceTrendData();

    }

    /**
     * 开始加载更多。由于监听了ScrollView、RecyclerView、AbsListView滚动到底部的事件，所以这里采用返回boolean来处理是否加载更多。否则使用endLoadingMore方法会失效
     *
     * @param refreshLayout
     * @return 如果要开始加载更多则返回true，否则返回false。（返回false的场景：没有网络、一共只有x页数据并且已经加载了x页数据了）
     */
    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }

    private void setOilTrendData(PriceTrendResponse priceTrend) {
        if (priceTrend.getStatus() == 0) {
            if (priceTrend.getData() != null && priceTrend.getData().size() > 0) {//显示数据
                PriceTrendResponse.DataBean bean = priceTrend.getData().get(0);
                priceTrendView.setVisibility(View.VISIBLE);
                svTrend1.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        //设置ScrollView取消手动滑动
                        return true;
                    }
                });
                svTrend2.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        //设置ScrollView取消手动滑动
                        return true;
                    }
                });
                //动态添加TextView  imageview
                float scrollWidth1 = 0f;//滚动的总宽度
                float scrollWidth2 = 0f;//滚动的总宽度
                tv1.setText(bean.getTrend_date() + bean.getTitle1() + ":" + bean.getPrice1());
                tv3.setText(bean.getPercent1() + " " + bean.getTitle2() + ":" + bean.getPrice2());
                tv5.setText(bean.getPercent2());
                if (!TextUtils.isEmpty(bean.getTrend1()) && bean.getTrend1().equals("up")) {
                    iv2.setImageResource(R.mipmap.price_up);
                } else {
                    iv2.setImageResource(R.mipmap.price_down);
                }
                if (!TextUtils.isEmpty(bean.getTrend2()) && bean.getTrend2().equals("up")) {
                    iv4.setImageResource(R.mipmap.price_up);
                } else {
                    iv4.setImageResource(R.mipmap.price_down);
                }
                tv21.setText(bean.getMsg2_title() + ":");
                tv22.setText(bean.getMsg2_percent());
                if (!TextUtils.isEmpty(bean.getMsg2_percent()) && bean.getMsg2_percent().contains("-")) {
                    tv22.setTextColor(getResources().getColor(R.color.down));
                    tv22.setText(bean.getMsg2_percent());
                } else {
                    tv22.setTextColor(getResources().getColor(R.color.up));
                }
                tv26.setText(bean.getMsg2_trend());
                if (!TextUtils.isEmpty(bean.getMsg2_trend()) && bean.getMsg2_trend().contains("下")) {
                    tv26.setTextColor(getResources().getColor(R.color.down));
                } else {
                    tv26.setTextColor(getResources().getColor(R.color.up));
                }
                tv23.setText("第");
                tv24.setText(bean.getMsg2_day());
                tv25.setText("工作日");
                tv27.setText(bean.getMsg2_unit());
                int spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                tv1.measure(spec, spec);
                tv3.measure(spec, spec);
                tv5.measure(spec, spec);
                iv2.measure(spec, spec);
                iv4.measure(spec, spec);
                tv21.measure(spec, spec);
                tv22.measure(spec, spec);
                tv23.measure(spec, spec);
                tv24.measure(spec, spec);
                tv25.measure(spec, spec);
                tv26.measure(spec, spec);
                tv27.measure(spec, spec);
                //每个TextView的宽度相加得到总宽度
                scrollWidth1 = tv1.getMeasuredWidth() + tv3.getMeasuredWidth() + tv5.getMeasuredWidth() + iv2.getMeasuredWidth() + iv4.getMeasuredWidth();
                scrollWidth2 = tv21.getMeasuredWidth() + tv22.getMeasuredWidth() + tv23.getMeasuredWidth() + tv24.getMeasuredWidth() +
                        tv25.getMeasuredWidth() + tv26.getMeasuredWidth() + tv27.getMeasuredWidth();
                if (scrollWidth1 > getScreenWidth()) {
                    startScroll(scrollWidth1, llTrend1);
                }
                if (scrollWidth2 > getScreenWidth()) {
                    startScroll(scrollWidth2, llTrend2);
                }
            } else {
                priceTrendView.setVisibility(View.GONE);
            }
        } else {
            MyToast.show(CyApplication.getCyContext(), priceTrend.getMsg());
        }
    }

    /**
     * 开始滚动
     */
    private void startScroll(float scrollWidth, LinearLayout view) {
        if (scrollWidth != 0) {
            //属性动画位移，从屏幕右边向左移动到屏幕外scrollWidth长度的距离
            final long duration = (long) (scrollWidth * 18);//动画时长
            ObjectAnimator anim = ObjectAnimator.ofFloat(view, "translationX", 100, -scrollWidth);
            anim.setDuration(duration);//动画时长
            anim.setInterpolator(new LinearInterpolator());
            anim.setRepeatMode(ValueAnimator.RESTART);//无限重复
            anim.setRepeatCount(ValueAnimator.INFINITE);
            anim.start();
        }
    }

    /**
     * 获得屏幕宽度
     */
    private int getScreenWidth() {
        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fgActy = (FragmentActivity) context;
    }


    private void intiView() {
        rpv.setFocusable(true);
        rpv.setFocusableInTouchMode(true);
        rpv.requestFocus();
        seekTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!NormalUtils.personDataPass(fgActy)) {
//                    MyToast.show(CyApplication.getCyContext(), "请先验证身份");
//                    NormalUtils.personDataVerify(fgActy);
                    return;
                }
                fgActy.startActivity(new Intent(fgActy, HomeSeekActivity.class));
            }
        });
        msgIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!NormalUtils.personDataPass(fgActy)) {
//                    MyToast.show(CyApplication.getCyContext(), "请先验证身份");
//                    NormalUtils.personDataVerify(fgActy);
                    return;
                }
                fgActy.startActivity(new Intent(fgActy, MsgHomeActivity.class));
            }
        });
        oilTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!NormalUtils.personDataPass(fgActy)) {
//                    MyToast.show(CyApplication.getCyContext(), "请先验证身份");
//                    NormalUtils.personDataVerify(fgActy);
                    return;
                }
                Intent intent = new Intent(fgActy, HomeOilActivity.class);
                intent.putExtra("manage", "1");
                fgActy.startActivity(intent);
            }
        });
        chemistryTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!NormalUtils.personDataPass(fgActy)) {
//                    MyToast.show(CyApplication.getCyContext(), "请先验证身份");
//                    NormalUtils.personDataVerify(fgActy);
                    return;
                }
                Intent intent = new Intent(fgActy, HomeOilActivity.class);
                intent.putExtra("manage", "2");
                fgActy.startActivity(intent);
            }
        });
        wantToBuyTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!NormalUtils.personDataPass(fgActy)) {
//                    MyToast.show(CyApplication.getCyContext(), "请先验证身份");
//                    NormalUtils.personDataVerify(fgActy);
                    return;
                }
                fgActy.startActivity(new Intent(fgActy, HomeWantBuyActivity.class));
            }
        });
        haveProductTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!NormalUtils.personDataPass(fgActy)) {
//                    MyToast.show(CyApplication.getCyContext(), "请先验证身份");
//                    NormalUtils.personDataVerify(fgActy);
                    return;
                }
                fgActy.startActivity(new Intent(fgActy, HomeHaveProductActivity.class));
            }
        });
        tvFindProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (MyShare.getShared().getString("userType", "").equals("1")) {
//                    MyToast.show(CyApplication.getCyContext(), "您当前账号为个人账号, 不可进入车找货功能");
//                    return;
//                }
//                if (MyShare.getShared().getString("userType", "").equals("2")) {
//                    MyToast.show(CyApplication.getCyContext(), "您当前账号为公司账号, 不可进入车找货功能");
//                    return;
//                }
                if (!NormalUtils.personDataPass(fgActy)) {
//                    MyToast.show(CyApplication.getCyContext(), "请先验证身份");
//                    NormalUtils.personDataVerify(fgActy);
                    return;
                }
                fgActy.startActivity(new Intent(fgActy, HomeHaveCarActivity.class));
            }
        });
        msgMoreTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fgActy.startActivity(new Intent(fgActy, HomeInformationActivity.class));

            }
        });

    }

    private void rollImage() {
        rpv.setAdapter(new TestLoopAdapter(rpv, rpvList));
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        indexData();
        homeDatar();
        homePriceTrendData();
    }

    @Override
    public void onResume() {
        super.onResume();
        homeMsgNum();
    }

    //消息数量
    private void homeMsgNum() {
        HashMap<String, String> getAttentionNum = new HashMap<>();
        getAttentionNum.put("id", MyShare.getShared().getString("userId", ""));
        SimplifyThread getAttentionThread = new SimplifyThread(UrlUtil.URL_USER_NUM, getAttentionNum);
        getAttentionThread.setOnKeyResultListener(new SimplifyThread.OnResultListener() {
            @Override
            public void resultBody(String res) {
                Message msg = new Message();
                msg.obj = res;
                msg.what = 4;
                handler.sendMessage(msg);
            }

            @Override
            public void errorBody(String error) {
                Message msg = new Message();
                msg.obj = error;
                msg.what = 0;
                handler.sendMessage(msg);
            }
        });
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        LogUtil.e("----hidden-------" + hidden);
    }

    /**
     * 主页轮播和资讯
     */
    private void indexData() {
        HashMap<String, String> hp = new HashMap<>();
        hp.put("page_size", "20");
        SimplifyThread simplifyThread = new SimplifyThread(UrlUtil.URL_SLIDESHOW_INDEX, hp);
        simplifyThread.setOnKeyResultListener(new SimplifyThread.OnResultListener() {
            @Override
            public void resultBody(String res) {
                Message message = new Message();
                message.obj = res;
                message.what = MessageWhat.SLIDESHOW_INDEX;
                handler.sendMessage(message);
            }

            @Override
            public void errorBody(String error) {
                Message msg = new Message();
                msg.what = 0;
                msg.obj = error;
                handler.sendMessage(msg);
            }
        });
    }

    /**
     * 原油数据
     */
    private void homeData() {
        SimplifyThread homeData = new SimplifyThread(UrlUtil.HOME_DATA, new HashMap<String, String>());
        homeData.setOnGetResultListener(new SimplifyThread.OnResultListener() {
            @Override
            public void resultBody(String res) {
                Log.e("ATG", res.toString());

                Message msg = new Message();
                msg.what = 1;
                msg.obj = res;
                handler.sendMessage(msg);
            }

            @Override
            public void errorBody(String error) {
                Message msg = new Message();
                msg.what = 0;
                msg.obj = error;
                handler.sendMessage(msg);
            }
        });
    }

    /**
     * 原油数据
     */
    private void homeDatar() {
        mHandler.postDelayed(r, 100);//延时100毫秒
    }

    Handler mHandler = new Handler();
    Runnable r = new Runnable() {
        @Override
        public void run() {
            homeData();
            //do something
            //每隔120s循环执行run方法
            mHandler.postDelayed(this, 120000);
        }
    };

    /**
     * 原油价格走势数据
     */
    private void homePriceTrendData() {
        SimplifyThread homeData = new SimplifyThread(UrlUtil.URL_PRICE_TREND, new HashMap<String, String>());
        homeData.setOnGetResultListener(new SimplifyThread.OnResultListener() {
            @Override
            public void resultBody(String res) {
                Log.e("ATG", res.toString());

                Message msg = new Message();
                msg.what = 3;
                msg.obj = res;
                handler.sendMessage(msg);
            }

            @Override
            public void errorBody(String error) {
                Message msg = new Message();
                msg.what = 0;
                msg.obj = error;
                handler.sendMessage(msg);
            }
        });
    }

    private Gson gson = new Gson();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MessageWhat.SLIDESHOW_INDEX:
                    if (msg.obj != null) {
                        try {
                            IndexResponse index = gson.fromJson(msg.obj.toString(), IndexResponse.class);

                            if (index.getStatus() == 0) {
                                rpvList = index.getData().getSlid().getData();
                                msgList = index.getData().getNewa().getData();

                                if (rpvList != null)
                                    rollImage();

                                if (msgList != null) {
                                    int i = 120;
                                    msgLv.getLayoutParams().height = msgList.size() * i + 90;
                                    msgLv.setAdapter(new HomeAdapter());
                                    msgLv.setOnTouchListener(new View.OnTouchListener() {
                                        @Override
                                        public boolean onTouch(View view, MotionEvent motionEvent) {
                                            switch (motionEvent.getAction()) {
                                                case MotionEvent.ACTION_MOVE:
                                                    return true;
                                                default:
                                                    break;
                                            }
                                            return true;
                                        }
                                    });
                                }

                            } else if (index != null) {
                                MyToast.show(CyApplication.getCyContext(), index.getMsg());
//                                if (!MyShare.getShared().getString("userId", "").equals("")) {
//                                    if (index.getMsg().equals("您的账号处于禁用状态，请更换账号重新登录")) {
//                                        EMClient.getInstance().logout(true);
//                                        SharedPreferences.Editor editor = MyShare.getShared().edit();
//                                        editor.clear();
//                                        editor.commit();
//                                        Intent intent = new Intent(fgActy, LoginActivity.class);
//                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                        startActivity(intent);
//                                        break;
//                                    }
//                                }
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
                case 1:
                    try {
                        JSONObject obj = new JSONObject(hFiveToJs(msg.obj.toString()));
                        String hq_str_hf_OIL = obj.getString("hq_str_hf_OIL");
                        String hq_str_hf_CL = obj.getString("hq_str_hf_CL");
                        String[] hq_str_hf_OILArr = hq_str_hf_OIL.split(",");
                        String[] hq_str_hf_CLArr = hq_str_hf_CL.split(",");
                        allTimeTv.setText(hq_str_hf_OILArr[6]);
                        leftPriceTv.setText(hq_str_hf_OILArr[0]);
                        leftRateTv.setText(Math.round(Double.parseDouble(hq_str_hf_OILArr[1]) * 100) / 100.00 + "%");
                        leftUpTv.setText((Math.round((Double.parseDouble(hq_str_hf_OILArr[0]) -
                                Double.parseDouble(hq_str_hf_OILArr[7])) * 100) / 100.00f) + "");

                        if (leftUpTv.getText().toString().contains("-")) {
                            leftUpTv.setTextColor(fgActy.getResources().getColor(R.color.home_yuan_reduce_color));
                        } else if (leftUpTv.getText().toString().equals("0.00")) {
                            leftUpTv.setTextColor(fgActy.getResources().getColor(R.color.home_yuan_increase_color));
                        } else {
                            leftUpTv.setTextColor(fgActy.getResources().getColor(R.color.home_yuan_increase_color));
                            leftUpTv.setText("+" + leftUpTv.getText().toString());
                        }

                        if (leftRateTv.getText().toString().contains("-")) {
                            leftRateTv.setTextColor(fgActy.getResources().getColor(R.color.home_yuan_reduce_color));
                            leftPriceTv.setTextColor(fgActy.getResources().getColor(R.color.home_yuan_reduce_color));
                        } else if (leftRateTv.getText().toString().equals("0.00")) {
                            leftRateTv.setTextColor(fgActy.getResources().getColor(R.color.home_yuan_increase_color));
                            leftPriceTv.setTextColor(fgActy.getResources().getColor(R.color.home_yuan_increase_color));
                        } else {
                            leftRateTv.setTextColor(fgActy.getResources().getColor(R.color.home_yuan_increase_color));
                            leftPriceTv.setTextColor(fgActy.getResources().getColor(R.color.home_yuan_increase_color));
                            leftRateTv.setText("+" + leftRateTv.getText().toString());
                        }

                        rightPriceTv.setText(hq_str_hf_CLArr[0]);
                        rightRateTv.setText(Math.round(Double.parseDouble(hq_str_hf_CLArr[1]) * 100) / 100.00 + "%");
                        rightUpTv.setText((Math.round((Double.parseDouble(hq_str_hf_CLArr[0]) -
                                Double.parseDouble(hq_str_hf_CLArr[7])) * 100) / 100.00f) + "");

                        if (rightUpTv.getText().toString().contains("-")) {
                            rightUpTv.setTextColor(fgActy.getResources().getColor(R.color.home_yuan_reduce_color));
                        } else if (rightUpTv.getText().toString().equals("0.00")) {
                            rightUpTv.setTextColor(fgActy.getResources().getColor(R.color.home_yuan_increase_color));
                        } else {
                            rightUpTv.setTextColor(fgActy.getResources().getColor(R.color.home_yuan_increase_color));
                            rightUpTv.setText("+" + rightUpTv.getText().toString());
                        }

                        if (rightRateTv.getText().toString().contains("-")) {
                            rightRateTv.setTextColor(fgActy.getResources().getColor(R.color.home_yuan_reduce_color));
                            rightPriceTv.setTextColor(fgActy.getResources().getColor(R.color.home_yuan_reduce_color));
                        } else if (rightRateTv.getText().toString().equals("0.00")) {
                            rightRateTv.setTextColor(fgActy.getResources().getColor(R.color.home_yuan_increase_color));
                            rightPriceTv.setTextColor(fgActy.getResources().getColor(R.color.home_yuan_increase_color));
                        } else {
                            rightRateTv.setTextColor(fgActy.getResources().getColor(R.color.home_yuan_increase_color));
                            rightPriceTv.setTextColor(fgActy.getResources().getColor(R.color.home_yuan_increase_color));
                            rightRateTv.setText("+" + rightRateTv.getText().toString());
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    if (msg.obj != null) {
                        try {
                            PriceTrendResponse priceTrend = gson.fromJson(msg.obj.toString(), PriceTrendResponse.class);
                            if (priceTrend != null) {
                                setOilTrendData(priceTrend);
                            } else {
                                MyToast.show(CyApplication.getCyContext(), "暂无数据!");
                            }
                        } catch (Exception e) {
                            MyToast.show(CyApplication.getCyContext(), "返回数据异常!");
                            e.printStackTrace();
                        }
                    } else {
                        MyToast.show(CyApplication.getCyContext(), "返回数据失败!");
                    }
                    bgaRefresh.endRefreshing();
                    break;
                case 4:
                    MsgClickBean msgClickBean = new Gson().fromJson(msg.obj.toString(), MsgClickBean.class);
                    int systemNum = 0;
                    int productNum = 0;
                    int quoteNum = 0;
                    int totalNum = 0;
                    if (msgClickBean != null && msgClickBean.getData() != null) {
                        systemNum = msgClickBean.getData().getSys();
                    }
                    if (msgClickBean != null && msgClickBean.getData() != null) {
                        productNum = msgClickBean.getData().getGoods();
                    }
                    if (msgClickBean != null && msgClickBean.getData() != null) {
                        quoteNum = msgClickBean.getData().getOffer();
                    }
                    totalNum = systemNum + productNum + quoteNum;
                    if (totalNum > 0) {
                        msgtvNum.setVisibility(View.VISIBLE);
                        if (totalNum > 99) {
                            msgtvNum.setText("99+");
                        } else {
                            msgtvNum.setText(totalNum + "");
                        }
                    } else {
                        msgtvNum.setVisibility(View.GONE);
                    }
                    break;
                case 0:
                    bgaRefresh.endRefreshing();
                    MyToast.show(CyApplication.getCyContext(), msg.obj.toString());
                    break;

            }
        }
    };


    private String hFiveToJs(String res) {
        String js = "";
        if (res.indexOf("</body>") != -1) {
            js = res.substring(res.indexOf("var"), res.indexOf("</body>"));
            js = js.replace("var ", "\"").replace("=", "\":").replace(";", ",");
            js = js.substring(0, js.length() - 3);
            js = "{" + js + "}";
        } else {
            js = res;
            js = js.substring(res.indexOf("var"), res.lastIndexOf("\";") + 1);
            js = js.replace("var ", "\"").replace("=", "\":").replace(";", ",");
            js = js.substring(0, js.length());
            js = "{" + js + "}";
        }
        return js;
    }


    private class TestLoopAdapter extends LoopPagerAdapter {
        private int[] imgs = {
                R.mipmap.ic_launcher,
                R.mipmap.ic_launcher,
                R.mipmap.ic_launcher,
                R.mipmap.ic_launcher,
        };
        private List<Slid> list;

        public TestLoopAdapter(RollPagerView viewPager, List<Slid> list) {
            super(viewPager);
            this.list = list;
        }

        @Override
        public View getView(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());
//            view.setImageResource(imgs[position]);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            Glide.with(fgActy)
                    .load(UrlUtil.IMAGE_URL + list.get(position).getImg())
                    .error(R.mipmap.default_banner)
                    .into(view);
            return view;
        }

        @Override
        public int getRealCount() {
            if (list != null)
                return list.size();
            return 0;
        }
    }

    class HomeAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (msgList != null)
                return msgList.size();
            return 0;
        }

        @Override
        public Object getItem(int i) {
            return msgList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            ViewHolder vh;
            if (view == null) {
                view = LayoutInflater.from(fgActy).inflate(R.layout.item_home_main, viewGroup, false);
                vh = new ViewHolder(view);
                view.setTag(vh);
            } else {
                vh = (ViewHolder) view.getTag();
            }

            if (msgList.get(i).getTitle() != null)
                vh.itemTitle.setText(msgList.get(i).getTitle());
            else
                vh.itemTitle.setText("");

            if (msgList.get(i).getTitle() != null)
                vh.itemTime.setText(DateTimeUtil.stampToDate("MM/dd", msgList.get(i).getTime() + "000"));
            else
                vh.itemTime.setText("--");

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(fgActy, InformationDetailsActivity.class);
                    intent.putExtra("inforID", msgList.get(i).getId() + "");
                    intent.putExtra("flag", "information");
                    startActivity(intent);
                }
            });
            return view;
        }

        class ViewHolder {
            @BindView(R.id.item_home_main_title_tv)
            TextView itemTitle;
            @BindView(R.id.item_home_main_time_tv)
            TextView itemTime;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
