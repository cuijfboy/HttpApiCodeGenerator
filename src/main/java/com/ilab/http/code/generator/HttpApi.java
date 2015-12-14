package com.ilab.http.code.generator;

import com.ilab.http.HttpMethod;

import java.util.*;

/**
 * Created by cuijfboy on 15/11/28.
 */
public class HttpApi {
    private String name;
    private HttpMethod method;
    private String fullUrl;
    private String url;
    private String urlBase;
    private Map<String, Map<String, String>> request;
    private Map<String, Map<String, String>> response;
    private String packageName;
    private String hookName;
    private List<String> importList;
    private String codeFileFolder;
    private Map<String, Map<String, String>> model;

    public void combine(String name, HttpApi api) {
        this.name = this.name == null ? name : this.name;
        this.method = this.method == null ? api.method : this.method;
        if (this.fullUrl == null) {
            this.urlBase = this.urlBase == null ? api.urlBase : this.urlBase;
            this.url = this.url == null ? api.url : this.url;
            this.fullUrl = this.urlBase + this.url;
        }
        combineParameterMap(this.request, api.request);
        combineParameterMap(this.response, api.response);
        this.packageName = this.packageName == null ? api.packageName : this.packageName;
        this.hookName = this.hookName == null ? api.hookName : this.hookName;
        this.importList = this.importList == null ? api.importList : this.importList;
        this.codeFileFolder = this.codeFileFolder == null ? api.codeFileFolder : this.codeFileFolder;
    }

    private void combineParameterMap(Map<String, Map<String, String>> thisMap,
                                     Map<String, Map<String, String>> thatMap) {
        combineParameterSubMap(getNotNullValueFromMap(thisMap, "header"), thatMap.get("header"));
        combineParameterSubMap(getNotNullValueFromMap(thisMap, "body"), thatMap.get("body"));
    }

    private Map<String, String> getNotNullValueFromMap(Map<String, Map<String, String>> map, String key) {
        Map<String, String> value = map.get(key);
        if (value == null) {
            value = new HashMap<>();
            map.put(key, value);
        }
        return value;
    }

    private void combineParameterSubMap(Map<String, String> thisMap,
                                        Map<String, String> thatMap) {
        if (thisMap == null) {
            thisMap = new HashMap<>();
            if (thatMap == null) {
                return;
            } else {
                addExtraParameter(thisMap, thatMap);
            }
        } else {
            if (thatMap == null) {
                removeUnnecessaryParameter(thisMap);
            } else {
                addExtraParameter(thisMap, thatMap);
                removeUnnecessaryParameter(thisMap);
            }
        }
    }

    private void addExtraParameter(Map<String, String> thisMap,
                                   Map<String, String> thatMap) {
        Set<String> diffKeySet = new HashSet<>();
        diffKeySet.addAll(thatMap.keySet());
        diffKeySet.removeAll(thisMap.keySet());
        for (String key : diffKeySet) {
            thisMap.put(key, thatMap.get(key));
        }
    }

    private void removeUnnecessaryParameter(Map<String, String> map) {
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            if (entry.getValue() == null) {
                iterator.remove();
            }
        }
    }

    public String getName() {
        return name;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getFullUrl() {
        return fullUrl;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlBase() {
        return urlBase;
    }

    public Map<String, Map<String, String>> getRequest() {
        return request;
    }

    public Map<String, Map<String, String>> getResponse() {
        return response;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getHookName() {
        return hookName;
    }

    public List<String> getImportList() {
        return importList;
    }

    public String getCodeFileFolder() {
        return codeFileFolder;
    }

    public Map<String, Map<String, String>> getModel() {
        return model;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public void setFullUrl(String fullUrl) {
        this.fullUrl = fullUrl;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUrlBase(String urlBase) {
        this.urlBase = urlBase;
    }

    public void setRequest(Map<String, Map<String, String>> request) {
        this.request = request;
    }

    public void setResponse(Map<String, Map<String, String>> response) {
        this.response = response;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setHookName(String hookName) {
        this.hookName = hookName;
    }

    public void setImportList(List<String> importList) {
        this.importList = importList;
    }

    public void setCodeFileFolder(String codeFileFolder) {
        this.codeFileFolder = codeFileFolder;
    }

    public void setModel(Map<String, Map<String, String>> model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return "HttpApi{" +
                "name='" + name + '\'' +
                ", method=" + method +
                ", fullUrl='" + fullUrl + '\'' +
                ", url='" + url + '\'' +
                ", urlBase='" + urlBase + '\'' +
                ", request=" + request +
                ", response=" + response +
                ", packageName='" + packageName + '\'' +
                ", hookName='" + hookName + '\'' +
                ", importList=" + importList +
                ", codeFileFolder='" + codeFileFolder + '\'' +
                ", model=" + model +
                '}';
    }
}
