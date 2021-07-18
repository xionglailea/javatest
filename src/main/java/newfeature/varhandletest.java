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
//        /**VarHandle 可以与任何字段、数组元素或静态变量关联，支持在不同访问模型下对这些类型变量的访问，
//         *包括简单的 read/write 访问，volatile 类型的 read/write 访问，和 CAS(compare-and-swap)等。
//         * 现在jdk的并发包中的unsafe.compareAndSwap都替换成了VarHandle的实现
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
