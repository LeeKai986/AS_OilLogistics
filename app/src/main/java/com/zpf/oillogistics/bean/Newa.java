package com.zpf.oillogistics.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/9/20.
 */

public  class Newa {
    /**
     * id : 8
     * region : 华南
     * class : 1
     * title : 阿德萨
     * author : 超
     * img : /uploads/20170921/7c8e83b226471c753da3af6d19678818.jpg
     * content : 1231231
     * status : 1
     * time : 1507233266
     */

    private int id;
    private String region;
    @SerializedName("class")
    private int classX;
    private String title;
    private String author;
    private String img;
    private String content;
    private int status;
    private int time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getClassX() {
        return classX;
    }

    public void setClassX(int classX) {
        this.classX = classX;
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

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
