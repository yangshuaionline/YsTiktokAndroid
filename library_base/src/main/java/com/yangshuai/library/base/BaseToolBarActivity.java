package com.yangshuai.library.base;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.appbar.AppBarLayout;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yangshuai.library.base.databinding.ActBaseToolbarBinding;
import com.yangshuai.library.base.entity.CityInfoData;
import com.yangshuai.library.base.interfaces.BaseActFragmImpl;
import com.yangshuai.library.base.utils.ClassUtil;
import com.yangshuai.library.base.utils.SoftKeyBoardUtils;
import com.yangshuai.library.base.utils.StatusBarUtils;
import com.yangshuai.library.base.utils.SystemUtils;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 带有标题栏的基类Activity
 *
 * @author hcp
 * @created 2019/3/15
 */

public abstract class BaseToolBarActivity<VB extends ViewDataBinding, VM extends AndroidViewModel>
        extends AppCompatActivity implements BaseActFragmImpl {

    protected Context mContext;
    // ViewModel
    protected VM mViewModel;
    // ViewModel关联的ID
    private int viewModelId;
    // 布局DataBinding
    protected VB mBinding;
    private CompositeDisposable mDisposable;

    // 标题栏相关
    public Toolbar mToolBar;
    public TextView mToolbarTitle; // ToolBar上面的标题栏文本(暂时停用)
    public AppBarLayout mAppBarLayout;
    public ActBaseToolbarBinding mBaseToolBarBinding;

    // 当前城市
    public static CityInfoData currentCity;

    // 用于列表分页
    protected int pageNo = 1; // 第几页
    protected int pageSize = 20; // 每页多少条

    protected boolean isHideKey=true;//是否隐藏键盘

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = BaseToolBarActivity.this;
        initParam();
    }

    @Override
    public void setContentView(int layoutResID) {
        mBaseToolBarBinding = DataBindingUtil.inflate(LayoutInflater.from(this),
                R.layout.act_base_toolbar, null, false);
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), layoutResID, null, false);

        mToolBar = mBaseToolBarBinding.toolbar;
        mToolbarTitle = mBaseToolBarBinding.toolbarTitle;
        mAppBarLayout = mBaseToolBarBinding.appBarLayout;

        // content
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mBinding.getRoot().setLayoutParams(params);
        RelativeLayout mContainer = (RelativeLayout) mBaseToolBarBinding.getRoot().findViewById(R.id.ui_layout);
        mContainer.addView(mBinding.getRoot());
        getWindow().setContentView(mBaseToolBarBinding.getRoot());

        // 适配状态栏
        if (SystemUtils.v21()) {
            if (StatusBarUtils.setMiuiStatusBarDarkMode(this, true)
                    || StatusBarUtils.setMeizuStatusBarDarkIcon(this, true)) {
                getWindow().setStatusBarColor(Color.WHITE);

            }
        }
        if (SystemUtils.v21()) {
            //必须在代码里面设置状态栏颜色，否则在乐视手机上回出现状态栏变黑问题
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }

        // 适配Toolbar阴影
        if (!SystemUtils.v21()) {
            mBaseToolBarBinding.toolbarShadowCompat.setVisibility(View.VISIBLE);
        } else {
            mBaseToolBarBinding.toolbarShadowCompat.setVisibility(View.GONE);
            mBaseToolBarBinding.appBarLayout.setStateListAnimator(AnimatorInflater
                    .loadStateListAnimator(this,
                            R.anim.appbar_elevation));
        }

        refreshToolbar();
        showBackBtn(true);
        initViewModel();
        initData();
        initViewObservable();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (this.mDisposable != null && !mDisposable.isDisposed()) {
            // clear 和 dispose的区别是：disposed = true;
            this.mDisposable.clear(); // 解除所有订阅
        }

        if (mBinding != null) {
            mBinding.unbind();
        }

        if (mBaseToolBarBinding != null) {
            mBaseToolBarBinding.unbind();
        }
    }


    /**
     * 初始化ViewModel
     */
    private void initViewModel() {
        // 自定义 ViewModel
        this.mViewModel = initCustomViewModel();
        if (this.mViewModel != null) return;

        Class<VM> viewModelClass = ClassUtil.getViewModel(this);
        if (viewModelClass != null) {
            this.mViewModel = ViewModelProviders.of(this).get(viewModelClass);
        }
    }

    /**
     * 实现自定义的ViewModel
     *
     * @return VM
     */
    protected VM initCustomViewModel() {
        return null;
    }



    @Override
    public void initParam() {

    }


    @Override
    public void initViewObservable() {

    }

    @Override
    public void initData() {

    }

    /**
     * 刷新ToolBar
     */
    public void refreshToolbar() {
        if (mBaseToolBarBinding != null && mBaseToolBarBinding.toolbar != null) {
            setSupportActionBar(mBaseToolBarBinding.toolbar);
            mBaseToolBarBinding.toolbar.setNavigationOnClickListener(view -> onBackClick());
        }
    }

    /**
     * 是否显示ToolBar的返回按钮
     *
     * @param isShow
     */
    public void showBackBtn(boolean isShow) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(isShow);
    }

    public void onBackClick() {
        onBackPressed();
    }

    /**
     * 设置ToolBar标题文本
     *
     * @param title
     */
    public void setToolbarTitle(String title) {
        if (mBaseToolBarBinding != null && mBaseToolBarBinding.toolbar != null) {
            mBaseToolBarBinding.toolbar.setTitle(title);
//            mToolbarBinding.toolbarTitle.setText(title);
//            if (TextUtils.isEmpty(title)) {
//                mToolbarBinding.toolbarTitle.setVisibility(View.GONE);
//            } else {
//                mToolbarBinding.toolbarTitle.setVisibility(View.VISIBLE);
//            }
            refreshToolbar();
        }
    }

    public void setToolbarTitle(int strId) {
        setToolbarTitle(getString(strId));
        refreshToolbar();
    }


    /**
     * 设置ToolBar居中标题文本
     *
     * @param title
     */
    public void setToolbarTitleCenter(String title) {
        if (mBaseToolBarBinding != null && mBaseToolBarBinding.toolbar != null) {
            mBaseToolBarBinding.toolbarTitle.setText(title);
            if (TextUtils.isEmpty(title)) {
                mBaseToolBarBinding.toolbarTitle.setVisibility(View.GONE);
            } else {
                mBaseToolBarBinding.toolbarTitle.setVisibility(View.VISIBLE);
            }
            refreshToolbar();
        }
    }


    /**
     * 改变返回按钮的宽度
     *
     * @param width
     */
    protected void setImageButtonWidth(int width) {
        if (mBaseToolBarBinding.toolbar == null) {
            return;
        }
        mBaseToolBarBinding.toolbar.post(() -> {
            final int len = mBaseToolBarBinding.toolbar.getChildCount();
            for (int i = 0; i < len; i++) {
                View view = mBaseToolBarBinding.toolbar.getChildAt(i);
                if (view instanceof AppCompatImageButton) {
                    ViewGroup.LayoutParams params = view.getLayoutParams();
                    params.width = width;
                    view.setLayoutParams(params);
                }
            }
        });
    }


    /**
     * 隐藏菜单按钮
     */
    protected void hideActionMenuView() {
        if (mBaseToolBarBinding.toolbar == null) {
            return;
        }
        mBaseToolBarBinding.toolbar.post(() -> {
            final int len = mBaseToolBarBinding.toolbar.getChildCount();
            for (int i = 0; i < len; i++) {
                View view = mBaseToolBarBinding.toolbar.getChildAt(i);
                if (view instanceof ActionMenuView) {
                    view.setVisibility(View.GONE);
                }
            }
        });

    }

    /**
     * 显示菜单按钮
     */
    protected void showActionMenuView() {
        if (mBaseToolBarBinding.toolbar == null) {
            return;
        }
        mBaseToolBarBinding.toolbar.post(() -> {
            final int len = mBaseToolBarBinding.toolbar.getChildCount();
            for (int i = 0; i < len; i++) {
                View view = mBaseToolBarBinding.toolbar.getChildAt(i);
                if (view instanceof ActionMenuView) {
                    view.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    /**
     * 设置ToolBar兼容透明状态栏，避免显示到状态栏上面，高度异常的问题
     */
    public void setToolBarCompatStatusBar() {
        // 判断版本大于V19就需要增加标题栏的高度为状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && mBaseToolBarBinding.toolbar != null) {
            int statusBarHeight = StatusBarUtils.getStatusBarHeight(this);
            mBaseToolBarBinding.toolbar.setPadding(0, statusBarHeight, 0, 0); // 左、上、右、下
        }
    }

    /**
     * 设置ToolBar返回按钮颜色
     *
     * @param color
     */
    protected void setToolbarBackMenuColor(int color) {
        if (mBaseToolBarBinding.toolbar == null) {
            return;
        }
        Drawable upArrow = mBaseToolBarBinding.toolbar.getNavigationIcon();
        if (upArrow != null) {
            upArrow.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        }
    }

    /**
     * 设置AppBarLayout阴影
     *
     * @param elevation
     */
    protected void setAppBarLayoutElevation(float elevation) {
        if (mBaseToolBarBinding.appBarLayout == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            StateListAnimator stateListAnimator = new StateListAnimator();
            stateListAnimator.addState(new int[0], ObjectAnimator.ofFloat(mBaseToolBarBinding.appBarLayout, "elevation", elevation));
            mBaseToolBarBinding.appBarLayout.setStateListAnimator(stateListAnimator);
        }
    }

    /**
     * 隐藏AppBarLayout阴影
     */
    protected void hideAppbarLayoutShadow() {
        //去掉Appbar阴影
        setAppBarLayoutElevation(0);
        //隐藏掉兼容阴影
        mBaseToolBarBinding.toolbarShadowCompat.setVisibility(View.GONE);
    }

    /**
     * 显示AppBarLayout阴影
     */
    protected void showAppbarLayoutShadow() {
        //去掉Appbar阴影
        setAppBarLayoutElevation(5);
        //隐藏掉兼容阴影
        mBaseToolBarBinding.toolbarShadowCompat.setVisibility(View.VISIBLE);
    }


    /**
     * 添加订阅
     * 需要在Activity退出时解除订阅的添加
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

    /**
     * 通过这个方法可以避免重复实例化Fragment
     *
     * @param <T>
     * @param clazz
     * @param tag
     * @return
     */
    public <T extends BaseLazyFragment> T getLazyFragment(Class<?> clazz, String tag) {

        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        BaseLazyFragment fragment = null;
        if (fragments != null && fragments.size() > 0) {
            for (Fragment fragment1 : fragments) {

                if (fragment1 != null && fragment1 instanceof BaseFragment) {
                    BaseLazyFragment baseFragment = (BaseLazyFragment) fragment1;
                    if (baseFragment.getClass().getName().equals(clazz.getName())) {
                        if (!TextUtils.isEmpty(tag) && !TextUtils.isEmpty(baseFragment.tag)) {
                            if (baseFragment.tag.equals(tag)) {
                                fragment = baseFragment;
                            }
                        } else {
                            fragment = baseFragment;
                        }
                    }

                }
            }
        }


        if (fragment == null) {
            try {
                fragment = (BaseLazyFragment) clazz.newInstance();

            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if (tag != null) {
            fragment.tag = tag;
        }

        return (T) fragment;
    }

    /**
     * 通过这个方法可以避免重复实例化Fragment
     *
     * @param <T>
     * @param clazz
     * @param tag
     * @return
     */
    public <T extends BaseFragment> T getFragment(Class<?> clazz, String tag) {

        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        BaseFragment fragment = null;
        if (fragments != null && fragments.size() > 0) {
            for (Fragment fragment1 : fragments) {

                if (fragment1 != null && fragment1 instanceof BaseFragment) {
                    BaseFragment baseFragment = (BaseFragment) fragment1;
                    if (baseFragment.getClass().getName().equals(clazz.getName())) {
                        if (!TextUtils.isEmpty(tag) && !TextUtils.isEmpty(baseFragment.tag)) {
                            if (baseFragment.tag.equals(tag)) {
                                fragment = baseFragment;
                            }
                        } else {
                            fragment = baseFragment;
                        }
                    }

                }
            }
        }


        if (fragment == null) {
            try {
                fragment = (BaseFragment) clazz.newInstance();

            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if (tag != null) {
            fragment.tag = tag;
        }

        return (T) fragment;
    }

    /**
     * 页面挂起时关闭页面的软键盘
     */
    @Override
    protected void onStop() {
        super.onStop();
        SoftKeyBoardUtils.closeKeyBoard(this);
    }


    /**
     * 空白区隐藏输入法
     *
     * @param ev
     * @return
     */

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev) && isHideKey==true) {
                SoftKeyBoardUtils.closeKeyBoard(this);
            }
        }

        return super.dispatchTouchEvent(ev);

    }

    public void setHideSoftKeyboard(boolean isHideKey) {
        this.isHideKey = isHideKey;
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

}
