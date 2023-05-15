package com.yangshuai.library.base.widget.filter.base;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yangshuai.library.base.interfaces.DataResponseListener;

import java.util.Map;

/**
 * 筛选容器(需要筛选功能的UI都需要继承这个来实现)
 *
 * @author hcp
 * @created 17/1/5
 * @editor hcp
 * @edited 19/3/29
 * @type 筛选组件
 */
public class BaseFilterContainerView extends FrameLayout {

    private static final long DEFAULT_DURATION = 400;
    private View mShadowView;
    private RelativeLayout mContentView;
    /**
     * 阴影颜色
     */
    private int mShadowColor = Color.parseColor("#70000000");
    private boolean mShowing;
    private boolean isInit = false;
    private long mDuration = DEFAULT_DURATION;
    /**
     * 扩展字段
     */
    private Object extend;

    private DataResponseListener dataResponseListener;
    protected Map<String, Object> filterParams;


    public BaseFilterContainerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs, defStyleAttr);
        init();
    }

    public BaseFilterContainerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs, 0);
        init();
    }


    private void initAttrs(Context context, AttributeSet attrs, int defStyleAttr) {

    }

    public Object getExtend() {
        return extend;
    }

    public void setExtend(Object extend) {
        this.extend = extend;
    }

    public View getShadowView() {
        return mShadowView;
    }

    public RelativeLayout getContentView() {
        return mContentView;
    }

    /**
     * 初始化控件
     */
    private void init() {
        this.setVisibility(INVISIBLE);
        // 背景阴影
        mShadowView = new View(getContext());
        LayoutParams shadowParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mShadowView.setLayoutParams(shadowParams);
        mShadowView.setBackgroundColor(mShadowColor);
        this.addView(mShadowView);
        // 容器
        mContentView = new RelativeLayout(getContext());
        LayoutParams containerParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mContentView.setLayoutParams(containerParams);
        mContentView.setClickable(true); // 避免容器布局可以被点击关闭
        this.addView(mContentView);

        mShowing = false;
        onCreateBefore();
        onCreate();
    }

    /**
     * 设置内容视图
     *
     * @param layoutId 布局文件
     */
    public View setContentView(int layoutId) {
        View contentView = LayoutInflater.from(getContext()).inflate(layoutId, null);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        contentView.setLayoutParams(params);
        return setContentView(contentView);
    }

    /**
     * 设置容器的view高度(默认是自适应高度)
     *
     * @param height 需要转换dp之后入参
     */
    public void setContainerHeight(int height) {
        if (height != 0) {
            ViewGroup.LayoutParams params = mContentView.getLayoutParams();
            params.height = height;
            mContentView.setLayoutParams(params);
        }
    }

    /**
     * 设置内容视图
     *
     * @param contentView 内容视图
     */
    public View setContentView(View contentView) {
        mContentView.addView(contentView);
        return contentView;
    }


    /**
     * 是否显示
     *
     * @return
     */
    public boolean isShowing() {
        return mShowing;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.setOnClickListener(view -> close());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (!isInit) {
            setVisibility(GONE);
        }
        isInit = true;
    }

    protected void onCreateBefore() {

    }

    /**
     * 子类可以重写进行初始化
     */
    protected void onCreate() {


    }

    /**
     * 带动画显示
     */
    public void show() {
        show(true);
    }

    /**
     * 显示
     *
     * @param anim 是否进行动画显示
     */
    public void show(boolean anim) {
        if (isShowing()) {
            return;
        }
        mShowing = true;
        onShowBefore();
        if (dataResponseListener == null) {
            if (anim) {
                showRunAnim();
            } else {
                showNoAnim();
            }
            onShowAfter();
        } else {
            dataResponseListener.onResponse(this);
        }
    }


    public void showNotify(boolean anim) {
        if (anim) {
            showRunAnim();
        } else {
            showNoAnim();
        }
        onShowAfter();
    }

    public void setShowBeforeListener(DataResponseListener<BaseFilterContainerView> dataResponseListener) {
        this.dataResponseListener = dataResponseListener;
    }

    public void showRunAnim() {
        TranslateAnimation anim = new TranslateAnimation(0, 0, -getHeight(), 0);
        anim.setDuration(mDuration);

        this.setVisibility(VISIBLE);
        mContentView.clearAnimation();
        mContentView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        mContentView.startAnimation(anim);
        //
        AlphaAnimation alphaAnim = new AlphaAnimation(0, 1.f);
        alphaAnim.setDuration(mDuration);
        alphaAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mContentView.clearAnimation();
                mShadowView.clearAnimation();
                mContentView.setLayerType(View.LAYER_TYPE_NONE, null);
                mShadowView.setLayerType(View.LAYER_TYPE_NONE, null);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mShadowView.clearAnimation();
        mShadowView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        mShadowView.startAnimation(alphaAnim);

    }

    private void showNoAnim() {
        this.setVisibility(VISIBLE);
        mShadowView.clearAnimation();
        mContentView.clearAnimation();

    }


    /**
     * 关闭（带动画）
     */
    public void close() {
        close(true);
    }

    /**
     * 关闭
     *
     * @param anim 是否带动画
     */
    public void close(boolean anim) {
        if (!isShowing()) {
            return;
        }
        mShowing = false;
        onCloseBefore();
        //
        if (anim) {
            closeRunAnim();
        } else {
            closeNoAnim();
        }
        //
        onCloseAfter();

    }


    private void closeRunAnim() {
        TranslateAnimation anim = new TranslateAnimation(0, 0, 0, -getHeight());
        anim.setDuration(mDuration);


        mContentView.clearAnimation();
        mContentView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        mContentView.startAnimation(anim);
        //
        AlphaAnimation alphaAnim = new AlphaAnimation(1.f, 0);
        alphaAnim.setDuration(mDuration);
        alphaAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                BaseFilterContainerView.this.setVisibility(GONE);
                mContentView.clearAnimation();
                mShadowView.clearAnimation();
                mContentView.setLayerType(View.LAYER_TYPE_NONE, null);
                mShadowView.setLayerType(View.LAYER_TYPE_NONE, null);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mShadowView.clearAnimation();
        mShadowView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        mShadowView.startAnimation(alphaAnim);

    }


    private void closeNoAnim() {
        this.setVisibility(GONE);
        mShadowView.clearAnimation();
        mContentView.clearAnimation();
    }


    protected void onShowBefore() {

    }

    protected void onCloseBefore() {

    }

    protected void onShowAfter() {
        if (onFilterContainerListener != null) {
            onFilterContainerListener.onShow(this);
        }
    }

    protected void onCloseAfter() {
        if (dataResponseListener != null) {
            dataResponseListener.onClose();
        }
        if (onFilterContainerListener != null) {
            onFilterContainerListener.onClose(this);
        }
    }


    protected void clearAllEditText(View view) {
        if (view == null) {
            return;
        }
        if (view instanceof ViewGroup) {

            ViewGroup viewGroup = (ViewGroup) view;
            final int len = viewGroup.getChildCount();
            for (int i = 0; i < len; i++) {
                View child = viewGroup.getChildAt(i);
                clearAllEditText(child);
            }
        } else if (view instanceof EditText) {
            EditText editText = (EditText) view;
            editText.setText("");
        }
    }

    public final class ClearTextWatcher implements TextWatcher {

        private int id;

        public ClearTextWatcher(int id) {
            this.id = id;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (TextUtils.isEmpty(charSequence.toString())) {
                findViewById(id).setVisibility(GONE);
            } else {
                findViewById(id).setVisibility(VISIBLE);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }

    /**
     * 输入法搜索按钮监听
     *
     * @author Administrator
     */
    public class OnEditorActionListenerImpl implements TextView.OnEditorActionListener {

        @Override
        public boolean onEditorAction(TextView tv, int actionId, KeyEvent ke) {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                InputMethodManager imm = (InputMethodManager) tv.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm.isActive()) {
                    imm.hideSoftInputFromWindow(tv.getApplicationWindowToken(), 0);
                    onKeyboardSearch();
                }
                return true;
            }

            return false;
        }

    }

    protected void onKeyboardSearch() {

    }

    protected OnFilterContainerListener onFilterContainerListener;

    public void setOnFilterContainerListener(OnFilterContainerListener onFilterContainerListener) {
        this.onFilterContainerListener = onFilterContainerListener;
    }

    public interface OnFilterContainerListener {
        void onShow(BaseFilterContainerView fcv);

        void onClose(BaseFilterContainerView fcv);
    }

    public OnFilterResultListener onFilterResultListener;

    public void setOnFilterResultListener(Map<String, Object> filterParams,OnFilterResultListener onFilterResultListener) {
        this.filterParams = filterParams;
        this.onFilterResultListener = onFilterResultListener;
    }

    public void setOnFilterResultListener(OnFilterResultListener onFilterResultListener) {
        this.onFilterResultListener = onFilterResultListener;
    }

    public interface OnFilterResultListener {
        void onResult(BaseFilterContainerView fcv, Map<String, ?> result);

        default void onClear() {
        }
    }


}
