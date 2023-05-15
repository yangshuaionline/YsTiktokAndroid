package com.yangshuai.module.dialog.alert

import com.yangshuai.module.dialog.base.BaseBeanSet

/**
 * @Author yangshuai
 * @Date : 2023-03-23 17:02
 * @Version 1.0
 * Description:创建dialog调用的bean，根据bean生成相应的dialog
 */
open class AlertBeanSet: BaseBeanSet() {
    var imageUrl:String? = null//图片路径
    fun setImageUrl(s:String):AlertBeanSet{
        this.imageUrl = s
        return this
    }
}