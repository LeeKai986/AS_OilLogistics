package com.zpf.oillogistics.bean.response;

import com.google.gson.annotations.SerializedName;
import com.zpf.oillogistics.bean.Newa;
import com.zpf.oillogistics.bean.Slid;

import java.util.List;

/**
 * Created by Administrator on 2017/9/20.
 */

public class IndexResponse {


    /**
     * status : 0
     * msg : 获取成功
     * data : {"slid":{"total":2,"page":1,"page_size":10,"all_page":1,"data":[{"id":7,"img":"/uploads/20170918/b23a659c70b48be92db0beb56e7f0329.jpg","url":"13","add_time":1506507166},{"id":1,"img":"/uploads/20170918/f461ab17bc191a6caee69f1968a1c418.jpg","url":"12","add_time":1506507156}]},"newa":{"total":3,"page":1,"page_size":10,"all_page":1,"data":[{"id":8,"region":"华南","class":1,"title":"阿德萨","author":"超","img":"/uploads/20170921/7c8e83b226471c753da3af6d19678818.jpg","content":"1231231","status":1,"time":1507233266},{"id":9,"region":"华南","class":2,"title":"阿斯顿","author":"实打实","img":"/uploads/20170928/33daca2b54954a80dd838d50ae53c786.jpg","content":"阿斯顿","status":1,"time":1506507156},{"id":7,"region":"华南","class":1,"title":"1231","author":"1231","img":"/uploads/20170920/b0313f65137ac07a6aec2d31887f6266.jpg","content":"12","status":1,"time":1497233266}]}}
     * time : 1000
     */

    private int status;
    private String msg;
    private DataBeanXX data;
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

    public DataBeanXX getData() {
        return data;
    }

    public void setData(DataBeanXX data) {
        this.data = data;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public static class DataBeanXX {
        /**
         * slid : {"total":2,"page":1,"page_size":10,"all_page":1,"data":[{"id":7,"img":"/uploads/20170918/b23a659c70b48be92db0beb56e7f0329.jpg","url":"13","add_time":1506507166},{"id":1,"img":"/uploads/20170918/f461ab17bc191a6caee69f1968a1c418.jpg","url":"12","add_time":1506507156}]}
         * newa : {"total":3,"page":1,"page_size":10,"all_page":1,"data":[{"id":8,"region":"华南","class":1,"title":"阿德萨","author":"超","img":"/uploads/20170921/7c8e83b226471c753da3af6d19678818.jpg","content":"1231231","status":1,"time":1507233266},{"id":9,"region":"华南","class":2,"title":"阿斯顿","author":"实打实","img":"/uploads/20170928/33daca2b54954a80dd838d50ae53c786.jpg","content":"阿斯顿","status":1,"time":1506507156},{"id":7,"region":"华南","class":1,"title":"1231","author":"1231","img":"/uploads/20170920/b0313f65137ac07a6aec2d31887f6266.jpg","content":"12","status":1,"time":1497233266}]}
         */

        private SlidBean slid;
        private NewaBean newa;

        public SlidBean getSlid() {
            return slid;
        }

        public void setSlid(SlidBean slid) {
            this.slid = slid;
        }

        public NewaBean getNewa() {
            return newa;
        }

        public void setNewa(NewaBean newa) {
            this.newa = newa;
        }

        public static class SlidBean {
            /**
             * total : 2
             * page : 1
             * page_size : 10
             * all_page : 1
             * data : [{"id":7,"img":"/uploads/20170918/b23a659c70b48be92db0beb56e7f0329.jpg","url":"13","add_time":1506507166},{"id":1,"img":"/uploads/20170918/f461ab17bc191a6caee69f1968a1c418.jpg","url":"12","add_time":1506507156}]
             */

            private int total;
            private int page;
            private int page_size;
            private int all_page;
            private List<Slid> data;

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

            public List<Slid> getData() {
                return data;
            }

            public void setData(List<Slid> data) {
                this.data = data;
            }
        }

        public static class NewaBean {
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
