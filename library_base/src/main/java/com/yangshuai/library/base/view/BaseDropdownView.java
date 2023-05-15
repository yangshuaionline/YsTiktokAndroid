package com.yangshuai.library.base.view;

import android.content.Context;
import android.graphics.Color;
import androidx.core.view.ViewCompat;

import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.yangshuai.library.base.utils.SoftKeyBoardUtils;

/**
 * @Author hcp
 * @Created 5/17/19
 * @Editor hcp
 * @Edited 5/17/19
 * @Type
 * @Layout
 * @Api
 */
public abstract class BaseDropdownView extends FrameLayout {

    private View mContentView;
    private static final int ANIM_DURATION = 300;

    private boolean isShowing = false;

    public BaseDropdownView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBackgroundColor(Color.parseColor("#66000000"));
        setVisibility(GONE);
        initData(context, attrs);
        mContentView = onCreateView(this);
        addView(mContentView);

        setOnClickListener(v -> dismiss());
    }

    protected void initData(Context context, AttributeSet attrs) {

    }

    public void show() {
        isShowing = true;
        setVisibility(VISIBLE);
        //viewModel.clear();
        int contentHeight = mContentView.getMeasuredHeight();
        ViewCompat.animate(mContentView).translationY(contentHeight == 0 ? -1300 : -contentHeight).setDuration(0).start();
        ViewCompat.animate(mContentView).translationY(0).setDuration(ANIM_DURATION).start();
        ViewCompat.animate(this).alpha(0).setDuration(0).start();
        ViewCompat.animate(this).alpha(1).setDuration(ANIM_DURATION).start();
    }

    public void dismiss() {
        isShowing = false;
        ViewCompat.animate(mContentView).translationY(-mContentView.getMeasuredHeight()).setDuration(ANIM_DURATION).start();
        ViewCompat.animate(this).alpha(0).setDuration(ANIM_DURATION).start();
        postDelayed(() -> {
            SoftKeyBoardUtils.closeKeyBoard(mContentView);
            setVisibility(GONE);
        }, ANIM_DURATION);
    }

    protected abstract View onCreateView(ViewGroup viewGroup);

    public boolean isShowing() {
        return isShowing;
    }

}
