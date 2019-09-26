package com.seven.lib_model.model.extension;

/**
 * Created by xxxxxxH on 2019/5/4.
 */
public class RewardItem {
    private int id;
    private String reward_name;
    private double reward_number;
    private int reward_type;//奖励类型(1-直推令牌奖励, 2-间推令牌奖励, 3-利润分成, 4-矿主名额, 5-场主名额, 6-报单产品, 7-购买产品令牌奖励, 8-新进会员令牌奖励)
    private int is_processed;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReward_name() {
        return reward_name;
    }

    public void setReward_name(String reward_name) {
        this.reward_name = reward_name;
    }

    public double getReward_number() {
        return reward_number;
    }

    public void setReward_number(double reward_number) {
        this.reward_number = reward_number;
    }

    public int getReward_type() {
        return reward_type;
    }

    public void setReward_type(int reward_type) {
        this.reward_type = reward_type;
    }

    public int getIs_processed() {
        return is_processed;
    }

    public void setIs_processed(int is_processed) {
        this.is_processed = is_processed;
    }
}
