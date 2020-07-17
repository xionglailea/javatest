package asmtest;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ChangetoChildAdapter extends MethodVisitor {
    private String superClassName;
    public ChangetoChildAdapter(MethodVisitor mv, String superClassName){
        super(Opcodes.ASM7, mv);
        this.superClassName = superClassName;
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
        if (opcode == Opcodes.INVOKESPECIAL && name.equals("<init>")){
            owner = superClassName;
        }
        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
    }
}
