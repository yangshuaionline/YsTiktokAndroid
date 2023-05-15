package com.yangshuai.library.base.entity;

import android.app.Dialog;
import android.view.View;

/**
 * @Description: java类作用描述
 * @Author:
 * @CreateDate: 2020/3/26 15:42
 * @UpdateUser: hcp'
 * @UpdateDate: 2020/3/26 15:42
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class DialogBean {

    private Dialog dialog;
    private View dialogView;//dialog的view
    private int priority;//优先级

    public DialogBean(Dialog dialog, int priority) {
        this.dialog = dialog;
        this.priority = priority;
    }

    public Dialog getDialog() {
        return dialog;
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public View getDialogView() {
        return dialogView;
    }

    public void setDialogView(View dialogView) {
        this.dialogView = dialogView;
    }
}
