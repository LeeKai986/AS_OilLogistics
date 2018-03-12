package com.zpf.oillogistics.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/11/9.
 */

public class ShipTimeBean {

    /**
     * status : 0
     * msg : 数据请求成功
     * data : [{"id":1,"kilometre":100,"service_time":"12小时","time":1510217748},{"id":2,"kilometre":200,"service_time":"24小时","time":1510217769}]
     * time : 1000
     */

    private int status;
    private String msg;
    private int time;
    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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
         * id : 1
         * kilometre : 100
         * service_time : 12小时
         * time : 1510217748
         */

        private int id;
        private String kilometre;
        private String service_time;
        private int time;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getKilometre() {
            return kilometre;
        }

        public void setKilometre(String kilometre) {
            this.kilometre = kilometre;
        }

        public String getService_time() {
            return service_time;
        }

        public void setService_time(String service_time) {
            this.service_time = service_time;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }
    }
}
