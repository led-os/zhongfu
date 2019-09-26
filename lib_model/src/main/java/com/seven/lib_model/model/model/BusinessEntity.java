package com.seven.lib_model.model.model;

import java.io.Serializable;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/5/5
 */

public class BusinessEntity implements Serializable {

    /**
     * avatar :
     * username : tyler1
     * price : 10.00
     * token_number : 20
     * ratio : 99%
     * business_success : 123
     * ali_account : xxx
     * wx_account : xxx
     * id : 1
     * role:1
     */

    private String avatar;
    private String username;
    private double price;
    private double token_number;
    private String ali_account;
    private String wx_account;
    private int status;
    private int type;
    private int id;
    private String status_name;
    private double ratio;
    private Integer business_success;
    private int role;

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getToken_number() {
        return token_number;
    }

    public void setToken_number(double token_number) {
        this.token_number = token_number;
    }

    public String getAli_account() {
        return ali_account;
    }

    public void setAli_account(String ali_account) {
        this.ali_account = ali_account;
    }

    public String getWx_account() {
        return wx_account;
    }

    public void setWx_account(String wx_account) {
        this.wx_account = wx_account;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus_name() {
        return status_name;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }

    public Integer getBusiness_success() {
        return business_success;
    }

    public void setBusiness_success(Integer business_success) {
        this.business_success = business_success;
    }
}
