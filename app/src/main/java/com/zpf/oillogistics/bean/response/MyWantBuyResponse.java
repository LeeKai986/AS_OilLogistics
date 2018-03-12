package com.zpf.oillogistics.bean.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/10/25.
 */

public class MyWantBuyResponse {


    /**
     * status : 0
     * msg : 数据请求成功
     * data : {"total":4,"page":1,"page_size":10,"all_page":1,"data":[{"uid":87,"id":37,"title":"took","number":"58","name":"测试","address":"四川省资阳市雁江区刚好哈哈","phone":"156855569856","img":"/uploads/20171020/150846914159e9699505e89.jpg","intruduce":"缩头","time":1508469141,"is_user":1,"class":1,"price":"","status":1,"statuss":2,"longitude":null,"latitude":null},{"uid":87,"id":40,"title":"测试企业发求购","number":"500","name":"测试","address":"四川省资阳市雁江区刚好哈哈","phone":"188888888888","img":"/uploads/20171023/150872664159ed5771b7394.jpg","intruduce":"见过几个粗跟","time":1508726641,"is_user":1,"class":1,"price":"","status":1,"statuss":2,"longitude":null,"latitude":null},{"uid":87,"id":42,"title":"吃饭","number":"85","name":"测试","address":"四川省资阳市雁江区刚好哈哈","phone":"15855445455","img":null,"intruduce":"才吃饭自行车","time":1508749325,"is_user":1,"class":2,"price":"","status":1,"statuss":2,"longitude":null,"latitude":null},{"uid":87,"id":46,"title":"v回家更好","number":"59","name":"测试","address":"黑龙江哈尔滨刚好哈哈","phone":"18888888888","img":"/uploads/20171025/150892502959f05e6580558.jpg","intruduce":"查酒驾","time":1508925029,"is_user":2,"class":2,"price":"","status":1,"statuss":2,"longitude":null,"latitude":null}]}
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
         * total : 4
         * page : 1
         * page_size : 10
         * all_page : 1
         * data : [{"uid":87,"id":37,"title":"took","number":"58","name":"测试","address":"四川省资阳市雁江区刚好哈哈","phone":"156855569856","img":"/uploads/20171020/150846914159e9699505e89.jpg","intruduce":"缩头","time":1508469141,"is_user":1,"class":1,"price":"","status":1,"statuss":2,"longitude":null,"latitude":null},{"uid":87,"id":40,"title":"测试企业发求购","number":"500","name":"测试","address":"四川省资阳市雁江区刚好哈哈","phone":"188888888888","img":"/uploads/20171023/150872664159ed5771b7394.jpg","intruduce":"见过几个粗跟","time":1508726641,"is_user":1,"class":1,"price":"","status":1,"statuss":2,"longitude":null,"latitude":null},{"uid":87,"id":42,"title":"吃饭","number":"85","name":"测试","address":"四川省资阳市雁江区刚好哈哈","phone":"15855445455","img":null,"intruduce":"才吃饭自行车","time":1508749325,"is_user":1,"class":2,"price":"","status":1,"statuss":2,"longitude":null,"latitude":null},{"uid":87,"id":46,"title":"v回家更好","number":"59","name":"测试","address":"黑龙江哈尔滨刚好哈哈","phone":"18888888888","img":"/uploads/20171025/150892502959f05e6580558.jpg","intruduce":"查酒驾","time":1508925029,"is_user":2,"class":2,"price":"","status":1,"statuss":2,"longitude":null,"latitude":null}]
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
             * uid : 87
             * id : 37
             * title : took
             * number : 58
             * name : 测试
             * address : 四川省资阳市雁江区刚好哈哈
             * phone : 156855569856
             * img : /uploads/20171020/150846914159e9699505e89.jpg
             * intruduce : 缩头
             * time : 1508469141
             * is_user : 1
             * class : 1
             * price :
             * status : 1
             * statuss : 2
             * longitude : null
             * latitude : null
             */

            private int uid;
            private int id;
            private String title;
            private String number;
            private String name;
            private String address;
            private String phone;
            private String img;
            private String intruduce;
            private int time;
            private int is_user;
            @SerializedName("class")
            private int classX;
            private String price;
            private int status;
            private int statuss;
            private Object longitude;
            private Object latitude;

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

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

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
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

            public int getTime() {
                return time;
            }

            public void setTime(int time) {
                this.time = time;
            }

            public int getIs_user() {
                return is_user;
            }

            public void setIs_user(int is_user) {
                this.is_user = is_user;
            }

            public int getClassX() {
                return classX;
            }

            public void setClassX(int classX) {
                this.classX = classX;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getStatuss() {
                return statuss;
            }

            public void setStatuss(int statuss) {
                this.statuss = statuss;
            }

            public Object getLongitude() {
                return longitude;
            }

            public void setLongitude(Object longitude) {
                this.longitude = longitude;
            }

            public Object getLatitude() {
                return latitude;
            }

            public void setLatitude(Object latitude) {
                this.latitude = latitude;
            }
        }
    }
}
