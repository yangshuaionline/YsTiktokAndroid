package com.yangshuai.library.base.router
/**
 * 判断路由是否重复，是否可以继续执行逻辑
 * */
enum class RouterType {
    CAN,//栈中没有，并且不在白名单内，可以继续添加
    CAN_NOT,//栈中有，或者在白名单中，不可以继续添加(跳转的时候要跳转到该页面，清除栈后边的页面)
    REPEAT,//栈中有，在白名单中，可以继续添加
}