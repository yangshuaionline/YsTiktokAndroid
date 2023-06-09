/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yangshuai.library.base.network.interceptor;

import com.yangshuai.library.base.utils.JsonformatJsonUtils;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;

import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;
import okio.BufferedSource;

import okhttp3.logging.HttpLoggingInterceptor.Logger;


/**
 * An OkHttp interceptor which logs request and response information. Can be applied as an
 * {@linkplain OkHttpClient#interceptors() application interceptor} or as a {@linkplain
 * OkHttpClient#networkInterceptors() network interceptor}. <p> The format of the logs created by
 * this class should not be considered stable and may change slightly between releases. If you need
 * a stable logging format, use your own interceptor.
 */
public final class HttpLoggingInterceptor2 implements Interceptor {
    private static final Charset UTF8 = Charset.forName("UTF-8");

    public enum Level {
        /**
         * No logs.
         */
        NONE,
        /**
         * Logs request and response lines.
         *
         * <p>Example:
         * <pre>{@code
         * --> POST /greeting http/1.1 (3-byte body)
         *
         * <-- 200 OK (22ms, 6-byte body)
         * }</pre>
         */
        BASIC,
        /**
         * Logs request and response lines and their respective headers.
         *
         * <p>Example:
         * <pre>{@code
         * --> POST /greeting http/1.1
         * Host: example.com
         * Content-Type: plain/text
         * Content-Length: 3
         * --> END POST
         *
         * <-- 200 OK (22ms)
         * Content-Type: plain/text
         * Content-Length: 6
         * <-- END HTTP
         * }</pre>
         */
        HEADERS,
        /**
         * Logs request and response lines and their respective headers and bodies (if present).
         *
         * <p>Example:
         * <pre>{@code
         * --> POST /greeting http/1.1
         * Host: example.com
         * Content-Type: plain/text
         * Content-Length: 3
         *
         * Hi?
         * --> END POST
         *
         * <-- 200 OK (22ms)
         * Content-Type: plain/text
         * Content-Length: 6
         *
         * Hello!
         * <-- END HTTP
         * }</pre>
         */
        BODY
    }


    public HttpLoggingInterceptor2() {
        this(Logger.DEFAULT);
    }

    public HttpLoggingInterceptor2(Logger logger) {
        this.logger = logger;
    }

    private final Logger logger;

    private volatile Level level = Level.NONE;

    /**
     * Change the level at which this interceptor logs.
     */
    public HttpLoggingInterceptor2 setLevel(Level level) {
        if (level == null) throw new NullPointerException("level == null. Use Level.NONE instead.");
        this.level = level;
        return this;
    }

    public Level getLevel() {
        return level;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Level level = this.level;
        StringBuffer requestMessages = new StringBuffer();
        StringBuffer responseMessages = new StringBuffer();

        Request request = chain.request();
        if (level == Level.NONE) {
            return chain.proceed(request);
        }

        boolean logBody = level == Level.BODY;
        boolean logHeaders = logBody || level == Level.HEADERS;

        RequestBody requestBody = request.body();
        boolean hasRequestBody = requestBody != null;

        Connection connection = chain.connection();
        Protocol protocol = connection != null ? connection.protocol() : Protocol.HTTP_1_1;
        String requestStartMessage = "--> " + request.method() + ' ' + request.url() + ' ' + protocol;
        if (!logHeaders && hasRequestBody) {
            requestStartMessage += " (" + requestBody.contentLength() + "-byte body)";
        }
        requestMessages.append(requestStartMessage + "\n");

        if (logHeaders) {
            if (hasRequestBody) {
                // Request body headers are only present when installed as a network interceptor. Force
                // them to be included (when available) so there values are known.
                if (requestBody.contentType() != null) {
                    requestMessages.append("Content-Type: " + requestBody.contentType() + "\n");
                }
                if (requestBody.contentLength() != -1) {
                    requestMessages.append("Content-Length: " + requestBody.contentLength() + "\n");
                }
            }

            Headers headers = request.headers();
            for (int i = 0, count = headers.size(); i < count; i++) {
                String name = headers.name(i);
                // Skip headers from the request body as they are explicitly logged above.
                if (!"Content-Type".equalsIgnoreCase(name) && !"Content-Length".equalsIgnoreCase(name)) {
                    requestMessages.append(name + ": " + headers.value(i) + "\n");
                }
            }

            if (!logBody || !hasRequestBody) {
                requestMessages.append("--> END " + request.method() + "\n");
            } else if (bodyEncoded(request.headers())) {
                requestMessages.append("--> END " + request.method() + " (encoded body omitted)\n");
            } else {
                Buffer buffer = new Buffer();
                requestBody.writeTo(buffer);

                Charset charset = UTF8;
                MediaType contentType = requestBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset(UTF8);
                }

                if (isPlaintext(buffer)) {
                    requestMessages.append("\n");
                    String body = buffer.readString(charset);
                    if ((body.startsWith("{") && body.endsWith("}"))
                            || (body.startsWith("[") && body.endsWith("]"))) {
                        String jsonBody = JsonformatJsonUtils.formatJson(JsonformatJsonUtils.decodeUnicode(body));
                        requestMessages.append(jsonBody + "\n");
                    } else {
                        requestMessages.append(body + "\n");
                    }
                    requestMessages.append("--> END " + request.method()
                            + " (" + requestBody.contentLength() + "-byte body)");
                } else {
                    requestMessages.append("--> END " + request.method() + " (binary "
                            + requestBody.contentLength() + "-byte body omitted)");
                }
            }
            logger.log(requestMessages.toString());
        }

        long startNs = System.nanoTime();
        Response response;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            responseMessages.append("<-- HTTP FAILED: " + e);
            logger.log(responseMessages.toString());
            throw e;
        }
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

        ResponseBody responseBody = response.body();
        long contentLength = responseBody.contentLength();
        String bodySize = contentLength != -1 ? contentLength + "-byte" : "unknown-length";
        responseMessages.append("<-- " + response.code() + ' ' + response.message() + ' '
                + response.request().url() + " (" + tookMs + "ms" + (!logHeaders ? ", "
                + bodySize + " body" : "") + ")\n");

        if (logHeaders) {
            Headers headers = response.headers();
            for (int i = 0, count = headers.size(); i < count; i++) {
                responseMessages.append(headers.name(i) + ": " + headers.value(i) + "\n");
            }

            if (!logBody || !HttpHeaders.hasBody(response)) {
                responseMessages.append("<-- END HTTP\n");
            } else if (bodyEncoded(response.headers())) {
                responseMessages.append("<-- END HTTP (encoded body omitted)\n");
            } else {
                BufferedSource source = responseBody.source();
                source.request(Long.MAX_VALUE); // Buffer the entire body.
                Buffer buffer = source.buffer();

                Charset charset = UTF8;
                MediaType contentType = responseBody.contentType();
                if (contentType != null) {
                    try {
                        charset = contentType.charset(UTF8);
                    } catch (UnsupportedCharsetException e) {
                        responseMessages.append("Couldn't decode the response body; charset is likely malformed.\n");
                        responseMessages.append("<-- END HTTP");
                        logger.log(responseMessages.toString());

                        return response;
                    }
                }

                if (isPlaintext(buffer)) {
                    if (contentLength != 0) {
                        String body = buffer.clone().readString(charset);
                        responseMessages.append("\n");
                        if ((body.startsWith("{") && body.endsWith("}"))
                                || (body.startsWith("[") && body.endsWith("]"))) {

                            String jsonBody = JsonformatJsonUtils.formatJson(JsonformatJsonUtils.decodeUnicode(body));
                            responseMessages.append(jsonBody + "\n");
                        } else {
                            responseMessages.append(body + "\n");
                        }

                    }
                } else {
                    if (contentLength != 0) {
                        String body = uncompress(buffer.clone(), "UTF-8");
                        responseMessages.append("\n");
                        if ((body.startsWith("{") && body.endsWith("}"))
                                || (body.startsWith("[") && body.endsWith("]"))) {

                            String jsonBody = JsonformatJsonUtils.formatJson(JsonformatJsonUtils.decodeUnicode(body));
                            responseMessages.append(jsonBody + "\n");
                        } else {
                            responseMessages.append(body + "\n");
                        }
                    }
                }


                responseMessages.append("<-- END HTTP (" + buffer.size() + "-byte body)");

            }
            logger.log(responseMessages.toString());
        }

        return response;
    }

    /**
     * Returns true if the body in question probably contains human readable text. Uses a small sample
     * of code points to detect unicode control characters commonly used in binary file signatures.
     */
    static boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }

    /**
     * 取消过滤请求和响应参数
     *
     * @param headers
     * @return
     */
    private boolean bodyEncoded(Headers headers) {
        String contentType = headers.get("content-type");
        return headers.get("encoding") != null || contentType == null || !contentType.contains("application/json");
    }

    private String uncompress(Buffer buffer, String charset) {
        try {
            GZIPInputStream gInputStream = new GZIPInputStream(buffer.inputStream());
            byte[] by = new byte[1024];
            StringBuffer strBuffer = new StringBuffer();
            int len = 0;
            while ((len = gInputStream.read(by)) != -1) {
                strBuffer.append(new String(by, 0, len, charset));
            }
            return strBuffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
