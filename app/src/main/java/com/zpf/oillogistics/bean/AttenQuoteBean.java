package com.zpf.oillogistics.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/9/27.
 * <p>
 * 关注的报价信息
 */

public class AttenQuoteBean {

    /**
     * status : 0
     * msg : 数据请求成功
     * data : [{"id":19,"uid":200,"admin":2,"class":2,"companyname":"山东石油集团","title":"四川成都成都分公司石油、柴油行业资讯","author":"admin","content":"<p><img src=\"/uploads/ueditor/20171109/1510216501542899.jpg\" title=\"1510216501542899.jpg\" alt=\"2441962023_551839156.310x310.jpg\"/><\/p><p><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">中石油成都分公司销售报价，石油<\/span>90# &nbsp;1000<span style=\"font-family:宋体\">元<\/span><span style=\"font-family:Calibri\">/<\/span><span style=\"font-family:宋体\">吨，<\/span><span style=\"font-family:Calibri\">80# &nbsp;1200<\/span><span style=\"font-family:宋体\">元<\/span><span style=\"font-family:Calibri\">/<\/span><span style=\"font-family:宋体\">吨，柴油<\/span><span style=\"font-family:Calibri\">90# &nbsp;1000<\/span><span style=\"font-family:宋体\">元<\/span><span style=\"font-family:Calibri\">/<\/span><span style=\"font-family:宋体\">吨，<\/span><span style=\"font-family:Calibri\">80# &nbsp;1200<\/span><span style=\"font-family:宋体\">元<\/span><span style=\"font-family:Calibri\">/<\/span><span style=\"font-family:宋体\">吨，汽油<\/span><span style=\"font-family:Calibri\">90# &nbsp;1000<\/span><span style=\"font-family:宋体\">元<\/span><span style=\"font-family:Calibri\">/<\/span><span style=\"font-family:宋体\">吨，<\/span><span style=\"font-family:Calibri\">80# &nbsp;900<\/span><span style=\"font-family:宋体\">元<\/span><span style=\"font-family:Calibri\">/<\/span><span style=\"font-family:宋体\">吨，煤油<\/span><span style=\"font-family:Calibri\">90# &nbsp;1000<\/span><span style=\"font-family:宋体\">元<\/span><span style=\"font-family:Calibri\">/<\/span><span style=\"font-family:宋体\">吨，<\/span><span style=\"font-family:Calibri\">80# &nbsp;1200<\/span><span style=\"font-family:宋体\">元<\/span><span style=\"font-family:Calibri\">/<\/span><span style=\"font-family:宋体\">吨，<\/span><\/span><\/p><p><br/><\/p>","region":"华南","classify":"煤油","model":"90#","time":1510216507,"status":1,"excel":"/uploads/20171109/c150a12043b8b1928e3220785554b62b.xls"},{"id":23,"uid":200,"admin":2,"class":2,"companyname":"山东石油集团","title":"最新煤汽油","author":"山东石油集团","content":"<p style=\"text-align:center\"><img src=\"/uploads/ueditor/20171114/1510654208528535.jpg\" title=\"1510654208528535.jpg\" alt=\"4150277964_181539607.310x310.jpg\"/><\/p><p>最新煤汽油<\/p>","region":"北京","classify":"汽油","model":"89c","time":1510735727,"status":1,"excel":"/uploads/20171114/1264dcd90f44c600ec028f998c16889c.xls"},{"id":33,"uid":200,"admin":2,"class":2,"companyname":"山东石油集团","title":"山东济南最新汽油、石油、煤油","author":"山东石油集团","content":"<p><img src=\"/uploads/ueditor/20171114/1510659532528603.jpg\" title=\"1510659532528603.jpg\" alt=\"4150277964_181539607.310x310.jpg\"/><\/p><p>打开了根茎类第三方赶紧快山东理工<\/p>","region":"山东济南","classify":"煤油","model":"90c","time":1510659540,"status":1,"excel":"/uploads/20171114/450d11960ffa0103ff021c3188013029.xls"},{"id":35,"uid":200,"admin":2,"class":2,"companyname":"山东石油集团","title":"最新国四汽油","author":"山东石油集团","content":"<p><img src=\"/uploads/ueditor/20171116/1510816759813846.jpg\" title=\"1510816759813846.jpg\" alt=\"3749475020_998729174.310x310.jpg\"/><\/p><p><br/><\/p><p><br/><\/p><p><br/><\/p><p><br/><\/p><p><br/><\/p><p><br/><\/p><p><br/><\/p><p><br/><\/p><p><br/><\/p><p><br/><\/p><p><br/><\/p><p>最新国四汽油<\/p>","region":"四川成都","classify":"汽油","model":"89c","time":1510816792,"status":1,"excel":"/uploads/20171116/bed8072b5159a02fe399d7a12bcc3f16.xls"}]
     * time : 1000
     */

    private int status;
    private String msg;
    private int time;
    private List<DataBean> data;

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

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 19
         * uid : 200
         * admin : 2
         * class : 2
         * companyname : 山东石油集团
         * title : 四川成都成都分公司石油、柴油行业资讯
         * author : admin
         * content : <p><img src="/uploads/ueditor/20171109/1510216501542899.jpg" title="1510216501542899.jpg" alt="2441962023_551839156.310x310.jpg"/></p><p><span style=";font-family:宋体;font-size:14px"><span style="font-family:宋体">中石油成都分公司销售报价，石油</span>90# &nbsp;1000<span style="font-family:宋体">元</span><span style="font-family:Calibri">/</span><span style="font-family:宋体">吨，</span><span style="font-family:Calibri">80# &nbsp;1200</span><span style="font-family:宋体">元</span><span style="font-family:Calibri">/</span><span style="font-family:宋体">吨，柴油</span><span style="font-family:Calibri">90# &nbsp;1000</span><span style="font-family:宋体">元</span><span style="font-family:Calibri">/</span><span style="font-family:宋体">吨，</span><span style="font-family:Calibri">80# &nbsp;1200</span><span style="font-family:宋体">元</span><span style="font-family:Calibri">/</span><span style="font-family:宋体">吨，汽油</span><span style="font-family:Calibri">90# &nbsp;1000</span><span style="font-family:宋体">元</span><span style="font-family:Calibri">/</span><span style="font-family:宋体">吨，</span><span style="font-family:Calibri">80# &nbsp;900</span><span style="font-family:宋体">元</span><span style="font-family:Calibri">/</span><span style="font-family:宋体">吨，煤油</span><span style="font-family:Calibri">90# &nbsp;1000</span><span style="font-family:宋体">元</span><span style="font-family:Calibri">/</span><span style="font-family:宋体">吨，</span><span style="font-family:Calibri">80# &nbsp;1200</span><span style="font-family:宋体">元</span><span style="font-family:Calibri">/</span><span style="font-family:宋体">吨，</span></span></p><p><br/></p>
         * region : 华南
         * classify : 煤油
         * model : 90#
         * time : 1510216507
         * status : 1
         * excel : /uploads/20171109/c150a12043b8b1928e3220785554b62b.xls
         */

        private String id;
        private String uid;
        private String admin;
        @SerializedName("class")
        private String classX;
        private String companyname;
        private String title;
        private String author;
        private String content;
        private String region;
        private String classify;
        private String model;
        private String time;
        private String status;
        private String excel;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getAdmin() {
            return admin;
        }

        public void setAdmin(String admin) {
            this.admin = admin;
        }

        public String getClassX() {
            return classX;
        }

        public void setClassX(String classX) {
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

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getExcel() {
            return excel;
        }

        public void setExcel(String excel) {
            this.excel = excel;
        }
    }
}
