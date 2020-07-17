package metrictest;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.UniformReservoir;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * ���ݷֲ������ͳ����Сֵ�����ֵ��ƽ��ֵ����λ���ȵ�
 *
 * create by xiongjieqing on 2020/6/5 16:02
 */
public class MetricHistogram {
  private static Random random = new Random();

  public static void main(String[] args) throws Exception {
    MetricRegistry registry = new MetricRegistry();

    ConsoleReporter reporter = ConsoleReporter.forRegistry(registry).build();
    reporter.start(1, TimeUnit.SECONDS);

    Histogram histogram = new Histogram(new UniformReservoir());
    registry.register("request.histogram", histogram);

    while (true) {
      Thread.sleep(1000);
      histogram.update(random.nextInt(200));
    }
  }

}
