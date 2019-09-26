package com.seven.lib_model.builder.extension;

/**
 * Created by xxxxxxH on 2019/4/24.
 */
public class RewardRuleBuilder {
        private int role;

    public RewardRuleBuilder(Builder builder) {
        this.role = builder.role;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public static class Builder{
        private int role;
        public Builder role(int role){
            this.role = role;
            return this;
        }
        public RewardRuleBuilder build(){
            return new RewardRuleBuilder(this);
        }
    }
}
