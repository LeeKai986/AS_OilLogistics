package com.zpf.oillogistics.bean.response;

import java.util.List;

/**
 * Created by Administrator on 2017/9/20.
 */

public class PriceTrendResponse {


    /**
     * data : [{"add_time":1520065558,"id":1,"msg2_day":"3","msg2_percent":"-0.22%","msg2_title":"原油变化率","msg2_trend":"下调13","msg2_unit":"元/吨","percent1":"0.25","percent2":"0.12","price1":"WTI: 66.79","price2":"66.75","title1":"原油收盘价","title2":"布伦特","trend1":"up","trend2":"up","trend_date":"2月2号"}]
     * msg : 获取成功.
     * status : 0
     * time : 1000
     */

    private String msg;
    private int status;
    private int time;
    private List<DataBean> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * add_time : 1520065558
         * id : 1
         * msg2_day : 3
         * msg2_percent : -0.22%
         * msg2_title : 原油变化率
         * msg2_trend : 下调13
         * msg2_unit : 元/吨
         * percent1 : 0.25
         * percent2 : 0.12
         * price1 : WTI: 66.79
         * price2 : 66.75
         * title1 : 原油收盘价
         * title2 : 布伦特
         * trend1 : up
         * trend2 : up
         * trend_date : 2月2号
         */

        private int add_time;
        private int id;
        private String msg2_day;
        private String msg2_percent;
        private String msg2_title;
        private String msg2_trend;
        private String msg2_unit;
        private String percent1;
        private String percent2;
        private String price1;
        private String price2;
        private String title1;
        private String title2;
        private String trend1;
        private String trend2;
        private String trend_date;

        public int getAdd_time() {
            return add_time;
        }

        public void setAdd_time(int add_time) {
            this.add_time = add_time;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMsg2_day() {
            return msg2_day;
        }

        public void setMsg2_day(String msg2_day) {
            this.msg2_day = msg2_day;
        }

        public String getMsg2_percent() {
            return msg2_percent;
        }

        public void setMsg2_percent(String msg2_percent) {
            this.msg2_percent = msg2_percent;
        }

        public String getMsg2_title() {
            return msg2_title;
        }

        public void setMsg2_title(String msg2_title) {
            this.msg2_title = msg2_title;
        }

        public String getMsg2_trend() {
            return msg2_trend;
        }

        public void setMsg2_trend(String msg2_trend) {
            this.msg2_trend = msg2_trend;
        }

        public String getMsg2_unit() {
            return msg2_unit;
        }

        public void setMsg2_unit(String msg2_unit) {
            this.msg2_unit = msg2_unit;
        }

        public String getPercent1() {
            return percent1;
        }

        public void setPercent1(String percent1) {
            this.percent1 = percent1;
        }

        public String getPercent2() {
            return percent2;
        }

        public void setPercent2(String percent2) {
            this.percent2 = percent2;
        }

        public String getPrice1() {
            return price1;
        }

        public void setPrice1(String price1) {
            this.price1 = price1;
        }

        public String getPrice2() {
            return price2;
        }

        public void setPrice2(String price2) {
            this.price2 = price2;
        }

        public String getTitle1() {
            return title1;
        }

        public void setTitle1(String title1) {
            this.title1 = title1;
        }

        public String getTitle2() {
            return title2;
        }

        public void setTitle2(String title2) {
            this.title2 = title2;
        }

        public String getTrend1() {
            return trend1;
        }

        public void setTrend1(String trend1) {
            this.trend1 = trend1;
        }

        public String getTrend2() {
            return trend2;
        }

        public void setTrend2(String trend2) {
            this.trend2 = trend2;
        }

        public String getTrend_date() {
            return trend_date;
        }

        public void setTrend_date(String trend_date) {
            this.trend_date = trend_date;
        }
    }
}
