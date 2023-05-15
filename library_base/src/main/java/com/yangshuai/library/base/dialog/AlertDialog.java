package com.yangshuai.library.base.dialog;

import android.content.Context;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.yangshuai.library.base.R;
import com.yangshuai.library.base.utils.Utils;
import com.yangshuai.library.base.widget.MaxHeightScrollView;

/**
 * 消息提示对话框
 * <p>
 * 使用例子：
 * {@link com.yangshuai.library.base.dialog.Examples#showAlertDialog}
 *
 * @Author hcp
 * @Created 3/20/19
 * @Editor hcp
 * @Edited 3/20/19
 * @Type
 * @Layout
 * @Api
 */
public class AlertDialog extends BaseDialog {

    private MaxHeightScrollView mScrollView; // 跟布局
    private TextView tvMessage;

    public AlertDialog(@NonNull Context context) {
        super(context);
    }

    /**
     * 定义自己的内容布局
     *
     * @param viewGroup 内容布局依赖的父布局
     * @return
     */
    @Override
    protected View createContentView(ViewGroup viewGroup) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.base_dialog_alert, viewGroup, false);
        tvMessage = view.findViewById(R.id.tv_dialog_alert_message);
        mScrollView = view.findViewById(R.id.scrollView);
        return view;
    }

    @Override
    public void onShow() {

    }

    @Override
    public void onDismiss() {

    }

    /**
     * 设置消息内容
     *
     * @param msg
     */
    public void setMessage(CharSequence msg) {
        if (msg == null) return;
        tvMessage.setText(msg);
    }

    /**
     * 设置富文本可以点击
     */
    public void setMovementMethod() {
        tvMessage.setMovementMethod(LinkMovementMethod.getInstance());
    }

    /**
     * 设置文字对齐
     *
     * @param gravity
     */
    public void setMessageGravity(int gravity) {
        tvMessage.setGravity(gravity);
    }

    /**
     * 设置上文字边距
     *
     * @param
     * @param dp
     */
    public void setMessagePaddingTop(int dp) {
        tvMessage.setPadding(0, Utils.dp2Px(getContext(), dp), 0, 0);
    }

    /**
     * 设置跟布局的滚动条高度
     *
     * @param height 需要转换dp之后入参
     */
    public void setScrollViewMaxHeight(int height) {
        if (height != 0 && mScrollView != null) {
            mScrollView.setMaxHeight(height);
        }
    }

    /**
     * 隐藏标题
     */
    public void setHideDialogTitle() {
        hideTitle();
    }

    /**
     * 设置文字大小
     */
    public void setMessageSize(float size) {
        tvMessage.setTextSize(size);
    }

}
