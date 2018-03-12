package com.zpf.oillogistics.bean.response;

/**
 * Created by Administrator on 2017/10/12.
 */

public class CompanyOrderDetailsResponse {


    /**
     * status : 0
     * msg : 数据请求成功
     * data : {"id":23,"no":"20179710097923","uid":87,"goodsname":"发布产品测试数据","company":"测试","address":"四川省成都市武侯区","phone":"18888888888","price":"999.00","number":22,"tocompany":"测试","toname":"","tophone":"","toplace":"吉林省长春市","toaddress":"楚峰国际","money":"0.00","status":6,"time":1507793706,"totalmoney":21978}
     * time : 1000
     */

    public int status;
    public String msg;
    public DataBean data;
    public int time;

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
         * id : 23
         * no : 20179710097923
         * uid : 87
         * goodsname : 发布产品测试数据
         * company : 测试
         * address : 四川省成都市武侯区
         * phone : 18888888888
         * price : 999.00
         * number : 22
         * tocompany : 测试
         * toname : 
         * tophone : 
         * toplace : 吉林省长春市
         * toaddress : 楚峰国际
         * money : 0.00
         * status : 6
         * time : 1507793706
         * totalmoney : 21978
         */

        public int id;
        public String no;
        public int uid;
        public String goodsname;
        public String company;
        public String address;
        public String phone;
        public String price;
        public int number;
        public String tocompany;
        public String toname;
        public String tophone;
        public String toplace;
        public String toaddress;
        public String money;
        public int status;
        public int time;
        public float totalmoney;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getTocompany() {
            return tocompany;
        }

        public void setTocompany(String tocompany) {
            this.tocompany = tocompany;
        }

        public String getToname() {
            return toname;
        }

        public void setToname(String toname) {
            this.toname = toname;
        }

        public String getTophone() {
            return tophone;
        }

        public void setTophone(String tophone) {
            this.tophone = tophone;
        }

        public String getToplace() {
            return toplace;
        }

        public void setToplace(String toplace) {
            this.toplace = toplace;
        }

        public String getToaddress() {
            return toaddress;
        }

        public void setToaddress(String toaddress) {
            this.toaddress = toaddress;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public float getTotalmoney() {
            return totalmoney;
        }

        public void setTotalmoney(float totalmoney) {
            this.totalmoney = totalmoney;
        }
    }
}
