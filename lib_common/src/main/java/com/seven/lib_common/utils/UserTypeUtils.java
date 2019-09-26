package com.seven.lib_common.utils;

import com.seven.lib_common.R;
import com.seven.lib_router.Constants;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/6/5
 */

public class UserTypeUtils {

    public static int userTypeImage(int type) {

        switch (type) {

            case Constants.InterfaceConfig.USER_TYPE_VIP:
                return R.drawable.lv_1;

            case Constants.InterfaceConfig.USER_TYPE_MINERS:
                return R.drawable.lv_2;

            case Constants.InterfaceConfig.USER_TYPE_FARM:
                return R.drawable.lv_3;

            case Constants.InterfaceConfig.USER_TYPE_LORD:
                return R.drawable.lv_4;

            default:
                return R.drawable.lv_0;
        }

    }

}
