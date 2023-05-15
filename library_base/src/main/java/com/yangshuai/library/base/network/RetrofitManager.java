package com.yangshuai.library.base.network;

import com.yangshuai.library.base.BuildConfig;
import com.yangshuai.library.base.config.AppBaseConfig;
import com.yangshuai.library.base.network.converter.CollegeGsonConverterFactory;
import com.yangshuai.library.base.network.converter.FormConverterFactory;
import com.yangshuai.library.base.network.converter.GsonConverterFactory;
import com.yangshuai.library.base.network.converter.NobodyConverterFactory;
import com.yangshuai.library.base.network.interceptor.DynamicTimeoutInterceptor;
import com.yangshuai.library.base.network.interceptor.HttpLoggingInterceptor2;
import com.yangshuai.library.base.network.interceptor.NetworkInterceptor;
import com.yangshuai.library.base.network.interceptor.TokenInterceptor;
import com.yangshuai.library.base.network.ssl.SSLFactory;
import com.yangshuai.library.base.oss.OSSService;

import java.net.Proxy;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * 网络框架管理类,在Appaction中初始化
 *
 * @author hcp
 * @date 2019/03/14
 */
public class RetrofitManager {
    private static Retrofit retrofitForm;

    private static RetrofitManager mInstance;
    private static Retrofit retrofit;
    private static Retrofit ossRetrofit;
    private static Retrofit collegeRetrofit;
    private static volatile ApiServer request = null;

    private String host;

    public String getHost() {
        return host;
    }

    public static RetrofitManager getInstance() {
        if (mInstance == null) {
            synchronized (RetrofitManager.class) {
                if (mInstance == null) {
                    mInstance = new RetrofitManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 初始化必要对象和参数
     */
    public void init(String host, boolean canProxy) {

        this.host = host;

        // 初始化Retrofit
        retrofit = new Retrofit.Builder()
                .client(getOkhttpClient(canProxy))
                .baseUrl(host)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(NobodyConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // retrofitForm
        retrofitForm = new Retrofit.Builder()
                .client(getOkhttpClient(canProxy))
                .baseUrl(host)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(NobodyConverterFactory.create())
                .addConverterFactory(FormConverterFactory.create())
                .build();
        configOssClient(host, canProxy);
        configCollegeClient(canProxy);
    }

    /**
     * 配置oss上传文件的网络请求
     */
    private void configOssClient(String host, boolean canProxy) {
        OkHttpClient.Builder ossBuilder = getOkHttpBuilder(canProxy)
                .connectTimeout(15, TimeUnit.MINUTES)
                .readTimeout(15, TimeUnit.MINUTES)
                .writeTimeout(15, TimeUnit.MINUTES);
        ossRetrofit = new Retrofit.Builder()
                .client(ossBuilder.build())
                .baseUrl(host)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(NobodyConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    /**
     * 配置优居学院的网络请求
     */
    private void configCollegeClient(boolean canProxy) {
        OkHttpClient.Builder collegeBuilder = getOkHttpBuilder(canProxy)
                .connectTimeout(8, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.MINUTES)
                .writeTimeout(15, TimeUnit.MINUTES);
        collegeRetrofit = new Retrofit.Builder()
                .client(collegeBuilder.build())
                .baseUrl(AppBaseConfig.get().getConfig().getBaseUrl())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(NobodyConverterFactory.create())
                .addConverterFactory(CollegeGsonConverterFactory.create())
                .build();
    }


    /**
     * 获取oss服务
     *
     * @return
     */
    public static OSSService getOSSService() {
        return ossRetrofit.create(OSSService.class);
    }



    public static ApiServer getRequest() {
        if (request == null) {
            synchronized (ApiServer.class) {
                request = retrofit.create(ApiServer.class);
            }
        }
        return request;
    }


    /**
     * 构建指定的API服务
     *
     * @param serviceClass
     * @param <T>
     * @return
     */
    public static <T> T create(Class<T> serviceClass) {
        return retrofit.create(serviceClass);
    }
    /**
     * 构建指定的API服务
     *
     * @param serviceClass
     * @param <T>
     * @return
     */
    public static <T> T createForm(Class<T> serviceClass) {
        return retrofitForm.create(serviceClass);
    }
    /**
     * 构建指定优居学院的API服务
     *
     * @param serviceClass
     * @param <T>
     * @return
     */
    public static <T> T createCollege(Class<T> serviceClass) {
        return collegeRetrofit.create(serviceClass);
    }

    /**
     * 配置okhttp
     */
    public static OkHttpClient getOkhttpClient(boolean canProxy) {
        // 初始化okhttp
        return getOkHttpBuilder(canProxy).build();

    }

    /**
     * 获取okhttp配置类
     *
     * @return
     */
    public static OkHttpClient.Builder getOkHttpBuilder(boolean canProxy) {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(2, TimeUnit.MINUTES)//原来10秒，暂时扩大为2分钟
                .writeTimeout(2, TimeUnit.MINUTES)//原来10秒，暂时扩大为2分钟
                .retryOnConnectionFailure(true)//错误重联
                .dns(new ApiDns());
        // 动态token
        httpClientBuilder
                .addInterceptor(new DynamicTimeoutInterceptor())
                .addInterceptor(new TokenInterceptor());

        if (BuildConfig.DEBUG) {
            // set your desired log level
            HttpLoggingInterceptor2 logging = new HttpLoggingInterceptor2();
            logging.setLevel(HttpLoggingInterceptor2.Level.BODY);
            httpClientBuilder.addNetworkInterceptor(logging);
        }

        // 只针对线上环境做代理限制
        if (!canProxy && !AppBaseConfig.get().getConfig().isDebug()) {
            httpClientBuilder.proxy(Proxy.NO_PROXY); // 禁止使用代理网络
        }


        // 配置ssl
        SSLFactory.SSLParams sslParams = SSLFactory.getSslSocketFactory();
        httpClientBuilder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);

        // 校验域名
        httpClientBuilder.hostnameVerifier((hostname, session) -> true);


        // 处理无网络异常
        httpClientBuilder.addInterceptor(new NetworkInterceptor());

        return httpClientBuilder;
    }


}
