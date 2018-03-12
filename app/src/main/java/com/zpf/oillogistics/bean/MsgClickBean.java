package com.zpf.oillogistics.bean;

/**
 * Created by Administrator on 2017/11/14.
 *
 * 未查看消息计数
 */

public class MsgClickBean {

    /**
     * status : 0
     * msg : 数据请求成功
     * data : {"sys":0,"goods":0,"offer":1,"click":1}
     * time : 1000
     */

    private int status;
    private String msg;
    private DataBean data;
    private int time;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public static class DataBean {
        /**
         * sys : 0
         * goods : 0
         * offer : 1
         * click : 1
         */

        private int sys;
        private int goods;
        private int offer;
        private int click;

        public int getSys() {
            return sys;
        }

        public void setSys(int sys) {
            this.sys = sys;
        }

        public int getGoods() {
            return goods;
        }

        public void setGoods(int goods) {
            this.goods = goods;
        }

        public int getOffer() {
            return offer;
        }

        public void setOffer(int offer) {
            this.offer = offer;
        }

        public int getClick() {
            return click;
        }

        public void setClick(int click) {
            this.click = click;
        }
    }
}
