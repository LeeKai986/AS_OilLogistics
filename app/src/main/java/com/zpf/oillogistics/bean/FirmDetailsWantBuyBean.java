package com.zpf.oillogistics.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/9/21.
 *
 * 企业详情(求购)
 */

public class FirmDetailsWantBuyBean {


    /**
     * status : 0
     * msg : 数据请求成功
     * data : {"info":{"uid":87,"id":48,"title":"企业法求购","number":"60","name":"","address":"河北省唐山市乐亭县河北省唐山市乐亭县","phone":"18888888888","img":"/uploads/20171026/150900168959f189d938586.jpeg","intruduce":"阿斯顿西安市","time":1509001689,"is_user":2,"class":1,"price":"","status":1,"statuss":2,"longitude":"","latitude":""},"data":{"id":87,"phone":"18888888888","password":"f00715421ebb78a9152b37986a593925","niname":"","email":"","face":"/uploads/20171026/150899989859f182da0cbf2.jpg","add_time":1506566528,"status":2,"province":"黑龙江","city":"哈尔滨","area":null,"logintoken":"9eec7df68212f9d15602b5309c3a302b","searchlog":"","logintime":0,"relname":"","card":"","telphone":"","manage":1,"toaddress":"刚好哈哈","suggest":null,"companyname":"测试","type":"","enterprise":"/uploads/20171026/150899908659f17fae05835.jpg","license":"/uploads/20171026/150899908659f17fae05959.jpg","place":"0","adds_time":"0","img":"0","cartcode":"0","car_type":"1","load":"0","driverpic":"0","runpic":"0","operatepic":"0","supercargopic":"0","myorder":"0","stroke":"0","statuss":1,"longitude":null,"latitude":null}}
     * time : 1000
     */

    private int status;
    private String msg;
    private DataBeanX data;
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

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public static class DataBeanX {
        /**
         * info : {"uid":87,"id":48,"title":"企业法求购","number":"60","name":"","address":"河北省唐山市乐亭县河北省唐山市乐亭县","phone":"18888888888","img":"/uploads/20171026/150900168959f189d938586.jpeg","intruduce":"阿斯顿西安市","time":1509001689,"is_user":2,"class":1,"price":"","status":1,"statuss":2,"longitude":"","latitude":""}
         * data : {"id":87,"phone":"18888888888","password":"f00715421ebb78a9152b37986a593925","niname":"","email":"","face":"/uploads/20171026/150899989859f182da0cbf2.jpg","add_time":1506566528,"status":2,"province":"黑龙江","city":"哈尔滨","area":null,"logintoken":"9eec7df68212f9d15602b5309c3a302b","searchlog":"","logintime":0,"relname":"","card":"","telphone":"","manage":1,"toaddress":"刚好哈哈","suggest":null,"companyname":"测试","type":"","enterprise":"/uploads/20171026/150899908659f17fae05835.jpg","license":"/uploads/20171026/150899908659f17fae05959.jpg","place":"0","adds_time":"0","img":"0","cartcode":"0","car_type":"1","load":"0","driverpic":"0","runpic":"0","operatepic":"0","supercargopic":"0","myorder":"0","stroke":"0","statuss":1,"longitude":null,"latitude":null}
         */

        private InfoBean info;
        private DataBean data;

        public InfoBean getInfo() {
            return info;
        }

        public void setInfo(InfoBean info) {
            this.info = info;
        }

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public static class InfoBean {
            /**
             * uid : 87
             * id : 48
             * title : 企业法求购
             * number : 60
             * name :
             * address : 河北省唐山市乐亭县河北省唐山市乐亭县
             * phone : 18888888888
             * img : /uploads/20171026/150900168959f189d938586.jpeg
             * intruduce : 阿斯顿西安市
             * time : 1509001689
             * is_user : 2
             * class : 1
             * price :
             * status : 1
             * statuss : 2
             * longitude :
             * latitude :
             */

            private int uid;
            private int id;
            private String title;
            private String number;
            private String name;
            private String address;
            private String phone;
            private String img;
            private String intruduce;
            private int time;
            private int is_user;
            @SerializedName("class")
            private int classX;
            private String price;
            private int status;
            private int statuss;
            private String longitude;
            private String latitude;

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getIntruduce() {
                return intruduce;
            }

            public void setIntruduce(String intruduce) {
                this.intruduce = intruduce;
            }

            public int getTime() {
                return time;
            }

            public void setTime(int time) {
                this.time = time;
            }

            public int getIs_user() {
                return is_user;
            }

            public void setIs_user(int is_user) {
                this.is_user = is_user;
            }

            public int getClassX() {
                return classX;
            }

            public void setClassX(int classX) {
                this.classX = classX;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getStatuss() {
                return statuss;
            }

            public void setStatuss(int statuss) {
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
        }

        public static class DataBean {
            /**
             * id : 87
             * phone : 18888888888
             * password : f00715421ebb78a9152b37986a593925
             * niname :
             * email :
             * face : /uploads/20171026/150899989859f182da0cbf2.jpg
             * add_time : 1506566528
             * status : 2
             * province : 黑龙江
             * city : 哈尔滨
             * area : null
             * logintoken : 9eec7df68212f9d15602b5309c3a302b
             * searchlog :
             * logintime : 0
             * relname :
             * card :
             * telphone :
             * manage : 1
             * toaddress : 刚好哈哈
             * suggest : null
             * companyname : 测试
             * type :
             * enterprise : /uploads/20171026/150899908659f17fae05835.jpg
             * license : /uploads/20171026/150899908659f17fae05959.jpg
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
             * statuss : 1
             * longitude : null
             * latitude : null
             */

            private int id;
            private String phone;
            private String password;
            private String niname;
            private String email;
            private String face;
            private int add_time;
            private int status;
            private String province;
            private String city;
            private Object area;
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
            private Object longitude;
            private Object latitude;

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

            public Object getArea() {
                return area;
            }

            public void setArea(Object area) {
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

            public Object getLongitude() {
                return longitude;
            }

            public void setLongitude(Object longitude) {
                this.longitude = longitude;
            }

            public Object getLatitude() {
                return latitude;
            }

            public void setLatitude(Object latitude) {
                this.latitude = latitude;
            }
        }
    }
}
