package com.zpf.oillogistics.bean;

/**
 * Created by Administrator on 2017/9/20.
 * <p>
 * 通用型bean
 * 1.获取验证码(sendCode)
 */

public class NormalBean {

    /**
     * status : 2
     * msg : 验证码错误或已过期
     * data : null
     * time : 1000
     */

    private int status;
    private String msg;
    private String data;
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
