package com.seven.lib_model.builder.model;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/5/5
 */

public class BusinessBuilder {
    private int type;//交易类型(1. 购买交易, 2.售卖交易)
    private double token_number;
    private double price;
    private String wx_account;
    private String ali_account;

    public BusinessBuilder(Builder builder) {
        this.type = builder.type;
        this.token_number=builder.token_number;
        this.price=builder.price;
        this.wx_account=builder.wx_account;
        this.ali_account=builder.ali_account;
    }

    public static class Builder {

        private int type;//交易类型(1. 购买交易, 2.售卖交易)
        private double token_number;
        private double price;
        private String wx_account;
        private String ali_account;

        public Builder type(int type) {
            this.type = type;
            return this;
        }

        public Builder token_number(double token_number) {
            this.token_number = token_number;
            return this;
        }

        public Builder price(double price) {
            this.price = price;
            return this;
        }

        public Builder wx_account(String wx_account) {
            this.wx_account = wx_account;
            return this;
        }

        public Builder ali_account(String ali_account) {
            this.ali_account = ali_account;
            return this;
        }

        public BusinessBuilder build() {
            return new BusinessBuilder(this);
        }

    }

}
