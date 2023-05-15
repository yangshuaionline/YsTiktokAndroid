package com.yangshuai.module.dialog.picker.base

import android.app.Dialog
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.yangshuai.module.dialog.DialogConst
import com.yangshuai.module.dialog.R

/**
 * @Author yangshuai
 * @Date : 2023-03-23 10:03
 * @Version 1.0
 * Description:
 * 1.负责处理dialog弹窗得title
 * 2.接收参数直接处理业务逻辑
 */
class BasePickerDialogTitle {
    private var dialog:Dialog? = null//dialog
    private var linearLayout: LinearLayout? = null//承载title得LinearLayout
    private var layout: LinearLayout? = null//title整体布局
    private var tv: TextView? = null//title文字布局文字
    private var leftText: TextView? = null//左边按钮（取消）最多4个字符
    private var rightText: TextView? = null//右边按钮（确定）最多4个字符
    constructor(dialog: Dialog?,linearLayout: LinearLayout){
        dialog?.let {
            this.dialog = dialog
            this.linearLayout = linearLayout
            //初始化布局view
            initView()
            //初始化view属性
            initAttr()
        }
    }

    private fun initView(){
        val view = View.inflate(dialog?.context, R.layout.dialog_picker_title,null)
        layout = view.findViewById(R.id.dialog_layout_title)
        tv = view.findViewById(R.id.picker_dialog_tv_dialog_title)
        leftText = view.findViewById(R.id.picker_dialog_title_left)
        rightText = view.findViewById(R.id.picker_dialog_title_right)
        linearLayout?.addView(view)
    }

    private fun initAttr(){
        tv?.let {t->
            with(t){
                text = DialogConst.title
                dialog?.let {
                    setTextColor(ContextCompat.getColor(it.context,DialogConst.titleColor))
                }
            }
        }
    }

    //设置title文字
    fun setTitle(text:String){
        tv?.text = text
    }
    fun getTitle():String{
        return tv?.text.toString()
    }
    //设置title文字颜色
    fun setTitleColor(color:Int){
        dialog?.let {
            tv?.setTextColor(ContextCompat.getColor(it.context,color))
        }
    }
    //显示title（默认显示，隐藏之后才需要调用）
    fun setTitleShow(){
        layout?.visibility = View.VISIBLE
    }
    //隐藏title
    fun setTitleHind(){
        layout?.visibility = View.GONE
    }

    /**
     * 设置left按钮得文字提示
     * */
    fun setLeft(text:String){
        leftText?.text = text
        leftText?.let {
            it.visibility = View.VISIBLE
        }
    }
    /**
     * 设置left按钮得点击事件
     * */
    fun setLeft(listener: View.OnClickListener){
        leftText?.let {
            it.visibility = View.VISIBLE
            it.setOnClickListener(listener)
        }
    }
    /**
     * 设置left按钮背景样式
     * */
    fun setLeftBg(drawable:Int){
        leftText?.let {
            dialog?.let { d->
                it.background = ContextCompat.getDrawable(d.context,drawable)
            }
        }
    }
    /**
     * 设置right按钮得文字提示
     * */
    fun setRight(s:String){
        rightText?.text = s
    }
    /**
     * 设置right按钮得点击事件
     * */
    fun setRight(listener: View.OnClickListener){
        rightText?.let {
            it.setOnClickListener(listener)
        }
    }
    /**
     * 设置right按钮背景样式
     * */
    fun setRightBg(drawable:Int){
        rightText?.let {
            dialog?.let { d->
                it.background = ContextCompat.getDrawable(d.context,drawable)
            }
        }
    }
}