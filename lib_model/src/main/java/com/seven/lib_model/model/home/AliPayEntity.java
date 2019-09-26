package com.seven.lib_model.model.home;


import java.io.Serializable;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/4/23
 */

public class AliPayEntity implements Serializable {

    private String pay_info;

    public String getPay_info() {
        return pay_info;
    }

    public void setPay_info(String pay_info) {
        this.pay_info = pay_info;
    }
}
