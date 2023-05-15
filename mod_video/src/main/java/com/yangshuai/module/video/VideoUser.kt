package com.yangshuai.module.video

import android.app.Activity
import android.view.ViewGroup
import com.yangshuai.module.video.views.TiktokView
import com.yangshuai.module.video.views.VideoView

/**
 * @Author yangshuai
 * @Date 2023-04-13 周四 10:24
 * @Description 视频播放入口
 */
object VideoUser {
    /**
     * tiktok布局嵌入
     * */
    fun setTiktok(activity: Activity, viewGroup: ViewGroup, bean: VideoBean){
        val view = TiktokView(activity,viewGroup)
        viewGroup.addView(view)
    }
    /**
     * 视频播放(不带小窗)
     * */
    fun setVideo(activity: Activity, viewGroup: ViewGroup, bean: VideoBean){
        val view = VideoView(activity,viewGroup)
        val url = "http://xiaotouhang.oss-cn-beijing.aliyuncs.com/xxth/manager/766e3bd859de4d15b3e65602845b8c14.mp4"
        view.setPlay(url)
        viewGroup.addView(view)
    }
    /**
     * 视频播放（带小窗）
     * */
//    fun setVideoWithWindow(){
//
//    }

}