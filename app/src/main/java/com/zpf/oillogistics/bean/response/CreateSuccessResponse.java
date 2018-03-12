package com.zpf.oillogistics.bean.response;

/**
 * Created by Administrator on 2017/10/13.
 */

public class CreateSuccessResponse {


    /**
     * status : 0
     * msg : 订单生成成功
     * data : {"no":"20171005597923","uid":87,"goodsname":"发布产品测试数据","company":"测试","address":"四川省成都市武侯区","phone":"18888888888","price":"999","number":"45","tocompany":"测试","toname":"","tophone":"","toplace":"","toaddress":"","status":0,"time":1507889469,"id":"36"}
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
         * no : 20171005597923
         * uid : 87
         * goodsname : 发布产品测试数据
         * company : 测试
         * address : 四川省成都市武侯区
         * phone : 18888888888
         * price : 999
         * number : 45
         * tocompany : 测试
         * toname :
         * tophone :
         * toplace :
         * toaddress :
         * status : 0
         * time : 1507889469
         * id : 36
         */

        private String no;
        private int uid;
        private String goodsname;
        private String company;
        private String address;
        private String phone;
        private String price;
        private String number;
        private String tocompany;
        private String toname;
        private String tophone;
        private String toplace;
        private String toaddress;
        private int status;
        private int time;
        private String id;
        private String money;

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
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

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
