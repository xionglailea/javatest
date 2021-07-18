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
            thread.stop = true;//����߳���������������˱���
            System.out.println(thread.isInterrupted());
            thread.interrupt();
            System.out.println(thread.isInterrupted()); //��ʱ��true �жϱ�־ ������
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
                        System.out.println("Catch the exception: " + thread.isInterrupted()); // ����
                    }
                }
                System.out.println("Thread exiting under request...");
            }
        }
}
