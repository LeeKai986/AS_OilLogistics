package com.zpf.oillogistics.zfbpay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.PayTask;
import com.zpf.oillogistics.base.MessageWhat;
import com.zpf.oillogistics.zfbpay.util.OrderInfoUtil2_0;

import java.util.Map;

/**
 * Created by Administrator on 2017/9/11.
 */

public class ZFBPay {

    private Context mContext;
    public ZFBPay(Context context) {
        this.mContext=context;
    }

    /** 支付宝支付业务：入参app_id */
    public static final String APPID = "2017110609759337";

    /** 支付宝账户登录授权业务：入参pid值 */
    public static final String PID = "";
    /** 支付宝账户登录授权业务：入参target_id值 */
    public static final String TARGET_ID = "";

    /** 商户私钥，pkcs8格式 */
    /** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
    /** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
    /** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
    /** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
    /** 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1 */
    public static final String RSA2_PRIVATE ="MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCa87Ay2XcLuTFcwnutP3dfUqA4dnzYTuEG3YTyMBQeWyvnt4A19Dpd5tE6qeDE3Cv6Y0ifgZSSxNP5FxOVCE7aNalayN4dxhJEFU2EFbXMAo7xkhbXyU+THfhvBTJDHQaGm8lXfv7y1lXEHUgt7+6Wv3yXcmEbUGEJkAXZi8DWQQcvTCdaTpw/aHUzoEATC3dZcoPxZp/p26GXox+UNOhNbcosfTkLksCtUZQ2pjUviHIQymTVE7WneAiHzOAz19d37eqEGFu35p1dpBidQQa/J8ZsktFm/zD67zTAnq63EqksLLob73KKdelkLuR1txyOck+LDuKGSQIgaA45E5cJAgMBAAECggEBAInUT5u7kkfsUDs5h3eAp7czsO7ofn6OE13e913SZGLoKDKyKCpV9wEFyoDLEPMrATd0zumLAtRWnlLIMxPy0ukIqH7ng6OqRx8mb9AY25//epTopMcG+3BaTVy7m4zVKbPcqQjaHv90pc+PWF/8NxcCd1njVPtN0TMHWBy9UieBPglsAvsmDhB24h57HD5AIaZZfwVDhkkUIKRliNvurBHMZA/gsHgFXSWOI1XU4F29or6GOvXg89RN88wWRCh8ByAwdH1LdborcyrNp0/rbI8IwSUYUHd+DD991QX36pYU5qaANhSVusn6n2AgUTg5tFfJaFQ0xgBnjh8wIFGrgU0CgYEA/GmSk13UBZlRvK2CN9sOMdHO8oI5xGUjoExARAcArIms0HbxLSqAEyoM9OXCyrp2zO+i1PMz1oqzbP0pJHW7qzY7zWvpPiJ1PxUAzDI0nn+JK8F/JMHJcJnC2zKPkzND2AgKTUCEp4RpE4In+fZmv42eMqhS2jbd2uD1Mz7D8d8CgYEAnSd/AMeNiEO1CN9vJL54wEO6ksU0s96lLPuXtSsOmUG4gmIgxlZcWR9bVL6pIa3qlLWKv/AD73pr0hZyG9wKmZgbuafI8H5wio2LhuxLaws6Y+zOYQ5ZFPcooeRSF6MhR+pF8xZXsoZkn6CoOdOc16ir0PoFnoPNQ9L94v46pBcCgYEA91/sNBs/4ODbF4Qg2+cG6VKLdGamgNDycnZogYMvaOlhAcmgIjjF3oJWwmivWIoYRk8REYzsn/W9VTJjcQUlkO2M/I0mwSt6JwHJj1Yg9bleURDJL9fwOTeonbTWu1gBxKZUmlOB7r85T9I+pZ6UImWDxnfrA0vEcHAKP1fjJ5cCgYAh3l00oc0SzOS7IMwnMxV6+h2/e9i72wPmCeLMHJds2AkXsGY32GzWmDdqsBxrMbwYiHd6nhN7DPsnr8av0mBGcc2n2U0o51cBxTeIwGwQib7vwh1INXtlVU7gdQBsL7AAe7DPjsUfqG8PJ+SZcc2ffK5qvzduziDyuQoWXYvtyQKBgQDt4Ts+wA9YLVksZSMQXCrs0ilGyr4TpumhYW5nvNX1ER3P80Rd+6osDZwuG09/6ykgJ023zwm6k1hYVRQVfqDdH+oK24LXvT2gBTqDlM0J5vAEV7KzjVD0PvVvFpeXIm4hOxFg2ZzwKOxvPQw5/jj0X9kvkxrLJgFrYgzJTdxHGA==";
    public static final String RSA_PRIVATE = "";

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    private Handler mHandler;


    /**
     * 支付宝支付业务
     *
     */
    public void payV2(Handler handler,String money) {
        mHandler=handler;
        if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
            new AlertDialog.Builder(mContext).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            //
                            dialoginterface.dismiss();
                        }
                    }).show();
            return;
        }

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2,money);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask((Activity) mContext);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = MessageWhat.SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * 支付宝账户授权业务
     *
     */
    public void authV2(Handler handler) {
        mHandler=handler;
        if (TextUtils.isEmpty(PID) || TextUtils.isEmpty(APPID)
                || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))
                || TextUtils.isEmpty(TARGET_ID)) {
            new AlertDialog.Builder(mContext).setTitle("警告").setMessage("需要配置PARTNER |APP_ID| RSA_PRIVATE| TARGET_ID")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            dialoginterface.dismiss();
                        }
                    }).show();
            return;
        }

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * authInfo的获取必须来自服务端；
         */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> authInfoMap = OrderInfoUtil2_0.buildAuthInfoMap(PID, APPID, TARGET_ID, rsa2);
        String info = OrderInfoUtil2_0.buildOrderParam(authInfoMap);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(authInfoMap, privateKey, rsa2);
        final String authInfo = info + "&" + sign;
        Runnable authRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造AuthTask 对象
                AuthTask authTask = new AuthTask((Activity) mContext);
                // 调用授权接口，获取授权结果
                Map<String, String> result = authTask.authV2(authInfo, true);

                Message msg = new Message();
                msg.what = MessageWhat.SDK_AUTH_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread authThread = new Thread(authRunnable);
        authThread.start();
    }

    /**
     * get the sdk version. 获取SDK版本号
     *
     */
    public void getSDKVersion() {
        PayTask payTask = new PayTask((Activity) mContext);
        String version = payTask.getVersion();
        Toast.makeText(mContext, version, Toast.LENGTH_SHORT).show();
    }

    /**
     * 原生的H5（手机网页版支付切natvie支付） 【对应页面网页支付按钮】
     *
     * @param v
     */
    public void h5Pay(View v) {
        Intent intent = new Intent(mContext, H5PayDemoActivity.class);
        Bundle extras = new Bundle();
        /**
         * url 是要测试的网站，在 Demo App 中会使用 H5PayDemoActivity 内的 WebView 打开。
         *
         * 可以填写任一支持支付宝支付的网站（如淘宝或一号店），在网站中下订单并唤起支付宝；
         * 或者直接填写由支付宝文档提供的“网站 Demo”生成的订单地址
         * （如 https://mclient.alipay.com/h5Continue.htm?h5_route_token=303ff0894cd4dccf591b089761dexxxx）
         * 进行测试。
         *
         * H5PayDemoActivity 中的 MyWebViewClient.shouldOverrideUrlLoading() 实现了拦截 URL 唤起支付宝，
         * 可以参考它实现自定义的 URL 拦截逻辑。
         */
        String url = "http://m.taobao.com";
        extras.putString("url", url);
        intent.putExtras(extras);
        mContext.startActivity(intent);
    }

//    @SuppressLint("HandlerLeak")
//    private Handler mHandler = new Handler() {
//        @SuppressWarnings("unused")
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case SDK_PAY_FLAG: {
//                    @SuppressWarnings("unchecked")
//                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
//                    /**
//                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
//                     */
//                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
//                    String resultStatus = payResult.getResultStatus();
//                    // 判断resultStatus 为9000则代表支付成功
//                    if (TextUtils.equals(resultStatus, "9000")) {
//                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
//                        Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();
//
//                    } else {
//                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
//                        Toast.makeText(mContext, "支付失败", Toast.LENGTH_SHORT).show();
//                    }
//                    break;
//                }
//                case SDK_AUTH_FLAG: {
//                    @SuppressWarnings("unchecked")
//                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
//                    String resultStatus = authResult.getResultStatus();
//
//                    // 判断resultStatus 为“9000”且result_code
//                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
//                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
//                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
//                        // 传入，则支付账户为该授权账户
//                        Toast.makeText(mContext,
//                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
//                                .show();
//                    } else {
//                        // 其他状态值则为授权失败
//                        Toast.makeText(mContext,
//                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();
//                    }
//                    break;
//                }
//                default:
//                    break;
//            }
//        };
//    };


}
