package com.zpf.oillogistics.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/20.
 * <p>
 * 本地省市解析
 */

public class ProviceAndCityBean {

    /**
     * pro : 北京市
     * proid : 1
     * citys : [{"cname":"东城区","cid":37},{"cname":"西城区","cid":38},{"cname":"崇文区","cid":39},{"cname":"宣武区","cid":40},{"cname":"朝阳区","cid":41},{"cname":"丰台区","cid":42},{"cname":"石景山区","cid":43},{"cname":"海淀区","cid":44},{"cname":"门头沟区","cid":45},{"cname":"房山区","cid":46},{"cname":"通州区","cid":47},{"cname":"顺义区","cid":48},{"cname":"昌平区","cid":49},{"cname":"大兴区","cid":50},{"cname":"怀柔区","cid":51},{"cname":"平谷区","cid":52},{"cname":"密云县","cid":53},{"cname":"延庆县","cid":54}]
     */

    private String pro;
    private int proid;
    private List<CitysBean> citys;

    public String getPro() {
        return pro;
    }

    public void setPro(String pro) {
        this.pro = pro;
    }

    public int getProid() {
        return proid;
    }

    public void setProid(int proid) {
        this.proid = proid;
    }

    public List<CitysBean> getCitys() {
        return citys;
    }

    public void setCitys(List<CitysBean> citys) {
        this.citys = citys;
    }

    public static class CitysBean {
        /**
         * cname : 东城区
         * cid : 37
         */

        private String cname;
        private int cid;

        public String getCname() {
            return cname;
        }

        public void setCname(String cname) {
            this.cname = cname;
        }

        public int getCid() {
            return cid;
        }

        public void setCid(int cid) {
            this.cid = cid;
        }
    }
}
