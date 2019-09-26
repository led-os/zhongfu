package com.seven.lib_router;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/3/12
 */

public class Constants {

    public static class SharedConfig {

        public static final String SHARED_NAME = "seven";

        public static final String FIRST = "first";
        public static final String TOKEN = "token";
        public static final String USER_INFO = "user_info";
    }

    public static class BundleConfig {
        public static final String ENTITY = "entity";

        public static final String FLOW = "flow";
        public static final int FLOW_ENTRANCE = 1;
        public static final int FLOW_SEARCH = 2;

        public static final String TYPE = "type";
        public static final int TYPE_SELL = 1;
        public static final int TYPE_BUY = 2;

        public static final String ID = "id";
        public static final String DETAILS = "details";
        public static final String STATUS = "status";
        public static final String URL = "url";
        public static final String NORMAL = "normal";

        public static final String EVENT_CODE = "eventCode";
        public static final String BUY_BD = "buy_bd";
    }

    public static class TimeConfig {

        public static final int SPLASH_TIME = 2000;

        public static final int TRANS_TIME = 300;
        public static final int TRANS_TIME_LONG = 750;

        public static final int ROTATION_TIME = 300;
        public static final int ROTATION_TIME_LONG = 1000;

    }

    public static class AnimName {

        public static final String TRANS_X = "translationX";
        public static final String TRANS_Y = "translationY";
        public static final String ALPHA = "alpha";
        public static final String ROTATION = "rotation";
    }

    public static class RequestConfig {

        /* common */
        public static final int SMS = 1001;
        public static final int PAY_PASSWORD = 1002;
        public static final int UPLOAD = 1003;
        public static final int PUSH_ID = 1004;
        public static final int DELETE_PUSH_ID = 1005;
        public static final int MESSAGE_NOT_READ = 1006;
        public static final int MESSAGE_LIST = 1007;
        public static final int MESSAGE_READ = 1008;
        public static final int UPDATE = 1009;

        /*home*/
        public static final int BANNER = 3001;
        public static final int ENTRANCE = 3002;
        public static final int COMMODITY_RECOMMEND_LIST = 3003;
        public static final int COMMODITY_DETAILS = 3004;
        public static final int COMMODITY_LIST = 3005;

        public static final int CART_TOTAL = 3006;
        public static final int CART_ADD = 3007;

        public static final int ORDER_PAYMENT = 3008;
        public static final int ORDER_ADD = 3009;
        public static final int ORDER_PAY = 3010;

        public static final int CONTACT_DEFAULT = 3011;

        public static final int COLLECT = 3012;

        /*extension*/
        public static final int INCOME_COMMODITY_LIST = 4001;

        /* model */
        public static final int LOOPER_MESSAGE = 5001;
        public static final int BUSINESS_LIST = 5002;
        public static final int BUSINESS = 5003;
        public static final int BUSINESS_ORDER_LIST = 5004;
        public static final int BUSINESS_INFO = 5005;
        public static final int BUSINESS_ORDER_INFO = 5006;
        public static final int BUSINESS_ACCEPT = 5007;
        public static final int BUSINESS_PROOF = 5008;
        public static final int BUSINESS_CONFIRM = 5009;
        public static final int BUSINESS_CANCEL = 5010;
        public static final int BUSINESS_TOTAL = 5011;

        /*user*/
        public static final int REGISTER = 6001;
        public static final int LOGIN = 6002;
        public static final int PASSWORD = 6003;

        public static final int USER_INFO = 300;
    }

    public static class EventConfig {

        public static final int REGISTER = 100;
        public static final int LOGIN = 101;
        public static final int LOGOUT = 102;
        public static final int BUY_BD = 103;

        public static final int MESSAGE_READ = 110;
        public static final int MESSAGE_READ_POINT = 111;

        public static final int BUSINESS_PROOF = 200;

        /* router */
        public static final int SHOPPING_CART = 900;
        public static final int ADDRESS = 901;
        public static final int USER_DATA_CHANGE = 104;

        /* 第三方sdk */
        public static final int PAY_RESULT = 1000;


    }

    public static class ResourceConfig {

    }

    public static class ShareConfig {

//        public static final String PRODUCTION = "http://mobile.zf.fqwlkj.com.cn/goods_info.html";
//        public static final String LOGISTICS = "http://mobile.zf.fqwlkj.com.cn/express.html";
//        public static final String INVITE = "http://mobile.zf.fqwlkj.com.cn/invite.html";

        public static final String PRODUCTION = "http://www.qingyan.vrplay2018.com/goods_info.html";
        public static final String LOGISTICS = "http://www.qingyan.vrplay2018.com/express.html";
        public static final String INVITE = "http://www.qingyan.vrplay2018.com/invite.html";
    }

    public static class PayConfig {

        public static final int PAY_ALI = 1;//支付宝
        public static final int PAY_WX = 2;//微信

        /**
         * 支付来源
         */
        public static final int STORE = 1;
        public static final int APP = 2;
        public static final int H5 = 3;

        /**
         * 支付方式
         */
        public static final String CASH = "cash";
        public static final String ALIPAY = "alipay";
        public static final String WECHAT = "wechat";
        public static final String CASH_CARD = "cashCard";

        /**
         * order type
         */
        public static final int MEMBER = 3;//会员卡

        /**
         * activity type
         */
        public static final int DISCOUNT = 1;//折扣
        public static final int DIRECT_REDUCTION = 2;//直减
    }

    public static class MediaConfig {

        public static final String TYPE_VIDEO_MP4 = "video/mp4";
        public static final String TYPE_IMAGE_PNG = "image/png";
        public static final String TYPE_IMAGE_JPG = "image/jpeg";

    }

    public static class PermissionConfig {

        public static final int SPLASH = 1;

    }

    public static class SMSConfig {
        public static final String REGISTER = "register";
        public static final String PASSWORD = "password";
        public static final String PAY_PASSWORD = "pay_password";
    }

    public static class JpushConfig {

        public static final int ALIAS_CODE = 100;

    }

    public static class InterfaceConfig {

        public static final String CART = "cart";//购物车购买
        public static final String GOODS_DETAILS = "goods_detail";//商品详情购买

        public static final String PAY_ALI = "alipay";//支付宝支付
        public static final String PAY_WX = "wxpay";//微信支付
        public static final String PAY_APP = "tokenPay";//令牌支付

        public static final int SORT_COMPREHENSIVE = 1;//综合排序
        public static final int SORT_SALES_RISE = 2;//销量升序
        public static final int SORT_SALES_DROP = 3;//销量降序
        public static final int SORT_PRICE_RISE = 4;//价格升序
        public static final int SORT_PRICE_DROP = 5;//价格降序

        public static final int BUSINESS_TYPE_SALE = 1;//我要卖
        public static final int BUSINESS_TYPE_BUY = 2;//我要买

        public static final int BUSINESS_SORT_NORMAL = 0;//综合
        public static final int BUSINESS_SORT_TIME = 1;//时间
        public static final int BUSINESS_SORT_PRICE = 2;//价格

        public static final int BUSINESS_BUY = 1;//购买交易
        public static final int BUSINESS_SALE = 2;//售卖交易

        public static final int BUSINESS_STATUS_ALL = 0;//全部
        public static final int BUSINESS_STATUS_BUSINESS = 1;//待交易
        public static final int BUSINESS_STATUS_UPLOAD = 2;//待上传凭证
        public static final int BUSINESS_STATUS_SURE = 3;//待确认
        public static final int BUSINESS_STATUS_END = 4;//已完成

        public static final String UPLOAD_AVATAR = "avatar";

        public static final int USER_TYPE_NORMAL = 0;//role;// 0.普通会员, 1.vip, 2.矿主, 3.场主, 4.城主
        public static final int USER_TYPE_VIP = 1;
        public static final int USER_TYPE_MINERS = 2;
        public static final int USER_TYPE_FARM = 3;
        public static final int USER_TYPE_LORD = 4;
    }

}
