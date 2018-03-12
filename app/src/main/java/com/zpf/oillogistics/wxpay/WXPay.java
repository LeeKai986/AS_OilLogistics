package com.zpf.oillogistics.wxpay;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zpf.oillogistics.activity.OrderPayActivity;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/11/8.
 */

public class WXPay {
    private Context context;
    private IWXAPI api;

    public WXPay(Context context,IWXAPI api) {
        this.context = context;
        this.api=api;
    }

    public void wxPay(){
        String url = "http://wxpay.wxutil.com/pub_v2/app/app_pay.php";

        RequestBody requestBody = null;

        FormBody.Builder builder = new FormBody.Builder();

        requestBody = builder.build();
        // 请求对象
        final Request request = new Request.Builder().url(url).post(requestBody).build();
        OkHttpClient mClient =new OkHttpClient();
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("PAY_GET", "返回错误");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                try {
                    JSONObject json = new JSONObject(result);
                    if (null != json && !json.has("retcode")) {
                        PayReq req = new PayReq();
                        //req.appId = "wxf8b4f85f3a794e77";  // 测试用appId
                        req.appId = json.getString("appid");
                        req.partnerId = json.getString("partnerid");
                        req.prepayId = json.getString("prepayid");
                        req.nonceStr = json.getString("noncestr");
                        req.timeStamp = json.getString("timestamp");
                        req.packageValue = json.getString("package");
                        req.sign = json.getString("sign");
                        req.extData = "app data"; // optional
//								Toast.makeText(PayActivity.this, "正常调起支付", Toast.LENGTH_SHORT).show();
                        // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                        api.sendReq(req);
                    } else {
//								Log.d("PAY_GET", "返回错误"+json.getString("retmsg"));
//								Toast.makeText(PayActivity.this, "返回错误"+json.getString("retmsg"), Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){

                }
            }
        });
    }
}
