package com.zpf.oillogistics.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.zpf.oillogistics.R;

/**
 * Created by Administrator on 2017/9/18.
 */

public class MyIntent {
    public static final String LAST_ACT = "LAST_ACT";
    public static final String BUNDLE = "TAG_BUNDLE";

    private Context mContext;


    public MyIntent(Context context) {
        mContext=context;
    }

    /**
     * @see #startAct(Class, Bundle)
     */
    public void startAct(Class<?> cls) {
        startAct(cls, null);
    }

    /**
     * @see #startAct(Intent, Class, Bundle)
     */
    public void startAct(Class<?> cls, Bundle bundle) {
        startAct(null, cls, bundle);
    }

    /**
     * 启动Activity
     */
    public void startAct(Intent intent, Class<?> cls, Bundle bundle) {
        if (intent == null)
            intent = new Intent();
        if (bundle != null)
            intent.putExtra(BUNDLE, bundle);
        intent.putExtra(LAST_ACT, this.getClass().getSimpleName());
        intent.setClass(mContext, cls);
        mContext.startActivity(intent);
        ((Activity)mContext).overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
    }
}
