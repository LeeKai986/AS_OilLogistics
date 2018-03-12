package com.zpf.oillogistics.diy;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;

import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.zpf.oillogistics.R;

/**
 * Created by Administrator on 2017/10/13.
 * <p>
 * 自定义SwipeMenuListview
 */

public class DiySwipeMenuListview extends SwipeMenuListView {

    private Context context;

    // 头尾布局
    View headerView;
    View footerView;

    // 当前第一条item所在adapter位置
    int firstPosition = 0;
    // 当前最后一条item所在adapter位置
    int lastPosition;
    // adapter总条数
    int totalSize;
    // 是否刷新
    boolean isRefresh = false;
    // 是否加载
    boolean isLoad = false;
//    // 刷新监听实例
//    OnRefreshLoadListener onRefreshLoadListener;

    // 滑动位置记录
    // 按下记录
    float downX;
    float downY;
    // 抬起记录
    float leaveX;
    float leaveY;

    public DiySwipeMenuListview(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public DiySwipeMenuListview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        initView();
    }

    public DiySwipeMenuListview(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    private void initView() {
        headerView = LayoutInflater.from(context).inflate(R.layout.diy_swipe_menu_listview_header, null);
        footerView = LayoutInflater.from(context).inflate(R.layout.diy_swipe_menu_listview_footer, null);
        this.setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                firstPosition = i;
                lastPosition = i1;
                totalSize = i2;
            }
        });
    }

    public void setOnRefreshLoadListener(final OnRefreshLoadListener onRefreshLoadListener) {
//        this.onRefreshLoadListener = onRefreshLoadListener;
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    downX = motionEvent.getX();
                    downY = motionEvent.getY();
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                    if (downX - motionEvent.getX() < -200 || downX - motionEvent.getX() > 200) {
                        return false;
                    }
                    if (DiySwipeMenuListview.this.getLastVisiblePosition() == totalSize - 1) {

                        if (motionEvent.getY() - downX > 200) {
                            if (totalSize >= 5) {
                                if (!isLoad) {
                                    DiySwipeMenuListview.this.addFooterView(footerView);
                                    isLoad = true;
                                }
                            }
                        }
                    }
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    leaveX = motionEvent.getX();
                    leaveY = motionEvent.getY();
                    if (firstPosition == 0) {
                        if (downY - leaveY < -750) {
                            onRefreshLoadListener.onRefresh();
                            DiySwipeMenuListview.this.addHeaderView(headerView);
                            isRefresh = true;
                            return true;
                        }
                    }
                    if (totalSize >= 5) {
                        if (DiySwipeMenuListview.this.getLastVisiblePosition() == totalSize - 1) {

                            if (downY - leaveY > 750) {
                                onRefreshLoadListener.onLoad();
                                return true;
                            } else if (downY - leaveY > 100) {
                                DiySwipeMenuListview.this.removeFooterView(footerView);
                                isLoad = false;
                            }
                        }
                    }
                }
                return false;
            }
        });
    }

    public void onRLStop() {
        this.removeHeaderView(headerView);
        this.removeFooterView(footerView);
        isRefresh = false;
        isLoad = false;
//        headerView.setVisibility(GONE);
//        footerView.setVisibility(GONE);
//        headerView.setPadding(0, -headerView.getHeight(), 0, 0);
//        footerView.setPadding(0, -footerView.getHeight(), 0, 0);
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//        super.onTouchEvent(ev);
//        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
//            downX = ev.getX();
//            downY = ev.getY();
//        }
//        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
//            if (firstPosition == 0) {
//                if (ev.getY() - leaveY < -100) {
//                    if (!isRefresh) {
//                        this.addHeaderView(headerView);
//                        isRefresh = true;
//                    }
//                }
//            }
//            if (this.getLastVisiblePosition() == totalSize - 1) {
//                if (ev.getY() - leaveY > 100) {
//                    if (!isLoad) {
//                        this.addFooterView(footerView);
//                        isLoad = true;
//                    }
//                }
//            }
//        }
//        if (ev.getAction() == MotionEvent.ACTION_UP) {
//            leaveX = ev.getX();
//            leaveY = ev.getY();
//            if (firstPosition == 0) {
//                if (downY - leaveY < -450) {
//                    onRefreshLoadListener.onRefresh();
//                    return true;
//                } else if (downY - leaveY < -100) {
//                    this.removeHeaderView(headerView);
//                    isRefresh = false;
//                }
//            }
//            if (this.getLastVisiblePosition() == totalSize - 1) {
//                if (downY - leaveY > 450) {
//                    onRefreshLoadListener.onLoad();
//                    return true;
//                } else if (downY - leaveY > 100) {
//                    this.removeFooterView(footerView);
//                    isLoad = false;
//                }
//            }
//        }
//        return false;
//    }

    public interface OnRefreshLoadListener {
        void onRefresh();

        void onLoad();
    }
}