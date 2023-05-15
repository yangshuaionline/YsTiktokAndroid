package com.yangshuai.library.base.application;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.danikula.videocache.HttpProxyCacheServer;
import com.yangshuai.library.base.entity.DictionaryBean;
import com.yangshuai.library.base.utils.Utils;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import io.rong.imlib.RongCoreClient;
import io.rong.imlib.RongIMClient;

/**
 * @author hcp
 * Create on 2019-12-22 23:53
 */
public class BaseApplication extends LitePalApplication implements AppFrontBackHelper.OnAppStatusListener {

    /*当前对象的静态实例*/
    private static BaseApplication mInstance;
    /*当前显示的Activity*/
    private static Activity mCurrentActivity;

    //字典操作辅助类里面加了一个全局变量来存放字典的数据，防止刚进app就马上去获取数据库里面的字典数据为空的问题
    public static List<DictionaryBean> data = new ArrayList<>();

    public static BaseApplication getBaseApplication() {
        return mInstance;
    }

    private LinkedList<View> floatViewList = new LinkedList<>();


    //视频缓存
    private HttpProxyCacheServer porxy;
    public static Activity getCurrentContext() {
        if (mCurrentActivity != null && !mCurrentActivity.isFinishing()) {
            return mCurrentActivity;
        } else {
            return null;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        LitePal.initialize(this);//初始化数据库
        AppFrontBackHelper.getInstance().register(this, this);
        //初始化融云
        initRongYun();
    }

    @Override
    public void onFront(int isFirstStartApp) {
    }

    @Override
    public void onBack() {
    }

    @Override
    public void onActivityResumed(Activity activity) {
        mCurrentActivity = activity;
    }


    /**
     * 显示View
     *
     * @param view 需要显示到Activity的视图
     */
    public void showView(View view) {
        /*Activity不为空并且没有被释放掉*/
        if (this.mCurrentActivity != null && !this.mCurrentActivity.isFinishing()) {
            /*获取Activity顶层视图,并添加自定义View*/
            mCurrentActivity.runOnUiThread(() -> {
                ViewGroup root = ((ViewGroup) this.mCurrentActivity.getWindow().getDecorView());
                if (floatViewList.size() > 0) {
                    for (int i = 0; i < floatViewList.size(); i++) {
                        View floatView = floatViewList.get(i);
                        root.removeView(floatView);
                    }
                }
                
                root.addView(view);
                floatViewList.add(view);

                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = Utils.dp2Px(BaseApplication.getCurrentContext(), 100);
                view.setLayoutParams(layoutParams);
            });
        }
    }

    /**
     * 隐藏View
     *
     * @param view 需要从Activity中移除的视图
     */
    public void hideView(View view) {
        /*Activity不为空并且没有被释放掉*/
//        if (this.mCurrentActivity != null && !this.mCurrentActivity.isFinishing()) {
        if (this.mCurrentActivity != null) {
            /*获取Activity顶层视图*/
            ViewGroup root = ((ViewGroup) this.mCurrentActivity.getWindow().getDecorView());
            /*如果Activity中存在View对象则删除*/
            if (root.indexOfChild(view) != -1) {
                /*从顶层视图中删除*/
                root.removeView(view);
                floatViewList.remove(view);
            }
        }
    }

    public static HttpProxyCacheServer getProxy(Context context){
        BaseApplication app = (BaseApplication) context.getApplicationContext();
        return app.porxy == null?(app.porxy = app.newProxy()):app.porxy;
    }

    private HttpProxyCacheServer newProxy(){
        return new HttpProxyCacheServer(this);
    }

    /**
     * 初始化融云
     * */
    public boolean isPrivacyAccepted = false;
    //需要在接受隐私协议的时候调用
    public void initRongYun(){
        //开启单进程
        RongCoreClient.getInstance().enableSingleProcess(true);
        //伪代码，从 sp 里读取用户是否已接受隐私协议
//        boolean isPrivacyAccepted = getPrivacyStateFromSp();
        //用户已接受隐私协议，进行初始化
        if (isPrivacyAccepted) {
            String appKey = "融云开发者后台创建的应用的 AppKey";
            //第一个参数必须传应用上下文
            RongIMClient.init(this.getApplicationContext(), appKey);
        } else {
            //用户未接受隐私协议，跳转到隐私授权页面。
//            goToPrivacyActivity();
        }
    }

}
