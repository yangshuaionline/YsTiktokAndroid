package com.yangshuai.module.dialog.picker

import com.yangshuai.module.dialog.base.BaseBeanSet

/**
 * @Author yangshuai
 * @Date : 2023-03-23 17:02
 * @Version 1.0
 * Description:创建dialog调用的bean，根据bean生成相应的dialog
 */
open class PickerBeanSet: BaseBeanSet() {
    var imageUrl:String? = null//图片路径
    var scroller1:MutableList<PickerData>? = null
    var scroller2:MutableList<PickerData>? = null
    var scroller3:MutableList<PickerData>? = null
    fun setImageUrl(s:String):PickerBeanSet{
        this.imageUrl = s
        return this
    }
    fun setScroller1(list:MutableList<PickerData>):PickerBeanSet{
        this.scroller1 = list
        return this
    }
    fun setScroller2(list:MutableList<PickerData>):PickerBeanSet{
        this.scroller2 = list
        return this
    }
    fun setScroller3(list:MutableList<PickerData>):PickerBeanSet{
        this.scroller3 = list
        return this
    }
}