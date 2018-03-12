package com.zpf.oillogistics.base;

import android.os.Environment;

/**
 * Created by Administrator on 2017/9/14.
 */

public class Constant {

    public static final String API_URI = "http://yuanyoutong.zpftech.com/api/";

    //获取好友
    public static final String URL_USER_NICK = API_URI + "user/nick";

    //获取好友
    public static final String URL_SLIDESHOW_INDEX = API_URI + "Slideshow/index";


    //图片保存路径
    public static String APK_IMAG_URI = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Oillogistics/imag/";
    //app缓存路径
    public static String APK_CASHE_URI = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Oillogistics/";
}
