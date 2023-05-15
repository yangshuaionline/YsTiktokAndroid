package com.yangshuai.module.pay.activity

import android.os.Bundle
import android.text.TextUtils
import android.text.method.DigitsKeyListener
import com.alibaba.android.arouter.facade.annotation.Route
import com.yangshuai.library.base.BaseToolBarActivity
import com.yangshuai.library.base.viewmodel.NoViewModel
import com.yangshuai.module.pay.PayRouterPath
import com.yangshuai.module.pay.R
import com.yangshuai.module.pay.authorization.AuthorizationManager
import com.yangshuai.module.pay.databinding.PayActAuthorizeBinding

/**
 * <pre>
 *     author : yangshuai
 *     time   : 2023 03 02 17:04
 *     desc   : 授权确认页面
 * </pre>
 */
@Route(path = PayRouterPath.ROUT_PAY_AUTHORIZE)
class AuthorizeActivity : BaseToolBarActivity<PayActAuthorizeBinding, NoViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pay_act_authorize)
    }
    override fun initData() {
        super.initData()
        initView()
        setToolbarTitle("授权确认")
    }
    private fun initView() {
        mBinding.etPhone.keyListener = DigitsKeyListener.getInstance("0123456789")
        mBinding.tvLogoutText.setOnClickListener {
//            val url = ProtocolLinkConfig.getLink(ProtocolType.VALUEADDED)
//            ARouter.getInstance().build(RouterPath.APPMain.ROUTE_MAIN_WEB_VIEW)
//                .withString("url",url)
//                .withString("barTitle","授权服务协议")
//                .withString("addtVersionName","2")
//                .navigation()
        }
        mBinding.tvGoCancel.setOnClickListener { finish() }
        mBinding.tvGoAuthorize.setOnClickListener {
            var msg:String?=null
            if(!mBinding.cbox.isChecked){
                msg = "请阅读并同意《授权服务协议》"
            }
            if(TextUtils.isEmpty(mBinding.etName.text.trim())){
                msg = "姓名不能为空"
            }
            if(TextUtils.isEmpty(mBinding.etPhone.text.trim())){
                msg = "手机号不能为空"
            }
            if(!mBinding.etPhone.text.trim().startsWith("1")||mBinding.etPhone.text.trim().length!=11){
                msg = "请输入正确手机号"
            }
            msg?.let {
//                var dialog: DialogBuilder?=null
//                dialog = DialogBuilder(this@AuthorizeActivity)
//                    .setAlert(it)
//                    .setTitle("提示")
//                    .setCenterText("知道了")
//                    .setCenterListener{ dialog?.dismiss() }
//                    .build() as DialogBuilder?
//                dialog?.show()
                return@setOnClickListener
            }
            AuthorizationManager().addAuthorization(
                mBinding.etName.text.toString().trim(),
                mBinding.etPhone.text.toString().trim(),
                this@AuthorizeActivity
            )
        }
    }

}