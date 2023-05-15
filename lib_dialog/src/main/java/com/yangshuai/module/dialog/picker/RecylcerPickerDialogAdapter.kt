package com.yangshuai.module.dialog.picker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yangshuai.module.dialog.*

/**
 * @Author yangshuai
 * @Date : 2023-03-27 11:05
 * @Version 1.0
 * Description:
 */
class RecylcerPickerDialogAdapter(
    var context: Context,
    var dataBean:PickerBean,
    var callBack: HeightCallBack
) :
    RecyclerView.Adapter<RecylcerPickerDialogAdapter.ViewHolder>() {
    var height:Int? = null
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var layout:LinearLayout? = null
        var content:TextView? = null
        init {
            layout = view.findViewById(R.id.layout)
            content = view.findViewById(R.id.content)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = View.inflate(context, R.layout.dialog_list_input_item,null)
        //获取布局解析器
        val layoutInflater =  LayoutInflater.from(parent.context)
        when(dataBean.rvType){
            PickerEnum.GRID_VIEW,PickerEnum.LIST_VIEW->{
                //解析布局
                val item = layoutInflater.inflate(R.layout.dialog_picker_recycler_item_grid,parent,false)
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
            else->{
                //解析布局
                val item = layoutInflater.inflate(R.layout.dialog_picker_recycler_item_grid,parent,false)
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
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        dataBean.rvList?.let {list->
            val data = list[position]
            holder.content?.text = data.text
            if(data.isChoose == null) data.isChoose = false
            holder.content?.isSelected = data.isChoose == true
            if(dataBean.canClick){
                holder.content?.setOnClickListener {
                    data.isChoose?.let {
                        dataBean.maxChooseCount?.let {max->
                            if(!it){
                                var count = 0
                                for(item in list.iterator()){
                                    if(item.isChoose == true){
                                        count++
                                    }
                                }
                                if(count>=max){
                                    dataBean.maxChooseListener?.show()
                                    return@setOnClickListener
                                }
                            }
                        }
                        data.isChoose = !it
                        notifyItemChanged(position)
                    }
                }
            }
        }

    }
    fun getDataList():MutableList<PickerData>{
        return dataBean.rvList ?: mutableListOf()
    }
    override fun getItemCount(): Int {
        return dataBean.rvList?.size ?: 0
    }
}