package asmtest;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class AddFeatureMethodAdapter extends MethodVisitor {
    public AddFeatureMethodAdapter(int api, MethodVisitor methodVisitor) {
        super(api, methodVisitor);
    }

    @Override
    public void visitCode() {
        visitMethodInsn(Opcodes.INVOKESTATIC, "asmtest/ExtraFeature", "feature", "()V", false);
    }
}
