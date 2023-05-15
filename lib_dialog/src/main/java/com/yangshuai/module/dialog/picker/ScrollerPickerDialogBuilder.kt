package com.yangshuai.module.dialog.picker

import android.content.Context
import android.view.View
import androidx.annotation.StyleRes
import com.yangshuai.module.dialog.base.BaseBean


import com.yangshuai.module.dialog.*
import com.yangshuai.module.dialog.base.BaseBeanSet
import com.yangshuai.module.dialog.picker.base.BasePickerDialogBuilder
import com.yangshuai.module.dialog.views.GetConfigReq
import com.yangshuai.module.dialog.views.PickerScrollView


/**
 * @Author yangshuai
 * @Date : 2023-03-22 17:52
 * @Version 1.0
 * Description:
 * recyclerview样式的布局
 * 1.gridview样式
 * 2.listview样式
 * 3.瀑布流样式
 */
open class ScrollerPickerDialogBuilder: BasePickerDialogBuilder{
    private var view:View? = null//本页面布局view
    private var scroller1: PickerScrollView? = null//滚动布局
    private var scroller2: PickerScrollView? = null//滚动布局
    private var scroller3: PickerScrollView? = null//滚动布局
    private var dataBean:PickerBean? = null//生成dialog数据bean
    private var chooseList:MutableList<PickerData> = mutableListOf()
    //默认主题样式
    constructor(context: Context):this(context, DialogConst.style)
    //设置主题样式
    constructor(context: Context, @StyleRes themeResId:Int):super(context,themeResId){
        view = View.inflate(context, R.layout.dialog_picker_scroller,null)
        view?.let {
            scroller1 = it.findViewById(R.id.picker_dialog_scroller1)
            scroller2 = it.findViewById(R.id.picker_dialog_scroller2)
            scroller3 = it.findViewById(R.id.picker_dialog_scroller3)
        }
        scroller1?.visibility = View.GONE
        scroller2?.visibility = View.GONE
        scroller3?.visibility = View.GONE
        super.setChild(view)
    }
    override fun setBean(bean: BaseBean): DialogBuilder {
        dataBean = bean as PickerBean
        return super.setBean(bean)
    }
    //build()之后更新数据
    override fun setBeanSet(bean: BaseBeanSet): DialogBuilder {
        if(bean is PickerBeanSet){
            bean.scroller1?.let {sList->
                val list = mutableListOf<GetConfigReq.DatasBean>()
                for(item in sList.iterator()){
                    val gdBean = GetConfigReq.DatasBean()
                    gdBean.categoryName = item.text
                    list.add(gdBean)
                }
                if (chooseList.size < 1&&list.size>0) {
                    val pickerData = PickerData()
                    pickerData.text = list[0].categoryName
                    chooseList.add(pickerData)
                }else{
                    chooseList[0].text = list[0].categoryName
                }
                scroller1?.let {scr->
                    with(scr){
                        visibility = View.VISIBLE
                        setData(list)
                        setSelected(0)
                        //滚动监听
                        //滚动监听
                        setOnSelectListener { pickers ->
                            chooseList[0].text = pickers.categoryName
                        }
                    }
                }
            }
            bean.scroller2?.let {sList->
                val list = mutableListOf<GetConfigReq.DatasBean>()
                for(item in sList.iterator()){
                    val gdBean = GetConfigReq.DatasBean()
                    gdBean.categoryName = item.text
                    list.add(gdBean)
                }
                if (chooseList.size < 2&&list.size>1) {
                    val pickerData = PickerData()
                    pickerData.text = list[0].categoryName
                    chooseList.add(pickerData)
                }else{
                    chooseList[1].text = list[0].categoryName
                }
                scroller2?.let {scr->
                    with(scr){
                        visibility = View.VISIBLE
                        setData(list)
                        setSelected(0)
                        //滚动监听
                        //滚动监听
                        setOnSelectListener { pickers ->
                            chooseList[1].text = pickers.categoryName
                        }
                    }
                }
            }
            bean.scroller3?.let {sList->
                val list = mutableListOf<GetConfigReq.DatasBean>()
                for(item in sList.iterator()){
                    val gdBean = GetConfigReq.DatasBean()
                    gdBean.categoryName = item.text
                    list.add(gdBean)
                }
                if (chooseList.size < 3&&list.size>2) {
                    val pickerData = PickerData()
                    pickerData.text = list[0].categoryName
                    chooseList.add(pickerData)
                }else{
                    chooseList[2].text = list[0].categoryName
                }
                scroller3?.let {scr->
                    with(scr){
                        visibility = View.VISIBLE
                        setData(list)
                        setSelected(0)
                        //滚动监听
                        //滚动监听
                        setOnSelectListener { pickers ->
                            chooseList[2].text = pickers.categoryName
                        }
                    }
                }
            }
        }
        return super.setBeanSet(bean)
    }
    override fun build(): DialogBuilder {
        dataBean?.let {bean->
            bean.scrollerList1?.let { sList->
                val list = mutableListOf<GetConfigReq.DatasBean>()
                for(item in sList.iterator()){
                    val gdBean = GetConfigReq.DatasBean()
                    gdBean.categoryName = item.text
                    list.add(gdBean)
                }
                if (chooseList.size < 1&&list.size>0) {
                    val pickerData = PickerData()
                    pickerData.text = list[0].categoryName
                    chooseList.add(pickerData)
                }
                scroller1?.let {scr->
                    with(scr){
                        visibility = View.VISIBLE
                        setData(list)
                        setSelected(0)
                        //滚动监听
                        //滚动监听
                        setOnSelectListener { pickers ->
                            chooseList[0].text = pickers.categoryName
                        }
                    }
                }
            }
            bean.scrollerList2?.let { sList->
                val list = mutableListOf<GetConfigReq.DatasBean>()
                for(item in sList.iterator()){
                    val gdBean = GetConfigReq.DatasBean()
                    gdBean.categoryName = item.text
                    list.add(gdBean)
                }
                if (chooseList.size < 2&&list.size>1) {
                    val pickerData = PickerData()
                    pickerData.text = list[0].categoryName
                    chooseList.add(pickerData)
                }
                scroller2?.let {scr->
                    with(scr){
                        visibility = View.VISIBLE
                        setData(list)
                        setSelected(0)
                        //滚动监听
                        //滚动监听
                        setOnSelectListener { pickers ->
                            chooseList[1].text = pickers.categoryName
                        }
                    }
                }
            }
            bean.scrollerList3?.let { sList->
                val list = mutableListOf<GetConfigReq.DatasBean>()
                for(item in sList.iterator()){
                    val gdBean = GetConfigReq.DatasBean()
                    gdBean.categoryName = item.text
                    list.add(gdBean)
                }
                if (chooseList.size < 3&&list.size>2) {
                    val pickerData = PickerData()
                    pickerData.text = list[0].categoryName
                    chooseList.add(pickerData)
                }
                scroller3?.let {scr->
                    with(scr){
                        visibility = View.VISIBLE
                        setData(list)
                        setSelected(0)
                        //滚动监听
                        //滚动监听
                        setOnSelectListener { pickers ->
                            chooseList[2].text = pickers.categoryName
                        }
                    }
                }
            }

        }
        return super.build()
    }

    override fun setRight(text: String, listener: View.OnClickListener): DialogBuilder {
        dataBean?.let {bean->
            val listenerClick = View.OnClickListener{ view->
                bean.scrollerListener?.chooseItems(chooseList)
                listener.onClick(view)
            }
            return super.setRight(text,listenerClick)
        }?: return super.setRight(text, listener)
    }
}