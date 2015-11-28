package com.ilab.http;

import java.util.Map;

/**
 * Created by cuijfboy on 15/11/28.
 */
public interface IApiHook {

    void onRequest(String apiName, HttpMethod method, String url, Map<String, String> header, String body, Object data, Class dataClass);

    void onResponse(String apiName, int statusCode, Map<String, String> header, String body);

    void onResponseData(String apiName, Object data, Class dataClass, Map<String, String> header, String body);

}
