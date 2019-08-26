package com.seven.lib_opensource.event;


/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2017/10/24
 */

public class ObjectsEvent extends Event{

    private Object[] objects;
    private int what;

    public ObjectsEvent(Integer what,Object... objects){
        this.objects=objects;
        this.what=what;
    }

    public Object[] getObjects() {
        return objects;
    }

    public void setObjects(Object[] objects) {
        this.objects = objects;
    }

    @Override
    public int eventType() {
        return EVENT_OBJECTS;
    }

    @Override
    public int eventWhat() {
        return what;
    }
}
