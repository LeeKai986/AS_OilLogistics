package com.zpf.oillogistics.bean;

/**
 * Created by Administrator on 2017/9/19.
 */

public class FeedBack {
    /**
     * id : 12
     * uid : 86
     * feedback : v回家
     * img : /uploads/20171010/150761898059dc70a4422a8.jpg
     * fbtime : 1507618980
     * revert : 0
     * retime : 0
     */

    public int id;
    public int uid;
    public String feedback;
    public String img;
    public int fbtime;
    public String revert;
    public int retime;

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

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getFbtime() {
        return fbtime;
    }

    public void setFbtime(int fbtime) {
        this.fbtime = fbtime;
    }

    public String getRevert() {
        return revert;
    }

    public void setRevert(String revert) {
        this.revert = revert;
    }

    public int getRetime() {
        return retime;
    }

    public void setRetime(int retime) {
        this.retime = retime;
    }
}
