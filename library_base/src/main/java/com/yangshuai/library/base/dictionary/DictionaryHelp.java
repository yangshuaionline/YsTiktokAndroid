package com.yangshuai.library.base.dictionary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yangshuai.library.base.application.BaseApplication;
import com.yangshuai.library.base.entity.DictDataDtosBean;
import com.yangshuai.library.base.entity.DictionaryBean;
import com.yangshuai.library.base.network.ApiServer;
import com.yangshuai.library.base.network.RetrofitManager;
import com.yangshuai.library.base.observer.RetryWithDelay;
import com.yangshuai.library.base.utils.IOUtils;
import com.yangshuai.library.base.utils.RxUtils;
import com.yangshuai.library.base.utils.StringUtils;

import org.litepal.LitePal;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 字典操作辅助类
 *
 * @Author hcp
 * @Created 2019-05-10
 * @Editor hcp
 * @Edited 2019-05-10
 * @Type
 * @Layout
 * @Api
 */
public class DictionaryHelp {


    public final static String VAL = "val";
    public final static String NAME = "name";

    /**
     * 根据code获取字典数据
     */
    public static List<DictionaryBean> getDictionaryByCode(String code) {
        List<DictionaryBean> dictionaryBeans = LitePal.where("code = ?", code).find(DictionaryBean.class, true);
        if (dictionaryBeans.size()==0){
            if (BaseApplication.data!=null && BaseApplication.data.size()!=0){
                List<DictionaryBean> temp = new ArrayList<>();
                for (int i = 0; i <BaseApplication.data.size() ; i++) {
                    DictionaryBean dictionaryBean = BaseApplication.data.get(i);
                    if (dictionaryBean.getCode().equals(code)){
                        temp.add(dictionaryBean);
                    }
                }
                return temp;
            }
        }
        return dictionaryBeans;
    }

    /**
     * 根据code获取字典名字与码值数组（暂时没想好怎么写）
     */
    public static List<Map<String, String>> getNameAndValByCode(String code) {
        List<Map<String, String>> nameList = new ArrayList<>();
        List<DictionaryBean> dictionaryBeanList = getDictionaryByCode(code);
        if (dictionaryBeanList != null && dictionaryBeanList.size() > 0) {
            if (dictionaryBeanList.get(0).getDictDataDtos() != null) {
                for (DictDataDtosBean dictDataDtosBean : dictionaryBeanList.get(0).getDictDataDtos()) {
                    Map<String, String> map = new HashMap<>();
                    map.put(VAL, dictDataDtosBean.getVal());
                    map.put(NAME, dictDataDtosBean.getDictDataName());
                    nameList.add(map);
                }
            }
        }
        return nameList;
    }

    /**
     * 根据code获取字典键值对
     * <p>
     * key:名称 value:码值
     */
    public static Map<String, String> getMapByCode(String code) {
        Map<String, String> dictionary = new LinkedHashMap<>();

        List<DictDataDtosBean> list = getSortedDictByCode(code);
        for (DictDataDtosBean dictDataDtosBean : list) {
            dictionary.put(dictDataDtosBean.getDictDataName(), dictDataDtosBean.getVal());
        }

        return dictionary;
    }

    /**
     * 根据code获取字典名称
     */
    public static String getNameByCode(String groupCode, String code) {
        if (StringUtils.isEmpty(groupCode) || StringUtils.isEmpty(code)) return null;

        List<DictDataDtosBean> list = getSortedDictByCode(groupCode);
        for (DictDataDtosBean dictDataDtosBean : list) {
            if (code.equals(dictDataDtosBean.getVal()))
                return dictDataDtosBean.getDictDataName();
        }

        return null;
    }

    /**
     * 获取有序的字典数据
     *
     * @param code
     * @return
     */
    public static List<DictDataDtosBean> getSortedDictByCode(String code) {
        List<DictDataDtosBean> list = new ArrayList<>();
        if (!StringUtils.isEmpty(code)) {
            List<DictionaryBean> dictionaryBeanList = getDictionaryByCode(code);
            if (dictionaryBeanList != null && dictionaryBeanList.size() > 0) {
                List<DictDataDtosBean> dictDataDtosBeanList = dictionaryBeanList.get(0).getDictDataDtos();
                if (dictDataDtosBeanList != null && dictDataDtosBeanList.size() > 0) {
                    list.addAll(dictDataDtosBeanList);
                    Collections.sort(list, (o1, o2) -> o1.getSort() - o2.getSort());
                }
            }
        }
        return list;
    }

    /**
     * 根据code获取字典名称数组
     **/
    public static List<String> getNameByCode(String code) {
        List<String> nameList = new ArrayList<>();
        List<DictionaryBean> dictionaryBeanList = getDictionaryByCode(code);

        if (dictionaryBeanList != null && dictionaryBeanList.size() != 0) {
            try {
                for (DictDataDtosBean dictDataDtosBean : dictionaryBeanList.get(0).getDictDataDtos()) {
                    nameList.add(dictDataDtosBean.getDictDataName());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return nameList;
    }

    /**
     * 保存字典数据
     *
     * @param data
     */
    private synchronized static void saveDictionary(List<DictionaryBean> data) {
        if (data == null) return;
        BaseApplication.data.addAll(data);
        LitePal.deleteAll(DictionaryBean.class);
        LitePal.deleteAll(DictDataDtosBean.class);
        for (int i = 0, len = data.size(); i < len; i++) {
            DictionaryBean dictionaryBean = data.get(i);
            if (dictionaryBean != null && !dictionaryBean.getDictDataDtos().isEmpty()) {
                LitePal.saveAll(data.get(i).getDictDataDtos());
            }
        }
        LitePal.saveAll(data);
    }

    /**
     * 首次使用app时从缓存文件加载字典数据
     */
    @SuppressLint("CheckResult")
    public static void loadFromCacheFile(Context context, String fileName) {
        Observable
                .create((ObservableOnSubscribe<List<DictionaryBean>>) emitter -> {

                    // 数据库有数据时说明缓存数据已经加载
                    if (LitePal.count(DictionaryBean.class) == 0) {

                        Log.d("dictionary", "--------------->load start");
                        String json = IOUtils.getFromAssets(context, fileName);
                        Type type = new TypeToken<List<DictionaryBean>>() {
                        }.getType();

                        List<DictionaryBean> data = new Gson().fromJson(json, type);
                        Log.d("dictionary", "--------------->load end");

                        emitter.onNext(data);
                        emitter.onComplete();
                    } else {
                        emitter.onError(new Throwable("字典缓存已经加载"));
                    }

                })
                .subscribeOn(Schedulers.io())
                // 保存字典数据到数据库
                .subscribe(DictionaryHelp::saveDictionary, Throwable::printStackTrace);
    }

    /**
     * 获取字典并存储
     */
    public static void loadRemoteDictionary() {
        RetrofitManager.create(ApiServer.class).getDictionary()
                .retryWhen(new RetryWithDelay(3, 5, TimeUnit.SECONDS))
                .compose(RxUtils.responseTransformer())
                .map(data -> {
                    if (data != null) {
                        DictionaryHelp.saveDictionary(data);
                        return Boolean.TRUE;
                    } else {
                        return Boolean.FALSE;
                    }
                })
                .compose(RxUtils.schedulersTransformer())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean != null && aBoolean) {
                            Log.d("dictionary", "字典更新成功");
                        } else {
                            Log.d("dictionary", "字典更新失败");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
