package com.yangshuai.library.base.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * 处理DrawableLeft与DrawableRight的点击事件
 *
 * @Author hcp
 * @Created 2020/9/18
 * @Editor hcp
 * @Edited 2020/9/18
 * @Type
 * @Layout
 * @Api
 */
public class DrawableClickTextView extends AppCompatTextView {
    /**
     * TextView四周drawable的序号。
     * 0 left,  1 top, 2 right, 3 bottom
     */
    public final int LEFT = 0;
    public final int RIGHT = 2;

    private OnDrawableClickListener drawableClickListener;

    public DrawableClickTextView(Context context) {
        super(context);
    }

    public DrawableClickTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawableClickTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public interface OnDrawableClickListener {
        default void onLeft(View v) {
        }

        void onRight(View v);
    }

    public void setOnDrawableClickListener(OnDrawableClickListener onDrawableClickListener) {
        this.drawableClickListener = onDrawableClickListener;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (drawableClickListener != null) {
                    Drawable drawableLeft = this.getCompoundDrawables()[LEFT];
                    if (drawableLeft != null && event.getRawX() <= (this.getLeft() + drawableLeft.getBounds().width())) {
                        drawableClickListener.onLeft(this);
                        return true;
                    }

                    Drawable drawableRight = this.getCompoundDrawables()[RIGHT];
                    if (drawableRight != null && event.getRawX() >= (this.getRight() - drawableRight.getBounds().width())) {
                        drawableClickListener.onRight(this);
                        return true;
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }
}
