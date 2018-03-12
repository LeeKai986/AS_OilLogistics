package com.zpf.oillogistics.bean.response;

import com.zpf.oillogistics.bean.DriverOrder;

import java.util.List;

/**
 * Created by Administrator on 2017/10/13.
 */

public class DriverOrderResponse {


    /**
     * status : 0
     * msg : 数据请求成功
     * data : {"total":4,"page":1,"page_size":10,"all_page":1,"data":[{"id":9,"no":"2017975752572","uid":89,"goodsname":"铝单板","company":"超星网络","address":"成都市武侯区楚峰国际中心B座","phone":"13766666663","price":"20000.00","number":1999,"tocompany":"健健康康","toname":"中国石化","tophone":"13766666664","toplace":"天府长城中段站","toaddress":"交子大道975号","money":"0.00","status":7,"time":1506327034,"confirm":null},{"id":10,"no":"2017995097562","uid":89,"goodsname":"铝单板","company":"钟老师","address":"成都市武侯区楚峰国际中心B座","phone":"13766666663","price":"20000.00","number":1999,"tocompany":"超星网络","toname":"中国石化","tophone":"13766666664","toplace":"天府长城中段站","toaddress":"交子大道975号","money":"0.00","status":7,"time":1506327036,"confirm":null},{"id":11,"no":"2017100531022","uid":89,"goodsname":"铝单板","company":"钟老师","address":"成都市武侯区楚峰国际中心B座","phone":"13766666663","price":"20000.00","number":1999,"tocompany":"超星网络","toname":"中国石化","tophone":"13766666664","toplace":"天府长城中段站","toaddress":"交子大道975号","money":"0.00","status":7,"time":1506327037,"confirm":null},{"id":12,"no":"2017101551012","uid":89,"goodsname":"铝单板","company":"钟老师","address":"成都市武侯区楚峰国际中心B座","phone":"13766666663","price":"20000.00","number":1999,"tocompany":"超星网络","toname":"中国石化","tophone":"13766666664","toplace":"天府长城中段站","toaddress":"交子大道975号","money":"0.00","status":5,"time":1506327038,"confirm":null}]}
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
         * total : 4
         * page : 1
         * page_size : 10
         * all_page : 1
         * data : [{"id":9,"no":"2017975752572","uid":89,"goodsname":"铝单板","company":"超星网络","address":"成都市武侯区楚峰国际中心B座","phone":"13766666663","price":"20000.00","number":1999,"tocompany":"健健康康","toname":"中国石化","tophone":"13766666664","toplace":"天府长城中段站","toaddress":"交子大道975号","money":"0.00","status":7,"time":1506327034,"confirm":null},{"id":10,"no":"2017995097562","uid":89,"goodsname":"铝单板","company":"钟老师","address":"成都市武侯区楚峰国际中心B座","phone":"13766666663","price":"20000.00","number":1999,"tocompany":"超星网络","toname":"中国石化","tophone":"13766666664","toplace":"天府长城中段站","toaddress":"交子大道975号","money":"0.00","status":7,"time":1506327036,"confirm":null},{"id":11,"no":"2017100531022","uid":89,"goodsname":"铝单板","company":"钟老师","address":"成都市武侯区楚峰国际中心B座","phone":"13766666663","price":"20000.00","number":1999,"tocompany":"超星网络","toname":"中国石化","tophone":"13766666664","toplace":"天府长城中段站","toaddress":"交子大道975号","money":"0.00","status":7,"time":1506327037,"confirm":null},{"id":12,"no":"2017101551012","uid":89,"goodsname":"铝单板","company":"钟老师","address":"成都市武侯区楚峰国际中心B座","phone":"13766666663","price":"20000.00","number":1999,"tocompany":"超星网络","toname":"中国石化","tophone":"13766666664","toplace":"天府长城中段站","toaddress":"交子大道975号","money":"0.00","status":5,"time":1506327038,"confirm":null}]
         */

        private int total;
        private int page;
        private int page_size;
        private int all_page;
        private List<DriverOrder> data;

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

        public List<DriverOrder> getData() {
            return data;
        }

        public void setData(List<DriverOrder> data) {
            this.data = data;
        }

    }
}
