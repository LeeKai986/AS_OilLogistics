package com.dream.life.library.throwable;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.text.format.Formatter;


import com.dream.life.library.throwable.utils.LogUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


/**
 * 记录异常信息类
 */
public class ThrowableLogger {
    private static final String TAG = "ThrowableLogger";
    //
    private static final String PATH_NAME = "z_ErrLog";//SDCard存放错误日志目录
    private static final String ERROR_FILE_NAME_PREFIX = "err";//错误文件头部
    // CrashHandler实例
    private volatile static ThrowableLogger instance = null;
    // 程序的Context对象
    private Context mContext;
    // 用来存储设备信息和异常信息
    private Map<String, String> infos = new HashMap<String, String>();
    // 用于格式化日期,作为日志文件名的一部分
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.CHINA);
    private DateFormat df_d = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
    // 错误文件头部
    private final String FILE_PRE = ERROR_FILE_NAME_PREFIX;
    // sd卡的根目录
    private String mSDCardRootPath = Environment.getExternalStorageDirectory().getPath();
    // 手机的缓存根目录
    private String mDataRootPath = null;
    // 文件保存的目录
    private final static String FOLDER_NAME = "/" + PATH_NAME + "/error_file";

    private ThrowableLogger() {
    }

    public static ThrowableLogger getInstance() {
        //先检查实例是否存在，如果不存在才进入下面的同步块
        if (instance == null) {
                //同步块，线程安全的创建实例
            synchronized (ThrowableLogger.class) {
                //再次检查实例是否存在，如果不存在才真正的创建实例
                if (instance == null) {
                    instance = new ThrowableLogger();
                }
            }
        }
        return instance;
    }

    public void init(Context context) {
        mContext = context;
        mDataRootPath = context.getCacheDir().getPath();
    }

    /**
     * 获取储存错误日志的目录
     *
     * @return
     */
    private String getStorageDirectory() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ? mSDCardRootPath + FOLDER_NAME : mDataRootPath + FOLDER_NAME;
    }

    /**
     * 保存错误信息到文件中
     *
     * @param ex
     * @param type 错误类型
     */
    public void saveThrowableInfo2File(Throwable ex, String type) {
        // 文件名start
        String name = "unknow";

        String time = formatter.format(new Date());
        String fileName = FILE_PRE + "_" + time + "_" + name + ".log";
        StringBuffer sb = new StringBuffer();
        String pathString = getStorageDirectory();
        File createFile = new File(pathString);
        if (!createFile.exists()) {
            createFile.mkdirs();// 创建文件目录
        }

        File file = new File(createFile, fileName);// 错误日志保存路劲

        File[] files = new File(pathString).listFiles(filenameFilter);// 文件名过滤器(以文件头进行搜索Constants.ERROR_FILE_NAME_PREFIX)
        if (files != null && files.length > 0) {
            file = files[0];
        }

        if (file.isDirectory()) {// 如果createFile+fileName表示的是一个目录则返回true
            file.delete();// 删除目录
        }

        if (!file.exists()) {// 错误日志最顶部信息；当此抽象路径名表示的文件或目录存在时，返回 true;否则返回 false
            collectDeviceInfo(mContext);// 收集设备参数信息
            for (Map.Entry<String, String> entry : infos.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                sb.append(key + "=" + value + "\n");
            }
            getTotalMemory(mContext, sb);// 获取总内存信息
        }
        sb.append("\n" + formatter.format(new Date()) + " " + type + " \n===============》异常信息《===============\n");
        getMemoryInfo(mContext, sb);
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);

        try {
            //true表示:写入到流中的数据将追加在现存文件之后
            //false表示:将用写入到流中的数据替换现存文件
            FileOutputStream fos = new FileOutputStream(file, true);
            fos.write(sb.toString().getBytes());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            LogUtil.e(TAG, e);
        }
    }

    /**
     * 收集设备参数信息
     *
     * @param ctx
     */
    private void collectDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (NameNotFoundException e) {
            LogUtil.e(TAG, e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
                LogUtil.d(TAG, field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                LogUtil.e(TAG, e);
            }
        }
    }

    // 获取内存信息
    private void getMemoryInfo(Context context, StringBuffer stringBuffer) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        // 单个app的内存信息
        int appMaxMemory = activityManager.getMemoryClass();
        MemoryInfo machineMi = new MemoryInfo();
        activityManager.getMemoryInfo(machineMi);
        // 全局可用内存
        String availMem = Formatter.formatFileSize(context, machineMi.availMem);
        // 系统内存不足的阀值，即临界值
        String threshold = Formatter.formatFileSize(context, machineMi.threshold);
        // 如果当前可用内存<=threshold，该值为真
        boolean isLowMemory = machineMi.lowMemory;
        stringBuffer.append("MachineCurrentMemory : 全局可用内存 availMem=" + availMem + ", 系统处于低内存运行值 threshold=" + threshold + ", (是否内存不足)isLowMemory="
                + isLowMemory + "\n");
        android.os.Debug.MemoryInfo[] appMi = activityManager.getProcessMemoryInfo(new int[]{android.os.Process.myPid()});
        if (appMi != null) {
            // 获取进程占内存信息形如3.14MB
            double memSize = (appMi[0].getTotalPss()) / 1024.0;
            int temp = (int) (memSize * 100);
            memSize = temp / 100.0;
            stringBuffer.append("APP最大使用的内存空间 AppMaxMemory=" + appMaxMemory + ".00MB,当前占用内存空间 AppCurrentMemory=" + memSize + "MB\n");
        }
    }

    // 获取总内存信息
    private void getTotalMemory(Context context, StringBuffer stringBuffer) {
        String str1 = "/proc/meminfo";// 系统内存信息文件
        String str2;
        String[] arrayOfString;
        long initial_memory = 0;
        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(localFileReader, 8192);
            str2 = localBufferedReader.readLine();// 读取meminfo第一行，系统总内存大小
            arrayOfString = str2.split("\\s+");
            for (String num : arrayOfString) {
                LogUtil.i(str2, num + "\t");
            }

            initial_memory = Integer.valueOf(arrayOfString[1]).intValue() * 1024;// 获得系统总内存，单位是KB，乘以1024转换为Byte
            localBufferedReader.close();

        } catch (IOException e) {
        }
        String totalMemory = Formatter.formatFileSize(context, initial_memory);// Byte转换为KB或者MB，内存大小规格化
        stringBuffer.append("TotalMemory=" + totalMemory + "\n");
    }

    // 文件名过滤器
    FilenameFilter filenameFilter = new FilenameFilter() {

        @Override
        public boolean accept(File dir, String filename) {
            if (filename.startsWith(FILE_PRE + "_" + df_d.format(new Date()))) {
                return true;
            }
            return false;
        }
    };

}
