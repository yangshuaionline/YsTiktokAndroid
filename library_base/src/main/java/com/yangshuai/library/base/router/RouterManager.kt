package com.yangshuai.library.base.router

import android.app.Activity
import com.alibaba.android.arouter.launcher.ARouter
import com.yangshuai.library.base.utils.KLog
import java.util.Objects
import java.util.Stack

/**
 * @Author yangshuai
 * @Date 2023-05-09 周二 15:17
 * @Description
 * 路由管理器
 * 使用方法
 * @Route(path = RouterPath.IM.IM_MESSAGE)
 * class ImMessageListActivity  : BaseToolBarActivity<ImActMessageBinding, ImGroupViewModel>() {
 *      ...
 * }
 * override fun initParam() {
        ARouter.getInstance().inject(this)
        RouterManager.addPage(RouterPath.IM.IM_MESSAGE,this)
    }
    override fun onDestroy() {
        RouterManager.remove(this)
        super.onDestroy()
    }
 */
object RouterManager {
    //activity栈
    private val stack:Stack<RouterBean> = Stack()
    //白名单，不进行限制，可以不断添加
    private val whiteList = arrayListOf<String>(
        RouterPath.IM.IM,
        RouterPath.IM.IM,
        RouterPath.IM.IM,
        RouterPath.IM.IM,
    )
    /**
     * 跳转页面
     * */
    fun goPage(url:String){
        goPage(url,null)
    }
    fun goPage(url:String,map:Map<String,Any>?){
        removeEmpty()
        //判断是否可以跳转
        when(isHave(url)){
            RouterType.REPEAT,RouterType.CAN->{
                val postcard =  ARouter.getInstance().build(url)
                with(postcard){
                    map?.forEach { (k, v) ->
                        when(v){
                            is String-> withString(k,v)
                            is Boolean-> withBoolean(k,v)
                            is Int-> withInt(k,v)
                            is Objects -> withObject(k,v)
                        }
                    }
                }
                postcard.navigation()
            }
            RouterType.CAN_NOT->{
                var isStart = false
                for(i in stack.indices){
                    if(isStart){
                        stack[i].activity?.let { remove(it) }
                    }else{
                        if(stack[i].url == url)isStart = true
                    }
                }
                KLog.w("栈中已经存在$url，跳转$url,清除后续activity")
            }
        }
    }
    /**
     * 添加页面
     * */
    fun addPage(url:String,activity: Activity){
        removeEmpty()
        when(isHave(url)){
            RouterType.REPEAT ->{
                //可以重复
                stack.reversed().forEachIndexed { index, routerBean ->
                    if(routerBean.url == url){
                        val bean = RouterBean()
                        bean.url = url
                        bean.activity = activity
                        bean.pageType = routerBean.pageType++
                        stack.add(bean)
                        return@forEachIndexed
                    }
                }
            }
            RouterType.CAN->{
                //可以添加
                val bean = RouterBean()
                bean.url = url
                bean.activity = activity
                stack.add(bean)
            }
            RouterType.CAN_NOT->{
                //不可以添加
                KLog.w("栈中已经存在$url，不可以继续添加")
            }
        }
    }
    /**
     * 删除
     * */
    fun remove(activity: Activity){
        removeEmpty()
        var isRemove = false//后续是否删除
        try {
            val iterator = stack.iterator()
            while (iterator.hasNext()){
                if(isRemove){
                    iterator.next().activity?.finish()
                    iterator.remove()
                }else{
                    if(activity == iterator.next().activity){
                        iterator.next().activity?.finish()
                        iterator.remove()
                        isRemove = true//首次匹配到，栈后边的都删除掉
                    }
                }
            }
        }catch (e:java.util.NoSuchElementException){
            KLog.w(e.message.toString())
        }

    }
    /**
     * 获取白名单的大小
     * */
    fun getWhiteSize():Int{
        removeEmpty()
        return whiteList.size
    }
    /**
     * 获取栈的大小
     * */
    fun getStackSize():Int{
        removeEmpty()
        return stack.size
    }
    /**
     * 判断是否可以继续执行
     * 1.当activity在白名单的时候，可以继续执行，pageType++
     * 2.当activity不在白名单的时候，栈内已经存在不可执行
     * 3.当activity不在白名单的时候，栈内不存在可以执行
     * */
    private fun isHave(url:String):RouterType{
        //判断是否在白名单
        for(i in whiteList.indices){
            if(whiteList[i] == url) {
                //在白名单
                return if(isInStack(url)){
                    //栈中存在  pageType++
                    RouterType.REPEAT
                }else{
                    //栈中不存在 直接添加
                    RouterType.CAN
                }
            }
        }
        return if(isInStack(url)){
            //栈内已经存在
            RouterType.CAN_NOT
        }else{
            //栈内不存在 直接添加
            RouterType.CAN
        }
    }
    //判断是否在栈中
    private fun isInStack(url:String):Boolean{
        for(i in stack.indices){
            if(url == stack[i].url) return true
        }
        return false
    }
    /**
     * 删除
     * */
    private fun removeEmpty(){
        val iterator = stack.iterator()
        while (iterator.hasNext()){
            if(iterator.next().activity == null){
                iterator.remove()
            }
        }
    }
}