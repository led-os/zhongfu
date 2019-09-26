package com.seven.lib_router.router;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2018/3/7
 */

public class RouterPath {

    public static final String SERVICE_JSON = "/service/JsonServiceImpl";

    /* module app */
    public static final String ACTIVITY_HOME = "/app/HomeActivity";
    public static final String ACTIVITY_WEB = "/app/WebActivity";
    public static final String ACTIVITY_ACTIVE="/app/ActiveActivity";


    /* module common */
//    public static final String ACTIVITY_COMMON_SELECT = "/common/CommonSelectActivity";


    /* module home */
    public static final String FRAGMENT_HOME = "/home/HomeFragment";
    public static final String ACTIVITY_COMMODITY="/home/CommodityListActivity";
    public static final String ACTIVITY_COMMODITY_DETAILS="/home/CommodityDetailsActivity";
    public static final String ACTIVITY_COMMODITY_ORDER="/home/CommodityOrderActivity";
    public static final String ACTIVITY_PAY="/home/PayActivity";

    /* module extension */
    public static final String FRAGMENT_EXTENSION = "/extension/ExtensionFragment";
    public static final String ACTIVITY_IN_COME = "/extension/IncomeActivity";
    public static final String ACTIVITY_BUY_ROLE = "/extension/BuyRoleActivity";
    public static final String ACTIVITY_BUY_BD = "/extension/BuyActivity";
    public static final String ACTIVITY_MY_INTERVIEW = "/extension/MyInterviewActivity";
    public static final String ACTIVITY_LEVEL = "/extension/LevelRuleActivity";
    public static final String ACTIVITY_REWARD_LIST = "/extension/ReceivAwardsActivity";
    public static final String ACTIVITY_BD_LIST = "/extension/ReceiveBDActivity";
    public static final String ACTIVITY_QUOTA = "/extension/QuotaActivity";
    public static final String ACTIVITY_BIND = "/extension/BindActivity";
    public static final String ACTIVITY_UP = "/extension/UpActivity";

    /* module model */
    public static final String FRAGMENT_MODEL = "/model/ModelFragment";
    public static final String ACTIVITY_RELEASE_DEMAND = "/model/ReleaseDemandActivity";
    public static final String ACTIVITY_MESSAGE= "/model/MessageActivity";
    public static final String ACTIVITY_VOUCHER= "/model/VoucherActivity";
    public static final String ACTIVITY_TRANSACTION_DETAILS= "/model/TransactionDetailsActivity";
    public static final String ACTIVITY_UPLOAD_VOUCHER= "/model/UploadVoucherActivity";

    /* module user */
    public static final String FRAGMENT_USER = "/user/UserFragment";

    public static final String ACTIVITY_LOGIN="/user/LoginActivity";
    public static final String ACTIVITY_REGISTER="/user/RegisterActivity";
    public static final String ACTIVITY_PASSWORD="/user/PasswordActivity";
    public static final String ACTIVITY_MOBILE="/user/MobileActivity";

    public static final String ACTIVITY_SHOPPING_CART="/user/UserShoppingCartActivity";//购物车
    public static final String ACTIVITY_ADDRESS="/user/AddressList";
    public static final String ACTIVITY_PAY_PASSWORD="/user/UserSetPayPassWordActivity";
    public static final String ACTIVITY_MINE_ORDER="/user/UserOrderListActivity";//我的订单
    public static final String ACTIVITY_MINE_SHOP_CAR="/user/ShopCartActivity";
    public static final String ACTIVITY_MINE_SHOP_PAY="/user/PayActivity";
    public static final String ACTIVITY_MINE_ADD_ADDRESS="/user/CreateAddress";
    public static final String ACTIVITY_ACCOUNT="/user/UserSetPayAccountActivity";
    public static final String ACTIVITY_LOGISTICS="/user/LogisticsActivity";
    public static final String ACTIVITY_TOKEN="/user/UserTokenListActivity";
    public static final String ACTIVITY_CUSTOMER_SERVICE="/user/CustomerServiceActivity";

}
