package asmtest;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;

public class Generator {
    private static class SelfClassLoader extends ClassLoader{
        public Class defineClassFromFile(String className, byte[] classFile){
            return defineClass(className, classFile, 0, classFile.length);
        }
    }
    private static SelfClassLoader classLoader = new SelfClassLoader();

    private static Class newClass = null;

    public static BeCheckedClass generate() throws IOException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        if (newClass == null){
            ClassReader cr = new ClassReader("asmtest.BeCheckedClass");
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            ClassVisitor classVisitor = new AddFeatureClassAdapter(Opcodes.ASM7, cw);
            cr.accept(classVisitor, ClassReader.SKIP_DEBUG);
            byte[] data = cw.toByteArray();
            Files.write(new File("generatorclass.class").toPath(), data); //将文件存盘看生成结果
            newClass = classLoader.defineClassFromFile("asmtest.BeCheckedClass$EnhancedByASM", data);
        }
        System.out.println(newClass.getName());
        return (BeCheckedClass)newClass.getConstructor().newInstance();
    }
}
