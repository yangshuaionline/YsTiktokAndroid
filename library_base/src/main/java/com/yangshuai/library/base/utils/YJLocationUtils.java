package com.yangshuai.library.base.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.Settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yangshuai.library.base.dialog.AlertDialog;
import com.yangshuai.library.base.event.LocationEvent;
import com.yangshuai.library.base.exception.ApiException;
import com.yangshuai.library.base.interfaces.OnLocationListener;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author hcp
 * @create 2020-5-14
 * @Describe 百度地图获取定位工具，内部实现了生命周期的监听，防止内存泄漏，自动回收对象。
 */
public class YJLocationUtils implements LifecycleObserver {

    // Activity
    private AppCompatActivity mActivity;

    // 当前实例
    private static YJLocationUtils mInstance;

    // 百度定位
    private LocationClient mLocationClient;

    // 定位间隔
    private int scanSpan = 5000;

    // 定位监听器
    private OnLocationListener listener;

    // 权限检查监听者
    private VerifyPermissionsObservable observable;

    // 定位次数
    private int locationNumber = 1;

    // 是跳转了系统设置
    private boolean goSystemSet;

    public static YJLocationUtils getInstance(AppCompatActivity activity) {
        if (mInstance == null) {
            mInstance = new YJLocationUtils(activity);
        }
        return mInstance;
    }

    private YJLocationUtils(AppCompatActivity activity) {
        this.mActivity = activity;

        // Lifecycle监听宿主的生命周期
        mActivity.getLifecycle().addObserver(this);

        init();

    }

    private void init() {
        mLocationClient = new LocationClient(mActivity);

        // 通过LocationClientOption设置LocationClient相关参数
        LocationClientOption option = new LocationClientOption();
        // 打开gps
        option.setOpenGps(true);
        option.setCoorType("bd09ll");
        //定位时间间隔，默认5秒定位一次
        option.setScanSpan(scanSpan);
        // 定位精度
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);

        // 设置locationClientOption
        mLocationClient.setLocOption(option);

        // 定位监听器回调
        mLocationClient.registerLocationListener(new BDAbstractLocationListener() {

            private int number = 1;

            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                // 定位信息获取失败
                if (listener == null) {
                    listener.onFailed("定位信息获取失败");
                    return;
                }

                // 百度地图获取定位失败的一个标识符，好像是没开启定位服务
                if (4.9E-324 == bdLocation.getLongitude() || 4.9E-324 == bdLocation.getLatitude()) {
                    // 停止服务
                    stopLocate();
                    // 开启定位服务
                    enableLocationService();
                    return;
                }

                // 定位完成
                listener.onSuccess(bdLocation);
                //发送定位通知
                LocationEvent event = new LocationEvent();
                event.setLatitude("" + bdLocation.getLatitude());
                event.setLongitude("" + bdLocation.getLongitude());
                EventBus.getDefault().postSticky(event);

                if (number >= locationNumber) {
                    onDestroy();
                }

                // 定位计数
                number++;
            }
        });
    }

    /**
     * 开始定位
     */
    public void startLocate() {
        if (observable == null) {
            observable = new VerifyPermissionsObservable();
        }

        Observable.create(observable)
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object bdLocation) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        // 停止定位
                        YJLocationUtils.this.stopLocate();

                        if (listener == null) return;

                        if (e instanceof ApiException) {
                            ApiException exception = (ApiException) e;

                            // 没有开启定位服务
                            if (exception.getCode().equals(YJLocationCode.NO_LOCATION_SERVICE)) {
                                // 开启定位服务
                                enableLocationService();
                                return;
                            }

                            // 没有权限
                            if (exception.getCode().equals(YJLocationCode.NO_LOCATION_PERMISSIONS)) {
                                // 手动获取定位权限
                                manualGetLocationPermissions();
                                return;
                            }
                            listener.onFailed(exception.getMsg());
                        } else {
                            listener.onFailed(e.getMessage());
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (mLocationClient == null) return;

                        // 开始定位
                        mLocationClient.start();
                    }
                });

    }

    /**
     * 停止定位，如果需要手动停止定位可以外部调用，不必要的话已自动处理了。
     */
    public void stopLocate() {
        if (mLocationClient == null) return;

        // 停止定位
        mLocationClient.stop();

    }

    /**
     * 获取定位前的权限判断
     */
    private class VerifyPermissionsObservable implements ObservableOnSubscribe<Object> {

        @SuppressLint("CheckResult")
        @Override
        public void subscribe(ObservableEmitter emitter) throws Exception {
            // 检查网络状态
            if (!Utils.checkNetWork(mActivity)) {
                throw new ApiException(YJLocationCode.NO_NETWORK,
                        "检测到您手机未连接网络，请检查您的网络设置。");
            }

            // 检查定位服务是否开启
            if (!Utils.checkLocationEnabled(mActivity)) {
                throw new ApiException(YJLocationCode.NO_LOCATION_SERVICE,
                        "检测到您手机未开启定位服务，请检查您的定位服务设置。");
            }

            // 检查定位权限
            if (ActivityCompat
                    .checkSelfPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                RxPermissions permissions = new RxPermissions((FragmentActivity) mActivity);
                permissions.request(Manifest.permission.ACCESS_FINE_LOCATION)
                        .subscribe(permission -> {
                            if (permission) {
                                // 权限判断完成
                                emitter.onComplete();
                            } else {
                                emitter.onError(new ApiException(YJLocationCode.NO_LOCATION_PERMISSIONS,
                                        "检测到您手机未开启定位权限，请检查您的定位权限设置。"));
                            }
                        });
            } else {
                emitter.onComplete();
            }
        }
    }

    /**
     * 手动获取定位权限
     */
    private void manualGetLocationPermissions() {
        AlertDialog alertDialog = new AlertDialog(mActivity);
        alertDialog.setTitle("提示");
        alertDialog.setMessage("获取权限失败，请到设置中开启定位权限");
        alertDialog.setCancelable(false);

        // 取消按钮
        alertDialog.setLeftButton("取消", v -> {
            if (listener != null) {
                listener.onFailed("获取定位权限失败");
            }
            alertDialog.dismiss();
        });

        // 去设置按钮
        alertDialog.setRightButton("去设置", v1 -> {
            Utils.toAndroidSetting(mActivity);

            // 记录去系统设置
            goSystemSet = true;

            alertDialog.dismiss();
        });

        // 显示弹框
        if (!mActivity.isFinishing()) {
            alertDialog.show();
        }

    }

    /**
     * 开启定位服务弹窗
     */
    private void enableLocationService() {
        AlertDialog alertDialog = new AlertDialog(mActivity);
        alertDialog.setTitle("提示");
        alertDialog.setMessage("检测到您手机未开启定位服务，前往开启定位服务？");
        alertDialog.setCancelable(false);

        // 取消按钮
        alertDialog.setLeftButton("取消", v -> {
            if (listener != null) {
                listener.onFailed("开启定位服务失败");
            }
            alertDialog.dismiss();
        });

        // 确定按钮
        alertDialog.setRightButton("确定", v1 -> {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            mActivity.startActivity(intent);

            // 记录去系统设置
            goSystemSet = true;

            alertDialog.dismiss();
        });

        // 显示弹框
        if (!mActivity.isFinishing()) {
            alertDialog.show();
        }

    }

    /**
     * 设置扫描间隔，在多次扫描的基础上
     *
     * @param scanSpan
     * @return
     */
    public YJLocationUtils setScanSpan(int scanSpan) {
        this.scanSpan = scanSpan;
        if (mLocationClient != null) {
            LocationClientOption locOption = mLocationClient.getLocOption();
            locOption.setScanSpan(scanSpan);
            // 设置locationClientOption
            mLocationClient.setLocOption(locOption);
        }
        return mInstance;
    }

    /**
     * 定位的次数
     *
     * @param locationNumber
     */
    public YJLocationUtils setLocationNumber(int locationNumber) {
        this.locationNumber = locationNumber;
        return mInstance;
    }

    /**
     * 定位的回调
     *
     * @param listener
     */
    public YJLocationUtils setListener(OnLocationListener listener) {
        this.listener = listener;
        return mInstance;
    }

    /**
     * 因为无法监听到用户跳转到系统设置后的操作，
     * 所以在这里判断下如果跳转到系统设置页面，
     * 且返回重新显示页面，递归获取定位数据。
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    protected void onRestart() {
        if (!goSystemSet) return;

        // 重置跳转设置页面状态
        goSystemSet = false;

        // 重新定位
        startLocate();

    }

    /**
     * 销毁事件
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    protected void onDestroy() {
        // 停止服务
        stopLocate();
        // 销毁当前实例
        mInstance = null;
        mActivity.getLifecycle().removeObserver(this);
    }

    public interface YJLocationCode {
        // 无网络
        String NO_NETWORK = "Y1000";

        // 无定位服务
        String NO_LOCATION_SERVICE = "Y1001";

        // 定位权限
        String NO_LOCATION_PERMISSIONS = "Y1002";

    }

}
