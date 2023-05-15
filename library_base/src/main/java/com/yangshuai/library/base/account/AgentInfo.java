package com.yangshuai.library.base.account;

import java.io.Serializable;

/**
 * @author hcp
 * Create on 2019-12-16 16:45
 */
public class AgentInfo implements Serializable {
    private String agentId; // 经纪人Id
    private String branchSimpleName; // 分公司名称
    private String brandSimpleName; // 品牌名称
    private int isAgent; // 状态判断(1:经纪人 2:在职员工 3:被禁用经纪人 4:非入职员工)
    private StorePoBean storePo; // 门店信息

    public String getBrandSimpleName() {
        return brandSimpleName;
    }

    public void setBrandSimpleName(String brandSimpleName) {
        this.brandSimpleName = brandSimpleName;
    }

    public StorePoBean getStorePo() {
        return storePo;
    }

    public void setStorePo(StorePoBean storePo) {
        this.storePo = storePo;
    }

    public String getBranchSimpleName() {
        return branchSimpleName;
    }

    public void setBranchSimpleName(String branchSimpleName) {
        this.branchSimpleName = branchSimpleName;
    }

    public int getIsAgent() {
        return isAgent;
    }

    public void setIsAgent(int isAgent) {
        this.isAgent = isAgent;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public static class StorePoBean implements Serializable {
        private String address; // 门店地址
        private String simpleAddress; // 简单门店地址
        private String storeCode; // 门店码
        private String storeEnabled; // 门店禁用状态
        private String storeId;
        private String storeName; // 门店名称

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getSimpleAddress() {
            return simpleAddress;
        }

        public void setSimpleAddress(String simpleAddress) {
            this.simpleAddress = simpleAddress;
        }

        public String getStoreCode() {
            return storeCode;
        }

        public void setStoreCode(String storeCode) {
            this.storeCode = storeCode;
        }

        public String getStoreEnabled() {
            return storeEnabled;
        }

        public void setStoreEnabled(String storeEnabled) {
            this.storeEnabled = storeEnabled;
        }

        public String getStoreId() {
            return storeId;
        }

        public void setStoreId(String storeId) {
            this.storeId = storeId;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }
    }
}
