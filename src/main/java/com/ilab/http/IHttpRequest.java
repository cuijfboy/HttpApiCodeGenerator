package com.ilab.http;

import java.util.Map;

/**
 * Created by cuijfboy on 15/11/28.
 */
public interface IHttpRequest {

    HttpMethod getMethod();

    String getUrl();

    Map<String,String> getHeader();

    String getBody();

    void onResponse(int statusCode, Map<String,String> header, String body);

}
