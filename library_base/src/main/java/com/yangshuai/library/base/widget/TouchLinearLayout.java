package com.yangshuai.library.base.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * @author hcp
 * on 2019-08-23
 */
public class TouchLinearLayout extends LinearLayout {

    public float mRawX;
    public float mRawY;


    public TouchLinearLayout(Context context) {
        super(context);
    }

    public TouchLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TouchLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            mRawX = ev.getRawX();
            mRawY = ev.getRawY();
        }

        return super.onInterceptTouchEvent(ev);
    }



}
