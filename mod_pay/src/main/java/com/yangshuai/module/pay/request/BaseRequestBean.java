package com.yangshuai.module.pay.request;


import com.yangshuai.library.base.interfaces.ResponseListener;
import com.yangshuai.module.pay.bean.BuyServiceBean;

import java.io.Serializable;

/**
 * @Author yangshuai
 * @Date : 2023-02-14 16:36
 * @Version 1.0
 * Description:调用请求base类
 */
public class BaseRequestBean implements Serializable {
    private ResponseListener<BuyServiceBean> listener;//支付结果回调，只有跳转待支付成功或订单详情才回调

    public ResponseListener<BuyServiceBean> getListener() {
        return listener;
    }

    public void setListener(ResponseListener<BuyServiceBean> listener) {
        this.listener = listener;
    }
}
