package com.zpf.oillogistics.bean.response;

import java.util.List;

/**
 * Created by Administrator on 2017/11/8.
 */

public class PriceInforTweResponse {

    /**
     * status : 0
     * msg : 获取成功
     * data : [{"title":"华南国四 柴油、汽油、煤油","time":1510017795,"author":"admin","content":"<p><img src=\"http://yuanyoutong.zpftech.com/uploads/ueditor/20171107/1510017786623924.jpg\" title=\"1510017786623924.jpg\" alt=\"3749475020_998729174.310x310.jpg\"/><\/p><p><span style=\";font-family:宋体;font-size:14px\"><span style=\"font-family:宋体\">中石油成都分公司销售报价，石油<\/span>90# &nbsp;1000<span style=\"font-family:宋体\">元<\/span><span style=\"font-family:Calibri\">/<\/span><span style=\"font-family:宋体\">吨，<\/span><span style=\"font-family:Calibri\">80# &nbsp;1200<\/span><span style=\"font-family:宋体\">元<\/span><span style=\"font-family:Calibri\">/<\/span><span style=\"font-family:宋体\">吨，柴油<\/span><span style=\"font-family:Calibri\">90# &nbsp;1000<\/span><span style=\"font-family:宋体\">元<\/span><span style=\"font-family:Calibri\">/<\/span><span style=\"font-family:宋体\">吨，<\/span><span style=\"font-family:Calibri\">80# &nbsp;1200<\/span><span style=\"font-family:宋体\">元<\/span><span style=\"font-family:Calibri\">/<\/span><span style=\"font-family:宋体\">吨，汽油<\/span><span style=\"font-family:Calibri\">90# &nbsp;1000<\/span><span style=\"font-family:宋体\">元<\/span><span style=\"font-family:Calibri\">/<\/span><span style=\"font-family:宋体\">吨，<\/span><span style=\"font-family:Calibri\">80# &nbsp;1200<\/span><span style=\"font-family:宋体\">元<\/span><span style=\"font-family:Calibri\">/<\/span><span style=\"font-family:宋体\">吨，煤油<\/span><span style=\"font-family:Calibri\">90# &nbsp;1000<\/span><span style=\"font-family:宋体\">元<\/span><span style=\"font-family:Calibri\">/<\/span><span style=\"font-family:宋体\">吨，<\/span><span style=\"font-family:Calibri\">80# &nbsp;1200<\/span><span style=\"font-family:宋体\">元<\/span><span style=\"font-family:Calibri\">/<\/span><span style=\"font-family:宋体\">吨，<\/span><\/span><\/p>"}]
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
         * title : 华南国四 柴油、汽油、煤油
         * time : 1510017795
         * author : admin
         * content : <p><img src="http://yuanyoutong.zpftech.com/uploads/ueditor/20171107/1510017786623924.jpg" title="1510017786623924.jpg" alt="3749475020_998729174.310x310.jpg"/></p><p><span style=";font-family:宋体;font-size:14px"><span style="font-family:宋体">中石油成都分公司销售报价，石油</span>90# &nbsp;1000<span style="font-family:宋体">元</span><span style="font-family:Calibri">/</span><span style="font-family:宋体">吨，</span><span style="font-family:Calibri">80# &nbsp;1200</span><span style="font-family:宋体">元</span><span style="font-family:Calibri">/</span><span style="font-family:宋体">吨，柴油</span><span style="font-family:Calibri">90# &nbsp;1000</span><span style="font-family:宋体">元</span><span style="font-family:Calibri">/</span><span style="font-family:宋体">吨，</span><span style="font-family:Calibri">80# &nbsp;1200</span><span style="font-family:宋体">元</span><span style="font-family:Calibri">/</span><span style="font-family:宋体">吨，汽油</span><span style="font-family:Calibri">90# &nbsp;1000</span><span style="font-family:宋体">元</span><span style="font-family:Calibri">/</span><span style="font-family:宋体">吨，</span><span style="font-family:Calibri">80# &nbsp;1200</span><span style="font-family:宋体">元</span><span style="font-family:Calibri">/</span><span style="font-family:宋体">吨，煤油</span><span style="font-family:Calibri">90# &nbsp;1000</span><span style="font-family:宋体">元</span><span style="font-family:Calibri">/</span><span style="font-family:宋体">吨，</span><span style="font-family:Calibri">80# &nbsp;1200</span><span style="font-family:宋体">元</span><span style="font-family:Calibri">/</span><span style="font-family:宋体">吨，</span></span></p>
         */

        private String title;
        private int time;
        private String author;
        private String content;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
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
    }
}
