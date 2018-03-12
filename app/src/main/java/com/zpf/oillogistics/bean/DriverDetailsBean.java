package com.zpf.oillogistics.bean;

/**
 * Created by Administrator on 2017/9/21.
 * <p>
 * 司机详情
 */

public class DriverDetailsBean {


    /**
     * status : 0
     * msg : 数据请求成功
     * data : {"info":{"id":89,"phone":"18666666666","password":"f00715421ebb78a9152b37986a593925","niname":"","email":"","face":"/uploads/20171010/150759756059dc1cf81a0f4.jpg","add_time":1506567019,"status":3,"province":"四川省","city":"资阳市","area":"安岳县","logintoken":"2af1f99079b2ccc082b81b70cb477cdc","searchlog":"","logintime":0,"relname":"成交价","card":"867686868689","telphone":"","manage":0,"toaddress":"今天晚上我要吃肉","suggest":null,"companyname":"","type":"","enterprise":"0","license":"","place":"0","adds_time":"0","img":"/uploads/20171011/150772536059de1030856e4.jpg","cartcode":"云566888888","car_type":"第三类","load":"回家吧v","driverpic":"/uploads/20171011/150772536059de103085855.jpg","runpic":"/uploads/20171011/150772536059de10308593e.jpg","operatepic":"/uploads/20171011/150772536059de103085a3f.jpg","supercargopic":"/uploads/20171011/150772536059de103085b44.jpg","myorder":"0","stroke":"0","statuss":2},"route":"/uploads/default.png"}
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
         * info : {"id":89,"phone":"18666666666","password":"f00715421ebb78a9152b37986a593925","niname":"","email":"","face":"/uploads/20171010/150759756059dc1cf81a0f4.jpg","add_time":1506567019,"status":3,"province":"四川省","city":"资阳市","area":"安岳县","logintoken":"2af1f99079b2ccc082b81b70cb477cdc","searchlog":"","logintime":0,"relname":"成交价","card":"867686868689","telphone":"","manage":0,"toaddress":"今天晚上我要吃肉","suggest":null,"companyname":"","type":"","enterprise":"0","license":"","place":"0","adds_time":"0","img":"/uploads/20171011/150772536059de1030856e4.jpg","cartcode":"云566888888","car_type":"第三类","load":"回家吧v","driverpic":"/uploads/20171011/150772536059de103085855.jpg","runpic":"/uploads/20171011/150772536059de10308593e.jpg","operatepic":"/uploads/20171011/150772536059de103085a3f.jpg","supercargopic":"/uploads/20171011/150772536059de103085b44.jpg","myorder":"0","stroke":"0","statuss":2}
         * route : /uploads/default.png
         */

        private InfoBean info;
        private String route;

        public InfoBean getInfo() {
            return info;
        }

        public void setInfo(InfoBean info) {
            this.info = info;
        }

        public String getRoute() {
            return route;
        }

        public void setRoute(String route) {
            this.route = route;
        }

        public static class InfoBean {
            /**
             * id : 89
             * phone : 18666666666
             * password : f00715421ebb78a9152b37986a593925
             * niname :
             * email :
             * face : /uploads/20171010/150759756059dc1cf81a0f4.jpg
             * add_time : 1506567019
             * status : 3
             * province : 四川省
             * city : 资阳市
             * area : 安岳县
             * logintoken : 2af1f99079b2ccc082b81b70cb477cdc
             * searchlog :
             * logintime : 0
             * relname : 成交价
             * card : 867686868689
             * telphone :
             * manage : 0
             * toaddress : 今天晚上我要吃肉
             * suggest : null
             * companyname :
             * type :
             * enterprise : 0
             * license :
             * place : 0
             * adds_time : 0
             * img : /uploads/20171011/150772536059de1030856e4.jpg
             * cartcode : 云566888888
             * car_type : 第三类
             * load : 回家吧v
             * driverpic : /uploads/20171011/150772536059de103085855.jpg
             * runpic : /uploads/20171011/150772536059de10308593e.jpg
             * operatepic : /uploads/20171011/150772536059de103085a3f.jpg
             * supercargopic : /uploads/20171011/150772536059de103085b44.jpg
             * myorder : 0
             * stroke : 0
             * statuss : 2
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
            private String route;
            private String position;

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

            public String getRoute() {
                return route;
            }

            public void setRoute(String route) {
                this.route = route;
            }

            public String getPosition() {
                return position;
            }

            public void setPosition(String position) {
                this.position = position;
            }
        }
    }
}
