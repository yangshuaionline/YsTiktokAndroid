package com.yangshuai.module.pay
/**
 * @Author yangshuai
 * @Date : 2023-02-20 17:55
 * @Version 1.0
 * Description:
 */
class PayRouterPath {
    companion object{
        private const val PAY = "/Pay"
        //服务市场 列表 详情 购买服务 支付页面
        const val ROUT_PAY_PATH = "$PAY/PayActivity"
        //服务市场 列表 详情 购买服务
        const val ROUT_CONFIRM_ORDER_PATH = "$PAY/ConfirmOrderActivity"
        //服务市场 列表支付结果界面
        const val ROUT_PAY_RES_PATH = "$PAY/PayRespActivity"
        //授权确认
        const val ROUT_PAY_AUTHORIZE = "$PAY/Authorize"
    }
}