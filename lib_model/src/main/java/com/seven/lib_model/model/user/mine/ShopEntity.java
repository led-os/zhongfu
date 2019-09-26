package com.seven.lib_model.model.user.mine;

import com.seven.lib_model.model.home.CartEntity;

import java.util.List;

/**
 * Created by xxxxxxH on 2019/4/23.
 */

public class ShopEntity {
    private PageEntity pagination;
    private List<CartEntity> items;

    public List<CartEntity> getItems() {
        return items;
    }

    public void setItems(List<CartEntity> items) {
        this.items = items;
    }

    public PageEntity getPagination() {
        return pagination;
    }

    public void setPagination(PageEntity pagination) {
        this.pagination = pagination;
    }
}
