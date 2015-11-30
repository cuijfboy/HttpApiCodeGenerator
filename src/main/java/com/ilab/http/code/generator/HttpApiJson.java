package com.ilab.http.code.generator;

import java.util.Map;

/**
 * Created by cuijfboy on 15/11/30.
 */
public class HttpApiJson {
    private HttpApi defaultApi;
    private Map<String, HttpApi> httpApi;

    public void refresh() {
        for (Map.Entry<String, HttpApi> entry : httpApi.entrySet()) {
            entry.getValue().combine(entry.getKey(), defaultApi);
        }
    }

    public HttpApi getDefaultApi() {
        return defaultApi;
    }

    public Map<String, HttpApi> getHttpApiMap() {
        return httpApi;
    }
}
