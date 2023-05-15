package com.yangshuai.module.dialog.picker

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.annotation.StyleRes
import com.yangshuai.module.dialog.base.BaseBean


import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexboxLayout
import com.yangshuai.module.dialog.*
import com.yangshuai.module.dialog.base.BaseBeanSet
import com.yangshuai.module.dialog.picker.base.BasePickerDialogBuilder


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
open class RecyclerPickerDialogBuilder: BasePickerDialogBuilder{
    private var view:View? = null//本页面布局view
    private var recyclerView:RecyclerView? = null//recyclerview
    private var flowLayout: FlexboxLayout? = null//流式布局
    private var dataBean:PickerBean? = null//生成dialog数据bean
    private var adapter:RecylcerPickerDialogAdapter? = null
    //默认主题样式
    constructor(context: Context):this(context, DialogConst.style)
    //设置主题样式
    constructor(context: Context, @StyleRes themeResId:Int):super(context,themeResId){
        view = View.inflate(context, R.layout.dialog_picker_recycler,null)
        view?.let {
            recyclerView = it.findViewById(R.id.picker_dialog_recycler_rv)
            flowLayout = it.findViewById(R.id.picker_dialog_recycler_flow)
        }
        super.setChild(view)
    }
    override fun setBean(bean: BaseBean): DialogBuilder {
        dataBean = bean as PickerBean
        return super.setBean(bean)
    }
    //build()之后更新数据
    override fun setBeanSet(bean: BaseBeanSet): DialogBuilder {
        if(bean is PickerBeanSet){

        }
        return super.setBeanSet(bean)
    }
    override fun build(): DialogBuilder {
        dataBean?.let {bean->
            when(bean.rvType){
                PickerEnum.GRID_VIEW->{
                    recyclerView?.let {
                        it.visibility = View.VISIBLE
                        if(bean.row > 0){
                            it.layoutManager = GridLayoutManager(context,bean.row, GridLayoutManager.VERTICAL,false)
                        }
                        adapter = RecylcerPickerDialogAdapter(context,bean,object :HeightCallBack{
                            override fun setHeight(height: Int) {

                            }
                        })
                        it.adapter = adapter
                    }
                    flowLayout?.let {
                        it.visibility = View.GONE
                    }
                }
                PickerEnum.LIST_VIEW->{
                    recyclerView?.let {rv->
                        rv.visibility = View.VISIBLE
                        rv.layoutManager = GridLayoutManager(context,1, GridLayoutManager.VERTICAL,false)
                        adapter = RecylcerPickerDialogAdapter(context,bean,object :HeightCallBack{
                            override fun setHeight(height: Int) {

                            }
                        })
                        rv.adapter = adapter
                    }
                    flowLayout?.let {
                        it.visibility = View.GONE
                    }
                }
                PickerEnum.FLOW->{
                    recyclerView?.let {
                        it.visibility = View.GONE
                    }
                    flowLayout?.let {flow->
                        flow.visibility = View.VISIBLE
                        flow.removeAllViews()
                        bean.rvList?.let {list->
                            for(item in list.iterator()){
                                val tagView = layoutInflater.inflate(R.layout.dialog_picker_recycler_item_grid,null,false)
                                val content = tagView.findViewById<TextView>(R.id.content)
                                content.text = item.text
                                if(item.isChoose == null) item.isChoose = false
                                content.isSelected = item.isChoose == true
                                if(bean.canClick){
                                    content.setOnClickListener {
                                        item.isChoose?.let {
                                            bean.maxChooseCount?.let {max->
                                                if(!it){
                                                    var count = 0
                                                    for(itemList in list.iterator()){
                                                        if(itemList.isChoose == true){
                                                            count++
                                                        }
                                                    }
                                                    if(count>=max){
                                                        bean.maxChooseListener?.show()
                                                        return@setOnClickListener
                                                    }
                                                }
                                            }
                                            item.isChoose = !it
                                            content.isSelected = item.isChoose == true
                                        }
                                    }
                                }
                                flow.addView(tagView)
                            }
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
                val returnList = mutableListOf<PickerData>()
                val list = bean.rvList?: mutableListOf()
                for(item in list.iterator()){
                    if(item.isChoose == true) returnList.add(item)
                }
                bean.chooseSubListener?.chooseItems(returnList)
                listener.onClick(view)
            }
            return super.setRight(text,listenerClick)
        }?: return super.setRight(text, listener)
    }
}