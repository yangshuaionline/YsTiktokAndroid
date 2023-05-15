package com.yangshuai.module.dialog.list

/**
 * @Author yangshuai
 * @Date : 2023-03-27 09:50
 * @Version 1.0
 * Description:
 * 选择列表传递和返回的data，怎么传怎么取
 */
class ListData{
    var id:String? = null
    var title:String? = null //如果样式有title的时候传递
    var content:String? = null//内容显示
    var hint:String? = null//EditText hint属性
    var isChoose:Boolean = false//是否选中（只能设置一个）
    var isCopy:Boolean? = null//是否可以复制
    var isContentRight:Boolean? = null//content是否靠右展示
}