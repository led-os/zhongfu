package com.seven.lib_model.model.home;

import java.io.Serializable;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/4/23
 */

public class OrderEntity implements Serializable {

    /**
     * order_sn : 15558336741994
     * subject : 七匹狼等1件商品
     * total : 398
     * token_price : 398
     */

    private String order_sn;
    private String subject;
    private double total;
    private double token_price;

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getToken_price() {
        return token_price;
    }

    public void setToken_price(double token_price) {
        this.token_price = token_price;
    }
}
