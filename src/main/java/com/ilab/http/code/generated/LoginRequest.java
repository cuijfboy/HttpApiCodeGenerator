package com.ilab.http.code.generated;

import com.ilab.http.IApiHook;
import com.ilab.http.code.template.BaseRequest;
import com.ilab.http.HttpMethod;
import com.ilab.http.code.generator.Utils;

import java.util.HashMap;
import java.util.Map;


public class LoginRequest extends BaseRequest {
    private final String API_NAME = "com.ilab.http.code.generated.LoginRequest";
    private final String HOOK_NAME = "com.ilab.http.code.generator.sample.SampleHook";

    public class Request {
       public  String userName;
       public  String userPassword;
       public transient String token;

        private void generateMethod() {
            method = HttpMethod.POST;
        }

        private void generateUrl() {
            url = "http://www.example.com/login";
        }

        private void generateHeader() {
            header.clear();
            
            
            header.put("token", token);
        }

        private void generateBody() {
            body = Utils.getGson().toJson(this);
        }
    }

    public class Response {
        public String userId;
        public String nickName;
        public int errorCode;
        public String errorInfo;
    }

    // ------------------------------------------

    public Request LoginRequest;

    public LoginRequest() {
        this.header = new HashMap<>();
        this.hook = Utils.getHook(HOOK_NAME);
        this.LoginRequest = new Request();
    }

    public Request getRequestData() {
        return LoginRequest;
    }

    public LoginRequest go() {
        getRequestData().generateMethod();
        getRequestData().generateUrl();
        getRequestData().generateHeader();
        getRequestData().generateBody();
        if (hook != null) {
            hook.onRequest(API_NAME, method, url, header, body, getRequestData(), getRequestData().getClass());
        }
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
        if (hook != null) {
            hook.onResponseData(API_NAME, response, response.getClass(), header, body);
        }
    }

    @Override
    public final void onResponse(int statusCode, Map<String, String> header, String body) {
        if (hook != null) {
            hook.onResponse(API_NAME, statusCode, header, body);
        }
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
