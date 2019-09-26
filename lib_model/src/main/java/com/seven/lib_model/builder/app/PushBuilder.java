package com.seven.lib_model.builder.app;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2017/10/26
 */

public class PushBuilder {

    private int registration_id;

    public PushBuilder(Builder builder) {
        this.registration_id = builder.registration_id;
    }

    public static class Builder {

        private int registration_id;

        public Builder registration_id(int registration_id) {
            this.registration_id = registration_id;
            return this;
        }

        public PushBuilder build() {
            return new PushBuilder(this);
        }
    }

}
