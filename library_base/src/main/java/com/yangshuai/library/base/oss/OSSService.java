package com.yangshuai.library.base.oss;

import com.yangshuai.library.base.entity.BaseResponse;
import com.yangshuai.library.base.entity.NoBodyResponse;
import com.yangshuai.library.base.entity.OssCallbackRequest;
import com.yangshuai.library.base.entity.OssCallbackResponse;
import com.yangshuai.library.base.entity.OssSignature;
import com.yangshuai.library.base.entity.OssUploadTickerRequest;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * @Author hcp
 * @Created 5/30/19
 * @Editor hcp
 * @Edited 5/30/19
 * @Type
 * @Layout
 * @Api
 */
public interface OSSService {
    public static final String REFERER_PHOTO = "app-android.xxxxxx.com";
    public static final String REFERER_VIDEO = "app-android-video.xxxxxx.com";
    public static final String REFERER_FILE = "app-android-file.xxxxxx.com";

//    /**
//     * 获取上传图片票据
//     *
//     * @param userDir 远程文件夹
//     * @return
//     */
//    @POST("/common.core.api/common/oss/ticker")
//    Observable<BaseResponse<OssSignature>> getOssSignature(@Query("userDir") String userDir);


    /**
     * 获取上传图片票据 不带水印
     *        userDir	需要获取存储目录, 目录结构统一规范，{项目名}/{模块名}/{业务线}/{房|客|合同}id/	body	TRUE	string
     *        applyId	oss bucket选择 0:普通 1:VR,不传默认为0	body	FALSE	integer(int64)
     *        uploadSize	需要上传的文件数量 默认1	body	FALSE	integer(int32)
     * @return
     */
    @POST("/common.core.api/common/oss/ticker")
    Observable<BaseResponse<OssSignature>> getOssSignatureALL(@Body OssUploadTickerRequest request);
//    Observable<BaseResponse<OssSignature>> getOssSignatureNotWater(@Query("userDir") String userDir,@Query("applyId") String applyId);

    /**
     * 请求oss上传成功后的回调
     *
     * @param request
     * @return
     */
    @POST("/common.core.api/common/oss/callback")
    Observable<BaseResponse<List<OssCallbackResponse>>> postOssCallback(@Body OssCallbackRequest request);


    @Headers({
            "encoding:true"
    })
    @POST
    Observable<Response<NoBodyResponse>> upload(@Url String url, @Body RequestBody request);

    @Streaming
//    @Headers("Referer: app-android-video.yjyz.com")
    @GET
    Observable<ResponseBody> download(@Header("Range") String range, @Url String url, @Header("Referer") String lang);

//    @Headers({
//            "encoding:true"
//    })
//    @POST
//    Observable<ResponseBody> downloadPost(@Header("Range") String range, @Url String url, @Header("Referer") String lang);
}
