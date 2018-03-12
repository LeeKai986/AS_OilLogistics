package com.zpf.oillogistics.net;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.zpf.oillogistics.bean.AttenProductBean;
import com.zpf.oillogistics.bean.AttenQuoteBean;
import com.zpf.oillogistics.bean.DriverDetailsBean;
import com.zpf.oillogistics.bean.FirmDetailsWantBuyBean;
import com.zpf.oillogistics.bean.FriendDetailsBean;
import com.zpf.oillogistics.bean.MsgHomeSystemBean;
import com.zpf.oillogistics.bean.NormalBean;
import com.zpf.oillogistics.bean.OilOrChemtryBean;
import com.zpf.oillogistics.bean.OilOrChemtryInfoBean;
import com.zpf.oillogistics.bean.ProviceAndCityBean;
import com.zpf.oillogistics.bean.QuoteBean;
import com.zpf.oillogistics.bean.RegisterBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/28.
 * <p>
 * 解析类
 */

public class JsonUtil {

    // 推荐订单(非农场)
    public static NormalBean normalBeanResolve(String json) {
        Gson gson = new Gson();
        NormalBean normalBean = null;
        try {
            normalBean = gson.fromJson(json, NormalBean.class);
        } catch (JsonSyntaxException e) {
            Log.i("The JsonError-->", e.toString());
            return normalBean;
        }
        return normalBean;
    }

    // 城市解析
    public static ArrayList<ProviceAndCityBean> proviceAndCityBeensResolve(String json) {
        Gson gson = new Gson();
        ArrayList<ProviceAndCityBean> proviceAndCityBeens = null;
        try {
            proviceAndCityBeens = gson.fromJson(json, new TypeToken<ArrayList<ProviceAndCityBean>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            Log.i("The JsonError-->", e.toString());
            return proviceAndCityBeens;
        }
        return proviceAndCityBeens;
    }

    // 司机详情
    public static DriverDetailsBean driverDetailsBeanResolve(String json) {
        Gson gson = new Gson();
        DriverDetailsBean driverDetailsBean = null;
        try {
            driverDetailsBean = gson.fromJson(json, DriverDetailsBean.class);
        } catch (JsonSyntaxException e) {
            Log.i("The JsonError-->", e.toString());
            return driverDetailsBean;
        }
        return driverDetailsBean;
    }

    // 求购企业详情
    public static FirmDetailsWantBuyBean firmDetailsWantBuyBeanResolve(String json) {
        Gson gson = new Gson();
        FirmDetailsWantBuyBean firmDetailsWantBuyBean = null;
        try {
            firmDetailsWantBuyBean = gson.fromJson(json, FirmDetailsWantBuyBean.class);
        } catch (JsonSyntaxException e) {
            Log.i("The JsonError-->", e.toString());
            return firmDetailsWantBuyBean;
        }
        return firmDetailsWantBuyBean;
    }

    // 石油类化工类详情
    public static OilOrChemtryInfoBean oilOrChemtryInfoBeanResolve(String json) {
        Gson gson = new Gson();
        OilOrChemtryInfoBean oilOrChemtryInfoBean = null;
        try {
            oilOrChemtryInfoBean = gson.fromJson(json, OilOrChemtryInfoBean.class);
        } catch (JsonSyntaxException e) {
            Log.i("The JsonError-->", e.toString());
            return oilOrChemtryInfoBean;
        }
        return oilOrChemtryInfoBean;
    }

    // 报价列表
    public static QuoteBean quoteBeanResolve(String json) {
        Gson gson = new Gson();
        QuoteBean quoteBean = null;
        try {
            quoteBean = gson.fromJson(json, QuoteBean.class);
        } catch (JsonSyntaxException e) {
            Log.i("The JsonError-->", e.toString());
            return quoteBean;
        }
        return quoteBean;
    }

    // 石油类 化工类
    public static OilOrChemtryBean oilOrChemtryBeanResolve(String json) {
        Gson gson = new Gson();
        OilOrChemtryBean oilOrChemtryBean = null;
        try {
            oilOrChemtryBean = gson.fromJson(json, OilOrChemtryBean.class);
        } catch (JsonSyntaxException e) {
            Log.i("The JsonError-->", e.toString());
            return oilOrChemtryBean;
        }
        return oilOrChemtryBean;
    }

    // 注册
    public static RegisterBean registerBeanResolve(String json) {
        Gson gson = new Gson();
        RegisterBean bean = null;
        try {
            bean = gson.fromJson(json, RegisterBean.class);
        } catch (JsonSyntaxException e) {
            Log.i("The JsonError-->", e.toString());
            return bean;
        }
        return bean;
    }

    // 关注的商品信息
    public static AttenProductBean attenProductBeanResolve(String json) {
        Gson gson = new Gson();
        AttenProductBean bean = null;
        try {
            bean = gson.fromJson(json, AttenProductBean.class);
        } catch (JsonSyntaxException e) {
            Log.i("The JsonError-->", e.toString());
            return bean;
        }
        return bean;
    }

    // 关注的报价信息
    public static AttenQuoteBean attenQuoteBeanResolve(String json) {
        Gson gson = new Gson();
        AttenQuoteBean bean = null;
        try {
            bean = gson.fromJson(json, AttenQuoteBean.class);
        } catch (JsonSyntaxException e) {
            Log.i("The JsonError-->", e.toString());
            return bean;
        }
        return bean;
    }

    // 系统信息
    public static MsgHomeSystemBean msgHomeSystemBeanResolve(String json) {
        Gson gson = new Gson();
        MsgHomeSystemBean bean = null;
        try {
            bean = gson.fromJson(json, MsgHomeSystemBean.class);
        } catch (JsonSyntaxException e) {
            Log.i("The JsonError-->", e.toString());
            return bean;
        }
        return bean;
    }

    // 获取好友信息
    public static FriendDetailsBean friendDetailsBeanResolve(String json) {
        Gson gson = new Gson();
        FriendDetailsBean bean = null;
        try {
            bean = gson.fromJson(json, FriendDetailsBean.class);
        } catch (JsonSyntaxException e) {
            Log.i("The JsonError-->", e.toString());
            return bean;
        }
        return bean;
    }
}
