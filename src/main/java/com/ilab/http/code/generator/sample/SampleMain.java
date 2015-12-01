package com.ilab.http.code.generator.sample;


import com.ilab.http.code.generated.LoginRequest;
import com.ilab.http.code.generator.HttpApiCodeGenerator;
import com.ilab.http.code.generator.annotation.HttpApiCode;

import java.io.File;
import java.util.Map;

/**
 * Created by cuijfboy on 15/11/28.
 */

@HttpApiCode(configFile = "./res/SampleLoginRequest.json")
public class SampleMain {

    private static void generateHttpApiCode() {
        new HttpApiCodeGenerator().generate(
                new File("./res/SampleLoginRequest.json"));

        System.out.println();
        System.out.println("Api code has been generated according to ./res/SampleLoginRequest.json");
    }

    private static void invokeHttpApiCode() {
        new LoginRequest() {
            {
                LoginRequest.userName = "admin@example.com";
                LoginRequest.userPassword = "passw0rd";
                LoginRequest.token = "1234567890";
            }

            @Override
            public boolean onResponse(int statusCode, Response data, Map<String, String> header, String body) {
                System.out.println();
                System.out.println("onResponse statusCode = " + statusCode);
                System.out.println("onResponse data.errorCode = " + data.errorCode);
                System.out.println("onResponse data.errorInfo = " + data.errorInfo);
                System.out.println("onResponse data.userId = " + data.userId);
                System.out.println("onResponse data.nickName = " + data.nickName);
                System.out.println("onResponse header = " + header);
                System.out.println("onResponse body = " + body);
                return true;
            }
        }.go();
    }

    public static void main(String[] args) {
        System.out.println("\n********** main start **********");

//        generateHttpApiCode();

        invokeHttpApiCode();

        System.out.println("\n********** main finish **********");
    }
}
