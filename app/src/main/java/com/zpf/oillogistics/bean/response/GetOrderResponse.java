package com.zpf.oillogistics.bean.response;

/**
 * Created by Administrator on 2017/9/21.
 */

public class GetOrderResponse {


    /**
     * status : 0
     * msg : 数据获取成功
     * data : {"id":1,"startplace":"成都市武侯区","startaddress":"成都市武侯区楚峰国际中心B座","endplace":"成都市锦江区","endaddress":"成都市锦江区899号","goodsname":"国有柴油","company":"中国石油","number":60,"price":"900.00","method":1,"time":1111111111,"name":"钟老师","phone":"18234578932","comment":"这是易碎物品，请轻拿轻放","newstime":"1576288983","status":1,"identity":2}
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
         * id : 1
         * startplace : 成都市武侯区
         * startaddress : 成都市武侯区楚峰国际中心B座
         * endplace : 成都市锦江区
         * endaddress : 成都市锦江区899号
         * goodsname : 国有柴油
         * company : 中国石油
         * number : 60
         * price : 900.00
         * method : 1
         * time : 1111111111
         * name : 钟老师
         * phone : 18234578932
         * comment : 这是易碎物品，请轻拿轻放
         * newstime : 1576288983
         * status : 1
         * identity : 2
         * longitude: "117.285064"
         * latitude: "39.118483"
         * s_longitude: "117.265222"
         * s_latitude: "39.062974"
         */

        private int id;
        private String startplace;
        private String startaddress;
        private String endplace;
        private String endaddress;
        private String goodsname;
        private String company;
        private int number;
        private String price;
        private int method;
        private String time;
        private String name;
        private String phone;
        private String comment;
        private String newstime;
        private int status;
        private int identity;
        private String longitude;
        private String latitude;
        private String s_longitude;
        private String s_latitude;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
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

        public int getIdentity() {
            return identity;
        }

        public void setIdentity(int identity) {
            this.identity = identity;
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
    }
}
