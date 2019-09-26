package com.seven.lib_common.utils;

import java.text.DecimalFormat;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/4/14
 */

public class FormatUtils {

    public static String formatCurrencyD(double price){
        return new DecimalFormat("0.00").format(price);
    }
}
