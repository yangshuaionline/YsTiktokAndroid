package com.yangshuai.module.dialog.alert

import com.yangshuai.module.dialog.base.BaseBean
import com.yangshuai.module.dialog.list.OnItemChooseBackListener

/**
 * @Author yangshuai
 * @Date : 2023-03-23 17:02
 * @Version 1.0
 * Description:创建dialog调用的bean，根据bean生成相应的dialog
 */
class AlertEditBean: BaseBean() {
    var message:String? = null//输入内容
    var hint:String? = null//提示文字
    var listener: OnItemChooseBackListener? = null
    fun setMessage(s:String):AlertEditBean{
        this.message = s
        return this
    }
    fun setHint(s:String):AlertEditBean{
        this.hint = s
        return this
    }
    fun setListener(listener:OnItemChooseBackListener):AlertEditBean{
        this.listener = listener
        return this
    }
}