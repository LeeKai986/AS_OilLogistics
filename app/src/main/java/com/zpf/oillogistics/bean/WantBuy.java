package com.zpf.oillogistics.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/9/20.
 */

public  class WantBuy {


    /**
     * uid : 87
     * id : 37
     * title : took
     * number : 58
     * name : 测试
     * address : 四川省资阳市雁江区刚好哈哈
     * phone : 156855569856
     * img : /uploads/20171020/150846914159e9699505e89.jpg
     * intruduce : 缩头
     * time : 1508469141
     * is_user : 1
     * class : 1
     * price :
     * status : 1
     * statuss : 2
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
}
