package com.yangshuai.module.dialog.list

import android.content.Context
import android.text.method.DigitsKeyListener
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.yangshuai.module.dialog.R

/**
 * @Author yangshuai
 * @Date : 2023-03-27 11:05
 * @Version 1.0
 * Description:
 */
class InputDialogAdapter(
    var context: Context,
    var list: MutableList<ListData>) :
    RecyclerView.Adapter<InputDialogAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var layout:LinearLayout? = null
        var title:TextView? = null
        var content:TextView? = null
        init {
            layout = view.findViewById(R.id.layout)
            title = view.findViewById(R.id.title)
            content = view.findViewById(R.id.content)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = View.inflate(context, R.layout.dialog_list_input_item,null)
        //获取布局解析器
        val layoutInflater =  LayoutInflater.from(parent.context)
        //解析布局
        val item = layoutInflater.inflate(R.layout.dialog_list_input_item,parent,false)
        return ViewHolder(item)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        holder.title?.text = data.title
        holder.content?.text = data.content
        holder.content?.hint = data.hint
        holder.content?.keyListener = DigitsKeyListener.getInstance("1234567890")
        holder.content?.addTextChangedListener {
            Log.i("=====>","打印${it.toString()}")
            data.content = it.toString()
        }
    }
    fun getDataList():MutableList<ListData>{
        return list
    }
    override fun getItemCount(): Int {
        return list.size
    }
}