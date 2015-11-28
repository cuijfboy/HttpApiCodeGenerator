package com.ilab.http.code.generator.annotation.processor;

/**
 * Created by cuijfboy on 15/11/28.
 */
public class HttpApiCodeRecord {
    String apiInfoJsonFile;
    String codeFileOutputFolder;

    @Override
    public String toString() {
        return "HttpApiCodeRecord{" +
                "apiInfoJsonFile='" + apiInfoJsonFile + '\'' +
                ", codeFileOutputFolder='" + codeFileOutputFolder + '\'' +
                '}';
    }
}
