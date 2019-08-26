package com.seven.module_home.model;

import java.io.Serializable;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/5/9
 */

public class SpecificationEntity implements Serializable {

    private int position;
    private int id;
    private String key;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
