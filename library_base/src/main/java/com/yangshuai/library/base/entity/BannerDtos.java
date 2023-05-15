package com.yangshuai.library.base.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author hcp
 * Create on 2020-05-11 10:13
 */
public class BannerDtos implements Serializable {

    private List<CmsOpsContentsBean> cmsOpsContents;

    public List<CmsOpsContentsBean> getCmsOpsContents() {
        return cmsOpsContents;
    }

    public void setCmsOpsContents(List<CmsOpsContentsBean> cmsOpsContents) {
        this.cmsOpsContents = cmsOpsContents;
    }

    public static class CmsOpsContentsBean {
        /**
         * operateId : 708192676356296704
         * companyId : 440100
         * size : 1
         */

        private String cityCode;
        private String operateId;
        private String companyId;
        private int size;


        public String getCityCode() {
            return cityCode;
        }

        public void setCityCode(String cityCode) {
            this.cityCode = cityCode;
        }

        public String getOperateId() {
            return operateId;
        }

        public void setOperateId(String operateId) {
            this.operateId = operateId;
        }

        public String getCompanyId() {
            return companyId;
        }

        public void setCompanyId(String companyId) {
            this.companyId = companyId;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }
    }
}
