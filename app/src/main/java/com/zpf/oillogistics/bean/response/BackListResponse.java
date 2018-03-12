package com.zpf.oillogistics.bean.response;

import com.zpf.oillogistics.bean.FeedBack;

import java.util.List;

/**
 * Created by Administrator on 2017/10/10.
 */

public class BackListResponse {


    /**
     * status : 0
     * msg : 数据请求成功
     * data : [{"id":12,"uid":86,"feedback":"v回家","img":"/uploads/20171010/150761898059dc70a4422a8.jpg","fbtime":1507618980,"revert":"0","retime":0},{"id":13,"uid":86,"feedback":"差别","img":"/uploads/20171010/150761928259dc71d249cb9.jpg","fbtime":1507619282,"revert":"0","retime":0}]
     * time : 1000
     */

    public int status;
    public String msg;
    public int time;
    public List<FeedBack> data;

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

    public List<FeedBack> getData() {
        return data;
    }

    public void setData(List<FeedBack> data) {
        this.data = data;
    }


}
