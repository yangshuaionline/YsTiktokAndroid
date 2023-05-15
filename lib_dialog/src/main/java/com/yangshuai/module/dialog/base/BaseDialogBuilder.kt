package com.yangshuai.module.dialog.base

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.annotation.StyleRes
import com.yangshuai.module.dialog.DialogBuilder
import com.yangshuai.module.dialog.DialogConst
import com.yangshuai.module.dialog.R
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.yangshuai.module.dialog.manager.DialogManager
import com.yangshuai.module.dialog.manager.ManagerBean


/**
 * @Author yangshuai
 * @Date : 2023-03-22 18:23
 * @Version 1.0
 * Description:
 * 1.处理dialog得整体样式逻辑
 * 2.处理title样式逻辑
 * 3.处理底部按钮样式逻辑
 * 4.中间content部分甩给子类实现
 */
open class BaseDialogBuilder:Dialog, DialogBuilder {
    var activity:Activity? = null
    private var layoutChild:View? = null//中间建在得view
    private var baseLayout:RelativeLayout? = null//最外层layout
    private var linearLayout:LinearLayout? = null//背景layout，分别存放title、content、底部button
    private var linearLayoutBgDrawable:Drawable? = null//背景layout的背景（drawable）
    private var linearlayoutBgColor:Int? = null//背景layout的背景（颜色）
    var title: BaseDialogTitle? = null//dialog  title布局
    var bottom: BaseDialogBottom? = null//dialog  bottom布局
    private var content: FrameLayout? = null//中间显示内容得布局
    private var canClickClose = false//是否可以点击背景关闭
    private var bean: BaseBean? = null
    //title
    private var showIcon:Boolean = false//是否显示右上角icon
    private var closeListener:View.OnClickListener? = null//右上角关闭监听
    private var titleText:String? = null//title提示文字
    private var titleColor:Int? = null//title提示文字的颜色
    private var bgImageRes:Int? = null//背景资源图片
    private var bgImageUrl:String? = null//背景网络图片
    private var titleIsShow:Boolean = true//是否显示title
    //bottom
    private var leftText:String? = null//左侧按钮提示文字
    private var leftBgRes:Int? = null//左边按钮背景样式
    private var isOpenLeftListener:Boolean? = null//是否打开左边按钮点击事件
    private var leftListener:View.OnClickListener? = null//左侧按钮点击事件
    private var rightText:String? = null//右侧按钮提示文字
    private var rightBgRes:Int? = null//右边按钮背景样式
    private var isOpenRightListener:Boolean? = null//是否打开右边按钮点击事件
    private var rightListener:View.OnClickListener? = null//右侧按钮点击事件
    private var centerText:String? = null//一个按钮的提示文字
    private var centerBgRes:Int? = null//一个按钮的背景样式
    private var centerTextColorRes:Int? = null//一个按钮的文字颜色
    private var isOpenCenterListener:Boolean? = null//是否打开一个按钮点击事件
    private var centerListener:View.OnClickListener? = null//一个按钮点击事件
    private var linkText:String? = null//底部超链接提示文字
    private var linkListener:View.OnClickListener? = null//超链接点击事件
    //all
    private var icon: ImageView? = null//dialog右上角得关闭按钮
    private var iconListener:View.OnClickListener? = null//右上角得X点击事件，不设置默认就是dismiss
    var isFull = false//是否为全面屏
    //默认主题样式
    constructor(context: Context):this(context, DialogConst.style){
        this.activity = context as Activity
    }
    //设置主题样式
    constructor(context: Context,@StyleRes themeResId:Int):super(context,themeResId){
        this.activity = context as Activity
    }
    //添加中间自定义view,子类调用
    open fun setChild(layoutChild:View?){
        setContentView(R.layout.dialog_base)
        this.layoutChild = layoutChild
        linearLayout = findViewById(R.id.dialog_root_view)
        baseLayout = findViewById(R.id.base_layout)
        icon = findViewById(R.id.dialog_iv_dialog_close)
        icon?.let {i->
            with(i){
                context?.let { Glide.with(it).load(DialogConst.closeIcon).into(i) }
                visibility = View.GONE
            }
        }
        linearLayout?.let {
            title = BaseDialogTitle(this@BaseDialogBuilder,it)
            it.addView(layoutChild)
            bottom = BaseDialogBottom(this@BaseDialogBuilder,it)
        }
    }

    override fun setBean(bean: BaseBean): DialogBuilder {
        var addBean = ManagerBean(context,bean,this)
        DialogManager.add(addBean)
        this.bean = bean
        return this
    }

    override fun setBeanSet(bean: BaseBeanSet): DialogBuilder {
        return this
    }

    override fun setLayoutBg(res: Drawable): DialogBuilder {
        this.linearLayoutBgDrawable = res
        return this
    }

    override fun setLayoutBg(color: Int): DialogBuilder {
        this.linearlayoutBgColor = color
        return this
    }

    override fun setCanClickClose():DialogBuilder {
        this.canClickClose = true
        return this
    }

    override fun setFull(): DialogBuilder  {
        // 下面这些设置必须在此方法(onStart())中才有效
        var window = window
        window = getWindow()
        window?.let {
            with(it){
                // 如果不设置这句代码, 那么弹框就会与四边都有一定的距离
                setBackgroundDrawableResource(android.R.color.transparent)
                // 设置动画(实现从底部弹出的对话框效果)
//                    setWindowAnimations(R.style.BaseBottomSheetDialog)
                val params = window.attributes
                params.gravity = Gravity.BOTTOM
                // 如果不设置宽度,那么即使你在布局中设置宽度为 match_parent 也不会起作用
                params.width = context.resources.displayMetrics.widthPixels
                attributes = params
            }

        }
        linearLayout?.let {it->
            //取控件当前的布局参数
            val params = it.layoutParams
            params.width = LinearLayout.LayoutParams.MATCH_PARENT
            params.height =  LinearLayout.LayoutParams.WRAP_CONTENT
            it.layoutParams = params
            linearLayoutBgDrawable?.let {drawable->
                it.background = drawable
            }?:let{builder->
                it.background = ContextCompat.getDrawable(context, R.drawable.base_white_radius_3)
            }
            linearlayoutBgColor?.let {color->
                it.setBackgroundColor(ContextCompat.getColor(context,color))
            }?:let{builder->
                it.background = ContextCompat.getDrawable(context,R.drawable.base_white_radius_3)
            }
        }
        bottom?.setBottom()
        isFull = true
        return this
    }

    override fun build(): DialogBuilder {
        title?.let {t->
            with(t){
                titleText?.let { setTitle(it) }
                titleColor?.let { setTitleColor(it) }
                bgImageRes?.let { setBgImage(it) }
                bgImageUrl?.let { setBgImage(it) }
                when(titleIsShow){
                    false-> setTitleHind()
                    else-> setTitleShow()
                }
            }
        }
        bottom?.let {b->
            with(b){
                leftText?.let { setLeft(it) }
                leftBgRes?.let { setLeftBg(it) }
                leftListener?.let { setLeft(it) }
                isOpenCenterListener?.let { openLeftListener(it) }

                rightText?.let { setRight(it) }
                rightBgRes?.let { setRightBg(it) }
                rightListener?.let { setRight(it) }
                isOpenRightListener?.let { openRightListener(it) }

                centerText?.let { setCenter(it) }
                centerBgRes?.let { setCenterBg(it) }
                centerListener?.let { setCenter(it) }
                centerTextColorRes?.let { setCenterTextColor(it) }
                isOpenCenterListener?.let { openCenterListener(it) }

                linkText?.let { setLink(it) }
                linkListener?.let { setLink(it) }
            }

        }
        linearLayout?.let {layout->
            with(layout){
//                linearlayoutBgColor?.let { setBackgroundColor(it) }
//                linearLayoutBgDrawable.let { background = it }
                linearLayoutBgDrawable?.let {drawable->
                    background = drawable
                }?:let{
                    background = ContextCompat.getDrawable(context, R.drawable.base_white_radius_3)
                }
                linearlayoutBgColor?.let {color->
                    setBackgroundColor(ContextCompat.getColor(context,color))
                }?:let{
                    background = ContextCompat.getDrawable(context,R.drawable.base_white_radius_3)
                }
            }
        }
        setCanceledOnTouchOutside(canClickClose)
        showIcon(showIcon)
        return this
    }

    override fun showClose(): DialogBuilder {
        this.showIcon = true
        return this
    }

    override fun hindClose(): DialogBuilder {
        this.showIcon = false
        return this
    }

    override fun setCloseListener(listener: View.OnClickListener): DialogBuilder {
        closeListener = listener
        return this
    }

    override fun setTitle(text: String): DialogBuilder {
        titleText = text
        return this
    }

    override fun setTitleColor(color: Int): DialogBuilder {
        titleColor = color
        return this
    }

    override fun setBgImage(img: Int): DialogBuilder {
        bgImageRes = img
        return this
    }

    override fun setBgImage(img: String): DialogBuilder {
        bgImageUrl = img
        return this
    }

    override fun setTitleShow(): DialogBuilder  {
        titleIsShow = true
        return this
    }

    override fun setTitleHind(): DialogBuilder{
        titleIsShow = false
        return this
    }

    override fun setLeft(text: String, listener: View.OnClickListener): DialogBuilder {
        leftText = text
        leftListener = listener
        return this
    }

    override fun setLeftBg(drawable: Int): DialogBuilder {
        leftBgRes = drawable
        return this
    }

    override fun openLeftListener(): DialogBuilder {
        isOpenLeftListener = true
        return this
    }

    override fun closeLeftListener(): DialogBuilder {
        isOpenLeftListener = false
        return this
    }

    override fun setRight(text: String, listener: View.OnClickListener): DialogBuilder {
        rightText = text
        rightListener = listener
        return this
    }

    override fun setRightBg(drawable: Int): DialogBuilder {
        rightBgRes = drawable
        return this
    }

    override fun openRightListener(): DialogBuilder {
        isOpenRightListener = true
        return this
    }

    override fun closeRightListener(): DialogBuilder {
        isOpenRightListener = false
        return this
    }

    override fun setCenter(text: String, listener: View.OnClickListener): DialogBuilder {
        centerText = text
        centerListener = listener
        return this
    }

    override fun setCenterBg(drawable: Int): DialogBuilder {
        centerBgRes = drawable
        return this
    }

    override fun setCenterTextColor(color: Int): DialogBuilder {
        centerTextColorRes = color
        return this
    }

    override fun openCenterListener(): DialogBuilder {
        isOpenCenterListener = true
        return this
    }

    override fun closeCenterListener(): DialogBuilder {
        isOpenCenterListener = false
        return this
    }

    override fun setLink(text: String, listener: View.OnClickListener): DialogBuilder {
        linkText = text
        linkListener = listener
        return this
    }
    //设置是否显示右上角X
    //不设置默认为隐藏
    private fun showIcon(isShow:Boolean){
        when(isShow){
            true->{
                icon?.let {
                    with(it){
                        visibility = View.VISIBLE
                        iconListener?.let { listener->
                            setOnClickListener(listener)
                        }?:setOnClickListener { dismiss() }
                    }
                }
            }
            else->{
                icon?.let {
                    with(it){
                        visibility = View.GONE
                    }
                }
            }
        }
    }
    //设置右上角x得点击事件
    private fun setIconListener(listener:View.OnClickListener){
        this.iconListener = listener
        icon?.let {
            it.setOnClickListener(iconListener)
        }
    }

    override fun dismiss() {
        bean?.let {
            var addBean = ManagerBean(context,it,this)
            DialogManager.remove(addBean)
        }
        super.dismiss()
    }
}