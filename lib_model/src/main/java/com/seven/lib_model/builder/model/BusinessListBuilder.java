package com.seven.lib_model.builder.model;

import com.seven.lib_model.builder.common.PageBuilder;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/5/5
 */

public class BusinessListBuilder extends PageBuilder {

    private int type;//交易类型(1.我要卖,2.我要买)
    private int sort;//排序(0.综合,1.时间, 2. 价格)

    public BusinessListBuilder(Builder builder) {
        super(builder);
        this.type = builder.type;
        this.sort = builder.sort;
    }

    public static class Builder extends PageBuilder.Builder {
        private int type;//交易类型(1.我要卖,2.我要买)
        private int sort;//排序(0.综合,1.时间, 2. 价格)

        public Builder type(int type) {
            this.type = type;
            return this;
        }

        public Builder sort(int sort) {
            this.sort = sort;
            return this;
        }

        public BusinessListBuilder build() {
            return new BusinessListBuilder(this);
        }
    }
}
