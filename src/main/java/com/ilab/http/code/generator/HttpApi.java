package com.ilab.http.code.generator;

import com.ilab.http.HttpMethod;

import java.util.*;

/**
 * Created by cuijfboy on 15/11/28.
 */
public class HttpApi {
    private String name;
    private HttpMethod method;
    private String url;
    private String urlBase;
    private String urlWithoutBase;
    private Map<String, Map<String, String>> request;
    private Map<String, Map<String, String>> response;
    private String packageName;
    private String hookName;
    private List<String> importList;
    private String codeFileFolder;

    public void combine(String name, HttpApi api) {
        this.name = this.name == null ? name : this.name;
        this.method = this.method == null ? api.method : this.method;
        if (this.url == null) {
            this.urlBase = this.urlBase == null ? api.urlBase : this.urlBase;
            this.urlWithoutBase = this.urlWithoutBase == null ? api.urlBase : this.urlWithoutBase;
            this.url = this.urlBase + this.urlWithoutBase;
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
                removeUnnecessoryParameter(thisMap);
            } else {
                addExtraParameter(thisMap, thatMap);
                removeUnnecessoryParameter(thisMap);
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

    private void removeUnnecessoryParameter(Map<String, String> map) {
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

    public String getUrl() {
        return url;
    }

    public String getUrlBase() {
        return urlBase;
    }

    public String getUrlWithoutBase() {
        return urlWithoutBase;
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

    @Override
    public String toString() {
        return "HttpApi{" +
                "name='" + name + '\'' +
                ", method=" + method +
                ", url='" + url + '\'' +
                ", urlBase='" + urlBase + '\'' +
                ", urlWithoutBase='" + urlWithoutBase + '\'' +
                ", request=" + request +
                ", response=" + response +
                ", packageName='" + packageName + '\'' +
                ", hookName='" + hookName + '\'' +
                ", importList=" + importList +
                ", codeFileFolder='" + codeFileFolder + '\'' +
                '}';
    }
}
