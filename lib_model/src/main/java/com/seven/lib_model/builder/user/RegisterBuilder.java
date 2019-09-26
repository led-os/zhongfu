package com.seven.lib_model.builder.user;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2017/10/26
 */

public class RegisterBuilder {

    private String phone;
    private String code;
    private String password;
    private String password_confirmation;
    private String invite_code;

    private RegisterBuilder(Builder builder) {
        this.phone = builder.phone;
        this.code = builder.code;
        this.password = builder.password;
        this.password_confirmation = builder.password_confirmation;
        this.invite_code = builder.invite_code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword_confirmation() {
        return password_confirmation;
    }

    public void setPassword_confirmation(String password_confirmation) {
        this.password_confirmation = password_confirmation;
    }

    public String getInvite_code() {
        return invite_code;
    }

    public void setInvite_code(String invite_code) {
        this.invite_code = invite_code;
    }

    public static class Builder {

        private String phone;
        private String code;
        private String password;
        private String password_confirmation;
        private String invite_code;

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder code(String code) {
            this.code = code;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder password_confirmation(String password_confirmation) {
            this.password_confirmation = password_confirmation;
            return this;
        }

        public Builder invite_code(String invite_code) {
            this.invite_code = invite_code;
            return this;
        }


        public RegisterBuilder build() {
            return new RegisterBuilder(this);
        }
    }

}
