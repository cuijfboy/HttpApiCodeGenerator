package com.ilab.http;

import java.util.Map;

/**
 * Created by cuijfboy on 15/11/30.
 */
public class EmptyHook implements IApiHook {

    @Override
    public void onRequest(String apiName, HttpMethod method, String url, Map<String, String> header, String body, Object data, Class dataClass) {

    }

    @Override
    public void onResponse(String apiName, int statusCode, Map<String, String> header, String body) {

    }

    @Override
    public void onResponseData(String apiName, Object data, Class dataClass, Map<String, String> header, String body) {

    }
}
