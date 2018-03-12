package com.zpf.oillogistics.bean.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/9/20.
 */

public class OilSearchResponse {


    /**
     * status : 0
     * msg : 搜索成功
     * data : {"total":1,"page":1,"page_size":10,"all_page":1,"data":[{"id":10,"uid":62,"companyname":"请问恶气请问","money":"1000","class":1,"classify":"柴油","model":"0#","title":"阿斯顿","price":"9.5","province":"湖北省","city":"潜江市","area":"","address":"大大上档次","img":"/uploads/20170926/68790f7e8ca719b3fffefbb3a176a38b.jpg","intruduce":"","quantity":"100-500","status":2,"time":1505814669,"is_order":1,"followers":"","statuss":1,"shelf":2}]}
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
         * total : 1
         * page : 1
         * page_size : 10
         * all_page : 1
         * data : [{"id":10,"uid":62,"companyname":"请问恶气请问","money":"1000","class":1,"classify":"柴油","model":"0#","title":"阿斯顿","price":"9.5","province":"湖北省","city":"潜江市","area":"","address":"大大上档次","img":"/uploads/20170926/68790f7e8ca719b3fffefbb3a176a38b.jpg","intruduce":"","quantity":"100-500","status":2,"time":1505814669,"is_order":1,"followers":"","statuss":1,"shelf":2}]
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
             * id : 10
             * uid : 62
             * companyname : 请问恶气请问
             * money : 1000
             * class : 1
             * classify : 柴油
             * model : 0#
             * title : 阿斯顿
             * price : 9.5
             * province : 湖北省
             * city : 潜江市
             * area :
             * address : 大大上档次
             * img : /uploads/20170926/68790f7e8ca719b3fffefbb3a176a38b.jpg
             * intruduce :
             * quantity : 100-500
             * status : 2
             * time : 1505814669
             * is_order : 1
             * followers :
             * statuss : 1
             * shelf : 2
             */

            private int id;
            private int uid;
            private String companyname;
            private String money;
            @SerializedName("class")
            private int classX;
            private String classify;
            private String model;
            private String title;
            private String price;
            private String province;
            private String city;
            private String area;
            private String address;
            private String img;
            private String intruduce;
            private String quantity;
            private int status;
            private int time;
            private int is_order;
            private String followers;
            private int statuss;
            private int shelf;

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

            public String getCompanyname() {
                return companyname;
            }

            public void setCompanyname(String companyname) {
                this.companyname = companyname;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public int getClassX() {
                return classX;
            }

            public void setClassX(int classX) {
                this.classX = classX;
            }

            public String getClassify() {
                return classify;
            }

            public void setClassify(String classify) {
                this.classify = classify;
            }

            public String getModel() {
                return model;
            }

            public void setModel(String model) {
                this.model = model;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getIntruduce() {
                return intruduce;
            }

            public void setIntruduce(String intruduce) {
                this.intruduce = intruduce;
            }

            public String getQuantity() {
                return quantity;
            }

            public void setQuantity(String quantity) {
                this.quantity = quantity;
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

            public int getIs_order() {
                return is_order;
            }

            public void setIs_order(int is_order) {
                this.is_order = is_order;
            }

            public String getFollowers() {
                return followers;
            }

            public void setFollowers(String followers) {
                this.followers = followers;
            }

            public int getStatuss() {
                return statuss;
            }

            public void setStatuss(int statuss) {
                this.statuss = statuss;
            }

            public int getShelf() {
                return shelf;
            }

            public void setShelf(int shelf) {
                this.shelf = shelf;
            }
        }
    }
}
