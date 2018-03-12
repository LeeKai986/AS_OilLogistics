package com.zpf.oillogistics.bean.response;

/**
 * Created by Administrator on 2017/10/16.
 */

public class AboutUsResponse {

    /**
     * status : 0
     * msg : 获取成功
     * data : {"id":4,"content":"<p><p style=\"text-align: left;\">&nbsp; &nbsp; &nbsp; &nbsp;石化通是依托第三方平台（IOS平台、安卓平台、PC平台），与360集团公司联合开发的一款应用软件，主要针对危化品运输车主、危化品购销主体等群体，集信息收集发布共享、网上贸易平台、在线支付、语音视频聊天、空车配货、车辆定位与实时监控、GPS云地图、加油站等便民快捷支付等功能于一身的综合应用软件。<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;石化通APP一直以来以\u201c诚信\u201d作为公司和每位员工的行为准则，以\u201c超越客户期望\u201d为目标，致力于为用户提供丰富的信息选择、便捷的交易方式和完善的售后打造更好的交易平台。<br/><br/><br/><br/>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;东明佳运物流有限公司<br/><\/p><p><br/><\/p>","time":1510295471}
     * time : 1000
     */

    private int status;
    private String msg;
    private DataBean data;
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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public static class DataBean {
        /**
         * id : 4
         * content : <p><p style="text-align: left;">&nbsp; &nbsp; &nbsp; &nbsp;石化通是依托第三方平台（IOS平台、安卓平台、PC平台），与360集团公司联合开发的一款应用软件，主要针对危化品运输车主、危化品购销主体等群体，集信息收集发布共享、网上贸易平台、在线支付、语音视频聊天、空车配货、车辆定位与实时监控、GPS云地图、加油站等便民快捷支付等功能于一身的综合应用软件。<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;石化通APP一直以来以“诚信”作为公司和每位员工的行为准则，以“超越客户期望”为目标，致力于为用户提供丰富的信息选择、便捷的交易方式和完善的售后打造更好的交易平台。<br/><br/><br/><br/>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;东明佳运物流有限公司<br/></p><p><br/></p>
         * time : 1510295471
         */

        private int id;
        private String content;
        private int time;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }
    }
}
