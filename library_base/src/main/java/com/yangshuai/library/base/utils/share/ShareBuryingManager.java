package com.yangshuai.library.base.utils.share;

import android.text.TextUtils;

import com.yangshuai.library.base.api.BaseApi;
import com.yangshuai.library.base.network.RetrofitManager;
import com.yangshuai.library.base.observer.ResponseObserver;
import com.yangshuai.library.base.utils.KLog;
import com.yangshuai.library.base.utils.RxUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

/**
 * 分享埋点
 *
 * @author hcp
 * @date 2020/8/5
 */
public class ShareBuryingManager {
    /**
     * 分享埋点事件处理
     */
    public Observable<ShareBuilder> getShareBurying(ShareBuilder shareBuilder) {
        return Observable.just(shareBuilder)
                .map(shareBuilder1 -> {
                    ShareContent shareContent = shareBuilder1.getShareContent();
                    pushBuryingEvent(shareContent);
                    return shareBuilder1;
                });
    }


    /**
     * 分享埋点事件上报
     */
    private void pushBuryingEvent(ShareContent shareContent) {
        if (shareContent.getCategory() == ShareContent.SHARE_SOURCE_TYPE.OTHER_SHARE) {
            return;
        }
        if (TextUtils.isEmpty(shareContent.getTraceType())) {
            shareContent.setTraceType("150000");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("traceType", shareContent.getTraceType());//线索类型
        map.put("source", shareContent.getShareBuryingPlatform());
        map.put("category", shareContent.getCategory());
//        map.put("agentId", AccountManager.getUserInfo().getEmployeesId());//经纪人ID
//        map.put("cityCode", AccountManager.getCityCode());//城市编码
        if (shareContent.getBuryingParams() != null) {
            map.putAll(shareContent.getBuryingParams());
        }
        RetrofitManager.create(BaseApi.class)
                .shareClick(map)
                .compose(RxUtils.responseTransformer())
                .compose(RxUtils.schedulersTransformer())
                .subscribe(new ResponseObserver<Object>() {
                    @Override
                    public void onSuccess(Object data) {
                        KLog.i("onSuccess" + data);
                    }

                    @Override
                    public void onError(String code, String msg) {
                        KLog.i("onError" + code + " msg " + msg);
                    }
                });
    }
}
