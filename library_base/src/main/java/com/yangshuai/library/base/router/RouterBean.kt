package com.yangshuai.library.base.router

import android.app.Activity

/**
 * @Author yangshuai
 * @Date 2023-05-09 周二 15:18
 * @Description TODO
 */
class RouterBean {
    //路由路径
    var url:String? = null
    //路由跳转的activity
    var activity:Activity? = null
    //相同页面标记,每次添加的时候++
    var pageType:Int = 0
}