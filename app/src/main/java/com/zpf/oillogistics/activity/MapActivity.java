package com.zpf.oillogistics.activity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.Address;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.zpf.oillogistics.R;
import com.zpf.oillogistics.adapter.SearchAdressAdapter;
import com.zpf.oillogistics.base.BaseActivity;
import com.zpf.oillogistics.base.CyApplication;
import com.zpf.oillogistics.bean.DriverDetailsBean;
import com.zpf.oillogistics.customview.SelectMapPopupWindow;
import com.zpf.oillogistics.diy.DiyDialog;
import com.zpf.oillogistics.net.JsonUtil;
import com.zpf.oillogistics.net.SimplifyThread;
import com.zpf.oillogistics.net.UrlUtil;
import com.zpf.oillogistics.utils.BaiduMapManager;
import com.zpf.oillogistics.utils.DateTimeUtil;
import com.zpf.oillogistics.utils.MyToast;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/18.
 * <p>
 * 查看地图
 */

public class MapActivity extends BaseActivity implements SensorEventListener, OnGetRoutePlanResultListener {

    // 布局相关
    // 高德地图
    @BindView(R.id.map_mv)
    MapView mMapView;
    // 查看导航
    @BindView(R.id.map_navi_tv)
    TextView naviTv;
    @BindView(R.id.rel_back)
    RelativeLayout relBack;
    @BindView(R.id.edit_search_AdressAct)
    EditText editSearch;
    @BindView(R.id.tv_cancle_adress_activity)
    TextView tvCancle;
    @BindView(R.id.tv_adress)
    TextView tvAdress;
    @BindView(R.id.tv_config)
    TextView tvConfig;
    @BindView(R.id.rel_search_title)
    RelativeLayout relSearchTitle;
    @BindView(R.id.lv_search)
    ListView lvSearch;
    @BindView(R.id.lin_search)
    LinearLayout linSearch;
    @BindView(R.id.iv_startLocat_logicitsfrag)
    ImageView ivCenter;


    private BaiduMapManager baiduMapManager;
    /* 百度地图view*/
    private BaiduMap mBaiduMap;

    private Double lastX = 0.0;
    private int mCurrentDirection = 0;
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    private float mCurrentAccracy;
    private MyLocationData locData;
    private RoutePlanSearch mSearch = null;//路径规划
    private long lastTime = 0;
    private String cityStr = "成都市";
    private String adress = "";
    private SearchAdressAdapter searchAdapter = null;

    private float mapZoom;//缩放参数

    /* 起点地标注 */
    private Marker startMarker;
    private String starAdress;
    private double starLong = 0;
    private double starLat = 0;
    private String starCity = "";
    /* 终点标注 */
    private Marker endMarker;
    private String endAdress;
    private double endLong = 0;
    private double endLat = 0;
    private String endCity = "";

    private boolean isStarMarker = true;
    private boolean isMarkered = false;

    private List<SuggestionResult.SuggestionInfo> mList = new ArrayList<>();
    private String[] zoom = {"1km", "2km", "5km", "10km", "20km", "25km", "50km", "100km", "200km", "500km", "1000km", "2000km"};

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    break;
                case 1:
                    DriverDetailsBean driverDetailsBean = JsonUtil.driverDetailsBeanResolve(message.obj.toString());
                    if (driverDetailsBean.getData() == null) {
                        MyToast.show(MapActivity.this, "未能获取司机信息");
                        break;
                    }
                    String position = driverDetailsBean.getData().getInfo().getPosition();

                    if (position.equals("")) {
                        MyToast.show(MapActivity.this, "尚未获取司机当前位置");
                        break;
                    }
                    String[] positonArr = position.split(",");
                    if (positonArr.length > 1) {
                        MyToast.show(MapActivity.this, "司机最新位置时间:\n" + DateTimeUtil.getDateTime(Long.parseLong(positonArr[1]) * 1000, "yyyy-MM-dd HH:mm"));
                    }
                    if (positonArr.length > 0) {
                        String[] par = positonArr[0].split("-");
                        if (par.length > 1) {
                            //设定中心点坐标
//                            114.396376,38.627499
//                            LatLng cenpt = new LatLng(38.627499, 114.396376);
                            LatLng cenpt = new LatLng(Double.parseDouble(par[0]), Double.parseDouble(par[1]));
                            //定义地图状态
                            MapStatus mMapStatus = new MapStatus.Builder()
                                    .target(cenpt)
                                    .zoom(18)
                                    .build();
                            //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
                            MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
                            //改变地图状态
                            mBaiduMap.animateMapStatus(mMapStatusUpdate, 500);
//                            MarkerOptions markerOptions = new MarkerOptions();
//                            markerOptions.title("司机当前位置");
//                            markerOptions.position(cenpt);
//                            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.order_details_loca));
//                            mBaiduMap.addOverlay(markerOptions);
                        } else {
                            MyToast.show(MapActivity.this, "尚未获取司机当前位置");
                        }
                    } else {
                        MyToast.show(MapActivity.this, "尚未获取司机当前位置");
                    }
                    break;
            }
            return false;
        }
    });

    @Override
    protected int setLayout() {
        return R.layout.activity_map;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViewAction(Bundle savedInstanceState) {
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图

        baiduMapManager = new BaiduMapManager(MapActivity.this);
        mBaiduMap = mMapView.getMap();

//        MapStatusUpdateFactory.zoomTo(19.0f);
        //是否导航
        if (getIntent().getExtras().getBoolean("nav", true)) {
            tvAdress.setVisibility(View.GONE);
            naviTv.setVisibility(View.VISIBLE);
            tvConfig.setVisibility(View.GONE);
            relSearchTitle.setVisibility(View.GONE);
            linSearch.setVisibility(View.GONE);
            ivCenter.setVisibility(View.GONE);
            //是否定位
            if (getIntent().getExtras().getBoolean("location", false)) {

                // 开启定位图层
                mBaiduMap.setMyLocationEnabled(true);
                baiduMapManager.setLocation(locationListener);
                isStarMarker = false;
            } else {
                starCity = getIntent().getExtras().getString("scity", "");
                starAdress = getIntent().getExtras().getString("sadress", "");
                starLat = Double.parseDouble(getIntent().getExtras().getString("slatitude", "0"));
                starLong = Double.parseDouble(getIntent().getExtras().getString("slongitude", "0"));
                if (starLat != 0) {

                    //标注
                    startMarker = (Marker) mBaiduMap.addOverlay(
                            baiduMapManager.addMarker(new LatLng(starLat, starLong)
                                    , R.mipmap.start_order_poi));
                } else {

                    if (!starAdress.equals("")) {
                        baiduMapManager.citySearch(starCity, starAdress, getGeoCoderListener);
                    } else {
                        baiduMapManager.citySearch(starCity, starCity, getGeoCoderListener);
                    }
                }

                getEndMarker();
            }


            mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus
                    (new MapStatus.Builder().zoom(12.0f).build()));
        } else {
            mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus
                    (new MapStatus.Builder().zoom(15.0f).build()));
            naviTv.setVisibility(View.GONE);
            if (getIntent().getExtras().getString("city") != null && !getIntent().getExtras().getString("city").equals("")) {
                cityStr = getIntent().getExtras().getString("city");
                adress = getIntent().getExtras().getString("adress");
                if (!adress.equals("")) {
                    baiduMapManager.citySearch(cityStr, adress, getGeoCoderListener);
                } else {
                    baiduMapManager.citySearch(cityStr, cityStr, getGeoCoderListener);
                }
            } else {
                driverInfo(getIntent().getStringExtra("driverId"));
            }
        }

        // 初始化搜索模块，注册事件监听
        mSearch = RoutePlanSearch.newInstance();
        mSearch.setOnGetRoutePlanResultListener(this);

        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {

            @Override
            public void onMapStatusChangeStart(MapStatus status) {

            }

            // 移动结束，在这里需要计算出中心点坐标
            @Override
            public void onMapStatusChangeFinish(MapStatus status) {
                float f = mBaiduMap.getMapStatus().zoom;
//                if (isSta) {
                if (mapZoom == f) {
                    baiduMapManager.searchMoveFinish(status, getGeoCoderListener);
                } else {
                    mapZoom = f;
//                        baiduMapManager.setCenterDot(new LatLng((double) configHp.get("startLat"),
//                                (double) configHp.get("startLng")), getGeoCoderListener);
                }
//                }
            }

            @Override
            public void onMapStatusChange(MapStatus status) {
            }
        });

        naviTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mapWindow = new SelectMapPopupWindow(MapActivity.this, mapItemsClick);
                mapWindow.showAtLocation(findViewById(R.id.contanor_layout),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });

        relBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tvCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tvConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);
                finish();
            }
        });

        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                long time = new Date().getTime();

                if (time - lastTime > 1000) {

                    lastTime = time;
                    baiduMapManager.search(editable.toString(), cityStr, getSuggestionListener);
                }
            }

        });
        editSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lvSearch.setVisibility(View.VISIBLE);
            }
        });

        lvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                lvSearch.setVisibility(View.GONE);
                editSearch.setText(mList.get(i).key);
                tvAdress.setText(mList.get(i).key);
                CyApplication.area = mList.get(i).city + mList.get(i).district;
                CyApplication.adress = mList.get(i).key;
                CyApplication.lat = mList.get(i).pt.latitude + "";
                CyApplication.lon = mList.get(i).pt.longitude + "";

                baiduMapManager.setCenterDot(mList.get(i).pt, getGeoCoderListener);
            }
        });
    }

    private void driverInfo(String phone) {
        HashMap<String, String> driverInfoMap = new HashMap<>();
        driverInfoMap.put("phone", phone);
        SimplifyThread driverInfoThread = new SimplifyThread(UrlUtil.RUN_DRIVERINFO, driverInfoMap);
        driverInfoThread.setOnGetResultListener(new SimplifyThread.OnResultListener() {
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


    private void getEndMarker() {
        endCity = getIntent().getExtras().getString("ecity", "");
        endAdress = getIntent().getExtras().getString("eadress", "");
        endLat = Double.parseDouble(getIntent().getExtras().getString("elatitude", "0"));
        endLong = Double.parseDouble(getIntent().getExtras().getString("elongitude", "0"));

        if (endLat != 0.0) {

            //标注
            endMarker = (Marker) mBaiduMap.addOverlay(
                    baiduMapManager.addMarker(new LatLng(endLat, endLong)
                            , R.mipmap.end_order_poi));
            //居中显示
            baiduMapManager.setCenterDot(new LatLng(endLat, endLong)
                    , getGeoCoderListener);

        } else {

            if (!endAdress.equals("")) {
                baiduMapManager.citySearch(endCity, endAdress, getGeoCoderListener);
            } else {
                baiduMapManager.citySearch(endCity, endCity, getGeoCoderListener);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    private SelectMapPopupWindow mapWindow;
    private View.OnClickListener mapItemsClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mapWindow.dismiss();
            switch (v.getId()) {
                case R.id.gaodeBtn:
                    openAMapNavi();
                    break;
                case R.id.baiduBtn:
                    openBaiduNavi();
                    break;
                default:
                    break;
            }
        }
    };

    // 开启百度app导航
    private void openBaiduNavi() {
        try {
            Intent intent = Intent.getIntent("intent://map/direction?origin=latlng:" + starLat + "," + starLong + "|name:" + "我的位置" + "&destination=latlng:" + endLat + "," + endLong + "|name:" + endAdress + "&mode=driving&src=yourCompanyName|yourAppName#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
            if (isInstallByread("com.baidu.BaiduMap")) {
                startActivity(intent);
                Log.e("The MapResult-->", "百度地图客户端已经安装");
            } else {
                Log.e("The MapResult-->", "没有安装百度地图客户端");

                DiyDialog.hintTweBtnDialog(MapActivity.this, "没有安装百度地图客户端,是否安装?", new DiyDialog.HintTweBtnListener() {
                    @Override
                    public void confirm() {
                        installBaiduMap();
                    }
                });
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }


    // 开启高德app导航
    private void openAMapNavi() {
        try {
            Intent intent = Intent.getIntent("androidamap://route?sourceApplication=softname&slat="
                    + starLat + "&slon=" + starLong + "&sname="
                    + "我的位置" + "&dlat=" + endLat + "&dlon=" + endLong
                    + "&dname=" + endAdress + "&dev=0&m=0&t=0");

            if (isInstallByread("com.autonavi.minimap")) {
                startActivity(intent);
                Log.e("The MapResult-->", "高德地图客户端已经安装");
            } else {
                Log.e("The MapResult-->", "没有安装高德地图客户端");
                DiyDialog.hintTweBtnDialog(MapActivity.this, "没有安装高德地图客户端,是否安装?", new DiyDialog.HintTweBtnListener() {
                    @Override
                    public void confirm() {
                        installGaoMap();
                    }
                });
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断是否安装目标应用
     *
     * @param packageName 目标应用安装后的包名
     * @return 是否已安装目标应用
     */
    private boolean isInstallByread(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }

    /**
     * 安装百度地图
     */
    private void installBaiduMap() {
        //(1)百度地图
        //market为路径，id为包名
        //显示手机上所有的market商店
        Uri uri = Uri.parse("market://details?id=com.baidu.BaiduMap");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    /**
     * 安装高德地图
     */
    private void installGaoMap() {
        //(2)高德地图
        Uri uri = Uri.parse("market://details?id=com.autonavi.minimap");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        double x = sensorEvent.values[SensorManager.DATA_X];
        if (Math.abs(x - lastX) > 1.0) {
            mCurrentDirection = (int) x;
            locData = new MyLocationData.Builder()
                    .accuracy(mCurrentAccracy)
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection)
                    .latitude(mCurrentLat)
                    .longitude(mCurrentLon).build();
            mBaiduMap.setMyLocationData(locData);
        }
        lastX = x;
    }


    private BDLocationListener locationListener = new BDLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
//            isFristLoca = true;
            Address ss = location.getAddress();
            Log.i("onReceiveLocation", "ss.city----" + ss.city);

            mCurrentLat = location.getLatitude();
            mCurrentLon = location.getLongitude();
            mCurrentAccracy = location.getRadius();

            if (getIntent().getExtras().getBoolean("nav", true)) {

                starAdress = location.getProvince() + location.getCity() + location.getDistrict() + location.getStreet();
                starLat = location.getLatitude();
                starLong = location.getLongitude();

                //标注
                startMarker = (Marker) mBaiduMap.addOverlay(
                        baiduMapManager.addMarker(new LatLng(starLat, starLong)
                                , R.mipmap.start_order_poi));

//                baiduMapManager.setCenterDot(new LatLng(location.getLatitude(),
//                        location.getLongitude()), getGeoCoderListener);
                isStarMarker = false;

                if (getIntent().getExtras().getBoolean("location", false)) {
                    getEndMarker();
                }
            } else {

                locData = new MyLocationData.Builder()
                        .accuracy(location.getRadius())
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                        .direction(mCurrentDirection)
                        .latitude(location.getLatitude())
                        .longitude(location.getLongitude()).build();
                mBaiduMap.setMyLocationData(locData);
//
                baiduMapManager.setCenterDot(new LatLng(location.getLatitude(),
                        location.getLongitude()), getGeoCoderListener);
            }
        }
    };

    //中心点坐标计算监听
    private OnGetGeoCoderResultListener getGeoCoderListener = new OnGetGeoCoderResultListener() {

        @Override
        public void onGetReverseGeoCodeResult(ReverseGeoCodeResult arg0) { // TODO
            // stub
            if (arg0 != null && arg0.getPoiList() != null) {
                LatLng location = arg0.getLocation();
                baiduMapManager.stopLocation();

//                if (startMarker != null) {
//                        startMarker.remove();
//                startMarker = (Marker) mBaiduMap.addOverlay(
//                        baiduMapManager.addMarker(new LatLng(location.latitude, location.longitude)
//                                , R.mipmap.start_order_poi));
//                }

                locData = new MyLocationData.Builder()
                        .accuracy(mCurrentAccracy)
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                        .direction(mCurrentDirection).latitude(location.latitude)
                        .longitude(location.longitude).build();
                mBaiduMap.setMyLocationData(locData);

                if (arg0.getPoiList() != null && arg0.getPoiList().size() != 0) {

                    if (getIntent().getExtras().getBoolean("nav", true)) {
                        if (!isMarkered) {
                            if (isStarMarker) {
                                if (starLat == 0.0) {
                                    starLat = arg0.getPoiList().get(0).location.latitude;
                                    starLong = arg0.getPoiList().get(0).location.longitude;
                                    //标注
                                    startMarker = (Marker) mBaiduMap.addOverlay(
                                            baiduMapManager.addMarker(new LatLng(starLat, starLong)
                                                    , R.mipmap.start_order_poi));


                                    if (!endAdress.equals("")) {
                                        baiduMapManager.citySearch(endCity, endAdress, getGeoCoderListener);
                                    } else {
                                        baiduMapManager.citySearch(endCity, endCity, getGeoCoderListener);
                                    }
                                }
                                isStarMarker = false;
                            } else {

                                if (endLat == 0.0) {
                                    endLat = arg0.getPoiList().get(0).location.latitude;
                                    endLong = arg0.getPoiList().get(0).location.longitude;

                                    //标注
                                    endMarker = (Marker) mBaiduMap.addOverlay(
                                            baiduMapManager.addMarker(new LatLng(endLat, endLong)
                                                    , R.mipmap.end_order_poi));
                                }
                                isMarkered = true;
                            }
                        }
                    } else {

                        tvAdress.setText(arg0.getPoiList().get(0).name);
                        CyApplication.area = arg0.getAddressDetail().province
                                + arg0.getAddressDetail().city
                                + arg0.getAddressDetail().district;
                        CyApplication.adress = arg0.getPoiList().get(0).name;
                        CyApplication.lat = arg0.getPoiList().get(0).location.latitude + "";
                        CyApplication.lon = arg0.getPoiList().get(0).location.longitude + "";
                    }
                }

                baiduMapManager.animaMap(location, mBaiduMap);
            } else {
                isMarkered = true;
                MyToast.show(MapActivity.this, "该地址无法识别");
            }
        }

        @Override
        public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) { //

            Log.i("geoCodeResult--", "geoCodeResult");
            if (geoCodeResult != null && geoCodeResult.getLocation() != null) {
                LatLng location = geoCodeResult.getLocation();

                locData = new MyLocationData.Builder()
                        .accuracy(mCurrentAccracy)
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                        .direction(mCurrentDirection).latitude(location.latitude)
                        .longitude(location.longitude).build();
                mBaiduMap.setMyLocationData(locData);

                baiduMapManager.animaMap(location, mBaiduMap);
                baiduMapManager.setCenterDot(location, getGeoCoderListener);
            } else {
                isMarkered = true;
                MyToast.show(MapActivity.this, "该地址无法识别");
            }
        }
    };

    OnGetGeoCoderResultListener geoCoderListener = new OnGetGeoCoderResultListener() {
        @Override
        public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
            Log.i("geoCodeResult--", "geoCodeResult");
            if (geoCodeResult != null && geoCodeResult.getLocation() != null) {
                LatLng location = geoCodeResult.getLocation();

                locData = new MyLocationData.Builder()
                        .accuracy(mCurrentAccracy)
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                        .direction(mCurrentDirection).latitude(location.latitude)
                        .longitude(location.longitude).build();
                mBaiduMap.setMyLocationData(locData);

                baiduMapManager.animaMap(location, mBaiduMap);
            }
        }

        @Override
        public void onGetReverseGeoCodeResult(ReverseGeoCodeResult geoCodeResult) {

        }
    };

    private OnGetSuggestionResultListener getSuggestionListener = new OnGetSuggestionResultListener() {
        public void onGetSuggestionResult(SuggestionResult res) {
            if (res == null || res.getAllSuggestions() == null) {

                MyToast.show(MapActivity.this, "该地址无法识别");
                return;
                //未找到相关结果
            }
            //获取在线建议检索结果
            Log.i("search", "localCity----" + res.getAllSuggestions());

            List<SuggestionResult.SuggestionInfo> list;
            list = res.getAllSuggestions();
            mList.clear();

            for (int i = 0; list != null && i < list.size(); i++) {
                SuggestionResult.SuggestionInfo suggeInfo = list.get(i);
                if (suggeInfo.key != null && suggeInfo.pt != null && suggeInfo.city != null) {
                    mList.add(suggeInfo);
                }
            }

            searchAdapter = new SearchAdressAdapter(MapActivity.this, mList);
            lvSearch.setAdapter(searchAdapter);

        }
    };


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {

    }

    @Override
    public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

    }

    @Override
    public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

    }

    @Override
    public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {

    }

    @Override
    public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

    }

    @Override
    public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

    }

}
