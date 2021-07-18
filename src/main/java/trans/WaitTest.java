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
        notify.join();//��join�������غ�Ż�����������̻߳��ڴ˶���
        System.out.println("Test ends");
    }

    //����waiting״̬���߳�������, ������blocked״̬�Ǳ�����.
    // ����һ����˵, ����blocked״̬����ͬ��(synchronized����֮��), ������waiting״̬����ͬ������֮��.
    public static class AA implements Runnable{
        @Override
        public void run() {
             synchronized (lock){
                 while(flag){
                     System.out.println(Thread.currentThread().getName() + " " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " Starts to Wait");
                     try {
                         lock.wait(); //ֻ���ڳ�������ʱ����ܵ���
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
                        lock.wait(); //ֻ���ڳ�������ʱ����ܵ���
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
