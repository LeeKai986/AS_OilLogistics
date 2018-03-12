package com.zpf.oillogistics.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/10/23.
 * <p>
 * 首页系统消息
 */

public class MsgHomeSystemBean {

    /**
     * status : 0
     * msg : 请求数据成功
     * data : {"total":193,"page":"1","page_size":10,"all_page":20,"data":[{"id":9,"alias":"18888888888","title":"测试订购成功","content":"赶紧去查看下单详情吧!~ ","time":null},{"id":10,"alias":"18888888888","title":"发布产品测试数据订购成功","content":"赶紧去查看下单详情吧!~ ","time":null},{"id":11,"alias":"18888888888","title":"发布产品测试数据订购成功","content":"赶紧去查看下单详情吧!~ ","time":null},{"id":12,"alias":"18888888888","title":"发布产品测试数据订购成功","content":"赶紧去查看下单详情吧!~ ","time":null},{"id":39,"alias":"18888888888","title":"关注成功","content":"您关注.成功,该公司发布产品的时候会给你发消息，让您了解最新动态","time":null},{"id":43,"alias":"18888888888","title":"关注成功","content":"您关注测试.成功,该公司发布产品的时候会给你发消息，让您了解最新动态","time":null},{"id":44,"alias":"18888888888","title":"关注成功","content":"您关注.成功,该公司发布报价的时候会给你发消息，让您了解最新动态","time":null},{"id":45,"alias":"18888888888","title":"的说法都是订购成功","content":"赶紧去查看下单详情吧!~ ","time":null},{"id":46,"alias":"18581331339,13008169605,18888888888","title":"您关注的测试有新的新产品发布","content":"参加赶紧去查看详情吧!~ ","time":1508232868},{"id":50,"alias":"18381079491,13008169605,18888888888","title":"您关注的有新的新产品发布","content":"科技赶紧去查看详情吧!~ ","time":1508309381}]}
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
         * total : 193
         * page : 1
         * page_size : 10
         * all_page : 20
         * data : [{"id":9,"alias":"18888888888","title":"测试订购成功","content":"赶紧去查看下单详情吧!~ ","time":null},{"id":10,"alias":"18888888888","title":"发布产品测试数据订购成功","content":"赶紧去查看下单详情吧!~ ","time":null},{"id":11,"alias":"18888888888","title":"发布产品测试数据订购成功","content":"赶紧去查看下单详情吧!~ ","time":null},{"id":12,"alias":"18888888888","title":"发布产品测试数据订购成功","content":"赶紧去查看下单详情吧!~ ","time":null},{"id":39,"alias":"18888888888","title":"关注成功","content":"您关注.成功,该公司发布产品的时候会给你发消息，让您了解最新动态","time":null},{"id":43,"alias":"18888888888","title":"关注成功","content":"您关注测试.成功,该公司发布产品的时候会给你发消息，让您了解最新动态","time":null},{"id":44,"alias":"18888888888","title":"关注成功","content":"您关注.成功,该公司发布报价的时候会给你发消息，让您了解最新动态","time":null},{"id":45,"alias":"18888888888","title":"的说法都是订购成功","content":"赶紧去查看下单详情吧!~ ","time":null},{"id":46,"alias":"18581331339,13008169605,18888888888","title":"您关注的测试有新的新产品发布","content":"参加赶紧去查看详情吧!~ ","time":1508232868},{"id":50,"alias":"18381079491,13008169605,18888888888","title":"您关注的有新的新产品发布","content":"科技赶紧去查看详情吧!~ ","time":1508309381}]
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
             * id : 9
             * alias : 18888888888
             * title : 测试订购成功
             * content : 赶紧去查看下单详情吧!~
             * time : null
             */

            private String id;
            private String alias;
            private String title;
            private String content;
            private String time;
            private String order;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getAlias() {
                return alias;
            }

            public void setAlias(String alias) {
                this.alias = alias;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getOrder() {
                return order;
            }

            public void setOrder(String order) {
                this.order = order;
            }
        }
    }
}
