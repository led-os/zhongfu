package com.seven.lib_model.model.home;

import java.io.Serializable;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/4/5
 */

public class CommodityEntity implements Serializable {

    /**
     * thumb : http://img.zhongfu.com/1403225728afeebe.jpg
     * id : 1
     * sales : 2
     * price : 199
     * goods_name : 七匹狼
     * is_form_goods:1
     */

    private String thumb;
    private int id;
    private int sales;
    private double price;
    private String goods_name;
    private double token_price;
    private int is_form_goods;

    public int getIs_form_goods() {
        return is_form_goods;
    }

    public void setIs_form_goods(int is_form_goods) {
        this.is_form_goods = is_form_goods;
    }

    public double getToken_price() {
        return token_price;
    }

    public void setToken_price(double token_price) {
        this.token_price = token_price;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }
}
