package trans;

public class PossibleReordering {

    public static class NoVolatile{
        int x = 0, y = 0;
        int a = 0, b = 0;

        void run() throws InterruptedException {
            Thread one = new Thread(new Runnable() {
                @Override
                public void run() {
                    a = 1;
                    x = b;
                }
            });

            Thread other = new Thread(new Runnable() {
                @Override
                public void run() {
                    b = 1;
                    y = a;
                }
            });
            one.start();
            other.start();
            one.join();
            other.join();
        }
    }
    public static class WithSync{
        int x = 0, y = 0;
        int a = 0, b = 0;
        Object obj = new Object();


        void run() throws InterruptedException {
            Thread one = new Thread(new Runnable() {
                @Override
                public void run() {
                    a = 1;
                    synchronized (obj){}
                    x = b;
                }
            });

            Thread other = new Thread(new Runnable() {
                @Override
                public void run() {
                    b = 1;
                    synchronized (obj) {}
                    y = a;

                }
            });
            one.start();
            other.start();
            one.join();
            other.join();
        }
    }

    public static class WithVolatileOneWay{
        int x = 0, y = 0;
        int a = 0, b = 0;
        volatile int vv=33;

        void run() throws InterruptedException {
            Thread one = new Thread(new Runnable() {
                @Override
                public void run() {
                    a = 1;
                    vv = 33;
                    //int v = vv; //vv2;
                    x = b;
                }
            });

            Thread other = new Thread(new Runnable() {
                @Override
                public void run() {
                    b = 1;
                    int v = vv;
                    //vv= 333;
                    y = a;

                }
            });
            one.start();
            other.start();
            one.join();
            other.join();
        }
    }

    public static class WithVolatile{
        int x = 0, y = 0;
        int a = 0, b = 0;
        volatile int vv=33;

        void run() throws InterruptedException {
            Thread one = new Thread(new Runnable() {
                @Override
                public void run() {
                    a = 1;
                    vv = 33;
                    int v = vv; //vv2;
                    x = b;
                }
            });

            Thread other = new Thread(new Runnable() {
                @Override
                public void run() {
                    b = 1;
                    int v = vv;
                    vv= 333;
                    y = a;

                }
            });
            one.start();
            other.start();
            one.join();
            other.join();
        }
    }


    private static void test1() throws InterruptedException {
        System.out.println("no volatile");
        for(int i=0; i<100000; i++){
            NoVolatile nv = new NoVolatile();
            nv.run();
            if (nv.x == 0 && nv.y == 0){
                System.out.println("x=" + nv.x + ",y=" + nv.y);
            }
        }
    }

    private static void test2() throws InterruptedException {
        System.out.println("with sync");
        for(int i=0; i<1000000; i++){
            WithSync nv = new WithSync();
            nv.run();
            if (nv.x == 1 && nv.y == 1){
                System.out.println("x=" + nv.x + ",y=" + nv.y);
            }
        }
    }

    private static void test3() throws InterruptedException {
        System.out.println("with volatile");
        for(int i=0; i<100; i++){
            WithVolatile nv = new WithVolatile();
            nv.run();
//            if (nv.x == 0 && nv.y == 0){
                System.out.println("x=" + nv.x + ",y=" + nv.y);
//            }
        }
    }

    private static void test4() throws InterruptedException {
        System.out.println("with volatile one way");
        for(int i=0; i<100; i++){
            WithVolatileOneWay nv = new WithVolatileOneWay();
            nv.run();
//            if (nv.x == 0 && nv.y == 0){
                System.out.println("x=" + nv.x + ",y=" + nv.y);
//            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int i = 100;
//        test1();
        test2();
//        test3();
//        test4();

    }
}
