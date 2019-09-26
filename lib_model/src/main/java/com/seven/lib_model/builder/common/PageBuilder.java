package com.seven.lib_model.builder.common;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2017/10/26
 */

public class PageBuilder {

    private int page;
    private int page_size;

    public PageBuilder(Builder builder) {
        this.page = builder.page;
        this.page_size = builder.page_size;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPage_size() {
        return page_size;
    }

    public void setPage_size(int page_size) {
        this.page_size = page_size;
    }

    public static class Builder {

        private int page;
        private int page_size;

        public Builder page(int page) {
            this.page = page;
            return this;
        }

        public Builder page_size(int page_size) {
            this.page_size = page_size;
            return this;
        }

        public PageBuilder build() {
            return new PageBuilder(this);
        }
    }

}
