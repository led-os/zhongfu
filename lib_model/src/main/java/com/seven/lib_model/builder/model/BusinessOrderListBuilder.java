package com.seven.lib_model.builder.model;

import com.seven.lib_model.builder.common.PageBuilder;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/5/5
 */

public class BusinessOrderListBuilder extends PageBuilder {

    private int type;//1.购买交易,2.售出交易,
    private int status;//状态(1.等待,2.待上传凭证,3.待确认,4.已完成)

    public BusinessOrderListBuilder(Builder builder) {
        super(builder);
        this.type = builder.type;
        this.status = builder.status;
    }

    public static class Builder extends PageBuilder.Builder {
        private int type;//1.购买交易,2.售出交易,
        private int status;//状态(1.等待,2.待上传凭证,3.待确认,4.已完成)

        public Builder type(int type) {
            this.type = type;
            return this;
        }

        public Builder status(int status) {
            this.status = status;
            return this;
        }

        public BusinessOrderListBuilder build() {
            return new BusinessOrderListBuilder(this);
        }
    }
}
