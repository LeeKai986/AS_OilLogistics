package com.zpf.oillogistics.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zpf.oillogistics.R;
import com.zpf.oillogistics.base.BaseActivity;
import com.zpf.oillogistics.base.MessageWhat;
import com.zpf.oillogistics.net.SimplifyThread;
import com.zpf.oillogistics.net.UrlUtil;
import com.zpf.oillogistics.utils.MyToast;
import com.zpf.oillogistics.wxpay.WXPay;
import com.zpf.oillogistics.zfbpay.AuthResult;
import com.zpf.oillogistics.zfbpay.PayResult;
import com.zpf.oillogistics.zfbpay.ZFBPay;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/15.
 * <p>
 * 成功订单支付界面
 * <p>
 * *微信支付已被隐藏
 */

public class OrderPayActivity extends BaseActivity implements IWXAPIEventHandler {

    // 布局相关
    // 订单支付
    @BindView(R.id.order_pay_pay_tv)
    TextView payTv;
    @BindView(R.id.tv_orderNum)
    TextView tvOrderNum;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.rel_back)
    RelativeLayout relBack;
    @BindView(R.id.cb_weixin)
    CheckBox cbWeixin;
    @BindView(R.id.cb_zfb)
    CheckBox cbZfb;

    //支付宝类
    private ZFBPay zfbPay;
    //微信支付类
    private WXPay wxPay;
    private IWXAPI api;

    @Override
    protected int setLayout() {
        return R.layout.activity_order_pay;
    }

    @Override
    protected void initData() {
        cbZfb.setChecked(true);

        tvOrderNum.setText(getIntent().getExtras().getString("orderNo", ""));
        tvMoney.setText(getIntent().getExtras().getString("money", ""));
        zfbPay = new ZFBPay(OrderPayActivity.this);
        //微信支付注册  wxd930ea5d5a258f4f
        api = WXAPIFactory.createWXAPI(OrderPayActivity.this, "wxb4ba3c02aa476ea1");
        wxPay = new WXPay(OrderPayActivity.this, api);

    }

    @Override
    protected void initViewAction(Bundle savedInstanceState) {
        payTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cbZfb.isChecked())
                    zfbPay.payV2(handler, getIntent().getExtras().getString("money", ""));
//                Intent intent = new Intent(OrderPayActivity.this, OrderPaySuccessActivity.class);
//                intent.putExtra("no", tvOrderNum.getText().toString());
//                startActivity(intent);
                else
                    wxPay.wxPay();
            }
        });
        relBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = getIntent();
                in.putExtra("flag", "1");
                setResult(1, in);
                finish();
            }
        });

        cbWeixin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    cbZfb.setChecked(!b);
                }
            }
        });

        cbZfb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    cbWeixin.setChecked(!b);
                }
            }
        });
    }


    /**
     * 生成订单
     */
    private void orderPay() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("no", getIntent().getExtras().getString("orderNo", ""));
        hashMap.put("totalmoney", getIntent().getExtras().getString("money", ""));

        SimplifyThread simplifyThread = new SimplifyThread(UrlUtil.URL_ORDER_PAY, hashMap);
        simplifyThread.setOnKeyResultListener(new SimplifyThread.OnResultListener() {
            @Override
            public void resultBody(String res) {
                Message message = new Message();
                message.obj = res;
                message.what = MessageWhat.ORDER_PAY;
                handler.sendMessage(message);
            }

            @Override
            public void errorBody(String error) {
                handler.sendEmptyMessage(MessageWhat.REQUST_ERROR);
            }
        });
    }

    private Gson gson = new Gson();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MessageWhat.ORDER_PAY:
                    if (msg.obj != null) {
                        try {

                            Intent intent = new Intent(OrderPayActivity.this, OrderPaySuccessActivity.class);
                            intent.putExtra("no", getIntent().getExtras().getString("orderNo", ""));
                            startActivity(intent);
                            Intent in = getIntent();
                            in.putExtra("flag", "0");
                            setResult(1, in);
                            finish();
//                            if () {
//
//                            } else {
//                                MyToast.show(OrderPayActivity.this, "暂无数据!");
//                            }

                        } catch (Exception e) {
                            MyToast.show(OrderPayActivity.this, "返回数据异常!");
                        }

                    } else {
                        MyToast.show(OrderPayActivity.this, "返回数据失败!");
                    }
                    break;

                case MessageWhat.SDK_PAY_FLAG:
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(OrderPayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        orderPay();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(OrderPayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case MessageWhat.SDK_AUTH_FLAG:
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus2 = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus2, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Toast.makeText(OrderPayActivity.this,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(OrderPayActivity.this,
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
        Log.d("", "onPayFinish, errCode = ");
    }

    @Override
    public void onResp(BaseResp baseResp) {
        Log.d("", "onPayFinish, errCode = " + baseResp.errCode);

        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("提示");
            builder.setMessage("微信支付结果：%s" + baseResp.errCode);
            builder.show();
        }
    }
}
