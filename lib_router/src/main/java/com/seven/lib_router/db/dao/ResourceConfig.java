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
public class ResourceConfig {
    @Id
    private Long id;
    private String data;
    public String getData() {
        return this.data;
    }
    public void setData(String data) {
        this.data = data;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 690944758)
    public ResourceConfig(Long id, String data) {
        this.id = id;
        this.data = data;
    }
    @Generated(hash = 1165259375)
    public ResourceConfig() {
    }
}
