package com.seven.lib_model.builder.extension;

/**
 * Created by xxxxxxH on 2019/5/12.
 */
public class RewardBilder {
    public RewardBilder(Builder builder) {
        this.reward_info_id = builder.reward_info_id;
    }

    private String reward_info_id;

    public String getReward_info_id() {
        return reward_info_id;
    }

    public void setReward_info_id(String reward_info_id) {
        this.reward_info_id = reward_info_id;
    }

    public static class Builder {
        private String reward_info_id;

        public Builder reward_info_id(String reward_info_id) {
            this.reward_info_id = reward_info_id;
            return this;
        }

        public RewardBilder build() {
            return new RewardBilder(this);
        }
    }
}
