package newfeature;

import java.lang.invoke.MethodHandles;
//import java.lang.invoke.VarHandle;

public class varhandletest {

//    public static class Point{
//        public int x;
//        public int y;
//        public Point(int x, int y){
//            this.x = x;
//            this.y = y;
//        }
//    }
//
//    public static class VarHandleTest{
//        public int i;
//        public Point[] points = new Point[10];
//
//        /**VarHandle �������κ��ֶΡ�����Ԫ�ػ�̬����������֧���ڲ�ͬ����ģ���¶���Щ���ͱ����ķ��ʣ�
//         *�����򵥵� read/write ���ʣ�volatile ���͵� read/write ���ʣ��� CAS(compare-and-swap)�ȡ�
//         * ����jdk�Ĳ������е�unsafe.compareAndSwap���滻����VarHandle��ʵ��
//         */
//        private static final VarHandle INT_HANDLE;
//        private static final VarHandle ARRAY_HANDLE;
//        static {
//            try {
//                MethodHandles.Lookup lookup1 = MethodHandles.lookup();
//                INT_HANDLE = lookup1.findVarHandle(VarHandleTest.class, "i", int.class);
//                ARRAY_HANDLE = MethodHandles.arrayElementVarHandle(Point[].class);
//            } catch (ReflectiveOperationException e){
//                throw new Error(e);
//            }
//        }
//        public void test(){
//            System.out.println(INT_HANDLE.get(this));
//            INT_HANDLE.set(this, 100);
//            System.out.println(INT_HANDLE.get(this));
//            INT_HANDLE.compareAndSet(this, 100, 200);
//            System.out.println(INT_HANDLE.get(this));
//            INT_HANDLE.getAndAdd(this, 100);
//            System.out.println(INT_HANDLE.get(this));
//
//            System.out.println(((Point)ARRAY_HANDLE.get(points, 0)).x);
//            ARRAY_HANDLE.set(points, 0, new Point(10, 20));
//            System.out.println(((Point)ARRAY_HANDLE.get(points, 0)).x);
//
//
//        }
//    }
//
//
//    public static void main(String[] args) {
//        VarHandleTest test = new VarHandleTest();
//        test.i = 1;
//        test.points[0] = new Point(2, 3);
//        test.test();
//    }
}
