package com.seven.lib_model.builder.user;

/**
 * Created by xxxxxxH on 2019/5/19.
 */
public class EditAddressBuilder {

    private int contact_id;
    private String province_id;
    private String city_id;
    private String district_id;
    private String address;
    private String is_default;
    private String contact_name;
    private String contact_phone;

    public EditAddressBuilder(Builder builder){
        this.contact_id = builder.contact_id;
        this.province_id = builder.province_id;
        this.city_id = builder.city_id;
        this.district_id = builder.district_id;
        this.address = builder.address;
        this.is_default = builder.is_default;
        this.contact_name = builder.contact_name;
        this.contact_phone = builder.contact_phone;

    }

    public int getContact_id() {
        return contact_id;
    }

    public void setContact_id(int contact_id) {
        this.contact_id = contact_id;
    }

    public String getProvince_id() {
        return province_id;
    }

    public void setProvince_id(String province_id) {
        this.province_id = province_id;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(String district_id) {
        this.district_id = district_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIs_default() {
        return is_default;
    }

    public void setIs_default(String is_default) {
        this.is_default = is_default;
    }

    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public String getContact_phone() {
        return contact_phone;
    }

    public void setContact_phone(String contact_phone) {
        this.contact_phone = contact_phone;
    }

    public static class Builder{
        private int contact_id;
        private String province_id;
        private String city_id;
        private String district_id;
        private String address;
        private String is_default;
        private String contact_name;
        private String contact_phone;

        public Builder contact_id(int contact_id){
            this.contact_id = contact_id;
            return this;
        }
        public Builder province_id(String province_id){
            this.province_id = province_id;
            return this;
        }
        public Builder city_id(String city_id){
            this.city_id = city_id;
            return this;
        }
        public Builder district_id(String district_id){
            this.district_id = district_id;
            return this;
        }
        public Builder address(String address){
            this.address = address;
            return this;
        }
        public Builder is_default(String is_default){
            this.is_default = is_default;
            return this;
        }
        public Builder contact_name(String contact_name){
            this.contact_name = contact_name;
            return this;
        }
        public Builder contact_phone(String contact_phone){
            this.contact_phone = contact_phone;
            return this;
        }
        public EditAddressBuilder build() {
            return new EditAddressBuilder(this);
        }
    }
}
