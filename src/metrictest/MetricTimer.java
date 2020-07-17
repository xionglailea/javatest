package metrictest;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * meter和histogram的集合,既统计频率，也统计分布
 * create by xiongjieqing on 2020/6/5 16:16
 */
public class MetricTimer {
  public static Random random = new Random();

  private static void request() throws InterruptedException {
    Thread.sleep(random.nextInt(1000));
  }

  public static void main(String[] args) throws Exception {
    MetricRegistry registry = new MetricRegistry();

    ConsoleReporter reporter = ConsoleReporter.forRegistry(registry).build();
    reporter.start(1, TimeUnit.SECONDS);

    Timer timer = registry.timer("request.latency");
    Timer.Context ctx;
    while (true) {
      ctx = timer.time();
      request(); //模拟接口调用的频率和耗时分布
      ctx.stop();
    }
  }
}
