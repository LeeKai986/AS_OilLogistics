package com.zpf.oillogistics.utils;

import android.view.View;

import java.util.Calendar;

/**
 * 防止过快点击 重复响应
 */
public class CheckDoubleClickListener implements View.OnClickListener {
    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    public static final int MIN_CLICK_DELAY_TIME = 1000;
    private static long lastClickTime = 0;
    private OnCheckDoubleClick checkDoubleClick;


    public static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }

    public CheckDoubleClickListener(OnCheckDoubleClick checkDoubleClick) {
        this.checkDoubleClick = checkDoubleClick;
    }

    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            checkDoubleClick.onCheckDoubleClick(v);
        }
    }

    public interface OnCheckDoubleClick {
        void onCheckDoubleClick(View view);
    }
}