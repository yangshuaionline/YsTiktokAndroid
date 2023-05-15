package com.yangshuai.module.dialog.picker

/**
 * @Author yangshuai
 * @Date : 2023-03-30 11:11
 * @Version 1.0
 * Description:
 */
class PickerData {
    var icon:String? = null//图标url
    var text:String? = null//文字
    var isChoose:Boolean? = null//是否选中
    fun setIcon(s:String):PickerData{
        this.icon = s
        return this
    }
    fun setText(s:String):PickerData{
        this.text = s
        return this
    }
    fun isChoose(choose:Boolean):PickerData{
        this.isChoose = choose
        return this
    }
}