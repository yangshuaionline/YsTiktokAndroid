package com.yangshuai.library.base.oss;

import androidx.annotation.NonNull;

import com.squareup.okhttp.internal.Util;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

/**
 * 自定义文件请求体，监听上传进度
 *
 * @Author hcp
 * @Created 5/23/19
 * @Editor hcp
 * @Edited 5/23/19
 * @Type
 * @Layout
 * @Api
 */
public class FileRequestBody extends RequestBody {

    private static final int BUFFER_SIZE = 4 * 1024;

    private File file;
    private MediaType mediaType;
    private ProgressListener listener;

    public FileRequestBody(MediaType mediaType, @NonNull File file, @NonNull ProgressListener listener) {
        this.file = file;
        this.mediaType = mediaType;
        this.listener = listener;
    }

    @Override
    public long contentLength() throws IOException {
        return file.length();
    }

    @Override
    public MediaType contentType() {
        return mediaType;
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        Source source = null;
        try {
            source = Okio.source(file);
            long read;

            while ((read = source.read(sink.buffer(), BUFFER_SIZE)) != -1) {
                sink.flush();
                listener.onProgress(read);
            }
        } finally {
            Util.closeQuietly(source);
        }
    }

    public interface ProgressListener {
        void onProgress(long writeBytes);
    }

    public static FileRequestBody create(MediaType mediaType, @NonNull File file, @NonNull ProgressListener listener) {
        return new FileRequestBody(mediaType, file, listener);
    }
}
