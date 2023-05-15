package com.yangshuai.module.dialog.manager

import java.lang.NullPointerException

/**
 * @Author yangshuai
 * @Date : 2023-03-23 09:47
 * @Version 1.0
 * Description:
 * dialog管理类
 * 如果context为空的时候，进行任何操作都会清空该条数据
 */
object DialogManager {
    private const val TAG = "DialogManager异常"
    val list:MutableList<ManagerBean> = mutableListOf()
    fun add(bean:ManagerBean){
        if(bean.context == null) throw NullPointerException("${TAG}，dialog存储时context为空")
        if(bean.dialog == null) throw NullPointerException("${TAG}，dialog存储时dialog为空")
        if(bean.bean == null) throw NullPointerException("${TAG}，dialog存储时bean为空")
        list.add(bean)
        checkUp()
    }
    fun remove(bean: ManagerBean){
        if(bean.context == null) throw NullPointerException("${TAG}，dialog移除时context为空")
        if(bean.dialog == null) throw NullPointerException("${TAG}，dialog移除时dialog为空")
        if(bean.bean == null) throw NullPointerException("${TAG}，dialog移除时bean为空")
        list.remove(bean)
        checkUp()
    }
    fun clear(){
        val newList = mutableListOf<ManagerBean>()
        newList.addAll(list)
        for(item in newList.iterator()){
            item.dialog?.dismiss()
            list.remove(item)
        }
        list.clear()
    }
    //检测，context为空的dialog删除
    private fun checkUp(){
        val newList = mutableListOf<ManagerBean>()
        newList.addAll(list)
        for(item in newList.iterator()){
            if(item.context == null){
                item.dialog?.dismiss()
                list.remove(item)
            }
        }
        newList.clear()
    }
}