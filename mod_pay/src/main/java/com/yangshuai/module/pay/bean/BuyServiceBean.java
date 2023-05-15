package com.yangshuai.module.pay.bean;

import android.text.TextUtils;

import com.yangshuai.library.base.BaseConst;

import java.io.Serializable;
import java.util.List;

/**
 * @Author yangshuai
 * @Date 2023-04-12 周三 11:52
 * @Description TODO
 */

public class BuyServiceBean implements Serializable {

    private String code;//60001：已购买&&不可对自己下单 60002：未完成企业基础&&未完成上市目标
    private String message;
    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBean implements Serializable{
        private String nickName;//昵称
        private String avatarUrl;//头像
        private String xsdmxBillno;//订单号
        private Integer businessId;//业务编号
        private String businessName;//业务描述
        private String ministrantGoodUrl;//	商品图片
        private String billVipAmt;//订单金额
        private String ministrantGoodName;//商品名称
        private List<String> ministrantGoodUrlList;//商品图片集合
        private String id;//订单id
        private String billdateEnd;//订单支付截至时间
        //2.1.0
        private int payType;//支付类型：0: 现金; 1: 现金+超额收益
        private String payTypeDesc;//支付方式描述
        private String excessReturnRate;//超额收益比例
        //2.1.0确认订单页面使用
        private String needFlag = "1";//资产服务场景：是否需要公章 0: 否; 1: 是
        //2.1.0新版下单
        private int code;//状态码 0~6（除去2）：审核未通过 2：已授权投行提示 7：非工商注册企业 8：再次认领 9：未完成上市目标 10：未完成企业基础 11：服务确认书签署中 12：已购买当前服务 13：不可购买自己发布的服务 14：二次下单 15：正常下单
        private String message;
        private String explain;//失败原因
        private ContractInfo contractInfo;//code 为60010的时候返回的数据
        private String price;//订单金额

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getNickName() {
            return TextUtils.isEmpty(nickName)?BaseConst.NULL:nickName;
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

        public String getExplain() {
            return explain;
        }

        public void setExplain(String explain) {
            this.explain = explain;
        }

        public String getXsdmxBillno() {
            return xsdmxBillno;
        }

        public void setXsdmxBillno(String xsdmxBillno) {
            this.xsdmxBillno = xsdmxBillno;
        }

        public Integer getBusinessId() {
            return businessId;
        }

        public void setBusinessId(Integer businessId) {
            this.businessId = businessId;
        }

        public String getBusinessName() {
            return businessName;
        }

        public void setBusinessName(String businessName) {
            this.businessName = businessName;
        }

        public String getMinistrantGoodUrl() {
            return ministrantGoodUrl;
        }

        public void setMinistrantGoodUrl(String ministrantGoodUrl) {
            this.ministrantGoodUrl = ministrantGoodUrl;
        }

        public String getBillVipAmt() {
            return TextUtils.isEmpty(billVipAmt)? BaseConst.NULL :billVipAmt;
        }

        public void setBillVipAmt(String billVipAmt) {
            this.billVipAmt = billVipAmt;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getMinistrantGoodName() {
            return TextUtils.isEmpty(ministrantGoodName)?BaseConst.NULL:ministrantGoodName;
        }

        public void setMinistrantGoodName(String ministrantGoodName) {
            this.ministrantGoodName = ministrantGoodName;
        }

        public List<String> getMinistrantGoodUrlList() {
            return ministrantGoodUrlList;
        }

        public void setMinistrantGoodUrlList(List<String> ministrantGoodUrlList) {
            this.ministrantGoodUrlList = ministrantGoodUrlList;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getPayType() {
            return payType;
        }

        public void setPayType(int payType) {
            this.payType = payType;
        }

        public String getPayTypeDesc() {
            return TextUtils.isEmpty(payTypeDesc)?BaseConst.NULL:payTypeDesc;
        }

        public void setPayTypeDesc(String payTypeDesc) {
            this.payTypeDesc = payTypeDesc;
        }

        public String getNeedFlag() {
            return TextUtils.isEmpty(needFlag)? BaseConst.UN_NEED_SEEL:needFlag;
        }

        public void setNeedFlag(String needFlag) {
            this.needFlag = needFlag;
        }

        public String getExcessReturnRate() {
            return TextUtils.isEmpty(excessReturnRate)?BaseConst.NULL:excessReturnRate+"%";
        }

        public void setExcessReturnRate(String excessReturnRate) {
            this.excessReturnRate = excessReturnRate;
        }

        public String getBilldateEnd() {
            return TextUtils.isEmpty(billdateEnd)?BaseConst.NULL:billdateEnd;
        }

        public void setBilldateEnd(String billdateEnd) {
            this.billdateEnd = billdateEnd;
        }

        public ContractInfo getContractInfo() {
            return contractInfo;
        }

        public void setContractInfo(ContractInfo contractInfo) {
            this.contractInfo = contractInfo;
        }
    }
    public static class ContractInfo{
        private String explain;//提示
        private String address;//收件地址
        private String name;//收件人
        private String phone;//联系电话

        public String getExplain() {
            return explain;
        }

        public void setExplain(String explain) {
            this.explain = explain;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }
}
