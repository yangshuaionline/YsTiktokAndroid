package com.yangshuai.module.dialog.ios

/**
 * @Author yangshuai
 * @Date : 2023-03-27 09:50
 * @Version 1.0
 * Description:
 * 选择IOS列表传递和返回的data，怎么传怎么取
 */
class IOSData{
    var id:String? = null
    var content:String? = null//内容显示
    fun setID(s:String):IOSData{
        this.id = s
        return this
    }
    fun setContent(s:String):IOSData{
        this.content = s
        return this
    }
}