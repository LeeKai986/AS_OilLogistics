package com.zpf.oillogistics.sv;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.zpf.oillogistics.base.CyApplication;
import com.zpf.oillogistics.net.SimplifyThread;
import com.zpf.oillogistics.net.UrlUtil;
import com.zpf.oillogistics.utils.NormalUtils;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/11/21.
 * <p>
 * 定时上传司机位置信息
 */

public class DriverPositionUpData extends Service {

    HashMap<String, String> hp;
    Thread fThread;
    LocationClient mLocationClient = null;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        BDLocationListener myListener = new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                if (bdLocation.getLongitude() != 0 && bdLocation.getLatitude() != 0) {
                    hp = new HashMap<>();
                    hp.put("position", bdLocation.getLatitude() + "-" + bdLocation.getLongitude());
                    updataDriverPosition();
                }
            }
        };
        mLocationClient = new LocationClient(DriverPositionUpData.this);
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();

        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
//可选，设置定位模式，默认高精度
//LocationMode.Hight_Accuracy：高精度；
//LocationMode. Battery_Saving：低功耗；
//LocationMode. Device_Sensors：仅使用设备；

        option.setCoorType("bd09ll");
//可选，设置返回经纬度坐标类型，默认gcj02
//gcj02：国测局坐标；
//bd09ll：百度经纬度坐标；
//bd09：百度墨卡托坐标；
//海外地区定位，无需设置坐标类型，统一返回wgs84类型坐标

        option.setScanSpan(0);
//可选，设置发起定位请求的间隔，int类型，单位ms
//如果设置为0，则代表单次定位，即仅定位一次，默认为0
//如果设置非0，需设置1000ms以上才有效

        option.setOpenGps(true);
//可选，设置是否使用gps，默认false
//使用高精度和仅用设备两种定位模式的，参数必须设置为true

        option.setLocationNotify(true);
//可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false

        option.setIgnoreKillProcess(false);
//可选，定位SDK内部是一个service，并放到了独立进程。
//设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)

//        option.setIgnoreCacheException(false);
////可选，设置是否收集Crash信息，默认收集，即参数为false
//
//        option.setWifiValidTime(5*60*1000);
////可选，7.2版本新增能力
////如果设置了该接口，首次启动定位时，会先判断当前WiFi是否超出有效期，若超出有效期，会先重新扫描WiFi，然后定位

        option.setEnableSimulateGps(false);
//可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false

        mLocationClient.setLocOption(option);
//mLocationClient为第二步初始化过的LocationClient对象
//需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
//更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明
        fThread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (!NormalUtils.userId().equals("")) {
                    location();
                }
                try {
                    Thread.sleep(10 * 60 * 1000);
                    new Thread(fThread).start();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        fThread.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void location() {
        mLocationClient.start();
    }

    private void updataDriverPosition() {
        hp.put("id", NormalUtils.userId());
        SimplifyThread simplifyThread = new SimplifyThread(UrlUtil.URL_USER_LOCA, hp);
        simplifyThread.setOnKeyResultListener(new SimplifyThread.OnResultListener() {
            @Override
            public void resultBody(String res) {

            }

            @Override
            public void errorBody(String error) {

            }
        });
    }
}
