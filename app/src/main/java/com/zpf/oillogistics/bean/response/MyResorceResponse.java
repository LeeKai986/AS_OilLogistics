package com.zpf.oillogistics.bean.response;

import java.util.List;

/**
 * Created by Administrator on 2017/10/11.
 */

public class MyResorceResponse {

    /**
     * status : 0
     * msg : 数据请求成功
     * data : {"total":2,"page":"1","page_size":10,"all_page":1,"data":[{"id":69,"uid":205,"relname":"","startplace":"北京市宣武区天桥街道","startaddress":"北京市北京市西城区北纬路30号","endplace":"天津市河东区东新街道","endaddress":"天津市天津市河东区盘山道22号","goodsname":"石油","company":"东明佳运","number":200,"price":"1200.00","method":3,"time":1511539200,"name":"小王","phone":"18000000000","comment":"水电费","newstime":1510650724,"status":2,"identity":3,"statuss":1,"longitude":"117.282862","latitude":"39.148167","s_longitude":"116.403342","s_latitude":"39.889705","herder":"/uploads/20171121/15112280095a1382696b1fa.jpg"},{"id":85,"uid":205,"relname":"","startplace":"天津市河东区东新街道","startaddress":"天津市天津市河东区盘山道22号","endplace":"天津市河东区东新街道","endaddress":"天津市天津市河东区盘山道22号","goodsname":"石油","company":"东明佳运","number":200,"price":"1200.00","method":1,"time":1511336749,"name":"小张","phone":"18000000000","comment":"的四个佛挡杀佛的发个梵蒂冈","newstime":1511250349,"status":2,"identity":3,"statuss":1,"longitude":"117.28315","latitude":"39.148615","s_longitude":"117.283437","s_latitude":"39.148727","herder":"/uploads/20171121/15112280095a1382696b1fa.jpg"}]}
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
         * total : 2
         * page : 1
         * page_size : 10
         * all_page : 1
         * data : [{"id":69,"uid":205,"relname":"","startplace":"北京市宣武区天桥街道","startaddress":"北京市北京市西城区北纬路30号","endplace":"天津市河东区东新街道","endaddress":"天津市天津市河东区盘山道22号","goodsname":"石油","company":"东明佳运","number":200,"price":"1200.00","method":3,"time":1511539200,"name":"小王","phone":"18000000000","comment":"水电费","newstime":1510650724,"status":2,"identity":3,"statuss":1,"longitude":"117.282862","latitude":"39.148167","s_longitude":"116.403342","s_latitude":"39.889705","herder":"/uploads/20171121/15112280095a1382696b1fa.jpg"},{"id":85,"uid":205,"relname":"","startplace":"天津市河东区东新街道","startaddress":"天津市天津市河东区盘山道22号","endplace":"天津市河东区东新街道","endaddress":"天津市天津市河东区盘山道22号","goodsname":"石油","company":"东明佳运","number":200,"price":"1200.00","method":1,"time":1511336749,"name":"小张","phone":"18000000000","comment":"的四个佛挡杀佛的发个梵蒂冈","newstime":1511250349,"status":2,"identity":3,"statuss":1,"longitude":"117.28315","latitude":"39.148615","s_longitude":"117.283437","s_latitude":"39.148727","herder":"/uploads/20171121/15112280095a1382696b1fa.jpg"}]
         */

        private int total;
        private String page;
        private int page_size;
        private int all_page;
        private List<DataBean> data;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public String getPage() {
            return page;
        }

        public void setPage(String page) {
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
             * id : 69
             * uid : 205
             * relname :
             * startplace : 北京市宣武区天桥街道
             * startaddress : 北京市北京市西城区北纬路30号
             * endplace : 天津市河东区东新街道
             * endaddress : 天津市天津市河东区盘山道22号
             * goodsname : 石油
             * company : 东明佳运
             * number : 200
             * price : 1200.00
             * method : 3
             * time : 1511539200
             * name : 小王
             * phone : 18000000000
             * comment : 水电费
             * newstime : 1510650724
             * status : 2
             * identity : 3
             * statuss : 1
             * longitude : 117.282862
             * latitude : 39.148167
             * s_longitude : 116.403342
             * s_latitude : 39.889705
             * herder : /uploads/20171121/15112280095a1382696b1fa.jpg
             */

            private int id;
            private int uid;
            private String relname;
            private String startplace;
            private String startaddress;
            private String endplace;
            private String endaddress;
            private String goodsname;
            private String company;
            private int number;
            private String price;
            private int method;
            private int time;
            private String name;
            private String phone;
            private String comment;
            private int newstime;
            private int status;
            private int identity;
            private int statuss;
            private String longitude;
            private String latitude;
            private String s_longitude;
            private String s_latitude;
            private String herder;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public String getRelname() {
                return relname;
            }

            public void setRelname(String relname) {
                this.relname = relname;
            }

            public String getStartplace() {
                return startplace;
            }

            public void setStartplace(String startplace) {
                this.startplace = startplace;
            }

            public String getStartaddress() {
                return startaddress;
            }

            public void setStartaddress(String startaddress) {
                this.startaddress = startaddress;
            }

            public String getEndplace() {
                return endplace;
            }

            public void setEndplace(String endplace) {
                this.endplace = endplace;
            }

            public String getEndaddress() {
                return endaddress;
            }

            public void setEndaddress(String endaddress) {
                this.endaddress = endaddress;
            }

            public String getGoodsname() {
                return goodsname;
            }

            public void setGoodsname(String goodsname) {
                this.goodsname = goodsname;
            }

            public String getCompany() {
                return company;
            }

            public void setCompany(String company) {
                this.company = company;
            }

            public int getNumber() {
                return number;
            }

            public void setNumber(int number) {
                this.number = number;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public int getMethod() {
                return method;
            }

            public void setMethod(int method) {
                this.method = method;
            }

            public int getTime() {
                return time;
            }

            public void setTime(int time) {
                this.time = time;
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

            public String getComment() {
                return comment;
            }

            public void setComment(String comment) {
                this.comment = comment;
            }

            public int getNewstime() {
                return newstime;
            }

            public void setNewstime(int newstime) {
                this.newstime = newstime;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getIdentity() {
                return identity;
            }

            public void setIdentity(int identity) {
                this.identity = identity;
            }

            public int getStatuss() {
                return statuss;
            }

            public void setStatuss(int statuss) {
                this.statuss = statuss;
            }

            public String getLongitude() {
                return longitude;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }

            public String getLatitude() {
                return latitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }

            public String getS_longitude() {
                return s_longitude;
            }

            public void setS_longitude(String s_longitude) {
                this.s_longitude = s_longitude;
            }

            public String getS_latitude() {
                return s_latitude;
            }

            public void setS_latitude(String s_latitude) {
                this.s_latitude = s_latitude;
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
