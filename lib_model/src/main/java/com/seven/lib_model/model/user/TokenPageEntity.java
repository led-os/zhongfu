package com.seven.lib_model.model.user;

/**
 * Created by xxxxxxH on 2019/5/20.
 */

public class TokenPageEntity {
    private int page;
    private int page_size;
    private String status;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPage_size() {
        return page_size;
    }

    public void setPage_size(int page_size) {
        this.page_size = page_size;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
