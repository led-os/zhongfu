package com.seven.lib_model.model.user.mine;

/**
 * Created by xxxxxxH on 2019/4/25.
 */

public class GoodsListBean {
    /**
     * order_id : 9
     * goods_id : 1
     * goods_name : 七匹狼
     * goods_thumb : 1403225728afeebe.jpg
     * number : 2
     * price : 199.00
     * total : 398.00
     */

    private int order_id;
    private int goods_id;
    private String goods_name;
    private String goods_thumb;
    private int number;
    private String price;
    private String total;

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_thumb() {
        return goods_thumb;
    }

    public void setGoods_thumb(String goods_thumb) {
        this.goods_thumb = goods_thumb;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
