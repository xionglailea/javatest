package trans;

import java.util.concurrent.*;

/**
 * Created by arctest on 2015/12/14.
 */
public class ExecutorTest {
    public static int i = 0 ;
    public static void main(String[] args) throws InterruptedException, ExecutionException {
//        testFixed();
//        testCached();
        testShceldule();
    }

    public static void testFixed() throws InterruptedException, ExecutionException {
        ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(3); //创建线程池，可以通过工厂方法创建(会设置一些默认值)，也可以直接调用构造方法创建(灵活性好)
//        int a = 1000;
//        while(a > 0) {
//            pool.execute(new task1());
//            pool.execute(new task2());
//            a--;
//        }
        FutureTask<String> futureTask1 = new FutureTask<>(() -> {  //泛型方法自定义返回结果类型
            System.out.println("子线程开始");
            long a = System.currentTimeMillis();
            Thread.sleep(3000);
            System.out.println("子线程结束");
            return a + " ";
        });

        pool.submit(futureTask1);
        System.out.println("主线程");
        System.out.println(futureTask1.get());//这里用futureTask的get会阻塞住当前线程，直到futureTask的任务线程执行完毕，返回结果
        //所以可以在子线程里面执行一些耗时的工作，然后再主线程获取结果
        System.out.println(System.currentTimeMillis());
        pool.shutdown(); //注意要关闭线程池
        pool.awaitTermination(3, TimeUnit.SECONDS);
//        Thread t1 = new Thread(new ThreadTesterA());
//        Thread t2 = new Thread(new ThreadTesterB());
//        t1.start();
//        t1.join(); // wait t1 to be finished
//        t2.start();
//        t2.join(); // in this program, this may be removed
    }

    public static  class task1 implements Runnable{
        @Override
        public void run() {
            inc();
        }
    }
    public static class task2 implements Runnable{
        @Override
        public void run() {
            inc();
        }
    }

    public synchronized static void inc(){
        i = i + 1;
        System.out.println(i);
    }

    public static void testCached() throws InterruptedException {
        ThreadPoolExecutor CachedPool = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        ThreadTesterC t1 = new ThreadTesterC("t1");
        ThreadTesterC t2 = new ThreadTesterC("t2");
        ThreadTesterC t3 = new ThreadTesterC("t3");
        ThreadTesterC t4 = new ThreadTesterC("t4");
        System.out.println(CachedPool.getQueue().size()); //返回的容量始终为0
        CachedPool.execute(t1); //理论上这里不会出现阻塞的情况，每个新提交的任务，要么使用已有的工作线程，要么重新创建一个工作线程
        CachedPool.execute(t3);
        CachedPool.execute(t4);
        System.out.println(CachedPool.getQueue().size());
        Thread.sleep(2000); //加暂停后执行这两个任务的为同一个工作线程
        CachedPool.execute(t2);
        System.out.println(CachedPool.getQueue().size());
        CachedPool.shutdown();
    }

    public static int deadCount = 10;

    public static void testShceldule(){
        ScheduledExecutorService scheduleExecutor = Executors.newScheduledThreadPool(4);

//        for(int i = 0; i < 10; i++){

            scheduleExecutor.schedule(new RefRun(deadCount), 5, TimeUnit.SECONDS);
            deadCount = 20;
//        }
    }

    public static void testScheduled(){

    }

    public static class RefRun implements Runnable{
        int value;
        public RefRun(int a){
            value = a;
        }
        @Override
        public void run() {
            if(value != deadCount){
                System.out.println(value + " is not equals " + deadCount);
            }else {
                System.out.println("equals");
            }
        }
    }


   static class ThreadTesterA implements Runnable {

        public int counter;

        @Override
        public void run() {
            while (counter <= 10) {
                System.out.println("Counter = " + counter + " ");
                counter++;
            }
            System.out.println();
        }
    }

   static class ThreadTesterB implements Runnable {

        private int i;

        @Override
        public void run() {
            while (i <= 10) {
                System.out.println("i = " + i + " ");
                i++;
            }
            System.out.println();
        }
    }

    static class ThreadTesterC implements  Runnable{
        String name;
        public ThreadTesterC(String name){
            this.name = name;
        }
        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " is running " + name);
        }
    }

}
