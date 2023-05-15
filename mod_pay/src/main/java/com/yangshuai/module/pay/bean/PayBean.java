package com.yangshuai.module.pay.bean;

import java.io.Serializable;

/**
 * @author： pc$
 * @Create on： \$ 13:26:05$
 * @Dec：
 */
public class PayBean implements Serializable {
    private int code;//状态码 1：请签署服务确认书后在邀请服务商协作完成当前业务分析
    private String message;
    private String explain;
    private String url;//人脸识别链接 当code=20008时有值
    private ResponseMap responseMap;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResponseMap getResponseMap() {
        return responseMap;
    }

    public void setResponseMap(ResponseMap responseMap) {
        this.responseMap = responseMap;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static class ResponseMap implements Serializable{
        private QRAuthCode QRAuthCode;

        public PayBean.QRAuthCode getQRAuthCode() {
            return QRAuthCode;
        }

        public void setQRAuthCode(PayBean.QRAuthCode QRAuthCode) {
            this.QRAuthCode = QRAuthCode;
        }
    }

    public static class QRAuthCode implements Serializable{
        private String qRAuthCode;//授权码
        private String orderBillNo;//用户订单号
        private String outTranNo;//外部渠道返回流水号
        private String tranNo;//平台订单号
        private String status;//订单状态

        public String getqRAuthCode() {
            return qRAuthCode;
        }

        public void setqRAuthCode(String qRAuthCode) {
            this.qRAuthCode = qRAuthCode;
        }

        public String getOrderBillNo() {
            return orderBillNo;
        }

        public void setOrderBillNo(String orderBillNo) {
            this.orderBillNo = orderBillNo;
        }

        public String getOutTranNo() {
            return outTranNo;
        }

        public void setOutTranNo(String outTranNo) {
            this.outTranNo = outTranNo;
        }

        public String getTranNo() {
            return tranNo;
        }

        public void setTranNo(String tranNo) {
            this.tranNo = tranNo;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    //支付接口对接授权码对象
    public static class Code{
        private String appid;
        private String sign;
        private String partnerid;
        private String prepayid;
        private String noncestr;
        private String timestamp;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }
    }

}
