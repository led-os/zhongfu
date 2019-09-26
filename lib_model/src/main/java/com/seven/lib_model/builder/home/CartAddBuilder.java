package com.seven.lib_model.builder.home;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2017/10/26
 */

public class CartAddBuilder {

    private int goods_id;
    private int goods_sku_id;
    private int number;

    public CartAddBuilder(Builder builder) {
        this.goods_id = builder.goods_id;
        this.goods_sku_id = builder.goods_sku_id;
        this.number = builder.number;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public int getGoods_sku_id() {
        return goods_sku_id;
    }

    public void setGoods_sku_id(int goods_sku_id) {
        this.goods_sku_id = goods_sku_id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public static class Builder {

        private int goods_id;
        private int goods_sku_id;
        private int number;

        public Builder goods_id(int goods_id) {
            this.goods_id = goods_id;
            return this;
        }

        public Builder goods_sku_id(int goods_sku_id) {
            this.goods_sku_id = goods_sku_id;
            return this;
        }

        public Builder number(int number) {
            this.number = number;
            return this;
        }

        public CartAddBuilder build() {
            return new CartAddBuilder(this);
        }
    }

}
