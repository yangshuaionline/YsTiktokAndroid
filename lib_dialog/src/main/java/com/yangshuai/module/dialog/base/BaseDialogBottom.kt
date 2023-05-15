package com.yangshuai.module.dialog.base

import android.app.Dialog
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.yangshuai.module.dialog.DialogConst
import com.yangshuai.module.dialog.R

/**
 * @Author yangshuai
 * @Date : 2023-03-23 10:53
 * @Version 1.0
 * Description:
 * 1.dialog底部样式整理
 * 2.负责根据参数处理具体逻辑实现
 */
class BaseDialogBottom {
    private var dialog:Dialog? = null//dialog
    private var linearLayout: LinearLayout? = null//承载title得LinearLayout
    private var layout:LinearLayout? = null//bottom 最外层布局
    private var layoutTwo:LinearLayout? = null//两个按钮的布局
    private var leftBt:Button? = null//左边按钮
    private var leftListener:View.OnClickListener? = null//左边按钮点击事件
    private var rightBt:Button? = null//右边按钮
    private var rightListener:View.OnClickListener? = null//右边按钮点击事件
    private var layoutOne:LinearLayout? = null//两个按钮的布局
    private var centerBt:Button? = null//中间按钮
    private var centerListener:View.OnClickListener? = null//中间按钮点击事件
    private var tvLink:TextView? = null//dialog最下边的链接按钮
    private var linkListener:View.OnClickListener? = null//dialog最下边的超链接点击事件
    constructor(dialog: Dialog?, linearLayout: LinearLayout){
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
        val view = View.inflate(dialog?.context, R.layout.dialog_base_bottom,null)
        layout = view.findViewById(R.id.dialog_layout_bottom)
        layoutTwo = view.findViewById(R.id.dialog_layout_two)
        leftBt = view.findViewById(R.id.dialog_btn_left)
        rightBt = view.findViewById(R.id.dialog_btn_right)
        layoutOne = view.findViewById(R.id.dialog_layout_one)
        centerBt = view.findViewById(R.id.dialog_btn_center)
        tvLink = view.findViewById(R.id.dialog_tv_link)
        linearLayout?.addView(view)
    }

    private fun initAttr(){
        tvLink?.let {
            with(it){
                setTextColor(ContextCompat.getColor(context,DialogConst.linkTextColor))
            }
        }
        leftBt?.let {
            with(it){
                text = DialogConst.leftBtText
                dialog?.let { d->
                    background = ContextCompat.getDrawable(d.context,DialogConst.leftBtBg)
                    setOnClickListener(null)
                }
            }
        }
        rightBt?.let {
            with(it){
                text = DialogConst.rightBtText
                dialog?.let { d->
                    background = ContextCompat.getDrawable(d.context,DialogConst.rightBtBg)
                    setOnClickListener(null)
                }
            }
        }
        centerBt?.let {
            with(it){
                text = DialogConst.centerBtText
                dialog?.let { d->
                    background = ContextCompat.getDrawable(d.context,DialogConst.centerBtBg)
                    setOnClickListener(null)
                }
            }
        }

    }
    /**
     * 设置left按钮得文字提示
     * */
    fun setLeft(text:String){
        leftBt?.text = text
        leftBt?.let {
            it.visibility = View.VISIBLE
        }
        layoutTwo?.visibility = View.VISIBLE
    }
    /**
     * 设置left按钮得点击事件
     * */
    fun setLeft(listener: View.OnClickListener){
        this.leftListener = listener
        leftBt?.let {
            it.visibility = View.VISIBLE
            it.setOnClickListener(listener)
        }
        layoutTwo?.visibility = View.VISIBLE
    }
    /**
     * 设置left按钮背景样式
     * */
    fun setLeftBg(drawable:Int){
        leftBt?.let {
            dialog?.let { d->
                it.background = ContextCompat.getDrawable(d.context,drawable)
            }
        }
    }
    /**
     * 设置left打开关闭点击事件
     * */
    fun openLeftListener(isOpen:Boolean){
        when(isOpen){
            true->{
                leftListener?.let {
                    leftBt?.setOnClickListener(it)
                }
            }
            else->{
                leftBt?.setOnClickListener(null)
            }
        }
    }
    /**
     * 设置right按钮得文字提示
     * */
    fun setRight(s:String){
        rightBt?.text = s
        layoutTwo?.visibility = View.VISIBLE
    }
    /**
     * 设置right按钮得点击事件
     * */
    fun setRight(listener: View.OnClickListener){
        this.rightListener = listener
        rightBt?.let {
            it.setOnClickListener(listener)
        }
        layoutTwo?.visibility = View.VISIBLE
    }
    /**
     * 设置right按钮背景样式
     * */
    fun setRightBg(drawable:Int){
        rightBt?.let {
            dialog?.let { d->
                it.background = ContextCompat.getDrawable(d.context,drawable)
            }
        }
    }
    /**
     * 设置right打开关闭点击事件
     * */
    fun openRightListener(isOpen:Boolean){
        when(isOpen){
            true->{
                rightListener?.let {
                    rightBt?.setOnClickListener(it)
                }?:rightBt?.setOnClickListener { dialog?.dismiss() }
            }
            else->{
                rightBt?.setOnClickListener(null)
            }
        }
    }
    /**
     * 设置center按钮得文字提示以及点击事件
     * */
    fun setCenter(text:String){
        centerBt?.text = text
        layoutOne?.visibility = View.VISIBLE
    }
    /**
     * 设置center按钮得文字提示以及点击事件
     * */
    fun setCenter(listener: View.OnClickListener){
        this.centerListener = listener
        centerBt?.let {
            it.setOnClickListener(listener)
        }
        layoutOne?.visibility = View.VISIBLE
    }
    /**
     * 设置center按钮背景样式
     * */
    fun setCenterBg(drawable:Int){
        centerBt?.let {
            dialog?.let { d->
                it.background = ContextCompat.getDrawable(d.context,drawable)
            }
        }
    }

    /**
     * 设置center的文字颜色
     * */
    fun setCenterTextColor(color:Int){
        centerBt?.let {
            dialog?.let {d->
                it.setTextColor(ContextCompat.getColor(d.context,color))
            }
        }
    }
    /**
     * 设置left打开关闭点击事件
     * */
    fun openCenterListener(isOpen:Boolean){
        when(isOpen){
            true->{
                centerListener?.let {
                    centerBt?.setOnClickListener(it)
                }?:centerBt?.setOnClickListener { dialog?.dismiss() }
            }
            else->{
                centerBt?.setOnClickListener(null)
            }
        }
    }
    /**
     * 设置超链接文字提示
     * */
    fun setLink(s:String){
        tvLink?.let {
            with(it){
                visibility = View.VISIBLE
                text = s
            }
        }
    }
    /**
     * 设置超链接文字提示以及点击事件
     * 设置属性成功默认展示文本
     * */
    fun setLink(listener:View.OnClickListener){
        this.linkListener = linkListener
        tvLink?.let {
            with(it){
                visibility = View.VISIBLE
                setOnClickListener(listener)
            }
        }
    }

    fun setBottom(){
        leftBt?.background = dialog?.context?.let { ContextCompat.getDrawable(it,R.drawable.dialog_white_blue_radius_3) }
        dialog?.context?.resources?.getColor(R.color.color_blue_dark)?.let { leftBt?.setTextColor(it) }
    }

}