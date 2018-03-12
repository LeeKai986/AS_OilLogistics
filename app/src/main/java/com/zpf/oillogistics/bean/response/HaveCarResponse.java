package com.zpf.oillogistics.bean.response;

import java.util.List;

/**
 * Created by Administrator on 2017/9/21.
 */

public class HaveCarResponse {


    /**
     * status : 0
     * msg : 获取成功
     * data : {"total":2,"page":"1","page_size":10,"all_page":1,"data":[{"id":22,"uid":62,"relname":"陈先生","startplace":"吉林省白城市洮北区","startaddress":"成绩合格","endplace":"四川省资阳市雁江区","endaddress":"刚刚好","goodsname":"国家计划","company":"","number":1000,"price":"2500.00","method":1,"time":1508417774,"name":"国际化","phone":"13512345678","comment":"请填写备注放假就好","newstime":"1508417783","status":1,"identity":1,"statuss":2,"herder":"/uploads/20171018/150831852959e71d4118dc7.jpeg"},{"id":21,"uid":87,"relname":"","startplace":"北京北京东城区","startaddress":"大哥","endplace":"天津天津和平区","endaddress":"发广告","goodsname":"好好看看哭","company":"测试","number":5000,"price":"1800.00","method":1,"time":1508342400,"name":"喝咖啡","phone":"13512345678","comment":"好好好","newstime":"1508417680","status":1,"identity":2,"statuss":2,"herder":"/uploads/20171019/150837680159e800e1454c9.jpg"}]}
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
         * data : [{"id":22,"uid":62,"relname":"陈先生","startplace":"吉林省白城市洮北区","startaddress":"成绩合格","endplace":"四川省资阳市雁江区","endaddress":"刚刚好","goodsname":"国家计划","company":"","number":1000,"price":"2500.00","method":1,"time":1508417774,"name":"国际化","phone":"13512345678","comment":"请填写备注放假就好","newstime":"1508417783","status":1,"identity":1,"statuss":2,"herder":"/uploads/20171018/150831852959e71d4118dc7.jpeg"},{"id":21,"uid":87,"relname":"","startplace":"北京北京东城区","startaddress":"大哥","endplace":"天津天津和平区","endaddress":"发广告","goodsname":"好好看看哭","company":"测试","number":5000,"price":"1800.00","method":1,"time":1508342400,"name":"喝咖啡","phone":"13512345678","comment":"好好好","newstime":"1508417680","status":1,"identity":2,"statuss":2,"herder":"/uploads/20171019/150837680159e800e1454c9.jpg"}]
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
             * id : 22
             * uid : 62
             * relname : 陈先生
             * startplace : 吉林省白城市洮北区
             * startaddress : 成绩合格
             * endplace : 四川省资阳市雁江区
             * endaddress : 刚刚好
             * goodsname : 国家计划
             * company :
             * number : 1000
             * price : 2500.00
             * method : 1
             * time : 1508417774
             * name : 国际化
             * phone : 13512345678
             * comment : 请填写备注放假就好
             * newstime : 1508417783
             * status : 1
             * identity : 1
             * statuss : 2
             * herder : /uploads/20171018/150831852959e71d4118dc7.jpeg
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
            private String newstime;
            private int status;
            private int identity;
            private int statuss;
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

            public int getStatuss() {
                return statuss;
            }

            public void setStatuss(int statuss) {
                this.statuss = statuss;
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
