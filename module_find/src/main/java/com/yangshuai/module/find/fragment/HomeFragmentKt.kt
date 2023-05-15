package com.yangshuai.module.find.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.alibaba.android.arouter.facade.annotation.Route
import com.yangshuai.library.base.BaseFragmentKt
import com.yangshuai.library.base.interfaces.ResponseListener
import com.yangshuai.library.base.router.RouterPath
import com.yangshuai.module.find.R
import com.yangshuai.module.find.databinding.HomeFrgmKtBinding
import com.yangshuai.module.find.BR
import com.yangshuai.module.find.bean.BannerBean
import com.yangshuai.module.find.viewmodel.HomeKtViewModel
import io.reactivex.disposables.Disposable

@Route(path = RouterPath.HOME.ROUTE_MAIN_PATH)
class HomeFragmentKt : BaseFragmentKt<HomeFrgmKtBinding, HomeKtViewModel>(){
    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): Int= R.layout.home_frgm_kt

    override fun initVariableId(): Int = BR.viewModel

    override fun initData() {
        super.initData()
        initView()
        loadData()
    }

    /**
     * 获取数据
     */
    private fun loadData() {
        mViewModel?.getBannerData(object : ResponseListener<List<BannerBean>> {
            override fun loadSuccess(data: List<BannerBean>?) {
                data?.let {  for (dataitem in data){

                }}

            }

            override fun loadFailed(errorCode: String?, errorMsg: String?) {

            }

            override fun addDisposable(disposable: Disposable?) {

            }

        })

        mViewModel?.getHotNewsData()
        mViewModel?.getFriendData()

    }

    /**
     * 初始化控件
     */
    private fun initView() {


    }


}