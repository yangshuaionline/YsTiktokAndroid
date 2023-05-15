package com.yangshuai.library.base.utils;

import android.app.Activity;
import android.graphics.Rect;
import androidx.annotation.NonNull;
import android.view.View;

/**
 * 监听软键盘弹出与收起
 *
 * @Author hcp
 * @Created 5/21/19
 * @Editor hcp
 * @Edited 5/21/19
 * @Type
 * @Layout
 * @Api
 */
public class SoftKeyboard {
    private View rootView;
    private int rootViewVisibleHeight = 0;

    private SoftKeyboard(Activity activity) {
        rootView = activity.getWindow().getDecorView();
    }

    public static SoftKeyboard of(Activity activity) {
        return new SoftKeyboard(activity);
    }

    public void listen(@NonNull OnSoftKeyboardChangeListener listener) {
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            Rect r = new Rect();

            rootView.getWindowVisibleDisplayFrame(r);

            int visibleHeight = r.height();

            if (rootViewVisibleHeight == 0) {
                rootViewVisibleHeight = visibleHeight;
                return;
            }

            if (rootViewVisibleHeight - visibleHeight > 200) {
                listener.onShow();
            }

            if (visibleHeight - rootViewVisibleHeight > 200) {
                listener.onHide();
            }

            rootViewVisibleHeight = visibleHeight;

        });
    }

    public interface OnSoftKeyboardChangeListener {
        void onHide();

        void onShow();
    }
}
