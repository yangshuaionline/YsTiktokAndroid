package com.yangshuai.module.pay.request;

import com.yangshuai.library.base.interfaces.ResponseListener;
import com.yangshuai.module.pay.bean.BuyServiceBean;

import java.io.Serializable;

/**
 * @Author yangshuai
 * @Date : 2023-02-14 16:52
 * @Version 1.0
 * Description:资产场景服务邀请请求参数
 */
public class ResourceRequest extends BaseRequestBean implements Serializable {
    private String id;//服务商id
    private String serviceId;//商品id
    private String planId;//资产场景服务计划id
    private String price;//现金金额
    private String excessReturnRate;//超额收益比例 支持两位小数，不带%


    private String nickName;//昵称
    private String avatarUrl;//头像
    private String ministrantGoodUrl;//	商品图片
    private String billVipAmt;//订单金额
    private String ministrantGoodName;//商品名称
    private String billdateEnd;//订单支付截至时间
    private String businessName;
    private String payTypeDesc;
    private String payType;
    /**
     * 不需要处理回调时的调用
     * @param id 服务商id
     * @param serviceId 商品id
     * @param planId 资产场景服务计划id
     * @param price 现金金额
     * */
    public ResourceRequest(String id, String serviceId, String planId, String price){
        this.id = id;
        this.serviceId = serviceId;
        this.planId = planId;
        this.price = price;
    }
    /**
     * 不需要处理回调时的调用
     * @param id 服务商id
     * @param serviceId 商品id
     * @param planId 资产场景服务计划id
     * @param price 现金金额
     * @param excessReturnRate 超额收益比例 支持两位小数，不带%
     * */
    public ResourceRequest(String id, String serviceId, String planId, String price, String excessReturnRate){
        this(id,serviceId,planId,price);
        this.excessReturnRate = excessReturnRate;
    }
    /**
     * 不需要处理回调时的调用
     * @param id 服务商id
     * @param serviceId 商品id
     * @param planId 资产场景服务计划id
     * @param price 现金金额
     * @param excessReturnRate 超额收益比例 支持两位小数，不带%
     * @param listener 生成订单处理结果回调（如果需要特殊处理事件时调用）
     * */
    public ResourceRequest(String id, String serviceId, String planId, String price, String excessReturnRate, ResponseListener<BuyServiceBean> listener){
        this(id,serviceId,planId,price,excessReturnRate);
        this.setListener(listener);
    }
    public String getServiceId() {
        return serviceId;
    }

    public String getPlanId() {
        return planId;
    }

    public String getPrice() {
        return price;
    }

    public String getExcessReturnRate() {
        return excessReturnRate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getMinistrantGoodUrl() {
        return ministrantGoodUrl;
    }

    public void setMinistrantGoodUrl(String ministrantGoodUrl) {
        this.ministrantGoodUrl = ministrantGoodUrl;
    }

    public String getBillVipAmt() {
        return billVipAmt;
    }

    public void setBillVipAmt(String billVipAmt) {
        this.billVipAmt = billVipAmt;
    }

    public String getMinistrantGoodName() {
        return ministrantGoodName;
    }

    public void setMinistrantGoodName(String ministrantGoodName) {
        this.ministrantGoodName = ministrantGoodName;
    }

    public String getBilldateEnd() {
        return billdateEnd;
    }

    public void setBilldateEnd(String billdateEnd) {
        this.billdateEnd = billdateEnd;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getPayTypeDesc() {
        return payTypeDesc;
    }

    public void setPayTypeDesc(String payTypeDesc) {
        this.payTypeDesc = payTypeDesc;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }
}
