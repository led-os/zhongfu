package com.seven.lib_model.model.extension;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xxxxxxH on 2019/5/4.
 */
public class RewardListEntity implements Serializable {
    private List<RewardItem> items;

    public List<RewardItem> getItems() {
        return items;
    }

    public void setItems(List<RewardItem> items) {
        this.items = items;
    }
}
