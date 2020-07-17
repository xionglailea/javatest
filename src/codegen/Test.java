package codegen;

import com.squareup.javapoet.*;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.IOException;

public class Test {
    //javapoet 优雅的java代码生成库
    public static void main(String[] args) throws IOException {

        MethodSpec main = MethodSpec.methodBuilder("main").addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(void.class)
                .addParameter(String[].class, "args")
                .addStatement("$T.out.println($S)", System.class, "Hello JavaPoet!")
                .build();

        FieldSpec fieldSpec = FieldSpec.builder(int.class, "myName", Modifier.PUBLIC).build();
        FieldSpec staticField = FieldSpec.builder(int.class, "staticInt", Modifier.PUBLIC, Modifier.STATIC)
                .initializer("5")
                .build();

        //这里可以直接构造类名，包括中间生成的类型名
        ClassName name = ClassName.get("TestFile", "Test3");
        MethodSpec test = MethodSpec.methodBuilder("noneStatic").addModifiers(Modifier.PUBLIC)
                .returns(void.class)
                .addParameter(name, "param1")
                .addStatement("param1.main(new String[]{})")
                .build();

        TypeSpec hello = TypeSpec.classBuilder("HelloWorld")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(main)
                .addMethod(test)
                .addField(fieldSpec)
                .addField(staticField)
                .build();
        JavaFile javaFile = JavaFile.builder("codegen", hello).build();
        //这里只需要指定根目录，具体的子目录，包名里面已经包含了
        javaFile.writeTo(new File(System.getProperty("user.dir") + "\\src\\"));


        TypeSpec test2 = TypeSpec.classBuilder("Test3").addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(main)
                .build();

        JavaFile javaFile2 = JavaFile.builder("TestFile", test2).build();
        javaFile2.writeTo(new File(System.getProperty("user.dir") + "\\src\\"));

    }
}
