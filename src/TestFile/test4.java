package TestFile;

import java.util.BitSet;

/**
 * create by xiongjieqing on 2020/9/4 10:38
 */
public class test4 {

    public static class A {
        public int a;
        public Object foo(){
            return null;
        }
    }

    public static class B extends A{
        public int a;
//        public void bar(){}
        public Integer foo(){
            return null;
        }
    }

    public static void main(String[] args) {
        B b = new B();
        var fields = B.class.getFields();
        var methods = B.class.getMethods();
        b.a = 1;
        BitSet bitSet = new BitSet(10);
        bitSet.set(0);
        bitSet.set(1);
        bitSet.set(70);

        BitSet temp = (BitSet) bitSet.clone();
        System.out.println(temp == bitSet);
        System.out.println(temp.equals(bitSet));

    }

}
