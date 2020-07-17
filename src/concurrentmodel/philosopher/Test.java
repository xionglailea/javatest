package concurrentmodel.philosopher;

import java.util.concurrent.*;

public class Test {
    public static void main(String[] args) {
        int num = 5;
        ShareData data = new ShareData(num);
        Executor executor = new ThreadPoolExecutor(1, Runtime.getRuntime().availableProcessors() * 2, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1));
        for(int i = 0; i < num; i++){
            Philosopher philosopher = new Philosopher(i, data);
            Thread thread = new Thread(philosopher, "ÕÜÑ§¼Ò" + i);
            thread.start();
        }
        Thread thread = new Thread(() -> {
            while(true){
                try {
                    Thread.sleep(5000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                data.printStatistic();
            }
        });
        thread.start();
    }
}
