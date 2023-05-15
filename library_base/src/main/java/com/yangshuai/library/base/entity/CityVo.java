package com.yangshuai.library.base.entity;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

/**
 * @Description: java类作用描述
 * @Author:
 * @CreateDate: 2019/11/18 12:04
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/11/18 12:04
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class CityVo extends LitePalSupport implements Serializable {


    /**
     * cityCode : 0
     * cityName :
     */


        private String cityCode;
        private String cityName;

        public String getCityCode() {
            return cityCode;
        }

        public void setCityCode(String cityCode) {
            this.cityCode = cityCode;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

}
