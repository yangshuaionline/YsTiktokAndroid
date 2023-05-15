package com.yangshuai.module.dialog.list

import android.content.Context
import android.view.View
import androidx.annotation.StyleRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yangshuai.library.base.view.recycleview.SimpleDividerItemDecoration
import com.yangshuai.module.dialog.DialogBuilder
import com.yangshuai.module.dialog.DialogConst
import com.yangshuai.module.dialog.R
import com.yangshuai.module.dialog.alert.AlertDialogBuilder
import com.yangshuai.module.dialog.base.BaseBean

/**
 * @Author yangshuai
 * @Date : 2023-03-27 13:51
 * @Version 1.0
 * Description:
 */
class InputDialogBuilder : AlertDialogBuilder{
    var recyclerView:RecyclerView? = null
    var adapter:InputDialogAdapter? = null
    var bean:ListBean? = null
    var data:MutableList<ListData> = mutableListOf()
    //默认主题样式
    constructor(context: Context):this(context, DialogConst.style)
    //设置主题样式
    constructor(context: Context, @StyleRes themeResId:Int):super(context,themeResId){
        val view = View.inflate(context, R.layout.dialog_list_input,null)
        recyclerView = view.findViewById(R.id.recycleView)
        val manager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        recyclerView?.layoutManager = manager
        setChild(view)
    }

    override fun setBean(bean: BaseBean): DialogBuilder {
        this.bean = bean as ListBean
        return super.setBean(bean)
    }
    override fun build(): DialogBuilder {
        adapter = bean?.list?.let { InputDialogAdapter(context, it) }
        if(bean?.haveLine == true){
            recyclerView?.addItemDecoration(SimpleDividerItemDecoration(context, SimpleDividerItemDecoration.VERTICAL_LIST, R.drawable.base_line_divider, 0))
        }
        recyclerView?.adapter = adapter
        return super.build()
    }

    override fun setRight(text: String, listener: View.OnClickListener): DialogBuilder {
        return super.setRight(text){
            clickOK()
            listener.onClick(it)
        }
    }

    override fun setCenter(text: String, listener: View.OnClickListener): DialogBuilder {
        return super.setCenter(text){
            clickOK()
            listener.onClick(it)
        }
    }

    private fun clickOK(){
        adapter?.getDataList()?.let { it1 -> bean?.listener?.click(it1) }
    }
}