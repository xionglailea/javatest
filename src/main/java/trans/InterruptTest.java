package trans;

/**
 * Created by jieqinxiong on 2017/4/20.
 */
public class InterruptTest {
    public static void main(String[] args) throws InterruptedException {
        Thread sleeperThread = new Thread(() -> {
            Runnable job1 = new SleepJob();
            Runnable job2 = new SleepJob();
            Runnable job3 = new SleepJob();

            job1.run();
            if (Thread.interrupted()) {
                System.out.println("Interrupted in job1. Stop.");
                return;
            }
            job2.run();
            if (Thread.interrupted()) {
                System.out.println("Interrupted in job2. Stop.");
                return;
            }
            job3.run();
            if (Thread.interrupted()) { //调用该静态方法来检测该线程是否已经被中断了，判断的是中断标志位，如果检测到了中断，那么停止当前的作业
                System.out.println("Interrupted in job3. Stop.");
                return;
            }
            System.out.println("sleeperthread run over!");
        });

        sleeperThread.start();
        Thread.sleep(2500);
        sleeperThread.interrupt();
        sleeperThread.join();
    }
}
class SleepJob implements Runnable {
    @Override
    public void run() {
        try {
            System.out.println("Started sleeping.");
            Thread.sleep(1000);
            System.out.println("Finished sleeping.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Re-set the interrupted flag.  在这里捕获到异常后，如果不重置中断标志，那么整个
        }                                                                        //工作将会继续执行下去
    }
}
