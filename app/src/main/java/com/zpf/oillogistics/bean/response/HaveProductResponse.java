package com.zpf.oillogistics.bean.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/9/20.
 */

public class HaveProductResponse {

    /**
     * status : 0
     * msg : 获取成功
     * data : {"total":4,"page":"1","page_size":10,"all_page":1,"data":[{"id":38,"name":"王师傅","phone":"18012345678","startplace":"河北省石家庄市辖区","endplace":"山西省太原市辖区","time":"1511020800","class":2,"load":200,"cartcode":"川A12345","newstime":"1510624185","status":1,"dispatch":"2","herder":"/uploads/20171109/adb18304ff3d94bb326b18ccf662b26b.jpg"},{"id":36,"name":"刘师傅","phone":"18112345678","startplace":"北京-北京-东城区","endplace":"天津-天津-河东区","time":"1511107200","class":1,"load":180,"cartcode":"京G12345","newstime":"1510564690","status":1,"dispatch":"2","herder":"/uploads/20171109/92fc53cc094af82ed6890b1efaac74dc.jpg"},{"id":34,"name":"王师傅","phone":"18012345678","startplace":"河北省-石家庄-桥东区","endplace":"吉林省-长春-南关区","time":"1510848000","class":1,"load":500,"cartcode":"川A12345","newstime":"1510564614","status":1,"dispatch":"2","herder":"/uploads/20171109/adb18304ff3d94bb326b18ccf662b26b.jpg"},{"id":33,"name":"王师傅","phone":"18012345678","startplace":"北京-北京-东城区","endplace":"天津-天津-和平区","time":"1510675200","class":9,"load":200,"cartcode":"川A12345","newstime":"1510564590","status":1,"dispatch":"2","herder":"/uploads/20171109/adb18304ff3d94bb326b18ccf662b26b.jpg"}]}
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
         * data : [{"id":38,"name":"王师傅","phone":"18012345678","startplace":"河北省石家庄市辖区","endplace":"山西省太原市辖区","time":"1511020800","class":2,"load":200,"cartcode":"川A12345","newstime":"1510624185","status":1,"dispatch":"2","herder":"/uploads/20171109/adb18304ff3d94bb326b18ccf662b26b.jpg"},{"id":36,"name":"刘师傅","phone":"18112345678","startplace":"北京-北京-东城区","endplace":"天津-天津-河东区","time":"1511107200","class":1,"load":180,"cartcode":"京G12345","newstime":"1510564690","status":1,"dispatch":"2","herder":"/uploads/20171109/92fc53cc094af82ed6890b1efaac74dc.jpg"},{"id":34,"name":"王师傅","phone":"18012345678","startplace":"河北省-石家庄-桥东区","endplace":"吉林省-长春-南关区","time":"1510848000","class":1,"load":500,"cartcode":"川A12345","newstime":"1510564614","status":1,"dispatch":"2","herder":"/uploads/20171109/adb18304ff3d94bb326b18ccf662b26b.jpg"},{"id":33,"name":"王师傅","phone":"18012345678","startplace":"北京-北京-东城区","endplace":"天津-天津-和平区","time":"1510675200","class":9,"load":200,"cartcode":"川A12345","newstime":"1510564590","status":1,"dispatch":"2","herder":"/uploads/20171109/adb18304ff3d94bb326b18ccf662b26b.jpg"}]
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
             * id : 38
             * name : 王师傅
             * phone : 18012345678
             * startplace : 河北省石家庄市辖区
             * endplace : 山西省太原市辖区
             * time : 1511020800
             * class : 2
             * load : 200
             * cartcode : 川A12345
             * newstime : 1510624185
             * status : 1
             * dispatch : 2
             * herder : /uploads/20171109/adb18304ff3d94bb326b18ccf662b26b.jpg
             */

            private String id;
            private String name;
            private String phone;
            private String startplace;
            private String endplace;
            private String time;
            @SerializedName("class")
            private String classX;
            private String load;
            private String cartcode;
            private String newstime;
            private String status;
            private String dispatch;
            private String herder;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
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

            public String getStartplace() {
                return startplace;
            }

            public void setStartplace(String startplace) {
                this.startplace = startplace;
            }

            public String getEndplace() {
                return endplace;
            }

            public void setEndplace(String endplace) {
                this.endplace = endplace;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getClassX() {
                return classX;
            }

            public void setClassX(String classX) {
                this.classX = classX;
            }

            public String getLoad() {
                return load;
            }

            public void setLoad(String load) {
                this.load = load;
            }

            public String getCartcode() {
                return cartcode;
            }

            public void setCartcode(String cartcode) {
                this.cartcode = cartcode;
            }

            public String getNewstime() {
                return newstime;
            }

            public void setNewstime(String newstime) {
                this.newstime = newstime;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getDispatch() {
                return dispatch;
            }

            public void setDispatch(String dispatch) {
                this.dispatch = dispatch;
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
