package com.yangshuai.module.video.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.yangshuai.module.video.R
import com.yangshuai.module.video.jzvideo.*

/**
 * @Author yangshuai
 * @Date 2023-05-09 周二 17:35
 * @Description
 * 视频播放
 */
class VideoView:LinearLayout{
    var viewGroup: ViewGroup? = null
    var jzVideo: JzvdStd? = null
    constructor(context: Context, viewGroup: ViewGroup):this(context){
        this.viewGroup = viewGroup
    }
    constructor(context: Context):this(context, null)
    constructor(context: Context, attrs: AttributeSet?):this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int):this(context, attrs, defStyleAttr, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int):super(context,attrs,defStyleAttr,defStyleRes){
        val view: View = View.inflate(context, R.layout.video_video,null)
        jzVideo = view.findViewById(R.id.jz_video)
        addView(view)
    }
    fun setPlay(url:String){
        val jzDataSource = JZDataSource(url)
        jzDataSource.looping = true //设置循环播放
        jzVideo?.setUp(jzDataSource, Jzvd.SCREEN_NORMAL)
        post {
            setHeight()//设置视频的高度
        }
    }
    //设置视频的高度
    private fun setHeight(){
        val width = JZUtils.getScreenWidth(context)
        val params = jzVideo?.layoutParams as LinearLayout.LayoutParams
        params.width = width // 设置宽度为 100dp
        params.height = (width * 0.6).toInt() // 设置高度为 100dp
        jzVideo?.layoutParams = params
    }
}