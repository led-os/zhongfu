package com.seven.lib_model.builder.model;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/5/5
 */

public class BusinessProofBuilder {

    private int business_id;
    private String proof_picture;

    public BusinessProofBuilder(Builder builder) {
        this.business_id = builder.business_id;
        this.proof_picture=builder.proof_picture;
    }

    public static class Builder {

        private int business_id;
        private String proof_picture;

        public Builder business_id(int business_id) {
            this.business_id = business_id;
            return this;
        }

        public Builder proof_picture(String proof_picture) {
            this.proof_picture = proof_picture;
            return this;
        }

        public BusinessProofBuilder build() {
            return new BusinessProofBuilder(this);
        }

    }

}
