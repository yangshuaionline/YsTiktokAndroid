package com.yangshuai.library.base.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.yangshuai.library.base.R;
import com.yangshuai.library.base.utils.StringUtils;
import com.yangshuai.library.base.view.BaseWebView;

/**
 * @author hcp
 * Create on 2020-03-19 10:38
 */
public class WebViewDialog extends Dialog implements DialogLifecycle {

    private BaseWebView webView;
    private TextView tvDialogTitle;
    private FrameLayout layoutDialogContent;
    public Button btnDialogLeft, btnDialogRight, btnDialogCenter;


    public WebViewDialog(Context context) {
        super(context, R.style.BaseDialog);
        setContentView(R.layout.base_dialog_webview);
        initView();
    }

    private void initView() {
        webView = findViewById(R.id.webView);
        tvDialogTitle = findViewById(R.id.tv_dialog_title);
        layoutDialogContent = findViewById(R.id.layout_dialog_content);
        btnDialogLeft = findViewById(R.id.btn_dialog_left);
        btnDialogRight = findViewById(R.id.btn_dialog_right);
        btnDialogCenter = findViewById(R.id.btn_dialog_center);
    }

    public void setUrl(String url) {
        if (StringUtils.isEmpty(url)) return;
        webView.loadUrl(url);
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

    @Override
    public void onShow() {

    }

    @Override
    public void onDismiss() {

    }
}
