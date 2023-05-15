package com.yangshuai.module.pay.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.yangshuai.library.base.BaseConst
import com.yangshuai.library.base.BaseToolBarActivity
import com.yangshuai.library.base.interfaces.ResponseListener
import com.yangshuai.library.base.utils.ToastUtil
import com.yangshuai.module.pay.PayRouterPath
import com.yangshuai.module.pay.R
import com.yangshuai.module.pay.bean.BuyServiceBean
import com.yangshuai.module.pay.bean.PayBean
import com.yangshuai.module.pay.databinding.PayActConfirmOrderBinding
import com.yangshuai.module.pay.request.ResourceRequest
import com.yangshuai.module.pay.request.ServiceRequest
import com.yangshuai.module.pay.viewmodel.ConfirmOrderActViewModel
import io.reactivex.disposables.Disposable
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.lang.Exception

/**
 * <pre>
 *     author : pty
 *     time   : 2022 07 08 16:26
 *     desc   :  确认订单
 * </pre>
 */
@Route(path = PayRouterPath.ROUT_CONFIRM_ORDER_PATH)
class ConfirmOrderActivity : BaseToolBarActivity<PayActConfirmOrderBinding, ConfirmOrderActViewModel>() {

    @Autowired
    @JvmField
    var data: BuyServiceBean.DataBean? = null//资产场景服务使用
    @Autowired
    @JvmField
    var dataResource: ResourceRequest? = null//资产场景服务使用
    @Autowired
    @JvmField
    var dataService: ServiceRequest? = null//市场服务使用
    @Autowired
    @JvmField
    var RouterType:String?=null
    @Autowired
    @JvmField
    var fromType: String? = null
    @Autowired
    @JvmField
    var serviceId:String? = null
    @Autowired
    @JvmField
    var planId:String? = null
    @Autowired
    @JvmField
    var id:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pay_act_confirm_order)
        setToolbarTitleCenter("确认订单")
    }
    private  val TAG = "ConfirmOrderActivity"
    override fun initData() {
        super.initData()
        mBinding.viewModel = mViewModel
        mViewModel.activity = this
        initView()
    }

    private fun initView() {
        when(fromType){
            BaseConst.FROM_ASSET->{
                val d = BuyServiceBean.DataBean()
                dataResource?.let {
                    d.avatarUrl = it.avatarUrl
                    d.nickName = it.nickName
                    d.ministrantGoodUrl = it.ministrantGoodUrl
                    d.ministrantGoodName = it.ministrantGoodName
                    d.billVipAmt = it.billVipAmt
                    d.businessName = it.businessName
                    it.payType?.let {type->
                        d.payType = type.toInt()
                    }
                    d.payTypeDesc = it.payTypeDesc
                    d.excessReturnRate = it.excessReturnRate
                }
                data = d
                mBinding.data = data
            }
            else->{
                val d = BuyServiceBean.DataBean()
                dataService?.let {
                    d.avatarUrl = it.avatarUrl
                    d.nickName = it.nickName
                    d.ministrantGoodUrl = it.ministrantGoodUrl
                    d.ministrantGoodName = it.ministrantGoodName
                    d.billVipAmt = it.billVipAmt
                    d.businessName = it.businessName
                }
                data = d
                mBinding.data = data
            }
        }
        mViewModel.fromType = fromType
        mBinding.tvNextStep.setOnClickListener {
            setPay()
        }
        //todo 剪切板功能，缺少弹窗提示，UI确认是否需要
        var putOrGet = true
        mBinding.tvOrderCopy.setOnClickListener{
            try {
                if(putOrGet){
//                    CopyClipUtils.putTextIntoClip(this, mBinding.tvOrderNumShow.text.toString())
                }
                putOrGet = !putOrGet
            }catch (e:Exception){
            }

        }
        mBinding.rbNeed.isChecked = true
        mBinding.rbNeed.setOnClickListener {
            data?.needFlag = BaseConst.NEED_SEEL
        }
        mBinding.rbUnNeed.setOnClickListener {
            data?.needFlag = BaseConst.UN_NEED_SEEL
        }
    }

    override fun initParam() {
        super.initParam()
        ARouter.getInstance().inject(this)
        EventBus.getDefault().register(this)
    }
    /**
     *关闭界面
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public  fun onEvent(event:String) {
        when(event){
//            EvenCommon.MARKER-> {
//                finish()
//            }
//            EvenCommon.IMMIGRATION_VALUE-> {
//
//            }
        }
    }

    private fun setPay(){
        serviceId?.let { it1 ->
            data?.billVipAmt?.let {
                mViewModel.getData(it,planId,data?.needFlag, it1, data?.excessReturnRate,object :
                    ResponseListener<PayBean> {
                    override fun loadSuccess(d: PayBean?) {
                        if (d!=null){
                            //                            d.code = 200
//                            when(d.code){
//                                // 20014：请签署服务确认书后在邀请服务商协作完成当前业务分析
//                                20014->{
//                                    var dialogBuilder: DialogBuilder?=null
//                                    dialogBuilder = DialogBuilder(this@ConfirmOrderActivity)
//                                        .setAlert(d.message)
//                                        .setTitle("提示")
//                                        .setLeftText("稍后签署")
//                                        .setLeftListener { dialogBuilder?.dismiss() }
//                                        .setRightText("立即签署")
//                                        .setRightListener {
//                                            dialogBuilder?.dismiss()
//                                            val url =
//                                                BaseConst.H5URL + BaseConst.SERVICE_CONTENT
//                                            ARouter.getInstance()
//                                                .build(RouterPath.APPMain.ROUTE_MAIN_WEB_VIEW)
//                                                .withString("url", url)
//                                                .withString("barTitle", "确认协议")
//                                                .withString("RoutType", "4")
//                                                .navigation()
//                                        }
//                                        .build() as DialogBuilder?
//                                    dialogBuilder?.show()
//                                }
//                                //20015：服务确认书审核失败，原因：xxxxxxxx
//                                20015->{
//                                    var dialog: DialogBuilder?=null
//                                    dialog = DialogBuilder(this@ConfirmOrderActivity)
//                                        .setAlert(d.message,d.explain)
//                                        .setTitle("提示")
//                                        .setLeftText("暂不认证")
//                                        .setLeftListener { dialog?.dismiss() }
//                                        .setRightText("重新认证")
//                                        .setRightListener {
//                                            dialog?.dismiss()
//                                            ARouter.getInstance().build(RouterPath.Mine.ROUTE_MINE_AGENCY_AUTH).navigation()
//                                        }
//                                        .build() as DialogBuilder?
//                                    dialog?.show()
//                                }
//                                //20016：服务确认书签署中
//                                20016->{
//                                    var dialog: DialogBuilder?=null
//                                    dialog = DialogBuilder(this@ConfirmOrderActivity)
//                                        .setAlert(d.message)
//                                        .setTitle("提示")
//                                        .setCenterText("知道了")
//                                        .setCenterListener{ dialog?.dismiss() }
//                                        .build() as DialogBuilder?
//                                    dialog?.show()
//                                }
//                                //                                // 20007：机构审核未通过，请重新进行认证 原因：个人未实名,未人脸
//                                //                                20007->{
//                                //                                    var dialog: DialogBuilder?=null
//                                //                                    dialog = DialogBuilder(this@ConfirmOrderActivity)
//                                //                                        .setAlert(d.message)
//                                //                                        .setTitle("提示")
//                                //                                        .setLeftText("暂不实名")
//                                //                                        .setLeftListener { dialog?.dismiss() }
//                                //                                        .setRightText("去实名")
//                                //                                        .setRightListener {
//                                //                                            dialog?.dismiss()
//                                //                                            ARouter.getInstance()
//                                //                                                .build(RouterPath.Mine.ROUTE_MINE_REALNAME)
//                                //                                                .withString("source","1")
//                                //                                                .navigation()
//                                //                                        }
//                                //                                        .build() as DialogBuilder?
//                                //                                    dialog?.show()
//                                //                                }
//                                //                                // 20008：机构审核未通过，请重新进行认证 原因：个人已实名,未人脸
//                                //                                20008->{
//                                //                                    var dialog: DialogBuilder?=null
//                                //                                    dialog = DialogBuilder(this@ConfirmOrderActivity)
//                                //                                        .setAlert(d.message)
//                                //                                        .setTitle("提示")
//                                //                                        .setLeftText("暂不认证")
//                                //                                        .setLeftListener { dialog?.dismiss() }
//                                //                                        .setRightText("去认证")
//                                //                                        .setRightListener {
//                                //                                            dialog?.dismiss()
//                                //                                            //it.url 完整的补人脸识别地址
//                                //                                            ARouter.getInstance().build(RouterPath.APPMain.ROUTE_MAIN_EWEB_VIEW)
//                                //                                                .withString("url",d.url)
//                                //                                                .withString("barTitle","人脸识别")
//                                //                                                .withString("addtVersionName","1")
//                                //                                                .navigation()
//                                //                                        }
//                                //                                        .build() as DialogBuilder?
//                                //                                    dialog?.show()
//                                //                                }
//                                //                                // 20009：机构审核未通过，请重新进行认证 原因：机构待认证
//                                //                                // 20011：机构审核未通过，请重新进行认证 原因：机构未认证
//                                //                                20009,20011->{
//                                //                                    var dialog: DialogBuilder?=null
//                                //                                    dialog = DialogBuilder(this@ConfirmOrderActivity)
//                                //                                        .setAlert(d.message)
//                                //                                        .setTitle("提示")
//                                //                                        .setLeftText("暂不认证")
//                                //                                        .setLeftListener { dialog?.dismiss() }
//                                //                                        .setRightText("重新认证")
//                                //                                        .setRightListener {
//                                //                                            dialog?.dismiss()
//                                //                                            ARouter.getInstance().build(RouterPath.Mine.ROUTE_MINE_AGENCY_AUTH).navigation()
//                                //                                        }
//                                //                                        .build() as DialogBuilder?
//                                //                                    dialog?.show()
//                                //                                }
//                                //                                // 20010：机构认证中
//                                //                                20010->{
//                                //                                    var dialog: DialogBuilder?=null
//                                //                                    dialog = DialogBuilder(this@ConfirmOrderActivity)
//                                //                                        .setAlert(d.message)
//                                //                                        .setTitle("提示")
//                                //                                        .setCenterText("知道了")
//                                //                                        .setCenterListener{ dialog?.dismiss() }
//                                //                                        .build() as DialogBuilder?
//                                //                                    dialog?.show()
//                                //                                }
//                                //                                // 20012：机构审核未通过，请重新进行认证 原因：机构已认证,缺失法人信息
//                                //                                20012->{
//                                //                                    var dialog: DialogBuilder?=null
//                                //                                    dialog = DialogBuilder(this@ConfirmOrderActivity)
//                                //                                        .setAlert(d.message)
//                                //                                        .setTitle("提示")
//                                //                                        .setLeftText("暂不认证")
//                                //                                        .setLeftListener { dialog?.dismiss() }
//                                //                                        .setRightText("重新认证")
//                                //                                        .setRightListener {
//                                //                                            dialog?.dismiss()
//                                //                                            ARouter.getInstance().build(RouterPath.Mine.ROUTE_MINE_REALNAME)
//                                //                                                .withString("source","10000")
//                                //                                                .navigation()
//                                //                                        }
//                                //                                        .build() as DialogBuilder?
//                                //                                    dialog?.show()
//                                //                                }
//                                //                                // 20013：机构审核未通过，请重新进行认证 原因：缺失合同邮寄地址信息
//                                //                                20013->{
//                                //                                    var dialog: DialogBuilder?=null
//                                //                                    dialog = DialogBuilder(this@ConfirmOrderActivity)
//                                //                                        .setAlert(d.message)
//                                //                                        .setTitle("提示")
//                                //                                        .setLeftText("暂不认证")
//                                //                                        .setLeftListener { dialog?.dismiss() }
//                                //                                        .setRightText("重新认证")
//                                //                                        .setRightListener {
//                                //                                            dialog?.dismiss()
//                                //                                            ARouter.getInstance()
//                                //                                                .build(RouterPath.Mine.ROUTE_MINE_CONTRACT_MAILING)
//                                //                                                .withString("type","10000")
//                                //                                                .navigation()
//                                //                                        }
//                                //                                        .build() as DialogBuilder?
//                                //                                    dialog?.show()
//                                //                                }
//                                //200：正常支付
//                                200->{
//                                    //                                    Log.i("=================>","========>${d.responseMap?.qrAuthCode?.getqRAuthCode()}")
//                                    //                                    Log.i("=================>","========>${d.responseMap?.qrAuthCode?.toString()}")
//                                    //                                    Log.i("=================>","========>${d.responseMap?.toString()}")
//                                    ARouter.getInstance().build(PayRouterPath.ROUT_PAY_PATH)
//                                        .withString("xsdmxBillNo", d.responseMap?.qrAuthCode?.orderBillNo)
//                                        .withString("billVipAmt", data?.billVipAmt)
//                                        .withString("id",id)
//                                        .withString("type","1")
//                                        .withString("fromType",fromType)
//                                        .withString("RouterType",RouterType)
//                                        .withString("qRAuthCode",d.responseMap?.qrAuthCode?.getqRAuthCode())
//                                        .navigation()
//                                }
//                                else->{
//
//                                }
//                            }
                        }
                    }

                    override fun loadFailed(errorCode: String?, errorMsg: String?) {
                        ToastUtil.Short("${errorCode}${errorMsg}")
                    }

                    override fun addDisposable(disposable: Disposable?) {
                    }

                })
            }
        }
    }
}