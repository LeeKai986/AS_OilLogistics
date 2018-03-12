package com.zpf.oillogistics.bean.response;

/**
 * Created by Administrator on 2017/10/9.
 */

public class SelfChangeResponse {


    /**
     * status : 0
     * msg : 数据保存成功
     * data : {"id":86,"phone":"13008169605","password":"f00715421ebb78a9152b37986a593925","niname":"","email":"","face":"/uploads/20171009/150753465859db2742cff9d.jpg","add_time":1506566260,"status":1,"province":"山东省济南","city":"","area":null,"logintoken":"023613fed00dea554e42c9ec160bf8e0","searchlog":"","logintime":0,"relname":"哈哈哈","card":"5686466865689","telphone":"","manage":1,"toaddress":"吃那么久","suggest":null,"companyname":"","type":"","enterprise":"0","license":"","place":"0","adds_time":"0","img":"0","cartcode":"0","car_type":"1","load":"0","driverpic":"0","runpic":"0","operatepic":"0","supercargopic":"0","myorder":"0","stroke":"0","statuss":0}
     * time : 1000
     */

    public int status;
    public String msg;
    public DataBean data;
    public int time;

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
         * id : 86
         * phone : 13008169605
         * password : f00715421ebb78a9152b37986a593925
         * niname :
         * email :
         * face : /uploads/20171009/150753465859db2742cff9d.jpg
         * add_time : 1506566260
         * status : 1
         * province : 山东省济南
         * city :
         * area : null
         * logintoken : 023613fed00dea554e42c9ec160bf8e0
         * searchlog :
         * logintime : 0
         * relname : 哈哈哈
         * card : 5686466865689
         * telphone :
         * manage : 1
         * toaddress : 吃那么久
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

        public int id;
        public String phone;
        public String password;
        public String niname;
        public String email;
        public String face;
        public String add_time;
        public String status;
        public String province;
        public String city;
        public String area;
        public String logintoken;
        public String searchlog;
        public String logintime;
        public String relname;
        public String card;
        public String telphone;
        public String manage;
        public String toaddress;
        public String suggest;
        public String companyname;
        public String type;
        public String enterprise;
        public String license;
        public String place;
        public String adds_time;
        public String img;
        public String cartcode;
        public String car_type;
        public String load;
        public String driverpic;
        public String runpic;
        public String operatepic;
        public String supercargopic;
        public String myorder;
        public String stroke;
        public String statuss;
        public String longitude;
        public String latitude;
        public String route;


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

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
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

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
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

        public String getLogintime() {
            return logintime;
        }

        public void setLogintime(String logintime) {
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

        public String getManage() {
            return manage;
        }

        public void setManage(String manage) {
            this.manage = manage;
        }

        public String getToaddress() {
            return toaddress;
        }

        public void setToaddress(String toaddress) {
            this.toaddress = toaddress;
        }

        public String getSuggest() {
            return suggest;
        }

        public void setSuggest(String suggest) {
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

        public String getStatuss() {
            return statuss;
        }

        public void setStatuss(String statuss) {
            this.statuss = statuss;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getRoute() {
            return route;
        }

        public void setRoute(String route) {
            this.route = route;
        }
    }
}
