package com.yangshuai.library.base.view.recycleview;

/**
 * 如需使用RecycleView列表多布局效果，请实现本接口
 * Created by lsy on 2017/4/24.
 */

public interface MultiTypeSupport<T> {

    /**
     * item布局多样化功能,
     * 可根据数据实体来判断你需要使用哪种布局
     *
     * @param data     泛型数据实体
     * @param position ItemViewType的position
     * @return 布局ID
     */
    int getMultiTypeLayout(T data, int position);

}
