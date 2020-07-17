package jnitest;



public class TestJNI {
    static {
        System.loadLibrary("jni");
    }
    public static class DataA{
        int a;
        int b;
        int c;
    }
    public native void sayHello();
    public native void arrayTest(DataA[] array);

    public void arrayTest1(DataA[] array){
        System.out.println(array[0].a);
    }
    public static void main(String[] args) {
//        new TestJNI().sayHello();
        DataA[] array = new DataA[1];
        array[0] = new DataA();
        array[0].a = 10;
        TestJNI jni = new TestJNI();
        long start = System.currentTimeMillis();
        for(int i = 0; i < 1000000; i++) {
            jni.arrayTest(array);
//            jni.arrayTest1(array);
        }
        long end = System.currentTimeMillis();
        long cost = end - start;
        System.out.println("Cost Time = " + cost);
    }
}

//jni������������  1.дnative���� 2.���뵱ǰ��ʹ��javac -h . TestJNI.java ���ɶ�Ӧ��.h�ļ� 3.��.h�ļ�����c++���� 4.��c++����
//�����dll�ļ�(linux�����.so�ļ�) 5.java������load��Ӧ�Ŀ⣬ע�ⲻ����׺��һ�����Ϊlib+����