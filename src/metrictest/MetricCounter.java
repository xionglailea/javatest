package metrictest;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 计数器，累计指标
 *
 * create by xiongjieqing on 2020/6/5 15:53
 */
public class MetricCounter {

  private static Queue<String> queue = new LinkedBlockingQueue<String>();
  private static Counter pendingJobs;
  private static Random random = new Random();

  public static void addJob(String job) {
    pendingJobs.inc();
    queue.offer(job);
  }

  public static void takeJob() {
    pendingJobs.dec();
    queue.poll();
  }

  public static void main(String[] args) throws InterruptedException {
    MetricRegistry registry = new MetricRegistry();

    ConsoleReporter reporter = ConsoleReporter.forRegistry(registry).build();
    reporter.start(1, TimeUnit.SECONDS);

    pendingJobs = registry.counter("pending.jobs.size");
    for (int num = 1; ; num++) {
      Thread.sleep(1000);
      if (random.nextDouble() > 0.8) {
        takeJob();
      } else {
        addJob("Job-" + num);
      }
    }
  }

}
