package com.zpf.oillogistics.bean.response;

import java.util.List;

/**
 * Created by Administrator on 2017/11/2.
 */

public class ProductClassResponsse {


    /**
     * status : 0
     * msg : 数据请求成功
     * data : [{"id":28,"name":"汽油","pid":0,"time":1508464809,"child":[{"id":26,"name":"89c","pid":28,"time":1508463964,"child":[]}]},{"id":29,"name":"煤油","pid":0,"time":1508464908,"child":[{"id":27,"name":"90#","pid":29,"time":1508464797,"child":[]}]},{"id":30,"name":"柴油","pid":0,"time":1508464934,"child":[{"id":31,"name":"80#","pid":30,"time":1508464951,"child":[]}]}]
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
         * id : 28
         * name : 汽油
         * pid : 0
         * time : 1508464809
         * child : [{"id":26,"name":"89c","pid":28,"time":1508463964,"child":[]}]
         */

        private int id;
        private String name;
        private int pid;
        private int time;
        private List<ChildBean> child;

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

        public List<ChildBean> getChild() {
            return child;
        }

        public void setChild(List<ChildBean> child) {
            this.child = child;
        }

        public static class ChildBean {
            /**
             * id : 26
             * name : 89c
             * pid : 28
             * time : 1508463964
             * child : []
             */

            private int id;
            private String name;
            private int pid;
            private int time;
            private List<?> child;

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

            public List<?> getChild() {
                return child;
            }

            public void setChild(List<?> child) {
                this.child = child;
            }
        }
    }
}
