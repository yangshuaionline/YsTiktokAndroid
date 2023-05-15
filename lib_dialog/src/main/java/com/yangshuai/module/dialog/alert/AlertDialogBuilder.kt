package com.yangshuai.module.dialog.alert

import android.content.Context
import android.graphics.Color
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StyleRes
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.widget.NestedScrollView
import com.yangshuai.module.dialog.base.BaseBean
import com.yangshuai.module.dialog.base.BaseDialogBuilder


import android.widget.*
import com.bumptech.glide.Glide
import com.yangshuai.module.dialog.*
import com.yangshuai.module.dialog.base.BaseBeanSet


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
open class AlertDialogBuilder: BaseDialogBuilder{
    private var view:View? = null//本页面布局view
    private var scrollView: NestedScrollView? = null//黑色message外部嵌套的scrollview
    private var messageGray:TextView? = null//灰色提示文字
    private var messageBlack:TextView? = null//黑色提示文字
    private var frameLayout:FrameLayout? = null//嵌套子布局的layout
    private var layoutChildChild:View? = null//frameLayout承载的子view
    private var messageBlue:TextView? = null//蓝色提示文字
    private var messageWarning:TextView? = null//警告提示文字
    private var messageOrange:TextView?  = null//橙色提示文字
    private var layoutBt: LinearLayout? = null//下边单选框
    private var checkBox:CheckBox? = null//单选
    private var checkBoxLink:TextView? = null//单选提示文字提示链接（带点击事件）
    private var dataBean: AlertBean? = null//生成dialog的bean
    private var ivAdd:ImageView? = null//添加图片image
    //默认主题样式
    constructor(context: Context):this(context, DialogConst.style)
    //设置主题样式
    constructor(context: Context, @StyleRes themeResId:Int):super(context,themeResId){
        view = View.inflate(context, R.layout.dialog_alert,null)
        view?.let {
            scrollView = it.findViewById(R.id.alert_dialog_scroll_view)
            messageGray = it.findViewById(R.id.alert_dialog_tv_message_gray)
            messageBlack = it.findViewById(R.id.alert_dialog_tv_message_black)
            frameLayout = it.findViewById(R.id.alert_dialog_frame_layout)
            messageBlue = it.findViewById(R.id.alert_dialog_tv_message_blue)
            messageWarning = it.findViewById(R.id.alert_dialog_tv_message_warning)
            messageOrange = it.findViewById(R.id.alert_dialog_tv_message_orange)
            layoutBt = it.findViewById(R.id.alert_dialog_layout_bt)
            checkBox = it.findViewById(R.id.alert_dialog_cb_bt)
            checkBoxLink = it.findViewById(R.id.alert_dialog_tv_link)
            ivAdd = it.findViewById(R.id.dialog_iv_add_image)
        }
        ivAdd?.visibility = View.GONE
        messageGray?.visibility = View.GONE
        messageBlack?.visibility = View.GONE
        messageWarning?.visibility = View.GONE
        messageBlue?.visibility = View.GONE
        messageOrange?.visibility = View.GONE
        layoutBt?.visibility = View.GONE
        super.setChild(view)
    }
    //添加中间自定义view
    override fun setChild(layoutChild:View?){
        this.layoutChildChild = layoutChild
        frameLayout?.addView(layoutChild)
    }
    override fun setBean(bean: BaseBean): DialogBuilder {
        dataBean = bean as AlertBean
        return super.setBean(bean)
    }

    override fun setBeanSet(bean: BaseBeanSet): DialogBuilder {
        if(bean is AlertBeanSet){
            val url = bean.imageUrl
            url?.let {
                ivAdd?.let {iv->
                    Glide.with(context).load(it).into(iv)
                }
            }
        }
        return super.setBeanSet(bean)
    }
    override fun build(): DialogBuilder {
        dataBean?.let {bean->
            if(bean.imageClickListener != null){
                ivAdd?.let {iv->
                    iv.visibility = View.VISIBLE
                    iv.setOnClickListener(bean.imageClickListener)
                }
            }
            bean.messageGray?.let {gray->
                messageGray?.let {message_gray->
                    with(message_gray){
                        visibility = View.VISIBLE
                        text = if (gray.startsWith("<") && gray.endsWith(">")) {
                            Html.fromHtml(gray)
                        } else {
                            gray
                        }
                        post {
                            gravity = if (lineCount == 1) {
                                if(isFull)
                                    Gravity.LEFT
                                else
                                    Gravity.CENTER
                            } else {
                                Gravity.LEFT
                            }
                        }
                    }
                }
            }
            bean.messageBlack?.let { it->
                messageBlack?.let { message_black->
                    with(message_black){
                        visibility = View.VISIBLE
                        text = if (it.startsWith("<") && it.endsWith(">")) {
                            Html.fromHtml(it)
                        } else {
                            it
                        }
                        post {
                            gravity = if (lineCount == 1) {
                                Gravity.CENTER
                            } else {
                                Gravity.LEFT
                            }
                            //设置最大高度
                            if(lineCount>15){
                                scrollView?.layoutParams = LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,message_black.lineHeight*15)
                            }
                        }
                    }
                }
            }
            bean.messageBlue?.let {it->
                messageBlue?.let {message->
                    with(message){
                        visibility = View.VISIBLE
                        text = if (it.startsWith("<") && it.endsWith(">")) {
                            Html.fromHtml(it)
                        } else {
                            it
                        }
                        post {
                            gravity = Gravity.LEFT
                        }
                    }
                }
            }
            bean.messageWarning?.let {warning->
                messageWarning?.let { message->
                    with(message){
                        visibility = View.VISIBLE
                        text = if (warning.startsWith("<") && warning.endsWith(">")) {
                            Html.fromHtml(warning)
                        } else {
                            warning
                        }
                        post {
                            gravity = Gravity.LEFT
                        }
                    }
                }

            }
            bean.messageOrange?.let { it->
                messageOrange?.let { message->
                    with(message){
                        visibility = View.VISIBLE
                        text = if (it.startsWith("<") && it.endsWith(">")) {
                            Html.fromHtml(it)
                        } else {
                            it
                        }
                        post {
                            gravity = Gravity.LEFT
                        }
                    }
                }
            }
            bean.checkBoxLink?.let {link->
                layoutBt?.let {layout->
                    layout.visibility = View.VISIBLE
                    bean.checkBoxTitle?.let { it ->
                        checkBoxLink?.highlightColor = Color.TRANSPARENT
                        val textShow = "${it}${link}"
                        checkBoxLink?.text = textShow
                        checkBoxLink?.text = TextUtils.matcherSearchText(textShow, link){view->
                            bean.linkListener?.onClick(view)
                        }
                        checkBoxLink?.setMovementMethod(LinkMovementMethod.getInstance())
                    }
                    checkBox?.isChecked = bean.isChecked
                }
            }
        }
        return super.build()
    }

    override fun setRight(text: String, listener: View.OnClickListener): DialogBuilder {
        return if(dataBean?.linkListener == null){
            super.setRight(text, listener)
        }else{
            super.setRight(text){view->
                checkBox?.let {
                    if(it.isChecked){
                        listener.onClick(view)
                    }else{
                        ToastUtils.showIconText(context,"请先${checkBoxLink?.text.toString()}后再进行操作")
                    }
                }
            }
        }
    }
}