package com.zpf.oillogistics.bean.response;

import com.zpf.oillogistics.bean.CompanyOrder;

import java.util.List;

/**
 * Created by Administrator on 2017/10/12.
 */

public class CompanyOrderResponse {


    /**
     * status : 0
     * msg : 获取成功
     * data : {"total":2,"page":1,"page_size":10,"all_page":1,"data":[{"id":23,"no":"20179710097923","uid":87,"goodsname":"发布产品测试数据","company":"测试","address":"四川省成都市武侯区","phone":"18888888888","price":"999.00","number":22,"tocompany":"测试","toname":"","tophone":"","toplace":"吉林省长春市","toaddress":"楚峰国际","money":"0.00","status":6,"time":1507793706},{"id":24,"no":"20175148551020","uid":87,"goodsname":"汪汪汪汪汪","company":"0","address":"5971435","phone":"18888888888","price":"545.00","number":25,"tocompany":"测试","toname":"","tophone":"","toplace":"四川省成都市","toaddress":"楚峰国际","money":"0.00","status":0,"time":1507793779}]}
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
         * total : 2
         * page : 1
         * page_size : 10
         * all_page : 1
         * data : [{"id":23,"no":"20179710097923","uid":87,"goodsname":"发布产品测试数据","company":"测试","address":"四川省成都市武侯区","phone":"18888888888","price":"999.00","number":22,"tocompany":"测试","toname":"","tophone":"","toplace":"吉林省长春市","toaddress":"楚峰国际","money":"0.00","status":6,"time":1507793706},{"id":24,"no":"20175148551020","uid":87,"goodsname":"汪汪汪汪汪","company":"0","address":"5971435","phone":"18888888888","price":"545.00","number":25,"tocompany":"测试","toname":"","tophone":"","toplace":"四川省成都市","toaddress":"楚峰国际","money":"0.00","status":0,"time":1507793779}]
         */

        private int total;
        private int page;
        private int page_size;
        private int all_page;
        private List<CompanyOrder> data;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
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

        public List<CompanyOrder> getData() {
            return data;
        }

        public void setData(List<CompanyOrder> data) {
            this.data = data;
        }
    }
}
