package com.yangshuai.library.base.interfaces;

/**
 * @author hcp
 * @created 2019/3/14
 */
public interface BaseActFragmImpl {

    /**
     * 初始化界面传递参数
     * 在此方法注入路由框架，拿到Autowired值来获取路由传参
     * 必须在initParam方法中注入，否则传到ViewModel里面的参数就会为空
     * 例：ARouter.getInstance().inject(this);
     */
    void initParam();

    /**
     * 初始化数据
     */
    void initData();

    /**
     * 初始化界面观察者的监听
     */
    void initViewObservable();

}
