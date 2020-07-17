package trans;

import java.util.concurrent.SynchronousQueue;

/**
 * ≤‚ ‘◊Ë»˚∂”¡–
 *
 * create by xiongjieqing on 2020/6/9 16:02
 */
public class SynchronousQueueTest {

  public static void main(String[] args) throws InterruptedException {
    SynchronousQueue<Runnable> queue = new SynchronousQueue<>();
    Thread thread1 = new Thread(() -> {
      try {
        queue.put(() -> System.out.println("haha"));
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });
    thread1.start();
    Thread.sleep(1000);
    Thread thread2 = new Thread(() -> {
      try {
        Runnable runnable = queue.take();
        runnable.run();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });
    thread2.start();
  }
}
