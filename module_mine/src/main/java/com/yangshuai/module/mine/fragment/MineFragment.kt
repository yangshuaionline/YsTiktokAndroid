package com.yangshuai.module.mine.fragment

import com.yangshuai.library.base.router.RouterPath
import com.yangshuai.library.base.BaseFragmentKt
import com.yangshuai.module.mine.viewmodel.MineViewModel
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.yangshuai.module.mine.BR
import com.yangshuai.module.mine.R
import com.yangshuai.module.mine.databinding.MineFrgmBinding

/**
 * 我的
 *
 * @author hcp
 * @created 2019/3/22
 */
@Route(path = RouterPath.Mine.ROUTE_MINE_PATH)
  class MineFragment : BaseFragmentKt<MineFrgmBinding, MineViewModel>() {
    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): Int= R.layout.mine_frgm

    override fun initVariableId(): Int = BR.viewmodel

    override fun initData() {
        super.initData()
        mBinding?.viewmodel=mViewModel
    }
  }

