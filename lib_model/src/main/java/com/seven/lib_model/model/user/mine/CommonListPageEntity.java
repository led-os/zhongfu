package com.seven.lib_model.model.user.mine;

import java.util.List;

/**
 * Created by xxxxxxH on 2019/4/25.
 */

public class CommonListPageEntity<T> {
    private PageEntity pagination;
    private List<T> items;

    public PageEntity getPagination() {
        return pagination;
    }

    public void setPagination(PageEntity pagination) {
        this.pagination = pagination;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
