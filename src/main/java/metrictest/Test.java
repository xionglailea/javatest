package metrictest;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.util.concurrent.TimeUnit;

/**
 * 监控工具类的使用
 * create by xiongjieqing on 2020/6/5 15:02
 */
public class Test {

  public static void main(String[] args) throws InterruptedException {
    testGauge();
  }

  //测量瞬时值
  public static void testGauge() throws InterruptedException {
    MetricRegistry metric = new MetricRegistry();
    ConsoleReporter reporter = ConsoleReporter.forRegistry(metric).build();
    reporter.start(1, TimeUnit.SECONDS);

    MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
    metric.register("jvm.used", new Gauge<Long>() {
      @Override
      public Long getValue() {
        return  memoryMXBean.getHeapMemoryUsage().getUsed() + memoryMXBean.getNonHeapMemoryUsage().getUsed();
      }
    });
    while (true) {
      Thread.sleep(1000);
    }
  }



}
