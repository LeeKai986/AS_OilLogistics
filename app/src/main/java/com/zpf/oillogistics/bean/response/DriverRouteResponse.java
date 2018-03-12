package com.zpf.oillogistics.bean.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/10/11.
 */

public class DriverRouteResponse {


    /**
     * status : 0
     * msg : 数据请求成功
     * data : {"total":6,"page":1,"page_size":10,"all_page":1,"data":[{"id":7,"name":"成交价","phone":"18666666666","startplace":"北京北京东城区","endplace":"浙江省杭州市辖区","time":"1507737600","class":6,"load":9,"newstime":"1507792757","status":1,"herder":"/uploads/20171010/150759756059dc1cf81a0f4.jpg"},{"id":6,"name":"成交价","phone":"18666666666","startplace":"北京北京东城区","endplace":"辽宁省沈阳市辖区","time":"1507737600","class":5,"load":89,"newstime":"1507791338","status":1,"herder":"/uploads/20171010/150759756059dc1cf81a0f4.jpg"},{"id":4,"name":"我们","phone":"18666666666","startplace":"吉林省白城市","endplace":"四川省绵阳市","time":"1507545603","class":3,"load":69,"newstime":"1507545609","status":1,"herder":"/uploads/20171010/150759756059dc1cf81a0f4.jpg"},{"id":3,"name":"我们","phone":"18666666666","startplace":"四川省绵阳市","endplace":"四川省资阳市","time":"2147483647","class":6,"load":80,"newstime":"1507545517","status":1,"herder":"/uploads/20171010/150759756059dc1cf81a0f4.jpg"},{"id":2,"name":"我们","phone":"18666666666","startplace":"请输入起点地址","endplace":"请输入终点地址","time":"0","class":0,"load":0,"newstime":"1507545177","status":1,"herder":"/uploads/20171010/150759756059dc1cf81a0f4.jpg"},{"id":1,"name":"我们","phone":"18666666666","startplace":"吉林省白城市","endplace":"四川省资阳市","time":"1507544498768","class":3,"load":108,"newstime":"1507544506","status":1,"herder":"/uploads/20171010/150759756059dc1cf81a0f4.jpg"}]}
     * time : 1000
     */

    private int status;
    private String msg;
    private DataBeanX data;
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

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public static class DataBeanX {
        /**
         * total : 6
         * page : 1
         * page_size : 10
         * all_page : 1
         * data : [{"id":7,"name":"成交价","phone":"18666666666","startplace":"北京北京东城区","endplace":"浙江省杭州市辖区","time":"1507737600","class":6,"load":9,"newstime":"1507792757","status":1,"herder":"/uploads/20171010/150759756059dc1cf81a0f4.jpg"},{"id":6,"name":"成交价","phone":"18666666666","startplace":"北京北京东城区","endplace":"辽宁省沈阳市辖区","time":"1507737600","class":5,"load":89,"newstime":"1507791338","status":1,"herder":"/uploads/20171010/150759756059dc1cf81a0f4.jpg"},{"id":4,"name":"我们","phone":"18666666666","startplace":"吉林省白城市","endplace":"四川省绵阳市","time":"1507545603","class":3,"load":69,"newstime":"1507545609","status":1,"herder":"/uploads/20171010/150759756059dc1cf81a0f4.jpg"},{"id":3,"name":"我们","phone":"18666666666","startplace":"四川省绵阳市","endplace":"四川省资阳市","time":"2147483647","class":6,"load":80,"newstime":"1507545517","status":1,"herder":"/uploads/20171010/150759756059dc1cf81a0f4.jpg"},{"id":2,"name":"我们","phone":"18666666666","startplace":"请输入起点地址","endplace":"请输入终点地址","time":"0","class":0,"load":0,"newstime":"1507545177","status":1,"herder":"/uploads/20171010/150759756059dc1cf81a0f4.jpg"},{"id":1,"name":"我们","phone":"18666666666","startplace":"吉林省白城市","endplace":"四川省资阳市","time":"1507544498768","class":3,"load":108,"newstime":"1507544506","status":1,"herder":"/uploads/20171010/150759756059dc1cf81a0f4.jpg"}]
         */

        private int total;
        private int page;
        private int page_size;
        private int all_page;
        private List<DataBean> data;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getPage_size() {
            return page_size;
        }

        public void setPage_size(int page_size) {
            this.page_size = page_size;
        }

        public int getAll_page() {
            return all_page;
        }

        public void setAll_page(int all_page) {
            this.all_page = all_page;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * id : 7
             * name : 成交价
             * phone : 18666666666
             * startplace : 北京北京东城区
             * endplace : 浙江省杭州市辖区
             * time : 1507737600
             * class : 6
             * load : 9
             * newstime : 1507792757
             * status : 1
             * herder : /uploads/20171010/150759756059dc1cf81a0f4.jpg
             */

            private int id;
            private String name;
            private String phone;
            private String startplace;
            private String endplace;
            private String time;
            @SerializedName("class")
            private int classX;
            private int load;
            private String newstime;
            private int status;
            private String herder;

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

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getStartplace() {
                return startplace;
            }

            public void setStartplace(String startplace) {
                this.startplace = startplace;
            }

            public String getEndplace() {
                return endplace;
            }

            public void setEndplace(String endplace) {
                this.endplace = endplace;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public int getClassX() {
                return classX;
            }

            public void setClassX(int classX) {
                this.classX = classX;
            }

            public int getLoad() {
                return load;
            }

            public void setLoad(int load) {
                this.load = load;
            }

            public String getNewstime() {
                return newstime;
            }

            public void setNewstime(String newstime) {
                this.newstime = newstime;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getHerder() {
                return herder;
            }

            public void setHerder(String herder) {
                this.herder = herder;
            }
        }
    }
}
