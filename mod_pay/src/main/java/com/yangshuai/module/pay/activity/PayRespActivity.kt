package com.yangshuai.module.pay.activity

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.yangshuai.library.base.BaseToolBarActivity
import com.yangshuai.library.base.viewmodel.NoViewModel
import com.yangshuai.module.pay.PayRouterPath
import com.yangshuai.module.pay.R
import com.yangshuai.module.pay.databinding.PayActPayRespBinding

@Route(path = PayRouterPath.ROUT_PAY_RES_PATH)
class PayRespActivity : BaseToolBarActivity<PayActPayRespBinding, NoViewModel>() {

   @JvmField
   @Autowired
    var xsdmxBillNo:String?=null
    @JvmField
    @Autowired
    var name:String?=null
    @JvmField
    @Autowired
    var orderAmt:String?=null
    @JvmField
    @Autowired
    var time:String?=null
    @JvmField
    @Autowired
    var id:String?=null
    @JvmField
    @Autowired
    var RouterType:String?=null
    @JvmField
    @Autowired
    var businessId:String?=null
    @JvmField
    @Autowired
    var listPlanNo:String?=null
    @JvmField
    @Autowired
    var modelName:String?=null
    @Autowired
    @JvmField
    var fromType: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pay_act_pay_resp)
        mToolBar.visibility =View.GONE
        mBinding.id=xsdmxBillNo
        mBinding.name=name
        mBinding.price=orderAmt
        mBinding.time=time
        mBinding.tvGoPay.setOnClickListener {
//            ARouter.getInstance()
//                .build(RouterPath.Mine.ROUTE_MINE_ORDER_CENTER_DETAILS)
//                .withString("orderId",id)
//                .withString("fromType",fromType)
//                .navigation()
//            finish()
        }
    }

    override fun initParam() {
        ARouter.getInstance().inject(this)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if ("2"==RouterType||"3"==RouterType){
//            //业务详情
//            ARouter.getInstance()
//                    .build(RouterPath.Value.ROUTE_VALUE_BUILD_DETAILS_PATH)
//                    .withString("listPlanNo",listPlanNo)
//                    .withString("businessId",businessId)
//                    .withString("titleText",modelName)
//                    .navigation()
        }
        finish()
    }

}