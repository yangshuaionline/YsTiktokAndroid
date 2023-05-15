package com.yangshuai.module.first.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.yangshuai.library.base.BaseLazyFragment
import com.yangshuai.library.base.router.RouterPath
import com.yangshuai.library.base.utils.ToastUtil
import com.yangshuai.module.first.BR
import com.yangshuai.module.first.R
import com.yangshuai.module.first.databinding.DataFragmentBinding
import com.yangshuai.module.first.viewmodel.NewsViewModel
import com.yangshuai.module.video.VideoBean
import com.yangshuai.module.video.VideoUser.setVideo

/**
 * 资源首页
 *
 * @author hcp
 * @created 2020/10/12
 */
@Route(path = RouterPath.Contact.ROUTE_CONTACT_PATH)
class NewsFragment : BaseLazyFragment<DataFragmentBinding, NewsViewModel>() {
    override fun initContentView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = R.layout.data_fragment

    override fun initVariableId() = BR.viewModel

    override fun initData() {
        super.initData()
        activity?.let { setVideo(it,mBinding.body, VideoBean()) }?:let { ToastUtil.Short("activity为空") }
    }
    override fun initParam() {
        ARouter.getInstance().inject(this)
    }
}