package com.seven.lib_model;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/3/8
 */

public class HttpConfig {

    private String uuid;
    private String token;
    private String language;
    private String appUrl;
    private String storeUrl;
    private String appKey;
    private String storeKey;

    public HttpConfig() {
    }

    public HttpConfig(Builder builder) {
        this.uuid = builder.uuid;
        this.token = builder.token;
        this.language = builder.language;
        this.appUrl = builder.appUrl;
        this.storeUrl = builder.storeUrl;
        this.appKey = builder.appKey;
        this.storeKey = builder.storeKey;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getStoreKey() {
        return storeKey;
    }

    public void setStoreKey(String storeKey) {
        this.storeKey = storeKey;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public String getStoreUrl() {
        return storeUrl;
    }

    public void setStoreUrl(String storeUrl) {
        this.storeUrl = storeUrl;
    }

    public static class Builder {

        private String uuid;
        private String token;
        private String language;
        private String appUrl;
        private String storeUrl;
        private String appKey;
        private String storeKey;

        public Builder uuid(String uuid) {
            this.uuid = uuid;
            return this;
        }

        public Builder token(String token) {
            this.token = token;
            return this;
        }

        public Builder language(String language) {
            this.language = language;
            return this;
        }

        public Builder appUrl(String appUrl) {
            this.appUrl = appUrl;
            return this;
        }

        public Builder storeUrl(String storeUrl) {
            this.storeUrl = storeUrl;
            return this;
        }

        public Builder appKey(String appKey) {
            this.appKey = appKey;
            return this;
        }

        public Builder storeKey(String storeKey) {
            this.storeKey = storeKey;
            return this;
        }

        public HttpConfig build() {
            return new HttpConfig(this);
        }
    }

}
