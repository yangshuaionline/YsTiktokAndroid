package com.yangshuai.library.base.application;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

public class AppFrontBackHelper {

    private static AppFrontBackHelper mInstance;
    private OnAppStatusListener mOnAppStatusListener;
    private int isFirstStartApp = 1;


    public AppFrontBackHelper() {}

    public static AppFrontBackHelper getInstance() {
        if (mInstance == null) {
            synchronized (AppFrontBackHelper.class) {
                if (mInstance == null) {
                    mInstance = new AppFrontBackHelper();
                }
            }
        }
        return mInstance;
    }

    /**
     * 初始化
     * @param value
     */
    public void setIsFirstAppValue(int value) {
        isFirstStartApp = value;
    }

    /**
     * 注册状态监听，仅在Application中使用
     * @param application
     * @param listener
     */
    public void register(Application application, OnAppStatusListener listener){
        mOnAppStatusListener = listener;
        application.registerActivityLifecycleCallbacks(activityLifecycleCallbacks);
    }

    public void unRegister(Application application){
        application.unregisterActivityLifecycleCallbacks(activityLifecycleCallbacks);
    }

    private Application.ActivityLifecycleCallbacks activityLifecycleCallbacks = new Application.ActivityLifecycleCallbacks() {
        //打开的Activity数量统计
        private int activityStartCount = 0;

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        }

        @Override
        public void onActivityStarted(Activity activity) {
            activityStartCount++;
            //数值从0变到1说明是从后台切到前台
            if (activityStartCount == 1){
                //从后台切到前台
                if(mOnAppStatusListener != null){
                    mOnAppStatusListener.onFront(isFirstStartApp);
                    isFirstStartApp ++;
                }
            }
        }

        @Override
        public void onActivityResumed(Activity activity) {
            mOnAppStatusListener.onActivityResumed(activity);
        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {
            activityStartCount--;
            //数值从1到0说明是从前台切到后台
            if (activityStartCount == 0){
                //从前台切到后台
                if(mOnAppStatusListener != null){
                    mOnAppStatusListener.onBack();
                }
            }
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
    };

    public interface OnAppStatusListener{
        void onFront(int isFirstStartApp);
        void onBack();
        void onActivityResumed(Activity activity);
    }

}