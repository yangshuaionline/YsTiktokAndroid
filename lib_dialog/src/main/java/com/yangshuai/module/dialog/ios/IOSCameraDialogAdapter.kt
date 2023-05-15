package com.yangshuai.module.dialog.ios

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yangshuai.module.dialog.HeightCallBack
import com.yangshuai.module.dialog.R

/**
 * @Author yangshuai
 * @Date : 2023-03-27 11:05
 * @Version 1.0
 * Description:
 */
class IOSCameraDialogAdapter(
    var context: Context,
    var list: MutableList<IOSData>,
    var callBackHeight: HeightCallBack,
    var callBackItemClick:IOSListClickCallBack
) :
    RecyclerView.Adapter<IOSCameraDialogAdapter.ViewHolder>() {
    var height:Int? = null
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var content:TextView? = null
        init {
            content = view.findViewById(R.id.content)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = View.inflate(context, R.layout.dialog_list_input_item,null)
        //获取布局解析器
        val layoutInflater =  LayoutInflater.from(parent.context)
        //解析布局
        val item = layoutInflater.inflate(R.layout.dialog_ios_camera_item,parent,false)
        if(height == null){
            item.post {
                height = item.height
                height?.let{
                    callBackHeight.setHeight(it)
                }
            }
        }
        return ViewHolder(item)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        holder.content?.text = data.content
        holder.content?.setOnClickListener {
            callBackItemClick.clickItem(data)
        }
    }
    fun getDataList():MutableList<IOSData>{
        return list
    }
    override fun getItemCount(): Int {
        return list.size
    }
}