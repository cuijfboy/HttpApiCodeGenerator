package com.ilab.http.code.generator;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cuijfboy on 15/11/28.
 */
public class HttpApiCodeGenerator {
    private final String TEMPLATE_FOLDER = "/template";
    private final String HTTP_API_TEMPLATE_NAME = "http_api.ftl";
    private final String HTTP_API_GLOBAL_MODEL_TEMPLATE_NAME = "http_api_global_model.ftl";

    private HttpApiJson apiJson;
    private Template httpApiTemplate;
    private Template httpApiGlobalModelTemplate;

    public HttpApiCodeGenerator() {
        System.out.println("[HttpApiCodeGenerator] initialized !");
        Configuration freeMarkerConfig = new Configuration(Configuration.VERSION_2_3_23);
        freeMarkerConfig.setDefaultEncoding("UTF-8");

        try {
            freeMarkerConfig.setClassForTemplateLoading(getClass(), TEMPLATE_FOLDER);
            httpApiTemplate = freeMarkerConfig.getTemplate(HTTP_API_TEMPLATE_NAME);
            System.out.println("[HttpApiCodeGenerator] httpApiTemplate loaded : "
                    + httpApiTemplate.getName());
            httpApiGlobalModelTemplate = freeMarkerConfig.getTemplate(HTTP_API_GLOBAL_MODEL_TEMPLATE_NAME);
            System.out.println("[HttpApiCodeGenerator] httpApiGlobalModelTemplate loaded : "
                    + httpApiGlobalModelTemplate.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generate(String apiJsonInfo) {
        loadApiInfo(apiJsonInfo);
        generateGlobalModelCode();
        generateApiCode();
    }

    public void generate(File apiJsonFile) {
        String apiJsonInfo = Utils.loadStringFromFile(apiJsonFile);
        generate(apiJsonInfo);
    }

    private void loadApiInfo(String apiJsonInfo) {
        apiJson = Utils.getSerializeNullGson().fromJson(apiJsonInfo, HttpApiJson.class);
        apiJson.refresh();
        System.out.println("[HttpApiCodeGenerator] globalConfig loaded :\n " + apiJson.getGlobalConfig());
    }

    public void generateApiCode() {
        for (HttpApi api : apiJson.getApiMap().values()) {
            System.out.println("[HttpApiCodeGenerator] generating api : \n " + api);
            File outputFolder = new File(api.getCodeFileFolder());
            if (!outputFolder.exists()) {
                outputFolder.mkdirs();
            }
            generateCodeFile(api, httpApiTemplate);
        }
        System.out.println("[HttpApiCodeGenerator] generated " + apiJson.getApiMap().size() + " api code file(s).");
    }

    private void generateGlobalModelCode() {
        HttpApi global = apiJson.getGlobalConfig();
        File outputFolder = new File(global.getCodeFileFolder());
        if (!outputFolder.exists()) {
            outputFolder.mkdirs();
        }
        for (Map.Entry<String, Map<String, String>> model : global.getModel().entrySet()) {
            HttpApi api = new HttpApi();
            api.setImportList(global.getImportList());
            api.setCodeFileFolder(global.getCodeFileFolder());
            api.setPackageName(global.getPackageName());
            api.setName(model.getKey());
            Map<String, Map<String, String>> parameterMap = new HashMap<>();
            parameterMap.put(model.getKey(), model.getValue());
            api.setModel(parameterMap);

            System.out.println("[HttpApiCodeGenerator] generating global model : \n " + api);
            generateCodeFile(api, httpApiGlobalModelTemplate);
        }
        System.out.println("[HttpApiCodeGenerator] generated " + apiJson.getGlobalConfig().getModel().size()
                + " api code file(s).");
    }

    private void generateCodeFile(HttpApi api, Template template) {
        String codeFilePath = api.getCodeFileFolder() + File.separator + api.getName() + ".java";
        Map<String, Object> root = new HashMap<>();
        root.put("api", api);
        try {
            Writer writer = new OutputStreamWriter(new FileOutputStream(codeFilePath));
            template.process(root, writer);
            System.out.println("[HttpApiCodeGenerator] " + api.getName() + " generated as " + codeFilePath);
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
