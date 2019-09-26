package com.seven.lib_model.model.user;

import com.seven.lib_model.model.user.mine.GoodsListBean;

import java.util.List;

/**
 * Created by xxxxxxH on 2019/4/9.
 */

public class OrderEntity {

    /**
     * id : 9
     * order_sn : 155574753210942
     * status : 1
     * total : 398.00
     * goods_list : [{"order_id":9,"goods_id":1,"goods_name":"七匹狼","goods_thumb":"1403225728afeebe.jpg","number":2,"price":"199.00","total":"398.00"}]
     */

    private int id;
    private String order_sn;
    private int status;
    private String total;
    private double token_total;
    private List<GoodsListBean> goods_list;

    public double getToken_total() {
        return token_total;
    }

    public void setToken_total(double token_total) {
        this.token_total = token_total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<GoodsListBean> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<GoodsListBean> goods_list) {
        this.goods_list = goods_list;
    }

}
