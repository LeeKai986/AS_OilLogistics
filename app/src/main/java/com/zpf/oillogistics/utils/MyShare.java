package com.zpf.oillogistics.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.zpf.oillogistics.base.CyApplication;


/**
 * Created by wjw on 2017/2/28.
 */

public class MyShare {
    private static MyShare instance;
    private SharedPreferences share;
    public static final String SHARE_DEFAULT = "SHARE_DEFAULT";
    public static final String SHARE_NAME = "SHARE_OIL_USER";

    /**
     * 获取实例
     */
    public static MyShare get(Context context) {
        if (instance == null)
            instance = new MyShare(context,SHARE_NAME);
        return instance;
    }

    public MyShare(Context context) {
        this(context, SHARE_DEFAULT);
    }

    public MyShare(Context context, String shareName) {
        share = getShared(context, shareName);
    }

    public static SharedPreferences getShared() {
        return CyApplication.getCyContext().getSharedPreferences(SHARE_NAME,
                Context.MODE_PRIVATE);
    }

    public static SharedPreferences getShared(Context context, String shareName) {
        return context.getApplicationContext().getSharedPreferences(shareName,
                Context.MODE_PRIVATE);
    }

    public String getString(String key) {
        return share.getString(key, null);
    }

    public int getInt(String key) {
        return share.getInt(key, 0);
    }

    public long getLong(String key) {
        return share.getLong(key, 0);
    }

    public float getFloat(String key) {
        return share.getFloat(key, 0);
    }

    public boolean getBoolean(String key) {
        return share.getBoolean(key, false);
    }

    public boolean contains(String key) {
        return share.contains(key);
    }

    public void putString(String key, String value) {
        share.edit().putString(key, value).commit();
    }

    public void putInt(String key, int value) {
        share.edit().putInt(key, value).commit();
    }

    public void putLong(String key, long value) {
        share.edit().putLong(key, value).commit();
    }

    public void putFloat(String key, float value) {

        share.edit().putFloat(key, value).commit();
    }

    public void putBoolean(String key, boolean value) {

        share.edit().putBoolean(key, value).commit();
    }

    public void remove(String key) {
        share.edit().remove(key).commit();
    }

    public void clear() {
        share.edit().clear().commit();
    }

}
