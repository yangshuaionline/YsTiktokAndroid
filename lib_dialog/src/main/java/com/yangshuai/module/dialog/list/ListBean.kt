package com.yangshuai.module.dialog.list

import com.yangshuai.module.dialog.alert.AlertBean

/**
 * @Author yangshuai
 * @Date : 2023-03-27 09:52
 * @Version 1.0
 * Description:生成列表样式的dialog
 */
class ListBean : AlertBean() {
    var list:MutableList<ListData>? = null
    var listener:OnItemChooseBackListener? = null
    var position:Int = 0
    var haveLine:Boolean = false
    fun setList(list:MutableList<ListData>):ListBean{
        this.list = list
        for(item in list.indices){
            if(item == position){
                list[item].isChoose = true
                break
            }
        }
        return this
    }
    fun setItemChooseBackListener(listener:OnItemChooseBackListener):ListBean{
        this.listener = listener
        return this
    }
    fun setPosition(position:Int):ListBean{
        this.position = position
        return this
    }
    fun showLine():ListBean{
        this.haveLine = true
        return this
    }
}