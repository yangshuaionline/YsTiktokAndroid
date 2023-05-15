package com.yangshuai.library.base.network;

import com.yangshuai.library.base.account.AgentInfo;
import com.yangshuai.library.base.account.UserInfo;
import com.yangshuai.library.base.appinfo.AppInfo;
import com.yangshuai.library.base.entity.AnnounceBean;
import com.yangshuai.library.base.entity.AppInfoDtoRequest;
import com.yangshuai.library.base.entity.BaseResponse;
import com.yangshuai.library.base.entity.DictionaryBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 接口名称，基本的URL地址
 *
 * @author hcp
 * @date 2019/03/14
 */

public interface ApiServer {

    /**
     * 获取楼盘字典
     *
     * @return
     */
    @POST("/erp.settings.api/sys/dictgroup/all")
    Observable<BaseResponse<List<DictionaryBean>>> getDictionary();

    /**
     * 获取用户信息
     *
     * @return
     */
    @POST("/erp.settings.api/common/userInfo")
    Observable<BaseResponse<UserInfo>> getUserInfo();


    /**
     * 获取经纪人信息
     * @return
     */
    @POST("/erp.settings.api/agent/current")
    Observable<BaseResponse<AgentInfo>> getAgentInfo();

//    /**
//     * 获取最新版本app信息
//     * 新接口：https://api-beta.yjyz.com/ops.web.api/api/v1/app/getNewestByPackageName?packageName=uc&type=2&version=1
//     * @return
//     */
//    @  GET("/ops.web.api/api/v1/app/getNewestByPackageName?type=2")
//    Observable<BaseResponse<AppInfo>> getAppInfo(@Query("version") String version,@Query("packageName") String packageName);

    /**
     * 获取最新版本app信息
     * 新接口：https://api-beta.yjyz.com/ops.web.api/api/v1/app/getNewestByPackageName?packageName=uc&type=2&version=1
     * @return
     */
    @POST("/ops.web.api/api/v1/app/getNewestByPackageNamePost")
    Observable<BaseResponse<AppInfo>> getAppInfoPost(@Body AppInfoDtoRequest request);

    /**
     * 获取 公告数据
     * @return
     */
    @POST("/erp.settings.api/notice/checkForce")
    Observable<BaseResponse<AnnounceBean>> getAnnounceData();
}
