package com.ilab.http.code.template;

import com.ilab.http.IHttpRequest;
import com.ilab.http.HttpMethod;

import java.util.Map;

/**
 * Created by cuijfboy on 15/11/28.
 */
public abstract class BaseRequest implements IHttpRequest {
    protected HttpMethod method;
    protected String url;
    protected Map<String, String> header;
    protected String body;

    @Override
    public HttpMethod getMethod() {
        return method;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public Map<String, String> getHeader() {
        return header;
    }

    @Override
    public String getBody() {
        return body;
    }

}
