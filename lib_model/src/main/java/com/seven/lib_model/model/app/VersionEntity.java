package com.seven.lib_model.model.app;

/**
 * Created by xxxxxxH on 2019/5/27.
 */
public class VersionEntity {
    /**
     * ios_version_code : 1.0.0
     * ios_desc : <p>klasjdf</p>
     * ios_url : http://www.baidu.com
     * android_version_code : 1.0.2
     * android_desc : <p>123123</p>
     * android_url : http://www.baidu.com
     */

    private String ios_version_code;
    private String ios_desc;
    private String ios_url;
    private String android_version_code;
    private String android_desc;
    private String android_url;

    public String getIos_version_code() {
        return ios_version_code;
    }

    public void setIos_version_code(String ios_version_code) {
        this.ios_version_code = ios_version_code;
    }

    public String getIos_desc() {
        return ios_desc;
    }

    public void setIos_desc(String ios_desc) {
        this.ios_desc = ios_desc;
    }

    public String getIos_url() {
        return ios_url;
    }

    public void setIos_url(String ios_url) {
        this.ios_url = ios_url;
    }

    public String getAndroid_version_code() {
        return android_version_code;
    }

    public void setAndroid_version_code(String android_version_code) {
        this.android_version_code = android_version_code;
    }

    public String getAndroid_desc() {
        return android_desc;
    }

    public void setAndroid_desc(String android_desc) {
        this.android_desc = android_desc;
    }

    public String getAndroid_url() {
        return android_url;
    }

    public void setAndroid_url(String android_url) {
        this.android_url = android_url;
    }
}
