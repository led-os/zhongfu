package com.seven.lib_model.builder.common;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2017/10/26
 */

public class SmsBuilder {

    private String phone;
    private String scene;

    private SmsBuilder(Builder builder) {
        this.phone = builder.phone;
        this.scene = builder.scene;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public static class Builder {

        private String phone;
        private String scene;

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder scene(String scene) {
            this.scene = scene;
            return this;
        }

        public SmsBuilder build() {
            return new SmsBuilder(this);
        }
    }

}
