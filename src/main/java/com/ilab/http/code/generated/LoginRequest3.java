package com.ilab.http.code.generated;

import com.ilab.http.IApiHook;
import com.ilab.http.IHttpClient;
import com.ilab.http.code.template.BaseRequest;
import com.ilab.http.HttpMethod;
import com.ilab.http.code.generator.Utils;

import java.util.HashMap;
import java.util.Map;


public class LoginRequest3 extends BaseRequest {
    private final String API_NAME = "com.ilab.http.code.generated.LoginRequest3";
    private final String HOOK_NAME = "com.ilab.http.code.generator.sample.SampleHook";

    public class Request {
       public String userName;
       public String userPassword;

        private void generateMethod() {
            method = HttpMethod.GET;
        }

        private void generateUrl() {
            url = "http://www.example.com/login";
            if (HttpMethod.GET == method) {
                StringBuffer sb = new StringBuffer(url);
                sb.append("?");
                sb.append("userName").append("=").append(userName).append("&");
                sb.append("userPassword").append("=").append(userPassword).append("&");
                sb.deleteCharAt(sb.length() - 1);
                if (sb.length() != url.length()) {
                    url = sb.toString();
                }
            }
        }

        private void generateHeader() {
            header.clear();
        }

        private void generateBody() {
            body = Utils.getGson().toJson(this);
        }
    }

    public class Response {
        public transient String session;
        public String userId;
        public String nickName;
        public int errorCode;
    }


    // ------------------------------------------

    public Request LoginRequest3;

    public LoginRequest3() {
        this.header = new HashMap<>();
        this.hook = Utils.getHook(HOOK_NAME);
        this.LoginRequest3 = new Request();
    }

    public Request getRequestData() {
        return LoginRequest3;
    }

    public LoginRequest3 go(IHttpClient httpClient) {
        getRequestData().generateMethod();
        getRequestData().generateUrl();
        getRequestData().generateHeader();
        getRequestData().generateBody();
        hook.onRequest(API_NAME, method, url, header, body, getRequestData(), getRequestData().getClass());
        httpClient.request(this);
        return this;
    }

    public LoginRequest3 go() {
        return go(Utils.getDefaultHttpClient());
    }

    private void generateResponseData(Map<String, String> header, String body) {
        response = Utils.getGson().fromJson(body, Response.class);
        response = response == null ? new Response() : response;
        response.session = header.get("session");
        hook.onResponseData(API_NAME, response, response.getClass(), header, body);
    }

// Fixed BEGIN ##################################

    private Response response;
    private ResponseListener listener;
    private final IApiHook hook;

    public void setResponseListener(ResponseListener listener) {
        this.listener = listener;
    }

    @Override
    public final void onResponse(int statusCode, Map<String, String> header, String body) {
        hook.onResponse(API_NAME, statusCode, header, body);
        generateResponseData(header, body);
        if (listener != null) {
            listener.onResponse(statusCode, response, header, body);
        } else {
            if (!onResponse(statusCode, response)) {
                onResponse(statusCode, response, header, body);
            }
        }
    }

    public boolean onResponse(int statusCode, Response data) {
        return false;
    }

    public boolean onResponse(int statusCode, Response data, Map<String, String> header, String body) {
        return false;
    }

    public interface ResponseListener {
        boolean onResponse(int statusCode, Response data, Map<String, String> header, String body);
    }
}

// Fixed END ####################################
