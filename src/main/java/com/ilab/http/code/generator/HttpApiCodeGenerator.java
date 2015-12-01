package com.ilab.http.code.generator;

import com.google.gson.GsonBuilder;
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
    private final String TEMPLATE_NAME = "HttpApiCodeTemplate.ftl";

    private HttpApiJson apiJson;
    private Template template;

    public HttpApiCodeGenerator() {
        System.out.println("[HttpApiCodeGenerator] initialized !");
        Configuration freeMarkerConfig = new Configuration(Configuration.VERSION_2_3_23);
        freeMarkerConfig.setDefaultEncoding("UTF-8");

        try {
            freeMarkerConfig.setClassForTemplateLoading(getClass(), TEMPLATE_FOLDER);
            template = freeMarkerConfig.getTemplate(TEMPLATE_NAME);
            System.out.println("[HttpApiCodeGenerator] template loaded : " + template.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generate(String apiJsonInfo) {
        loadApiInfo(apiJsonInfo);
        generateCode();
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

    public void generateCode() {
        for (HttpApi api : apiJson.getApiMap().values()) {
            System.out.println("[HttpApiCodeGenerator] generating api : \n " + api);
            File outputFolder = new File(api.getCodeFileFolder());
            if (!outputFolder.exists()) {
                outputFolder.mkdirs();
            }
            String codeFilePath = api.getCodeFileFolder() + "/" + api.getName() + ".java";
            Map<String, Object> root = new HashMap<>();
            root.put("api", api);
            try {
                Writer writer = new OutputStreamWriter(new FileOutputStream(codeFilePath));
                template.process(root, writer);
            } catch (TemplateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("[HttpApiCodeGenerator] " + api.getName() + " generated as " + codeFilePath);
        }
        System.out.println("[HttpApiCodeGenerator] generated " + apiJson.getApiMap().size() + " api code file(s).");
    }

}
