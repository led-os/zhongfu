package com.seven.lib_router.db.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2017/11/15
 */

@Entity
public class SearchHistory {

    @Id
    private Long id;
    private String record;
    public String getRecord() {
        return this.record;
    }
    public void setRecord(String record) {
        this.record = record;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 1044003104)
    public SearchHistory(Long id, String record) {
        this.id = id;
        this.record = record;
    }
    @Generated(hash = 1905904755)
    public SearchHistory() {
    }

}
