package com.seven.lib_model.builder.extension;

/**
 * Created by xxxxxxH on 2019/5/12.
 */
public class LevelBuilder {
    private int id;

    public LevelBuilder(Builder builder) {
        this.id = builder.id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static class Builder {
        private int id;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public LevelBuilder build() {
            return new LevelBuilder(this);
        }
    }
}
