package com.zpf.oillogistics.bean.response;

/**
 * Created by Administrator on 2017/9/21.
 */

public class AddFriendResponse {


    /**
     * status : 0
     * msg : 用户昵称
     * data : {"relname":"Tom","companyname":"","header":"/uploads/20171101/150951511059f95f66685bf.jpg","phone":"18381079491","status":1}
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
         * relname : Tom
         * companyname :
         * header : /uploads/20171101/150951511059f95f66685bf.jpg
         * phone : 18381079491
         * status : 1
         */

        private String relname;
        private String companyname;
        private String header;
        private String phone;
        private int status;

        public String getRelname() {
            return relname;
        }

        public void setRelname(String relname) {
            this.relname = relname;
        }

        public String getCompanyname() {
            return companyname;
        }

        public void setCompanyname(String companyname) {
            this.companyname = companyname;
        }

        public String getHeader() {
            return header;
        }

        public void setHeader(String header) {
            this.header = header;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
