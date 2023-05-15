package com.yangshuai.module.dialog

import android.graphics.drawable.Drawable
import android.view.View
import com.yangshuai.module.dialog.base.BaseBean
import com.yangshuai.module.dialog.base.BaseBeanSet

/**
 * @Author yangshuai
 * @Date : 2023-03-22 17:35
 * @Version 1.0
 * Description:
 * dialog调用接口，提供全部调用方法
 */
interface DialogBuilder {
    /**
     * 公共方法
     * */
    fun show()//展示dialog
    fun dismiss()//关闭dialog
    fun setBean(bean: BaseBean):DialogBuilder//传递想生成的dialog对应的bean对象
    fun setFull():DialogBuilder//设置全屏
    fun showClose(): DialogBuilder //打开右上角关闭icon
    fun hindClose(): DialogBuilder //关闭右上角关闭icon
    fun setBeanSet(bean:BaseBeanSet):DialogBuilder//build之后设置属性
    fun setLayoutBg(res:Drawable):DialogBuilder//设置layout背景
    fun setLayoutBg(color:Int):DialogBuilder//设置layout背景
    fun setCanClickClose():DialogBuilder//点击背景关闭
    fun build(): DialogBuilder //生成dialog
    /**
     * title
     * */
    fun setCloseListener(listener:View.OnClickListener): DialogBuilder //设置右上角关闭icon事件，不设置默认dismiss
    fun setTitle(text:String): DialogBuilder //设置标题文字
    fun setTitleColor(color:Int): DialogBuilder //设置标题文字颜色
    fun setBgImage(img:Int): DialogBuilder //设置title背景图片
    fun setBgImage(img:String): DialogBuilder //设置title背景图片
    fun setTitleShow(): DialogBuilder //显示title（默认显示，隐藏之后才需要调用）
    fun setTitleHind(): DialogBuilder //隐藏title
    /**
     * bottom
     * */
    fun setLeft(text:String,listener: View.OnClickListener): DialogBuilder//设置left按钮得文字提示以及点击事件
    fun setLeftBg(drawable:Int):DialogBuilder//设置left按钮背景样式
    fun openLeftListener():DialogBuilder//打开left点击事件
    fun closeLeftListener():DialogBuilder//关闭left点击事件
    fun setRight(text:String,listener: View.OnClickListener):DialogBuilder//设置right按钮得文字提示以及点击事件
    fun setRightBg(drawable:Int):DialogBuilder//设置right按钮背景样式
    fun openRightListener():DialogBuilder//打开right点击事件
    fun closeRightListener():DialogBuilder//关闭right点击事件
    fun setCenter(text:String,listener: View.OnClickListener):DialogBuilder//设置center按钮得文字提示以及点击事件
    fun setCenterBg(drawable:Int):DialogBuilder//设置center按钮背景样式
    fun setCenterTextColor(color:Int):DialogBuilder//设置底部按钮文字颜色
    fun openCenterListener():DialogBuilder//打开center点击事件
    fun closeCenterListener():DialogBuilder//关闭center点击事件
    fun setLink(text:String,listener:View.OnClickListener):DialogBuilder//设置超链接文字提示以及点击事件
}