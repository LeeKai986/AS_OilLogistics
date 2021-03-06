package com.zpf.oillogistics.net;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.zpf.oillogistics.base.CyApplication;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/7/19.
 * <p>
 * okhttp请求
 */

public class Request {

    private OkHttpClient client;

    public static final MediaType JSON
            = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");

    // 无Cookie接口调用
    public String noKeyRequest(Map<String, String> maps, String url) throws IOException {
        String res;
        if (client == null) {
            client = new OkHttpClient();
        }

        //当前进度
        StringBuffer stringBuffer = new StringBuffer();
        for (Map.Entry<String, String> map : maps.entrySet()) {
            stringBuffer.append(map.getKey() + "=" + map.getValue() + "&");
        }
        String bodyStr = "";
        if (stringBuffer.length() > 0) {
            bodyStr = stringBuffer.substring(0, stringBuffer.length() - 1);
        }
        Log.i("bodyStr-->", bodyStr);
        RequestBody body = RequestBody.create(JSON, bodyStr);

        okhttp3.Request request = new okhttp3.Request.Builder().addHeader("Connection", "close")
                .addHeader("User-Agent", "Android").url(url).post(body).build();
        // 超时等待设置
        client.newBuilder().connectTimeout(7, TimeUnit.SECONDS);

        Response response = client.newCall(request).execute();
        Log.i("Response信息-->", response.toString());
        Log.i("Request信息-->", request.toString());
        if (response.isSuccessful()) {
            res = response.body().string();
            if (res.length() > 3000) {
                for (int i = 0; i < res.length(); i += 3000) {
                    if (i + 3000 < res.length()) {
                        Log.i("接口返回信息-->" + i, res.substring(i, i + 3000));
                    } else {
                        Log.i("接口返回信息-->" + i, res.substring(i, res.length()));
                    }
                }
            } else {
                Log.i("接口返回信息-->", res);
            }
            return res;
        } else {
            Log.i("接口错误信息返回-->", response.message() + "-" + response.code());
            return response.code() + "";
        }
    }

    // 有Cookie接口调用
    public String keyRequest(Map<String, String> maps, String url) throws IOException {
        String res;
        if (client == null) {
            client = new OkHttpClient();
        }

        SharedPreferences sp = CyApplication.getCyContext().getSharedPreferences("user", Context.MODE_PRIVATE);
//        Log.i("有cookie接口请求-->", sp.getString("cookie", ""));
        //当前进度
        StringBuffer stringBuffer = new StringBuffer();
        for (Map.Entry<String, String> map : maps.entrySet()) {
            stringBuffer.append(map.getKey() + "=" + map.getValue() + "&");
        }
        String bodyStr = "";
        if (stringBuffer.length() > 0) {
            bodyStr = stringBuffer.substring(0, stringBuffer.length() - 1);
        }
        Log.i("bodyStr-->", bodyStr);
        RequestBody body = RequestBody.create(JSON, bodyStr);
        // header("cookie", sp.getString("cookie", "")).
        okhttp3.Request request = new okhttp3.Request.Builder().url(url).header("token", sp.getString("token", "")).
                addHeader("Connection", "close").addHeader("User-Agent", "Android").post(body).build();
        // 超时等待设置
        client.newBuilder().connectTimeout(7, TimeUnit.SECONDS);
        Response response = client.newCall(request).execute();
        Log.i("Response信息-->", response.toString());
        Log.i("Request信息-->", request.toString());
        if (response.isSuccessful()) {
            res = response.body().string();
            if (res.length() > 3000) {
                for (int i = 0; i < res.length(); i += 3000) {
                    if (i + 3000 < res.length()) {
                        Log.i("接口返回信息-->" + i, res.substring(i, i + 3000));
                    } else {
                        Log.i("接口返回信息-->" + i, res.substring(i, res.length()));
                    }
                }
            } else {
                Log.i("接口返回信息-->", res);
            }
            return res;
        } else {
            Log.i("接口错误信息返回-->", response.message() + "-" + response.code());
            return response.code() + "";
        }
    }

// 有Cookie接口调用
    public String keyGetRequest(Map<String, String> maps, String url) throws IOException {
        String res;
        if (client == null) {
            client = new OkHttpClient();
        }

        SharedPreferences sp = CyApplication.getCyContext().getSharedPreferences("user", Context.MODE_PRIVATE);
//        Log.i("有cookie接口请求-->", sp.getString("cookie", ""));
        //当前进度
        StringBuffer stringBuffer = new StringBuffer();
        for (Map.Entry<String, String> map : maps.entrySet()) {
            stringBuffer.append(map.getKey() + "=" + map.getValue() + "&");
        }
        String bodyStr = "";
        if (stringBuffer.length() > 0) {
            bodyStr = stringBuffer.substring(0, stringBuffer.length() - 1);
        }
        okhttp3.Request request = new okhttp3.Request.Builder().url(url+"?"+bodyStr).header("token", sp.getString("token", "")).
                addHeader("Connection", "close").addHeader("User-Agent", "Android").get().build();
        // 超时等待设置
        client.newBuilder().connectTimeout(7, TimeUnit.SECONDS);
        Response response = client.newCall(request).execute();
        Log.i("Response信息-->", response.toString());
        Log.i("Request信息-->", request.toString());
        if (response.isSuccessful()) {
            res = response.body().string();
            if (res.length() > 3000) {
                for (int i = 0; i < res.length(); i += 3000) {
                    if (i + 3000 < res.length()) {
                        Log.i("接口返回信息-->" + i, res.substring(i, i + 3000));
                    } else {
                        Log.i("接口返回信息-->" + i, res.substring(i, res.length()));
                    }
                }
            } else {
                Log.i("接口返回信息-->", res);
            }
            return res;
        } else {
            Log.i("接口错误信息返回-->", response.message() + "-" + response.code());
            return response.code() + "";
        }
    }public String getRequest(Map<String, String> maps, String url) throws IOException{
        String res;
        if (client == null) {
            client = new OkHttpClient();
        }

        SharedPreferences sp = CyApplication.getCyContext().getSharedPreferences("user", Context.MODE_PRIVATE);
//        Log.i("有cookie接口请求-->", sp.getString("cookie", ""));
        //当前进度
        StringBuffer stringBuffer = new StringBuffer();
        for (Map.Entry<String, String> map : maps.entrySet()) {
            stringBuffer.append(map.getKey() + "=" + map.getValue() + "&");
        }
        String bodyStr = "";
        if (stringBuffer.length() > 0) {
            bodyStr = stringBuffer.substring(0, stringBuffer.length() - 1);
        }
        Log.i("bodyStr-->", bodyStr);
//        RequestBody body = RequestBody.create(JSON, bodyStr);
//        // header("cookie", sp.getString("cookie", "")).
//        okhttp3.Request request = new okhttp3.Request.Builder().url(url).header("token", sp.getString("token", "")).
//                addHeader("Connection", "close").addHeader("User-Agent", "Android").post(body).build();
        // 超时等待设置
        client.newBuilder().connectTimeout(7, TimeUnit.SECONDS);
        okhttp3.Request request = new okhttp3.Request.Builder().url(url + "?" + bodyStr).header("token", sp.getString("token", "")).build();
        Response response = client.newCall(request).execute();
        Log.i("Response信息-->", response.toString());
        Log.i("Request信息-->", request.toString());
        if (response.isSuccessful()) {
            res = response.body().string();
            if (res.length() > 3000) {
                for (int i = 0; i < res.length(); i += 3000) {
                    if (i + 3000 < res.length()) {
                        Log.i("接口返回信息-->" + i, res.substring(i, i + 3000));
                    } else {
                        Log.i("接口返回信息-->" + i, res.substring(i, res.length()));
                    }
                }
            } else {
                Log.i("接口返回信息-->", res);
            }
            return res;
        } else {
            Log.i("接口错误信息返回-->", response.message() + "-" + response.code());
            return response.code() + "";
        }
    }//    private void overLock() throws NoSuchAlgorithmException, KeyManagementException {
//        SSLContext sc = SSLContext.getInstance("SSL");
//        sc.init(null, new TrustManager[]{new X509TrustManager() {
//            @Override
//            public X509Certificate[] getAcceptedIssuers() {
//                return null;
//            }
//
//            @Override
//            public void checkClientTrusted(X509Certificate[] chain,
//                                           String authType) throws CertificateException {
//
//            }
//
//            @Override
//            public void checkServerTrusted(X509Certificate[] chain,
//                                           String authType) throws CertificateException {
//
//            }
//        }}, new SecureRandom());
//        client.setSslSocketFactory(sc.getSocketFactory());
//        client.setHostnameVerifier(new HostnameVerifier() {
//            @Override
//            public boolean verify(String hostname, SSLSession session) {
//                return true;
//            }
//        });
//    }

}
