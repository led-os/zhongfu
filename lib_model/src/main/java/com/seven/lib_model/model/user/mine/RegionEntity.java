package com.seven.lib_model.model.user.mine;

import java.util.List;

/**
 * Created by xxxxxxH on 2019/4/24.
 */

public class RegionEntity {
    private int id;//":2,
    private int pid;//":1,
    private String region_name;//":"北京",
    private int region_type;//":1,
    private List<RegionEntity> sub;//":

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getRegion_name() {
        return region_name;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }

    public int getRegion_type() {
        return region_type;
    }

    public void setRegion_type(int region_type) {
        this.region_type = region_type;
    }

    public List<RegionEntity> getSub() {
        return sub;
    }

    public void setSub(List<RegionEntity> sub) {
        this.sub = sub;
    }
}
