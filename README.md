# HttpApiCodeGenerator

---

需求文档里这样的表格：

接口名称      | 登录
-------------|----------------------
接入点        |http://www.example.com/login
Http Method  |POST
功能          |用户登录，验证身份，balabalabalabalabala
备注          |使用json描述接口数据

输入参数     |限定类型 |位置   |必填规则  |说明
------------|-------|-------|--------|----
name        |String |body   |必填     |填写用户登录名称
userPassword|String |body   |必填     |填写用户登录密码
token       |String |header |必填     |填写校验信息

输出参数     |限定类型 |位置   |必填规则  |说明
------------|-------|-------|--------|----
userId      |String |body   |必填     |用户ID
nickName    |String |body   |必填     |用户昵称
errorCode   |int    |header |必填     |错误代码
errorInfo   |String |header |必填     |错误信息描述

用Json描述是这样：
```json
{
  "defaultApi": {
    "method": "POST",
    "urlBase": "http://www.example.com/",
    "request": {
      "header": { "token": "String" },
      "body": { "userName": "String", "userPassword": "String" }
    },
    "response": {
      "header": { "session": "String" },
      "body": { "errorCode": "int", "errorInfo": "String" }
    },
    "packageName": "com.ilab.http.code.generated",
    "hookName": "com.ilab.http.code.generator.sample.SampleHook",
    "importList": [],
    "codeFileFolder": "./src/main/java/com/ilab/http/code/generated/"
  },
  "httpApi": {
    "LoginRequest": {
      "urlWithoutBase": "login",
      "request": {
        "body": { "userName": "String", "userPassword": "String" }
      },
      "response": {
        "body": { "userId": "String", "nickName": "String" }
      }
    }
  }
}
```

然后这么调用：

```java
new LoginRequest() {
    {
        LoginRequest.userName = "admin@example.com";
        LoginRequest.userPassword = "passw0rd";
        LoginRequest.token = "1234567890";
    }

    @Override
    public boolean onResponse(int statusCode, Response data) {
        System.out.println("data.errorCode = " + data.errorCode);
        System.out.println("data.errorInfo = " + data.errorInfo);
        System.out.println("data.userId = " + data.userId);
        System.out.println("data.nickName = " + data.nickName);
        // do something else
        return true;
    }
}.go();
```

文档里的需求就实现了。

对了，别忘了引入jar包，并在代码里加上这个注解：

```java
@HttpApiCode(configFile = "./res/SampleLoginRequest.json")
public class SampleMain {
    // something else
}
```

然后上面提到的Json文件会变成下面这样子，然后就可以像上面那样调用了。

```java
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
       public transient String token;
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
            header.put("token", token);
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

```
