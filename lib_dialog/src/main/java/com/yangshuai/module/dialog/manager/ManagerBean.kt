package com.yangshuai.module.dialog.manager

import android.app.Dialog
import android.content.Context
import com.yangshuai.module.dialog.base.BaseBean

/**
 * @Author yangshuai
 * @Date : 2023-04-03 15:44
 * @Version 1.0
 * Description:
 */
class ManagerBean {
    var context:Context? = null
    var bean:BaseBean? = null
    var dialog: Dialog? = null
    constructor(context: Context,bean: BaseBean,dialog: Dialog){
        this.context = context
        this.bean = bean
        this.dialog = dialog
    }
}