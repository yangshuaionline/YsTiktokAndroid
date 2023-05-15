package com.yangshuai.module.dialog.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.RecyclerView
import com.yangshuai.module.dialog.HeightCallBack
import com.yangshuai.module.dialog.R

/**
 * @Author yangshuai
 * @Date : 2023-03-27 11:05
 * @Version 1.0
 * Description:
 */
class RightIconChooseAdapter(
    var context: Context,
    var list: MutableList<ListData>,
    var listener: OnItemChooseBackListener,
    var callBack: HeightCallBack
) :
    RecyclerView.Adapter<RightIconChooseAdapter.ViewHolder>() {
    var height:Int? = null
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var layout:LinearLayoutCompat? = null
        var icon:ImageView? = null
        var tvContent:TextView? = null
        init {
            layout = view.findViewById(R.id.layout)
            icon = view.findViewById(R.id.icon)
            tvContent = view.findViewById(R.id.tv_content)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = View.inflate(context, R.layout.dialog_list_left_icon_item,null)
        //获取布局解析器
        val layoutInflater =  LayoutInflater.from(parent.context)
        //解析布局
        val item = layoutInflater.inflate(R.layout.dialog_list_right_icon_item,parent,false)
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
        val d = mutableListOf<ListData>()
        val data = list[position]
        holder.icon?.isSelected = (data.isChoose == true)
        if(data.isChoose == true){
            d.add(data)
            listener.click(d)
        }
        holder.tvContent?.text = data.content
        holder.layout?.setOnClickListener {
            for(item in list.indices){
                list[item].isChoose = false
            }
            data.isChoose = true
            notifyDataSetChanged()
            d.clear()
            d.add(data)
            listener.click(d)
        }
    }

    override fun getItemCount(): Int {
        return list.size ?:0
    }
}