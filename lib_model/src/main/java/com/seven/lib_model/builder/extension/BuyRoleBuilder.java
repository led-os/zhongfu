package com.seven.lib_model.builder.extension;

public class BuyRoleBuilder {

    public BuyRoleBuilder(Builder builder){
        this.role = builder.role;
        this.type = builder.type;
    }

    private int role;
    private String type;

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static class Builder{
        private int role;
        private String type;

        public Builder role(int role){
            this.role = role;
            return this;
        }

        public Builder type(String type){
            this.type = type;
            return  this;
        }

        public BuyRoleBuilder build(){return new BuyRoleBuilder(this);}
    }
}
