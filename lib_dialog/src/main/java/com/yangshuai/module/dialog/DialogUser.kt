package com.yangshuai.module.dialog

import android.app.Activity
import com.yangshuai.module.dialog.alert.AlertBean
import com.yangshuai.module.dialog.alert.AlertDialogBuilder
import com.yangshuai.module.dialog.alert.AlertEditBean
import com.yangshuai.module.dialog.alert.AlertEditDialogBuilder
import com.yangshuai.module.dialog.base.BaseBean
import com.yangshuai.module.dialog.image.ImageBean
import com.yangshuai.module.dialog.image.ImageDialogBuilder
import com.yangshuai.module.dialog.ios.IOSBean
import com.yangshuai.module.dialog.ios.IOSCameraDialogBuilder
import com.yangshuai.module.dialog.list.*
import com.yangshuai.module.dialog.picker.PickerBean
import com.yangshuai.module.dialog.picker.RecyclerPickerDialogBuilder
import com.yangshuai.module.dialog.picker.ScrollerPickerDialogBuilder

/**
 * @Author yangshuai
 * @Date : 2023-03-22 17:19
 * @Version 1.0
 * Description:
 * Dialog得唯一调用类，如果非拓展功能不要调用其他类
 */
class DialogUser {
    /**
     * 根据传递的BaseBean子类来生成对应的dialog样式
     * 具体参考每个子类bean的构造方法
     * @param bean AlertBean 内容区 黑色、蓝色、橙色、单选框、协议链接简单弹窗
     * @param bean AlertEditBean 中间一个180dp高度得edittext
     * @param bean ListBean 列表样式
     * 1. 传入data.hint，代表列表item为输入框
     * 2. 传入data.isCopy，代表为列表带复制按钮
     * 3. 否则为左边icon单选列表
     * */
    fun buildDialog(activity:Activity,bean: BaseBean):DialogBuilder{
        var builder:DialogBuilder? = null
        when(bean){
            is IOSBean->{
                val iosCameraBuilder = IOSCameraDialogBuilder(activity)
                iosCameraBuilder.setBean(bean).build()
                builder = iosCameraBuilder
            }
            is PickerBean->{
                if(bean.scrollerList1!=null){
                    val scrollerpickerBuilder = ScrollerPickerDialogBuilder(activity)
                    scrollerpickerBuilder.setBean(bean).build()
                    builder = scrollerpickerBuilder
                }else{
                    val pickerBuilder = RecyclerPickerDialogBuilder(activity)
                    pickerBuilder.setBean(bean).build()
                    builder = pickerBuilder
                }
            }
            is ImageBean->{
                val imageBuilder = ImageDialogBuilder(activity)
                imageBuilder.setBean(bean).build()
                builder = imageBuilder
            }
            is AlertEditBean->{
                val alertEditBuilder = AlertEditDialogBuilder(activity)
                alertEditBuilder.setBean(bean).build()
                builder = alertEditBuilder
            }
            is ListBean->{
                bean.list?.let {list->
                    if(list.isNotEmpty()){
                        val data = list[0]
                        builder = when {
                            data.hint!=null -> {
                                //input
                                val inputDialogBuilder = InputDialogBuilder(activity)
                                inputDialogBuilder.setBean(bean).build()
                                inputDialogBuilder
                            }
                            data.isCopy!=null||data.isContentRight!=null -> {
                                //copy
                                val copyDialogBuilder = CopyDialogBuilder(activity)
                                copyDialogBuilder.setBean(bean).build()
                                copyDialogBuilder
                            }

                            else -> {
                                if(bean.messageGray == null){
                                    val leftIconChooseDialogBuilder = LeftIconChooseDialogBuilder(activity)
                                    leftIconChooseDialogBuilder.setBean(bean).build()
                                    leftIconChooseDialogBuilder
                                }else{
                                    val rightIconChooseDialogBuilder = RightIconChooseDialogBuilder(activity)
                                    rightIconChooseDialogBuilder.setBean(bean).build()
                                    rightIconChooseDialogBuilder
                                }

                            }
                        }
                    }
                }
            }
            //所有样式的父类，优先级最低
            is AlertBean->{
                val alertBuilder = AlertDialogBuilder(activity)
                alertBuilder.setBean(bean).build()
                builder = alertBuilder
            }
            else->{

            }
        }
        if(builder == null){
            val alertBuilder = AlertDialogBuilder(activity)
            val returnBean = AlertBean().setMessageBlack("生成dialog参数传递错误，请检查代码！")
            alertBuilder.setBean(returnBean).build()
            builder = alertBuilder
        }
        return builder as DialogBuilder
    }
}