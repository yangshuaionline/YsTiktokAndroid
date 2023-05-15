package com.yangshuai.module.dialog.picker.base

import android.content.Context
import android.view.View
import androidx.annotation.StyleRes
import com.yangshuai.module.dialog.base.BaseDialogBuilder


import android.widget.*
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
open class BasePickerDialogBuilder: BaseDialogBuilder{
    private var view:View? = null//本页面布局view
    private var linearLayout:LinearLayout? = null//嵌套子布局的layout
    private var layoutChildChild:View? = null//frameLayout承载的子view
    //title start
    private var pickerTitle:BasePickerDialogTitle? = null//头部按钮
    private var leftText:String? = null//左边的文案（取消）
    private var leftListener:View.OnClickListener? = null//左边按钮的监听（取消）
    private var rightText:String? = null//右边按钮的文案（确定）
    private var rightListener:View.OnClickListener? = null//右边按钮的监听（确定）
    private var titleText:String? = null//标题文案
    private var titleColor:Int? = null//标题颜色
    //title end
    //默认主题样式
    constructor(context: Context):this(context, DialogConst.style)
    //设置主题样式
    constructor(context: Context, @StyleRes themeResId:Int):super(context,themeResId){
        view = View.inflate(context, R.layout.dialog_picker_base,null)
        view?.let {
            linearLayout = it.findViewById(R.id.picker_dialog_linear_layout)
            linearLayout?.let {layout->
                pickerTitle = BasePickerDialogTitle(this,layout)
            }
        }
        super.setChild(view)
    }
    //添加中间自定义view
    override fun setChild(layoutChild:View?){
        this.layoutChildChild = layoutChild
        linearLayout?.addView(layoutChild)
    }
    //build()之后更新数据
    override fun setBeanSet(bean: BaseBeanSet): DialogBuilder {
        if(bean is PickerBeanSet){

        }
        return super.setBeanSet(bean)
    }
    override fun build(): DialogBuilder {
        //title
        pickerTitle?.let {title_view->
            with(title_view){
                leftText?.let { setLeft(it) }
                leftListener?.let { setLeft(it) }
                rightText?.let { setRight(it) }
                rightListener?.let { setRight(it) }
                titleText?.let { setTitle(it) }
                titleColor?.let { setTitleColor(it) }
            }
        }
        setTitleHind()
        setFull()
        return super.build()
    }

    override fun setLeft(text: String, listener: View.OnClickListener): DialogBuilder {
        this.leftText = text
        this.leftListener = listener
        return this
    }

    override fun setRight(text: String, listener: View.OnClickListener): DialogBuilder {
        this.rightText = text
        this.rightListener = listener
        return this
    }

    override fun setTitle(text: String): DialogBuilder {
        this.titleText = text
        return this
    }

    override fun setTitleColor(color: Int): DialogBuilder {
        this.titleColor = color
        return this
    }

    override fun setCenter(text: String, listener: View.OnClickListener): DialogBuilder {
        return this
    }

    override fun setCenterBg(drawable: Int): DialogBuilder {
        return this
    }
}