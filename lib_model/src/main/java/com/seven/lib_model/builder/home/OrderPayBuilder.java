package com.seven.lib_model.builder.home;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2017/10/26
 */

public class OrderPayBuilder {

    private String type;
    private String order_sn;
    private String subject;

    public OrderPayBuilder(Builder builder) {
        this.type = builder.type;
        this.order_sn = builder.order_sn;
        this.subject = builder.subject;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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

    public static class Builder {

        private String type;
        private String order_sn;
        private String subject;

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder order_sn(String order_sn) {
            this.order_sn = order_sn;
            return this;
        }

        public Builder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public OrderPayBuilder build() {
            return new OrderPayBuilder(this);
        }
    }

}
