package com.yangshuai.library.base.dialog;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * @Author hcp
 * @Created 4/28/19
 * @Editor hcp
 * @Edited 4/28/19
 * @Type
 * @Layout
 * @Api
 */
public class ProgressLoading {

    private ProgressDialog progressDialog;

    public ProgressLoading(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setTitle("  ");
        progressDialog.setMessage("正在上传");
        progressDialog.setMax(100);
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        progressDialog.setTitle(title);
    }

    /**
     * 设置提示信息
     *
     * @param msg
     */
    public void setMessage(String msg) {
        progressDialog.setMessage(msg);
    }


    /**
     * 设置进度最大值
     *
     * @param max
     */
    public void setMax(int max) {
        progressDialog.setMax(max);
    }

    /**
     * 设置当前进度
     *
     * @param progress
     */
    public void setProgress(int progress) {
        progressDialog.setProgress(progress);
    }

    public void show() {
        progressDialog.setMax(100);
        progressDialog.setProgress(0);
        progressDialog.show();
    }

    public void dismiss() {
        try {
            progressDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ProgressDialog getProgressDialog() {
        return progressDialog;
    }
}
