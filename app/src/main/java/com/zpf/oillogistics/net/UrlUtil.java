package com.zpf.oillogistics.net;

/**
 * Created by Administrator on 2017/7/26.
 * <p>
 * 网络接口
 */

public class UrlUtil {

    // 本地
//    private static final String URL = "http://192.168.12.248/index.php/api/";
//
//    // 测试服
//    public static final String URL = "http://yuanyoutong.zpftech.com/api";
//    // 测试服image
//    public static final String IMAGE_URL = "http://yuanyoutong.zpftech.com";
//    //  分享地址
//    public static final String SHARE_URL = "http://yuanyoutong.zpftech.com/api/user/share";


//    // 正式
//    public static final String URL = "http://www.shihuatong66.com/api";
//    // 正式服image
//    public static final String IMAGE_URL = "http://img-ali-hb1.shihuatong66.com";
//    // 正式分享地址
//    public static final String SHARE_URL = "http://www.shihuatong66.com/api/user/share";


    // 测试服
    public static final String URL = "http://Tozpa.com/api";
    // 测试服image
    public static final String IMAGE_URL = "http://img.yyt.tozpa.com";
    // 分享地址
    public static final String SHARE_URL = "http://Tozpa.com/api/user/share";

    // 首页数据接口
    public static final String HOME_DATA = "http://info.oilchem.net/ciuderoil/queryCrudeOil?username=dongmingjy&pwd=20171121";


    //车找货和匹配接口
    public static final String URL_FREIGHT_CAR = URL + "/Freight/car";
    //车找货下单接口
    public static final String URL_FREIGHT_ORDER = URL + "/Freight/order";
    //车找货下单保存接口
    public static final String URL_ORDER_ADDCAR = URL + "/Order/addCar";
    public static final String LOGIN_REGISTER = URL + "/login/register";
    // 验证码获取
    public static final String LOGIN_SEND_CODE = URL + "/login/sendCode";
    // 找回密码
    public static final String LOGIN_FINDPWD = URL + "/login/findPwd";
    // 登陆
    public static final String LOGIN_LOGIN = URL + "/login/login";  // 登陆
    public static final String AGREEMENT = URL + "/about/agreement";


    /**
     * 主页
     */
    //轮播和资讯接口
    public static final String URL_SLIDESHOW_INDEX = URL + "/Slideshow/index";
    //主页搜索接口
    public static final String URL_TOUBUY_SEARCH = URL + "/Tobuy/search";
    //主页原油价格走势
    public static final String URL_PRICE_TREND = URL + "/price_trend/latest";
    //求购搜索接口
    public static final String URL_TOUBUY_TOBUYSEARCH = URL + "/Tobuy/tobuysearch";
    //货找车和匹配接口
    public static final String URL_RUN_FREIGHT = URL + "/run/freight";
    //产品分类接口
    public static final String URL_PRODUCT_GOODSCLASS = URL + "/Product/goodsClass";
    //发产品接口
    public static final String URL_PLUSSIGN_TOGOODS = URL + "/Plussign/togoods";
    //发求购接口
    public static final String URL_PLUSSIGN_TOBUY = URL + "/Plussign/tobuy";
    //发货运接口
    public static final String URL_PLUSSIGN_FREIGHT = URL + "/Plussign/freight";
    //订购详情
    public static final String URL_ORDER_ORDER = URL + "/Order/order";
    //生成订单
    public static final String URL_ORDER_GENERATE = URL + "/Order/generateOrder";
    //资讯列表
    public static final String URL_SLIDESHOW_NEWS = URL + "/Slideshow/news";
    //支付
    public static final String URL_ORDER_PAY = URL + "/Order/pay";
    //支付成功
    public static final String URL_ORDER_OVER = URL + "/Order/orderover";
    /**
     * 主页信息(搜索栏旁)
     */
    // 关注的商品信息
    public static final String USER_FOLLOW = URL + "/user/follow";
    // 取消关注的商品信息或报价
    public static final String USER_FOLLOW_CANCEL = URL + "/user/cancelfollow";
    // 关注的报价信息
    public static final String USER_OFFER = URL + "/user/offer";
    // 系统消息
    public static final String USER_SYSTEM = URL + "/user/system";
    // 删除系统消息
    public static final String USER_DELS = URL + "/user/dels";
    /**
     * 资讯详情接口
     */
    //资讯详情
    public static final String URL_SLIDESHOW_INFO = URL + "/Slideshow/info";
    //资讯关注
    public static final String URL_FOLLOW_FOLLOW = URL + "/Follow/follow";
    // Excel下载
    public static final String OFFER_DOWNLOAD = URL + "offer/download";
    /**
     * 获取求购信息列表接口
     */
//    public static final String URL_TOBUY_INDEX = URL + "/Tobuy/index";
    // 求购筛选
    public static final String TOBUY_SCREEN = URL + "/Tobuy/screen";
    // 求购筛选
    public static final String TOBUY_SEARCH = URL + "/Tobuy/search";
    // 石油&&化工类列表
    public static final String TOGOODS_INDEX = URL + "/Togoods/index";
    // 司机详情
    public static final String RUN_DRIVERINFO = URL + "/Run/driverInfo";
    // 求购详情
    public static final String TOBUY_TOBUYINFO = URL + "/Tobuy/tobuyinfo";
    // 石油化工类详情
    public static final String TOGOODS_TOGOODS_INFO = URL + "/Togoods/togoodsInfo";

    /**
     * 报价接口
     */
    public static final String OFFER_INDEX = URL + "/offer/index";
    //关注报价
    public static final String URL_FOLLOW_OFFER = URL + "/Follow/offer";

    /**
     * 聊天接口
     */
    public static final String URL_USER_NICK = URL + "/user/nick";
    public static final String URL_FRIENDSLIST = URL + "/friends/get";
    /**
     * 个人中心
     */
    //修改信息
    public static final String URL_USER_PERSONAL = URL + "/user/personal";
    //我的产品
    public static final String URL_USER_PRODUCTS = URL + "/user/products";
    //删除我的产品
    public static final String URL_USER_DEL = URL + "/user/del";
    //我的求购
    public static final String URL_USER_BUY = URL + "/user/buy";
    //删除我的求购
    public static final String URL_USER_DELBUY = URL + "/user/delbuy";
    //我的关注
    public static final String URL_USER_FOLLOW = URL + "/user/follow";
    //我的资源
    public static final String URL_FREIGT_SUPPLY = URL + "/Freight/supply";
    //我的资源删除
    public static final String URL_FREIGT_DELSUPPLY = URL + "/Freight/delsupply";
    //反馈
    public static final String URL_FEEDBACK_FEEDBACK = URL + "/feedback/feedback";
    //反馈列表
    public static final String URL_FEEDBACK_BACKD = URL + "/feedback/backd";
    //帮助列表
    public static final String URL_HELP_INDEX = URL + "/Help/index";
    //我的订单
    public static final String URL_ORDER_MYORDER = URL + "/Order/myorder";
    //司机身份认证
    public static final String URL_DRIVIER_IDENTITY = URL + "/Driver/identity";
    // 司机发布的行程
    public static final String URL_DRIVIER_TRIP = URL + "/Driver/trip";
    // 司机发布行程
    public static final String URL_PLUSSIGN_RUN = URL + "/Plussign/run";
    // 公司取消订单
    public static final String URL_ORDER_CANCEL = URL + "/Order/cancel";
    // 公司删除订单
    public static final String URL_ORDER_DELETE = URL + "/Order/del";
    // 公司提交
    public static final String URL_ORDER_TAKE = URL + "/Order/take";
    // 公司订单详情
    public static final String URL_ORDER_ORDERINFOR = URL + "/Order/orderInfor";
    // 司机订单
    public static final String URL_DRIVER_MYORDER = URL + "/Driver/myorder";
    // 司机确认提货
    public static final String URL_DRIVER_TAKE = URL + "/Driver/take";
    // 司机详情
    public static final String URL_DRIVER_INFO = URL + "/Driver/info";
    // 司机确认到达
    public static final String URL_DRIVER_ARRIVE = URL + "/Driver/arrive";
    // 关于我们
    public static final String URL_ABOUT_INDEX = URL + "/About/index";

    // 获取后台设置的平台订单运送时间
    public static final String URL_ORDER_SHIP_TIME = URL + "/togoods/service";

    // 关注数量
    public static final String URL_USER_NUM = URL + "/user/num";

    // 上传司机位置
    public static final String URL_USER_LOCA = URL + "/user/position";

    // 查询好友详情
    public static final String USER_NICKNAME = URL + "/user/nickname";

}
