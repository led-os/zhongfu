package com.seven.lib_model.model.extension;

import com.seven.lib_model.model.user.mine.PageEntity;

import java.util.List;

/**
 * Created by xxxxxxH on 2019/5/4.
 */
public class InComeDetailsEntity {
    private PageEntity pagination;
    private List<InComeItem> items;

    public PageEntity getPagination() {
        return pagination;
    }

    public void setPagination(PageEntity pagination) {
        this.pagination = pagination;
    }

    public List<InComeItem> getItems() {
        return items;
    }

    public void setItems(List<InComeItem> items) {
        this.items = items;
    }
}
