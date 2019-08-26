package com.seven.lib_opensource.event;


/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2017/10/24
 */

public class MessageEvent extends Event {

    private String message;
    private int what;

    public MessageEvent(Integer what,String message) {
        this.message = message;
        this.what=what;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public int eventType() {
        return EVENT_MESSAGE;
    }

    @Override
    public int eventWhat() {
        return what;
    }
}
