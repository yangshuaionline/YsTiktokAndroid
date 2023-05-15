package com.yangshuai.module.dialog.list

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yangshuai.library.base.utils.StringUtils
import com.yangshuai.module.dialog.HeightCallBack
import com.yangshuai.module.dialog.R
import com.yangshuai.module.dialog.ToastUtils
import java.lang.Exception

/**
 * @Author yangshuai
 * @Date : 2023-03-27 11:05
 * @Version 1.0
 * Description:
 */
class CopyDialogAdapter(
    var context: Context,
    var list: MutableList<ListData>,
    var callBack: HeightCallBack
) :
    RecyclerView.Adapter<CopyDialogAdapter.ViewHolder>() {
    var height:Int? = null
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var layout:LinearLayout? = null
        var title:TextView? = null
        var content:TextView? = null
        var bt:TextView? = null
        init {
            layout = view.findViewById(R.id.layout)
            title = view.findViewById(R.id.title)
            content = view.findViewById(R.id.content)
            bt = view.findViewById(R.id.bt)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = View.inflate(context, R.layout.dialog_list_input_item,null)
        //获取布局解析器
        val layoutInflater =  LayoutInflater.from(parent.context)
        //解析布局
        val item = layoutInflater.inflate(R.layout.dialog_list_copy_item,parent,false)
        if(height == null){
            item.post {
                height = item.height
                height?.let{
                    callBack.setHeight(it)
                }
            }
        }
        return ViewHolder(item)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        holder.title?.text = data.title
        holder.content?.text = data.content
        data.isContentRight?.let {
            when(it){
                true->{
                    holder.content?.gravity = Gravity.RIGHT
                }
                else->{}
            }
        }
        if(data.isCopy == null){
            holder.bt?.visibility = View.GONE
        }else{
            if(data.isCopy == true){
                holder.bt?.visibility = View.VISIBLE
                holder.bt?.setOnClickListener {
                    try {
                        if (StringUtils.isEmpty(data.content)){
                            ToastUtils.showIconText(context,"复制失败，${data.title}为空")
                            return@setOnClickListener
                        }
                        //获取剪贴板管理器
                        val cm = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        // 创建普通字符型ClipData
                        val mClipData = ClipData.newPlainText("Label", data.content)
                        // 将ClipData内容放到系统剪贴板里。
                        cm.setPrimaryClip(mClipData)
                        ToastUtils.showIconText(context,"复制成功")
                    } catch (e: Exception) {
                        ToastUtils.showIconText(context,"复制失败，${e.message}")
                    }
                }
            }else{
                holder.bt?.visibility = View.GONE
            }
        }
    }
    fun getDataList():MutableList<ListData>{
        return list
    }
    override fun getItemCount(): Int {
        return list.size
    }
}