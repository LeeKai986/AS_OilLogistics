package com.zpf.oillogistics.utils;

import android.content.Context;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;


/**
 * Created by Administrator on 2017/8/21.
 */

public class BaiduMapManager {

    private Context context;

    // 定位相关
    private LocationClient mLocClient;
    private SuggestionSearch mSuggestionSearch;
    private GeoCoder geocode;

    public BaiduMapManager(Context context) {
        this.context=context;
    }

    /**
     * 定位
     */
    public void setLocation(BDLocationListener myListener){
        // 定位初始化
        mLocClient = new LocationClient(context);

        mLocClient.registerLocationListener(myListener);

        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备

        option.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系

        option.setAddrType("all");
        //设置获取地址信息

        option.setScanSpan(0);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的

        option.setIsNeedAddress(true);
        //可选，设置是否需要地址信息，默认不需要

        option.setOpenGps(true);
        //可选，默认false,设置是否使用gps

        option.setLocationNotify(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果

        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”

        option.setIsNeedLocationPoiList(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到

        option.setIgnoreKillProcess(false);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死

        option.SetIgnoreCacheException(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集

        option.setEnableSimulateGps(false);

        mLocClient.setLocOption(option);
        mLocClient.start();
    }

    public void stopLocation(){
        if(mLocClient!=null)
        mLocClient.stop();
    }

    /**
     * 设置中心点
     */
    public void setCenterDot(LatLng ll , OnGetGeoCoderResultListener listener ){
        //设置地图中心点位置
        MapStatus status = new MapStatus.Builder().target(ll).build();
        searchMoveFinish(status,listener);
    }

    /**
     *计算中心坐标
     */
    public void searchMoveFinish(MapStatus status, OnGetGeoCoderResultListener listener ){
        GeoCoder geoCoder = GeoCoder.newInstance();
        ReverseGeoCodeOption reverCoder = new ReverseGeoCodeOption();
        reverCoder.location(status.target);
        geoCoder.reverseGeoCode(reverCoder); //
        geoCoder.setOnGetGeoCodeResultListener(listener);
    }

    /**
     * 移动到中心动画
     */
    public void animaMap(LatLng ll, BaiduMap baiduMap){
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(ll);
        baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        baiduMap.setMyLocationEnabled(false);
    }

    /**
     * 检索
     *
     * @param ss
     */
    public void search(String ss, String localCity, OnGetSuggestionResultListener listener) {

        if(mSuggestionSearch==null)
        mSuggestionSearch = SuggestionSearch.newInstance();

        mSuggestionSearch.setOnGetSuggestionResultListener(listener);

        mSuggestionSearch.requestSuggestion((new SuggestionSearchOption())
                .keyword(ss)
                .city(localCity));
    }

    public void citySearch(String city, OnGetGeoCoderResultListener listener){
        if(mSuggestionSearch==null)
        geocode= GeoCoder.newInstance();
        geocode.setOnGetGeoCodeResultListener(listener);
        geocode.geocode(new GeoCodeOption()
                .city(city)
                .address(city));
    }

    public void citySearch(String city,String adress, OnGetGeoCoderResultListener listener){
        if(mSuggestionSearch==null)
            geocode= GeoCoder.newInstance();
        geocode.setOnGetGeoCodeResultListener(listener);
        geocode.geocode(new GeoCodeOption()
                .city(city)
                .address(adress));
    }

    /**
     * 添加marker
     * @param ll
     * @param mipID
     * @return
     */
    public MarkerOptions addMarker(LatLng ll, int mipID){
    //准备 marker 的图片
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(mipID);
        //准备marker option 添加 marker 使用

        return new MarkerOptions().icon(bitmap).position(ll);
    }

    public void setMapUnMove(BaiduMap baiduMap, boolean b){
        UiSettings settings = baiduMap.getUiSettings();
        settings.setAllGesturesEnabled(b);   //关闭一切手势操作
        settings.setOverlookingGesturesEnabled(b);//屏蔽双指下拉时变成3D地图
        settings.setZoomGesturesEnabled(b);//获取是否允许缩放手势返回:是否允许缩放手势
    }


    public void destroy(){
        mLocClient.stop();
    }


    /**
     * 下载离线地图
     * @param city
     */
    private void getOfflineMa(String city){

//        mOffline = new MKOfflineMap();
//        // 传入接口事件，离线地图更新会触发该回调
//        mOffline.init(this);
//
//        // 获取城市可更新列表
//        ArrayList<MKOLSearchRecord> records = mOffline.searchCity(city);
//        for(int i=0;records!=null&&i<records.size();i++){
//            Log.d("records","size"+records.size()
//                    +"\ncityName"+records.get(i).cityName
//                    +"\ncityID"+records.get(i).cityID);
//            mOffline.start(records.get(i).cityID);
//        }

        // 开始下载离线地图，传入参数为cityID, cityID表示城市的数字标识。
//        mOffline.start(records.get(0).cityID);
        // 暂停下载
//        mOffline.pause(cityid);
//        // 删除下载
//        mOffline.remove(cityid);

        // 在更新过程中会通过onGetOfflineMapState来回调信息，可查看更新进度、新离线地图安装、版本更新提示。
    }


//    private void routeplanToNavi() {
//        if (!hasInitSuccess) {
//            Toast.makeText(context, "还未初始化!", Toast.LENGTH_SHORT).show();
//        }
//
//        BNRoutePlanNode sNode = null;
//        BNRoutePlanNode eNode = null;
//
//        sNode = new BNRoutePlanNode(116.30784537597782, 40.057009624099436, "百度大厦", null, BNRoutePlanNode.CoordinateType.BD09LL);
//        eNode = new BNRoutePlanNode(116.40386525193937, 39.915160800132085, "北京天安门", null, BNRoutePlanNode.CoordinateType.BD09LL);
//
//        if (sNode != null && eNode != null) {
//            List<BNRoutePlanNode> list = new ArrayList<BNRoutePlanNode>();
//            list.add(sNode);
//            list.add(eNode);
//            BaiduNaviManager.getInstance().launchNavigator(this, list, 1, true, new DemoRoutePlanListener(sNode));
//        }
//    }



}
