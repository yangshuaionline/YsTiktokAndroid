package com.yangshuai.module.dialog.base

import android.app.Dialog
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
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
class BaseDialogTitle {
    private var dialog:Dialog? = null//dialog
    private var linearLayout: LinearLayout? = null//承载title得LinearLayout
    private var layout: ConstraintLayout? = null//title整体布局
    private var tv: TextView? = null//title布局文字
    private var bg: ImageView? = null//dialog title得背景图片
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
        val view = View.inflate(dialog?.context, R.layout.dialog_base_title,null)
        layout = view.findViewById(R.id.dialog_layout_title)
        tv = view.findViewById(R.id.dialog_tv_dialog_title)
        bg = view.findViewById(R.id.dialog_iv_bg_title)
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

    //设置背景图片
    fun setBgImage(img:Int){
        bg?.let {
            dialog?.context?.let { it1 -> Glide.with(it1).load(img).into(it) }
        }
    }
    //设置title背景图片
    fun setBgImage(img:String){
        bg?.let {
            dialog?.context?.let { it1 -> Glide.with(it1).load(img).into(it) }
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
}