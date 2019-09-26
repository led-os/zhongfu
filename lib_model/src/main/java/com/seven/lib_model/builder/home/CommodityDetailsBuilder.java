package com.seven.lib_model.builder.home;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2017/10/26
 */

public class CommodityDetailsBuilder {

    private int goods_id;

    public CommodityDetailsBuilder(Builder builder) {
        this.goods_id = builder.goods_id;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public static class Builder {

        private int goods_id;

        public Builder goods_id(int goods_id) {
            this.goods_id = goods_id;
            return this;
        }

        public CommodityDetailsBuilder build() {
            return new CommodityDetailsBuilder(this);
        }
    }

}
