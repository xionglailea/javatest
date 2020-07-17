package metrictest;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 度量事件发生的频率 统计平均值，最近1分钟，最近5分钟，最近15分钟
 *
 * create by xiongjieqing on 2020/6/5 15:56
 */
public class MetricMeter {
  private static Random random = new Random();

  public static void request(Meter meter, int times) {
    for (int i = 0; i < times; i++) {
      meter.mark();
    }
  }

  public static void main(String[] args) throws InterruptedException {
    MetricRegistry registry = new MetricRegistry();

    ConsoleReporter reporter = ConsoleReporter.forRegistry(registry).build();
    reporter.start(1, TimeUnit.SECONDS);

    Meter meterTps = registry.meter("request.tps");
    while (true) {
      request(meterTps, random.nextInt(10));
      Thread.sleep(1000);
    }
  }
}
