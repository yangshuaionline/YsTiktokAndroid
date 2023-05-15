package com.yangshuai.module.login.dialog;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * @author hcp
 * @Created 4/15/19
 * @author hcp
 * @Edited 4/15/19
 * @Type
 * @Layout
 * @Api
 */
public class LoadingDialog {
    private ProgressDialog progressDialog;

    public LoadingDialog(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("加载中...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
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
