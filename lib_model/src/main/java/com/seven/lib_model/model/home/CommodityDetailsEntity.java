package com.seven.lib_model.model.home;

import java.io.Serializable;
import java.util.List;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/4/23
 */

public class CommodityDetailsEntity implements Serializable {

    /**
     * id : 1
     * is_collect : 0
     * goods_name : 七匹狼
     * sales : 2
     * thumb:"http://ssss.jpg"
     * price:100.0
     * pictures : ["http://img.zhongfu.com/1403225728afeebe.jpg","http://img.zhongfu.com/7457874e7c1df6fb.jpg","http://img.zhongfu.com/a1ea5b1f52f957d2.jpg","http://img.zhongfu.com/54065e7a757cf9fe.jpg","http://img.zhongfu.com/418ff8c13828802b.jpg"]
     * detail_pictures : ["http://img.zhongfu.com/1403225728afeebe.jpg","http://img.zhongfu.com/7457874e7c1df6fb.jpg","http://img.zhongfu.com/a1ea5b1f52f957d2.jpg","http://img.zhongfu.com/54065e7a757cf9fe.jpg","http://img.zhongfu.com/418ff8c13828802b.jpg"]
     * detail : 这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述
     * sku_list : [{"id":1,"sku":"1:1,2:3","price":"199.00","stock":100,"sales":2,"show_thumb":"http://img.zhongfu.com/1403225728afeebe.jpg"},{"id":2,"sku":"1:1,2:4","price":"199.00","stock":101,"sales":4,"show_thumb":"http://img.zhongfu.com/1403225728afeebe.jpg"},{"id":3,"sku":"1:2,2:3","price":"199.00","stock":89,"sales":5,"show_thumb":"http://img.zhongfu.com/1403225728afeebe.jpg"},{"id":4,"sku":"1:2,2:4","price":"199.00","stock":70,"sales":3,"show_thumb":"http://img.zhongfu.com/1403225728afeebe.jpg"}]
     * sku_attr_list : [{"attr_id":1,"attr_title":"颜色","attr_values":[{"attr_value_id":1,"attr_value_title":"黑色"},{"attr_value_id":2,"attr_value_title":"白色"}]},{"attr_id":2,"attr_title":"尺寸","attr_values":[{"attr_value_id":3,"attr_value_title":"M/165"},{"attr_value_id":4,"attr_value_title":"L/170"}]}]
     */

    private int id;
    private int is_collect;
    private double price;
    private double token_price;
    private String thumb;
    private String goods_name;
    private int sales;
    private String detail;
    private List<String> pictures;
    private List<String> detail_pictures;
    private List<SkuListBean> sku_list;
    private List<SkuAttrListBean> sku_attr_list;
    private int is_form_goods;

    public int getIs_form_goods() {
        return is_form_goods;
    }

    public void setIs_form_goods(int is_form_goods) {
        this.is_form_goods = is_form_goods;
    }

    public double getToken_price() {
        return token_price;
    }

    public void setToken_price(double token_price) {
        this.token_price = token_price;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public List<String> getDetail_pictures() {
        return detail_pictures;
    }

    public void setDetail_pictures(List<String> detail_pictures) {
        this.detail_pictures = detail_pictures;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIs_collect() {
        return is_collect;
    }

    public void setIs_collect(int is_collect) {
        this.is_collect = is_collect;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public List<String> getPictures() {
        return pictures;
    }

    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
    }

    public List<SkuListBean> getSku_list() {
        return sku_list;
    }

    public void setSku_list(List<SkuListBean> sku_list) {
        this.sku_list = sku_list;
    }

    public List<SkuAttrListBean> getSku_attr_list() {
        return sku_attr_list;
    }

    public void setSku_attr_list(List<SkuAttrListBean> sku_attr_list) {
        this.sku_attr_list = sku_attr_list;
    }

    public static class SkuListBean {
        /**
         * id : 1
         * sku : 1:1,2:3
         * price : 199.00
         * stock : 100
         * sales : 2
         * show_thumb : http://img.zhongfu.com/1403225728afeebe.jpg
         */

        private int id;
        private String sku;
        private double price;
        private int stock;
        private int sales;
        private String thumb;
        private double token_price;

        public double getToken_price() {
            return token_price;
        }

        public void setToken_price(double token_price) {
            this.token_price = token_price;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSku() {
            return sku;
        }

        public void setSku(String sku) {
            this.sku = sku;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }

        public int getSales() {
            return sales;
        }

        public void setSales(int sales) {
            this.sales = sales;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }
    }

    public static class SkuAttrListBean {
        /**
         * attr_id : 1
         * attr_title : 颜色
         * attr_values : [{"attr_value_id":1,"attr_value_title":"黑色"},{"attr_value_id":2,"attr_value_title":"白色"}]
         */

        private int attr_id;
        private String attr_title;
        private List<AttrValuesBean> attr_values;

        public int getAttr_id() {
            return attr_id;
        }

        public void setAttr_id(int attr_id) {
            this.attr_id = attr_id;
        }

        public String getAttr_title() {
            return attr_title;
        }

        public void setAttr_title(String attr_title) {
            this.attr_title = attr_title;
        }

        public List<AttrValuesBean> getAttr_values() {
            return attr_values;
        }

        public void setAttr_values(List<AttrValuesBean> attr_values) {
            this.attr_values = attr_values;
        }

        public static class AttrValuesBean {
            /**
             * attr_value_id : 1
             * attr_value_title : 黑色
             */

            private int attr_value_id;
            private String attr_value_title;
            private boolean select;

            public boolean isSelect() {
                return select;
            }

            public void setSelect(boolean select) {
                this.select = select;
            }

            public int getAttr_value_id() {
                return attr_value_id;
            }

            public void setAttr_value_id(int attr_value_id) {
                this.attr_value_id = attr_value_id;
            }

            public String getAttr_value_title() {
                return attr_value_title;
            }

            public void setAttr_value_title(String attr_value_title) {
                this.attr_value_title = attr_value_title;
            }
        }
    }
}
