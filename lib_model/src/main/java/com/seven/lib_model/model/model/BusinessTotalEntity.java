package com.seven.lib_model.model.model;

import java.io.Serializable;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/6/5
 */

public class BusinessTotalEntity implements Serializable {

    /**
     * total : 9.0
     * buy : {"total":0,"wait_accept":0,"wait_upload":0,"wait_confirm":0}
     * sale : {"total":9,"wait_accept":7,"wait_upload":0,"wait_confirm":2}
     */

    private int total;
    private BuyBean buy;
    private SaleBean sale;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public BuyBean getBuy() {
        return buy;
    }

    public void setBuy(BuyBean buy) {
        this.buy = buy;
    }

    public SaleBean getSale() {
        return sale;
    }

    public void setSale(SaleBean sale) {
        this.sale = sale;
    }

    public static class BuyBean {
        /**
         * total : 0.0
         * wait_accept : 0.0
         * wait_upload : 0.0
         * wait_confirm : 0.0
         */

        private int total;
        private int wait_accept;
        private int wait_upload;
        private int wait_confirm;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getWait_accept() {
            return wait_accept;
        }

        public void setWait_accept(int wait_accept) {
            this.wait_accept = wait_accept;
        }

        public int getWait_upload() {
            return wait_upload;
        }

        public void setWait_upload(int wait_upload) {
            this.wait_upload = wait_upload;
        }

        public int getWait_confirm() {
            return wait_confirm;
        }

        public void setWait_confirm(int wait_confirm) {
            this.wait_confirm = wait_confirm;
        }
    }

    public static class SaleBean {
        /**
         * total : 9.0
         * wait_accept : 7.0
         * wait_upload : 0.0
         * wait_confirm : 2.0
         */

        private int total;
        private int wait_accept;
        private int wait_upload;
        private int wait_confirm;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getWait_accept() {
            return wait_accept;
        }

        public void setWait_accept(int wait_accept) {
            this.wait_accept = wait_accept;
        }

        public int getWait_upload() {
            return wait_upload;
        }

        public void setWait_upload(int wait_upload) {
            this.wait_upload = wait_upload;
        }

        public int getWait_confirm() {
            return wait_confirm;
        }

        public void setWait_confirm(int wait_confirm) {
            this.wait_confirm = wait_confirm;
        }
    }
}
