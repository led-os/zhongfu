package com.seven.lib_model.builder.common;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2017/10/26
 */

public class PayPasswordBuilder {

    private String pay_password;

    private PayPasswordBuilder(Builder builder) {
        this.pay_password = builder.pay_password;
    }

    public static class Builder {

        private String pay_password;

        public Builder pay_password(String pay_password) {
            this.pay_password = pay_password;
            return this;
        }

        public PayPasswordBuilder build() {
            return new PayPasswordBuilder(this);
        }
    }

}
