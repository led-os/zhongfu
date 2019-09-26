package com.seven.lib_common.utils;


import com.seven.lib_opensource.application.SSDK;
import com.seven.lib_opensource.application.SevenApplication;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2017/11/3
 */
public class ResourceUtils {


    /**
     * @param id
     * @return
     */
    public static String getText(int id) {

        return SSDK.getInstance().getContext().getResources().getString(id);

    }

    /**
     * @param id
     * @param args
     * @return
     */
    public static String getFormatText(int id, Object... args) {
        return String.format(SSDK.getInstance().getContext().getResources().getString(id), args);
    }

//    /**
//     * @param money
//     * @return
//     */
//    public String getMoneyFormat(double money) {
//
//        if (money == 0)
//            return "";
//
//        if (money > 9999) {
//            return new DecimalFormat("#0.00").format(money / 10000) + "万";
//
//        } else {
//            return new DecimalFormat("#0.00").format(money);
//        }
//    }

//    public String getWeek(int index) {
//
//        switchs (index) {
//
//            case 0:
//                return getText(R.string.weekday);
//            case 1:
//                return getText(R.string.monday);
//            case 2:
//                return getText(R.string.tuesday);
//            case 3:
//                return getText(R.string.wednesday);
//            case 4:
//                return getText(R.string.thursday);
//            case 5:
//                return getText(R.string.friday);
//            case 6:
//                return getText(R.string.saturday);
//
//        }
//
//        return "";
//    }
//
//    public String getSex(int sex) {
//
//        switchs (sex) {
//            case 1:
//                return getText(R.string.sex_man);
//            case 2:
//                return getText(R.string.sex_woman);
//            default:
//                return "";
//        }
//    }

}
