package com.yangshuai.module.dialog.image

import android.content.Context
import android.view.View
import androidx.annotation.StyleRes
import com.yangshuai.module.dialog.DialogBuilder
import com.yangshuai.module.dialog.DialogConst
import com.yangshuai.module.dialog.R
import com.yangshuai.module.dialog.base.BaseBean
import com.yangshuai.module.dialog.base.BaseDialogBuilder


import android.widget.*
import com.bumptech.glide.Glide
import com.yangshuai.module.dialog.list.ListData


/**
 * @Author yangshuai
 * @Date : 2023-03-22 17:52
 * @Version 1.0
 * Description:
 * 处理简单弹窗,只处理中间内容部分，顶部title和底部按钮再base中处理
 * 包含：
 * 1.普通得文字弹窗
 * 2.黄色警告弹窗
 * 3.带单选框带链接得弹窗
 */
class ImageDialogBuilder: BaseDialogBuilder{
    private var imageView:ImageView? = null
    private var dataBean: ImageBean? = null//生成dialog的bean
    var dataList:MutableList<ListData> = mutableListOf()
    //默认主题样式
    constructor(context: Context):this(context, DialogConst.style)
    //设置主题样式
    constructor(context: Context, @StyleRes themeResId:Int):super(context,themeResId){
        val view = View.inflate(context, R.layout.dialog_image,null)
        imageView = view.findViewById(R.id.dialog_iv_image_full)
        setChild(view)
    }

    override fun setBean(bean: BaseBean): DialogBuilder {
        dataBean = bean as ImageBean
        return super.setBean(bean)
    }
    override fun build(): DialogBuilder {
        dataBean?.let {bean->
            imageView?.let {
                Glide.with(context).load(bean.url).into(it)
            }
        }
        return super.build()
    }
}