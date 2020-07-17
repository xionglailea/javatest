package trans;

/**
 * Created by jieqinxiong on 2017/7/25.
 */
public class ThreadInterruptTest extends Thread {
        volatile boolean stop = false;
        public static Object lock = new Object();
        public static ThreadInterruptTest thread;
        public static void main( String args[] ) throws Exception {
            thread = new ThreadInterruptTest();
            System.out.println( "Starting thread..." );
            thread.start();
            Thread.sleep( 3000 );
            System.out.println( "Asking thread to stop..." );
            thread.stop = true;//如果线程阻塞，将不会检查此变量
            System.out.println(thread.isInterrupted());
            thread.interrupt();
            System.out.println(thread.isInterrupted()); //此时是true 中断标志 设置了
            Thread.sleep( 3000 );
            System.out.println( "Stopping application..." );
        }

        public void run() {
            synchronized (lock) {
                while (!stop) {
                    System.out.println("Thread running...");
                    try {
                        System.out.println("Thread is blocking...");
                        lock.wait();
                    } catch (InterruptedException e) {
                        System.out.println("Thread interrupted...");
                        System.out.println("Catch the exception: " + thread.isInterrupted()); // 重置
                    }
                }
                System.out.println("Thread exiting under request...");
            }
        }
}
