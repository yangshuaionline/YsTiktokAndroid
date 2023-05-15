package com.yangshuai.library.base.entity;

import java.io.Serializable;

/**
 * @author hcp
 * Create on 2020-05-11 10:47
 */
public class BannerAutoField implements Serializable {

    /**
     * title : b端APP新房列表banner测试
     * sum : b端APP新房列表banner测试
     * url :
     * startTime :
     * endTime :
     * field1 : https://images-beta.yjyz.com/cms/contents/add/0adefacbe1974751bdfef426e5343028.jpg
     * appUrl : {"projectId":"644718672692563984","estateCode":"653447920886403216","prefix":"/newHouse/project/detail","prefixName":"B端新房详情页","name":"App原生页地址"}
     * redirectUrl : /newHouse/project/detail?projectId=644718672692563984&estateCode=653447920886403216
     * isAd : true
     */

    private String title;
    private String sum;
    private String url;
    private String startTime;
    private String endTime;
    private String field1;// imgUrl
    private String field2;// 是否必须登录访问
    private AppUrlBean appUrl;
    private String redirectUrl;
    private boolean isAd;


    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public AppUrlBean getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(AppUrlBean appUrl) {
        this.appUrl = appUrl;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public boolean isIsAd() {
        return isAd;
    }

    public void setIsAd(boolean isAd) {
        this.isAd = isAd;
    }

    public static class AppUrlBean {
        /**
         * projectId : 644718672692563984
         * estateCode : 653447920886403216
         * prefix : /newHouse/project/detail
         * prefixName : B端新房详情页
         * name : App原生页地址
         */

        private String projectId;
        private String estateCode;
        private String prefix;
        private String prefixName;
        private String name;

        public String getProjectId() {
            return projectId;
        }

        public void setProjectId(String projectId) {
            this.projectId = projectId;
        }

        public String getEstateCode() {
            return estateCode;
        }

        public void setEstateCode(String estateCode) {
            this.estateCode = estateCode;
        }

        public String getPrefix() {
            return prefix;
        }

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }

        public String getPrefixName() {
            return prefixName;
        }

        public void setPrefixName(String prefixName) {
            this.prefixName = prefixName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
