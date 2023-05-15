package com.yangshuai.module.dialog.picker

/**
 * @Author yangshuai
 * @Date : 2023-03-30 15:14
 * @Version 1.0
 * Description:
 */
interface ChooseSubListener {
    fun chooseItems(list:MutableList<PickerData>)//返回选择的items
}