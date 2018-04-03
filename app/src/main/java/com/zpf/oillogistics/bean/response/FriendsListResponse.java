package com.zpf.oillogistics.bean.response;

import java.util.List;

/**
 * Created by Administrator on 2017/9/21.
 */

public class FriendsListResponse {


    /**
     * data : [{"add_time":"1522599091","area":"东明县","car_type":"0","card":"","cartcode":"0","city":"荷泽市","click":"2","companyname":"东明佳润物流有限公司","driverpic":"0","enterprise":"/uploads/20180402/15225990915ac104b37ff06.jpg","face":"/uploads/20180402/15225990915ac104b37fa14.jpg","goods":"0","id":"1399","img":"0","latitude":"0","license":"/uploads/20180402/15225990915ac104b380027.jpg","load":"0","logintime":"0","logintoken":"3fcb00e9e1c01b5e1c2f1ebc831b7dde","manage":"1","myorder":"0","niname":"","offer":"0","operatepic":"0","password":"f00715421ebb78a9152b37986a593925","phone":"18911555110","place":"0","province":"山东省","relname":"东明佳润物流有限公司","runpic":"0","searchlog":"","status":"2","statuss":"1","stroke":"0","supercargopic":"0","sys":"1","telphone":"","toaddress":"菜园集工业园区"},{"add_time":"1522634526","car_type":"0","card":"370305198102092822","cartcode":"0","city":"","click":"2","companyname":"","driverpic":"0","enterprise":"0","face":"/uploads/default.png","goods":"0","id":"1540","img":"0","license":"","load":"0","logintime":"0","logintoken":"2d15891ba7aa24e76c22d4f11f195127","manage":"1","myorder":"0","niname":"","offer":"0","operatepic":"0","password":"f00715421ebb78a9152b37986a593925","phone":"15305334280","place":"0","province":"山东省淄博临淄区","relname":"孙小姐","runpic":"0","searchlog":"","status":"1","statuss":"1","stroke":"0","supercargopic":"0","sys":"0","telphone":"","toaddress":"鑫一诺大厦"}]
     * msg : 获取成功.
     * status : 0
     * time : 1000
     */

    private String msg;
    private int status;
    private int time;
    private List<DataBean> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * add_time : 1522599091
         * area : 东明县
         * car_type : 0
         * card :
         * cartcode : 0
         * city : 荷泽市
         * click : 2
         * companyname : 东明佳润物流有限公司
         * driverpic : 0
         * enterprise : /uploads/20180402/15225990915ac104b37ff06.jpg
         * face : /uploads/20180402/15225990915ac104b37fa14.jpg
         * goods : 0
         * id : 1399
         * img : 0
         * latitude : 0
         * license : /uploads/20180402/15225990915ac104b380027.jpg
         * load : 0
         * logintime : 0
         * logintoken : 3fcb00e9e1c01b5e1c2f1ebc831b7dde
         * manage : 1
         * myorder : 0
         * niname :
         * offer : 0
         * operatepic : 0
         * password : f00715421ebb78a9152b37986a593925
         * phone : 18911555110
         * place : 0
         * province : 山东省
         * relname : 东明佳润物流有限公司
         * runpic : 0
         * searchlog :
         * status : 2
         * statuss : 1
         * stroke : 0
         * supercargopic : 0
         * sys : 1
         * telphone :
         * toaddress : 菜园集工业园区
         */

        private String add_time;
        private String area;
        private String car_type;
        private String card;
        private String cartcode;
        private String city;
        private String click;
        private String companyname;
        private String driverpic;
        private String enterprise;
        private String face;
        private String goods;
        private String id;
        private String img;
        private String latitude;
        private String license;
        private String load;
        private String logintime;
        private String logintoken;
        private String manage;
        private String myorder;
        private String niname;
        private String offer;
        private String operatepic;
        private String password;
        private String phone;
        private String place;
        private String province;
        private String relname;
        private String runpic;
        private String searchlog;
        private String status;
        private String statuss;
        private String stroke;
        private String supercargopic;
        private String sys;
        private String telphone;
        private String toaddress;

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getCar_type() {
            return car_type;
        }

        public void setCar_type(String car_type) {
            this.car_type = car_type;
        }

        public String getCard() {
            return card;
        }

        public void setCard(String card) {
            this.card = card;
        }

        public String getCartcode() {
            return cartcode;
        }

        public void setCartcode(String cartcode) {
            this.cartcode = cartcode;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getClick() {
            return click;
        }

        public void setClick(String click) {
            this.click = click;
        }

        public String getCompanyname() {
            return companyname;
        }

        public void setCompanyname(String companyname) {
            this.companyname = companyname;
        }

        public String getDriverpic() {
            return driverpic;
        }

        public void setDriverpic(String driverpic) {
            this.driverpic = driverpic;
        }

        public String getEnterprise() {
            return enterprise;
        }

        public void setEnterprise(String enterprise) {
            this.enterprise = enterprise;
        }

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
        }

        public String getGoods() {
            return goods;
        }

        public void setGoods(String goods) {
            this.goods = goods;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLicense() {
            return license;
        }

        public void setLicense(String license) {
            this.license = license;
        }

        public String getLoad() {
            return load;
        }

        public void setLoad(String load) {
            this.load = load;
        }

        public String getLogintime() {
            return logintime;
        }

        public void setLogintime(String logintime) {
            this.logintime = logintime;
        }

        public String getLogintoken() {
            return logintoken;
        }

        public void setLogintoken(String logintoken) {
            this.logintoken = logintoken;
        }

        public String getManage() {
            return manage;
        }

        public void setManage(String manage) {
            this.manage = manage;
        }

        public String getMyorder() {
            return myorder;
        }

        public void setMyorder(String myorder) {
            this.myorder = myorder;
        }

        public String getNiname() {
            return niname;
        }

        public void setNiname(String niname) {
            this.niname = niname;
        }

        public String getOffer() {
            return offer;
        }

        public void setOffer(String offer) {
            this.offer = offer;
        }

        public String getOperatepic() {
            return operatepic;
        }

        public void setOperatepic(String operatepic) {
            this.operatepic = operatepic;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPlace() {
            return place;
        }

        public void setPlace(String place) {
            this.place = place;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getRelname() {
            return relname;
        }

        public void setRelname(String relname) {
            this.relname = relname;
        }

        public String getRunpic() {
            return runpic;
        }

        public void setRunpic(String runpic) {
            this.runpic = runpic;
        }

        public String getSearchlog() {
            return searchlog;
        }

        public void setSearchlog(String searchlog) {
            this.searchlog = searchlog;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getStatuss() {
            return statuss;
        }

        public void setStatuss(String statuss) {
            this.statuss = statuss;
        }

        public String getStroke() {
            return stroke;
        }

        public void setStroke(String stroke) {
            this.stroke = stroke;
        }

        public String getSupercargopic() {
            return supercargopic;
        }

        public void setSupercargopic(String supercargopic) {
            this.supercargopic = supercargopic;
        }

        public String getSys() {
            return sys;
        }

        public void setSys(String sys) {
            this.sys = sys;
        }

        public String getTelphone() {
            return telphone;
        }

        public void setTelphone(String telphone) {
            this.telphone = telphone;
        }

        public String getToaddress() {
            return toaddress;
        }

        public void setToaddress(String toaddress) {
            this.toaddress = toaddress;
        }
    }
}
