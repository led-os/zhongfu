package com.seven.lib_model.builder.user;

public class ConfirmOrderBuilder {

    public ConfirmOrderBuilder(Builder builder) {
        this.order_id = builder.order_id;
    }

    private int order_id;

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public static class Builder {
        private int order_id;

        public Builder order_id(int order_id){
            this.order_id = order_id;
            return this;
        }

        public ConfirmOrderBuilder build() {
            return new ConfirmOrderBuilder(this);
        }
    }
}
