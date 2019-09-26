package com.seven.lib_model.builder.extension;

/**
 * Created by xxxxxxH on 2019/5/3.
 */
public class InviteBuilder {
    private int user_id;
    private int page;
    private int page_size;

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

    public InviteBuilder(Builder builder) {
        this.user_id = builder.user_id;
        this.page = builder.page;
        this.page_size = builder.page_size;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public static class Builder {
        private int user_id;
        private int page;
        private int page_size;

        public Builder user_id(int user_id) {
            this.user_id = user_id;
            return this;
        }

        public Builder page(int page) {
            this.page = page;
            return this;
        }

        public Builder page_size(int page_size) {
            this.page_size = page_size;
            return this;
        }

        public InviteBuilder build() {
            return new InviteBuilder(this);
        }
    }
}
