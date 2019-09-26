package com.seven.lib_model.builder.model;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/5/5
 */

public class BusinessDetailsBuilder {

    private int business_id;

    public BusinessDetailsBuilder(Builder builder) {
        this.business_id = builder.business_id;
    }

    public static class Builder {

        private int business_id;

        public Builder business_id(int business_id) {
            this.business_id = business_id;
            return this;
        }

        public BusinessDetailsBuilder build() {
            return new BusinessDetailsBuilder(this);
        }

    }

}
