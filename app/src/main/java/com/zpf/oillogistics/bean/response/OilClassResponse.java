package com.zpf.oillogistics.bean.response;

import java.util.List;

/**
 * Created by Administrator on 2017/10/10.
 */

public class OilClassResponse {


    /**
     * status : 0
     * msg : 数据请求成功
     * data : [{"id":1,"name":"煤油","pid":0,"time":1506755097},{"id":2,"name":"汽油","pid":0,"time":1506755097},{"id":4,"name":"柴油","pid":0,"time":1506755097},{"id":5,"name":"90#","pid":2,"time":1506755097},{"id":6,"name":"90#加氢","pid":2,"time":1506755097},{"id":7,"name":"92#","pid":2,"time":1506755097},{"id":8,"name":"93#","pid":1,"time":1506755097},{"id":9,"name":"95#","pid":2,"time":1506755097},{"id":10,"name":"97#","pid":2,"time":1506755097},{"id":11,"name":"10","pid":4,"time":1506755097},{"id":12,"name":"0#车柴","pid":4,"time":1506755097},{"id":24,"name":"大幅度","pid":2,"time":1506755097},{"id":25,"name":"非固定","pid":0,"time":1506755097}]
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
         * name : 煤油
         * pid : 0
         * time : 1506755097
         */

        private int id;
        private String name;
        private int pid;
        private int time;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }
    }
}
