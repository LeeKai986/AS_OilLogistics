package com.zpf.oillogistics.bean.response;

import java.util.List;

/**
 * Created by Administrator on 2017/10/23.
 */

public class DriverResponse {


    /**
     * status : 0
     * msg : 数据请求成功
     * data : [{"face":"/uploads/20171019/150837680159e800e1454c9.jpg","relname":"","cartcode":"0"}]
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
         * face : /uploads/20171019/150837680159e800e1454c9.jpg
         * relname :
         * cartcode : 0
         */

        private String face;
        private String relname;
        private String cartcode;

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
        }

        public String getRelname() {
            return relname;
        }

        public void setRelname(String relname) {
            this.relname = relname;
        }

        public String getCartcode() {
            return cartcode;
        }

        public void setCartcode(String cartcode) {
            this.cartcode = cartcode;
        }
    }
}
