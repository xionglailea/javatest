package TestFile;


import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/**
 * Created by xiong on 2016/7/6.
 */
public class Test1 {

    public static class A {

        private int a = 1;

        public A() {
            a = 2;
            hoo();
        }

        public void hoo() {
            System.out.print("A");
        }

        public int geta() {
            return a;
        }

        private void haha() {

        }

    }

    public static class B extends A {

        @Override
        public void hoo() {
            super.hoo();
            System.out.print("B");
        }
    }

    public static class C extends A {

    }

    public static class D extends C {

    }

    public final List<Integer> list;

    public Test1() {
        list = new ArrayList<>();
    }

    public static void main(String[] args) throws InterruptedException {
        //        Queue<Integer> a = new LinkedList<>();
        //        a.add(1);
        //        a.add(2);
        //        a.add(3);
        //        int b = a.poll();
        //        a.add(b);
        //        System.out.println(a);
        //        A testA = new A();
        //        System.out.println(testA.geta());
        //        Map<Integer, Integer> map = new HashMap<>();
        //        map.put(1,2);
        //        map.put(4,6);
        //        Map<Integer, Map<Integer, Integer>> recru = new HashMap<>();
        //        recru.put(3, map);
        ////        System.out.println(recru.values().stream().flatMap(e -> e.values().stream().filter(i-> i == 6)).findFirst().orElse(3));
        //        String s = "abc";
        //        String t = "abc";
        //        System.out.println(s == t);
        //        int a = 3;
        //        switch (a){
        //            case 0:
        //            case 1:{
        //                System.out.println("01");
        //                break;
        //            }
        //            case 3:
        //            case 2:{
        //                System.out.println("2");
        //                break;
        //            }
        //            case 4:{
        //                System.out.println("4");
        //                break;
        //            }
        //        }
        //        float e = 1;
        //        int f = 10;
        //        System.out.println(e/f);
        //        int g = ~0; //负数用补码表示
        //        System.out.println(Integer.toBinaryString(g));
        //        String h = "abcde";
        //        List<Integer> j = new ArrayList<>();
        //        System.out.println("中文");
        //        Integer i1 = 0;
        //        Integer i2 = 0;
        //        Integer i3 = 256;
        //        Integer i4 = 256;
        //        Integer i5 = i4--;
        //
        //        System.out.println(i1 == i2);
        //        System.out.println(i5 == i4);
        //        float x =  4.2f;
        //        System.out.println((int)Math.floor(x));
        //
        //        Integer y = 1;
        //        System.out.println(Integer.toBinaryString(y));
        //        int ii = 123456789;
        //        float ff = 123456789f;
        //        float dd = 1.6f;
        //
        //        System.out.println(ii);
        //        System.out.println(ff);
        //        System.out.println(dd);
        //        System.out.println(Integer.toBinaryString(Float.floatToIntBits(0.6f)));
        //        A a11 = null;
        //        System.out.print(8 & 8 - 1);
        //        List<RefObject<Integer>> l1 = new ArrayList<>();
        //        l1.add(new RefObject<>(1));
        //        List<RefObject<Integer>> l2 = new ArrayList<>(l1);
        //        l1.get(0).value = 2;
        //        System.out.println(l2.get(0).value);
        //        RefObject<Integer>[] a1 = new RefObject[1];
        //        a1[0] = new RefObject<>(1);
        //        RefObject<Integer>[] a2 = new RefObject[1];
        //        System.arraycopy(a1, 0, a2, 0, 1); // 对于对象，拷贝的是引用，对于基础类型，拷贝的是值
        //        System.out.println(a2[0].value);
        //        a1[0].value = 2;
        //        System.out.println(a2[0].value);
        //
        //        int[] a3 = new int[1];
        //        a3[0] = 1;
        //        int[] a4 = new int[1];
        //        System.arraycopy(a3, 0, a4, 0, 1);
        //        System.out.println(a4[0]);
        //        a3[0] = 2;
        //        System.out.println(a4[0]);
        //        B b = new B();
        //        BlockingQueue<Integer> queue = new SynchronousQueue<>();
        //        //该阻塞队列用在了cachedThreadPool中 做任务的队列使用，（无容量）
        //        System.out.print(queue.offer(1) + " ");
        //        System.out.print(queue.offer(2) + " ");
        //        System.out.print(queue.offer(3) + " ");
        //        System.out.print(queue.poll() + " ");
        //        System.out.println(queue.size());
        //        int x = 3;
        //        float y = 1.1f;
        //        System.out.println(x/y);
        //      System.out.println(String.format("%s one minute load = %f", Thread.currentThread().getName(), 1f));
        //      List<Integer> l1 = new ArrayList<>();
        //      l1.add(1);
        //      l1.add(2);
        //      l1.add(3);
        //      List<Integer> l2 = new ArrayList<>();
        //      l2.add(4);
        //      l2.add(2);
        //      l2.add(3);
        //      l1.removeAll(l2);
        //      for(var i : l1){
        //        System.out.println(i);
        //      }
        //      Count count = new Count();
        //      int start = 100;
        //      Thread t1 = new Thread(() -> count.count(start));
        //      Thread t2 = new Thread(() -> count.count(start));
        //      t1.start();
        //      t2.start();
        //      t1.join();
        //      t2.join();
        //      AtomicLong atomicLong = new AtomicLong(System.currentTimeMillis() << 12 + 4000);
        //      System.out.println(atomicLong.get());
        //      float a = 1;
        //      int b = Float.floatToIntBits(1);
        //      System.out.println(b);
        //       File target = new File("G:\\big-map\\vision\\native\\src\\main\\resources\\libServerMapJNI.dll");
        //       System.load(target.getAbsolutePath());
        //List<Integer> list = new ArrayList<>();
        //list.add(6);
        //list.add(2);
        //list.add(3);
        //list.add(7);
        //list.sort((o1, o2) -> {
        //    if (o1.intValue() != o2.intValue()) {
        //        return o1 - o2;
        //    }
        //    return 0;
        //});
        //for (int i : list) {
        //    System.out.println(i);
        //}
        System.out.println(binaryString2String("0101000001110010011011110110011101110010011000010110110101101101"));
        System.out.println(binaryString2String("0110100101101110011001110100100101110011010001100111010101101110"));
    }

    public static void testThreadLocal() {
        ThreadLocal<Integer> local = new ThreadLocal<>();
    }

    public static int print() {
        try {
            System.out.println(1);
            return 1;
        } finally {
            System.out.println(2);//finally的执行是在返回之前  就是说finally中的代码块始终会被执行
        }

    }

    public static String binaryString2String(String source) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < source.length() / 8; i++) {
            String temp = source.substring(i * 8, (i + 1) * 8);
            int j = Integer.parseUnsignedInt(temp, 2);
            builder.append((char)j);
        }
        return builder.toString();
    }

    public static class Count {

        public void count(int start) {
            while (start > 0) {
                System.out.println(Thread.currentThread().getName() + " " + start);
                start--;
            }
        }
    }
}
