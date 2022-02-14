package com.dyf.myanotationprocessors;

import java.io.IOException;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

@AutoService(PluginProcessor.class)  //自动注册
//@SupportedOptions(Consts.ARGUMENTS_NAME) //处理器接受参数
@SupportedSourceVersion(SourceVersion.RELEASE_7) //指定java版本
@SupportedAnnotationTypes({"com.dyf.myanotation.PluginImpl"}) //处理的注解
public class PluginProcessor extends AbstractProcessor {

  private static final String TAG = "PluginProcessor";

  @Override
  public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
    System.out.println(TAG + " process called");

    MethodSpec main = MethodSpec.methodBuilder("main")
        .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
        .returns(void.class)
        .addParameter(String[].class, "args")
        .addStatement("$T.out.println($S)", System.class, "Hello, JavaPoet!")
        .build();

    TypeSpec helloWorld = TypeSpec.classBuilder("HelloWorld")
        .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
        .addMethod(main)
        .build();

    JavaFile javaFile = JavaFile.builder("com.dyf.helloworld", helloWorld)
        .build();

    try {
      javaFile.writeTo(System.out);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return true;
  }
}
