package com.yangshuai.library.base.utils;

import android.app.Activity;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.List;

/**
 * 操作软件盘
 *
 * @Author hcp
 * @Created 3/15/19
 * @Editor hcp
 * @Edited 3/15/19
 * @Type
 * @Layout
 * @Api
 */
public class SoftKeyBoardUtils {

    /**
     * 关闭软键盘
     */
    public static void closeKeyBoard(Activity activity) {
        View view = activity.getWindow().getDecorView();
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void closeKeyBoard(View target) {
        InputMethodManager imm = (InputMethodManager) target.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(target.getWindowToken(), 0);
    }

    public static void showKeyBoard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

    }

    public static void showKeyBoard(View target) {
        InputMethodManager imm = (InputMethodManager) target.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 当点击其他View时隐藏软键盘
     * @param activity
     * @param ev
     * @param excludeViews  点击这些View不会触发隐藏软键盘动作
     */
    public static final void hideInputWhenTouchOtherView(Activity activity, MotionEvent ev, List<View> excludeViews){
        if (ev.getAction() == MotionEvent.ACTION_DOWN){
            if (excludeViews != null && !excludeViews.isEmpty()){
                for (int i = 0; i < excludeViews.size(); i++){
                    if (isTouchView(excludeViews.get(i), ev)){
                        return;
                    }
                }
            }
            View v = activity.getCurrentFocus();
            if (isShouldHideInput(v, ev)){
                InputMethodManager inputMethodManager = (InputMethodManager)
                        activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager != null){
                    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }

        }
    }

    public static final boolean isTouchView(View view, MotionEvent event){
        if (view == null || event == null){
            return false;
        }
        int[] leftTop = {0, 0};
        view.getLocationInWindow(leftTop);
        int left = leftTop[0];
        int top = leftTop[1];
        int bottom = top + view.getHeight();
        int right = left + view.getWidth();
        if (event.getRawX() > left && event.getRawX() < right
                && event.getRawY() > top && event.getRawY() < bottom){
            return true;
        }
        return false;
    }

    public static final boolean isShouldHideInput(View v, MotionEvent event){
        if (v != null && (v instanceof EditText)){
            return !isTouchView(v, event);
        }
        return false;
    }
}
