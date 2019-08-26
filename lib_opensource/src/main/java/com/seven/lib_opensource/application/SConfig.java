package com.seven.lib_opensource.application;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/3/6
 */

public class SConfig {

    /**
     * 登录验证失效 minCode
     */
    private int minCode;
    /**
     * 登录验证失效 maxCode
     */
    private int maxCode;

    /**
     * 登录验证失效 event code
     */
    private int eventCode;

    public int getMinCode() {
        return minCode;
    }

    public void setMinCode(int minCode) {
        this.minCode = minCode;
    }

    public int getMaxCode() {
        return maxCode;
    }

    public void setMaxCode(int maxCode) {
        this.maxCode = maxCode;
    }

    public int getEventCode() {
        return eventCode;
    }

    public void setEventCode(int eventCode) {
        this.eventCode = eventCode;
    }
}
