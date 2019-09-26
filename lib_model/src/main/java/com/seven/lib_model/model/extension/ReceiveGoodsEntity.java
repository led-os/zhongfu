package com.seven.lib_model.model.extension;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xxxxxxH on 2019/4/23.
 */
public class ReceiveGoodsEntity implements Serializable {
    private String price;
    private List<Goods> goods_list;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<Goods> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<Goods> goods_list) {
        this.goods_list = goods_list;
    }
}
