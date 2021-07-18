package trans;

public class SyncTest {
    public static class A {
        public synchronized void a() throws InterruptedException {
            System.out.println("a exec");
            Thread.sleep(5000);
        }

        public synchronized void b() {
            System.out.println("b exec");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        A test = new A();
        A testb = new A();
        Thread threada = new Thread(() -> {
            try {
                test.a();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        threada.start();
        Thread threadb = new Thread(testb::b);
        threadb.start();
        threada.join();
        threadb.join();
    }

}
