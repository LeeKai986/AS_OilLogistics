package com.zpf.oillogistics.base;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.support.multidex.MultiDexApplication;

import com.baidu.mapapi.SDKInitializer;
import com.dream.life.library.throwable.utils.AppManagerUtil;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.DemoHelper;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.db.PersonInfo;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.mob.MobSDK;
import com.umeng.analytics.MobclickAgent;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2017/8/16.
 */

public class CyApplication extends MultiDexApplication {
    private static DbManager.DaoConfig daoConfig;

    public DbManager.DaoConfig getDaoConfig() {
        return daoConfig;
    }

    private static Context context;

    public static final int IS_BTRACE_STOP = 0;
    public static String province = "";
    public static String area = "";
    public static String adress = "";
    public static String lat = "";
    public static String lon = "";


    private static CyApplication mInstance;

    public static CyApplication getInstance() {
        return mInstance;
    }

    public Context getmContext() {
        return context;
    }

    // 全局时间加载
    public static boolean isFirstLoad = true;

    @Override
    public void onCreate() {
        super.onCreate();
//        SDKInitializer.initialize(this);
//        JPushInterface.setDebugMode(true);
//        JPushInterface.init(this);
//        updataObservable = new UpdataObservable();
        try {
            context = getApplicationContext();
            //保存上下文引用
            mInstance = this;
            //专为扩展库所用
            AppManagerUtil.getInstance().setApplication(this);
            JPushInterface.init(this);
            initBaiduMap();
            initHx();
            MobclickAgent.setDebugMode(true);
            MobSDK.init(this);
        } catch (Exception e) {

        }

        daoConfig = new DbManager.DaoConfig()
                .setDbName("sht_db")//创建数据库的名称
                .setDbVersion(1)//数据库版本号
                .setDbOpenListener(new DbManager.DbOpenListener() {
                    @Override
                    public void onDbOpened(DbManager db) {

                        db.getDatabase().enableWriteAheadLogging();
                    }
                })
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                        // TODO: ...
                        // db.addColumn(...);
                        // db.dropTable(...);
                        // ...
                    }
                });//数据库更新操作
        x.Ext.init(this);//Xutils初始化
        // 初始化异常记录器
//        if (isApkDebugable(this)) {
//            // 处理全局不可捕捉异常
//            CrashHandler crashHandler = CrashHandler.getInstance();
//            crashHandler.init(this);
//            // 初始化异常记录器
//            ThrowableLogger throwablelogger = ThrowableLogger.getInstance();
//            throwablelogger.init(this);
//        } else {
//            // 自定义Android崩溃界面。例如：throw new RuntimeException("Boom!");
//            CustomActivityOnCrash.install(this);
//        }

    }

    /**
     * 判断是开发debug模式，还是发布release模式
     *
     * @param context 上下文
     * @return debug模式 返回true 否则返回false
     */
    public static boolean IS_DEBUG;

    public static boolean isApkDebugable(Context context) {
        IS_DEBUG = false;
        try {
            ApplicationInfo info = context.getApplicationInfo();
            IS_DEBUG = (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
            return IS_DEBUG;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return IS_DEBUG;
    }

    public static void saveUserData(PersonInfo info) {
        DbManager db = x.getDb(daoConfig);
        try {
            if (findUserData(info.getPhone()) != null) {
                db.update(info);
            } else {
                db.save(info);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public static PersonInfo findUserData(String phone) {
        DbManager db = x.getDb(daoConfig);
        PersonInfo personInfo = new PersonInfo();
        try {
            personInfo = db.selector(PersonInfo.class).where("phone", "=", phone).findFirst();
            return personInfo;
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Context getCyContext() {
        return context;
    }

    /**
     * 百度地图注册
     */
    public void initBaiduMap() {

        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
//        SDKInitializer.setCoordType(CoordType.BD09LL);
    }

    /**
     * 环信注册
     */
    private void initHx() {
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        EaseUI.getInstance().init(context, options);
        DemoHelper.getInstance().init(this);
        EaseUserUtils.init();
    }
}
