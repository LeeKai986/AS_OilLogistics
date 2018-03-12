package com.zpf.oillogistics.bean.response;

import com.google.gson.annotations.SerializedName;
import com.zpf.oillogistics.bean.Newa;

import java.util.List;

/**
 * Created by Administrator on 2017/10/17.
 */

public class InformaListResponse {


    /**
     * status : 0
     * msg : 获取成功
     * data : {"news":{"total":3,"page":1,"page_size":10,"all_page":1,"data":[{"id":8,"region":"华南","class":1,"title":"阿德萨","author":"超","img":"/uploads/20170921/7c8e83b226471c753da3af6d19678818.jpg","content":"1231231","status":1,"time":1507233266},{"id":9,"region":"华南","class":2,"title":"阿斯顿","author":"实打实","img":"/uploads/20170928/33daca2b54954a80dd838d50ae53c786.jpg","content":"阿斯顿","status":1,"time":1506507156},{"id":7,"region":"华南","class":1,"title":"1231","author":"1231","img":"/uploads/20170920/b0313f65137ac07a6aec2d31887f6266.jpg","content":"12","status":1,"time":1497233266}]}}
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
         * news : {"total":3,"page":1,"page_size":10,"all_page":1,"data":[{"id":8,"region":"华南","class":1,"title":"阿德萨","author":"超","img":"/uploads/20170921/7c8e83b226471c753da3af6d19678818.jpg","content":"1231231","status":1,"time":1507233266},{"id":9,"region":"华南","class":2,"title":"阿斯顿","author":"实打实","img":"/uploads/20170928/33daca2b54954a80dd838d50ae53c786.jpg","content":"阿斯顿","status":1,"time":1506507156},{"id":7,"region":"华南","class":1,"title":"1231","author":"1231","img":"/uploads/20170920/b0313f65137ac07a6aec2d31887f6266.jpg","content":"12","status":1,"time":1497233266}]}
         */

        private NewsBean news;

        public NewsBean getNews() {
            return news;
        }

        public void setNews(NewsBean news) {
            this.news = news;
        }

        public static class NewsBean {
            /**
             * total : 3
             * page : 1
             * page_size : 10
             * all_page : 1
             * data : [{"id":8,"region":"华南","class":1,"title":"阿德萨","author":"超","img":"/uploads/20170921/7c8e83b226471c753da3af6d19678818.jpg","content":"1231231","status":1,"time":1507233266},{"id":9,"region":"华南","class":2,"title":"阿斯顿","author":"实打实","img":"/uploads/20170928/33daca2b54954a80dd838d50ae53c786.jpg","content":"阿斯顿","status":1,"time":1506507156},{"id":7,"region":"华南","class":1,"title":"1231","author":"1231","img":"/uploads/20170920/b0313f65137ac07a6aec2d31887f6266.jpg","content":"12","status":1,"time":1497233266}]
             */

            private int total;
            private int page;
            private int page_size;
            private int all_page;
            private List<Newa> data;

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

            public List<Newa> getData() {
                return data;
            }

            public void setData(List<Newa> data) {
                this.data = data;
            }

        }
    }
}
