package com.yangshuai.module.pay.bean;

import android.text.TextUtils;


import com.yangshuai.library.base.BaseConst;

import java.util.List;

/**
 * @author： pc$
 * @Create on： 订单数据类型$ 9:58:52$
 * @Dec：
 */

public class OrderListBean {

    private List<RecordsBean> records;//列表数据
    private Integer total;//总条目
    private Integer size;//总大小
    private Integer current;//
    private List<?> orders;
    private Boolean optimizeCountSql;
    private Boolean hitCount;
    private Boolean searchCount;
    private Integer pages;

    public List<RecordsBean> getRecords() {
        return records;
    }

    public void setRecords(List<RecordsBean> records) {
        this.records = records;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public List<?> getOrders() {
        return orders;
    }

    public void setOrders(List<?> orders) {
        this.orders = orders;
    }

    public Boolean getOptimizeCountSql() {
        return optimizeCountSql;
    }

    public void setOptimizeCountSql(Boolean optimizeCountSql) {
        this.optimizeCountSql = optimizeCountSql;
    }

    public Boolean getHitCount() {
        return hitCount;
    }

    public void setHitCount(Boolean hitCount) {
        this.hitCount = hitCount;
    }

    public Boolean getSearchCount() {
        return searchCount;
    }

    public void setSearchCount(Boolean searchCount) {
        this.searchCount = searchCount;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }


    public static class RecordsBean {
        private String id;//主键id
        private String xsdmxBillno;//订单编号
        private String ministrantUserName;//服务商编号
        private String ministrantGoodName;//服务类别
        private String billdate;//下单时间
        private String orderAmt;//订单金额
        private String payStatus;//支付状态
        private String payStatusDesc;//支付状态描述
        private String payIsOnline;//支付方式
        private String payIsOnlineDesc;//支付方式描述
        private String payTime;//支付时间
        private String ministrantGoodUrl;//商品图片url
        private String billdateEnd;//截止时间
        private String message;
        private Integer projectStatus ;
        private String businessIdDesc;
        private String businessId;
        private String listPlanNo;
        private String isSign;//双方签约状态 1：双方都已签约（隐藏退款按钮） 0：至少有一方未签约
        private String ministrantAvatarUrl;//服务商职业照
        private String excessReturnRate;//超额收益比例
        private String needFlagDesc;//是否需要公章描述
        private boolean needFlag;//是否需要公章

        public String getNeedFlagDesc() {
            return TextUtils.isEmpty(needFlagDesc)? BaseConst.NULL:needFlagDesc;
        }

        public void setNeedFlagDesc(String needFlagDesc) {
            this.needFlagDesc = needFlagDesc;
        }

        public boolean isNeedFlag() {
            return needFlag;
        }

        public void setNeedFlag(boolean needFlag) {
            this.needFlag = needFlag;
        }

        public String getExcessReturnRate() {
            return TextUtils.isEmpty(excessReturnRate)?BaseConst.NULL:excessReturnRate+"%";
        }

        public void setExcessReturnRate(String excessReturnRate) {
            this.excessReturnRate = excessReturnRate;
        }

        public String getMinistrantAvatarUrl() {
            return ministrantAvatarUrl;
        }

        public void setMinistrantAvatarUrl(String ministrantAvatarUrl) {
            this.ministrantAvatarUrl = ministrantAvatarUrl;
        }

        public String getIsSign() {
            return isSign;
        }

        public void setIsSign(String isSign) {
            this.isSign = isSign;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getXsdmxBillno() {
            return xsdmxBillno;
        }
        public String getXsdmxBillnoDec() {
            return xsdmxBillno.isEmpty()?"订单编号：--":"订单编号："+xsdmxBillno;
        }
        public void setXsdmxBillno(String xsdmxBillno) {
            this.xsdmxBillno = xsdmxBillno;
        }

        public String getMinistrantUserName() {
            return TextUtils.isEmpty(ministrantUserName)?"服务商：--":"服务商："+ministrantUserName;
        }

        public void setMinistrantUserName(String ministrantUserName) {
            this.ministrantUserName = ministrantUserName;
        }

        public String getMinistrantGoodName() {
            return TextUtils.isEmpty(ministrantGoodName)? BaseConst.NULL:ministrantGoodName.toString() ;
        }

        public void setMinistrantGoodName(String ministrantGoodName) {
            this.ministrantGoodName = ministrantGoodName;
        }

        public String getBilldate() {
            return billdate;
        }

        public void setBilldate(String billdate) {
            this.billdate = billdate;
        }

        public String getOrderAmt() {
            return TextUtils.isEmpty(orderAmt)?"--":orderAmt;
        }

        public void setOrderAmt(String orderAmt) {
            this.orderAmt = orderAmt;
        }
        public boolean getpay(){
            return "1".equals(payStatus);
        }
        public String gettext(){
            return "需付款："+orderAmt+"元";
        }
        public String getPayStatus() {
            return payStatus;
        }
        public int getPayStatustoInt() {
            return payStatus.isEmpty()?0:Integer.parseInt(payStatus);
        }
        public void setPayStatus(String payStatus) {
            this.payStatus = payStatus;
        }

        public String getPayStatusDesc() {
            return payStatusDesc;
        }

        public void setPayStatusDesc(String payStatusDesc) {
            this.payStatusDesc = payStatusDesc;
        }

        public String getPayIsOnline() {
            return payIsOnline;
        }

        public void setPayIsOnline(String payIsOnline) {
            this.payIsOnline = payIsOnline;
        }

        public String getPayIsOnlineDesc() {
            return payIsOnlineDesc;
        }

        public void setPayIsOnlineDesc(String payIsOnlineDesc) {
            this.payIsOnlineDesc = payIsOnlineDesc;
        }

        public String getPayTime() {
            return payTime;
        }

        public void setPayTime(String payTime) {
            this.payTime = payTime;
        }

        public String getMinistrantGoodUrl() {
            return ministrantGoodUrl;
        }

        public void setMinistrantGoodUrl(String ministrantGoodUrl) {
            this.ministrantGoodUrl = ministrantGoodUrl;
        }
        public String getBilldateDec(){
            return TextUtils.isEmpty(billdate)?"下单时间：--":"下单时间："+billdate;

        }
        public String getTime(){
            return "1".equals(payStatus)?billdate:payTime;

        }
        public String getTimeDec(){
            return "1".equals(payStatus)?"下单时间：":"付款时间";

        }
        public String getPayNumDec(){
            return orderAmt.isEmpty()?"--“：":"¥"+orderAmt;

        }

        public String getBilldateEnd() {
            return billdateEnd;
        }

        public void setBilldateEnd(String billdateEnd) {
            this.billdateEnd = billdateEnd;
        }

        public String getMessage() {
            return TextUtils.isEmpty(message)?"退款原因：--":"退款原因："+message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Integer getProjectStatus() {
            return projectStatus;
        }

        public void setProjectStatus(Integer projectStatus) {
            this.projectStatus = projectStatus;
        }

        public String getBusinessIdDesc() {
            return businessIdDesc;

        }

        public void setBusinessIdDesc(String businessIdDesc) {
            this.businessIdDesc = businessIdDesc;
        }

        public String getBusinessId() {
            return businessId;
        }

        public void setBusinessId(String businessId) {
            this.businessId = businessId;
        }

        public String getListPlanNo() {
            return listPlanNo;
        }

        public void setListPlanNo(String listPlanNo) {
            this.listPlanNo = listPlanNo;
        }
    }
}