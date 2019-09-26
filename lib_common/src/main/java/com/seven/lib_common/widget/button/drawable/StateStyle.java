package com.seven.lib_common.widget.button.drawable;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/9/23
 */

public class StateStyle {
    private String version;
    private String url;
    private String text_cn;
    private String text_en;
    private boolean is_update;
    private boolean param;

    public boolean isParam() {
        return param;
    }

    public void setParam(boolean param) {
        this.param = param;
    }

    public boolean isIs_update() {
        return is_update;
    }

    public void setIs_update(boolean is_update) {
        this.is_update = is_update;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getText_cn() {
        return text_cn;
    }

    public void setText_cn(String text_cn) {
        this.text_cn = text_cn;
    }

    public String getText_en() {
        return text_en;
    }

    public void setText_en(String text_en) {
        this.text_en = text_en;
    }
}
