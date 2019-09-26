package com.seven.lib_model.model.extension;

import java.util.List;

/**
 * Created by xxxxxxH on 2019/4/25.
 */
public class MyInterViewEntity {

    /**
     * pagination : {"total_page":1,"total_record":1,"page":1,"page_size":20}
     * items : [{"id":37,"avatar":"http://zhongfu.lerqin.com/images/avatar/20190503/1556867364Ya.jpg","username":"9902","status":"valid","role":0,"sex":"female"}]
     * parent_info : {"id":14,"avatar":"http://zhongfu.lerqin.com/images/avatar/20190501/1556678265C2.jpg","username":"9963","status":"valid","role":3,"sex":"male"}
     */

    private ParentInfo parent_info;
    private List<ItemsBean> items;

    public ParentInfo getParent_info() {
        return parent_info;
    }

    public void setParent_info(ParentInfo parent_info) {
        this.parent_info = parent_info;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }
}
