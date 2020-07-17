package trans;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by xiong on 2017/3/27.
 */
public class WaitTest {
    public volatile static boolean flag = true;
    public static Object lock = new Object();
    public static void main(String[] args) throws InterruptedException {
        Thread wait = new Thread(new AA(), "WaitThread");
        wait.start();
        Thread wait2 = new Thread(new CC(), "WaitThread2");
        wait2.start();
        Thread notify = new Thread(new BB(), "NotifyThread");
        notify.start();
        wait.join();
        wait2.join();
        notify.join();//从join方法返回后才会继续，否者线程会在此堵塞
        System.out.println("Test ends");
    }

    //进入waiting状态是线程主动的, 而进入blocked状态是被动的.
    // 更进一步的说, 进入blocked状态是在同步(synchronized代码之外), 而进入waiting状态是在同步代码之内.
    public static class AA implements Runnable{
        @Override
        public void run() {
             synchronized (lock){
                 while(flag){
                     System.out.println(Thread.currentThread().getName() + " " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " Starts to Wait");
                     try {
                         lock.wait(); //只有在持有锁的时候才能调用
                     } catch (InterruptedException e) {
                         e.printStackTrace();
                     }
                 }
                 System.out.println(Thread.currentThread().getName() + " " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " ends Wait");
             }
        }
    }


    public static class CC implements Runnable{
        @Override
        public void run() {
            synchronized (lock){
                while(flag){
                    System.out.println(Thread.currentThread().getName() + " " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " Starts to Wait");
                    try {
                        lock.wait(); //只有在持有锁的时候才能调用
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName() + " " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " ends Wait");
            }
        }
    }

    public static class BB implements Runnable{
        @Override
        public void run() {
            synchronized (lock){
                System.out.println(Thread.currentThread().getName() + " " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " Starts to notify");
                flag = false;
                lock.notifyAll();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
