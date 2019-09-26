package com.seven.lib_model.builder.extension;

/**
 * Created by xxxxxxH on 2019/5/12.
 */
public class ReceiveBuilder {
    private String ids;
    private String contact_id;

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getContact_id() {
        return contact_id;
    }

    public void setContact_id(String contact_id) {
        this.contact_id = contact_id;
    }

    public ReceiveBuilder(Builder builder) {
        this.ids = builder.ids;
        this.contact_id = builder.contact_id;
    }

    public static class Builder {
        private String ids;
        private String contact_id;

        public Builder ids(String ids) {
            this.ids = ids;
            return this;
        }

        public Builder contact_id(String contact_id) {
            this.contact_id = contact_id;
            return this;
        }

        public ReceiveBuilder build() {
            return new ReceiveBuilder(this);
        }
    }
}
