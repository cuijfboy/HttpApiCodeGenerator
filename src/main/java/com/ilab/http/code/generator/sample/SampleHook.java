package com.ilab.http.code.generator.sample;

import com.ilab.http.IApiHook;
import com.ilab.http.HttpMethod;

import java.util.Map;

/**
 * Created by cuijfboy on 15/11/28.
 */
public class SampleHook implements IApiHook {
    @Override
    public void onRequest(String apiName, HttpMethod method, String url, Map<String, String> header, String body, Object data, Class dataClass) {
        System.out.println();
        System.out.println("SampleHook onRequest name = " + apiName);
        System.out.println("SampleHook onRequest url = " + url);
        System.out.println("SampleHook onRequest header = " + header);
        System.out.println("SampleHook onRequest body = " + body);
        System.out.println("SampleHook onRequest data = " + data);
        System.out.println("SampleHook onRequest dataClass = " + dataClass);
    }

    @Override
    public void onResponse(String apiName, int statusCode, Map<String, String> header, String body) {
        System.out.println();
        System.out.println("SampleHook onResponse name = " + apiName);
        System.out.println("SampleHook onResponse statusCode = " + statusCode);
        System.out.println("SampleHook onResponse header = " + header);
        System.out.println("SampleHook onResponse body = " + body);
    }

    @Override
    public void onResponseData(String apiName, Object data, Class dataClass, Map<String, String> header, String body) {
        System.out.println();
        System.out.println("SampleHook onResponseData name = " + apiName);
        System.out.println("SampleHook onResponseData data = " + data);
        System.out.println("SampleHook onResponseData dataClass = " + dataClass);
        System.out.println("SampleHook onResponseData header = " + header);
        System.out.println("SampleHook onResponseData body = " + body);
    }
}
