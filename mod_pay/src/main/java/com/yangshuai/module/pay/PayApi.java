package com.yangshuai.module.pay;

import com.yangshuai.library.base.entity.BaseResponse;
import com.yangshuai.module.pay.bean.BuyServiceBean;
import com.yangshuai.module.pay.bean.OrderListBean;
import com.yangshuai.module.pay.bean.PayBean;
import com.yangshuai.module.pay.claim.bean.ClaimFlagBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @Author yangshuai
 * @Date : 2023-02-14 16:30
 * @Version 1.0
 * Description:支付接口
 */
public interface PayApi {
    /**
     *购买服务 生成订单
     * */
    @POST("/xxth-market/app/order/placeAnOrder")
    Observable<BuyServiceBean> getBuyService(@Body Map<String,String> request);
    /**
     *服务商邀约，资产场景服务购买 生成订单
     * */
    @POST("/xxth-market/app/order/placeSceneOrder")
    Observable<BuyServiceBean> placeSceneOrder(@Body Map<String,String> request);
    /**
     * 查询支付状态(订单详情)
     */
    @POST("/xxth-ucenter/app/sysXsdMx/xsdMxDetail")
    Observable<BaseResponse<OrderListBean.RecordsBean>> OrderDetail(@Body Map<String,String> request);
    /**
     *支付
     * */
    @POST("/xxth-ucenter/app/trade/unifiedOrder")
    Observable<BaseResponse<PayBean>> getPay(@Body Map<String,String> request);
    /**
     *二次支付
     * */
    @POST("/xxth-ucenter/app/trade/secondUnifiedOrder")
    Observable<BaseResponse<PayBean>> getSecondPay(@Body Map<String,String> request);

    /**
     * 企业认领->立即认领
     */
    @POST("/xxth-worth/app/listPlan/claimFlagUpdate")
    Observable<BaseResponse<ClaimFlagBean>> claimFlagUpdate(@Body Map<String, String> map);

    /**
     * 授权确认
     */
    @POST("/xxth-worth/app/listPlan/addAuthorization")
    Observable<BaseResponse<Object>> addAuthorization(@Body Map<String, String> map);
}
