package com.seven.lib_model.builder.home;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/4/23
 */

public class OrderPaymentBuilder {

    private String from;
    private String cart_ids;
    private int goods_id;
    private int sku_id;
    private int number;

    public OrderPaymentBuilder(Builder builder) {
        this.from=builder.from;
        this.cart_ids=builder.cart_ids;
        this.goods_id=builder.goods_id;
        this.sku_id=builder.sku_id;
        this.number=builder.number;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public int getSku_id() {
        return sku_id;
    }

    public void setSku_id(int sku_id) {
        this.sku_id = sku_id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getCart_ids() {
        return cart_ids;
    }

    public void setCart_ids(String cart_ids) {
        this.cart_ids = cart_ids;
    }

    public static class Builder {

        private String from;
        private String cart_ids;
        private int goods_id;
        private int sku_id;
        private int number;

        public Builder from(String from) {
            this.from = from;
            return this;
        }

        public Builder cart_ids(String cart_ids) {
            this.cart_ids = cart_ids;
            return this;
        }

        public Builder goods_id(int goods_id) {
            this.goods_id = goods_id;
            return this;
        }

        public Builder sku_id(int sku_id) {
            this.sku_id = sku_id;
            return this;
        }

        public Builder number(int number) {
            this.number = number;
            return this;
        }

        public OrderPaymentBuilder build() {
            return new OrderPaymentBuilder(this);
        }
    }
}
