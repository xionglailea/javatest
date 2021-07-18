package asmtest;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class AddFeatureClassAdapter extends ClassVisitor {
    public AddFeatureClassAdapter(int api, ClassVisitor classVisitor) {
        super(api, classVisitor);
    }

    private String enhancedSuperName;

    @Override
    public void visit(int version, int
            access, String name, String signature, String superName, String[] interfaces) {
        String enhancedName = name + "$EnhancedByASM";
        enhancedSuperName = name;
        super.visit(version, access, enhancedName, signature, enhancedSuperName, interfaces);

    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        if (name.equals("print")) { // 对不感兴趣的 返回null
            return null;
        }
        MethodVisitor mv = cv.visitMethod(access, name, descriptor, signature, exceptions);
        MethodVisitor wrappedMv = mv;
        if (mv != null){
            if (name.equals("operation")){
                wrappedMv = new AddFeatureMethodAdapter(Opcodes.ASM7, mv);
            } else if (name.equals("<init>")){
                wrappedMv = new ChangetoChildAdapter(mv, enhancedSuperName);
            }
        }
        return wrappedMv;
    }
}
