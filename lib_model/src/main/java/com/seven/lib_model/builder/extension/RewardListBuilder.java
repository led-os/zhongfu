package com.seven.lib_model.builder.extension;

/**
 * Created by xxxxxxH on 2019/5/12.
 */
public class RewardListBuilder {

    public RewardListBuilder(Builder builder){
        this.reward_id = builder.reward_id;
    }

    private int reward_id;

    public int getReward_id() {
        return reward_id;
    }

    public void setReward_id(int reward_id) {
        this.reward_id = reward_id;
    }

    public static class Builder {
        private int reward_id;

        public Builder reward_id(int reward_id) {
            this.reward_id = reward_id;
            return this;
        }

        public RewardListBuilder build() {
            return new RewardListBuilder(this);
        }
    }
}
