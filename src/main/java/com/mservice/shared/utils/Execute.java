package com.mservice.shared.utils;

import com.mservice.models.HttpRequest;
import com.mservice.models.HttpResponse;
import okhttp3.*;
import okio.Buffer;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;

public class Execute {

    @Value("${proxy.hostname}")
    private String hostname;

    @Value("${proxy.port}")
    private Integer port;

    public HttpResponse sendToMoMo(String endpoint, String payload) {

        OkHttpClient client = new OkHttpClient.Builder().proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.61.11.42", 3128))).build();

        if(hostname != null && port != null){
            client = new OkHttpClient.Builder().proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(hostname, port))).build();
        }

        try {

            HttpRequest httpRequest = new HttpRequest("POST", endpoint, payload, "application/json");

            Request request = createRequest(httpRequest);

            LogUtils.debug("[HttpPostToMoMo] Endpoint:: " + httpRequest.getEndpoint() + ", RequestBody:: " + httpRequest.getPayload());

            Response result = client.newCall(request).execute();
            HttpResponse response = new HttpResponse(result.code(), result.body().string(), result.headers());

            LogUtils.info("[HttpResponseFromMoMo] " + response.toString());

            return response;
        } catch (Exception e) {
            LogUtils.error("[ExecuteSendToMoMo] "+ e);
        }

        return null;
    }

    public static Request createRequest(HttpRequest request) {
        RequestBody body = RequestBody.create(MediaType.get(request.getContentType()), request.getPayload());
        return new Request.Builder()
                .method(request.getMethod(), body)
                .url(request.getEndpoint())
                .build();
    }

    public String getBodyAsString(Request request) throws IOException {
        Buffer buffer = new Buffer();
        RequestBody body = request.body();
        body.writeTo(buffer);
        return buffer.readUtf8();
    }
}
