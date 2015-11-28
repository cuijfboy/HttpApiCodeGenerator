package com.ilab.http.code.generator;

/**
 * Created by cuijfboy on 15/11/28.
 */
public class HttpApiParameter {
    public String name;
    public String type;
    public boolean isHeader;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean getIsHeader() {
        return isHeader;
    }

    public void setIsHeader(boolean header) {
        isHeader = header;
    }
}
