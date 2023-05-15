package com.yangshuai.library.base.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import androidx.core.view.ViewCompat;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

import com.yangshuai.library.base.R;

/**
 * 基础下拉组件：继承该组件实现统一的下拉效果
 *
 * @Author hcp
 * @Created 3/26/19
 * @Editor hcp
 * @Edited 3/26/19
 * @Type
 * @Layout
 * @Api
 */
public abstract class BaseDropdown extends PopupWindow {

    private View mWindowRootView;
    private View mContentView;
    // 显示位置参照的View
    private View mAnchorView;
    // 下拉动画执行的时间
    private static final int ANIM_DURATION = 300;
    private int screenHeight;
    private int offsetY = -1;

    public BaseDropdown(Activity context, View anchorView) {
        super(View.inflate(context, R.layout.base_dropdown, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        mWindowRootView = context.findViewById(android.R.id.content);
        mAnchorView = anchorView;
        screenHeight = getScreenHeight(context);
        initView();
    }

    /**
     * 调用此方法显示下拉列表
     */
    public void show() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (offsetY == -1) {
                int[] location = new int[2];
                mAnchorView.getLocationInWindow(location);
                offsetY = location[1] + mAnchorView.getHeight();
            }

            int height = mWindowRootView.getHeight()-mAnchorView.getHeight();

            setHeight(height);

            showAtLocation(mAnchorView, Gravity.NO_GRAVITY, 0, offsetY);
        } else {
            showAsDropDown(mAnchorView);
        }

        ViewCompat.animate(mContentView).translationY(-mWindowRootView.getHeight()).setDuration(0).start();
        ViewCompat.animate(mContentView).translationY(0).setDuration(ANIM_DURATION).start();
        ViewCompat.animate(getContentView()).alpha(0).setDuration(0).start();
        ViewCompat.animate(getContentView()).alpha(1).setDuration(ANIM_DURATION).start();
    }

    /**
     * 调用此方法关闭下拉列表
     */
    @Override
    public void dismiss() {
        ViewCompat.animate(mContentView).translationY(-mWindowRootView.getHeight()).setDuration(ANIM_DURATION).start();
        ViewCompat.animate(getContentView()).alpha(1).setDuration(0).start();
        ViewCompat.animate(getContentView()).alpha(0).setDuration(ANIM_DURATION).start();

        mContentView.postDelayed(BaseDropdown.super::dismiss, ANIM_DURATION);
    }

    private void initView() {

        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setAnimationStyle(android.R.style.Animation);
        FrameLayout root = getContentView().findViewById(R.id.dropdown_root);
        mContentView = createContentView(root);
        root.addView(mContentView);
        getContentView().setOnClickListener(v -> dismiss());
    }

    /**
     * 实现此方法添加内容布局
     *
     * @param parent
     * @return
     */
    protected abstract View createContentView(ViewGroup parent);

    private int getScreenHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }
}
