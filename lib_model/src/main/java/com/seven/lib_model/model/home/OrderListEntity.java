package com.seven.lib_model.model.home;

import java.io.Serializable;
import java.util.List;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/4/23
 */

public class OrderListEntity implements Serializable {

    /**
     * items : [{"goods_id":1,"goods_sku_id":1,"goods_sku_name":"M/165 黑色","goods_name":"七匹狼","goods_thumb":"1403225728afeebe.jpg","number":1,"price":"199.00","total":199}]
     * total : 199
     * from : goods_detail
     * from_ext :
     * express_fee : 0
     */

    private double total;
    private String from;
    private String from_ext;
    private int express_fee;
    private List<ItemsBean> items;

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getFrom_ext() {
        return from_ext;
    }

    public void setFrom_ext(String from_ext) {
        this.from_ext = from_ext;
    }

    public int getExpress_fee() {
        return express_fee;
    }

    public void setExpress_fee(int express_fee) {
        this.express_fee = express_fee;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        /**
         * goods_id : 1
         * goods_sku_id : 1
         * goods_sku_name : M/165 黑色
         * goods_name : 七匹狼
         * goods_thumb : 1403225728afeebe.jpg
         * number : 1
         * price : 199.00
         * total : 199
         */

        private int goods_id;
        private int goods_sku_id;
        private String goods_sku_name;
        private String goods_name;
        private String goods_thumb;
        private int number;
        private double price;
        private double total;

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public int getGoods_sku_id() {
            return goods_sku_id;
        }

        public void setGoods_sku_id(int goods_sku_id) {
            this.goods_sku_id = goods_sku_id;
        }

        public String getGoods_sku_name() {
            return goods_sku_name;
        }

        public void setGoods_sku_name(String goods_sku_name) {
            this.goods_sku_name = goods_sku_name;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getGoods_thumb() {
            return goods_thumb;
        }

        public void setGoods_thumb(String goods_thumb) {
            this.goods_thumb = goods_thumb;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public double getTotal() {
            return total;
        }

        public void setTotal(double total) {
            this.total = total;
        }
    }
}
