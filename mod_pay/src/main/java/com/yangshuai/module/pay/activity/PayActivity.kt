package com.yangshuai.module.pay.activity

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.yangshuai.library.base.BaseToolBarActivity
import com.yangshuai.library.base.dialog.LoadingDialog
import com.yangshuai.library.base.interfaces.ResponseListener
import com.yangshuai.library.base.network.RetrofitManager
import com.yangshuai.library.base.observer.ResponseObserver
import com.yangshuai.library.base.utils.RxUtils
import com.yangshuai.library.base.utils.ToastUtil
import com.yangshuai.module.dialog.DialogBuilder
import com.yangshuai.module.pay.PayApi
import com.yangshuai.module.pay.PayRouterPath
import com.yangshuai.module.pay.R
import com.yangshuai.module.pay.bean.OrderListBean
import com.yangshuai.module.pay.bean.PayBean
import com.yangshuai.module.pay.databinding.PayActPayBinding
import com.yangshuai.module.pay.viewmodel.PayViewModel
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.pay_act_pay.*
import kotlinx.coroutines.*
import java.util.*

/**
 * <pre>
 *     author : pty
 *     time   : 2022 07 08 17:04
 *     desc   : 支付页面  待支付页面
 * </pre>
 */
@Route(path = PayRouterPath.ROUT_PAY_PATH)
class PayActivity : BaseToolBarActivity<PayActPayBinding, PayViewModel>() {
    @Autowired
    @JvmField
    var xsdmxBillNo: String? = null//订单编号
    @Autowired
    @JvmField
    var billVipAmt: String? = null//金额
    @Autowired
    @JvmField
    var id: String? = null//id  主键id，获取订单详情的时候使用
    @Autowired
    @JvmField
    var type: String? = null//type 1支付  2为继续支付
    @Autowired
    @JvmField
    var fromType: String? = null
    @Autowired
    @JvmField
    var qRAuthCode:String? = null//授权码  确认订单页面跳转过来传，否则不传
    private val WECHAT = 1
    private val ALIPAY = 2
    var payType = WECHAT//默认为微信支付
    var job: Job? = null//携程
    private var t:Long= 0//倒计时时间，每次倒计时刷新
    /**
     *入口4种操作：
    null .入口是市场 返回到服务详情界面
    1，入口是 价值建立推荐服务商 返回到价值建立业务模块下
    2，入口是上市对标选择服务商 返回价值建立
    3， 订单详情二次支付 返回订单详情
     */
    @JvmField
    @Autowired
    var RouterType:String?=null
    var dialog: DialogBuilder?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pay_act_pay)
    }

    override fun initData() {
        super.initData()
        mBinding.viewModel = mViewModel
        billVipAmt = billVipAmt?.replace("元".toRegex(), "")
        billVipAmt = billVipAmt?.replace("￥".toRegex(), "")
        mBinding.price = billVipAmt
        initView()
    }

    private fun initView() {
        when(type){
            "2"->{
                mBinding.layoutTimer.visibility = View.VISIBLE
                mBinding.tvGoPay.text = "继续支付"
                setToolbarTitleCenter("小筱收银台")
            }
            else->{
                mBinding.layoutTimer.visibility = View.GONE
                mBinding.tvGoPay.text = "去支付"
                setToolbarTitleCenter("小筱收银台")
            }
        }
        mBinding.btWchat.isSelected = true
        //点击微信支付
        mBinding.btWchat.setOnClickListener{
            it.isSelected = true
            mBinding.btAlipay.isSelected = false
            payType = WECHAT
        }
        mBinding.btAlipay.isSelected = false
        //点击支付宝支付
        mBinding.btAlipay.setOnClickListener{
            it.isSelected = true
            mBinding.btWchat.isSelected = false
            payType = ALIPAY
        }
        //打开协议
        mBinding.goweb.setOnClickListener {
//            ARouter.getInstance().build(RouterPath.APPMain.ROUTE_MAIN_WEB_VIEW)
//                    .withString("url",ProtocolLinkConfig.getLink(ProtocolType.ORDER))
//                    .withString("barTitle","服务订单协议")
//                    .navigation()
        }
        qRAuthCode?.let { goWeChat() }?:goSecondPay()

    }
    private fun goWeChat(){
        mBinding.tvGoPay.setOnClickListener {
            if(!mBinding.btWchat.isChecked){
                ToastUtil.Short("请选择支付方式")
            }else{
                if(mBinding.cbox.isChecked){
                    qRAuthCode?.let {it1->
                        var code: PayBean.Code = Gson().fromJson(it1,PayBean.Code::class.java)
                        val appId =  code.appid
                        val partnerid =code.partnerid
                        val prepayid = code.prepayid
                        val nonceStr = code.noncestr
                        val timeStamp = code.timestamp
                        val sign = code.sign
//                        PayUtils.goWxPay(appId,partnerid,prepayid,nonceStr,timeStamp,sign) {it2->
//                            when(it2){
//                                //跳转微信成功
//                                true->{
//                                }
//                                //跳转微信失败
//                                false->{
//                                    ToastUtil.Short("您还未安装微信")
//
//                                }
//                            }
//                        }
                    }
                }else{
                    ToastUtil.Short("请同意,服务订单协议后方可下单")
                }
            }
        }
    }
    private fun goSecondPay(){
        if (xsdmxBillNo != null) {
            mViewModel.getSecondData(fromType,xsdmxBillNo!!,object : ResponseListener<PayBean> {
                override fun loadSuccess(data: PayBean?) {
                    data?.let {
                        qRAuthCode = it.responseMap.qrAuthCode.getqRAuthCode()
                        goWeChat()
                    }
                }

                override fun loadFailed(errorCode: String?, errorMsg: String?) {
                }

                override fun addDisposable(disposable: Disposable?) {

                }
            })
        }
    }
    override fun initParam() {
        ARouter.getInstance().inject(this)
    }
    val loadingDialog: LoadingDialog by lazy {
        LoadingDialog(this)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        id?.let {
            loadingDialog.setLoadingMessage("正在查询支付状态")
            loadingDialog.show()
            val map = mutableMapOf<String,String>()
            map["id"] = it
            fromType?.let {f-> map["releaseType"] = f }
            val params = mutableMapOf<String,String>()
            val encode = Base64.getEncoder().encodeToString(Gson().toJson(map).toByteArray())
            params["data"] = encode
            RetrofitManager.create(PayApi::class.java)
                    .OrderDetail(params)
                    .compose(RxUtils.responseTransformer())
                    .compose(RxUtils.schedulersTransformer())
                    .subscribe(object : ResponseObserver<OrderListBean.RecordsBean>() {
                        @RequiresApi(Build.VERSION_CODES.O)
                        override fun onSuccess(data:OrderListBean.RecordsBean) {
                            loadingDialog.dismiss()
                            when(data.payStatus){
                                "2"->{
                                    ARouter.getInstance()
                                        .build(PayRouterPath.ROUT_PAY_RES_PATH)
                                        .withString("xsdmxBillNo",data.xsdmxBillno)
                                        .withString("name",data.ministrantGoodName)
                                        .withString("orderAmt",data.orderAmt)
                                        .withString("time",data.billdate)
                                        .withString("id",data.id)
                                        .withString("RouterType",RouterType)
                                        .withString("modelName",data.businessIdDesc)
                                        .withString("businessId",data.businessId)
                                        .withString("listPlanNo",data.listPlanNo)
                                        .withString("fromType",fromType)
                                        .navigation()
                                    if(RouterType==null){
//                                        EventBus.getDefault().post(EvenCommon.MARKER)
                                    }
                                    finish()
                                }
                                else->{
                                    if(type == "1"){
                                        type = "2"
                                        return
                                    }
                                    //重置状态
//                                    type = "2"
//                                    initView()
//                                    mBinding.btWchat.isChecked = false
//                                    cbox.isChecked = false
//                                    val endTime = TimeUtil.getStringToDate(data.billdateEnd)//获取最后时间时间戳
//                                    val curDate = Date(System.currentTimeMillis())
//                                    val endDate = Date(endTime)
//                                    val diff: Long = endDate.time - curDate.time
//                                    t = diff
//                                    launch()
                                }
                            }
                        }

                        override fun onError(code: String?, msg: String?) {
                            super.onError(code, msg)

                            loadingDialog.dismiss()
                        }

                    })
        }
    }

    override fun onBackClick() {
//        dialog = DialogBuilder(this@PayActivity)
//            .setAlert("确认离开支付页面？", Gravity.CENTER)
//            .setTitle("提示")
//            .setRightText("继续支付")
//            .setLeftText("确认离开")
//            .setRightListener{
//                dialog?.dismiss()
//            }
//            .setLeftListener {
//                id?.let {
//                    //跳转订单详情
////                    ARouter.getInstance()
////                        .build(RouterPath.Mine.ROUTE_MINE_ORDER_CENTER_DETAILS)
////                        .withString("orderId",it)
////                        .withString("fromType",fromType)
////                        .navigation()
//                }
//                super.onBackClick()
//            }
//            .build() as DialogBuilder?
//        dialog?.show()
    }
    // launch方式创建协程
    @DelicateCoroutinesApi
    private fun launch() {
        if(job==null){
            job = GlobalScope.launch(Dispatchers.IO) {
                while (t>=0){
                    val date =  Date(t)
                    withContext(Dispatchers.Main) {
                        // 拿到数据后切换回UI线程刷新页面
                        tv_timer.text = "${date.minutes}:${date.seconds}"
                        if(t<0) finish()
                    }
                    t  -= 1000
                    Thread.sleep(1000)
                }
            }
        }


//        job = GlobalScope.launch(Dispatchers.IO) {
//            Thread.sleep(1000)
//            withContext(Dispatchers.Main) {
//                finish()
//            }
//        }
    }
    // 让协程运行完
//    private suspend fun join() = job?.join()

    // 取消协程
    private fun cancel() = job?.cancel()

    override fun onDestroy() {
        super.onDestroy()
        // 取消掉所有协程内容
        t = 0
        cancel()
        dialog?.dismiss()
        dialog = null
    }

}