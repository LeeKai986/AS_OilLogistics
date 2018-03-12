package com.zpf.oillogistics.bean.response;

import java.util.List;

/**
 * Created by Administrator on 2017/10/11.
 */

public class HelpResponse {


    /**
     * status : 0
     * msg : 获取成功
     * data : [{"id":1,"title":"司机验证有问题","contnet":"提交后需要3个工作日审核"},{"id":2,"title":"问题","contnet":"文热天"}]
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
         * title : 司机验证有问题
         * contnet : 提交后需要3个工作日审核
         */

        private int id;
        private String title;
        private String contnet;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContnet() {
            return contnet;
        }

        public void setContnet(String contnet) {
            this.contnet = contnet;
        }
    }
}
