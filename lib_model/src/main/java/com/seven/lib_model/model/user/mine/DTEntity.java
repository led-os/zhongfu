package com.seven.lib_model.model.user.mine;

import java.util.List;

/**
 * Created by xxxxxxH on 2019/4/24.
 */

public class DTEntity {
    private List<RegionEntity> items;
    private int contact_id;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getContact_id() {
        return contact_id;
    }

    public void setContact_id(int contact_id) {
        this.contact_id = contact_id;
    }

    public List<RegionEntity> getItems() {
        return items;
    }

    public void setItems(List<RegionEntity> items) {
        this.items = items;
    }
}
