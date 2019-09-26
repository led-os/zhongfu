package com.seven.lib_model.model.user.mine;

import java.util.List;

/**
 * Created by xxxxxxH on 2019/4/25.
 */

public class OrderDetailEntity {

    /**
     * id : 9
     * order_sn : 155574753210942
     * status : 1
     * total : 398.00
     * goods_total_fee : 0.00
     * express_fee : 0
     * province : 24
     * city : 271
     * district : 2723
     * address : 四川省成都市双流县华阳
     * contact_phone : 15680723020
     * contact_name :
     * goods_list : [{"goods_id":1,"goods_name":"七匹狼","goods_sku":"1","goods_thumb":"1403225728afeebe.jpg","number":2,"price":"199.00","total":"398.00"}]
     */

    private int id;
    private String order_sn;
    private int status;
    private String total;
    private String goods_total_fee;
    private int express_fee;
    private String province;
    private String city;
    private String district;
    private String address;
    private String contact_phone;
    private String contact_name;
    private String created_at;
    private double token_total;

    public double getToken_total() {
        return token_total;
    }

    public void setToken_total(double token_total) {
        this.token_total = token_total;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    private List<GoodsListBean> goods_list;

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

    public String getGoods_total_fee() {
        return goods_total_fee;
    }

    public void setGoods_total_fee(String goods_total_fee) {
        this.goods_total_fee = goods_total_fee;
    }

    public int getExpress_fee() {
        return express_fee;
    }

    public void setExpress_fee(int express_fee) {
        this.express_fee = express_fee;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact_phone() {
        return contact_phone;
    }

    public void setContact_phone(String contact_phone) {
        this.contact_phone = contact_phone;
    }

    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public List<GoodsListBean> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<GoodsListBean> goods_list) {
        this.goods_list = goods_list;
    }
}
