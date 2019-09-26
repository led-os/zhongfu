package com.seven.lib_model.builder.home;

import com.seven.lib_model.builder.common.PageBuilder;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/4/24
 */

public class CommodityListBuilder extends PageBuilder {

    private int sort;//1. 综合排序,2.销量升序,3.销量降序,4.价格升序,5.价格降序
    private String keyword;
    private int category;

    public CommodityListBuilder(Builder builder) {
        super(builder);
        this.sort=builder.sort;
        this.keyword=builder.keyword;
        this.category=builder.category;
    }

    public static class Builder extends PageBuilder.Builder {

        private int sort;//1. 综合排序,2.销量升序,3.销量降序,4.价格升序,5.价格降序
        private String keyword;
        private int category;

        public Builder sort(int sort) {
            this.sort = sort;
            return this;
        }

        public Builder keyword(String keyword) {
            this.keyword = keyword;
            return this;
        }

        public Builder category(int category) {
            this.category = category;
            return this;
        }

        public CommodityListBuilder build() {
            return new CommodityListBuilder(this);
        }
    }
}
