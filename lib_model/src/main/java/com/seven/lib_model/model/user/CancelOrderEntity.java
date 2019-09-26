package com.seven.lib_model.model.user;

/**
 * Created by xxxxxxH on 2019/5/15.
 */
public class CancelOrderEntity {

    private int order_id;
    private String comment;

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
