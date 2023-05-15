package com.yangshuai.library.base.oss;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.yangshuai.library.base.entity.BaseResponse;
import com.yangshuai.library.base.entity.OssCallbackRequest;
import com.yangshuai.library.base.entity.OssCallbackResponse;
import com.yangshuai.library.base.entity.OssSignature;
import com.yangshuai.library.base.entity.OssUploadTickerRequest;
import com.yangshuai.library.base.entity.Picture;
import com.yangshuai.library.base.luban.Luban;
import com.yangshuai.library.base.network.RetrofitManager;
import com.yangshuai.library.base.utils.AppContext;
import com.yangshuai.library.base.utils.RxUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

/**
 * OSS服务
 *
 * @Author hcp
 * @Created 4/28/19
 * @Editor hcp
 * @Edited 4/28/19
 * @Type
 * @Layout
 * @Api
 */
public class OSSClient {

    // 本地图片地址
    private List<String> paths;
    // 远程存储文件夹
    private String remoteDir;
    // 上传成功后返回的图片信息
    private List<Picture> pictures;
    // 上传监听
    private UploadListener uploadListener;
    private LiveProgressLisenter liveProgressLisenter;
    private Disposable disposable;

    // 文件类型标识
    private String type;
    //oss bucket选择 0:普通 1:VR,不传默认为0   // 0 有水印。1 无水印???
    private String applyId = "0";

    // 总文件大小
    private long totalSize = 0;
    // 已上传文件大小
    private long uploadedSize;

    // 处理进度回调
    private Handler progressHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            Bundle data = msg.getData();
            long writeBytes = data.getLong("writeBytes");
            uploadedSize += writeBytes;
            if (totalSize != 0) {
                if (uploadListener != null) {
                    float percent = uploadedSize * 100.f / totalSize;
                    uploadListener.onProgress((int) percent, 100);
                }

                if (liveProgressLisenter != null) {
                    liveProgressLisenter.onProgress(uploadedSize, totalSize);
                }
            }
        }
    };

    public OSSClient(List<String> paths, String remoteDir) {
        this.paths = new ArrayList<>(paths);
        this.remoteDir = remoteDir;
    }

    /**
     * 设置上传文件的类型标识，具体用途交给调用者
     *
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 是否需要水印 applyId = 1
     *
     * @param applyId
     */
    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    /**
     * 设置上传回调
     *
     * @param uploadListener
     */
    public void setUploadListener(UploadListener uploadListener) {
        this.uploadListener = uploadListener;
    }

    /**
     * 监听实时上传的进度
     *
     * @param liveProgressLisenter
     */
    public void setLiveProgressLisenter(LiveProgressLisenter liveProgressLisenter) {
        this.liveProgressLisenter = liveProgressLisenter;
    }

    /**
     * 上传图片
     *
     * @param image     图片
     * @param remoteDir 图片存放的远程文件夹路径
     * @return 返回图片信息
     */
    private Observable<Picture> upload(String image, String remoteDir) {

        // 网络图片直接返回
        if (isHttpUrl(image)) {
            Picture picture = new Picture();
            picture.setUrl(image);
            picture.setType(type);
            return Observable.just(picture);
        }

        // 获取文件类型
        String mediaType = OSSMediaType.getTypeByFile(image);
        if (!OSSMediaType.isSupportType(mediaType)) {
            return Observable.error(new OssException(image, "不支持的文件类型：" + mediaType));
        }

        File file = new File(image);
        String suffix = OSSMediaType.getFileSuffix(file);
//        // 获取OSS签名
//        Observable<BaseResponse<OssSignature>> observableSignature = ("1".equals(applyId)?
//                RetrofitManager.getOSSService().getOssSignatureNotWater(remoteDir,applyId):
//                RetrofitManager.getOSSService().getOssSignature(remoteDir));


        OssUploadTickerRequest requestUploadTicker = new OssUploadTickerRequest();
        requestUploadTicker.setUserDir(remoteDir);
        requestUploadTicker.setApplyId(applyId);
        requestUploadTicker.setUploadSize(1);//目前文件一个，所以暂时固定传1，否则出错
        // 获取OSS签名
        Observable<BaseResponse<OssSignature>> observableSignature =
                RetrofitManager.getOSSService().getOssSignatureALL(requestUploadTicker);
        return observableSignature
                .compose(RxUtils.responseTransformer())
                // 上传oss
                .flatMap((Function<OssSignature, ObservableSource<OssSignature>>) ossSignature -> {

                    String key = remoteDir + "/" + ossSignature.getKeys().get(0) + "." + suffix;
                    FileRequestBody fileRequestBody = FileRequestBody.create(MediaType.parse(mediaType), file, createProgreListener());
                    RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                            // 图片唯一标示
                            .addFormDataPart("key", key)
                            .addFormDataPart("Signature", ossSignature.getSignature())
                            .addFormDataPart("policy", ossSignature.getPolicy())
                            .addFormDataPart("OSSAccessKeyId", ossSignature.getAccessId())
                            // 返回成功时的状态码
                            .addFormDataPart("success_action_status", "200")
                            // 图片： image/* 视频：video/* 音频：audio/*
                            .addFormDataPart("file", key, fileRequestBody)
                            .build();
                    // 携带签名上传图片到oss
                    return RetrofitManager.getOSSService().upload(ossSignature.getHost(), requestBody)
                            .flatMap((Function<Response, ObservableSource<OssSignature>>) response -> {
                                // 构造响应消息
                                if (response.code() == 200) {
                                    return Observable.just(ossSignature);
                                }
                                return Observable.error(new OssException(image, "文件上传失败"));
                            });
                })
                // 请求回调接口
                .flatMap((Function<OssSignature, ObservableSource<BaseResponse<List<OssCallbackResponse>>>>) ossSignature -> {
                    OssCallbackRequest request = new OssCallbackRequest();
                    OssCallbackRequest.UploadInfo uploadInfo = new OssCallbackRequest.UploadInfo();
                    uploadInfo.setKey(ossSignature.getKeys().get(0));
                    uploadInfo.setSuffix(suffix);
                    List<OssCallbackRequest.UploadInfo> list = new ArrayList<>();
                    list.add(uploadInfo);
                    request.setUpload(list);
                    request.setUserData(ossSignature.getUserData());
                    return RetrofitManager.getOSSService().postOssCallback(request);
                })
                .compose(RxUtils.responseTransformer())
                // 组装文件信息
                .flatMap((Function<List<OssCallbackResponse>, ObservableSource<Picture>>) responses -> {

                    if (responses != null && responses.size() > 0) {
                        Picture picture = new Picture();
                        picture.setSizeStr(file.length() + "");
                        if (OSSMediaType.IMAGE_TYPE.equals(mediaType)) {
                            picture.setUrl(responses.get(0).getUrl());
                        } else {
                            picture.setUrl(responses.get(0).getFullUrl());
                        }
                        picture.setType(type);
                        return Observable.just(picture);
                    }

                    return Observable.error(new OssException(image, "文件上传失败"));
                });
    }

    /**
     * 使用handler发送进度
     */
    private void postProgress(long writeBytes) {
        progressHandler.post(() -> {
            Message message = progressHandler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putLong("writeBytes", writeBytes);
            message.setData(bundle);
            progressHandler.sendMessage(message);
        });
    }

    /**
     * 创建文件上传进度监听器
     *
     * @return
     */
    private FileRequestBody.ProgressListener createProgreListener() {
        return this::postProgress;
    }

    /**
     * 上传多张图片，遍历上传
     */
    public void upload() {
        pictures = new ArrayList<>();
        uploadedSize = 0;
        compressFiles(paths)
                .flatMap((Function<List<String>, ObservableSource<List<String>>>) this::computeTotalSize)
                .flatMap((Function<List<String>, ObservableSource<Picture>>) files -> Observable.concat(genImagesObservable(files, remoteDir)))
                .compose(RxUtils.schedulersTransformer())
                .subscribe(new Observer<Picture>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                        if (uploadListener != null) {
                            uploadListener.onStart(d);
                        }
                    }

                    @Override
                    public void onNext(Picture picture) {
                        pictures.add(picture);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        if (uploadListener != null) {
                            uploadListener.onError(e);
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (uploadListener != null) {
                            uploadListener.onComplete(pictures);
                        }
                    }
                });

    }


    /**
     * 计算总文件大小
     *
     * @return
     */
    private Observable<List<String>> computeTotalSize(List<String> files) {

        if (totalSize == 0) {
            for (String path : files) {
                if (!isHttpUrl(path)) {
                    File file = new File(path);
                    totalSize += file.length();
                }
            }
        }


        return Observable.just(files);
    }

    /**
     * 压缩图片
     *
     * @param files
     * @return
     */
    private Observable<List<String>> compressFiles(List<String> files) {
        return Observable.create(emitter -> {
            List<String> compressFiles = new ArrayList<>();
            for (String file : files) {

                // 文件为图片是压缩图片
                if (!isHttpUrl(file) && OSSMediaType.IMAGE_TYPE.equals(OSSMediaType.getTypeByFile(file))) {
                    File compressFile = Luban.with(AppContext.getAppContext()).get(file);
                    compressFiles.add(compressFile.getAbsolutePath());
                } else {
                    compressFiles.add(file);
                }

            }
            emitter.onNext(compressFiles);
            emitter.onComplete();
        });
    }

    /**
     * 生成上传的被观察者
     *
     * @param images
     * @param remoteDir
     * @return
     */
    private List<Observable<Picture>> genImagesObservable(List<String> images, String remoteDir) {

        List<Observable<Picture>> list = new ArrayList<>();

        for (String image : images) {
            Observable<Picture> imgObservable = upload(image, remoteDir);

            list.add(imgObservable);
        }

        return list;
    }

    /**
     * 判断是否为http地址
     *
     * @param url
     * @return
     */
    private boolean isHttpUrl(String url) {
        return url != null && url.startsWith("http");
    }

    /**
     * 取消上传
     */
    private void cancel() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    public interface UploadListener {

        void onStart(Disposable disposable);

        void onComplete(List<Picture> pictures);

        void onProgress(int progress, int max);

        void onError(Throwable e);
    }

    /**
     * 需要监听原始进度时使用此监听器
     */
    public interface LiveProgressLisenter {
        /**
         * 传输单位为byte
         *
         * @param uploadedBytes 已上传大小
         * @param totalBytes    总大小
         */
        void onProgress(long uploadedBytes, long totalBytes);
    }

}
