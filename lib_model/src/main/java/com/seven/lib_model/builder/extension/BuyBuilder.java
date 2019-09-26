package com.seven.lib_model.builder.extension;

public class BuyBuilder {
    public BuyBuilder(Builder builder) {

        this.contact_id = builder.contact_id;
        this.type = builder.type;
    }

    private int contact_id;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getContact_id() {
        return contact_id;
    }

    public void setContact_id(int contact_id) {
        this.contact_id = contact_id;
    }

    public static class Builder {
        private int contact_id;
        private int type;

        public Builder contact_id(int contact_id) {
            this.contact_id = contact_id;
            return this;
        }
        public Builder type(int type){
            this.type = type;
            return this;
        }

        public BuyBuilder build() {
            return new BuyBuilder(this);
        }
    }
}
