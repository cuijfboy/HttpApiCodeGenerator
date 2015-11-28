package com.ilab.http.code.generator.sample;

import com.ilab.http.code.template.BaseRequest;
import com.ilab.http.HttpMethod;
import com.ilab.http.code.generator.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cuijfboy on 15/11/28.
 */
public class GetModuleByName extends BaseRequest {

    public Request GetModuleByName;
    private Response response;

    public GetModuleByName() {
        this.header = new HashMap<>();
        this.GetModuleByName = new Request();
    }

    public Request getRequest() {
        return GetModuleByName;
    }

    public class Request {
        public String appId;

        private void generateMethod() {
            method = HttpMethod.POST;
        }

        private void generateUrl() {
            url = "http://uhome.haier.net:7910/SaasManage/module/getByName";
        }

        private void generateHeader() {
            header.clear();
        }

        private void generateBody() {
            body = Utils.getGson().toJson(this);
        }
    }

    public class Response {
        public String retCode;
        public String retInfo;
    }

    private void generateResponse(Map<String, String> header, String body) {
        response = Utils.getGson().fromJson(body, Response.class);
    }


    public GetModuleByName go() {
        GetModuleByName.generateMethod();
        GetModuleByName.generateUrl();
        GetModuleByName.generateHeader();
        GetModuleByName.generateBody();
        Utils.getHttpClient().request(this);
        return this;
    }

    public boolean onResponse(int statusCode, Response response) {
        return false;
    }

    public boolean onResponse(int statusCode, Response response, Map<String, String> header, String body) {
        return false;
    }


    @Override
    public final void onResponse(int statusCode, Map<String, String> header, String body) {
        generateResponse(header, body);
        if (!onResponse(statusCode, response)) {
            onResponse(statusCode, response, header, body);
        }
    }

}
