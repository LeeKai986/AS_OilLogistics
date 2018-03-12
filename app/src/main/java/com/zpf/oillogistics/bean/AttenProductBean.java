package com.zpf.oillogistics.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/9/27.
 * <p>
 * 关注的商品列表
 */

public class AttenProductBean {

    /**
     * status : 0
     * msg : 数据请求成功
     * data : {"total":7,"page":"1","page_size":10,"all_page":1,"data":[{"id":216,"uid":1,"companyname":"东明佳运","phone":"18124356789","money":"1000","class":1,"classify":"汽油","model":"89c","title":"jdisa你看到","price":"1","province":"上海市","city":"闵行区","area":"吴泾镇","address":"上海市上海市闵行区X210(虹梅南路)","img":"/uploads/20171117/e0fc13c45b3f9de516d8a8d94f2fc2be.png","intruduce":"放到我发的发","quantity":"20-35/吨","status":3,"time":1510907685,"is_order":1,"followers":"","statuss":1,"shelf":1,"longitude":"121.462612","latitude":"31.045473"},{"id":215,"uid":1,"companyname":"东明佳运","phone":"18000000000","money":"3333","class":1,"classify":"煤油","model":"90#","title":"测试 平台产品","price":"6666","province":"天津市","city":"河西区","area":"友谊路街道","address":"天津市天津市河西区","img":"/uploads/20171117/78619e3d49593aa82f235b683ee18b95.jpg","intruduce":"水电费三分","quantity":"20-35/吨","status":3,"time":1510904380,"is_order":1,"followers":"","statuss":1,"shelf":1,"longitude":"117.21059","latitude":"39.083767"},{"id":213,"uid":1,"companyname":"东明佳运","phone":"18000000000","money":"1200","class":1,"classify":"汽油","model":"89c","title":"国四汽油","price":"2000","province":"北京市","city":"西城区","area":"展览路街道","address":"北京市北京市西城区车公庄大街13号","img":"/uploads/20171115/ca9d30a40fec3beb1812a769a6a235e6.png","intruduce":"水电费四个","quantity":"20-35/吨","status":3,"time":1510716592,"is_order":1,"followers":"","statuss":1,"shelf":1,"longitude":"116.349358","latitude":"39.938401"},{"id":212,"uid":1,"companyname":"东明佳运","phone":"18000000000","money":"2000","class":2,"classify":"汽油","model":"89c","title":"国四汽油","price":"15000","province":"北京市","city":"西城区","area":"展览路街道","address":"北京市北京市西城区车公庄大街13号","img":"/uploads/20171114/d7e3348dcb26191b0c8f3ebb42f2c128.jpg","intruduce":"最新国四柴油","quantity":"20-35/吨","status":3,"time":1510640627,"is_order":1,"followers":"","statuss":1,"shelf":1,"longitude":"116.349367","latitude":"39.938443"},{"id":203,"uid":1,"companyname":"东明佳运","phone":"18000000000","money":"2000","class":1,"classify":"汽油","model":"89c","title":"国四柴油","price":"1200","province":"河北省","city":"唐山市","area":"丰润区","address":"河北省唐山市丰润区幸福道32号","img":"/uploads/20171110/61d165170214e967ede809a597ffbe10.png","intruduce":"最新柴油","quantity":"20-35/吨","status":3,"time":1510306552,"is_order":1,"followers":"","statuss":1,"shelf":1,"longitude":"118.168577","latitude":"39.838422"},{"id":195,"uid":1,"companyname":"东明佳运","phone":"18000000000","money":"2000","class":2,"classify":"柴油","model":"80#","title":"国四柴油","price":"5555","province":"天津市","city":"河东区","area":"","address":"天津市天津市河东区泰兴南路32号","img":"/uploads/20171109/31cb8c42df03e8789f38a705242edcf7.jpg","intruduce":"中石油成都分公司销售报价，石油90#  1000元/吨，80#  1200元/吨，柴油90#  1000元/吨，80#  1200元/吨，汽油90#  1000元/吨，80#  900元/吨，煤油90#  1000元/吨，80#  1200元/吨，","quantity":"35","status":3,"time":1510216248,"is_order":1,"followers":"","statuss":1,"shelf":1,"longitude":"117.258449","latitude":"39.134592"},{"id":194,"uid":1,"companyname":"东明佳运","phone":"18000000000","money":"5000","class":1,"classify":"汽油","model":"89c","title":"国四汽油","price":"20000","province":"北京市","city":"东城区","area":"东四街道","address":"北京市北京市东城区东四六条17","img":"/uploads/20171109/1813a97af7f9d4aae9b2313fb3e08bf3.jpg","intruduce":"中石油成都分公司销售报价，石油90#  1000元/吨，80#  1200元/吨，柴油90#  1000元/吨，80#  1200元/吨，汽油90#  1000元/吨，80#  900元/吨，煤油90#  1000元/吨，80#  1200元/吨，","quantity":"35","status":3,"time":1510215318,"is_order":1,"followers":"","statuss":1,"shelf":1,"longitude":"116.43039","latitude":"39.936383"}]}
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
         * total : 7
         * page : 1
         * page_size : 10
         * all_page : 1
         * data : [{"id":216,"uid":1,"companyname":"东明佳运","phone":"18124356789","money":"1000","class":1,"classify":"汽油","model":"89c","title":"jdisa你看到","price":"1","province":"上海市","city":"闵行区","area":"吴泾镇","address":"上海市上海市闵行区X210(虹梅南路)","img":"/uploads/20171117/e0fc13c45b3f9de516d8a8d94f2fc2be.png","intruduce":"放到我发的发","quantity":"20-35/吨","status":3,"time":1510907685,"is_order":1,"followers":"","statuss":1,"shelf":1,"longitude":"121.462612","latitude":"31.045473"},{"id":215,"uid":1,"companyname":"东明佳运","phone":"18000000000","money":"3333","class":1,"classify":"煤油","model":"90#","title":"测试 平台产品","price":"6666","province":"天津市","city":"河西区","area":"友谊路街道","address":"天津市天津市河西区","img":"/uploads/20171117/78619e3d49593aa82f235b683ee18b95.jpg","intruduce":"水电费三分","quantity":"20-35/吨","status":3,"time":1510904380,"is_order":1,"followers":"","statuss":1,"shelf":1,"longitude":"117.21059","latitude":"39.083767"},{"id":213,"uid":1,"companyname":"东明佳运","phone":"18000000000","money":"1200","class":1,"classify":"汽油","model":"89c","title":"国四汽油","price":"2000","province":"北京市","city":"西城区","area":"展览路街道","address":"北京市北京市西城区车公庄大街13号","img":"/uploads/20171115/ca9d30a40fec3beb1812a769a6a235e6.png","intruduce":"水电费四个","quantity":"20-35/吨","status":3,"time":1510716592,"is_order":1,"followers":"","statuss":1,"shelf":1,"longitude":"116.349358","latitude":"39.938401"},{"id":212,"uid":1,"companyname":"东明佳运","phone":"18000000000","money":"2000","class":2,"classify":"汽油","model":"89c","title":"国四汽油","price":"15000","province":"北京市","city":"西城区","area":"展览路街道","address":"北京市北京市西城区车公庄大街13号","img":"/uploads/20171114/d7e3348dcb26191b0c8f3ebb42f2c128.jpg","intruduce":"最新国四柴油","quantity":"20-35/吨","status":3,"time":1510640627,"is_order":1,"followers":"","statuss":1,"shelf":1,"longitude":"116.349367","latitude":"39.938443"},{"id":203,"uid":1,"companyname":"东明佳运","phone":"18000000000","money":"2000","class":1,"classify":"汽油","model":"89c","title":"国四柴油","price":"1200","province":"河北省","city":"唐山市","area":"丰润区","address":"河北省唐山市丰润区幸福道32号","img":"/uploads/20171110/61d165170214e967ede809a597ffbe10.png","intruduce":"最新柴油","quantity":"20-35/吨","status":3,"time":1510306552,"is_order":1,"followers":"","statuss":1,"shelf":1,"longitude":"118.168577","latitude":"39.838422"},{"id":195,"uid":1,"companyname":"东明佳运","phone":"18000000000","money":"2000","class":2,"classify":"柴油","model":"80#","title":"国四柴油","price":"5555","province":"天津市","city":"河东区","area":"","address":"天津市天津市河东区泰兴南路32号","img":"/uploads/20171109/31cb8c42df03e8789f38a705242edcf7.jpg","intruduce":"中石油成都分公司销售报价，石油90#  1000元/吨，80#  1200元/吨，柴油90#  1000元/吨，80#  1200元/吨，汽油90#  1000元/吨，80#  900元/吨，煤油90#  1000元/吨，80#  1200元/吨，","quantity":"35","status":3,"time":1510216248,"is_order":1,"followers":"","statuss":1,"shelf":1,"longitude":"117.258449","latitude":"39.134592"},{"id":194,"uid":1,"companyname":"东明佳运","phone":"18000000000","money":"5000","class":1,"classify":"汽油","model":"89c","title":"国四汽油","price":"20000","province":"北京市","city":"东城区","area":"东四街道","address":"北京市北京市东城区东四六条17","img":"/uploads/20171109/1813a97af7f9d4aae9b2313fb3e08bf3.jpg","intruduce":"中石油成都分公司销售报价，石油90#  1000元/吨，80#  1200元/吨，柴油90#  1000元/吨，80#  1200元/吨，汽油90#  1000元/吨，80#  900元/吨，煤油90#  1000元/吨，80#  1200元/吨，","quantity":"35","status":3,"time":1510215318,"is_order":1,"followers":"","statuss":1,"shelf":1,"longitude":"116.43039","latitude":"39.936383"}]
         */

        private int total;
        private String page;
        private int page_size;
        private int all_page;
        private List<DataBean> data;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public String getPage() {
            return page;
        }

        public void setPage(String page) {
            this.page = page;
        }

        public int getPage_size() {
            return page_size;
        }

        public void setPage_size(int page_size) {
            this.page_size = page_size;
        }

        public int getAll_page() {
            return all_page;
        }

        public void setAll_page(int all_page) {
            this.all_page = all_page;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * id : 216
             * uid : 1
             * companyname : 东明佳运
             * phone : 18124356789
             * money : 1000
             * class : 1
             * classify : 汽油
             * model : 89c
             * title : jdisa你看到
             * price : 1
             * province : 上海市
             * city : 闵行区
             * area : 吴泾镇
             * address : 上海市上海市闵行区X210(虹梅南路)
             * img : /uploads/20171117/e0fc13c45b3f9de516d8a8d94f2fc2be.png
             * intruduce : 放到我发的发
             * quantity : 20-35/吨
             * status : 3
             * time : 1510907685
             * is_order : 1
             * followers :
             * statuss : 1
             * shelf : 1
             * longitude : 121.462612
             * latitude : 31.045473
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
            private String city;
            private String area;
            private String address;
            private String img;
            private String intruduce;
            private String quantity;
            private int status;
            private String time;
            private int is_order;
            private String followers;
            private int statuss;
            private int shelf;
            private String longitude;
            private String latitude;

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

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
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
    }
}
