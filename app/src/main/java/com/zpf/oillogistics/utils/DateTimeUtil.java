package com.zpf.oillogistics.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Administrator on 2017/9/15.
 */

public class DateTimeUtil {


    /**
     * 获取当前时间Date
     *
     * @return 现在时间(Now)
     */
    public static String getNowTime(String type) {
        Date d = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat(type, Locale.CHINA);
        return formatter.format(d);
    }
    /**
     * 获取当前时间戳
     */
    public static long getDateTime() {
        return System.currentTimeMillis();
    }

    /**
     * 获取当前时间Date
     */
    public static String getDateTime(long ltime) {
        return getDateTime(ltime, null);
    }

    /**
     * 获取当前时间Date
     */
    public static String getDateTime(long ltime, String type) {
        if ((ltime + "").length() == 10) ltime = ltime * 1000L;
        if (type == null) type = "yyyy-MM-dd HH:mm:ss";
        Date d = new Date(ltime);
        SimpleDateFormat formatter = new SimpleDateFormat(type, Locale.CHINA);
        return formatter.format(d);
    }

    /**
     * 将时间转换为时间戳
     */
    public static String dateToStamp(String s) {
        String res="";
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
            Date ndate=new Date();
            String[] nStr=format.format(ndate).split(" ");
            Date date = format.parse(nStr[0]+" "+s);

            long ts = date.getTime();
            res = String.valueOf(ts/1000);
        } catch (ParseException e) {
        }
        return res;
    }

    /**
     * 将时间转换为时间戳
     */
    public static String dateToStamp(String flag,String s) {
        String res="";
        try {
            SimpleDateFormat format = new SimpleDateFormat(flag);
            Date date = format.parse(s);
            long ts = date.getTime();
            res = String.valueOf(ts/1000);
        } catch (ParseException e) {
        }
        return res;
    }

    /**
     *将时间戳转换成时间
     */
    public static String stampToDate(String model,String s) {
        String res="";
        SimpleDateFormat format = new SimpleDateFormat(model);
        long lt = new Long(s);
        Date date = new Date(lt);
        res = format.format(date);
        return res;
    }


}
