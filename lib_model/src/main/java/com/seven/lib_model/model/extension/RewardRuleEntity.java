package com.seven.lib_model.model.extension;

/**
 * Created by xxxxxxH on 2019/4/24.
 */
public class RewardRuleEntity {

    /**
     * name : 矿主直推令牌奖励
     * desc :
     * unit : 个
     * value : 75
     */

    private String name;
    private String desc;
    private String unit;
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
