package com.seven.lib_model.model.user.mine;

/**
 * Created by xxxxxxH on 2019/4/23.
 */

public class PageEntity {

    /**
     * total_page : 1
     * total_record : 1
     * page : 1
     * page_size : 10
     */

    private int total_page;
    private int total_record;
    private int page;
    private int page_size;

    public int getTotal_page() {
        return total_page;
    }

    public void setTotal_page(int total_page) {
        this.total_page = total_page;
    }

    public int getTotal_record() {
        return total_record;
    }

    public void setTotal_record(int total_record) {
        this.total_record = total_record;
    }

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
}
