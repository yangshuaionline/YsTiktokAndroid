package com.yangshuai.module.dialog.ios

import android.content.Context
import android.view.View
import androidx.annotation.StyleRes
import com.yangshuai.module.dialog.base.BaseBean
import com.yangshuai.module.dialog.base.BaseDialogBuilder


import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yangshuai.library.base.view.recycleview.SimpleDividerItemDecoration
import com.yangshuai.module.dialog.*
import com.yangshuai.module.dialog.base.BaseBeanSet
import com.yangshuai.module.dialog.picker.PickerBeanSet


/**
 * @Author yangshuai
 * @Date : 2023-03-22 17:52
 * @Version 1.0
 * Description:
 * picker样式base层
 * 1.处理title层逻辑
 * 2.提供content层对接接口
 */
open class IOSCameraDialogBuilder: BaseDialogBuilder{
    private var view:View? = null//本页面布局view
    private var recyclerView:RecyclerView? = null//列表
    private var iosBottomTv:TextView? = null//底部按钮
    private var dataBean:IOSBean? = null//生成数据
    //bottom
    private var iosCenterText:String? = null
    private var iosCenterListener:View.OnClickListener? = null
    private var iosCenterTextColor:Int? = null
    private var iosCenterBgDrawable:Int? = null
    //默认主题样式
    constructor(context: Context):this(context, DialogConst.style)
    //设置主题样式
    constructor(context: Context, @StyleRes themeResId:Int):super(context,themeResId){
        view = View.inflate(context, R.layout.dialog_ios_camera,null)
        view?.let {
            recyclerView = it.findViewById(R.id.recycleView)
            iosBottomTv = it.findViewById(R.id.ios_center_tv)
            val manager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            recyclerView?.layoutManager = manager
        }
        super.setChild(view)
    }

    override fun setBean(bean: BaseBean): DialogBuilder {
        this.dataBean = bean as IOSBean
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
            recyclerView?.let {rv->
                with(rv){
                    adapter = bean.list?.let {
                        bean.callBack?.let { it1 ->
                            IOSCameraDialogAdapter(context, it,object : HeightCallBack {
                                override fun setHeight(height: Int) {
                                    if(adapter?.itemCount!!>7){
                                        recyclerView?.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height*5)
                                    }
                                }
                            }, it1)
                        }
                    }
                    if(bean.haveLine){
                        recyclerView?.addItemDecoration(SimpleDividerItemDecoration(context, SimpleDividerItemDecoration.VERTICAL_LIST, R.drawable.dialog_line_divider, 0))
                    }
                    recyclerView?.adapter = adapter
                }
            }
        }
        iosBottomTv?.let {bottom->
            with(bottom){
                iosCenterBgDrawable?.let { background = ContextCompat.getDrawable(context, it) }
                text = iosCenterText?:"取消"
                iosCenterTextColor?.let { setTextColor(ContextCompat.getColor(context,it)) }
                setOnClickListener(iosCenterListener)
            }
        }
        setLayoutBg(R.color.transparent)
        setTitleHind()
        setFull()
        return super.build()
    }

    override fun setLeft(text: String, listener: View.OnClickListener): DialogBuilder {
        return this
    }

    override fun setRight(text: String, listener: View.OnClickListener): DialogBuilder {
        return this
    }

    override fun setTitle(text: String): DialogBuilder {
        return this
    }

    override fun setTitleColor(color: Int): DialogBuilder {
        return this
    }

    override fun setCenter(text: String, listener: View.OnClickListener): DialogBuilder {
        this.iosCenterText = text
        this.iosCenterListener = listener
        return this
    }

    override fun setCenterBg(drawable: Int): DialogBuilder {
        this.iosCenterBgDrawable = drawable
        return this
    }

    override fun setCenterTextColor(color: Int): DialogBuilder {
        this.iosCenterTextColor = color
        return this
    }
}