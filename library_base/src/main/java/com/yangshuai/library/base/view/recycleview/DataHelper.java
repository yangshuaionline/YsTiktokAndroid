package com.yangshuai.library.base.view.recycleview;

import java.util.List;

/**
 * BaseRecycleAdapter适配器数据操作帮助类
 * Created by lsy on 2017/4/24
 */
public interface DataHelper<T> {

    boolean addAll(List<T> list);

    boolean addAll(int position, List<T> list);

    void add(T data);

    void add(int position, T data);

    void clear();

    boolean contains(T data);

    T getData(int index);

    void modify(T oldData, T newData);

    void modify(int index, T newData);

    boolean remove(T data);

    void remove(int index);

}
