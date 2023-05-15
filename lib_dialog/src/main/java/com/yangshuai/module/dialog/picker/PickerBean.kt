package com.yangshuai.module.dialog.picker

import com.yangshuai.module.dialog.base.BaseBean

/**
 * @Author yangshuai
 * @Date : 2023-03-23 17:02
 * @Version 1.0
 * Description:创建picker_dialog调用的bean，根据bean生成相应的picker_dialog
 */
open class PickerBean: BaseBean() {
    var rvList:MutableList<PickerData>? = null//recyclerview列表
    var chooseSubListener:ChooseSubListener? = null//选择的条目列表
    var row:Int = 1//grid模式下的列数
    var maxChooseCount:Int? = null//最多选中条目
    var maxChooseListener:MaxChooseListener? = null//最大选择回调
    var rvType:PickerEnum = PickerEnum.GRID_VIEW//展示形式
    var canClick:Boolean = false//是否可以点击
    var scrollerList1:MutableList<PickerData>? = null//滚动列表
    var scrollerList2:MutableList<PickerData>? = null//滚动列表
    var scrollerList3:MutableList<PickerData>? = null//滚动列表
    var scrollerListener:ChooseSubListener? = null//滚动选择回调


    fun setRvList(list:MutableList<PickerData>):PickerBean{
        this.rvList = list
        return this
    }
    fun setMaxChooseCount(count:Int):PickerBean{
        this.maxChooseCount = count
        return this
    }
    fun setGridType():PickerBean{
        rvType = PickerEnum.GRID_VIEW
        return this
    }
    fun setListType():PickerBean{
        rvType = PickerEnum.LIST_VIEW
        return this
    }
    fun setFlowType():PickerBean{
        rvType = PickerEnum.FLOW
        return this
    }
    fun setRow(row:Int):PickerBean{
        this.row = row
        return this
    }
    fun setMaxChooseListener(listener: MaxChooseListener):PickerBean{
        this.maxChooseListener = listener
        return this
    }
    fun setCanClick(can:Boolean):PickerBean{
        this.canClick = can
        return this
    }
    fun setChooseSubListener(listener:ChooseSubListener):PickerBean{
        this.chooseSubListener = listener
        return this
    }
    fun setScrollerList1(list:MutableList<PickerData>):PickerBean{
        this.scrollerList1 = list
        return this
    }
    fun setScrollerList2(list:MutableList<PickerData>):PickerBean{
        this.scrollerList2 = list
        return this
    }
    fun setScrollerList3(list:MutableList<PickerData>):PickerBean{
        this.scrollerList3 = list
        return this
    }
    fun setScrollerListener(scrollerListener:ChooseSubListener):PickerBean{
        this.scrollerListener = scrollerListener
        return this
    }
}