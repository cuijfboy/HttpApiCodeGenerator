package com.ilab.http.code.generator.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by cuijfboy on 15/11/28.
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface HttpApiCode {

    String apiInfoJsonFile() default "";
    String codeFileOutputFolder() default "";
}
