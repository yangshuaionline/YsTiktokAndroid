package com.yangshuai.module.pay.request;

import com.yangshuai.library.base.interfaces.ResponseListener;
import com.yangshuai.module.pay.bean.BuyServiceBean;

import java.io.Serializable;

/**
 * @Author yangshuai
 * @Date : 2023-02-14 16:52
 * @Version 1.0
 * Description:服务购买请求参数
 */
public class ServiceRequest extends BaseRequestBean implements Serializable {
    private String serviceId;//服务id
    private String price;//服务价格


    private String nickName;//昵称
    private String avatarUrl;//头像
    private String ministrantGoodUrl;//	商品图片
    private String billVipAmt;//订单金额
    private String ministrantGoodName;//商品名称
    private String billdateEnd;//订单支付截至时间
    private String businessName;
    public ServiceRequest(){
        super();
    }
    /**
     * 不需要处理回调时的调用
     * @param price 支付金额
     * @param serviceId 服务id
     * */
    public ServiceRequest(String price, String serviceId){
        this.price = price;
        this.serviceId = serviceId;
    }
    /**
     * 需要处理回调时的调用
     * @param price 支付金额
     * @param serviceId 服务id
     * @param listener 生成订单处理结果回调（如果需要特殊处理事件时调用）
     * */
    public ServiceRequest(String price, String serviceId, ResponseListener<BuyServiceBean> listener){
        this(price,serviceId);
        this.setListener(listener);
    }
    public String getServiceId() {
        return serviceId;
    }
    public String getPrice() {
        return price;
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

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
