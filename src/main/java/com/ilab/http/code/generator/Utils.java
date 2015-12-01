package com.ilab.http.code.generator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ilab.http.EmptyHook;
import com.ilab.http.IApiHook;
import com.ilab.http.IHttpClient;
import com.ilab.http.code.generator.sample.DefaultHttpClient;

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

    public static synchronized IHttpClient getDefaultHttpClient() {
        return httpClient == null ? (httpClient = new DefaultHttpClient()) : httpClient;
    }

    private static Gson defaultGson;
    private static Gson serializeNullGson;

    public static synchronized Gson getGson() {
        return defaultGson == null ? (defaultGson = new Gson()) : defaultGson;
    }

    public static synchronized Gson getSerializeNullGson() {
        return serializeNullGson == null ?
                (serializeNullGson = new GsonBuilder().serializeNulls().create()) : serializeNullGson;
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
