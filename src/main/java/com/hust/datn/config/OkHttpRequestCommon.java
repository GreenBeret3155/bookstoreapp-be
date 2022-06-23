package com.hust.datn.config;

import okhttp3.*;

import javax.net.ssl.*;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class OkHttpRequestCommon {
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    public static final MediaType XML_START_ROOM = MediaType.parse("application/vnd.plcm.plcm-conference-start-request+xml");
    public static final MediaType XML_DIAL_OUT = MediaType.parse("application/vnd.plcm.plcm-dial-out-participant-v2+xml");
    public static final MediaType XML_PLAIN_TEXT = MediaType.parse("text/plain");

    public static OkHttpClient getUnsafeOkHttpClient() {
        try {
//            String hostname = "10.61.11.42"/*127.0.0.1*/;
//            int port = 3128;
//            Proxy proxy = new Proxy(Proxy.Type.HTTP,
//                    new InetSocketAddress(hostname, port));
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[]{};
                    }
                }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

//            OkHttpClient okHttpClient = builder.proxy(proxy).build();
            OkHttpClient okHttpClient = builder
                .connectionPool(new ConnectionPool(1, 30, TimeUnit.SECONDS))
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
//                     .proxy(proxy)
                .build();
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static Response post(String url, String json, Headers lstHeader, MediaType mediaType) throws IOException {
        RequestBody body = null;
        if (json != null)
            body = RequestBody.create(mediaType, json);
        Request request = new Request.Builder()
            .url(url)
            .method("POST", json != null ? body : null)
            .headers(lstHeader)
            .build();
        try (Response response = getUnsafeOkHttpClient().newCall(request).execute()) {
            return response.newBuilder().body(ResponseBody.create(mediaType, response.body().string())).build();
        }
    }

    public static String get(String url, String json, Headers lstHeader) {
        try {
            RequestBody body = null;
            if (json != null)
                body = RequestBody.create(JSON, json);
            Request request = new Request.Builder()
                .url(url)
                .method("GET", json != null ? body : null)
                .headers(lstHeader)
                .build();

            try (Response response = getUnsafeOkHttpClient().newCall(request).execute()) {
                if (response.code() == 200) {
                    return response.body().string();
                } else {
                    throw new RuntimeException(new NullPointerException());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Response delete(String url, String json, Headers lstHeader, MediaType mediaType) throws IOException {
        RequestBody body = null;
        if (json != null)
            body = RequestBody.create(mediaType, json);
        Request request = new Request.Builder()
            .url(url)
            .method("DELETE", json != null ? body : null)
            .headers(lstHeader)
            .build();
        try (Response response = getUnsafeOkHttpClient().newCall(request).execute()) {
            return response.newBuilder().body(ResponseBody.create(mediaType, response.body().string())).build();

        }
    }
}
