package com.yangshuai.module.dialog.alert

import android.view.View
import com.yangshuai.module.dialog.base.BaseBean

/**
 * @Author yangshuai
 * @Date : 2023-03-23 17:02
 * @Version 1.0
 * Description:创建dialog调用的bean，根据bean生成相应的dialog
 */
open class AlertBean: BaseBean() {
    var messageGray:String? = null//灰色提示文字
    var messageBlack:String? = null//黑色提示文字
    var messageBlue:String? = null//蓝色提示文字
    var messageOrange:String?  = null//橙色提示文字
    var messageWarning:String? = null//警告提示文字
    var isChecked: Boolean = false//是否选中
    var checkBoxTitle:String? = null//单选提示文字
    var checkBoxLink:String? = null//单选提示链接文字
    var linkListener:View.OnClickListener? = null//链接跳转事件
    var imageClickListener:View.OnClickListener? = null//图片的点击事件
    fun setMessageGray(s:String):AlertBean{
        this.messageGray = s
        return this
    }
    fun setMessageBlack(s:String):AlertBean{
        this.messageBlack = s
        return this
    }
    fun setMessageBlue(s:String):AlertBean{
        this.messageBlue = s
        return this
    }
    fun setMessageOrange(s:String):AlertBean{
        this.messageOrange = s
        return this
    }
    fun setMessageWarning(s:String):AlertBean{
        this.messageWarning = s
        return this
    }
    fun isChecked(s:Boolean):AlertBean{
        this.isChecked = s
        return this
    }
    fun setCheckBoxTitle(s:String):AlertBean{
        this.checkBoxTitle = s
        return this
    }
    fun setCheckBoxLink(s:String):AlertBean{
        this.checkBoxLink = s
        return this
    }
    fun setLinkListener(listener:View.OnClickListener):AlertBean{
        this.linkListener = listener
        return this
    }
    fun setImageListener(listener:View.OnClickListener):AlertBean{
        this.imageClickListener = listener
        return this
    }
}