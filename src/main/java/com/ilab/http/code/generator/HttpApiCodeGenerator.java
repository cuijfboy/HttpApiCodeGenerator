package com.ilab.http.code.generator;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ilab.http.HttpMethod;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cuijfboy on 15/11/28.
 */
public class HttpApiCodeGenerator {
    private final String TEMPLATE_FOLDER = "./res/";
    private final String TEMPLATE_NAME = "HttpApiCodeTemplate.ftl";

    private File outputFolder;
    private List<HttpApi> apiInfoList = new ArrayList<>();

    private Template template;

    public HttpApiCodeGenerator() {
        Configuration freeMarkerConfig = new Configuration(Configuration.VERSION_2_3_23);
        freeMarkerConfig.setDefaultEncoding("UTF-8");
        try {
            freeMarkerConfig.setDirectoryForTemplateLoading(new File(TEMPLATE_FOLDER));
            template = freeMarkerConfig.getTemplate(TEMPLATE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generate(String apiJsonInfo, String outputFolderPath) {
        checkOutputFolder(outputFolderPath);
        loadApiInfo(apiJsonInfo);
        generateCode();
    }

    public void generate(File apiJsonFile, String outputFolderPath) {
        String apiJsonInfo = Utils.loadStringFromFile(apiJsonFile);
        generate(apiJsonInfo, outputFolderPath);
    }

    private void checkOutputFolder(String outputFolderPath) {
        outputFolder = new File(outputFolderPath);
        if (!outputFolder.exists()) {
            outputFolder.mkdirs();
        }
    }

    private void loadApiInfo(String apiJsonInfo) {
        apiInfoList = Utils.getGson().fromJson(apiJsonInfo, new TypeToken<List<HttpApi>>() {
        }.getType());
    }

    public void loadSmapleApiInfo() {
        HttpApi api = new HttpApi();
        api.name = "SampleApi";
        api.packageName = "com.ilab.http.code.generated";
        api.importList = new ArrayList<>();

        api.httpMethod = "HttpMethod." + HttpMethod.POST.name();
        api.url = "<url>";
        api.hookName = "com.ilab.http.code.generator.sample.SampleHook";

        HttpApiParameter parameterA = new HttpApiParameter();
        parameterA.name = "parameterA";
        parameterA.type = "String";
        parameterA.isHeader = false;

        HttpApiParameter parameterB = new HttpApiParameter();
        parameterB.name = "parameterB";
        parameterB.type = "String";
        parameterB.isHeader = true;

        api.requestParameterList = new ArrayList<>();
        api.responseParameterList = new ArrayList<>();

        api.requestParameterList.add(parameterA);
        api.requestParameterList.add(parameterB);

        api.responseParameterList.add(parameterA);
        api.responseParameterList.add(parameterB);

        apiInfoList.add(api);

        String apiJsonString = new GsonBuilder().setPrettyPrinting().create().toJson(apiInfoList);
        System.out.println("apiJsonString = \n" + apiJsonString);
    }

    public void generateCode() {
        for (HttpApi api : apiInfoList) {
            Map<String, Object> root = new HashMap<>();
            root.put("api", api);

            String codeFilePath = outputFolder.getAbsolutePath() + "/" + api.name + ".java";

            try {
                Writer writer = new OutputStreamWriter(new FileOutputStream(codeFilePath));
                template.process(root, writer);
            } catch (TemplateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
