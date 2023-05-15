package com.yangshuai.library.base.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.WindowManager;

import com.yangshuai.library.base.utils.SystemUtils;
import com.yangshuai.library.base.utils.Utils;

/**
 * @Author hcp
 * @Created 4/15/19
 * @Editor hcp
 * @Edited 4/15/19
 * @Type
 * @Layout
 * @Api
 */
public class LoadingDialog {
    private ProgressDialog progressDialog;

    public LoadingDialog(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("正在提交...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);


        // 解决dialog在部分手机右边有默认间距问题 padding和LayoutParams一起使用才有效
        if (SystemUtils.v26()) {
            progressDialog
                    .getWindow()
                    .getDecorView()
                    .setPadding(Utils.dp2Px(context, 16), Utils.dp2Px(context, 16), Utils.dp2Px(context, 16), Utils.dp2Px(context, 16));
            WindowManager.LayoutParams layoutParams =
                    progressDialog
                            .getWindow().getAttributes();
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            progressDialog.getWindow().setAttributes(layoutParams);
        }

    }

    public void setLoadingMessage(String msg) {
        progressDialog.setMessage(msg);
    }

    public void show(String msg) {
        setLoadingMessage(msg);
        show();
    }

    public void show() {
        progressDialog.show();
    }

    public void dismiss() {
        progressDialog.dismiss();
    }
}
