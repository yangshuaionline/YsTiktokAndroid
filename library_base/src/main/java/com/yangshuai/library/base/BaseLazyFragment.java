package com.yangshuai.library.base;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yangshuai.library.base.interfaces.BaseActFragmImpl;
import com.yangshuai.library.base.utils.ClassUtil;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 基础懒加载Fragment
 *
 * @author hcp
 * @created 2019/3/15
 */
public abstract class BaseLazyFragment<VB extends ViewDataBinding, VM extends AndroidViewModel>
        extends LazyFragment implements BaseActFragmImpl {

    public String tag;
    // ViewModel
    protected VM mViewModel;
    // ViewModel关联的ID
    private int viewModelId;
    // 布局DataBinding
    protected VB mBinding;

    private CompositeDisposable mDisposable;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initParam();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, initContentView(inflater, container, savedInstanceState), container, false);
        viewModelId = initVariableId();
        initViewModel();
        // 关联ViewModel(设置变量的BR的id值，对应layout中variable的name)
        mBinding.setVariable(viewModelId, mViewModel);

        return mBinding.getRoot();
    }


//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//    }


    @Override
    public void onVisibleChanged(boolean isVisibleToUser) {
        super.onVisibleChanged(isVisibleToUser);

        if (isVisibleToUser) {
            //页面事件监听的方法，一般用于ViewModel层转到View层的事件注册
            initViewObservable();
        }

    }

    @Override
    public void loadDataByLazy() {
        //页面数据初始化方法
        initData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (this.mDisposable != null && !mDisposable.isDisposed()) {
            // clear 和 dispose的区别是：disposed = true;
            this.mDisposable.clear(); // 解除所有订阅
        }

        if (mBinding != null) {
            mBinding.unbind();
        }
    }

    /**
     * 初始化ViewModel
     */
    private void initViewModel() {
        Class<VM> viewModelClass = ClassUtil.getViewModel(this);
        if (viewModelClass != null) {
            this.mViewModel = ViewModelProviders.of(this).get(viewModelClass);
        }
    }

    /**
     * 在这个方法里面注入路由，拿到传参
     * 要在初始化布局之前执行
     */
    @Override
    public void initParam() {

    }


    /**
     * 初始化根布局
     *
     * @return 布局layout的id
     */
    public abstract int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
    public abstract int initVariableId();

    /**
     * 页面初始化数据
     */
    @Override
    public void initData() {

    }

    /**
     * 页面事件监听的方法，一般用于ViewModel层转到View层的事件注册
     */
    @Override
    public void initViewObservable() {

    }

    /**
     * 刷新布局
     */
    public void refreshLayout() {
        if (mViewModel != null) {
            mBinding.setVariable(viewModelId, mViewModel);
        }
    }

    /**
     * 添加订阅
     *
     * @param disposable
     */
    public void addSubscription(Disposable disposable) {
        if (this.mDisposable == null) {
            this.mDisposable = new CompositeDisposable();
        }
        this.mDisposable.add(disposable);
    }

    /**
     * 解除订阅
     */
    public void removeDisposable() {
        if (this.mDisposable != null && !mDisposable.isDisposed()) {
            this.mDisposable.dispose(); // 主动解除订阅
        }
    }
}
