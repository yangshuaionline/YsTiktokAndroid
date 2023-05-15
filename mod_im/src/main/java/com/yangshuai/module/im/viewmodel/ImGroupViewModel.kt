package com.yangshuai.module.im.viewmodel

import android.app.Application
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import com.yangshuai.module.im.R
import me.tatarka.bindingcollectionadapter2.ItemBinding
import com.yangshuai.module.im.BR
import com.yangshuai.module.im.bean.MessageBean

/**
 * @Author yangshuai
 * @Date 2023-05-08 周一 15:01
 * @Description
 * IM群聊功能ViewModel
 */
class ImGroupViewModel(application: Application) : AndroidViewModel(application){
    //聊天内容列表
    var itemListData =  ObservableArrayList<MessageBean>()
    //聊天内容item布局
    val itemListDataBinding = ItemBinding.of<MessageBean>(BR.data, R.layout.im_act_group_item).bindExtra(BR.viewModel, this)
    //输入框得内容
    var msgText = ObservableField<String>()
}