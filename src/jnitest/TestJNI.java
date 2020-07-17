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

//jni基本开发流程  1.写native函数 2.进入当前，使用javac -h . TestJNI.java 生成对应的.h文件 3.将.h文件拷到c++工程 4.将c++工程
//编译成dll文件(linux编译成.so文件) 5.java工程中load对应的库，注意不带后缀，一般库名为lib+名字