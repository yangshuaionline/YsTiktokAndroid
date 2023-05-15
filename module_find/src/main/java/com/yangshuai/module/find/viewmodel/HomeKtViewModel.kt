package com.yangshuai.module.find.viewmodel

import android.app.Application
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import com.yangshuai.library.base.interfaces.ResponseListener
import com.yangshuai.library.base.network.RetrofitManager
import com.yangshuai.library.base.observer.ResponseObserver
import com.yangshuai.library.base.utils.RxUtils
import com.yangshuai.module.find.BR
import com.yangshuai.module.find.R
import com.yangshuai.module.find.api.HomeAPI
import com.yangshuai.module.find.bean.BannerBean
import com.yangshuai.module.find.bean.FrindeBean
import com.yangshuai.module.find.bean.NewsBean
import me.tatarka.bindingcollectionadapter2.ItemBinding

class HomeKtViewModel(appContext: Application) :AndroidViewModel(appContext) {
    //列表数据
    var item :ObservableArrayList<String> = ObservableArrayList()
    var itemBinding = ItemBinding.of<String>(BR.databean, R.layout.home_news_item)
    var pageNo : ObservableField<Int>  = ObservableField(0)

    fun getBannerData(listener: ResponseListener<List<BannerBean>>){
        RetrofitManager
            .create(HomeAPI::class.java)
            .getBannerBata()
            .compose(RxUtils.responseTransformer())
            .compose(RxUtils.schedulersTransformer())
            .subscribe(object : ResponseObserver<List<BannerBean>>() {
                override fun onSuccess(data: List<BannerBean>?) {
                    listener.loadSuccess(data)

                }

                override fun onError(code: String?, msg: String?) {
                    super.onError(code, msg)
                    listener.loadFailed(code,msg)
                }

            })
    }
    fun getFriendData(){
        RetrofitManager
            .create(HomeAPI::class.java)
            .getfriendData()
            .compose(RxUtils.responseTransformer())
            .compose(RxUtils.schedulersTransformer())
            .subscribe(object : ResponseObserver<List<FrindeBean>>() {
                override fun onSuccess(data: List<FrindeBean>?) {

                }

                override fun onError(code: String?, msg: String?) {
                    super.onError(code, msg)
                }

            })
    }
    fun getHotNewsData(){
        val get = pageNo.get() as  Int
        RetrofitManager
            .create(HomeAPI::class.java)
            .getHotNewsData(get)
            .compose(RxUtils.responseTransformer())
            .compose(RxUtils.schedulersTransformer())
            .subscribe(object : ResponseObserver<NewsBean>() {
                override fun onSuccess(data: NewsBean?) {

                }

                override fun onError(code: String?, msg: String?) {
                    super.onError(code, msg)
                }

            })
    }

}