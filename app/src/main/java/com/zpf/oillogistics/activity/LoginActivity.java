package com.zpf.oillogistics.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.zpf.oillogistics.R;
import com.zpf.oillogistics.base.BaseActivity;
import com.zpf.oillogistics.net.SimplifyThread;
import com.zpf.oillogistics.net.UrlUtil;
import com.zpf.oillogistics.utils.MyShare;
import com.zpf.oillogistics.utils.MyToast;
import com.zpf.oillogistics.utils.NormalUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/9/13.
 * <p>
 * 登陆
 */

public class LoginActivity extends BaseActivity {

    // 界面相关
    // 账号
    @BindView(R.id.login_user_et)
    EditText userEt;
    // 密码
    @BindView(R.id.login_password_et)
    EditText passwordEt;
    // 注册
    @BindView(R.id.login_register_tv)
    TextView registerTv;
    // 忘记
    @BindView(R.id.login_forget_tv)
    TextView forgetTv;
    // 登陆
    @BindView(R.id.login_sub_tv)
    TextView subTv;
    // 账号密码填充判断
    SubCheck subCheck;

    // 数据相关
    // 登陆
    SimplifyThread loginThread;
    // 登陆传参
    HashMap<String, String> loginMap;
    // 登陆返回

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    MyToast.show(LoginActivity.this, message.obj.toString());
                    break;
                case 1:
                    if (message.obj != null) {
                        try {
                            JSONObject jo = new JSONObject(message.obj.toString());

                            if (jo.getInt("status") == 0) {
                                SharedPreferences sp = getSharedPreferences("SHARE_OIL_USER", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp.edit();
                                JSONObject data = jo.getJSONObject("data");
                                if (data.getString("statuss").equals("5")) {
                                    MyToast.show(LoginActivity.this, "您的账号处于禁用状态，请更换账号重新登录");
                                    break;
                                } else {
                                    MyToast.show(LoginActivity.this, "登录成功");
                                }
                                editor.putString("userId", data.getString("id"));
                                editor.putString("userPhone", data.getString("phone"));
                                editor.putString("niname", data.getString("niname"));
                                editor.putString("userHead", data.getString("face"));
                                editor.putString("add_time", data.getString("add_time"));
                                editor.putString("userType", data.getString("status"));//1个人,2企业,3司机
                                editor.putString("province", data.getString("province"));
                                editor.putString("city", data.getString("city"));
                                if (!data.getString("area").equals("null")) {
                                    editor.putString("area", data.getString("area"));
                                }
                                editor.putString("userToken", data.getString("logintoken"));
                                editor.putString("searchlog", data.getString("searchlog"));
                                editor.putString("logintime", data.getString("logintime"));
                                editor.putString("relname", data.getString("relname"));
                                editor.putString("card", data.getString("card"));
                                editor.putString("telphone", data.getString("telphone"));
                                editor.putString("manage", data.getInt("manage") + "");
                                editor.putString("toaddress", data.getString("toaddress"));
                                if (!data.getString("suggest").equals("null")) {
                                    editor.putString("suggest", data.getString("suggest"));
                                }
                                editor.putString("companyname", data.getString("companyname"));
                                editor.putString("enterprise", data.getString("enterprise"));
                                editor.putString("license", data.getString("license"));
                                editor.putString("place", data.getString("place"));
                                editor.putString("adds_time", data.getString("adds_time"));
                                editor.putString("img", data.getString("img"));
                                editor.putString("cartcode", data.getString("cartcode"));
                                editor.putString("car_type", data.getString("car_type"));
                                editor.putString("load", data.getString("load"));
                                editor.putString("driverpic", data.getString("driverpic"));
                                editor.putString("runpic", data.getString("runpic"));
                                editor.putString("operatepic", data.getString("operatepic"));
                                editor.putString("supercargopic", data.getString("supercargopic"));
                                editor.putString("myorder", data.getString("myorder"));
                                editor.putString("stroke", data.getString("stroke"));
                                if (!data.getString("longitude").equals("null")) {
                                    editor.putString("longitude", data.getString("longitude"));
                                }
                                if (!data.getString("latitude").equals("null")) {
                                    editor.putString("latitude", data.getString("latitude"));
                                }
                                editor.putString("statuss", data.getString("statuss"));
                                editor.putString("password", passwordEt.getText().toString());
                                if (!data.getString("route").equals("null")) {
                                    editor.putString("route", data.getString("route"));
                                }
                                editor.commit();


                                EMClient.getInstance().login(MyShare.getShared().getString("userPhone", ""), "yyt123456", new EMCallBack() {//回调
                                    @Override
                                    public void onSuccess() {
//                                        EMClient.getInstance().groupManager().loadAllGroups();
//                                        EMClient.getInstance().chatManager().loadAllConversations();
                                        Log.i("main", "登录聊天服务器成功！--");
                                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                        finish();

                                    }

                                    @Override
                                    public void onProgress(int progress, String status) {
                                        Log.i("main", "登录聊天服务器失败！--");
                                    }

                                    @Override
                                    public void onError(int code, String message) {
                                        Log.i("main", "登录聊天服务器失败！--");
                                    }
                                });
                            } else {
                                MyToast.show(LoginActivity.this, jo.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 2:
                    break;
            }
            return false;
        }
    });

    @Override
    protected int setLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {
        MyShare.getShared().edit().clear().commit();
    }

    @Override
    protected void initViewAction(Bundle savedInstanceState) {

        subCheck = new SubCheck() {
            @Override
            public void isSub(boolean isSub) {
                if (isSub) {
                    subTv.setBackgroundColor(0xffFC4F4F);
                    subTv.setEnabled(true);
                } else {
                    subTv.setBackgroundColor(0xffCCCCCC);
                    subTv.setEnabled(false);
                }
            }
        };

        registerTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        forgetTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ForgetActivity.class));
            }
        });
        userEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() != 0 && passwordEt.getText().length() != 0) {
                    subCheck.isSub(true);
                } else {
                    subCheck.isSub(false);
                }
            }
        });
        passwordEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() != 0 && userEt.getText().length() != 0) {
                    subCheck.isSub(true);
                } else {
                    subCheck.isSub(false);
                }
            }
        });
        subTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!doCheck()) {
                    return;
                }
                loginMap = new HashMap<String, String>();
                loginMap.put("phone", userEt.getText().toString());
                loginMap.put("password", passwordEt.getText().toString());
                login();
//                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });
    }

    private boolean doCheck() {
        if (userEt.getText().toString().equals("")) {
            MyToast.show(LoginActivity.this, "请输入账号");
            return false;
        }
        if (!NormalUtils.isMobile(userEt.getText().toString())) {
            MyToast.show(LoginActivity.this, "请输入正确手机账号");
            return false;
        }
        if (passwordEt.getText().toString().equals("")) {
            MyToast.show(LoginActivity.this, "请输入密码");
            return false;
        }
        return true;
    }

    private void login() {
        loginThread = new SimplifyThread(UrlUtil.LOGIN_LOGIN, loginMap);
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

    interface SubCheck {
        void isSub(boolean isSub);
    }
}
