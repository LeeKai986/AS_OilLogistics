package com.zpf.oillogistics.bean.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/9/20.
 */

public class PriceInforResponse {

    /**
     * status : 0
     * msg : 获取成功
     * data : {"total":1,"page":1,"page_size":10,"all_page":1,"data":[{"id":13,"uid":152,"admin":2,"class":2,"companyname":"成都分公司","title":"2017年第六届西北石油化工高峰论坛","author":"admin","content":"<p><img src=\"http://yuanyoutong.zpftech.com/uploads/ueditor/20171107/1510018666416107.jpg\" title=\"1510018666416107.jpg\" alt=\"3732954454_215928410.400x400.jpg\"/><\/p><p><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">中石油成都分公司销售报价，石油<\/span>90# &nbsp;1000<span style=\"font-family:宋体\">元<\/span><span style=\"font-family:Calibri\">/<\/span><span style=\"font-family:宋体\">吨，<\/span><span style=\"font-family:Calibri\">80# &nbsp;1200<\/span><span style=\"font-family:宋体\">元<\/span><span style=\"font-family:Calibri\">/<\/span><span style=\"font-family:宋体\">吨，柴油<\/span><span style=\"font-family:Calibri\">90# &nbsp;1000<\/span><span style=\"font-family:宋体\">元<\/span><span style=\"font-family:Calibri\">/<\/span><span style=\"font-family:宋体\">吨，<\/span><span style=\"font-family:Calibri\">80# &nbsp;1200<\/span><span style=\"font-family:宋体\">元<\/span><span style=\"font-family:Calibri\">/<\/span><span style=\"font-family:宋体\">吨，汽油<\/span><span style=\"font-family:Calibri\">90# &nbsp;1000<\/span><span style=\"font-family:宋体\">元<\/span><span style=\"font-family:Calibri\">/<\/span><span style=\"font-family:宋体\">吨，<\/span><span style=\"font-family:Calibri\">80# &nbsp;1200<\/span><span style=\"font-family:宋体\">元<\/span><span style=\"font-family:Calibri\">/<\/span><span style=\"font-family:宋体\">吨，煤油<\/span><span style=\"font-family:Calibri\">90# &nbsp;1000<\/span><span style=\"font-family:宋体\">元<\/span><span style=\"font-family:Calibri\">/<\/span><span style=\"font-family:宋体\">吨，<\/span><span style=\"font-family:Calibri\">80# &nbsp;1200<\/span><span style=\"font-family:宋体\">元<\/span><span style=\"font-family:Calibri\">/<\/span><span style=\"font-family:宋体\">吨，<\/span><\/span><\/p><p><br/><\/p>","region":"西南","classify":"汽油","time":1510018684,"status":1,"excel":"/uploads/20171107/4c5000ecf0f33e891feeb7b8a287e166.xls"}],"0":{"follow":2}}
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
         * data : [{"id":13,"uid":152,"admin":2,"class":2,"companyname":"成都分公司","title":"2017年第六届西北石油化工高峰论坛","author":"admin","content":"<p><img src=\"http://yuanyoutong.zpftech.com/uploads/ueditor/20171107/1510018666416107.jpg\" title=\"1510018666416107.jpg\" alt=\"3732954454_215928410.400x400.jpg\"/><\/p><p><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">中石油成都分公司销售报价，石油<\/span>90# &nbsp;1000<span style=\"font-family:宋体\">元<\/span><span style=\"font-family:Calibri\">/<\/span><span style=\"font-family:宋体\">吨，<\/span><span style=\"font-family:Calibri\">80# &nbsp;1200<\/span><span style=\"font-family:宋体\">元<\/span><span style=\"font-family:Calibri\">/<\/span><span style=\"font-family:宋体\">吨，柴油<\/span><span style=\"font-family:Calibri\">90# &nbsp;1000<\/span><span style=\"font-family:宋体\">元<\/span><span style=\"font-family:Calibri\">/<\/span><span style=\"font-family:宋体\">吨，<\/span><span style=\"font-family:Calibri\">80# &nbsp;1200<\/span><span style=\"font-family:宋体\">元<\/span><span style=\"font-family:Calibri\">/<\/span><span style=\"font-family:宋体\">吨，汽油<\/span><span style=\"font-family:Calibri\">90# &nbsp;1000<\/span><span style=\"font-family:宋体\">元<\/span><span style=\"font-family:Calibri\">/<\/span><span style=\"font-family:宋体\">吨，<\/span><span style=\"font-family:Calibri\">80# &nbsp;1200<\/span><span style=\"font-family:宋体\">元<\/span><span style=\"font-family:Calibri\">/<\/span><span style=\"font-family:宋体\">吨，煤油<\/span><span style=\"font-family:Calibri\">90# &nbsp;1000<\/span><span style=\"font-family:宋体\">元<\/span><span style=\"font-family:Calibri\">/<\/span><span style=\"font-family:宋体\">吨，<\/span><span style=\"font-family:Calibri\">80# &nbsp;1200<\/span><span style=\"font-family:宋体\">元<\/span><span style=\"font-family:Calibri\">/<\/span><span style=\"font-family:宋体\">吨，<\/span><\/span><\/p><p><br/><\/p>","region":"西南","classify":"汽油","time":1510018684,"status":1,"excel":"/uploads/20171107/4c5000ecf0f33e891feeb7b8a287e166.xls"}]
         * 0 : {"follow":2}
         */

        private int total;
        private int page;
        private int page_size;
        private int all_page;
        @SerializedName("0")
        private _$0Bean _$0;
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

        public _$0Bean get_$0() {
            return _$0;
        }

        public void set_$0(_$0Bean _$0) {
            this._$0 = _$0;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class _$0Bean {
            /**
             * follow : 2
             */

            private int follow;

            public int getFollow() {
                return follow;
            }

            public void setFollow(int follow) {
                this.follow = follow;
            }
        }

        public static class DataBean {
            /**
             * id : 13
             * uid : 152
             * admin : 2
             * class : 2
             * companyname : 成都分公司
             * title : 2017年第六届西北石油化工高峰论坛
             * author : admin
             * content : <p><img src="http://yuanyoutong.zpftech.com/uploads/ueditor/20171107/1510018666416107.jpg" title="1510018666416107.jpg" alt="3732954454_215928410.400x400.jpg"/></p><p><span style=";font-family:宋体;font-size:14px"><span style="font-family:宋体">中石油成都分公司销售报价，石油</span>90# &nbsp;1000<span style="font-family:宋体">元</span><span style="font-family:Calibri">/</span><span style="font-family:宋体">吨，</span><span style="font-family:Calibri">80# &nbsp;1200</span><span style="font-family:宋体">元</span><span style="font-family:Calibri">/</span><span style="font-family:宋体">吨，柴油</span><span style="font-family:Calibri">90# &nbsp;1000</span><span style="font-family:宋体">元</span><span style="font-family:Calibri">/</span><span style="font-family:宋体">吨，</span><span style="font-family:Calibri">80# &nbsp;1200</span><span style="font-family:宋体">元</span><span style="font-family:Calibri">/</span><span style="font-family:宋体">吨，汽油</span><span style="font-family:Calibri">90# &nbsp;1000</span><span style="font-family:宋体">元</span><span style="font-family:Calibri">/</span><span style="font-family:宋体">吨，</span><span style="font-family:Calibri">80# &nbsp;1200</span><span style="font-family:宋体">元</span><span style="font-family:Calibri">/</span><span style="font-family:宋体">吨，煤油</span><span style="font-family:Calibri">90# &nbsp;1000</span><span style="font-family:宋体">元</span><span style="font-family:Calibri">/</span><span style="font-family:宋体">吨，</span><span style="font-family:Calibri">80# &nbsp;1200</span><span style="font-family:宋体">元</span><span style="font-family:Calibri">/</span><span style="font-family:宋体">吨，</span></span></p><p><br/></p>
             * region : 西南
             * classify : 汽油
             * time : 1510018684
             * status : 1
             * excel : /uploads/20171107/4c5000ecf0f33e891feeb7b8a287e166.xls
             */

            private int id;
            private int uid;
            private int admin;
            @SerializedName("class")
            private int classX;
            private String companyname;
            private String title;
            private String author;
            private String content;
            private String region;
            private String classify;
            private int time;
            private int status;
            private String excel;
            private String follow;

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

            public int getAdmin() {
                return admin;
            }

            public void setAdmin(int admin) {
                this.admin = admin;
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

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
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

            public int getTime() {
                return time;
            }

            public void setTime(int time) {
                this.time = time;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getExcel() {
                return excel;
            }

            public void setExcel(String excel) {
                this.excel = excel;
            }

            public String getFollow() {
                return follow;
            }

            public void setFollow(String follow) {
                this.follow = follow;
            }
        }
    }
}
