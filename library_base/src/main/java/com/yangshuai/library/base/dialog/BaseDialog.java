package com.yangshuai.library.base.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.yangshuai.library.base.R;

/**
 * 基础对话框
 * 功能：
 * 1. 设置标题
 * 2. 设置内容布局
 * 3. 设置左右按钮
 * <p>
 * 使用例子:
 * {@link com.yangshuai.library.base.dialog.AlertDialog}
 *
 * @Author hcp
 * @Created 3/20/19
 * @Editor hcp
 * @Edited 3/20/19
 * @Type
 * @Layout
 * @Api
 */
public abstract class BaseDialog extends Dialog implements DialogLifecycle {

    private TextView tvDialogTitle;
    private FrameLayout layoutDialogContent;
    public Button btnDialogLeft, btnDialogRight, btnDialogCenter;
    public ImageView ivDialogClose;

    public BaseDialog(@NonNull Context context) {
        super(context, R.style.BaseDialog);
        setContentView(R.layout.base_dialog);
        initView();
    }

    private void initView() {
        tvDialogTitle = findViewById(R.id.tv_dialog_title);
        layoutDialogContent = findViewById(R.id.layout_dialog_content);
        btnDialogLeft = findViewById(R.id.btn_dialog_left);
        btnDialogRight = findViewById(R.id.btn_dialog_right);
        btnDialogCenter = findViewById(R.id.btn_dialog_center);
        ivDialogClose = findViewById(R.id.iv_dialog_close);
        layoutDialogContent.addView(createContentView(layoutDialogContent), FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        tvDialogTitle.setText(title);
    }

    /**
     * 设置标题的不同颜色
     *
     * @param color
     * @param titleAndColor
     */
    public void setTitleAndColor(int color, String titleAndColor) {
        tvDialogTitle.setTextColor(color);
        tvDialogTitle.setText(Html.fromHtml(titleAndColor));
    }

    /**
     * 设置标题位置
     *
     * @param gravity
     */
    public void setTitleGravity(int gravity) {
        tvDialogTitle.setGravity(gravity);
    }

    /**
     * 设置左边按钮，不设置则不显示
     *
     * @param text
     * @param listener
     */
    public void setLeftButton(String text, View.OnClickListener listener) {
        btnDialogLeft.setVisibility(View.VISIBLE);
        btnDialogLeft.setText(text);
        btnDialogLeft.setOnClickListener(listener);
    }

    /**
     * 设置右边按钮，不设置则不显示
     *
     * @param text
     * @param listener
     */
    public void setRightButton(String text, View.OnClickListener listener) {
        btnDialogRight.setVisibility(View.VISIBLE);
        btnDialogRight.setText(text);
        btnDialogRight.setOnClickListener(listener);
    }

    /**
     * 设置中间按钮，不设置则不显示
     * tips:仅支持中间按钮单独显示，不与左右按钮同时显示
     */
    public void setCenterButton(String text, View.OnClickListener listener) {
        btnDialogCenter.setVisibility(View.VISIBLE);
        btnDialogCenter.setText(text);
        btnDialogCenter.setOnClickListener(listener);
    }

    public void setIvDialogClose(View.OnClickListener listener){
        ivDialogClose.setVisibility(View.VISIBLE);
        ivDialogClose.setOnClickListener(listener);
    }

    /**
     * 创建内容布局
     *
     * @param viewGroup 内容布局依赖的父布局
     * @return 返回内容布局
     */
    protected abstract View createContentView(ViewGroup viewGroup);

    /**
     * 设置对话框内容高度
     * @param height  需要转换dp之后入参
     */
    public void setContentHeight(int height) {
        if (height != 0 && layoutDialogContent != null) {
            ViewGroup.LayoutParams params = layoutDialogContent.getLayoutParams();
            params.height = height;
            layoutDialogContent.setLayoutParams(params);
        }
    }
    /**
     * 显示对话框
     */
    @Override
    public void show() {
        super.show();
        onShow();
    }

    /**
     * 隐藏对话框
     */
    @Override
    public void dismiss() {
        onDismiss();
        super.dismiss();
    }

    protected void hideTitle() {
        tvDialogTitle.setVisibility(View.GONE);
    }
}
