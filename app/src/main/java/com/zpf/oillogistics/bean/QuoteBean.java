package com.zpf.oillogistics.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/9/22.
 * <p>
 * 报价列表
 */

public class QuoteBean {

    /**
     * status : 0
     * msg : 获取成功
     * data : {"total":3,"page":"1","page_size":10,"all_page":1,"data":[{"id":4,"uid":87,"class":1,"companyname":"低洼倒萨啊但是","title":" 请问请问","author":"123","img":"/uploads/20170925/76bbb9d940763ad8fe79550592dae5bf.jpg","content":"123","status":2,"time":1567876534,"region":"西南","classify":"柴油10"},{"id":5,"uid":87,"class":1,"companyname":"阿迪王撒撒旦","title":"阿斯顿","author":"阿斯顿","img":"/uploads/20170925/b2ff3fe20f4fb1c68e3dcda759793c1a.jpg","content":"的阿三","status":1,"time":1567876534,"region":"东南","classify":"煤油"},{"id":6,"uid":71,"class":1,"companyname":"打算","title":"阿斯顿","author":"阿迪斯","img":"/uploads/20170925/f3b75700f0761391d7f0b9e5facec78c.png","content":"阿斯顿阿斯顿","status":1,"time":1567876534,"region":"东北","classify":"柴油10#"}]}
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
         * total : 3
         * page : 1
         * page_size : 10
         * all_page : 1
         * data : [{"id":4,"uid":87,"class":1,"companyname":"低洼倒萨啊但是","title":" 请问请问","author":"123","img":"/uploads/20170925/76bbb9d940763ad8fe79550592dae5bf.jpg","content":"123","status":2,"time":1567876534,"region":"西南","classify":"柴油10"},{"id":5,"uid":87,"class":1,"companyname":"阿迪王撒撒旦","title":"阿斯顿","author":"阿斯顿","img":"/uploads/20170925/b2ff3fe20f4fb1c68e3dcda759793c1a.jpg","content":"的阿三","status":1,"time":1567876534,"region":"东南","classify":"煤油"},{"id":6,"uid":71,"class":1,"companyname":"打算","title":"阿斯顿","author":"阿迪斯","img":"/uploads/20170925/f3b75700f0761391d7f0b9e5facec78c.png","content":"阿斯顿阿斯顿","status":1,"time":1567876534,"region":"东北","classify":"柴油10#"}]
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
             * id : 4
             * uid : 87
             * class : 1
             * companyname : 低洼倒萨啊但是
             * title :  请问请问
             * author : 123
             * img : /uploads/20170925/76bbb9d940763ad8fe79550592dae5bf.jpg
             * content : 123
             * status : 2
             * time : 1567876534
             * region : 西南
             * classify : 柴油10
             */

            private int id;
            private int uid;
            @SerializedName("class")
            private int classX;
            private String companyname;
            private String title;
            private String author;
            private String img;
            private String content;
            private int status;
            private String time;
            private String region;
            private String classify;

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

            public int getClassX() {
                return classX;
            }

            public void setClassX(int classX) {
                this.classX = classX;
            }

            public String getCompanyname() {
                return companyname;
            }

            public void setCompanyname(String companyname) {
                this.companyname = companyname;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getAuthor() {
                return author;
            }

            public void setAuthor(String author) {
                this.author = author;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getRegion() {
                return region;
            }

            public void setRegion(String region) {
                this.region = region;
            }

            public String getClassify() {
                return classify;
            }

            public void setClassify(String classify) {
                this.classify = classify;
            }
        }
    }
}
