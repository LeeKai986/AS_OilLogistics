package com.zpf.oillogistics.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/11/30.
 * <p>
 * 朋友详情
 */

public class FriendDetailsBean {

    /**
     * status : 0
     * msg : 数据请求成功
     * data : [{"relname":"刘师傅","niname":"","face":"/uploads/20171109/92fc53cc094af82ed6890b1efaac74dc.jpg"},{"relname":"","niname":"","face":"/uploads/20171109/bf475b08147a9e8c13b1232a46cfd363.jpg"},{"relname":"","niname":"","face":"/uploads/20171109/cc4ba6ac3ac7d3f4ab751847198349c1.jpg"}]
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
         * relname : 刘师傅
         * niname :
         * face : /uploads/20171109/92fc53cc094af82ed6890b1efaac74dc.jpg
         */

        private String relname;
        private String niname;
        private String face;

        public String getRelname() {
            return relname;
        }

        public void setRelname(String relname) {
            this.relname = relname;
        }

        public String getNiname() {
            return niname;
        }

        public void setNiname(String niname) {
            this.niname = niname;
        }

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
        }
    }
}
