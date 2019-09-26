package com.seven.lib_model.model.user.mine;

/**
 * Created by xxxxxxH on 2019/4/25.
 */

public class OrderDetailRequestEntity {
    private int order_id;

    public OrderDetailRequestEntity(int order_id) {
        this.order_id = order_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }
}
