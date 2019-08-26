package com.seven.lib_opensource.event;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2017/10/24
 */

public abstract class Event {

    public static final int EVENT_MESSAGE = 1;
    public static final int EVENT_OBJECTS = 2;

    private int type;

    private int what;

    public int getWhat() {
        return eventWhat();
    }

    public void setWhat(int what) {
        this.what = what;
    }

    public int getType() {
        return eventType();
    }

    public void setType(int type) {
        this.type = type;
    }

    public abstract int eventType();

    public abstract int eventWhat();
}
