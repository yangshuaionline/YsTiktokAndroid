package com.yangshuai.module.dialog

/**
 * @Author yangshuai
 * @Date : 2023-03-23 10:26
 * @Version 1.0
 * Description:统一样式设置，需要设置哪个直接调用哪个，属性中间可以特殊设置
 */
object DialogConst {
    /**
     * 全局属性
     * */
    var isDebug:Boolean = true//是否为debug代码（true的时候打印台打印Log日志）
    var style:Int = R.style.BaseDialog//dialog的主题
    /**
     * title
     * */
    //右上角关闭按钮
    var closeIcon:Int = R.mipmap.icon_close
    //标题文案
    var title:String = "提示"
    //title 的默认文字的颜色
    var titleColor:Int = R.color.black
    /**
     * bottom
     * */
    //链接textcolor
    var linkTextColor = R.color.text_blue_dark
    //左边按钮得默认文案
    var leftBtText = "取消"
    //左边按钮背景drawable
    var leftBtBg = R.drawable.base_white_gray_radius_3
    //右边按钮得默认文案
    var rightBtText = "确认"
    //右边按钮背景drawable
    var rightBtBg = R.drawable.base_blue_blue_radius_3
    //下边一个按钮的时候drawable
    var centerBtText = "确认"
    //下边一个按钮的时候drawable
    var centerBtBg = R.drawable.base_blue_blue_radius_3

//
}