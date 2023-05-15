package com.yangshuai.module.dialog.ios

import com.yangshuai.module.dialog.base.BaseBean

/**
 * @Author yangshuai
 * @Date : 2023-03-22 17:19
 * @Version 1.0
 * Description:
 * 使用者调用dialog传递的bean类
 */
class IOSBean: BaseBean() {
    var list:MutableList<IOSData>? = null//列表显示内容
    var callBack:IOSListClickCallBack? = null//列表点击回调
    var haveLine:Boolean = false//是否有分割线
    fun setList(list:MutableList<IOSData>):IOSBean{
        this.list = list
        return this
    }
    fun setItemClickCallBack(callBack: IOSListClickCallBack):IOSBean{
        this.callBack = callBack
        return this
    }
    fun setHaveLine(have:Boolean):IOSBean{
        this.haveLine = have
        return this
    }
}