package com.yangshuai.module.dialog.image

import com.yangshuai.module.dialog.base.BaseBean

/**
 * @Author yangshuai
 * @Date : 2023-03-23 17:02
 * @Version 1.0
 * Description:创建图片
 */
class ImageBean: BaseBean() {
    var url:String? = null
    fun setUrl(s:String):ImageBean{
        this.url = s
        return this
    }
}