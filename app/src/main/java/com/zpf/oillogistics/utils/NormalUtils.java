package com.zpf.oillogistics.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;

import com.hyphenate.chat.EMClient;
import com.zpf.oillogistics.activity.DirverPersonMsgActivity;
import com.zpf.oillogistics.activity.InforSelfActivity;
import com.zpf.oillogistics.activity.InforSelfCompanyActivity;
import com.zpf.oillogistics.activity.LoginActivity;
import com.zpf.oillogistics.base.CyApplication;
import com.zpf.oillogistics.diy.DiyDialog;
import com.zpf.oillogistics.diy.GetContext;
import com.zpf.oillogistics.net.SimplifyThread;
import com.zpf.oillogistics.net.UrlUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/9/20.
 * <p>
 * 通用方法
 */

public class NormalUtils {

    static Context tContext;

    static GetContext getContext = new GetContext() {
        @Override
        public void getCt(Context context) {
            tContext = context;
        }
    };

    public static Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    MyToast.show(CyApplication.getCyContext(), message.obj.toString());
                    break;
                case 1:
                    if (message.obj != null) {
                        try {
                            JSONObject jo = new JSONObject(message.obj.toString());
                            if (jo.getInt("status") == 0) {
                                SharedPreferences sp = CyApplication.getCyContext().getSharedPreferences("SHARE_OIL_USER", Context.MODE_PRIVATE);
                                final SharedPreferences.Editor editor = sp.edit();
                                JSONObject data = jo.getJSONObject("data");
                                if (data.getString("statuss").equals("1")) {
//                                    editor.putString("userId", data.getString("id"));
//                                    editor.putString("userPhone", data.getString("phone"));
//                                    editor.putString("niname", data.getString("niname"));
//                                    editor.putString("userHead", data.getString("face"));
//                                    editor.putString("add_time", data.getString("add_time"));
//                                    editor.putString("userType", data.getString("status"));
//                                    editor.putString("province", data.getString("province"));
//                                    editor.putString("city", data.getString("city"));
//                                    editor.putString("area", data.getString("area"));
//                                    editor.putString("userToken", data.getString("logintoken"));
//                                    editor.putString("searchlog", data.getString("searchlog"));
//                                    editor.putString("logintime", data.getString("logintime"));
//                                    editor.putString("relname", data.getString("relname"));
//                                    editor.putString("card", data.getString("card"));
//                                    editor.putString("telphone", data.getString("telphone"));
//                                    editor.putString("manage", data.getString("manage"));
//                                    editor.putString("toaddress", data.getString("toaddress"));
//                                    editor.putString("suggest", data.getString("suggest"));
//                                    editor.putString("companyname", data.getString("companyname"));
//                                    editor.putString("enterprise", data.getString("enterprise"));
//                                    editor.putString("license", data.getString("license"));
//                                    editor.putString("place", data.getString("place"));
//                                    editor.putString("adds_time", data.getString("adds_time"));
//                                    editor.putString("img", data.getString("img"));
//                                    editor.putString("cartcode", data.getString("cartcode"));
//                                    editor.putString("car_type", data.getString("car_type"));
//                                    editor.putString("load", data.getString("load"));
//                                    editor.putString("driverpic", data.getString("driverpic"));
//                                    editor.putString("runpic", data.getString("runpic"));
//                                    editor.putString("operatepic", data.getString("operatepic"));
//                                    editor.putString("supercargopic", data.getString("supercargopic"));
//                                    editor.putString("myorder", data.getString("myorder"));
//                                    editor.putString("stroke", data.getString("stroke"));
                                    editor.putString("statuss", data.getString("statuss"));
//                                    editor.putString("longitude", data.getString("longitude"));
//                                    editor.putString("latitude", data.getString("latitude"));
                                    editor.commit();
                                    AlertDialog.Builder builder = new AlertDialog.Builder(CyApplication.getCyContext());
                                    builder.setMessage("身份验证已通过, 请重新登陆");
                                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            editor.clear();
                                            editor.commit();
                                            CyApplication.province = "";
                                            CyApplication.area = "";
                                            CyApplication.adress = "";
                                            CyApplication.lat = "";
                                            CyApplication.lon = "";
                                            Intent intent = new Intent(CyApplication.getCyContext(), LoginActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            CyApplication.getCyContext().startActivity(intent);
                                        }
                                    });
                                    Dialog dialog = builder.create();
                                    dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                                    dialog.setCanceledOnTouchOutside(false);
                                    dialog.show();
                                } else {
                                    editor.putString("statuss", data.getString("statuss"));
                                    editor.commit();
                                    if (data.getString("statuss").equals("4")) {
                                        MyToast.show(CyApplication.getCyContext(), "审核中,请等待");
                                        DiyDialog.hintTweBtnDialog(tContext, "当前账号处于待审核状态,是否登陆其他账号?", new DiyDialog.HintTweBtnListener() {
                                            @Override
                                            public void confirm() {
                                                EMClient.getInstance().logout(true);
                                                MyShare.getShared().edit().clear().commit();
                                                CyApplication.province = "";
                                                CyApplication.area = "";
                                                CyApplication.adress = "";
                                                CyApplication.lat = "";
                                                CyApplication.lon = "";
                                                Intent intent = new Intent(tContext, LoginActivity.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                tContext.startActivity(intent);
                                            }
                                        });
                                    } else if (data.getString("statuss").equals("5")) {
                                        MyToast.show(CyApplication.getCyContext(), "该账号已被禁用");
                                        DiyDialog.hintOneBtnDialog(tContext, "该账号已被禁用,请更换账号重新登陆", new DiyDialog.HintTweBtnListener() {
                                            @Override
                                            public void confirm() {
                                                EMClient.getInstance().logout(true);
                                                MyShare.getShared().edit().clear().commit();
                                                CyApplication.province = "";
                                                CyApplication.area = "";
                                                CyApplication.adress = "";
                                                CyApplication.lat = "";
                                                CyApplication.lon = "";
                                                Intent intent = new Intent(tContext, LoginActivity.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                tContext.startActivity(intent);
                                            }
                                        });
                                    } else {
                                        MyToast.show(CyApplication.getCyContext(), "请先验证身份");
                                        personDataVerify();
                                    }
                                }
                            } else {
                                MyToast.show(CyApplication.getCyContext(), jo.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
//                        MyToast.show(CyApplication.getCyContext(), "未能获取数据");
                    }
                    break;
            }
            return false;
        }
    });

    // 电话号码验证
    public static boolean isMobile(String str) {
        if (str == null) {
            return false;
        }
        return Pattern.compile("^[1][3,4,5,6,7,8][0-9]{9}$").matcher(str).matches(); // 验证手机号
    }

    // 获取账号信息
    public static String userId() {
        SharedPreferences sp = CyApplication.getCyContext().getSharedPreferences("SHARE_OIL_USER", Context.MODE_PRIVATE);
        return sp.getString("userId", "");
    }

    // 获取电话
    public static String userPhone() {
        SharedPreferences sp = CyApplication.getCyContext().getSharedPreferences("SHARE_OIL_USER", Context.MODE_PRIVATE);
        return sp.getString("userPhone", "");
    }

    // 审核状态判断
    public static boolean personDataPass(Context context) {
        getContext.getCt(context);
        SharedPreferences sp = CyApplication.getCyContext().getSharedPreferences("SHARE_OIL_USER", Context.MODE_PRIVATE);
        if (sp.getString("statuss", "").equals("")) {
            MyToast.show(CyApplication.getCyContext(), "请先登录");
            CyApplication.province = "";
            CyApplication.area = "";
            CyApplication.adress = "";
            CyApplication.lat = "";
            CyApplication.lon = "";
            Intent intent = new Intent(CyApplication.getCyContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            CyApplication.getCyContext().startActivity(intent);
            return false;
        } else if (sp.getString("statuss", "").equals("1")) {
            return true;
        } else if (sp.getString("statuss", "").equals("2")) {
//            login();
            return false;
        }
//        else if (sp.getString("statuss", "").equals("2")){
//            login();
//            return true;
//        }
        else {
//            login();
//            MyToast.show(CyApplication.getCyContext(), "个人信息审核中");
            return false;
        }
    }

    // 审核状态判断
    public static boolean isLogin(Context context) {
        getContext.getCt(context);
        SharedPreferences sp = CyApplication.getCyContext().getSharedPreferences("SHARE_OIL_USER", Context.MODE_PRIVATE);
        if (sp.getString("statuss", "").equals("")) {
            return false;
        } else if (sp.getString("statuss", "").equals("1")) {
            return true;
        } else if (sp.getString("statuss", "").equals("2")) {
//            login();
            return false;
        }
//        else if (sp.getString("statuss", "").equals("2")){
//            login();
//            return true;
//        }
        else {
//            login();
//            MyToast.show(CyApplication.getCyContext(), "个人信息审核中");
            return false;
        }
    }

    // 身份验证跳转
    public static void personDataVerify() {
        SharedPreferences sp = CyApplication.getCyContext().getSharedPreferences("SHARE_OIL_USER", Context.MODE_PRIVATE);
        if (sp.getString("statuss", "").equals("")) {
            MyToast.show(CyApplication.getCyContext(), "请先登录");
            MyShare.getShared().edit().clear().commit();
            CyApplication.province = "";
            CyApplication.area = "";
            CyApplication.adress = "";
            CyApplication.lat = "";
            CyApplication.lon = "";
            Intent intent = new Intent(tContext, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            tContext.startActivity(intent);
        } else if (sp.getString("userType", "").equals("1")) {
            Intent intent = new Intent(tContext, InforSelfActivity.class);
            tContext.startActivity(intent);
        } else if (sp.getString("userType", "").equals("2")) {
            Intent intent = new Intent(tContext, InforSelfCompanyActivity.class);
            tContext.startActivity(intent);
        } else {
            Intent intent = new Intent(tContext, DirverPersonMsgActivity.class);
            tContext.startActivity(intent);
        }
    }

    private static void login() {
        SharedPreferences sp = CyApplication.getCyContext().getSharedPreferences("SHARE_OIL_USER", Context.MODE_PRIVATE);
        HashMap<String, String> loginMap = new HashMap<>();
        loginMap.put("phone", sp.getString("userPhone", ""));
        loginMap.put("password", sp.getString("password", ""));
        SimplifyThread loginThread = new SimplifyThread(UrlUtil.LOGIN_LOGIN, loginMap);
        loginThread.setOnNoKeyResultListener(new SimplifyThread.OnResultListener() {
            @Override
            public void resultBody(String res) {
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

}
