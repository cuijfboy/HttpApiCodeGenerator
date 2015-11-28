package com.ilab.http.code.generator.sample;

import com.ilab.http.IHttpClient;
import com.ilab.http.IHttpRequest;

import java.util.Collections;
import java.util.HashMap;

/**
 * Created by cuijfboy on 15/11/28.
 */
public class HttpClientAdapter implements IHttpClient {

    @Override
    public void request(IHttpRequest request) {
        System.out.println();
        System.out.println("HttpClientAdapter request.getMethod() = " + request.getMethod());
        System.out.println("HttpClientAdapter request.getUrl() = " + request.getUrl());
        System.out.println("HttpClientAdapter request.getHeader() = " + request.getHeader());
        System.out.println("HttpClientAdapter request.getBody() = " + request.getBody());

        request.getMethod();
        request.getUrl();
        request.getHeader();
        request.getBody();
        request.onResponse(200,
                new HashMap<String, String>() {
                    {
                        put("sessionId", "9876543210");
                    }
                }, "{\"userId\":\"123\"," +
                        "\"nickName\":\"administrator\"," +
                        "\"errorCode\":\"0\"," +
                        "\"errorInfo\":\"Everything goes well.\"}");
    }
}
