package codegen;

import com.squareup.javapoet.*;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.IOException;

public class Test {
    //javapoet ���ŵ�java�������ɿ�
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

        //�������ֱ�ӹ��������������м����ɵ�������
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
        //����ֻ��Ҫָ����Ŀ¼���������Ŀ¼�����������Ѿ�������
        javaFile.writeTo(new File(System.getProperty("user.dir") + "\\src\\"));


        TypeSpec test2 = TypeSpec.classBuilder("Test3").addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(main)
                .build();

        JavaFile javaFile2 = JavaFile.builder("TestFile", test2).build();
        javaFile2.writeTo(new File(System.getProperty("user.dir") + "\\src\\"));

    }
}
