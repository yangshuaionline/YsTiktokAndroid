package com.yangshuai.library.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProviders
import com.yangshuai.library.base.interfaces.BaseActFragmImpl
import com.yangshuai.library.base.utils.ClassUtil
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseFragmentKt<VB : ViewDataBinding, VM : AndroidViewModel>() : Fragment(), BaseActFragmImpl {
    var fragmenTag: String? =null

    // ViewModel
    protected var mViewModel: VM? = null

    // ViewModel关联的ID
    private var viewModelId = 0

    // 布局DataBinding
    protected var mBinding: VB? = null
    private var mDisposable: CompositeDisposable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initParam()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(
            inflater,
            initContentView(inflater, container, savedInstanceState),
            container,
            false
        )
        viewModelId = initVariableId()
        initViewModel()
        // 关联ViewModel(设置变量的BR的id值，对应layout中variable的name)
        mBinding?.setVariable(viewModelId, mViewModel)
        return mBinding?.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //页面数据初始化方法
        initData()
        //页面事件监听的方法，一般用于ViewModel层转到View层的事件注册
        initViewObservable()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mDisposable != null && !mDisposable!!.isDisposed) {
            // clear 和 dispose的区别是：disposed = true;
            mDisposable!!.clear() // 解除所有订阅
        }
        if (mBinding != null) {
            mBinding!!.unbind()
        }
    }

    /**
     * 初始化ViewModel
     */
    private fun initViewModel() {
        // 自定义 ViewModel

        // 自定义 ViewModel
        mViewModel = initCustomViewModel()
        if (mViewModel != null) return

        val viewModelClass: Class<VM> = ClassUtil.getViewModel(this)
        if (viewModelClass != null) {
            mViewModel = ViewModelProviders.of(this)[viewModelClass]
        }
    }

    /**
     * 实现自定义的ViewModel
     *
     * @return VM
     */
    protected fun initCustomViewModel(): VM? = null

    /**
     * 在这个方法里面注入路由，拿到传参
     * 要在初始化布局之前执行
     */
    override fun initParam() {}

    /**
     * 初始化根布局
     *
     * @return 布局layout的id
     */
    abstract fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): Int

    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
    abstract fun initVariableId(): Int

    /**
     * 页面初始化数据
     */
    override fun initData() {}

    /**
     * 页面事件监听的方法，一般用于ViewModel层转到View层的事件注册
     */
    override fun initViewObservable() {}

    /**
     * 刷新布局
     */
    fun refreshLayout() {
        if (mViewModel != null) {
            mBinding!!.setVariable(viewModelId, mViewModel)
        }
    }

    /**
     * 添加订阅
     *
     * @param disposable
     */
    fun addSubscription(disposable: Disposable?) {
        if (mDisposable == null) {
            mDisposable = CompositeDisposable()
        }
        mDisposable!!.add(disposable!!)
    }

    /**
     * 解除订阅
     */
    fun removeDisposable() {
        if (mDisposable != null && !mDisposable!!.isDisposed) {
            mDisposable!!.dispose() // 主动解除订阅
        }
    }
}