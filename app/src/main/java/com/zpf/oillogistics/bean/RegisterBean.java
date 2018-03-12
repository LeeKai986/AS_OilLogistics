package com.zpf.oillogistics.bean;

/**
 * Created by Administrator on 2017/9/27.
 * <p>
 * 注册
 */

public class RegisterBean {


    /**
     * status : 0
     * msg : 注册成功
     * data : {"user":{"id":93,"phone":"13022222222","niname":"","email":"","face":"/uploads/default.png","add_time":1506568191,"status":1,"address":"","logintoken":"39b275845553723b0f26323c8cf93293","searchlog":"","logintime":0,"relname":"","card":"","telphone":"","manage":0,"toaddress":"","suggest":null,"companyname":"","type":"","enterprise":"0","license":"","place":"0","adds_time":"0","img":"0","cartcode":"0","car_type":"1","load":"0","driverpic":"0","runpic":"0","operatepic":"0","supercargopic":"0","myorder":"0","stroke":"0","statuss":0}}
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
         * user : {"id":93,"phone":"13022222222","niname":"","email":"","face":"/uploads/default.png","add_time":1506568191,"status":1,"address":"","logintoken":"39b275845553723b0f26323c8cf93293","searchlog":"","logintime":0,"relname":"","card":"","telphone":"","manage":0,"toaddress":"","suggest":null,"companyname":"","type":"","enterprise":"0","license":"","place":"0","adds_time":"0","img":"0","cartcode":"0","car_type":"1","load":"0","driverpic":"0","runpic":"0","operatepic":"0","supercargopic":"0","myorder":"0","stroke":"0","statuss":0}
         */

        private UserBean user;

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public static class UserBean {
            /**
             * id : 93
             * phone : 13022222222
             * niname :
             * email :
             * face : /uploads/default.png
             * add_time : 1506568191
             * status : 1
             * address :
             * logintoken : 39b275845553723b0f26323c8cf93293
             * searchlog :
             * logintime : 0
             * relname :
             * card :
             * telphone :
             * manage : 0
             * toaddress :
             * suggest : null
             * companyname :
             * type :
             * enterprise : 0
             * license :
             * place : 0
             * adds_time : 0
             * img : 0
             * cartcode : 0
             * car_type : 1
             * load : 0
             * driverpic : 0
             * runpic : 0
             * operatepic : 0
             * supercargopic : 0
             * myorder : 0
             * stroke : 0
             * statuss : 0
             */

            private int id;
            private String phone;
            private String niname;
            private String email;
            private String face;
            private int add_time;
            private int status;
            private String address;
            private String logintoken;
            private String searchlog;
            private int logintime;
            private String relname;
            private String card;
            private String telphone;
            private int manage;
            private String toaddress;
            private Object suggest;
            private String companyname;
            private String type;
            private String enterprise;
            private String license;
            private String place;
            private String adds_time;
            private String img;
            private String cartcode;
            private String car_type;
            private String load;
            private String driverpic;
            private String runpic;
            private String operatepic;
            private String supercargopic;
            private String myorder;
            private String stroke;
            private int statuss;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getNiname() {
                return niname;
            }

            public void setNiname(String niname) {
                this.niname = niname;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getFace() {
                return face;
            }

            public void setFace(String face) {
                this.face = face;
            }

            public int getAdd_time() {
                return add_time;
            }

            public void setAdd_time(int add_time) {
                this.add_time = add_time;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getLogintoken() {
                return logintoken;
            }

            public void setLogintoken(String logintoken) {
                this.logintoken = logintoken;
            }

            public String getSearchlog() {
                return searchlog;
            }

            public void setSearchlog(String searchlog) {
                this.searchlog = searchlog;
            }

            public int getLogintime() {
                return logintime;
            }

            public void setLogintime(int logintime) {
                this.logintime = logintime;
            }

            public String getRelname() {
                return relname;
            }

            public void setRelname(String relname) {
                this.relname = relname;
            }

            public String getCard() {
                return card;
            }

            public void setCard(String card) {
                this.card = card;
            }

            public String getTelphone() {
                return telphone;
            }

            public void setTelphone(String telphone) {
                this.telphone = telphone;
            }

            public int getManage() {
                return manage;
            }

            public void setManage(int manage) {
                this.manage = manage;
            }

            public String getToaddress() {
                return toaddress;
            }

            public void setToaddress(String toaddress) {
                this.toaddress = toaddress;
            }

            public Object getSuggest() {
                return suggest;
            }

            public void setSuggest(Object suggest) {
                this.suggest = suggest;
            }

            public String getCompanyname() {
                return companyname;
            }

            public void setCompanyname(String companyname) {
                this.companyname = companyname;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getEnterprise() {
                return enterprise;
            }

            public void setEnterprise(String enterprise) {
                this.enterprise = enterprise;
            }

            public String getLicense() {
                return license;
            }

            public void setLicense(String license) {
                this.license = license;
            }

            public String getPlace() {
                return place;
            }

            public void setPlace(String place) {
                this.place = place;
            }

            public String getAdds_time() {
                return adds_time;
            }

            public void setAdds_time(String adds_time) {
                this.adds_time = adds_time;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getCartcode() {
                return cartcode;
            }

            public void setCartcode(String cartcode) {
                this.cartcode = cartcode;
            }

            public String getCar_type() {
                return car_type;
            }

            public void setCar_type(String car_type) {
                this.car_type = car_type;
            }

            public String getLoad() {
                return load;
            }

            public void setLoad(String load) {
                this.load = load;
            }

            public String getDriverpic() {
                return driverpic;
            }

            public void setDriverpic(String driverpic) {
                this.driverpic = driverpic;
            }

            public String getRunpic() {
                return runpic;
            }

            public void setRunpic(String runpic) {
                this.runpic = runpic;
            }

            public String getOperatepic() {
                return operatepic;
            }

            public void setOperatepic(String operatepic) {
                this.operatepic = operatepic;
            }

            public String getSupercargopic() {
                return supercargopic;
            }

            public void setSupercargopic(String supercargopic) {
                this.supercargopic = supercargopic;
            }

            public String getMyorder() {
                return myorder;
            }

            public void setMyorder(String myorder) {
                this.myorder = myorder;
            }

            public String getStroke() {
                return stroke;
            }

            public void setStroke(String stroke) {
                this.stroke = stroke;
            }

            public int getStatuss() {
                return statuss;
            }

            public void setStatuss(int statuss) {
                this.statuss = statuss;
            }
        }
    }
}
