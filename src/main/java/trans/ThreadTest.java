package trans;

/**
 * Created by arctest on 2016/5/3.
 */
class InterruptibleSumCalculator implements Runnable {
    private long low;
    private long high;

    private boolean finished = false;
    private long result = 0;

    public InterruptibleSumCalculator(long low, long high) {
        this.low = low;
        this.high = high;
    }

    @Override
    public void run() {
        final long CHUNK_SIZE = 1024;
        long s = 0;

        for (long i = low; i <= high; i += CHUNK_SIZE) {
            for (long j = i; j < i + CHUNK_SIZE; j++) {
                s += j;
            }
            if (Thread.interrupted()) {
                System.out.format("Sum calculation interrupted.\nCurrent i: %d, partial sum: %d\n", i, s);
                result = s;
                Thread.currentThread().interrupt();  // re-set the interrupted flag.
                // Maybe this calculator is part of a bigger job.
                // I'll come back to this issue later.
                return;
            }
        }
        finished = true;
        result = s;
    }

    public boolean isFinished() {
        return finished;
    }

    public long getResult() {
        return result;
    }
}

public class ThreadTest {
    public static void main(String[] args) throws InterruptedException {
        InterruptibleSumCalculator isc = new InterruptibleSumCalculator(0L, 100000000000L);

        Thread t = new Thread(isc);

        System.out.println("Start calculating...");
        t.start();
        System.out.println("Waiting for it to finish...");
        t.join(2000);

        if(t.isAlive()) {
            System.out.println("Still alive. Try to interrupt it...");
            t.interrupt();
            System.out.println("Waiting for it to die...");
            t.join();//等待线程返回
        } else {
            System.out.println("Already dead.");
        }

        System.out.format("Finished? %s\nResult: %d\n", isc.isFinished(), isc.getResult());

    }
}
