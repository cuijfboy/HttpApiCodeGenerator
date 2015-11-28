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

    final String TAG = HttpApiCodeProcessor.class.getSimpleName();
    Messager messager;
    List<HttpApiCodeRecord> recordList;
    HttpApiCodeGenerator generator;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
        recordList = new ArrayList<>();
        messager.printMessage(Diagnostic.Kind.NOTE, TAG + " initialized!");
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (annotations.size() == 0) {
            for (HttpApiCodeRecord record : recordList) {
                if (record.apiInfoJsonFile.trim().equals("")
                        || record.codeFileOutputFolder.trim().equals("")) {
                    continue;
                }
                generator = generator == null ? new HttpApiCodeGenerator() : generator;
                messager.printMessage(Diagnostic.Kind.NOTE, "Generating code for record : " + record);
                generator.generate(new File(record.apiInfoJsonFile), record.codeFileOutputFolder);
                messager.printMessage(Diagnostic.Kind.NOTE, "Code has been generated successfully for record : " + record);
            }
            return true;
        }
        for (TypeElement typeElement : annotations) {
            for (Element element : roundEnv.getElementsAnnotatedWith(typeElement)) {
                if (HttpApiCode.class.getName().equals(typeElement.toString())) {
                    HttpApiCode httpApiCode = element.getAnnotation(HttpApiCode.class);
                    HttpApiCodeRecord record = new HttpApiCodeRecord();
                    record.apiInfoJsonFile = httpApiCode.apiInfoJsonFile();
                    record.codeFileOutputFolder = httpApiCode.codeFileOutputFolder();
                    recordList.add(record);
                    messager.printMessage(Diagnostic.Kind.NOTE, "Found cord record : " + record);
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
