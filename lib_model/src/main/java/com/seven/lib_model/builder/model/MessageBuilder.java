package com.seven.lib_model.builder.model;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/5/5
 */

public class MessageBuilder {

    private int id;

    public MessageBuilder(Builder builder) {
        this.id = builder.id;
    }

    public static class Builder {

        private int id;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public MessageBuilder build() {
            return new MessageBuilder(this);
        }

    }

}
