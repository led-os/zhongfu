package com.seven.lib_model.builder.home;

import java.util.List;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2017/10/26
 */

public class OrderAddBuilder {

    private int contact_id;
    private String from;
    private String from_ext;
    private List<GoodsListBean> goods_list;

    public OrderAddBuilder(Builder builder) {
        this.contact_id = builder.contact_id;
        this.from = builder.from;
        this.from_ext = builder.from_ext;
        this.goods_list = builder.goods_list;
    }

    public int getContact_id() {
        return contact_id;
    }

    public void setContact_id(int contact_id) {
        this.contact_id = contact_id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getFrom_ext() {
        return from_ext;
    }

    public void setFrom_ext(String from_ext) {
        this.from_ext = from_ext;
    }

    public List<GoodsListBean> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<GoodsListBean> goods_list) {
        this.goods_list = goods_list;
    }

    public static class GoodsListBean {
        private int goods_id;
        private int sku_id;
        private int number;

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
    }


    public static class Builder {

        private int contact_id;
        private String from;
        private String from_ext;
        private List<GoodsListBean> goods_list;

        public Builder contact_id(int contact_id) {
            this.contact_id = contact_id;
            return this;
        }

        public Builder from(String from) {
            this.from = from;
            return this;
        }

        public Builder from_ext(String from_ext) {
            this.from_ext = from_ext;
            return this;
        }

        public Builder addList(List<GoodsListBean> goods_list) {
            this.goods_list=goods_list;
            return this;
        }

        public OrderAddBuilder build() {
            return new OrderAddBuilder(this);
        }
    }

}
