package com.ilab.http.code.generator;

import com.google.gson.Gson;
import com.ilab.http.EmptyHook;
import com.ilab.http.IApiHook;
import com.ilab.http.IHttpClient;
import com.ilab.http.code.generator.sample.HttpClientAdapter;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cuijfboy on 15/11/28.
 */
public class Utils {

    private static IHttpClient httpClient;

    public static synchronized IHttpClient getHttpClient() {
        return httpClient == null ? (httpClient = new HttpClientAdapter()) : httpClient;
    }

    private static Gson gson;

    public static synchronized Gson getGson() {
        return gson == null ? (gson = new Gson()) : gson;
    }

    private static Map<String, IApiHook> hookMap = new HashMap<>();
    private static List<String> badHookList = new ArrayList<>();
    private static final EmptyHook EMPTY_HOOK = new EmptyHook();

    public static synchronized IApiHook getHook(String name) {
        if (name == null || badHookList.contains(name)) {
            return EMPTY_HOOK;
        }
        IApiHook hook = hookMap.get(name);
        if (hook == null) {
            try {
                hook = (IApiHook) Class.forName(name).newInstance();
                hookMap.put(name, hook);
            } catch (Exception e) {
                e.printStackTrace();
                badHookList.add(name);
                hook = EMPTY_HOOK;
            }
        }
        return hook;
    }

    public static String loadStringFromFile(File file) {
        StringBuilder sb = new StringBuilder();
        try {
            InputStream is = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}
