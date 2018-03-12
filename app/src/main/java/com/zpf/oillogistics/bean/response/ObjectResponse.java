package com.zpf.oillogistics.bean.response;

/**
 * Created by Administrator on 2017/10/18.
 */

public class ObjectResponse {


    /**
     * status : 0
     * msg : 删除数据成功
     * data : null
     * time : 1000
     */

    private int status;
    private String msg;
    private Object data;
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
