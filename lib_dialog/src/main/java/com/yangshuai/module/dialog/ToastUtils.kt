package com.yangshuai.module.dialog

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

/**
 * @Author yangshuai
 * @Date : 2023-03-28 10:32
 * @Version 1.0
 * Description:
 */
object ToastUtils {

    //只有一个文字的Toast
    fun showText(context:Context,message:String){
        //LayoutInflater的作用：对于一个没有被载入或者想要动态载入的界面，都需要LayoutInflater.inflate()来载入，LayoutInflater是用来找res/layout/下的xml布局文件，并且实例化
        val view: View = LayoutInflater.from(context).inflate(R.layout.dialog_toast_style, null) //加載layout下的布局
        val title = view.findViewById<TextView>(R.id.tvTitleToast)
        with(title){
            visibility = android.view.View.VISIBLE
            text = if (message.startsWith("<") && message.endsWith(">")) {
                android.text.Html.fromHtml(message)
            } else {
                message
            }
            post {
                android.view.Gravity.CENTER
//                gravity = if (lineCount == 1) {
//                    android.view.Gravity.CENTER
//                } else {
//                    android.view.Gravity.LEFT
//                }
            }
        }
        val toast = Toast(context)
        toast.setGravity(Gravity.CENTER, 0, 0) //setGravity用来设置Toast显示的位置，相当于xml中的android:gravity或android:layout_gravity
        toast.duration = Toast.LENGTH_SHORT //setDuration方法：设置持续时间，以毫秒为单位。该方法是设置补间动画时间长度的主要方法
        toast.setView(view) //添加视图文件
        toast.show()
    }
    //只有一个图标，一个文字的Toast
    fun showIconText(context:Context,message:String){
        //LayoutInflater的作用：对于一个没有被载入或者想要动态载入的界面，都需要LayoutInflater.inflate()来载入，LayoutInflater是用来找res/layout/下的xml布局文件，并且实例化
        val view: View = LayoutInflater.from(context).inflate(R.layout.dialog_toast_style, null) //加載layout下的布局
        val title = view.findViewById<TextView>(R.id.tvTitleToast)
        with(title){
            visibility = android.view.View.VISIBLE
            text = if (message.startsWith("<") && message.endsWith(">")) {
                android.text.Html.fromHtml(message)
            } else {
                message
            }
            post {
                gravity = if (lineCount == 1) {
                    android.view.Gravity.CENTER
                } else {
                    android.view.Gravity.LEFT
                }
            }
        }
        val ivTitle = view.findViewById<ImageView>(R.id.ivTitleToast)
        ivTitle.visibility = View.VISIBLE
        val toast = Toast(context)
        toast.setGravity(Gravity.CENTER, 0, 0) //setGravity用来设置Toast显示的位置，相当于xml中的android:gravity或android:layout_gravity
        toast.duration = Toast.LENGTH_SHORT //setDuration方法：设置持续时间，以毫秒为单位。该方法是设置补间动画时间长度的主要方法
        toast.setView(view) //添加视图文件
        toast.show()
    }


}