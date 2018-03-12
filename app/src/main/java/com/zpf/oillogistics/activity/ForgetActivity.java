package com.zpf.oillogistics.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zpf.oillogistics.R;
import com.zpf.oillogistics.base.BaseActivity;
import com.zpf.oillogistics.bean.NormalBean;
import com.zpf.oillogistics.net.JsonUtil;
import com.zpf.oillogistics.net.SimplifyThread;
import com.zpf.oillogistics.net.UrlUtil;
import com.zpf.oillogistics.utils.MyIntent;
import com.zpf.oillogistics.utils.MyToast;
import com.zpf.oillogistics.utils.NormalUtils;

import java.util.HashMap;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/9/13.
 * <p>
 * 忘记密码
 */

public class ForgetActivity extends BaseActivity {

    // 布局相关
    // 返回
    @BindView(R.id.finish_rl)
    RelativeLayout finishRl;
    // 修改密码
    @BindView(R.id.forget_title_tv)
    TextView titleTv;
    // 账号
    @BindView(R.id.forget_user_et)
    EditText userEt;
    // 验证码
    @BindView(R.id.forget_verify_et)
    EditText verifyEt;
    // 获取验证码
    @BindView(R.id.forget_verify_tv)
    TextView verifyTv;
    // 密码
    @BindView(R.id.forget_password_et)
    EditText passwordEt;
    // 再次密码
    @BindView(R.id.forget_again_password_et)
    EditText againPassworEt;
    // 两次密码是否相同
    @BindView(R.id.forget_pass_agin_pass_tv)
    TextView passAginPassTv;
    // 提交
    @BindView(R.id.forget_sub_tv)
    TextView subTv;

    CountDownTimer timer;

    TextLengthListener textLengthListener = new TextLengthListener();
    PasswordChangeListener passwordChangeListener;

    // 数据相关
    // 获取验证码
    SimplifyThread getCodeThread;
    // 获取验证码传参
    HashMap<String, String> getCodeMap;
    // 获取验证码返回
    NormalBean sendCodeBean, alterPassBean;
    // 找回密码
    SimplifyThread forgetPassThread;
    // 找回密码传参
    HashMap<String, String> forgetPassMap;
    // 找回密码返回

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    MyToast.show(ForgetActivity.this, message.obj.toString());
                    break;
                case 1:
                    if (sendCodeBean == null) {
                        MyToast.show(ForgetActivity.this, "未能获取数据");
                    } else {
                        if (sendCodeBean.getStatus() == 0) {
                            verifyTv.setBackgroundColor(0xffCCCCCC);
                            verifyTv.setEnabled(false);
                            timer = new CountDownTimer(60000, 1000) {
                                @Override
                                public void onTick(long l) {
                                    verifyTv.setText((l / 1000) + "'s重新获取");
                                }

                                @Override
                                public void onFinish() {
                                    verifyTv.setText("获取验证码");
                                    verifyTv.setBackgroundColor(0xffFC4F4F);
                                    verifyTv.setEnabled(true);
                                }
                            }.start();
                        }
                        MyToast.show(ForgetActivity.this, sendCodeBean.getMsg());
                    }
                    break;
                case 2:
                    if (alterPassBean == null) {
                        MyToast.show(ForgetActivity.this, "未能获取数据");
                    } else {
                        if (alterPassBean.getStatus() == 0) {
                            finish();
                        }
                        MyToast.show(ForgetActivity.this, alterPassBean.getMsg());
                    }
                    break;
            }
            return false;
        }
    });

    @Override
    protected int setLayout() {
        return R.layout.activity_forget;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViewAction(Bundle savedInstanceState) {
        if (getIntent().getExtras() != null && !getIntent().getBundleExtra(MyIntent.BUNDLE).getString("type", "").equals("")) {
            titleTv.setText("修改密码");
        }
        finishRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        verifyTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        passwordChangeListener = new PasswordChangeListener() {
            @Override
            public void pass(boolean isNoClear) {
                if (isNoClear) {
                    passAginPassTv.setVisibility(View.GONE);
                } else {
                    passAginPassTv.setVisibility(View.VISIBLE);
                }
            }
        };

        verifyTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userEt.getText() == null || userEt.getText().toString().equals("")) {
                    MyToast.show(ForgetActivity.this, "请输入正确的手机号码");
                    return;
                }
                if (!NormalUtils.isMobile(userEt.getText().toString())) {
                    MyToast.show(ForgetActivity.this, "请输入正确的手机号码");
                    return;
                }
                getCodeMap = new HashMap<>();
                getCodeMap.put("phone", userEt.getText().toString());
                getSendCode();
            }
        });

        subTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!doCheck()) {
                    return;
                }
                forgetPassMap = new HashMap<String, String>();

                forgetPassMap.put("phone", userEt.getText().toString());
                forgetPassMap.put("code", verifyEt.getText().toString());
                forgetPassMap.put("password", passwordEt.getText().toString());
                forgetPassMap.put("repassword", againPassworEt.getText().toString());
                forgetPass();
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
                if (editable.length() != 0 && !editable.toString().equals("")) {
                    textLengthListener.userText(true);
                } else {
                    textLengthListener.userText(false);
                }
            }
        });

        verifyEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() != 0 && !editable.toString().equals("")) {
                    textLengthListener.verifyText(true);
                } else {
                    textLengthListener.verifyText(false);
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
                if (editable.length() != 0 && !editable.toString().equals("")) {
                    textLengthListener.passText(true);
                } else {
                    textLengthListener.passText(false);
                }
                if (againPassworEt.getText().toString().equals(passwordEt.getText().toString())) {
                    passwordChangeListener.pass(true);
                } else {
                    if (againPassworEt.getText().length() > 0) {
                        passwordChangeListener.pass(false);
                    } else {
                        passwordChangeListener.pass(true);
                    }
                }
            }
        });

        againPassworEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() != 0 && !editable.toString().equals("")) {
                    textLengthListener.aginPassText(true);
                } else {
                    textLengthListener.aginPassText(false);
                }
                if (passwordEt.getText().toString().equals(againPassworEt.getText().toString())) {
                    passwordChangeListener.pass(true);
                } else {
                    if (passwordEt.getText().length() > 0) {
                        passwordChangeListener.pass(false);
                    } else {
                        passwordChangeListener.pass(true);
                    }
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (timer != null) {
            timer.onFinish();
            timer = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.onFinish();
            timer = null;
        }
    }

    private boolean doCheck() {
        if (userEt.getText().toString().equals("") && !NormalUtils.isMobile(userEt.getText().toString())) {
            MyToast.show(ForgetActivity.this, "请填入正确的手机号码");
            return false;
        }
        if (verifyEt.getText().toString().equals("")) {
            MyToast.show(ForgetActivity.this, "请填入验证码");
            return false;
        }
//        if (verifyEt.getText().toString().equals("")) {
//            MyToast.show(ForgetActivity.this, "请填入验证码");
//            return false;
//        }
        if (passwordEt.getText().toString().equals("")) {
            MyToast.show(ForgetActivity.this, "请填入密码");
            return false;
        }
        if (!againPassworEt.getText().toString().equals(passwordEt.getText().toString())) {
            MyToast.show(ForgetActivity.this, "两次输入的密码不一致");
            return false;
        }
        return true;
    }

    private void getSendCode() {
        getCodeThread = new SimplifyThread(UrlUtil.LOGIN_SEND_CODE, getCodeMap);
        getCodeThread.setOnNoKeyResultListener(new SimplifyThread.OnResultListener() {
            @Override
            public void resultBody(String res) {
                sendCodeBean = JsonUtil.normalBeanResolve(res);
                handler.sendEmptyMessage(1);
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

    private void forgetPass() {
        forgetPassThread = new SimplifyThread(UrlUtil.LOGIN_FINDPWD, forgetPassMap);
        forgetPassThread.setOnNoKeyResultListener(new SimplifyThread.OnResultListener() {
            @Override
            public void resultBody(String res) {
                alterPassBean = JsonUtil.normalBeanResolve(res);
                handler.sendEmptyMessage(2);
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

    interface AllTextChangeListener {
        void userText(boolean user);

        void verifyText(boolean verify);

        void passText(boolean pass);

        void aginPassText(boolean aginPass);
    }

    interface PasswordChangeListener {
        void pass(boolean isNoClear);
    }

    class TextLengthListener implements AllTextChangeListener {
        boolean user = false, verify = false, pass = false, aginPass = false;

        @Override
        public void userText(boolean user) {
            this.user = user;
            check();
        }

        @Override
        public void verifyText(boolean verify) {
            this.verify = verify;
            check();
        }

        @Override
        public void passText(boolean pass) {
            this.pass = pass;
            check();
        }

        @Override
        public void aginPassText(boolean aginPass) {
            this.aginPass = aginPass;
            check();
        }

        private void check() {
            if (user && verify && pass && aginPass) {
                subTv.setEnabled(true);
                subTv.setBackgroundColor(0xffFC4F4F);
            } else {
                subTv.setEnabled(false);
                subTv.setBackgroundColor(0xffCCCCCC);
            }
        }
    }
}
