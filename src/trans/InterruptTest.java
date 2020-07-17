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
            if (Thread.interrupted()) { //���øþ�̬�����������߳��Ƿ��Ѿ����ж��ˣ��жϵ����жϱ�־λ�������⵽���жϣ���ôֹͣ��ǰ����ҵ
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
            Thread.currentThread().interrupt(); // Re-set the interrupted flag.  �����ﲶ���쳣������������жϱ�־����ô����
        }                                                                        //�����������ִ����ȥ
    }
}
