package com.monk.annotationprocessor;

import com.google.auto.service.AutoService;
import com.mono.annotaton.BindPath;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;

/**
 * 这个 @AutoService(Processor.class)一定要加上，否则无法生成java文件
 *
 * @author monk
 * @date 2019-1-22 11:16:31
 */
@AutoService(Processor.class)
public class MyAnnotationProcessor extends AbstractProcessor {

    /*** 文件相关的辅助类*/
    private Filer mFiler;

    /**
     * 每一个注解处理器类都必须有一个空的构造函数。
     * 然而，这里有一个特殊的init()方法，它会被注解处理工具调用，并输入ProcessingEnvironment 参数。
     */
    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mFiler = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnv) {
        // 构建方法 此处使用到square 公司的javapoet库，用来辅助生成类的代码
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("show")
                .addModifiers(Modifier.PUBLIC)
                .addStatement("String test = \"$N\"","HELLO WORLD");

       // 类名和包名
        TypeSpec finderClass = TypeSpec.classBuilder("MyGeneratedClass")
                .addModifiers(Modifier.PUBLIC)
//                .addSuperinterface(ParameterizedTypeName.get(TypeUtil.INJECTOR, TypeName.get(mClassElement.asType())))
                .addMethod(methodBuilder.build())
                .build();

        // 创建Java文件
        JavaFile javaFile = JavaFile.builder("com.monk.annotationprocessor", finderClass).build();

        try {
            javaFile.writeTo(mFiler);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String, String> map = new HashMap<>();
        // 拿到被注解的元素的集合。包含所有被BindPath注解的元素。
        Set<? extends Element> set1 = roundEnv.getElementsAnnotatedWith(BindPath.class);
        for (Element element : set1) {
            TypeElement te = (TypeElement) element;
            BindPath bindPath = te.getAnnotation(BindPath.class);
            Name qualifiedName = te.getQualifiedName();
            map.put(bindPath.value(), qualifiedName + ".class");
        }


        return true;
    }

    /*** 声明注解处理器支持的java版本*/
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return processingEnv.getSourceVersion();
    }

    /**
     * 这个方法必须重写，否则无法生成Java文件
     * 这里必须指定，这个注解处理器是注册给哪个注解的。
     * 注意，它的返回值是一个字符串的集合，包含本处理器想要处理的注解类型的合法全称。
     * 换句话说，在这里定义你的注解处理器注册到哪些注解上。
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(BindPath.class.getCanonicalName());
        return types;
    }
}
