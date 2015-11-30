package com.ilab.http.code.generator.annotation.processor;

import com.ilab.http.code.generator.HttpApiCodeGenerator;
import com.ilab.http.code.generator.annotation.HttpApiCode;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by cuijfboy on 15/11/28.
 */
@SupportedAnnotationTypes({"com.ilab.http.code.generator.annotation.HttpApiCode"})
public class HttpApiCodeProcessor extends AbstractProcessor {

    final String TAG = "[" + HttpApiCodeProcessor.class.getSimpleName() + "]";
    Messager messager;
    List<String> configFilePathList;
    HttpApiCodeGenerator generator;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
        configFilePathList = new ArrayList<>();
        messager.printMessage(Diagnostic.Kind.NOTE, TAG + " initialized!");
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (annotations.size() == 0) {
            for (String configFilePath : this.configFilePathList) {
                if (configFilePath.trim().equals("")) {
                    continue;
                }
                generator = generator == null ? new HttpApiCodeGenerator() : generator;
                messager.printMessage(Diagnostic.Kind.NOTE, TAG + " Generating code file(s) for " + configFilePath);
                generator.generate(new File(configFilePath));
                messager.printMessage(Diagnostic.Kind.NOTE,
                        TAG + " Code file(s) has been generated successfully for " + configFilePath);
            }
            return true;
        }
        for (TypeElement typeElement : annotations) {
            for (Element element : roundEnv.getElementsAnnotatedWith(typeElement)) {
                if (HttpApiCode.class.getName().equals(typeElement.toString())) {
                    HttpApiCode httpApiCode = element.getAnnotation(HttpApiCode.class);
                    String configFilePath = httpApiCode.configFile();
                    configFilePathList.add(configFilePath);
                    messager.printMessage(Diagnostic.Kind.NOTE,
                            TAG + " Found HttpApiCode with configFile : " + configFilePath);
                }
            }
        }
        return true;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}
