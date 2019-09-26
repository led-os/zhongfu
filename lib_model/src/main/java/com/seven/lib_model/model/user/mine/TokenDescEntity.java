package com.seven.lib_model.model.user.mine;

import com.google.gson.annotations.SerializedName;

/**
 * Created by xxxxxxH on 2019/5/12.
 */

public class TokenDescEntity {

    /**
     * content :
     * title : 标题
     * id : 文章id
     */

    private String content;
    private String title;
    private int id;
    /**
     * id : 2
     */


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
