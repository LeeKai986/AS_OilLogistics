package com.zpf.oillogistics.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/9/22.
 * <p>
 * 石油类化工类详情
 */

public class OilOrChemtryInfoBean {


    /**
     * status : 0
     * msg : 获取成功
     * data : {"data":[{"id":24,"uid":87,"companyname":"0","money":"0","class":1,"classify":"汽油","model":"#93","title":"陈百万","price":"866","province":"云南省昆明市辖区","city":null,"area":null,"address":"非驴非马","img":"/uploads/20171010/150763862959dcbd65dfd40.jpg","intruduce":"想回家才能","quantity":"0","status":2,"time":1507638629,"is_order":1,"followers":"","statuss":2,"shelf":1,"follow":1}],"info":{"id":87,"phone":"18888888888","password":"f00715421ebb78a9152b37986a593925","niname":"","email":"","face":"/uploads/20171010/150763610659dcb38a76978.jpg","add_time":1506566528,"status":2,"province":"四川省","city":"资阳市","area":"雁江区","logintoken":"f3a2205d83f3f1608918973f66197f07","searchlog":"","logintime":0,"relname":"","card":"","telphone":"","manage":2,"toaddress":"你们的话我","suggest":null,"companyname":"测试","type":"","enterprise":"/uploads/20171010/150763610659dcb38a76da5.jpg","license":"/uploads/20171010/150763610659dcb38a7706c.jpg","place":"0","adds_time":"0","img":"0","cartcode":"0","car_type":"1","load":"0","driverpic":"0","runpic":"0","operatepic":"0","supercargopic":"0","myorder":"0","stroke":"0","statuss":0}}
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
         * data : [{"id":24,"uid":87,"companyname":"0","money":"0","class":1,"classify":"汽油","model":"#93","title":"陈百万","price":"866","province":"云南省昆明市辖区","city":null,"area":null,"address":"非驴非马","img":"/uploads/20171010/150763862959dcbd65dfd40.jpg","intruduce":"想回家才能","quantity":"0","status":2,"time":1507638629,"is_order":1,"followers":"","statuss":2,"shelf":1,"follow":1}]
         * info : {"id":87,"phone":"18888888888","password":"f00715421ebb78a9152b37986a593925","niname":"","email":"","face":"/uploads/20171010/150763610659dcb38a76978.jpg","add_time":1506566528,"status":2,"province":"四川省","city":"资阳市","area":"雁江区","logintoken":"f3a2205d83f3f1608918973f66197f07","searchlog":"","logintime":0,"relname":"","card":"","telphone":"","manage":2,"toaddress":"你们的话我","suggest":null,"companyname":"测试","type":"","enterprise":"/uploads/20171010/150763610659dcb38a76da5.jpg","license":"/uploads/20171010/150763610659dcb38a7706c.jpg","place":"0","adds_time":"0","img":"0","cartcode":"0","car_type":"1","load":"0","driverpic":"0","runpic":"0","operatepic":"0","supercargopic":"0","myorder":"0","stroke":"0","statuss":0}
         */

        private InfoBean info;
        private List<DataBean> data;

        public InfoBean getInfo() {
            return info;
        }

        public void setInfo(InfoBean info) {
            this.info = info;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class InfoBean {
            /**
             * id : 87
             * phone : 18888888888
             * password : f00715421ebb78a9152b37986a593925
             * niname :
             * email :
             * face : /uploads/20171010/150763610659dcb38a76978.jpg
             * add_time : 1506566528
             * status : 2
             * province : 四川省
             * city : 资阳市
             * area : 雁江区
             * logintoken : f3a2205d83f3f1608918973f66197f07
             * searchlog :
             * logintime : 0
             * relname :
             * card :
             * telphone :
             * manage : 2
             * toaddress : 你们的话我
             * suggest : null
             * companyname : 测试
             * type :
             * enterprise : /uploads/20171010/150763610659dcb38a76da5.jpg
             * license : /uploads/20171010/150763610659dcb38a7706c.jpg
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
            private String password;
            private String niname;
            private String email;
            private String face;
            private int add_time;
            private int status;
            private String province;
            private String city;
            private String area;
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

        public static class DataBean {
            /**
             * id : 24
             * uid : 87
             * companyname : 0
             * money : 0
             * class : 1
             * classify : 汽油
             * model : #93
             * title : 陈百万
             * price : 866
             * province : 云南省昆明市辖区
             * city : null
             * area : null
             * address : 非驴非马
             * img : /uploads/20171010/150763862959dcbd65dfd40.jpg
             * intruduce : 想回家才能
             * quantity : 0
             * status : 2
             * time : 1507638629
             * is_order : 1
             * followers :
             * statuss : 2
             * shelf : 1
             * follow : 1
             */

            private int id;
            private int uid;
            private String companyname;
            private String phone;
            private String money;
            @SerializedName("class")
            private int classX;
            private String classify;
            private String model;
            private String title;
            private String price;
            private String province;
            private Object city;
            private Object area;
            private String address;
            private String img;
            private String intruduce;
            private String quantity;
            private int status;
            private int time;
            private int is_order;
            private String followers;
            private int statuss;
            private int shelf;
            private int follow;

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

            public String getCompanyname() {
                return companyname;
            }

            public void setCompanyname(String companyname) {
                this.companyname = companyname;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public int getClassX() {
                return classX;
            }

            public void setClassX(int classX) {
                this.classX = classX;
            }

            public String getClassify() {
                return classify;
            }

            public void setClassify(String classify) {
                this.classify = classify;
            }

            public String getModel() {
                return model;
            }

            public void setModel(String model) {
                this.model = model;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public Object getCity() {
                return city;
            }

            public void setCity(Object city) {
                this.city = city;
            }

            public Object getArea() {
                return area;
            }

            public void setArea(Object area) {
                this.area = area;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
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

            public String getQuantity() {
                return quantity;
            }

            public void setQuantity(String quantity) {
                this.quantity = quantity;
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

            public int getIs_order() {
                return is_order;
            }

            public void setIs_order(int is_order) {
                this.is_order = is_order;
            }

            public String getFollowers() {
                return followers;
            }

            public void setFollowers(String followers) {
                this.followers = followers;
            }

            public int getStatuss() {
                return statuss;
            }

            public void setStatuss(int statuss) {
                this.statuss = statuss;
            }

            public int getShelf() {
                return shelf;
            }

            public void setShelf(int shelf) {
                this.shelf = shelf;
            }

            public int getFollow() {
                return follow;
            }

            public void setFollow(int follow) {
                this.follow = follow;
            }
        }
    }
}
