package com.zpf.oillogistics.net;

import android.content.Context;
import android.content.SharedPreferences;

import com.zpf.oillogistics.base.CyApplication;


/**
 * Created by Administrator on 2017/7/31.
 * <p>
 * 錯誤碼對照
 */

public class ErrorCode {

    public static String getCodeCompare(String errorCode) {
        if (errorCode.contains("SocketTimeoutException")) {
            return "服务端异常,请稍后再试";
        } else if (errorCode.contains("ConnectException")) {
            return "网络连接失败";
        } else if (errorCode.contains("UnknownHostException")) {
            return "无网络链接 ";
        } else if (errorCode.contains("请先登录")) {
//            NormalToast.centerToast(CyApplication.getCyContext(), "登陆失效", 0);
            SharedPreferences sp = CyApplication.getCyContext().getSharedPreferences("user", Context.MODE_PRIVATE);
            String userStr = sp.getString("user", "");
            SharedPreferences.Editor editor = sp.edit();
            editor.clear();
            editor.commit();
            if (userStr != null && !userStr.equals("")) {
                editor.putString("user", userStr);
                editor.commit();
            }

//            Intent intent = new Intent(CyApplication.getCyContext(), LoginActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//            CyApplication.getCyContext().startActivity(intent);
            return errorCode;
        } else if (errorCode.equals("500")) {
            return "请求数据有误";
        } else {
            return errorCode;
        }
    }
}
