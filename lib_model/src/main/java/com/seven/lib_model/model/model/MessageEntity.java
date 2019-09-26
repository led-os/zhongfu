package com.seven.lib_model.model.model;

import java.io.Serializable;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/4/19
 */

public class MessageEntity implements Serializable {

    /**
     * id : 1.0
     * type : 2.0
     * trigger_id : 16.0
     * content : 你的交易已被发起售卖
     * is_read : 0.0
     * "created_at":"2019-06-10 09:02:53"
     */

    private int id;
    private int type;
    private int trigger_id;
    private String content;
    private int is_read;
    private String created_at;

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getTrigger_id() {
        return trigger_id;
    }

    public void setTrigger_id(int trigger_id) {
        this.trigger_id = trigger_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getIs_read() {
        return is_read;
    }

    public void setIs_read(int is_read) {
        this.is_read = is_read;
    }
}
