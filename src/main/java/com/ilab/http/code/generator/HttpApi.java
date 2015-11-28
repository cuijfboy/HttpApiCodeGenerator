package com.ilab.http.code.generator;

import java.util.List;

/**
 * Created by cuijfboy on 15/11/28.
 */
public class HttpApi {
    public String name;
    public String packageName;
    public List<String> importList;
    public String httpMethod;
    public String url;
    public List<HttpApiParameter> requestParameterList;
    public List<HttpApiParameter> responseParameterList;
    public String hookName;

    public String getName() {
        return name;
    }

    public String getPackageName() {
        return packageName;
    }

    public List<String> getImportList() {
        return importList;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public String getUrl() {
        return url;
    }

    public List<HttpApiParameter> getRequestParameterList() {
        return requestParameterList;
    }

    public List<HttpApiParameter> getResponseParameterList() {
        return responseParameterList;
    }

    public String getHookName() {
        return hookName;
    }
}
