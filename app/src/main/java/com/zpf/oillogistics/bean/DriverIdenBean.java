package com.zpf.oillogistics.bean;

/**
 * Created by Administrator on 2017/11/10.
 * <p>
 * 司机身份资格认证
 */

public class DriverIdenBean {

    /**
     * status : 0
     * msg : 数据请求成功
     * data : {"id":197,"phone":"18012345678","password":"f00715421ebb78a9152b37986a593925","niname":"","face":"/uploads/20171109/adb18304ff3d94bb326b18ccf662b26b.jpg","add_time":1510214457,"status":3,"province":"山西省","city":"大同市","area":"城区","searchlog":"","logintime":0,"relname":"王师傅","card":"511602199410117090","telphone":"","manage":0,"toaddress":"山西省大同市城区迎宾西路30号","suggest":null,"companyname":"","enterprise":"0","license":"","place":"0","img":"/uploads/20171109/060364d7bed727535cc61e9c1c150510.jpg","cartcode":"川A12345","car_type":"1","load":"200","driverpic":"/uploads/20171109/6d4abe111357a8c88f9e676e66e8f45c.jpg","runpic":"/uploads/20171109/c2c75817a47067b2bbbe30adba186dc2.jpg","operatepic":"/uploads/20171109/9fb9dae7bd37fd85de9ff153538d64f6.jpg","supercargopic":"/uploads/20171109/8862613b96cb92f89116c37c2d8a272e.jpg","myorder":"0","stroke":"0","statuss":1,"longitude":"113.304748","latitude":"40.082005","logintoken":"162344c3db483f86bdfda912a4921a17","adds_time":1510214457}
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
         * id : 197
         * phone : 18012345678
         * password : f00715421ebb78a9152b37986a593925
         * niname :
         * face : /uploads/20171109/adb18304ff3d94bb326b18ccf662b26b.jpg
         * add_time : 1510214457
         * status : 3
         * province : 山西省
         * city : 大同市
         * area : 城区
         * searchlog :
         * logintime : 0
         * relname : 王师傅
         * card : 511602199410117090
         * telphone :
         * manage : 0
         * toaddress : 山西省大同市城区迎宾西路30号
         * suggest : null
         * companyname :
         * enterprise : 0
         * license :
         * place : 0
         * img : /uploads/20171109/060364d7bed727535cc61e9c1c150510.jpg
         * cartcode : 川A12345
         * car_type : 1
         * load : 200
         * driverpic : /uploads/20171109/6d4abe111357a8c88f9e676e66e8f45c.jpg
         * runpic : /uploads/20171109/c2c75817a47067b2bbbe30adba186dc2.jpg
         * operatepic : /uploads/20171109/9fb9dae7bd37fd85de9ff153538d64f6.jpg
         * supercargopic : /uploads/20171109/8862613b96cb92f89116c37c2d8a272e.jpg
         * myorder : 0
         * stroke : 0
         * statuss : 1
         * longitude : 113.304748
         * latitude : 40.082005
         * logintoken : 162344c3db483f86bdfda912a4921a17
         * adds_time : 1510214457
         */

        private int id;
        private String phone;
        private String password;
        private String niname;
        private String face;
        private String add_time;
        private String status;
        private String province;
        private String city;
        private String area;
        private String searchlog;
        private String logintime;
        private String relname;
        private String card;
        private String telphone;
        private String manage;
        private String toaddress;
        private String suggest;
        private String companyname;
        private String enterprise;
        private String license;
        private String place;
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
        private String statuss;
        private String longitude;
        private String latitude;
        private String logintoken;
        private String adds_time;

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

        public String getLogintoken() {
            return logintoken;
        }

        public void setLogintoken(String logintoken) {
            this.logintoken = logintoken;
        }

        public String getAdds_time() {
            return adds_time;
        }

        public void setAdds_time(String adds_time) {
            this.adds_time = adds_time;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "id=" + id +
                    ", phone='" + phone + '\'' +
                    ", password='" + password + '\'' +
                    ", niname='" + niname + '\'' +
                    ", face='" + face + '\'' +
                    ", add_time='" + add_time + '\'' +
                    ", status='" + status + '\'' +
                    ", province='" + province + '\'' +
                    ", city='" + city + '\'' +
                    ", area='" + area + '\'' +
                    ", searchlog='" + searchlog + '\'' +
                    ", logintime='" + logintime + '\'' +
                    ", relname='" + relname + '\'' +
                    ", card='" + card + '\'' +
                    ", telphone='" + telphone + '\'' +
                    ", manage='" + manage + '\'' +
                    ", toaddress='" + toaddress + '\'' +
                    ", suggest='" + suggest + '\'' +
                    ", companyname='" + companyname + '\'' +
                    ", enterprise='" + enterprise + '\'' +
                    ", license='" + license + '\'' +
                    ", place='" + place + '\'' +
                    ", img='" + img + '\'' +
                    ", cartcode='" + cartcode + '\'' +
                    ", car_type='" + car_type + '\'' +
                    ", load='" + load + '\'' +
                    ", driverpic='" + driverpic + '\'' +
                    ", runpic='" + runpic + '\'' +
                    ", operatepic='" + operatepic + '\'' +
                    ", supercargopic='" + supercargopic + '\'' +
                    ", myorder='" + myorder + '\'' +
                    ", stroke='" + stroke + '\'' +
                    ", statuss='" + statuss + '\'' +
                    ", longitude='" + longitude + '\'' +
                    ", latitude='" + latitude + '\'' +
                    ", logintoken='" + logintoken + '\'' +
                    ", adds_time='" + adds_time + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "DriverIdenBean{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                ", time=" + time +
                '}';
    }
}
