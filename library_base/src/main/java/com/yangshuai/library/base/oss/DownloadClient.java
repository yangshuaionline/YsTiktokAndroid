package com.yangshuai.library.base.oss;

import androidx.annotation.NonNull;

import com.yangshuai.library.base.entity.DownloadTask;
import com.yangshuai.library.base.network.RetrofitManager;
import com.yangshuai.library.base.utils.RxUtils;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;


import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;

/**
 * @Author hcp
 * @Created 5/31/19
 * @Editor hcp
 * @Edited 5/31/19
 * @Type
 * @Layout
 * @Api
 */
public class DownloadClient {

    private DownloadTask task;
    private DisposableProxy downloadDisposable;

    // 下载监听
    private DownloadListener listener;
    // 下载的文件
    private File file;

    private long progress;

    public DownloadClient(@NonNull DownloadTask downloadTask) {
        task = downloadTask;
        file = task.getOutPutFile();
    }

//    public void download(String str_referer) {
//        caculateDownloadRange()
//                .flatMap((Function<String, ObservableSource<ResponseBody>>) range -> RetrofitManager.getOSSService().download(range, task.getUrl(),str_referer))
//                .flatMap((Function<ResponseBody, ObservableSource<Long>>) this::downloadToDisk)
//                .compose(RxUtils.schedulersTransformer())
//                .subscribe(new Observer<Long>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        downloadDisposable = new DisposableProxy(d);
//                        if (listener != null) listener.onStart(downloadDisposable);
//                    }
//
//                    @Override
//                    public void onNext(Long downloadedBytes) {
//                        if (listener != null)
//                            listener.onProgress(downloadedBytes, task.getTotalSize());
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        if (listener != null) listener.onError("500", e.getMessage());
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        if (listener != null) listener.onComplete(file);
//                    }
//                });
//    }
    public void downloadPost(String str_referer) {
        caculateDownloadRange()
                .flatMap((Function<String, ObservableSource<ResponseBody>>) range -> RetrofitManager.getOSSService().download(range, task.getUrl(),str_referer))
                .flatMap((Function<ResponseBody, ObservableSource<Long>>) this::downloadToDisk)
                .compose(RxUtils.schedulersTransformer())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        downloadDisposable = new DisposableProxy(d);
                        if (listener != null) listener.onStart(downloadDisposable);
                    }

                    @Override
                    public void onNext(Long downloadedBytes) {
                        if (listener != null)
                            listener.onProgress(downloadedBytes, task.getTotalSize());
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (listener != null) listener.onError("500", e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        if (listener != null) listener.onComplete(file);
                    }
                });
    }
//    public void downloadPost() {
//        caculateDownloadRange()
//                .flatMap((Function<String, ObservableSource<ResponseBody>>) range -> RetrofitManager.getOSSService().downloadPost(range, task.getUrl()))
//                .flatMap((Function<ResponseBody, ObservableSource<Long>>) this::downloadToDisk)
//                .compose(RxUtils.schedulersTransformer())
//                .subscribe(new Observer<Long>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        downloadDisposable = new DisposableProxy(d);
//                        if (listener != null) listener.onStart(downloadDisposable);
//                    }
//
//                    @Override
//                    public void onNext(Long downloadedBytes) {
//                        if (listener != null)
//                            listener.onProgress(downloadedBytes, task.getTotalSize());
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        if (listener != null) listener.onError("500", e.getMessage());
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        if (listener != null) listener.onComplete(file);
//                    }
//                });
//    }

    private Observable<String> caculateDownloadRange() {
        return Observable.create(emitter -> {

            if (file.exists()) {
                progress = file.length();
                // 进度大于总量说明发生写入错误
                if (progress > task.getTotalSize()) {
                    task.setTotalSize(0);
                    progress = 0;
                    file.delete();
                }
            }

            String range = "bytes=" + progress + "-";
            emitter.onNext(range);
            emitter.onComplete();
        });
    }

    private Observable<Long> downloadToDisk(ResponseBody responseBody) {

        return Observable.create(emitter -> {


            InputStream inputStream = null;
            RandomAccessFile accessFile = null;
            try {

                // 获取下载文件的大小并记录
                long totalBytes = responseBody.contentLength();
                if (task.getTotalSize() == 0) {
                    task.setTotalSize(totalBytes);
                    // 更新任务状态
                    task.saveWithFileName();
                }

                inputStream = responseBody.byteStream();

                // rwd权限在文件不存在时会自动创建，并在文件写入时实时更新到存储中
                accessFile = new RandomAccessFile(file, "rwd");

                // 跳转到上次下载的地方
                accessFile.seek(progress);

                byte[] buf = new byte[4 * 1024];
                int len;
                while (!downloadDisposable.isDisposed() && (len = inputStream.read(buf)) != -1) {
                    accessFile.write(buf, 0, len);
                    progress += len;
                    if (!downloadDisposable.isDisposed())
                        emitter.onNext(progress);
                }

                if (!downloadDisposable.isDisposed()) {
                    emitter.onComplete();
                }

            } catch (Exception e) {
                if (!downloadDisposable.isDisposed())
                    emitter.onError(e);
            } finally {
                try {
                    if (accessFile != null) {
                        accessFile.close();
                    }
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 停止下载
     */
    public void stopDownload() {
        if (downloadDisposable != null && !downloadDisposable.isDisposed())
            downloadDisposable.dispose();
    }

    public void setListener(DownloadListener listener) {
        this.listener = listener;
    }

    public interface DownloadListener {
        void onStart(Disposable disposable);

        void onProgress(long downloadedBytes, long totalBytes);

        void onError(String code, String msg);

        void onComplete(File file);
    }

    public static class DisposableProxy implements Disposable{

        private Disposable disposable;
        private volatile boolean stop;

        DisposableProxy(Disposable disposable) {
            this.disposable = disposable;
            this.stop = false;
        }

        @Override
        public void dispose() {
            disposable.dispose();
            stop = true;
        }

        @Override
        public boolean isDisposed() {
            return disposable.isDisposed() && stop;
        }
    }
}
