package com.seven.lib_model.model.extension;

import java.io.Serializable;

/**
 * Created by xxxxxxH on 2019/4/23.
 */
public class Goods implements Serializable {
    private String thumb;
    private String goods_name;
    private String number;

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
