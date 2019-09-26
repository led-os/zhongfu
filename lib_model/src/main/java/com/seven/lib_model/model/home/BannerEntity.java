package com.seven.lib_model.model.home;

import java.io.Serializable;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/4/7
 */

public class BannerEntity implements Serializable {

    private String title;
    private String link;
    private String show_picture;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getShow_picture() {
        return show_picture;
    }

    public void setShow_picture(String show_picture) {
        this.show_picture = show_picture;
    }
}
