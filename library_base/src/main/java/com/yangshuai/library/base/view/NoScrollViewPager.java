package com.yangshuai.library.base.view;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @Author hcp
 * @Created 6/3/19
 * @Editor hcp
 * @Edited 6/3/19
 * @Type
 * @Layout
 * @Api
 */
public class NoScrollViewPager extends ViewPager {

    public NoScrollViewPager(@NonNull Context context) {
        super(context);
    }

    public NoScrollViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return false;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {

        return false;
    }
}
