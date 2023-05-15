package com.yangshuai.library.base.entity.city;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Author: Blincheng.
 * Date: 2017/5/9.
 * Description:
 */

public class AddressBean implements Serializable {
    public ArrayList<AddressItemBean> province = new ArrayList<>();
    public ArrayList<AddressItemBean> city = new ArrayList<>();
    public ArrayList<AddressItemBean> district = new ArrayList<>();

    public ArrayList<AddressItemBean> getProvince() {
        return province;
    }

    public void setProvince(ArrayList<AddressItemBean> province) {
        this.province = province;
    }

    public ArrayList<AddressItemBean> getCity() {
        return city;
    }

    public void setCity(ArrayList<AddressItemBean> city) {
        this.city = city;
    }

    public ArrayList<AddressItemBean> getDistrict() {
        return district;
    }

    public void setDistrict(ArrayList<AddressItemBean> district) {
        this.district = district;
    }

    public static class AddressItemBean implements Serializable, CityInterface {
        private String id;
        private String name;
        private String provinceId;

        @Override
        public String getCityName() {
            return name;
        }

        @Override
        public String getCityI() {
            return id;
        }

        @Override
        public String getCityP() {
            return provinceId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getProvinceId() {
            return provinceId;
        }

        public void setProvinceId(String provinceId) {
            this.provinceId = provinceId;
        }
    }
}
