package com.seven.lib_model.model.extension;

/**
 * Created by xxxxxxH on 2019/5/14.
 */
public class QuotaItem {
    private int id;
    private int quota_role;
    private String user_id;
    private String phone;
    private int is_bind;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuota_role() {
        return quota_role;
    }

    public void setQuota_role(int quota_role) {
        this.quota_role = quota_role;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getIs_bind() {
        return is_bind;
    }

    public void setIs_bind(int is_bind) {
        this.is_bind = is_bind;
    }
}
