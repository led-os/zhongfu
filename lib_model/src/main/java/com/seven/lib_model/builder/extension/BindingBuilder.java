package com.seven.lib_model.builder.extension;

/**
 * Created by xxxxxxH on 2019/5/12.
 */
public class BindingBuilder {
    private String phone;
    private String id;

    public BindingBuilder(Builder builder){
        this.phone = builder.phone;
        this.id = builder.id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static class Builder {
        private String phone;
        private String id;

        public Builder phone(String phone){
            this.phone = phone;
            return this;
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public BindingBuilder build() {
            return new BindingBuilder(this);
        }
    }
}
