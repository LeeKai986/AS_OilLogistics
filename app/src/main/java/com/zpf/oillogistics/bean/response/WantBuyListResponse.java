package com.zpf.oillogistics.bean.response;

import com.google.gson.annotations.SerializedName;
import com.zpf.oillogistics.bean.WantBuy;

import java.util.List;

/**
 * Created by Administrator on 2017/9/20.
 */

public class WantBuyListResponse {


    /**
     * status : 0
     * msg : 数据请求成功
     * data : {"total":15,"page":1,"page_size":10,"all_page":2,"data":[{"id":26,"title":"好好","number":"866","name":"测试","address":"你们的话我","phone":"85656898","img":"/uploads/20171010/150763025459dc9cae9ddf3.jpg","intruduce":"发货厚此薄彼","time":1507630254,"is_user":1,"class":1,"price":"","status":1,"statuss":2},{"id":25,"title":"汽油","number":"13","name":"测试","address":"你们的话我","phone":"18712345678","img":"/uploads/20171010/150762939459dc9952a82e1.jpg","intruduce":"sdfdslf","time":1507629394,"is_user":1,"class":1,"price":"100","status":1,"statuss":2},{"id":24,"title":"那就","number":"22","name":"测试","address":"你们的话我","phone":"18235225405","img":"/uploads/20171010/150761969359dc736d8771d.jpg","intruduce":"看看咯哦哦哦","time":1507619693,"is_user":1,"class":1,"price":"","status":1,"statuss":2},{"id":23,"title":"发求购测试数据","number":"6666","name":"未全额去","address":"天津市河东区东新街道万州大道555号","phone":"18381331343","img":"/uploads/20171010/7da142bfb8c41669e1a532702ef89434.png","intruduce":"成长手册三","time":1507615695,"is_user":1,"class":1,"price":"","status":1,"statuss":2},{"id":22,"title":"发布求购测试数据","number":"77","name":"测试","address":"你们的话我","phone":"18582021644","img":"/uploads/20171010/150761475559dc6023e3fcc.png","intruduce":"的方式发生","time":1507614755,"is_user":1,"class":1,"price":"","status":1,"statuss":2},{"id":21,"title":"发布求购测试数据","number":"77","name":"测试","address":"你们的话我","phone":"18582021644","img":"/uploads/20171010/150761474359dc6017a2dfe.png","intruduce":"的方式发生","time":1507614743,"is_user":1,"class":1,"price":"","status":1,"statuss":2},{"id":20,"title":"都是撒大","number":"77","name":"测试","address":"你们的话我","phone":"18582021644","img":"/uploads/20171010/150761451759dc5f35bc0a6.png","intruduce":"的方式发生","time":1507614517,"is_user":1,"class":1,"price":"","status":1,"statuss":2},{"id":19,"title":"多大点事","number":"11","name":"未全额去","address":"黑龙江省大庆市红岗区","phone":"18381331343","img":"/uploads/20171009/d66cf61c237e2192a740c3d72ee1734d.png","intruduce":"服务费","time":1507517623,"is_user":2,"class":2,"price":"332","status":1,"statuss":2},{"id":18,"title":"看没看好吧","number":"22","name":"未全额去","address":"121952282","phone":"18381331343","img":"/uploads/20170930/2229200e92a7c20628d582f774a86eb8.png","intruduce":"的爽身粉","time":1506757010,"is_user":2,"class":1,"price":"4","status":1,"statuss":2},{"id":17,"title":"的说法都是","number":"22","name":"未全额去","address":"3741150","phone":"18381331343","img":"/uploads/20170930/0eb54e00d124efac1d887643ffca7bf4.png","intruduce":"工地上的","time":1506756897,"is_user":2,"class":1,"price":"33","status":1,"statuss":2}]}
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
         * total : 15
         * page : 1
         * page_size : 10
         * all_page : 2
         * data : [{"id":26,"title":"好好","number":"866","name":"测试","address":"你们的话我","phone":"85656898","img":"/uploads/20171010/150763025459dc9cae9ddf3.jpg","intruduce":"发货厚此薄彼","time":1507630254,"is_user":1,"class":1,"price":"","status":1,"statuss":2},{"id":25,"title":"汽油","number":"13","name":"测试","address":"你们的话我","phone":"18712345678","img":"/uploads/20171010/150762939459dc9952a82e1.jpg","intruduce":"sdfdslf","time":1507629394,"is_user":1,"class":1,"price":"100","status":1,"statuss":2},{"id":24,"title":"那就","number":"22","name":"测试","address":"你们的话我","phone":"18235225405","img":"/uploads/20171010/150761969359dc736d8771d.jpg","intruduce":"看看咯哦哦哦","time":1507619693,"is_user":1,"class":1,"price":"","status":1,"statuss":2},{"id":23,"title":"发求购测试数据","number":"6666","name":"未全额去","address":"天津市河东区东新街道万州大道555号","phone":"18381331343","img":"/uploads/20171010/7da142bfb8c41669e1a532702ef89434.png","intruduce":"成长手册三","time":1507615695,"is_user":1,"class":1,"price":"","status":1,"statuss":2},{"id":22,"title":"发布求购测试数据","number":"77","name":"测试","address":"你们的话我","phone":"18582021644","img":"/uploads/20171010/150761475559dc6023e3fcc.png","intruduce":"的方式发生","time":1507614755,"is_user":1,"class":1,"price":"","status":1,"statuss":2},{"id":21,"title":"发布求购测试数据","number":"77","name":"测试","address":"你们的话我","phone":"18582021644","img":"/uploads/20171010/150761474359dc6017a2dfe.png","intruduce":"的方式发生","time":1507614743,"is_user":1,"class":1,"price":"","status":1,"statuss":2},{"id":20,"title":"都是撒大","number":"77","name":"测试","address":"你们的话我","phone":"18582021644","img":"/uploads/20171010/150761451759dc5f35bc0a6.png","intruduce":"的方式发生","time":1507614517,"is_user":1,"class":1,"price":"","status":1,"statuss":2},{"id":19,"title":"多大点事","number":"11","name":"未全额去","address":"黑龙江省大庆市红岗区","phone":"18381331343","img":"/uploads/20171009/d66cf61c237e2192a740c3d72ee1734d.png","intruduce":"服务费","time":1507517623,"is_user":2,"class":2,"price":"332","status":1,"statuss":2},{"id":18,"title":"看没看好吧","number":"22","name":"未全额去","address":"121952282","phone":"18381331343","img":"/uploads/20170930/2229200e92a7c20628d582f774a86eb8.png","intruduce":"的爽身粉","time":1506757010,"is_user":2,"class":1,"price":"4","status":1,"statuss":2},{"id":17,"title":"的说法都是","number":"22","name":"未全额去","address":"3741150","phone":"18381331343","img":"/uploads/20170930/0eb54e00d124efac1d887643ffca7bf4.png","intruduce":"工地上的","time":1506756897,"is_user":2,"class":1,"price":"33","status":1,"statuss":2}]
         */

        private int total;
        private int page;
        private int page_size;
        private int all_page;
        private List<WantBuy> data;

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

        public List<WantBuy> getData() {
            return data;
        }

        public void setData(List<WantBuy> data) {
            this.data = data;
        }

    }
}
