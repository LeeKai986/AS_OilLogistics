package com.zpf.oillogistics.bean.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/10/9.
 */

public class MyProductResponse {


    /**
     * status : 0
     * msg : 数据请求成功
     * data : {"total":2,"page":1,"page_size":10,"all_page":1,"data":[{"id":16,"uid":0,"companyname":"0","money":"0","class":1,"classify":"","model":"","title":"","price":"","province":"","city":"","area":"","address":"","img":"","intruduce":null,"quantity":"0","status":1,"time":1506753676,"is_order":0,"followers":"","statuss":1,"shelf":1},{"id":17,"uid":0,"companyname":"0","money":"0","class":2,"classify":"","model":"","title":"的说法都是","price":"444","province":"23","city":"385","area":"4209","address":"鼎折覆餗444","img":"/uploads/20170930/03e48caa7df5e1490e80b1550cbbd878.png","intruduce":"沙发是的范德萨","quantity":"0","status":1,"time":1506754148,"is_order":0,"followers":"","statuss":1,"shelf":1}]}
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
         * data : [{"id":16,"uid":0,"companyname":"0","money":"0","class":1,"classify":"","model":"","title":"","price":"","province":"","city":"","area":"","address":"","img":"","intruduce":null,"quantity":"0","status":1,"time":1506753676,"is_order":0,"followers":"","statuss":1,"shelf":1},{"id":17,"uid":0,"companyname":"0","money":"0","class":2,"classify":"","model":"","title":"的说法都是","price":"444","province":"23","city":"385","area":"4209","address":"鼎折覆餗444","img":"/uploads/20170930/03e48caa7df5e1490e80b1550cbbd878.png","intruduce":"沙发是的范德萨","quantity":"0","status":1,"time":1506754148,"is_order":0,"followers":"","statuss":1,"shelf":1}]
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
             * id : 16
             * uid : 0
             * companyname : 0
             * money : 0
             * class : 1
             * classify :
             * model :
             * title :
             * price :
             * province :
             * city :
             * area :
             * address :
             * img :
             * intruduce : null
             * quantity : 0
             * status : 1
             * time : 1506753676
             * is_order : 0
             * is_user: 2,
             * followers :
             * statuss : 1
             * shelf : 1
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
            private int is_user;
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

            public int getIs_user() {
                return is_user;
            }

            public void setIs_user(int is_user) {
                this.is_user = is_user;
            }
        }
    }
}
