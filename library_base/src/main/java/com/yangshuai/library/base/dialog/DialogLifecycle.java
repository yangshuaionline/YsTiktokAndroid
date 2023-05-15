package com.yangshuai.library.base.dialog;

/**
 * @Author hcp
 * @Created 3/20/19
 * @Editor hcp
 * @Edited 3/20/19
 * @Type
 * @Layout
 * @Api
 */
public interface DialogLifecycle {
    /**
     * 在对话框展示时作一些操作，例如恢复一些初始化状态
     */
    void onShow();

    /**
     * 在对话框消失时作一些操作，例如有输入控件要关闭软键盘等
     */
    void onDismiss();
}
