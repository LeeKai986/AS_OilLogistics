package com.zpf.oillogistics.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.zpf.oillogistics.R;
import com.zpf.oillogistics.base.BaseActivity;
import com.zpf.oillogistics.bean.NormalBean;
import com.zpf.oillogistics.bean.RegisterBean;
import com.zpf.oillogistics.diy.DiyDialog;
import com.zpf.oillogistics.net.JsonUtil;
import com.zpf.oillogistics.net.SimplifyThread;
import com.zpf.oillogistics.net.UrlUtil;
import com.zpf.oillogistics.utils.MyToast;
import com.zpf.oillogistics.utils.NormalUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/9/13.
 * <p>
 * 注册
 */

public class RegisterActivity extends BaseActivity {

    // 布局相关
    // 返回
    @BindView(R.id.finish_rl)
    RelativeLayout finishRl;
    // 身份
    @BindView(R.id.register_identity_card_tv)
    TextView idCardTv;
    // 账号
    @BindView(R.id.register_user_et)
    EditText userEt;
    // 验证码
    @BindView(R.id.register_verify_et)
    EditText verifyEt;
    // 验证码获取
    @BindView(R.id.register_verify_tv)
    TextView verifyTv;
    // 密码
    @BindView(R.id.register_password_et)
    EditText passwordEt;
    // 再次密码
    @BindView(R.id.register_again_password_et)
    EditText againPasswordEt;
    // 两次密码是否相同
    @BindView(R.id.register_pass_agin_pass_tv)
    TextView passAginPassTv;
    // 提交
    @BindView(R.id.register_sub_tv)
    TextView subTv;
    // 确认条款
    @BindView(R.id.register_agree_terms_cb)
    CheckBox agreeTermsCb;
    // 查看条款
    @BindView(R.id.register_service_terms_tv)
    TextView serviceTermsTv;
    // timer
    CountDownTimer timer;

    // 数据相关
    // 获取验证码
    SimplifyThread getCodeThread;
    // 获取验证码传参
    HashMap<String, String> getCodeMap;
    // 获取验证码返回
    NormalBean sendCodeBean;
    // 注册
    SimplifyThread registerThread;
    // 注册传参
    HashMap<String, String> registerMap;
    // 注册返回
    RegisterBean registerBean;

    TextLengthListener textLengthListener = new TextLengthListener();
    PasswordChangeListener passwordChangeListener;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    MyToast.show(RegisterActivity.this, message.obj.toString());
                    break;
                case 1:
                    if (sendCodeBean == null) {
                        MyToast.show(RegisterActivity.this, "未能获取数据");
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
                        MyToast.show(RegisterActivity.this, sendCodeBean.getMsg());
                    }
                    break;
                case 2:
                    if (registerBean != null) {
                        if (registerBean.getMsg().contains("成功")) {
                            MyToast.show(RegisterActivity.this, "注册成功,请完善个人信息");
                            SharedPreferences sp = getSharedPreferences("SHARE_OIL_USER", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.clear().commit();
                            editor.putString("userId", registerBean.getData().getUser().getId() + "");
                            editor.putString("userType", registerBean.getData().getUser().getStatus() + "");
                            editor.putString("userPhone", registerBean.getData().getUser().getPhone());
                            editor.putString("password", passwordEt.getText().toString());
                            editor.putString("userToken", registerBean.getData().getUser().getLogintoken());
                            editor.putString("statuss", registerBean.getData().getUser().getStatuss() + "");
                            editor.commit();


                            if (sp.getString("userType", "").equals("1")) {
                                Intent intent = new Intent(RegisterActivity.this, InforSelfActivity.class);
                                startActivity(intent);
                            } else if (sp.getString("userType", "").equals("2")) {
                                Intent intent = new Intent(RegisterActivity.this, InforSelfCompanyActivity.class);
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(RegisterActivity.this, DirverPersonMsgActivity.class);
                                startActivity(intent);
                            }
                            finish();
//                            new Thread() {
//                                @Override
//                                public void run() {
//                                    //注册失败会抛出HyphenateException
//                                    try {
//                                        // 调用sdk注册方法
//                                        EMClient.getInstance().createAccount(userEt.getText().toString(), "yyt123456");//同步方法
//                                    } catch (final HyphenateException e) {
//                                        final int errorCode = e.getErrorCode();
//                                        //注册失败
//                                        runOnUiThread(new Runnable() {
//                                            @Override
//                                            public void run() {
//                                                if (errorCode == EMError.NETWORK_ERROR) {
//                                                    Toast.makeText(getApplicationContext(), "网络异常，请检查网络！", Toast.LENGTH_SHORT).show();
//                                                } else if (errorCode == EMError.USER_ALREADY_EXIST) {
//
//                                                } else {
//                                                    Toast.makeText(getApplicationContext(), "环信注册失败: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                                                    finish();
//                                                }
//
//
//                                            }
//                                        });
//
//                                        e.printStackTrace();
//                                    }
//                                }
//                            }.start();


                        } else {
                            MyToast.show(RegisterActivity.this, registerBean.getMsg());
                        }
                    }
                    break;
            }
            return false;
        }
    });

    @Override
    protected int setLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViewAction(Bundle savedInstanceState) {

        finishRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
        serviceTermsTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, ServiceTermsActivity.class));
            }
        });

        idCardTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] identityArr = getResources().getStringArray(R.array.identity_register);
                List<String> identitys = new ArrayList<>();
                for (int i = 0; i < identityArr.length; i++) {
                    identitys.add(identityArr[i]);
                }
                DiyDialog.singleSelectDialog(RegisterActivity.this, identitys, new DiyDialog.SingleSelectListener() {
                    @Override
                    public void SingleSelect(String res) {
                        idCardTv.setText(res);
                    }
                });
            }
        });

        verifyTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userEt.getText() == null || userEt.getText().toString().equals("")) {
                    MyToast.show(RegisterActivity.this, "请输入正确的手机号码");
                    return;
                }
                if (!NormalUtils.isMobile(userEt.getText().toString())) {
                    MyToast.show(RegisterActivity.this, "请输入正确的手机号码");
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
                registerMap = new HashMap<String, String>();
                if (idCardTv.getText().toString().equals("个人")) {
                    registerMap.put("status", "1");
                } else if (idCardTv.getText().toString().equals("企业")) {
                    registerMap.put("status", "2");
                } else if (idCardTv.getText().toString().equals("司机")) {
                    registerMap.put("status", "3");
                }
                registerMap.put("phone", userEt.getText().toString());
                registerMap.put("code", verifyEt.getText().toString());
                registerMap.put("password", passwordEt.getText().toString());
                registerMap.put("repassword", againPasswordEt.getText().toString());
                register();


            }
        });

        idCardTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() != 0 && !editable.toString().equals("")) {
                    textLengthListener.typeText(true);
                } else {
                    textLengthListener.typeText(false);
                }
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
                if (againPasswordEt.getText().toString().equals(passwordEt.getText().toString())) {
                    passwordChangeListener.pass(true);
                } else {
                    if (againPasswordEt.getText().length() > 0) {
                        passwordChangeListener.pass(false);
                    } else {
                        passwordChangeListener.pass(true);
                    }
                }
            }
        });

        againPasswordEt.addTextChangedListener(new TextWatcher() {
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
                if (passwordEt.getText().toString().equals(againPasswordEt.getText().toString())) {
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

        agreeTermsCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                textLengthListener.TermsCb(b);
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
        if (idCardTv.getText().toString().equals("")) {
            MyToast.show(RegisterActivity.this, "请选择身份类型");
            return false;
        }
        if (userEt.getText().toString().equals("") && !NormalUtils.isMobile(userEt.getText().toString())) {
            MyToast.show(RegisterActivity.this, "请填入正确的手机号码");
            return false;
        }
//        if (verifyEt.getText().toString().equals("")) {
//            MyToast.show(RegisterActivity.this, "请填入验证码");
//            return false;
//        }
        if (verifyEt.getText().toString().equals("")) {
            MyToast.show(RegisterActivity.this, "请填入验证码");
            return false;
        }
        if (passwordEt.getText().toString().equals("")) {
            MyToast.show(RegisterActivity.this, "请填入密码");
            return false;
        }
        if (!againPasswordEt.getText().toString().equals(passwordEt.getText().toString())) {
            MyToast.show(RegisterActivity.this, "两次输入的密码不一致");
            return false;
        }
        if (!agreeTermsCb.isChecked()) {
            MyToast.show(RegisterActivity.this, "您还没有同意服务条款");
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

    private void register() {
        registerThread = new SimplifyThread(UrlUtil.LOGIN_REGISTER, registerMap);
        registerThread.setOnNoKeyResultListener(new SimplifyThread.OnResultListener() {
            @Override
            public void resultBody(String res) {
                registerBean = JsonUtil.registerBeanResolve(res);
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
        void typeText(boolean type);

        void userText(boolean user);

        void verifyText(boolean verify);

        void passText(boolean pass);

        void aginPassText(boolean aginPass);

        void TermsCb(boolean termcb);
    }

    interface PasswordChangeListener {
        void pass(boolean isNoClear);
    }

    class TextLengthListener implements AllTextChangeListener {
        boolean type = false, user = false, verify = false, pass = false, aginPass = false, termCb = true;

        @Override
        public void typeText(boolean type) {
            this.type = type;
            check();
        }

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

        @Override
        public void TermsCb(boolean termcb) {
            this.termCb = termcb;
            check();
        }

        private void check() {
            if (type && user && verify && pass && aginPass && termCb) {
                subTv.setEnabled(true);
                subTv.setBackgroundColor(0xffFC4F4F);
            } else {
                subTv.setEnabled(false);
                subTv.setBackgroundColor(0xffCCCCCC);
            }
        }
    }
}
