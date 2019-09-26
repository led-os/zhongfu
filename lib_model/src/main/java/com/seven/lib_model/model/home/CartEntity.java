package com.seven.lib_model.model.home;

import java.io.Serializable;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/4/23
 */

public class CartEntity implements Serializable {

    /**
     * goods_name : 七匹狼
     * price : 199
     * number : 1
     * sales : 2
     * stock : 100
     * id : 1
     * thumb : http://img.zhongfu.com/1403225728afeebe.jpg
     */

    private String goods_name;
    private double price;
    private int number;
    private int sales;
    private int stock;
    private int id;
    private String thumb;
    private String goods_sku_name;
    private boolean isSelect;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    private String from;//来源(购物车:cart,订单详情:goods_detail)
    private String cart_ids;//购物车id列表(逗号分隔)
    private int sku_id;//商品sku id(from为goods_detail提供)

    public String getGoods_sku_name() {
        return goods_sku_name;
    }

    public void setGoods_sku_name(String goods_sku_name) {
        this.goods_sku_name = goods_sku_name;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getCart_ids() {
        return cart_ids;
    }

    public void setCart_ids(String cart_ids) {
        this.cart_ids = cart_ids;
    }

    public int getSku_id() {
        return sku_id;
    }

    public void setSku_id(int sku_id) {
        this.sku_id = sku_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }
}
