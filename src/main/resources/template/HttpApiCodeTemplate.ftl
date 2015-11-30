package ${api.packageName};

import com.ilab.http.IApiHook;
import com.ilab.http.code.template.BaseRequest;
import com.ilab.http.HttpMethod;
import com.ilab.http.code.generator.Utils;

import java.util.HashMap;
import java.util.Map;

<#list api.importList as import>
import ${import};
</#list>

public class ${api.name} extends BaseRequest {
    private final String API_NAME = "${api.packageName}.${api.name}";
    private final String HOOK_NAME = <#if api.hookName??>"${api.hookName}"<#else>null</#if>;

    public class Request {
       <#list api.request.header?keys as parameter>
       public transient ${api.request.header[parameter]} ${parameter};
       </#list>
       <#list api.request.body?keys as parameter>
       public ${api.request.body[parameter]} ${parameter};
       </#list>

        private void generateMethod() {
            method = HttpMethod.${api.method};
        }

        private void generateUrl() {
            url = "${api.url}";
        }

        private void generateHeader() {
            header.clear();
            <#list api.request.header?keys as parameter>
            header.put("${parameter}", ${parameter});
            </#list>
        }

        private void generateBody() {
            body = Utils.getGson().toJson(this);
        }
    }

    public class Response {
        <#list api.response.header?keys as parameter>
        public transient ${api.response.header[parameter]} ${parameter};
        </#list>
        <#list api.response.body?keys as parameter>
        public ${api.response.body[parameter]} ${parameter};
        </#list>
    }

    // ------------------------------------------

    public Request ${api.name};

    public ${api.name}() {
        this.header = new HashMap<>();
        this.hook = Utils.getHook(HOOK_NAME);
        this.${api.name} = new Request();
    }

    public Request getRequestData() {
        return ${api.name};
    }

    public ${api.name} go() {
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