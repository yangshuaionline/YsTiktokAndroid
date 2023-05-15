package com.yangshuai.library.base.view.loadView;

import android.animation.Animator;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yangshuai.library.base.R;
import com.yangshuai.library.base.exception.ExceptionCode;
import com.yangshuai.library.base.interfaces.ULoadViewLocationCallback;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

/**
 * 加载视图
 * 跟布局是ScrollView 不能较好的适配loading 位置
 *
 * @author hcp
 * @editor hcp
 * @created 2019/3/27
 */
public class ULoadView implements ILoadVew {

    private Context context;
    private View bindView;
    private View loadView;
    private ImageView iconView;
    private TextView messageView;
    private TextView tipsView;
    private Button buttonView;
    private ProgressBar progressBar;
    private GifImageView ivLoadGif;
//    private LottieAnimationView lottieAnimationView;

    private boolean isShowLoading;
    private boolean isShowError;

    /**
     * 是否仅显示gifLoading
     */
    private boolean isOnlyGifLoading;
    /**
     * 空界面的iconRes
     */
    private int emptyResId;

    public ULoadView(View bindView) {
        this.bindView = bindView;
        this.context = bindView.getContext();
        initData();
        initView();
    }

    private void initData() {
        emptyResId = R.mipmap.icon_empty_data;
    }

    public ULoadView(View bindView, ULoadViewLocationCallback callback) {
        this.bindView = bindView;
        this.context = bindView.getContext();
        initData();
        initViewByCallback(callback);
    }

    private void initViewByCallback(ULoadViewLocationCallback callback) {

        LayoutInflater inflater = LayoutInflater.from(context);
        loadView = inflater.inflate(R.layout.base_load_view, null);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        loadView.setLayoutParams(params);

        // location
        ViewGroup parent = (ViewGroup) bindView.getParent();
        callback.onLocationCallBack(parent, loadView);

        iconView = loadView.findViewById(R.id.load_view_icon);
        messageView = loadView.findViewById(R.id.load_view_message);
        tipsView = loadView.findViewById(R.id.load_view_tips);
        progressBar = loadView.findViewById(R.id.load_view_progress);
//        lottieAnimationView = loadView.findViewById(R.id.animation_view);
//        lottieAnimationView.setImageAssetsFolder("airbnb_loader/");
        buttonView = loadView.findViewById(R.id.load_view_btn);

        ivLoadGif = loadView.findViewById(R.id.load_view_iv_gif);


        loadView.setVisibility(View.GONE);
    }

    private void initView() {

        LayoutInflater inflater = LayoutInflater.from(context);
        loadView = inflater.inflate(R.layout.base_load_view, null);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        loadView.setLayoutParams(params);

        ViewGroup parent = (ViewGroup) bindView.getParent();

        parent.addView(loadView);

        iconView = loadView.findViewById(R.id.load_view_icon);
        messageView = loadView.findViewById(R.id.load_view_message);
        tipsView = loadView.findViewById(R.id.load_view_tips);
        progressBar = loadView.findViewById(R.id.load_view_progress);
//        lottieAnimationView = loadView.findViewById(R.id.animation_view);
//        lottieAnimationView.setImageAssetsFolder("airbnb_loader/");
        buttonView = loadView.findViewById(R.id.load_view_btn);

        ivLoadGif = loadView.findViewById(R.id.load_view_iv_gif);


        loadView.setVisibility(View.GONE);
    }


    @Override
    public void showError(String message, String buttonText, View.OnClickListener listener) {
        setGifWhiteBg(R.color.white);
        isShowLoading = false;
        isShowError = true;
        loadView.setAlpha(1);
        loadView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        ivLoadGif.setVisibility(View.GONE);
//        lottieAnimationView.setVisibility(View.GONE);
//        lottieAnimationView.cancelAnimation(); // 取消动画
        iconView.setVisibility(View.VISIBLE);
        tipsView.setVisibility(View.VISIBLE);
        buttonView.setVisibility(View.GONE);
        String networkError = context.getResources().getString(R.string.load_network_error);
        if (null == message) {
            iconView.setImageResource(emptyResId);
            message = "暂无数据";
        } else {
            if (message.contains("网络连接异常")
                    || message.contains("未连接到服务器")
                    || message.contains(networkError)) {
                // 网络异常
                iconView.setImageResource(R.mipmap.icon_network_error);
            } else {
                // 数据异常
                iconView.setImageResource(R.mipmap.icon_error_data);
            }
        }

        messageView.setText(message);
        if (TextUtils.isEmpty(buttonText)) {
            tipsView.setText(context.getResources().getString(R.string.load_refresh_tips));
        } else {
            tipsView.setText(buttonText);
        }
        loadView.setClickable(true);
        loadView.setOnClickListener(listener);
        bindView.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message, View.OnClickListener listener) {
        showError(message, null, listener);
    }

    /**
     * 所有错误都可以走这里，处理了网络异常
     *
     * @param errorCode
     * @param errorMsg
     * @param listener
     */
    public void showErrors(String errorCode, String errorMsg, View.OnClickListener listener) {
        setGifWhiteBg(R.color.white);

        // errorCode可能为NULL
        if (!TextUtils.isEmpty(errorCode) && errorCode.equals(ExceptionCode.NO_NERWORK_ERROR)) {
            showNetworkError(listener); // 无网络
            return;
        }

        if (!TextUtils.isEmpty(errorCode) && errorCode.equals(ExceptionCode.TIMEOUT_ERROR)) {
            showTimeOutError(listener); // 连接超时
            return;
        }

        // 数据为空
        if (!TextUtils.isEmpty(errorCode) && errorCode.equals(ExceptionCode.NO_DATA_ERROR)) {
            showEmpty(errorMsg, listener);
            return;
        }

        if (TextUtils.isEmpty(errorCode)) {
            showError("数据加载失败：未知Code。" + "\n" + errorMsg, null, listener);//
        } else {
            // code= 403 开头表示无权限,加载视图显示“不好意思，您暂无查看权限！”
            if (errorCode.startsWith("403")) {
                showNotAuthority(context.getResources().getString(R.string.load_no_authority), listener);
                tipsView.setVisibility(View.INVISIBLE);
            } else {
                if (errorCode.startsWith("500205")) {//彭子烈 房源标记  钥匙、独家、速销、委托书、封盘  特殊处理
                    showError(errorMsg, null, listener);
                } else {
                    showError("数据加载失败：Code " + errorCode + "\n" + errorMsg, null, listener);
                }
            }
        }

    }

    /**
     * 设置Gif背景，以便实现覆盖控件
     *
     * @param resId
     */
    private void setGifWhiteBg(int resId) {
        if (isOnlyGifLoading) {
            isOnlyGifLoading = false;
            loadView.setBackgroundResource(resId);
        }
    }

    /**
     * 显示默认的空数据界面
     *
     * @param listener
     */
    @Override
    public void showEmpty(View.OnClickListener listener) {
        showError("暂无数据", listener);
        iconView.setImageResource(emptyResId);
    }

    /**
     * 显示空数据界面(自定义提示内容)
     *
     * @param msg      提示语
     * @param listener
     */
    public void showEmptys(String msg, View.OnClickListener listener) {
        showError(msg, listener);
        iconView.setImageResource(emptyResId);
    }

    /**
     * 显示无权限页面(使用了showErrors方法的话，当code= 403 开头会显示无权限页面)
     */
    public void showNotAuthority(String msg, View.OnClickListener listener) {
        showError(msg, listener);
        tipsView.setVisibility(View.INVISIBLE);
        iconView.setImageResource(R.mipmap.icon_not_authority);
    }

    /**
     * 显示带有按钮的空数据页面
     *
     * @param msg         提示语
     * @param btnTxt      按钮文本内容
     * @param listener    页面刷新点击事件
     * @param buttonClick 按钮点击事件(处理一些自己业务的特殊需求，例如：在页面数据为空时，显示按钮可以新增房源标记)
     */
    public void showEmptyAndBtn(String msg, String btnTxt, View.OnClickListener listener, OnButtonClick buttonClick) {
        showError(msg, listener);
        iconView.setImageResource(emptyResId);
        tipsView.setVisibility(View.GONE);
        buttonView.setVisibility(View.VISIBLE);
        buttonView.setText(btnTxt);
        buttonView.setOnClickListener(v -> {
            if (buttonClick != null) {
                buttonClick.onBtnClick();
            }
        });
    }

    @Override
    public void showEmpty(String message, View.OnClickListener listener) {
        showError(message, listener);
        iconView.setImageResource(emptyResId);
    }

    @Override
    public void showError(String message) {
        showError(message, null);
        tipsView.setVisibility(View.GONE);
    }

    @Override
    public void showNetworkError(View.OnClickListener listener) {
        showError(context.getResources().getString(R.string.load_network_error), null, listener);
        iconView.setImageResource(R.mipmap.icon_network_error);

    }

    @Override
    public void showTimeOutError(View.OnClickListener listener) {
        showError(context.getResources().getString(R.string.load_time_out_error), null, listener);
        iconView.setImageResource(R.mipmap.icon_time_out_error);
    }

    @Override
    public void hide() {

        isShowLoading = false;
        isShowError = false;

        bindView.setVisibility(View.VISIBLE);
        loadView.animate().alpha(0).setDuration(500).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                try {
                    loadView.setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        }).start();
//        lottieAnimationView.cancelAnimation();
//        lottieAnimationView.clearAnimation();

    }

    @Override
    public void showLoading(String message) {
        showProgress(message);
    }

    public void showOnlyLoadingGif() {
        isOnlyGifLoading = true;
        isShowLoading = true;
        if (!isShowError) {
            loadView.setBackground(null);
        }
        if (bindView.getVisibility() == View.GONE) {
            bindView.setVisibility(View.VISIBLE);
        }
        isShowError = false;
        // 加载中不允许再次点击，避免一直重复刷新
        loadView.setClickable(false);
        bindView.setClickable(false);
        ivLoadGif.setVisibility(View.VISIBLE);
        GifDrawable gifDrawable = (GifDrawable) ivLoadGif.getDrawable();
        if (gifDrawable != null) {
            // 每次加载都从头播放GIF
            gifDrawable.seekTo(0);
        }
        progressBar.setVisibility(View.GONE);
        iconView.setVisibility(View.GONE);
        tipsView.setVisibility(View.GONE);
        buttonView.setVisibility(View.GONE);
        messageView.setText("");
        loadView.setAlpha(1);
        loadView.setVisibility(View.VISIBLE);
    }

    public void hideGifFirst() {
        isShowLoading = false;
        isOnlyGifLoading = false;
        isShowError = false;

        loadView.setVisibility(View.GONE);
        bindView.setClickable(true);
    }

    public void hideGif() {
        isShowLoading = false;
        isOnlyGifLoading = false;
        isShowError = false;
        loadView.animate().alpha(0).setDuration(500).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                loadView.setVisibility(View.GONE);
                bindView.setClickable(true);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        }).start();
    }


    @Override
    public void showLoading() {
        showProgress("正在加载...");
    }

    @Override
    public boolean isShowLoading() {
        return isShowLoading;
    }

    @Override
    public boolean isShowError() {
        return isShowError;
    }

    @Override
    public boolean isShow() {
        return isShowLoading || isShowError;
    }


    private void showProgress(String message) {
        isShowLoading = true;
        isShowError = false;

        // 加载中不允许再次点击，避免一直重复刷新
        loadView.setClickable(false);

        ivLoadGif.setVisibility(View.VISIBLE);
        GifDrawable gifDrawable = (GifDrawable) ivLoadGif.getDrawable();
        if (gifDrawable != null) {
            gifDrawable.seekTo(0); // 每次加载都从头播放GIF
        }
//        Glide.with(context)
//                .load(R.drawable.yjyz_logo_loading)
//                .into(ivLoadGif);
//        GifDrawable drawable = (GifDrawable) ivLoadGif.getDrawable();
//        if (drawable != null) {
//            drawable.stop();
//            drawable.startFromFirstFrame();
//        }
        progressBar.setVisibility(View.GONE);

        iconView.setVisibility(View.GONE);
        tipsView.setVisibility(View.GONE);
        buttonView.setVisibility(View.GONE);
        messageView.setText(message);
        bindView.setVisibility(View.GONE);
        loadView.setAlpha(1);
        loadView.setVisibility(View.VISIBLE);
    }

    // 按钮点击事件(根据业务需求选择性实现)
    public interface OnButtonClick {
        void onBtnClick();
    }

    /**
     * 设置空界面的icon
     */
    public void setEmptyResId(int emptyResId) {
        this.emptyResId = emptyResId;
    }

}
