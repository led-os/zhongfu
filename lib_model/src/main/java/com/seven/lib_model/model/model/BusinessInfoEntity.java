package com.seven.lib_model.model.model;

import java.io.Serializable;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/5/6
 */

public class BusinessInfoEntity implements Serializable {

    /**
     * avatar :
     * username :
     * business_success : 0.0
     * price : 0.00
     * token_number : 0.0
     * ali_account :
     * wx_account :
     * type : 2.0
     * ratio : 0.0
     * pay_way:1
     * user_id:1
     * status_name:
     * proof_picture:凭证图片
     */

    private String avatar;
    private String username;
    private int business_success;
    private double price;
    private double token_number;
    private String ali_account;
    private String wx_account;
    private int type;
    private double ratio;

    private int status;
    private int pay_way;
    private int user_id;
    private String status_name;
    private String proof_picture;
    private int role;

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getPay_way() {
        return pay_way;
    }

    public void setPay_way(int pay_way) {
        this.pay_way = pay_way;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getStatus_name() {
        return status_name;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }

    public String getProof_picture() {
        return proof_picture;
    }

    public void setProof_picture(String proof_picture) {
        this.proof_picture = proof_picture;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public int getBusiness_success() {
        return business_success;
    }

    public void setBusiness_success(int business_success) {
        this.business_success = business_success;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }
}
