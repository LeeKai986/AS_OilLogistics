package com.zpf.oillogistics.bean.response;

import java.util.List;

/**
 * Created by Administrator on 2017/10/25.
 */

public class InformationResponse {

    /**
     * status : 0
     * msg : 获取成功
     * data : [{"title":"阿斯顿","time":1506507156,"author":"实打实","img":"/uploads/20170928/33daca2b54954a80dd838d50ae53c786.jpg","content":"阿斯顿"}]
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
         * title : 阿斯顿
         * time : 1506507156
         * author : 实打实
         * img : /uploads/20170928/33daca2b54954a80dd838d50ae53c786.jpg
         * content : 阿斯顿
         */

        private String title;
        private int time;
        private String author;
        private String img;
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
    }
}
