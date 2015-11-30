package com.ilab.http.code.generated;

import com.ilab.http.IApiHook;
import com.ilab.http.code.template.BaseRequest;
import com.ilab.http.HttpMethod;
import com.ilab.http.code.generator.Utils;

import java.util.HashMap;
import java.util.Map;


public class LoginRequest2 extends BaseRequest {
    private final String API_NAME = "com.ilab.http.code.generated.LoginRequest2";
    private final String HOOK_NAME = "com.ilab.http.code.generator.sample.SampleHook";

    public class Request {
       public String userName;
       public String userPassword;

        private void generateMethod() {
            method = HttpMethod.POST;
        }

        private void generateUrl() {
            url = "http://www.example.com/login";
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

    public Request LoginRequest2;

    public LoginRequest2() {
        this.header = new HashMap<>();
        this.hook = Utils.getHook(HOOK_NAME);
        this.LoginRequest2 = new Request();
    }

    public Request getRequestData() {
        return LoginRequest2;
    }

    public LoginRequest2 go() {
        getRequestData().generateMethod();
        getRequestData().generateUrl();
        getRequestData().generateHeader();
        getRequestData().generateBody();
        hook.onRequest(API_NAME, method, url, header, body, getRequestData(), getRequestData().getClass());
        Utils.getHttpClient().request(this);
        return this;
    }

// Fixed BEGIN ##################################

    private Response response;
    private ResponseListener listener;
    private final IApiHook hook;

    public void setResponseListener(ResponseListener listener) {
        this.listener = listener;
    }

    private void generateResponseData(Map<String, String> header, String body) {
        response = Utils.getGson().fromJson(body, Response.class);
        hook.onResponseData(API_NAME, response, response.getClass(), header, body);
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
