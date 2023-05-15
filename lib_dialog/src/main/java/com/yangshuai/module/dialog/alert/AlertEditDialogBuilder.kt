package com.yangshuai.module.dialog.alert

import android.content.Context
import android.view.Gravity
import android.view.View
import androidx.annotation.StyleRes
import com.yangshuai.module.dialog.DialogBuilder
import com.yangshuai.module.dialog.DialogConst
import com.yangshuai.module.dialog.R
import com.yangshuai.module.dialog.base.BaseBean
import com.yangshuai.module.dialog.base.BaseDialogBuilder


import android.widget.*
import androidx.core.widget.addTextChangedListener
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
class AlertEditDialogBuilder: BaseDialogBuilder{
    private var editText:EditText? = null
    private var dataBean: AlertEditBean? = null//生成dialog的bean
    var dataList:MutableList<ListData> = mutableListOf()
    //默认主题样式
    constructor(context: Context):this(context, DialogConst.style)
    //设置主题样式
    constructor(context: Context, @StyleRes themeResId:Int):super(context,themeResId){
        val view = View.inflate(context, R.layout.dialog_alert_edit,null)
        editText = view.findViewById(R.id.alert_dialog_et_message)
        setChild(view)
    }

    override fun setBean(bean: BaseBean): DialogBuilder {
        dataBean = bean as AlertEditBean
        return super.setBean(bean)
    }
    override fun build(): DialogBuilder {
        dataBean?.let {bean->
            editText?.let {et->
                et.hint = bean.hint
                bean.message?.let { et.setText(it) }
                et.addTextChangedListener {
                    val data = ListData()
                    data.content = it?.toString()
                    if(dataList.isNotEmpty())dataList.clear()
                    dataList.add(data)
                }
            }
        }
        return super.build()
    }

    override fun setRight(text: String, listener: View.OnClickListener): DialogBuilder {
        super.setRight(text) { view ->
            editText?.let {
                if (it.text.trim().isNotEmpty()) {
                    dataBean?.listener?.click(dataList)
                    listener.onClick(view)
                } else {
                    //LayoutInflater的作用：对于一个没有被载入或者想要动态载入的界面，都需要LayoutInflater.inflate()来载入，LayoutInflater是用来找res/layout/下的xml布局文件，并且实例化
                    val inflater = layoutInflater //调用Activity的getLayoutInflater()
                    val view: View =
                        inflater.inflate(R.layout.dialog_toast_style, null) //加載layout下的布局
                    val title = view.findViewById<TextView>(R.id.tvTitleToast)
                    title.text = "${dataBean?.hint.toString()}"  //toast的标题
                    val toast = Toast(context)
                    toast.setGravity(
                        Gravity.CENTER,
                        0,
                        0
                    ) //setGravity用来设置Toast显示的位置，相当于xml中的android:gravity或android:layout_gravity
                    toast.duration =
                        Toast.LENGTH_SHORT //setDuration方法：设置持续时间，以毫秒为单位。该方法是设置补间动画时间长度的主要方法
                    toast.setView(view) //添加视图文件
                    toast.show()
                }
            }
        }
        return this
    }
}